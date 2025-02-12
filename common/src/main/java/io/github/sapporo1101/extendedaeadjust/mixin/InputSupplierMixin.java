package io.github.sapporo1101.extendedaeadjust.mixin;

import io.github.sapporo1101.extendedaeadjust.resource.ResourceModifier;
import net.minecraft.server.packs.resources.IoSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@Mixin(IoSupplier.class)
public interface InputSupplierMixin {
    @Inject(method = "create(Ljava/nio/file/Path;)Lnet/minecraft/server/packs/resources/IoSupplier;", at = @At("HEAD"), cancellable = true)
    private static void create(Path path, CallbackInfoReturnable<IoSupplier<InputStream>> cir) throws IOException {
        ResourceModifier.init();

        var modifiedData = ResourceModifier.modifyResource(path);
        if (modifiedData != null) {
            cir.setReturnValue(() -> new ByteArrayInputStream(modifiedData));
        }
    }
}