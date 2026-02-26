package top.vmctcn.vmtu.mod.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Formatting;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.ModPlatform;
import top.vmctcn.vmtu.mod.config.ModConfigs;
import top.vmctcn.vmtu.mod.utils.RequiredMods;
import top.vmctcn.vmtu.multiversion.Texts;
import top.vmctcn.vmtu.multiversion.screen.ScreenUtils;
import top.vmctcn.vmtu.multiversion.screen.Widgets;
import top.vmctcn.vmtu.multiversion.screen.widgets.CheckboxWidget;
import top.vmctcn.vmtu.multiversion.screen.widgets.PlainTextButtonWidget;
import top.vmctcn.vmtu.multiversion.util.StringUtils;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class MissingModScreen extends Screen {
    public final Text title;
    @Nullable
    private final ArrayList<RequiredMods> missingMods;
    private RequiredMods mod;
    @Nullable
    private final Screen parent;
    private CheckboxWidget checkbox;

    protected static final int HEADER_HEIGHT = 40;
    protected static final int FOOTER_HEIGHT = 50;

    public MissingModScreen(@Nullable ArrayList<RequiredMods> missingMods) {
        this(missingMods, null);
    }

    public MissingModScreen(@Nullable ArrayList<RequiredMods> missingMods, @Nullable Screen parent) {
        this.title = Texts.translatable(ModContexts.getTranslationKey("screen", "missing_mod", "title"), RequiredMods.getAllMissing().size());
        this.missingMods = missingMods;
        this.parent = parent;
    }

    @Override
    public void init() {
        if (missingMods != null && !missingMods.isEmpty()) {
            int widest = missingMods.stream().map(mod -> mod.getName().length())
                    .max(Comparator.naturalOrder()).orElse(0);
            String brackets = "[" + StringUtils.repeat(" ", widest + 17) + "]";
            AtomicInteger index = new AtomicInteger(0);

            missingMods.forEach(mod -> {
                Text text = Texts.literal(mod.getName())
                        .setStyle(new Style().setColor(Formatting.RED))
                        .setStyle(new Style().setColor(mod.isRequired() ? Formatting.RED : Formatting.YELLOW));

                int y = 40 + (height - 90) * index.incrementAndGet() / ((int) missingMods.size() + 1);

                if (mod.isLoadedCheck()) {
                    if (mod.isRequired()) {
                        // Brackets
                        modDownloadButton(mod, Texts.literal(brackets).setStyle(new Style().setColor(Formatting.DARK_RED)), y);
                        // Names
                        modDownloadButton(mod, text, y);
                    } else {
                        // Brackets
                        modDownloadButton(mod, Texts.literal(brackets).setStyle(new Style().setColor(Formatting.YELLOW)), y);
                        // Names
                        modDownloadButton(mod, text, y);
                    }
                }
            });

            Text optionalModCheck = ModContexts.getTranslatableText("checkbox", "missing_mod", "optional");
            optionalModCheckBox(optionalModCheck);

            Text quit = ModContexts.getTranslatableText("button", "missing_mod", "quit");
            Text skip = ModContexts.getTranslatableText("button", "missing_mod", "skip");

            if (this.shouldPauseGame()) {
                skipAndQuitButton(quit, skip);
            } else {
                quitButton(quit);
            }
        } else {
            this.minecraft.openScreen(parent);
        }
    }

    @Override
    public boolean shouldPauseGame() {
        return this.parent != null;
    }

    public void onClose() {
        this.minecraft.openScreen(parent);
    }

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        this.renderBackground();
        super.render(mouseX, mouseY, tickDelta);

        Text title = Texts.literal(this.title.getString()).setStyle(new Style().setColor(Formatting.BOLD));
        Text subtitle = ModContexts.getTranslatableText("screen", "missing_mod", "subtitle");
        Text description = ModContexts.getTranslatableText("screen", "missing_mod", "description");

        ScreenUtils.drawCenteredTextWithShadow(textRenderer, title, this.width / 2, (HEADER_HEIGHT / 2) - (this.textRenderer.fontHeight / 2), -1);
        ScreenUtils.drawCenteredTextWithShadow(textRenderer, subtitle, this.width / 2, (HEADER_HEIGHT / 2) - (this.textRenderer.fontHeight / 2) + 15, -1);
        ScreenUtils.drawCenteredTextWithShadow(textRenderer, description, this.width / 2, (HEADER_HEIGHT / 2) - (this.textRenderer.fontHeight / 2) + 30, -1);
    }

    private void modDownloadButton(RequiredMods mod, Text text, int y) {
        Text tooltip = ModContexts.getTranslatableText("button", "missing_mod", mod.getName().toLowerCase(), "tooltip");
        PlainTextButtonWidget button = Widgets.createPlainTextButton(0, text, tooltip,
                this.width / 2 - this.textRenderer.getWidth(text.getContent()) / 2, y,
                this.textRenderer.getWidth(text.getContent()), 10,
                this.textRenderer
        );
        this.mod = mod;

        this.addButtonWidget(button);
    }

    private void skipAndQuitButton(Text quit, Text skip) {
        // Quit
        this.addButtonWidget(Widgets.createButton(1, quit, (this.width / 2) - 155, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20));

        // Skip
        this.addButtonWidget(Widgets.createButton(2, skip, (this.width / 2) + 5, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20));
    }

    private void quitButton(Text quit) {
        this.addButtonWidget(Widgets.createButton(3, quit, (this.width / 2) - 155, this.height - (FOOTER_HEIGHT / 2) - 10, 300, 20));
    }

    private void optionalModCheckBox(Text text) {
        Text tooltip = ModContexts.getTranslatableText("checkbox", "missing_mod", "optional", "tooltip");
        checkbox = Widgets.createCheckbox(4, text, tooltip, (this.width / 2) - 50, this.height - (FOOTER_HEIGHT / 2) - 35, (checkboxWidget, selected) -> {
            this.setOptionalModConfigOption(!selected);
        });
        checkbox.visible = ModConfigs.modInstallCheck.textureLocaleRedirector;
        ModConfigs.modInstallCheck.textureLocaleRedirector = !checkbox.isChecked();
        this.addButtonWidget(checkbox);
    }

    @Override
    protected void buttonClicked(ButtonWidget button) {
        switch (button.id) {
            case 0:
                mod.openUrl();
                break;
            case 1:
            case 3:
                try {
                    Desktop.getDesktop().open(ModPlatform.getGameDir().resolve("mods").toFile());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (this.minecraft != null) {
                    minecraft.stop();
                }
                break;
            case 2:
                this.onClose();
                break;
            case 4:
                if (checkbox.isChecked()) {
                    setOptionalModConfigOption(false);
                    checkbox.visible = false;
                }
                break;
        }
    }

    public <T extends ButtonWidget> T addButtonWidget(T widget) {
        return this.addButton(widget);
    }

    public void setOptionalModConfigOption(boolean value) {
        ModConfigs.modInstallCheck.textureLocaleRedirector = value;
    }
}
