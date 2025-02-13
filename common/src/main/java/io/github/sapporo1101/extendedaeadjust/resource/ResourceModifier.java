package io.github.sapporo1101.extendedaeadjust.resource;

import io.github.sapporo1101.extendedaeadjust.Extendedaeadjust;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class ResourceModifier {
    private static boolean initialized = false;
    private static final HashMap<String, QuickResourceModifier> quickResourceModifiers = new HashMap<>();
    private static final HashMap<String, PathResourceModifier> startsWithResourceModifiers = new HashMap<>();

    public static void init() {
        if (initialized) return;
        initialized = true;
        Extendedaeadjust.registerModifiers();
    }

    public static byte @Nullable [] modifyResource(Path path) throws IOException {
        String pathString = path.toString().replaceFirst("^/", "");
        var modifier = quickResourceModifiers.get(pathString);
        if (modifier != null) {
            byte[] data;
            try (var inputStream = Files.newInputStream(path)) {
                data = inputStream.readAllBytes();
            }

            return modifier.modify(data);
        }

        for (var entry : startsWithResourceModifiers.entrySet()) {
            if (pathString.startsWith(entry.getKey())) {
                byte[] data;
                try (var inputStream = Files.newInputStream(path)) {
                    data = inputStream.readAllBytes();
                }

                return entry.getValue().modify(pathString, data);
            }
        }

        return null;
    }

    public static void registerQuickModifier(String path, QuickResourceModifier modifier) {
        quickResourceModifiers.put(path, modifier);
    }

    public static void registerStartsWithModifier(String path, PathResourceModifier modifier) {
        startsWithResourceModifiers.put(path, modifier);
    }
}