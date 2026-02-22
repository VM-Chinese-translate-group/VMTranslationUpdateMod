package top.vmctcn.vmtu.mod.screen;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.ChatFormatting;
//? if >=1.20.1 {
import net.minecraft.client.gui.GuiGraphics;
//?} else {
/*import com.mojang.blaze3d.vertex.PoseStack;
*///?}
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Checkbox;
//? if >=1.18.2 {
import net.minecraft.client.gui.components.PlainTextButton;
//?} else {
/*import top.vmctcn.vmtu.multiversion.screen.widgets.PlainTextButtonWidget;
*///?}
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.ModPlatform;
import top.vmctcn.vmtu.mod.config.ModConfigHelper;
import top.vmctcn.vmtu.mod.config.ModConfigs;
import top.vmctcn.vmtu.mod.utils.RequiredMods;
import top.vmctcn.vmtu.multiversion.util.StringUtils;
import top.vmctcn.vmtu.multiversion.Texts;
import top.vmctcn.vmtu.multiversion.screen.ScreenUtils;
import top.vmctcn.vmtu.multiversion.screen.Widgets;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class MissingModScreen extends Screen {
    @Nullable
    private final ArrayList<RequiredMods> missingMods;
    @Nullable
    private final Screen parent;

    protected static final int HEADER_HEIGHT = 40;
    protected static final int FOOTER_HEIGHT = 50;

    public MissingModScreen(@Nullable ArrayList<RequiredMods> missingMods) {
        this(missingMods, null);
    }

    public MissingModScreen(@Nullable ArrayList<RequiredMods> missingMods, @Nullable Screen parent) {
        super(Texts.translatable(ModContexts.getTranslationKey("screen", "missing_mod", "title"), RequiredMods.getAllMissing().size()));
        this.missingMods = missingMods;
        this.parent = parent;
    }

    @Override
    protected void init() {
        if (missingMods != null && !missingMods.isEmpty()) {
            int widest = missingMods.stream().map(mod -> mod.getName().length())
                    .max(Comparator.naturalOrder()).orElse(0);
            String brackets = "[" + StringUtils.repeat(" ", widest + 17) + "]";
            AtomicInteger index = new AtomicInteger(0);

            missingMods.forEach(mod -> {
                Component text = Texts.literal(mod.getName())
                        .withStyle(ChatFormatting.RED)
                        .withStyle(mod.isRequired() ? ChatFormatting.RED : ChatFormatting.YELLOW);

                int y = 40 + (height - 90) * index.incrementAndGet() / ((int) missingMods.size() + 1);

                if (mod.isLoadedCheck()) {
                    if (mod.isRequired()) {
                        // Brackets
                        modDownloadButton(mod, Texts.literal(brackets).withStyle(ChatFormatting.DARK_RED), y);
                        // Names
                        modDownloadButton(mod, text, y);
                    } else {
                        // Brackets
                        modDownloadButton(mod, Texts.literal(brackets).withStyle(ChatFormatting.YELLOW), y);
                        // Names
                        modDownloadButton(mod, text, y);
                    }
                }
            });

            Component optionalModCheck = ModContexts.getTranslatableText("checkbox", "missing_mod", "optional");
            optionalModCheckBox(optionalModCheck);

            Component quit = ModContexts.getTranslatableText("button", "missing_mod", "quit");
            Component skip = ModContexts.getTranslatableText("button", "missing_mod", "skip");

            if (this.shouldCloseOnEsc()) {
                skipAndQuitButton(quit, skip);
            } else {
                quitButton(quit);
            }
        } else {
            this.minecraft.setScreen(parent);
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return this.parent != null;
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(parent);
    }

    @Override
    public void render(
            /*? if >=1.20.1 {*/
            GuiGraphics guiGraphics
            /*?} else {*/
            /*PoseStack poseStack
             *//*?}*/,
            int mouseX, int mouseY, float delta
    ) {
        //? if 1.20.1 {
        /*this.renderBackground(guiGraphics);
         *///?} else if <=1.19.2 {
        /*this.renderBackground(poseStack);
         *///?}

        //? if >=1.20.1 {
        super.render(guiGraphics, mouseX, mouseY, delta);
        //?} else if <=1.19.2 {
        /*super.render(poseStack, mouseX, mouseY, delta);
         *///?}

        MutableComponent title = Texts.literal(this.title.getString()).withStyle(ChatFormatting.BOLD);
        MutableComponent subtitle = ModContexts.getTranslatableText("screen", "missing_mod", "subtitle");
        MutableComponent description = ModContexts.getTranslatableText("screen", "missing_mod", "description");

        ScreenUtils.drawCenteredTextWithShadow(
                /*? if >=1.20.1 {*/
                guiGraphics
                /*?} else {*/
                /*poseStack
                 *//*?}*/,
                this.font, title, this.width / 2, (HEADER_HEIGHT / 2) - (this.font.lineHeight / 2), -1);
        ScreenUtils.drawCenteredTextWithShadow(
                /*? if >=1.20.1 {*/
                guiGraphics
                /*?} else {*/
                /*poseStack
                 *//*?}*/,
                this.font, subtitle, this.width / 2, (HEADER_HEIGHT / 2) - (this.font.lineHeight / 2) + 15, -1);
        ScreenUtils.drawCenteredTextWithShadow(
                /*? if >=1.20.1 {*/
                guiGraphics
                /*?} else {*/
                /*poseStack
                 *//*?}*/,
                this.font, description, this.width / 2, (HEADER_HEIGHT / 2) - (this.font.lineHeight / 2) + 30, -1);
    }

    private void modDownloadButton(RequiredMods mod, Component text, int y) {
        Component tooltip = ModContexts.getTranslatableText("button", "missing_mod", mod.getName().toLowerCase(), "tooltip");
        /*? if >=1.18.2 {*/PlainTextButton/*?} else {*//*PlainTextButtonWidget*//*?}*/ button = Widgets.createPlainTextButton(text, tooltip,
                this.width / 2 - this.font.width(text) / 2, y,
                this.font.width(text), 10, buttonWidget -> {
                    mod.openUrl();
                },
                this.font
        );

        this.addButtonWidget(button);
    }

    private void skipAndQuitButton(Component quit, Component skip) {
        // Quit
        this.addButtonWidget(
            Widgets.createButton(quit, (this.width / 2) - 155, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20, buttonWidget -> {
                Util.getPlatform().openFile(ModPlatform.getGameDir().resolve("mods").toFile());
                if (this.minecraft != null) {
                    minecraft.stop();
                }
            })
        );

        // Skip
        this.addButtonWidget(
            Widgets.createButton(skip, (this.width / 2) + 5, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20, buttonWidget -> {
                this.onClose();
            })
        );
    }

    private void quitButton(Component quit) {
        this.addButtonWidget(
            Widgets.createButton(quit, (this.width / 2) - 155, this.height - (FOOTER_HEIGHT / 2) - 10, 300, 20, buttonWidget -> {
                Util.getPlatform().openFile(ModPlatform.getGameDir().resolve("mods").toFile());
                if (this.minecraft != null) {
                    minecraft.stop();
                }
            })
        );
    }

    private void optionalModCheckBox(Component text) {
        Component tooltip = ModContexts.getTranslatableText("checkbox", "missing_mod", "optional", "tooltip");
        Checkbox checkbox = Widgets.createCheckbox(this.font, text, tooltip, (this.width / 2) - 50, this.height - (FOOTER_HEIGHT / 2) - 35, (checkboxWidget, selected) -> {
            this.setOptionalModConfigOption(!selected);
        });
        checkbox.visible = ModConfigHelper.getConfig().modInstallCheck.textureLocaleRedirector;
        this.addButtonWidget(checkbox);
    }

    public <T extends AbstractWidget> T addButtonWidget(T widget) {
        //? if >1.16.5 {
        return this.addRenderableWidget(widget);
        //?} else {
        /*return this.addButton(widget);
         *///?}
    }

    public void setOptionalModConfigOption(boolean value) {
        ModConfigHelper.getConfig().modInstallCheck.textureLocaleRedirector = value;
        AutoConfig.getConfigHolder(ModConfigs.class).save();
    }
}
