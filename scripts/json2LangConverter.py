#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
JSON到lang文件格式转换器

此脚本提供了将JSON格式文件转换为lang文件格式的功能。
支持自定义转换规则，具有良好的异常处理和可扩展性。
包含命令行界面和图形用户界面两种使用方式。

License: MIT
Author: TexTrue
"""

import json
import os
import sys
import argparse
import configparser
import tkinter as tk
from tkinter import filedialog, ttk, messagebox
from typing import Dict, Any, Optional, Union, Callable, List

# 确保中文显示正常
try:
    import matplotlib
    matplotlib.use('Agg')
    import matplotlib.pyplot as plt
    plt.rcParams['font.sans-serif'] = ['SimHei']
    plt.rcParams['axes.unicode_minus'] = False
except ImportError:
    pass


class LangFormatError(Exception):
    """当lang文件格式不符合预期时抛出的异常"""
    pass


class PathManager:
    """
    路径管理类，负责处理文件路径的选择、验证、记忆和错误处理
    """
    
    def __init__(self, config_file: str = None):
        """
        初始化路径管理器
        
        Args:
            config_file: 配置文件路径，用于保存最近使用的路径
        """
        # 默认配置文件路径
        if config_file is None:
            # 使用用户目录下的配置文件夹
            config_dir = os.path.join(os.path.expanduser("~"), ".json_to_lang")
            os.makedirs(config_dir, exist_ok=True)
            config_file = os.path.join(config_dir, "config.ini")
        
        self.config_file = config_file
        self.recent_paths = []
        self.max_recent_paths = 5  # 最多保存的最近路径数量
        
        # 加载配置
        self.load_config()
    
    def load_config(self):
        """
        从配置文件加载最近使用的路径
        """
        try:
            if os.path.exists(self.config_file):
                config = configparser.ConfigParser()
                config.read(self.config_file, encoding='utf-8')
                
                if 'RecentPaths' in config:
                    self.recent_paths = config['RecentPaths'].get('paths', '').split(';') if config['RecentPaths'].get('paths') else []
                    # 移除空路径和不存在的路径
                    self.recent_paths = [p for p in self.recent_paths if p and os.path.exists(p)]
        except Exception as e:
            print(f"加载配置文件时出错: {str(e)}")
    
    def save_config(self):
        """
        保存最近使用的路径到配置文件
        """
        try:
            config = configparser.ConfigParser()
            config['RecentPaths'] = {'paths': ';'.join(self.recent_paths[:self.max_recent_paths])}
            
            with open(self.config_file, 'w', encoding='utf-8') as f:
                config.write(f)
        except Exception as e:
            print(f"保存配置文件时出错: {str(e)}")
    
    def add_recent_path(self, path: str):
        """
        添加路径到最近使用列表
        
        Args:
            path: 要添加的路径
        """
        # 确保路径存在
        if not path or not os.path.exists(path):
            return
        
        # 如果路径已存在，先移除
        if path in self.recent_paths:
            self.recent_paths.remove(path)
        
        # 添加到列表开头
        self.recent_paths.insert(0, path)
        
        # 限制列表长度
        if len(self.recent_paths) > self.max_recent_paths:
            self.recent_paths = self.recent_paths[:self.max_recent_paths]
        
        # 保存配置
        self.save_config()
    
    def validate_path(self, path: str, is_directory: bool = False) -> tuple:
        """
        验证路径是否有效
        
        Args:
            path: 要验证的路径
            is_directory: 是否验证为目录
            
        Returns:
            (是否有效, 错误信息或空字符串)
        """
        # 检查路径是否为空
        if not path or not path.strip():
            return False, "路径不能为空"
        
        # 标准化路径
        path = os.path.normpath(path)
        
        # 如果是目录验证
        if is_directory:
            # 检查目录是否存在
            if not os.path.exists(path):
                # 尝试创建目录
                try:
                    os.makedirs(path, exist_ok=True)
                    return True, ""
                except PermissionError:
                    return False, f"没有权限创建或访问目录: {path}"
                except Exception as e:
                    return False, f"验证目录时出错: {str(e)}"
            
            # 检查是否是目录
            if not os.path.isdir(path):
                return False, f"指定的路径不是目录: {path}"
        else:
            # 文件路径验证
            # 检查父目录是否存在
            parent_dir = os.path.dirname(path)
            if parent_dir and not os.path.exists(parent_dir):
                return False, f"父目录不存在: {parent_dir}"
        
        # 检查写入权限
        try:
            # 对于目录，尝试创建临时文件来检查写入权限
            if is_directory:
                test_file = os.path.join(path, "_permission_test.tmp")
                with open(test_file, 'w') as f:
                    f.write("test")
                os.remove(test_file)
            # 对于文件，如果文件存在，检查是否可写；如果不存在，检查父目录是否可写
            else:
                if os.path.exists(path):
                    if not os.access(path, os.W_OK):
                        return False, f"没有权限写入文件: {path}"
                elif parent_dir:
                    if not os.access(parent_dir, os.W_OK):
                        return False, f"没有权限在目录中创建文件: {parent_dir}"
            
            return True, ""
        except PermissionError:
            return False, f"没有写入权限: {path}"
        except Exception as e:
            return False, f"验证路径权限时出错: {str(e)}"
    
    def select_directory_gui(self, initial_dir: str = None) -> Optional[str]:
        """
        通过GUI选择目录
        
        Args:
            initial_dir: 初始目录
            
        Returns:
            选择的目录路径或None
        """
        root = tk.Tk()
        root.withdraw()  # 隐藏主窗口
        
        # 如果没有提供初始目录，使用最近的路径
        if not initial_dir and self.recent_paths:
            # 找到第一个存在的目录作为初始目录
            for path in self.recent_paths:
                if os.path.isdir(path):
                    initial_dir = path
                    break
        
        # 如果初始目录不存在，使用当前目录
        if not initial_dir or not os.path.exists(initial_dir):
            initial_dir = os.getcwd()
        
        # 打开目录选择对话框
        directory = filedialog.askdirectory(
            title="选择输出目录",
            initialdir=initial_dir
        )
        
        root.destroy()
        
        # 如果用户选择了目录，添加到最近路径
        if directory:
            self.add_recent_path(directory)
        
        return directory
    
    def select_file_gui(self, initial_dir: str = None, file_type: tuple = ("JSON文件", "*.json")) -> Optional[str]:
        """
        通过GUI选择文件
        
        Args:
            initial_dir: 初始目录
            file_type: 文件类型过滤器
            
        Returns:
            选择的文件路径或None
        """
        root = tk.Tk()
        root.withdraw()  # 隐藏主窗口
        
        # 如果没有提供初始目录，使用最近的路径
        if not initial_dir and self.recent_paths:
            # 找到第一个存在的目录作为初始目录
            for path in self.recent_paths:
                if os.path.isdir(path):
                    initial_dir = path
                    break
                elif os.path.isfile(path):
                    initial_dir = os.path.dirname(path)
                    break
        
        # 如果初始目录不存在，使用当前目录
        if not initial_dir or not os.path.exists(initial_dir):
            initial_dir = os.getcwd()
        
        # 打开文件选择对话框
        file_path = filedialog.askopenfilename(
            title="选择文件",
            initialdir=initial_dir,
            filetypes=[file_type]
        )
        
        root.destroy()
        
        # 如果用户选择了文件，添加目录到最近路径
        if file_path:
            self.add_recent_path(os.path.dirname(file_path))
        
        return file_path


class JsonToLangConverter:
    """
    JSON到lang文件格式的转换器类
    
    提供了灵活的配置选项，支持不同的JSON结构和lang文件格式要求。
    默认以完全展平的格式输出。
    """
    
    def __init__(self,
                 key_separator: str = '.',
                 value_separator: str = '=',
                 comment_char: str = '#',
                 custom_formatter: Optional[Callable[[str, Any], str]] = None):
        """
        初始化转换器
        
        Args:
            key_separator: 嵌套键之间的分隔符
            value_separator: 键值之间的分隔符
            comment_char: 注释行的前缀字符
            custom_formatter: 自定义的格式化函数，接收(key, value)参数，返回格式化后的字符串
        """
        self.key_separator = key_separator
        self.value_separator = value_separator
        self.comment_char = comment_char
        self.flatten_nested = True  # 强制展平，移除选项
        self.custom_formatter = custom_formatter
        self.path_manager = PathManager()  # 添加路径管理器
    
    def read_json_file(self, file_path: str) -> Dict[str, Any]:
        """
        读取并解析JSON文件
        
        Args:
            file_path: JSON文件路径
            
        Returns:
            解析后的JSON数据
            
        Raises:
            FileNotFoundError: 文件不存在
            PermissionError: 没有文件读取权限
            json.JSONDecodeError: JSON格式错误
        """
        try:
            # 检查文件大小是否为0
            if os.path.getsize(file_path) == 0:
                raise ValueError("JSON文件为空，无法解析")
                
            with open(file_path, 'r', encoding='utf-8') as f:
                try:
                    data = json.load(f)
                    if not isinstance(data, dict):
                        raise LangFormatError(f"JSON文件必须包含一个对象，而不是 {type(data).__name__}")
                    return data
                except json.JSONDecodeError as e:
                    # 直接抛出原始异常，保留完整的错误信息
                    raise json.JSONDecodeError(str(e), e.doc, e.pos)
        except FileNotFoundError:
            raise FileNotFoundError(f"JSON文件不存在: {file_path}")
        except PermissionError:
            raise PermissionError(f"没有权限读取文件: {file_path}")
        except Exception as e:
            raise Exception(f"读取JSON文件时发生未知错误: {str(e)}")
    
    def flatten_json(self, data: Dict[str, Any], parent_key: str = '') -> Dict[str, Any]:
        """
        展平嵌套的JSON结构
        
        Args:
            data: JSON数据
            parent_key: 父键名
            
        Returns:
            展平后的字典
        """
        items = []
        for k, v in data.items():
            new_key = f"{parent_key}{self.key_separator}{k}" if parent_key else k
            if isinstance(v, dict):
                items.extend(self.flatten_json(v, new_key).items())
            else:
                items.append((new_key, v))
        return dict(items)
    
    def format_value(self, value: Any) -> str:
        """
        格式化值为字符串
        
        Args:
            value: 要格式化的值
            
        Returns:
            格式化后的字符串
        """
        if isinstance(value, bool):
            return 'true' if value else 'false'
        elif isinstance(value, (int, float)):
            return str(value)
        elif isinstance(value, (list, dict)):
            # 对于复杂类型，序列化为JSON字符串
            return json.dumps(value, ensure_ascii=False)
        elif value is None:
            return 'null'
        else:
            # 对于字符串，直接返回
            return str(value)
    
    def convert_to_lang(self, data: Dict[str, Any]) -> str:
        """
        将JSON数据转换为lang格式的字符串
        
        Args:
            data: JSON数据
            
        Returns:
            lang格式的字符串
            
        Raises:
            LangFormatError: 转换过程中遇到格式错误
        """
        try:
            result_lines = []
            
            # 添加文件头部注释
            result_lines.append(f"{self.comment_char} 此文件由JSON到lang格式转换器生成")
            result_lines.append(f"{self.comment_char} 请谨慎手动修改")
            result_lines.append("")
            
            # 强制展平数据
            processed_data = self.flatten_json(data)
            
            # 转换每一个键值对
            for key, value in processed_data.items():
                if self.custom_formatter:
                    # 使用自定义格式化函数
                    formatted_line = self.custom_formatter(key, value)
                    if formatted_line:
                        result_lines.append(formatted_line)
                else:
                    # 使用默认格式化
                    formatted_value = self.format_value(value)
                    result_lines.append(f"{key}{self.value_separator}{formatted_value}")
            
            return '\n'.join(result_lines)
        except Exception as e:
            raise LangFormatError(f"转换为lang格式时发生错误: {str(e)}")
    
    def write_lang_file(self, content: str, file_path: str) -> None:
        """
        将内容写入lang文件
        
        Args:
            content: 要写入的内容
            file_path: 目标文件路径
            
        Raises:
            PermissionError: 没有文件写入权限
            IOError: 文件写入失败
        """
        try:
            # 确保目录存在
            os.makedirs(os.path.dirname(os.path.abspath(file_path)), exist_ok=True)
            
            with open(file_path, 'w', encoding='utf-8') as f:
                f.write(content)
        except PermissionError:
            raise PermissionError(f"没有权限写入文件: {file_path}")
        except IOError as e:
            raise IOError(f"写入文件失败: {str(e)}")
        except Exception as e:
            raise Exception(f"写入lang文件时发生未知错误: {str(e)}")
    
    def convert_file(self, json_path: str, lang_path: str) -> bool:
        """
        完整的文件转换流程
        
        Args:
            json_path: 输入的JSON文件路径
            lang_path: 输出的lang文件路径
            
        Returns:
            转换是否成功
        """
        try:
            print(f"开始转换: {json_path} -> {lang_path}")
            
            # 验证输入文件是否存在
            if not os.path.exists(json_path):
                print(f"错误: 输入文件不存在: {json_path}")
                return False
            
            # 验证输出路径
            valid, error_msg = self.path_manager.validate_path(lang_path)
            if not valid:
                print(f"错误: {error_msg}")
                return False
            
            # 读取JSON文件
            data = self.read_json_file(json_path)
            print(f"成功读取JSON文件，包含 {len(data)} 个顶级键")
            
            # 转换为lang格式
            lang_content = self.convert_to_lang(data)
            print(f"成功转换为lang格式，生成 {lang_content.count('\n') + 1} 行内容")
            
            # 写入lang文件
            self.write_lang_file(lang_content, lang_path)
            print(f"成功写入lang文件: {lang_path}")
            
            # 添加到最近使用的路径
            self.path_manager.add_recent_path(os.path.dirname(lang_path))
            
            return True
        except Exception as e:
            print(f"错误: {str(e)}")
            return False
            
    def scan_directory_for_json(self, directory: str) -> list:
        """
        扫描目录中的所有JSON文件
        
        Args:
            directory: 要扫描的目录路径
            
        Returns:
            JSON文件路径列表
            
        Raises:
            NotADirectoryError: 路径不是目录
            PermissionError: 没有目录访问权限
        """
        if not os.path.isdir(directory):
            raise NotADirectoryError(f"指定的路径不是目录: {directory}")
        
        json_files = []
        try:
            for root, _, files in os.walk(directory):
                for file in files:
                    if file.lower().endswith('.json'):
                        file_path = os.path.join(root, file)
                        json_files.append(file_path)
        except PermissionError:
            raise PermissionError(f"没有权限访问目录: {directory}")
        except Exception as e:
            raise Exception(f"扫描目录时发生错误: {str(e)}")
        
        return json_files
    
    def batch_convert_files(self, json_files: list, output_dir: str = None) -> dict:
        """
        批量转换多个JSON文件
        
        Args:
            json_files: JSON文件路径列表
            output_dir: 输出目录，None表示与输入文件在同一目录
            
        Returns:
            转换结果字典，键为输入文件路径，值为(成功标志, 输出文件路径或错误信息)
        """
        results = {}
        
        for json_path in json_files:
            # 确定输出文件路径
            if output_dir:
                # 确保输出目录存在
                os.makedirs(output_dir, exist_ok=True)
                # 使用输入文件名但更改扩展名
                base_name = os.path.basename(json_path)
                name_without_ext = os.path.splitext(base_name)[0]
                lang_path = os.path.join(output_dir, f"{name_without_ext}.lang")
            else:
                # 在输入文件同一目录生成，更改扩展名
                lang_path = os.path.splitext(json_path)[0] + '.lang'
            
            # 执行单个文件转换
            success = self.convert_file(json_path, lang_path)
            
            if success:
                results[json_path] = (True, lang_path)
            else:
                results[json_path] = (False, f"转换失败")
        
        return results


class ConverterGUI:
    """
    转换器图形用户界面类
    """
    
    def __init__(self, root):
        """
        初始化GUI
        
        Args:
            root: tkinter根窗口
        """
        self.root = root
        self.root.title("JSON到lang文件转换器")
        self.root.geometry("700x500")
        self.root.resizable(True, True)
        
        # 设置中文字体
        self.setup_fonts()
        
        # 创建转换器实例
        self.converter = JsonToLangConverter()
        self.path_manager = self.converter.path_manager
        
        # 存储输入文件列表
        self.input_files = []
        
        # 创建界面
        self.create_widgets()
        
        # 加载最近路径
        self.load_recent_paths()
    
    def setup_fonts(self):
        """
        设置中文字体
        """
        try:
            # 在Windows上使用系统字体
            if sys.platform == 'win32':
                self.font_family = 'SimHei'
            else:
                # 在其他平台尝试常见的中文字体
                self.font_family = 'WenQuanYi Micro Hei'  # Linux
        except:
            self.font_family = 'Arial'  # 回退字体
    
    def create_widgets(self):
        """
        创建GUI组件
        """
        # 创建主框架
        main_frame = ttk.Frame(self.root, padding="10")
        main_frame.pack(fill=tk.BOTH, expand=True)
        
        # 输入文件选择区域
        input_frame = ttk.LabelFrame(main_frame, text="输入文件", padding="5")
        input_frame.pack(fill=tk.X, pady=5)
        
        # 添加文件按钮
        ttk.Button(
            input_frame,
            text="添加文件...",
            command=self.add_input_files
        ).pack(side=tk.LEFT, padx=5)
        
        # 添加文件夹按钮
        ttk.Button(
            input_frame,
            text="添加文件夹...",
            command=self.add_input_folder
        ).pack(side=tk.LEFT, padx=5)
        
        # 移除文件按钮
        ttk.Button(
            input_frame,
            text="移除选中",
            command=self.remove_selected_files
        ).pack(side=tk.LEFT, padx=5)
        
        # 清空文件按钮
        ttk.Button(
            input_frame,
            text="清空",
            command=self.clear_all_files
        ).pack(side=tk.LEFT, padx=5)
        
        # 文件列表框
        self.file_listbox = tk.Listbox(
            input_frame,
            selectmode=tk.EXTENDED,
            width=80,
            height=6
        )
        self.file_listbox.pack(side=tk.LEFT, fill=tk.BOTH, expand=True, padx=5)
        
        # 滚动条
        scrollbar = ttk.Scrollbar(input_frame, orient=tk.VERTICAL, command=self.file_listbox.yview)
        scrollbar.pack(side=tk.RIGHT, fill=tk.Y)
        self.file_listbox.config(yscrollcommand=scrollbar.set)
        
        # 输出路径区域
        output_frame = ttk.LabelFrame(main_frame, text="输出设置", padding="5")
        output_frame.pack(fill=tk.X, pady=5)
        
        # 输出目录选择
        ttk.Label(output_frame, text="输出目录:").grid(row=0, column=0, sticky=tk.W, padx=5, pady=5)
        
        self.output_dir_var = tk.StringVar()
        output_dir_entry = ttk.Entry(output_frame, textvariable=self.output_dir_var, width=50)
        output_dir_entry.grid(row=0, column=1, sticky=tk.EW, padx=5, pady=5)
        
        ttk.Button(
            output_frame,
            text="浏览...",
            command=self.select_output_directory
        ).grid(row=0, column=2, padx=5, pady=5)
        
        # 最近路径下拉菜单
        ttk.Label(output_frame, text="最近路径:").grid(row=1, column=0, sticky=tk.W, padx=5, pady=5)
        
        self.recent_path_var = tk.StringVar()
        self.recent_paths_combo = ttk.Combobox(output_frame, textvariable=self.recent_path_var, width=48)
        self.recent_paths_combo.grid(row=1, column=1, sticky=tk.EW, padx=5, pady=5)
        self.recent_paths_combo.bind("<<ComboboxSelected>>", self.on_recent_path_selected)
        
        # 转换选项区域
        options_frame = ttk.LabelFrame(main_frame, text="转换选项", padding="5")
        options_frame.pack(fill=tk.X, pady=5)
        
        # 键分隔符
        ttk.Label(options_frame, text="键分隔符:").grid(row=0, column=0, sticky=tk.W, padx=5, pady=5)
        self.key_separator_var = tk.StringVar(value='.')
        ttk.Entry(options_frame, textvariable=self.key_separator_var, width=10).grid(row=0, column=1, sticky=tk.W, padx=5, pady=5)
        
        # 值分隔符
        ttk.Label(options_frame, text="值分隔符:").grid(row=0, column=2, sticky=tk.W, padx=5, pady=5)
        self.value_separator_var = tk.StringVar(value='=')
        ttk.Entry(options_frame, textvariable=self.value_separator_var, width=10).grid(row=0, column=3, sticky=tk.W, padx=5, pady=5)
        
        # 注释字符
        ttk.Label(options_frame, text="注释字符:").grid(row=0, column=4, sticky=tk.W, padx=5, pady=5)
        self.comment_char_var = tk.StringVar(value='#')
        ttk.Entry(options_frame, textvariable=self.comment_char_var, width=10).grid(row=0, column=5, sticky=tk.W, padx=5, pady=5)
        
        # 日志区域
        log_frame = ttk.LabelFrame(main_frame, text="转换日志", padding="5")
        log_frame.pack(fill=tk.BOTH, expand=True, pady=5)
        
        # 日志文本框
        self.log_text = tk.Text(log_frame, wrap=tk.WORD, height=10)
        self.log_text.pack(side=tk.LEFT, fill=tk.BOTH, expand=True, padx=5)
        
        # 日志滚动条
        log_scrollbar = ttk.Scrollbar(log_frame, orient=tk.VERTICAL, command=self.log_text.yview)
        log_scrollbar.pack(side=tk.RIGHT, fill=tk.Y)
        self.log_text.config(yscrollcommand=log_scrollbar.set)
        
        # 按钮区域
        button_frame = ttk.Frame(main_frame)
        button_frame.pack(fill=tk.X, pady=10)
        
        # 转换按钮
        ttk.Button(
            button_frame,
            text="开始转换",
            command=self.start_conversion,
            style="Accent.TButton"
        ).pack(side=tk.RIGHT, padx=10)
        
        # 设置样式
        style = ttk.Style()
        style.configure("Accent.TButton", font=(self.font_family, 10, "bold"))
        
        # 配置列权重，使输入框可以拉伸
        output_frame.columnconfigure(1, weight=1)
        options_frame.columnconfigure(6, weight=1)
    
    def load_recent_paths(self):
        """
        加载最近使用的路径到下拉菜单
        """
        self.recent_paths_combo['values'] = self.path_manager.recent_paths
    
    def add_input_files(self):
        """
        添加输入文件
        """
        # 获取初始目录
        initial_dir = None
        if self.input_files:
            initial_dir = os.path.dirname(self.input_files[0])
        elif self.path_manager.recent_paths:
            # 尝试使用最近路径中的目录或文件的父目录
            for path in self.path_manager.recent_paths:
                if os.path.isdir(path):
                    initial_dir = path
                    break
                elif os.path.isfile(path):
                    initial_dir = os.path.dirname(path)
                    break
        
        # 打开文件选择对话框
        files = filedialog.askopenfilenames(
            title="选择JSON文件",
            filetypes=[("JSON文件", "*.json")],
            initialdir=initial_dir
        )
        
        # 添加选择的文件
        self._add_files_to_list(files)
    
    def add_input_folder(self):
        """
        添加输入文件夹（扫描其中的所有JSON文件）
        """
        # 获取初始目录
        initial_dir = None
        if self.input_files:
            initial_dir = os.path.dirname(self.input_files[0])
        elif self.path_manager.recent_paths:
            # 尝试使用最近路径中的目录或文件的父目录
            for path in self.path_manager.recent_paths:
                if os.path.isdir(path):
                    initial_dir = path
                    break
                elif os.path.isfile(path):
                    initial_dir = os.path.dirname(path)
                    break
        
        # 打开目录选择对话框
        folder = filedialog.askdirectory(
            title="选择包含JSON文件的文件夹",
            initialdir=initial_dir
        )
        
        if not folder:
            return
        
        # 扫描目录中的JSON文件
        try:
            self.log(f"正在扫描文件夹: {folder}")
            json_files = self.converter.scan_directory_for_json(folder)
            
            if not json_files:
                messagebox.showinfo("信息", f"所选文件夹中未找到JSON文件")
                return
            
            self.log(f"找到 {len(json_files)} 个JSON文件")
            
            # 添加找到的文件
            self._add_files_to_list(json_files)
            
            # 更新最近路径
            self.path_manager.add_recent_path(folder)
            self.load_recent_paths()
            
        except NotADirectoryError as e:
            messagebox.showerror("错误", str(e))
        except PermissionError as e:
            messagebox.showerror("错误", str(e))
        except Exception as e:
            messagebox.showerror("错误", f"扫描文件夹时出错: {str(e)}")
    
    def _add_files_to_list(self, files):
        """
        将文件添加到列表和列表框
        
        Args:
            files: 文件路径列表
        """
        added_count = 0
        for file in files:
            if file not in self.input_files:
                self.input_files.append(file)
                self.file_listbox.insert(tk.END, file)
                added_count += 1
        
        # 更新最近路径
        if files and added_count > 0:
            self.path_manager.add_recent_path(os.path.dirname(files[0]))
            self.load_recent_paths()
            
        if added_count > 0:
            self.log(f"添加了 {added_count} 个文件")
    
    def remove_selected_files(self):
        """
        移除选中的文件
        """
        # 获取选中的索引（反向获取，避免删除时索引变化）
        selected_indices = sorted(self.file_listbox.curselection(), reverse=True)
        
        for index in selected_indices:
            # 从列表和列表框中移除
            self.input_files.pop(index)
            self.file_listbox.delete(index)
    
    def clear_all_files(self):
        """
        清空所有文件
        """
        self.input_files.clear()
        self.file_listbox.delete(0, tk.END)
    
    def select_output_directory(self):
        """
        选择输出目录
        """
        # 获取当前输入的目录或最近路径
        current_dir = self.output_dir_var.get()
        if not current_dir or not os.path.exists(current_dir):
            current_dir = None
        
        # 使用路径管理器选择目录
        directory = self.path_manager.select_directory_gui(current_dir)
        
        if directory:
            self.output_dir_var.set(directory)
            self.load_recent_paths()  # 更新最近路径下拉菜单
    
    def on_recent_path_selected(self, event):
        """
        选择最近路径时的处理
        """
        selected_path = self.recent_path_var.get()
        if selected_path:
            self.output_dir_var.set(selected_path)
    
    def log(self, message):
        """
        记录日志消息
        
        Args:
            message: 要记录的消息
        """
        self.log_text.insert(tk.END, message + "\n")
        self.log_text.see(tk.END)  # 滚动到底部
    
    def validate_inputs(self):
        """
        验证输入
        
        Returns:
            (是否有效, 错误消息或空字符串)
        """
        # 检查是否选择了文件
        if not self.input_files:
            return False, "请选择至少一个输入文件"
        
        # 检查所有输入文件是否存在
        for file in self.input_files:
            if not os.path.exists(file):
                return False, f"输入文件不存在: {file}"
        
        # 检查输出目录
        output_dir = self.output_dir_var.get()
        if not output_dir:
            # 如果未指定输出目录，使用第一个输入文件的目录
            output_dir = os.path.dirname(self.input_files[0])
            self.output_dir_var.set(output_dir)
        
        # 验证输出目录
        valid, error_msg = self.path_manager.validate_path(output_dir, is_directory=True)
        if not valid:
            return False, f"输出目录无效: {error_msg}"
        
        return True, ""
    
    def start_conversion(self):
        """
        开始转换
        """
        # 验证输入
        valid, error_msg = self.validate_inputs()
        if not valid:
            messagebox.showerror("输入错误", error_msg)
            return
        
        # 清除日志
        self.log_text.delete(1.0, tk.END)
        
        # 更新转换器设置
        self.converter.key_separator = self.key_separator_var.get() or '.'
        self.converter.value_separator = self.value_separator_var.get() or '='
        self.converter.comment_char = self.comment_char_var.get() or '#'
        
        # 获取输出目录
        output_dir = self.output_dir_var.get()
        
        # 记录开始信息
        self.log(f"开始转换 {len(self.input_files)} 个文件")
        self.log(f"输出目录: {output_dir}")
        self.log("=" * 50)
        
        # 执行批量转换
        results = self.converter.batch_convert_files(self.input_files, output_dir)
        
        # 统计结果
        success_count = sum(1 for success, _ in results.values() if success)
        
        self.log("=" * 50)
        self.log(f"批量转换完成")
        self.log(f"成功: {success_count}")
        self.log(f"失败: {len(results) - success_count}")
        
        # 显示详细结果
        failed_files = []
        for file, (success, info) in results.items():
            if success:
                self.log(f"✓ 成功: {os.path.basename(file)} -> {os.path.basename(info)}")
            else:
                failed_files.append(file)
                self.log(f"✗ 失败: {os.path.basename(file)} - {info}")
        
        # 显示完成消息
        if success_count == len(results):
            messagebox.showinfo("转换成功", f"所有 {success_count} 个文件转换成功！")
        else:
            messagebox.showwarning(
                "部分成功",
                f"{success_count} 个文件转换成功，{len(failed_files)} 个文件转换失败。请查看日志获取详细信息。"
            )


def main():
    """
    主函数，处理命令行参数并执行转换
    """
    # 检查是否需要启动GUI
    if len(sys.argv) == 1:
        # 没有命令行参数，启动GUI
        root = tk.Tk()
        app = ConverterGUI(root)
        root.mainloop()
        return
    
    # 有命令行参数，使用命令行模式
    parser = argparse.ArgumentParser(description='将JSON格式文件转换为lang文件格式')
    parser.add_argument('input_paths', nargs='+', help='输入的JSON文件路径或文件夹路径（支持多个）')
    parser.add_argument('-o', '--output-dir', help='输出目录（可选，默认与输入文件在同一目录）')
    parser.add_argument('--key-separator', default='.', help='嵌套键之间的分隔符 (默认: .)')
    parser.add_argument('--value-separator', default='=', help='键值之间的分隔符 (默认: =)')
    parser.add_argument('--comment-char', default='#', help='注释行的前缀字符 (默认: #)')
    parser.add_argument('--gui', action='store_true', help='启动图形用户界面')
    
    args = parser.parse_args()
    
    # 如果指定了--gui参数，启动GUI
    if args.gui:
        root = tk.Tk()
        app = ConverterGUI(root)
        root.mainloop()
        return
    
    # 创建转换器实例
    converter = JsonToLangConverter(
        key_separator=args.key_separator,
        value_separator=args.value_separator,
        comment_char=args.comment_char
    )
    
    # 处理输入路径，支持文件和文件夹
    all_json_files = []
    for path in args.input_paths:
        if os.path.isfile(path):
            # 单个文件，直接添加
            if path.lower().endswith('.json'):
                all_json_files.append(path)
            else:
                print(f"警告: {path} 不是JSON文件，将被忽略")
        elif os.path.isdir(path):
            # 文件夹，扫描其中的JSON文件
            try:
                print(f"正在扫描文件夹: {path}")
                json_files = converter.scan_directory_for_json(path)
                print(f"在 {path} 中找到 {len(json_files)} 个JSON文件")
                all_json_files.extend(json_files)
            except Exception as e:
                print(f"错误: 处理文件夹 {path} 时出错: {str(e)}")
        else:
            print(f"警告: {path} 不存在，将被忽略")
    
    if not all_json_files:
        print("错误: 没有找到有效的JSON文件")
        exit(1)
    
    # 判断是单个文件还是多个文件
    if len(all_json_files) == 1:
        # 单个文件处理逻辑
        input_file = all_json_files[0]
        if args.output_dir:
            # 如果指定了输出目录，使用输入文件名创建输出文件
            base_name = os.path.basename(input_file)
            name_without_ext = os.path.splitext(base_name)[0]
            output_file = os.path.join(args.output_dir, f"{name_without_ext}.lang")
        else:
            # 默认输出文件名
            output_file = os.path.splitext(input_file)[0] + '.lang'
        
        success = converter.convert_file(input_file, output_file)
        exit(0 if success else 1)
    else:
        # 批量处理多个文件
        print(f"开始批量转换 {len(all_json_files)} 个文件")
        results = converter.batch_convert_files(all_json_files, args.output_dir)
        
        # 统计结果
        success_count = sum(1 for success, _ in results.values() if success)
        
        print(f"\n批量转换完成")
        print(f"成功: {success_count}")
        print(f"失败: {len(results) - success_count}")
        
        # 输出失败的文件
        failed_files = [file for file, (success, _) in results.items() if not success]
        if failed_files:
            print(f"\n失败的文件:")
            for file in failed_files:
                print(f"  - {file}")
        
        exit(0 if success_count == len(results) else 1)


if __name__ == '__main__':
    main()