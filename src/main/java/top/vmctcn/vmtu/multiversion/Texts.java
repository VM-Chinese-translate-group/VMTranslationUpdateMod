package top.vmctcn.vmtu.multiversion;

import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class Texts {
    public static Text empty() {
        return new LiteralText("").copy();
    }

    public static Text literal(String string) {
        return new LiteralText(string);
    }

    public static Text translate(String key) {
        return new TranslatableText(key);
    }

    public static Text translatable(String key, Object... args) {
        return new TranslatableText(key, args);
    }
}
