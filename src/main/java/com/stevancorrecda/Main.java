package com.stevancorrecda;

import com.stevancorrecda.slaughterhouse.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static com.stevancorrecda.Utils.formatPricePerKilo;
import static com.stevancorrecda.Utils.formatWeight;

public final class Main {
    private static final Slaughterhouse Slaughterhouse = new Slaughterhouse();

    public static void main(String[] args) {
        Action action;
        do {
            action = handleInterface();

            switch (action) {
                case SetPricesOfTheDay -> handleSetPricesOfTheDay();
                case SetSlaughterWeights -> handleSetSlaughterWeights();
                case RegisterPoultry -> handleRegisterPoultry();
                case UpdatePoultry -> handleUpdatePoultry();
                case ListAll -> handleListAll();
            }
        }
        while (action != Action.Exit);
    }

    private static void clear() {
        System.out.println("\n".repeat(50));
        System.out.println("===== Slaughterhouse =====");
    }

    private static void waitForKeyPress() {
        System.out.println("Press ENTER to continue...");
        new Scanner(System.in).nextLine();
    }

    private static Action handleInterface() {
        try {
            clear();
            System.out.println(" [1]: Set prices of the day");
            System.out.println(" [2]: Set slaughter weights");
            System.out.println(" [3]: Register poultry");
            System.out.println(" [4]: Update poultry");
            System.out.println(" [5]: List all");
            System.out.println(" [6]: Exit");

            final int action = new Scanner(System.in).nextInt();
            if (action < 1 || action > 6) throw new IllegalArgumentException();
            return Action.values()[action];
        } catch (Exception e) {
            System.out.println("Invalid action! Please enter a number from 1 to 5");

            waitForKeyPress();
            return Action.None;
        }
    }

    private static void handleSetPricesOfTheDay() {
        final Scanner scanner = new Scanner(System.in);
        boolean success = false;
        do {
            try {
                clear();
                System.out.println("Current prices");
                System.out.println("Duck: " + formatPricePerKilo(Slaughterhouse.duckPrice()));
                System.out.println("Chicken: " + formatPricePerKilo(Slaughterhouse.chickenPrice()));

                System.out.print("Enter the price per kilo of duck: ");
                final double duckPrice = scanner.nextDouble();

                System.out.print("Enter the price per kilo of chicken: ");
                final double chickenPrice = scanner.nextDouble();

                if (duckPrice <= 0 || chickenPrice <= 0)
                    throw new IllegalArgumentException();

                Slaughterhouse.duckPrice(duckPrice);
                Slaughterhouse.chickenPrice(chickenPrice);
                success = true;

                System.out.println("Prices updated");
            } catch (Exception e) {
                System.out.println("Invalid prices");
                e.printStackTrace();
            } finally {
                waitForKeyPress();
            }
        }
        while (!success);
    }

    private static void handleSetSlaughterWeights() {
        final Scanner scanner = new Scanner(System.in);
        boolean success = false;
        do {
            try {
                clear();
                System.out.println("Current slaughter weights");
                System.out.println("Duck: " + formatWeight(Slaughterhouse.duckSlaughterWeight()));
                System.out.println("Chicken: " + formatWeight(Slaughterhouse.chickenSlaughterWeight()));

                System.out.print("Enter the ducks' slaughter weight: ");
                final double duckSlaughterWeight = scanner.nextDouble();

                System.out.print("Enter the chicken' slaughter weight: ");
                final double chickenSlaughterWeight = scanner.nextDouble();

                if (duckSlaughterWeight <= 0 || chickenSlaughterWeight <= 0)
                    throw new IllegalArgumentException();

                Slaughterhouse.duckSlaughterWeight(duckSlaughterWeight);
                Slaughterhouse.chickenSlaughterWeight(chickenSlaughterWeight);
                success = true;

                System.out.println("Slaughter weights updated");
            } catch (Exception e) {
                System.out.println("Invalid weights");
                e.printStackTrace();
            } finally {
                waitForKeyPress();
            }
        }
        while (!success);
    }

    private static void handleRegisterPoultry() {
        final Scanner scanner = new Scanner(System.in);
        boolean success = false;
        do {
            try {
                clear();

                System.out.print("Enter the poultry type [1=duck, 2=chicken]: ");
                final int poultryType = scanner.nextInt();

                System.out.print("Enter its ID: ");
                final int id = scanner.nextInt();

                System.out.print("Enter its weight: ");
                final double weight = scanner.nextDouble();

                if (weight <= 0 || poultryType < 1 || poultryType > 2)
                    throw new IllegalArgumentException();

                final Poultry poultry = poultryType == 1 ?
                        new Duck(id, weight) :
                        new Chicken(id, weight);

                Slaughterhouse.add(poultry);
                success = true;

                System.out.println("Poultry registered");
            }
            catch (KeyAlreadyExistsException e) {
                System.out.println("A poultry with this id already exists!");
            }
            catch (Exception e) {
                System.out.println("Invalid parameters");
            } finally {
                waitForKeyPress();
            }
        }
        while (!success);
    }

    private static void handleUpdatePoultry() {
        if(Slaughterhouse.isEmpty()) {
            clear();

            System.out.println("No poultries registered yet");
        }
        else
        {
            final Scanner scanner = new Scanner(System.in);
            boolean success = false;
            do {
                try {
                    clear();

                    for (final Poultry poultry: Slaughterhouse.poultries()) {
                        System.out.println(" " + poultry.toString());
                    }

                    System.out.print("Enter the poultry id: ");
                    final int id = scanner.nextInt();

                    final Poultry poultry = Slaughterhouse.findById(id);
                    if(poultry == null)
                        throw new NoSuchElementException();

                    System.out.println("Poultry selected");

                    handleUpdatePoultry(poultry);
                    success = true;
                }
                catch (NoSuchElementException e) {
                    System.out.println("No poultry with this id exists!");
                }
                catch (Exception e) {
                    System.out.println("Invalid id format");
                } finally {
                    waitForKeyPress();
                }
            }
            while (!success);
        }
    }

    private static void handleUpdatePoultry(final Poultry poultry) {
        final Scanner scanner = new Scanner(System.in);
        boolean success = false;
        do {
            try {
                clear();

                System.out.println("Preview weight: " + formatWeight(poultry.weight()));
                System.out.print("Enter its new weight: ");
                final double weight = scanner.nextDouble();

                if (weight <= 0)
                    throw new IllegalArgumentException();

                poultry.weight(weight);
                success = true;

                System.out.println("Poultry updated!");
            }
            catch (Exception e) {
                System.out.println("Invalid weight");
            }
        }
        while (!success);
    }

    private static void handleListAll() {
        clear();

        if (Slaughterhouse.duckPrice() == null || Slaughterhouse.chickenPrice() == null) {
            System.out.println("Prices of the day aren't set! Please set them then come back here");
            waitForKeyPress();
            return;
        }

        if (Slaughterhouse.duckSlaughterWeight() == null || Slaughterhouse.chickenSlaughterWeight() == null) {
            System.out.println("Slaughter weights aren't set! Please set them then come back here");
            waitForKeyPress();
            return;
        }

        System.out.println("Prices of the day:");
        System.out.println(" Duck: " + formatPricePerKilo(Slaughterhouse.duckPrice()));
        System.out.println(" Chicken: " + formatPricePerKilo(Slaughterhouse.chickenPrice()));

        System.out.println("Slaughter weights:");
        System.out.println(" Duck: " + formatWeight(Slaughterhouse.duckSlaughterWeight()));
        System.out.println(" Chicken: " + formatWeight(Slaughterhouse.chickenSlaughterWeight()));

        System.out.println();

        System.out.println("Poultries:");
        final PoultriesToSlaughter poultries = Slaughterhouse.getPoultriesToSlaughter();
        if (poultries.isEmpty())
            System.out.println("No poultries registered yet!");
        else
            System.out.println(poultries);

        waitForKeyPress();
    }
}
