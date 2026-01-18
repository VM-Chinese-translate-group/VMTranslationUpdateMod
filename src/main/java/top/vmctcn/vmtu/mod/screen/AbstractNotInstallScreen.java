package top.vmctcn.vmtu.mod.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import top.vmctcn.vmtu.multiversion.Texts;
import top.vmctcn.vmtu.multiversion.screen.ScreenUtils;
import top.vmctcn.vmtu.multiversion.screen.WidgetUtils;

public abstract class AbstractNotInstallScreen extends Screen {
    public final Text title;
    public static final Text downloadButtonText = Texts.translatable("vmtu.required_mod.download.button");
    public static final Text ignoreButtonText = Texts.translatable("vmtu.required_mod.ignore.button");
    public final Screen lastScreen;

    private static final int HEADER_HEIGHT = 40;
    private static final int FOOTER_HEIGHT = 50;

    protected final String modName;

    public AbstractNotInstallScreen(Screen lastScreen, String modName) {
        this.title = Texts.translatable("vmtu.required_mod.not_install.title", modName);
        this.modName = modName;
        this.lastScreen = lastScreen;
    }

    @Override
    public void init() {
        int centerX = this.width / 2;

        super.init();
        this.buttons.clear();
        this.addButtonWidget(WidgetUtils.createButton(0, downloadButtonText,centerX - 5 - 150, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20));
        this.addButtonWidget(WidgetUtils.createButton(1, ignoreButtonText, centerX + 5, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20));
    }

    public abstract String getDownloadUrl();

    public abstract Text getScreenDescription();

    public <T extends ButtonWidget> T addButtonWidget(T widget) {
        return this.addButton(widget);
    }

    @Override
    protected void buttonClicked(ButtonWidget button) {
        if (button.id == 0) {
            ScreenUtils.openUrlOnScreen(this.minecraft, this, getDownloadUrl());
        } else if (button.id == 1) {
            this.onClose();
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        this.renderBackground();

        super.render(mouseX, mouseY, tickDelta);

        ScreenUtils.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, (HEADER_HEIGHT / 2) - (this.textRenderer.fontHeight / 2), -1);
        ScreenUtils.drawCenteredTextWithShadow(this.textRenderer, this.getScreenDescription(), this.width / 2, 70, -1);
        ScreenUtils.drawCenteredTextWithShadow(this.textRenderer, Texts.translatable("vmtu.required_mod.not_install.desc.download"), this.width / 2, 80, -1);
    }

    public void onClose() {
        this.minecraft.openScreen(this.lastScreen);
    }
}
