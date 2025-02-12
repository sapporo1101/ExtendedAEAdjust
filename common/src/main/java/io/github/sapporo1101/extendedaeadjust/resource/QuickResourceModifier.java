package io.github.sapporo1101.extendedaeadjust.resource;

import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface QuickResourceModifier {
    byte @Nullable [] modify(byte[] data);
}