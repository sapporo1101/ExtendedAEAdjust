package io.github.sapporo1101.extendedaeadjust.neoforge.mixin;

import com.glodblock.github.glodium.registry.RegistryHandler;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RegistryHandler.class)
public class RegistryHandlerMixin {
    @Inject(method = "block(Ljava/lang/String;Lnet/minecraft/world/level/block/Block;)V", at = @At("HEAD"), cancellable = true)
    private void block(String name, Block block, CallbackInfo ci) {
        if (name.equals("mddyue") || name.equals("fishbig")) ci.cancel();
    }
}
