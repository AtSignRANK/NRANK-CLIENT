package io.github.rank.mod.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public class MainScreen extends Screen {
    private final Screen parentScreen;

    public MainScreen(Screen parentScreen) {
        super(new TranslatableText("rankclient.modsettings"));
        this.parentScreen = parentScreen;
    }

    @Override
    protected void init() { super.init(); }

    @Override
    public boolean shouldCloseOnEsc() { return true; }

    @Override
    public void onClose() { assert this.client != null;this.client.setScreen(parentScreen); }

    @Override
    public void removed() { assert this.client != null; this.client.keyboard.setRepeatEvents(false); }



    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        renderBackground(matrices);

        assert this.client != null;
        drawCenteredText(matrices, this.client.textRenderer, title, width / 2, 17, 16777215);

        drawTextWithShadow(matrices, this.client.textRenderer, new TranslatableText("rankclient.modsettings.test"), width / 2 - 100, 64, 10526880);

        super.render(matrices, mouseX, mouseY, delta);
    }
}
