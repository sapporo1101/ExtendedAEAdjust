package io.github.sapporo1101.extendedaeadjust.neoforge.mixin;

import com.glodblock.github.extendedae.common.blocks.BlockMascot;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Block.class)
public class BlockMixin {
    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/DefaultedRegistry;createIntrusiveHolder(Ljava/lang/Object;)Lnet/minecraft/core/Holder$Reference;"))
    private Holder.Reference onInit(DefaultedRegistry instance, Object o, Operation<Holder.Reference> original) {
        if (o instanceof BlockMascot) {
            return null;
        }
        return original.call(instance, o);
    }
}
