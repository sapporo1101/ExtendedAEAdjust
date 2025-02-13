package io.github.sapporo1101.extendedaeadjust.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.sapporo1101.extendedaeadjust.Extendedaeadjust;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.FallbackResourceManager;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.Resource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

@Mixin(FallbackResourceManager.class)
public abstract class FallbackResourceManagerMixin {
    @Shadow
    static ResourceLocation getMetadataLocation(ResourceLocation resourceLocation) {
        throw new AssertionError();
    }

    @Inject(method = "listResources", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Maps;newTreeMap()Ljava/util/TreeMap;"))
    private void listResources(String string, Predicate<ResourceLocation> predicate, CallbackInfoReturnable<Map<ResourceLocation, Resource>> cir, @Local(ordinal = 0) Map map, @Local(ordinal = 1) Map map2) throws ReflectiveOperationException {
        for (var resourceLocation : Extendedaeadjust.MCMETAS) {
            if (map.containsKey(resourceLocation)) {
                var resource = map.get(resourceLocation);
                var resourceWithSourceAndIndexClass = resource.getClass();

                var packResourcesMethod = resourceWithSourceAndIndexClass.getDeclaredMethod("packResources");
                packResourcesMethod.setAccessible(true);
                var packResources = packResourcesMethod.invoke(resource);

                IoSupplier<InputStream> ioSupplier = () -> Objects.requireNonNull(Extendedaeadjust.class.getClassLoader().getResourceAsStream("assets/extendedaeadjust/" + resourceLocation.getPath() + ".mcmeta"));

                var packIndexMethod = resourceWithSourceAndIndexClass.getDeclaredMethod("packIndex");
                packIndexMethod.setAccessible(true);
                var packIndex = packIndexMethod.invoke(resource);

                var ctor = resourceWithSourceAndIndexClass.getDeclaredConstructors()[0];
                ctor.setAccessible(true);

                var metadata = ctor.newInstance(packResources, ioSupplier, packIndex);
                map2.put(getMetadataLocation(resourceLocation), metadata);
            }
        }
    }
}
