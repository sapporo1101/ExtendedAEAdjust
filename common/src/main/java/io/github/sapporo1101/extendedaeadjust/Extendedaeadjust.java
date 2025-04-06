package io.github.sapporo1101.extendedaeadjust;

import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import io.github.sapporo1101.extendedaeadjust.resource.ResourceModifier;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public final class Extendedaeadjust {
    public static final String MOD_ID = "extendedaeadjust";

    public static void init() {
        // Write common init code here.
    }

    public static void registerModifiers() {
        ResourceModifier.registerQuickModifier("data/minecraft/tags/block/mineable/pickaxe.json", data -> {
            var json = JsonParser.parseString(new String(data)).getAsJsonObject();
            var values = json.getAsJsonArray("values");
            values.remove(new JsonPrimitive("extendedae:fishbig"));
            values.remove(new JsonPrimitive("extendedae:mddyue"));
            return json.toString().getBytes();
        });

        ResourceModifier.registerQuickModifier("data/extendedae/recipe/cobblestone_cell.json", data -> {
            var json = JsonParser.parseString(new String(data)).getAsJsonObject();
            json.getAsJsonObject("key").getAsJsonObject("X").addProperty("item", "ae2:cell_component_256k");
            return json.toString().getBytes();
        });

        ResourceModifier.registerQuickModifier("data/extendedae/recipe/water_cell.json", data -> {
            var json = JsonParser.parseString(new String(data)).getAsJsonObject();
            json.getAsJsonObject("key").getAsJsonObject("X").addProperty("item", "ae2:cell_component_256k");
            return json.toString().getBytes();
        });

        for (var modid : List.of("extendedae", "ae2", "ae2netanalyser", "megacells")) {
            ResourceModifier.registerStartsWithModifier("assets/" + modid + "/textures/", (path, data) -> {
                try (var modifiedData = Extendedaeadjust.class.getClassLoader().getResourceAsStream(path.replaceFirst("^assets/" + modid + "/", "assets/extendedaeadjust/" + modid + "/"))) {
                    if (modifiedData == null) return null;
                    return modifiedData.readAllBytes();
                }
            });
        }
    }

    public static final List<ResourceLocation> MCMETAS = List.of(
            ResourceLocation.fromNamespaceAndPath("extendedae", "textures/block/assembler_matrix/frame_block_on.png"),
            ResourceLocation.fromNamespaceAndPath("extendedae", "textures/block/assembler_matrix/frame_column_on.png"),
            ResourceLocation.fromNamespaceAndPath("extendedae", "textures/block/assembler_matrix/crafter_core.png"),
            ResourceLocation.fromNamespaceAndPath("extendedae", "textures/block/assembler_matrix/pattern_core.png"),
            ResourceLocation.fromNamespaceAndPath("extendedae", "textures/block/assembler_matrix/speed_core.png")
    );
}
