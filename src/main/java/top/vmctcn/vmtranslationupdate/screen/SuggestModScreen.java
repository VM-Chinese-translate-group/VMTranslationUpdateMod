package top.vmctcn.vmtranslationupdate.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import top.vmctcn.vmtranslationupdate.util.SuggestScreenUtil;

public class SuggestModScreen extends Screen {
    public final Screen lastScreen;
    private int ticksUntilEnable = 20 * 10;

    private static final int HEADER_HEIGHT = 40;
    private static final int FOOTER_HEIGHT = 50;
    private static final int BORDER = 40;

    public SuggestModScreen(Screen lastScreen) {
        this.lastScreen = lastScreen;
    }

    @Override
    public void init() {
        int centerX = this.width / 2;

        super.init();
        this.buttons.clear();
        this.addButton(new ButtonWidget(0, centerX - 5 - 150, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20, SuggestScreenUtil.downloadButtonText.getContent()));
        this.addButton(new ButtonWidget(1, centerX + 5, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20, SuggestScreenUtil.quitButtonText.getContent()));

    }

    @Override
    protected void buttonClicked(ButtonWidget button) {
        if (button.id == 0) {
            SuggestScreenUtil.getDownloadUrl(minecraft, this);
        } else if (button.id == 2) {
            minecraft.scheduleStop();
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        this.renderBackground();
        super.render(mouseX, mouseY, tickDelta);

        this.drawCenteredString(this.textRenderer, SuggestScreenUtil.getSuggestScreenTitle().getContent(), this.width / 2, (HEADER_HEIGHT / 2) - (this.textRenderer.fontHeight / 2), -1);
        this.drawCenteredString(this.textRenderer, SuggestScreenUtil.getSuggestScreenText().getContent(), this.width / 2, 70, -1);
    }

    @Override
    public void removed() {
        Minecraft.getInstance().openScreen(this.lastScreen);
    }
}
