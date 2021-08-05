package io.github.rank.mod.mixins;

import io.github.rank.mod.PerspectiveMod;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = Mouse.class)
public class PerspectiveMouseMixin {
    @Inject(method = "updateMouse", at = @At(value = "INVOKE", target = "net/minecraft/client/tutorial/TutorialManager.onUpdateMouse(DD)V"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void perspectiveUpdatePitchYaw(CallbackInfo info, double adjustedSens, double x, double y, int invert) {
        if (PerspectiveMod.INSTANCE.pressing) {
            PerspectiveMod.INSTANCE.cameraYaw += x / 8.0F;
            PerspectiveMod.INSTANCE.cameraPitch += (y * invert) / 8.0F;

            if (Math.abs(PerspectiveMod.INSTANCE.cameraPitch) > 90.0F) PerspectiveMod.INSTANCE.cameraPitch = PerspectiveMod.INSTANCE.cameraPitch > 0.0F ? 90.0F : -90.0F;
        }
    }

    @Inject(method = "updateMouse", at = @At(value = "INVOKE", target = "net/minecraft/client/network/ClientPlayerEntity.changeLookDirection(DD)V"), cancellable = true)
    private void perspectivePreventPlayerMovement(CallbackInfo info) {
        if (PerspectiveMod.INSTANCE.pressing) info.cancel();
    }
}
