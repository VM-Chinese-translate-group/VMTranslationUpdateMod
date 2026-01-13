package top.vmctcn.vmtu.multiversion;

//? if >=1.19.2 {
import net.minecraft.network.chat.Component;
//?} else if <=1.18.2 {
/*import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
*///?}
import net.minecraft.network.chat.MutableComponent;

public class Texts {
    public static MutableComponent empty() {
        //? if >=1.19.2 {
        return Component.empty();
        //?} else if <=1.18.2 {
        /*return TextComponent.EMPTY.copy();
        *///?}
    }

    public static MutableComponent literal(String key) {
        //? if >=1.19.2 {
        return Component.literal(key);
        //?} else if <=1.18.2 {
        /*return new TextComponent(key);
        *///?}
    }

    public static MutableComponent translate(String key) {
        //? if >=1.19.2 {
        return Component.translatable(key);
        //?} else if <=1.18.2 {
        /*return new TranslatableComponent(key);
        *///?}
    }

    public static MutableComponent translatable(String key, Object... args) {
        //? if >=1.19.2 {
        return Component.translatable(key, args);
        //?} else if <=1.18.2 {
        /*return new TranslatableComponent(key, args);
        *///?}
    }
}
