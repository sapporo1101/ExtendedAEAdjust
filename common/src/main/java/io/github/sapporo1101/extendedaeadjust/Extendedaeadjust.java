package io.github.sapporo1101.extendedaeadjust;

import com.google.gson.JsonParser;
import io.github.sapporo1101.extendedaeadjust.resource.ResourceModifier;

public final class Extendedaeadjust {
    public static final String MOD_ID = "extendedaeadjust";

    public static void init() {
        // Write common init code here.
    }

    public static void registerModifiers() {
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
    }
}
