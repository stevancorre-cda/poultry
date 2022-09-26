package com.stevancorrecda.slaughterhouse;

import static com.stevancorrecda.Utils.formatWeight;

/**
 * Represents a poultry in a slaughterhouse
 */
public abstract class Poultry {
    private final int id;
    private double weight;

    public Poultry(final int id, final double weight) {
        this.id = id;
        this.weight = weight;
    }

    /**
     * Get the poultry's id
     */
    public int id() {
        return id;
    }

    /**
     * Get the poultry's weight in kg
     */
    public double weight() {
        return weight;
    }

    /**
     * Set the poultry's weight in kg
     */
    public void weight(final double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [id=" + id() + ", " + formatWeight(weight()) + "]";
    }
}
