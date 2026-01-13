package top.vmctcn.vmtu.multiversion.screen;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class ButtonUtils {
    public static Button create(Component component, int i, int j, int k, int l, Button.OnPress onPress) {
        //? if >=1.20.1 {
        return Button.builder(component, onPress).bounds(i, j, k, l).build();
        //?} else if <=1.19.2 {
        /*return new Button(i, j, k, l, component, onPress);
        *///?}
    }
}
