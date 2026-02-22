package top.vmctcn.vmtu.multiversion.screen;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
//? if >=1.20.1 {
import net.minecraft.client.gui.components.Tooltip;
//?}
//? if >=1.18.2 {
import net.minecraft.client.gui.components.PlainTextButton;
//?}
import net.minecraft.network.chat.Component;
//? if <=1.20.1 {
/*import top.vmctcn.vmtu.multiversion.screen.widgets.CheckboxWidget;
import top.vmctcn.vmtu.multiversion.screen.widgets.PlainTextButtonWidget;
*///?}

public class Widgets {
    public static Button createButton(Component component, int x, int y, int width, int height, Button.OnPress onPress) {
        //? if >=1.20.1 {
        return Button.builder(component, onPress).bounds(x, y, width, height).build();
        //?} else if <=1.19.2 {
        /*return new Button(x, y, width, height, component, onPress);
        *///?}
    }

    public static /*? if >=1.18.2 {*/PlainTextButton/*?} else {*//*PlainTextButtonWidget*//*?}*/ createPlainTextButton(Component component, Component tooltip, int x, int y, int width, int height, Button.OnPress onPress, Font font) {
        //? if >=1.20.1 {
        PlainTextButton button = new PlainTextButton(x, y, width, height, component, onPress, font);
        button.setTooltip(Tooltip.create(tooltip));
        return button;
        //?} else if <=1.19.2 {
        /*PlainTextButtonWidget button = new PlainTextButtonWidget(x, y, width, height, component, onPress, font);
        button.setTooltip(tooltip);
        return button;
        *///?}
    }

    public static Checkbox createCheckbox(Font font, Component component, Component tooltip, int x, int y, /*? if >=1.20.4 {*/Checkbox.OnValueChange/*?} else {*//*CheckboxWidget.OnValueChange*//*?}*/ onValueChange) {
        //? if >=1.20.4 {
        return Checkbox.builder(component, font)
                .tooltip(Tooltip.create(tooltip))
                .pos(x, y)
                .onValueChange(onValueChange)
                .build();
        //?} else if 1.20.1 {
        /*return CheckboxWidget.builder(component, font)
                .tooltip(Tooltip.create(tooltip))
                .pos(x, y)
                .onValueChange(onValueChange)
                .build();
        *///?} else if <=1.19.2 {
        /*return CheckboxWidget.builder(component, font)
                .tooltip(tooltip)
                .pos(x, y)
                .onValueChange(onValueChange)
                .build();
        *///?}
    }
}
