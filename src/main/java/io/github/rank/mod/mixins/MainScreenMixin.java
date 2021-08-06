package io.github.rank.mod.mixins;

import io.github.rank.mod.screens.ModSettingsScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value = EnvType.CLIENT)
@Mixin(value = TitleScreen.class)
public class MainScreenMixin extends Screen {
    protected MainScreenMixin(Text text) { super(text); }

    @Inject(at = @At("RETURN"), method = "initWidgetsNormal")
    private void addServerButton(int y, int spacingY, CallbackInfo ci) {
        TexturedButtonWidget buttonWidget =
                new TexturedButtonWidget(this.width / 2 - 130, this.height / 2, 20, 20, 0, 0,
                        20, new Identifier("rankclient", "textures/mod_settings_widget.png"),
                        20, 40, (button) -> MinecraftClient.getInstance().setScreen(new ModSettingsScreen(this)),
                        new LiteralText("RANK CLIENT MOD SETTINGS"));
        this.addDrawableChild(buttonWidget);
    }
}
