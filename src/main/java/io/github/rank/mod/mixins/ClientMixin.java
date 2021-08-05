package io.github.rank.mod.mixins;

import io.github.rank.mod.MainClient;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MinecraftClient.class)
public class ClientMixin {
    @Inject(at = @At(value = "HEAD"), method = "getWindowTitle", cancellable = true)
    public void onGetWindowTitle(CallbackInfoReturnable<String> info) {
        String title = MainClient.MOD_NAME + " " + MainClient.GAME_VERSION + "-" + MainClient.MOD_VERSION;
        info.setReturnValue(title);
    }
}
