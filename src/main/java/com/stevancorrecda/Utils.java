package com.stevancorrecda;

/**
 * Utils class
 */
public final class Utils {
    /**
     * Format double to #.##€/kg if not null. Otherwise UNSET
     */
    public static String formatPricePerKilo(final Double price) {
        if (price == null) return "UNSET";
        return String.format("%.2f €/kg", price);
    }

    /**
     * Format double to #.## kg if not null. Otherwise UNSET
     */
    public static String formatWeight(final Double weight) {
        if (weight == null) return "UNSET";
        return String.format("%.2f kg", weight);
    }
}
