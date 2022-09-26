package com.stevancorrecda.slaughterhouse;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Holds collection of poultries and gives you functions to interact with
 */
public final class Slaughterhouse {
    private final ArrayList<Poultry> poultries;
    private Double duckPrice = null;
    private Double chickenPrice = null;
    private Double duckSlaughterWeight = null;
    private Double chickenSlaughterWeight = null;

    public Slaughterhouse() {
        this.poultries = new ArrayList<>();
    }

    /**
     * Checks the slaughterhouse if empty
     */
    public boolean isEmpty() {
        return poultries.isEmpty();
    }

    /**
     * Add a new poultry
     */
    public void add(final Poultry newPoultry) {
        if (findById(newPoultry.id()) != null)
            throw new KeyAlreadyExistsException();

        this.poultries.add(newPoultry);
    }

    /**
     * Get the price €/kg for any poultry
     */
    private double getPoultryPrice(final Poultry poultry) {
        if (poultry instanceof Chicken) return chickenPrice;
        if (poultry instanceof Duck) return duckPrice;

        throw new IllegalArgumentException("Unknown poultry");
    }

    /**
     * Checks if a poultry can be slaughtered
     */
    private boolean canBeSlaughtered(final Poultry poultry) {
        if (poultry instanceof Chicken) return poultry.weight() >= chickenSlaughterWeight;
        if (poultry instanceof Duck) return poultry.weight() >= duckSlaughterWeight;

        throw new IllegalArgumentException("Unknown poultry");
    }

    /**
     * Get a collection of poultries that can be slaughtered
     */
    public PoultriesToSlaughter getPoultriesToSlaughter() {
        final List<Poultry> poultries = this.poultries
                .stream()
                .sorted(Comparator.comparingDouble(Poultry::weight))
                .filter(this::canBeSlaughtered)
                .toList();
        final double total = poultries.stream().mapToDouble(this::getPoultryPrice).sum();

        return new PoultriesToSlaughter(poultries, total);
    }

    /**
     * Get a poultry by its id
     */
    public Poultry findById(final int id) {
        return poultries
                .stream()
                .filter(x -> x.id() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Get a clone of the poultries in the slaughterhouse
     */
    public List<Poultry> poultries() {
        return new ArrayList<>(poultries);
    }

    /**
     * Get duck price in €/kg
     */
    public Double duckPrice() {
        return duckPrice;
    }

    /**
     * Get chicken price in €/kg
     */
    public Double chickenPrice() {
        return chickenPrice;
    }

    /**
     * Duck minimum weight to be slaughtered
     */
    public Double duckSlaughterWeight() {
        return duckSlaughterWeight;
    }

    /**
     * Chicken minimum weight to be slaughtered
     */
    public Double chickenSlaughterWeight() {
        return chickenSlaughterWeight;
    }

    /**
     * Set duck price in €/kg
     */
    public void duckPrice(final double price) {
        duckPrice = price;
    }

    /**
     * Set chicken price in €/kg
     */
    public void chickenPrice(final double price) {
        chickenPrice = price;
    }

    /**
     * Set duck slaughter weight in kg
     */
    public void duckSlaughterWeight(final double weight) {
        duckSlaughterWeight = weight;
    }

    /**
     * Set chicken slaughter weight in kg
     */
    public void chickenSlaughterWeight(final double weight) {
        chickenSlaughterWeight = weight;
    }
}
