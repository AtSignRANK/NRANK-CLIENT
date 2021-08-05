package io.github.rank.mod.mixins;

import io.github.rank.mod.ZoomMod;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloader;

import org.objectweb.asm.Opcodes;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = GameRenderer.class)
public class ZoomMixin implements AutoCloseable, SynchronousResourceReloader {
    @Redirect(at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;fov:D",
            opcode = Opcodes.GETFIELD, ordinal = 0), method = {"getFov(Lnet/minecraft/client/render/Camera;FZ)D"})
    private double getZoom(GameOptions options) { return ZoomMod.INSTANCE.changeFovBasedOnZoom(options.fov); }

    @Shadow
    @Override
    public void reload(ResourceManager var1) { }

    @Shadow
    @Override
    public void close() throws Exception { }
}
