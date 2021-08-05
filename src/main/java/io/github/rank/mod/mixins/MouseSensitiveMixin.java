package io.github.rank.mod.mixins;

import io.github.rank.mod.MouseSensitiveMod;
import net.minecraft.client.Mouse;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Mouse.class)
public class MouseSensitiveMixin {
    @Inject(at = {@At("RETURN")}, method = {"onMouseScroll(JDD)V"})
    private void onOnMouseScroll(long long_1, double double_1, double double_2, CallbackInfo ci) {
        if (MouseSensitiveMod.INSTANCE.getSensitiveKey().isPressed())
        { MouseSensitiveMod.INSTANCE.scrollMouseSensitive(double_2); }
    }
}
