package top.vmctcn.vmtu.mod.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Formatting;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import org.jetbrains.annotations.Nullable;
import top.vmctcn.vmtu.mod.ModContexts;
import top.vmctcn.vmtu.mod.ModPlatform;
import top.vmctcn.vmtu.mod.config.ModConfigs;
import top.vmctcn.vmtu.mod.legacyforge.cache.ModInstanceCacheFile;
import top.vmctcn.vmtu.mod.utils.RequiredMods;
import top.vmctcn.vmtu.multiversion.Texts;
import top.vmctcn.vmtu.multiversion.screen.ScreenUtils;
import top.vmctcn.vmtu.multiversion.screen.Widgets;
import top.vmctcn.vmtu.multiversion.screen.widgets.CheckboxWidget;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MissingModScreen extends Screen {
    public final Text title;
    @Nullable
    private final ArrayList<RequiredMods> missingMods;
    @Nullable
    private final Screen parent;

    private final List<ButtonWidget> missingModButtons = new ArrayList<>();
    private final Map<Integer, RequiredMods> buttonIdToMod = new HashMap<>();
    private CheckboxWidget checkbox;

    protected static final int HEADER_HEIGHT = 40;
    protected static final int FOOTER_HEIGHT = 50;

    // Button IDs
    private static final int BUTTON_QUIT_WITH_SKIP = 114;
    private static final int BUTTON_SKIP = 514;
    private static final int BUTTON_QUIT = 251;
    private static final int BUTTON_CHECKBOX = 314;

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
            AtomicInteger index = new AtomicInteger(0);

            missingMods.forEach(mod -> {
                Text text = Texts.literal(mod.getName())
                        .setStyle(new Style().setColor(Formatting.RED))
                        .setStyle(new Style().setColor(mod.isRequired() ? Formatting.RED : Formatting.YELLOW));

                int y = 40 + (height - 90) * index.incrementAndGet() / (missingMods.size() + 1);

                if (mod.isLoadedCheck()) {
                    buttonIdToMod.put(index.get(), mod);
                    if (mod.isRequired()) {
                        modDownloadButton(index.get(), text, y);
                    } else {
                        modDownloadButton(index.get(), text, y);
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

        for (ButtonWidget button : missingModButtons) {
            if (isMouseOverButton(button, mouseX, mouseY)) {
                RequiredMods mod = buttonIdToMod.get(button.id);
                if (mod != null) {
                    Text tooltip = ModContexts.getTranslatableText("button", "missing_mod", mod.getName().toLowerCase(), "tooltip");
                    this.renderTooltip(tooltip.getFormattedString(), mouseX, mouseY);
                }
                break;
            }
        }

        if (checkbox != null && checkbox.visible && isMouseOverButton(checkbox, mouseX, mouseY)) {
            this.renderTooltip(checkbox.getTooltip(), mouseX, mouseY);
        }
    }

    private boolean isMouseOverButton(ButtonWidget button, int mouseX, int mouseY) {
        return mouseX >= button.x && mouseX < button.x + button.getWidth()
            && mouseY >= button.y && mouseY < button.y + button.height;
    }

    private void modDownloadButton(int buttonId, Text text, int y) {
        ButtonWidget button = Widgets.createButton(buttonId, text, (this.width / 2) - 75, y, 150, 20);
        this.missingModButtons.add(button);
        this.addButtonWidget(button);
    }

    private void skipAndQuitButton(Text quit, Text skip) {
        // Quit
        ButtonWidget quitButton = Widgets.createButton(BUTTON_QUIT_WITH_SKIP, quit, (this.width / 2) - 155, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20);
        this.addButtonWidget(quitButton);

        // Skip
        ButtonWidget skipButton = Widgets.createButton(BUTTON_SKIP, skip, (this.width / 2) + 5, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20);
        this.addButtonWidget(skipButton);
    }

    private void quitButton(Text quit) {
        ButtonWidget quitButton = Widgets.createButton(BUTTON_QUIT, quit, (this.width / 2) - 155, this.height - (FOOTER_HEIGHT / 2) - 10, 300, 20);
        this.addButtonWidget(quitButton);
    }

    private void optionalModCheckBox(Text text) {
        Text tooltip = ModContexts.getTranslatableText("checkbox", "missing_mod", "optional", "tooltip");
        this.checkbox = Widgets.createCheckbox(BUTTON_CHECKBOX, text, tooltip, (this.width / 2) - 50, this.height - (FOOTER_HEIGHT / 2) - 35);
        // Hide checkbox on startup if user previously confirmed (marker file exists)
        this.checkbox.visible = isOptionalModConfirmed();
        this.addButtonWidget(checkbox);
    }

    @Override
    protected void buttonClicked(ButtonWidget button) {
        // Handle mod download buttons (dynamic IDs from buttonIdToMod)
        RequiredMods mod = buttonIdToMod.get(button.id);
        if (mod != null) {
            openModDownloadUrl(mod, button.id);
            return;
        }

        // Handle fixed buttons
        switch (button.id) {
            case BUTTON_QUIT_WITH_SKIP:
            case BUTTON_QUIT:
                openModsFolderAndQuit();
                break;
            case BUTTON_SKIP:
                this.onClose();
                break;
            case BUTTON_CHECKBOX:
                if (checkbox.isChecked()) {
                    markOptionalModConfirmed();
                    setOptionalModCheckConfig();
                    // checkbox will be hidden on next startup based on marker file
                }
                break;
        }
    }

    private void openModDownloadUrl(RequiredMods mod, int buttonId) {
        URL url = mod.getUrl();
        if (url != null) {
            try {
                Desktop.getDesktop().browse(url.toURI());
            } catch (URISyntaxException | IOException e) {
                ModContexts.LOGGER.warn("Cannot handle URL for mod {}!", mod.getName(), e);
            }
        } else {
            ModContexts.LOGGER.info("No URL found for button id {}! ({})", buttonId, mod.getName());
        }
    }

    private void openModsFolderAndQuit() {
        try {
            Desktop.getDesktop().open(ModPlatform.getGameDir().resolve("mods").toFile());
        } catch (IOException e) {
            ModContexts.LOGGER.error("Failed to open mods folder!", e);
        }
        if (this.minecraft != null) {
            minecraft.stop();
        }
    }

    public <T extends ButtonWidget> T addButtonWidget(T widget) {
        return this.addButton(widget);
    }

    public void setOptionalModCheckConfig() {
        ModConfigs.modInstallCheck.textureLocaleRedirector = isOptionalModConfirmed();
        ConfigManager.sync(ModContexts.MOD_ID, Config.Type.INSTANCE);
    }

    private boolean isOptionalModConfirmed() {
        return ModInstanceCacheFile.getInstance().checkOptionalMod;
    }

    private void markOptionalModConfirmed() {
        ModInstanceCacheFile.getInstance().checkOptionalMod = false;
        ModInstanceCacheFile.save();
    }
}
