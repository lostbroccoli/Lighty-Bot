package net.neoooo.modules.ticketsystem.utils;

import java.util.List;
import java.util.function.Predicate;

public interface IFormatHelper {

    List<String> formats();

    default boolean isFormat(String format) {
        return formats().stream().anyMatch(Predicate.isEqual(format));
    }
}
