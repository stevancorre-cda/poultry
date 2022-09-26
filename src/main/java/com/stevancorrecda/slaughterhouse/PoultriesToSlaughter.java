package com.stevancorrecda.slaughterhouse;

import java.util.List;

/**
 * Represents a collection of poultries that can be slaughtered, associated with the total price for them
 */
public record PoultriesToSlaughter(List<Poultry> poultries, double total) {
    /**
     * If there are any poultry that can be slaughtered, returns true. Otherwise false
     */
    public boolean isEmpty() {
        return poultries.isEmpty();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        for (final Poultry poultry : poultries) {
            builder
                    .append(" ")
                    .append(poultry.toString())
                    .append('\n');
        }

        builder.append(String.format("Total: %.2f\n", total));

        return builder.toString();
    }
}
