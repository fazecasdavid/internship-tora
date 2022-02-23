package com.tora;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;

import org.apache.commons.validator.routines.BigDecimalValidator;

public class Console {

    private final Scanner scanner;
    private final Calculator calculator;

    public Console(Calculator calculator) {
        this.scanner = new Scanner(System.in);
        this.calculator = calculator;
    }

    private void printMenu() {
        System.out.println("Options:");
        System.out.println("\t\t-\t+");
        System.out.println("\t\t-\t-");
        System.out.println("\t\t-\t/");
        System.out.println("\t\t-\t*");
        System.out.println("\t\t-\texit");
        System.out.println();
    }

    private Optional<BigDecimal> validateBigDecimal(String value) {
        return Optional.ofNullable(BigDecimalValidator.getInstance().validate(value));
    }

    private void handleAddition() {
        System.out.print("Type a number: ");
        String stringValue = scanner.nextLine();

        BigDecimal value = validateBigDecimal(stringValue).orElseThrow(() -> new IllegalArgumentException("The input must be a number"));

        calculator.add(value);
    }

    private void handleSubtraction() {
        System.out.print("Type a number: ");
        String stringValue = scanner.nextLine();

        BigDecimal value = validateBigDecimal(stringValue).orElseThrow(() -> new IllegalArgumentException("The input must be a number"));

        calculator.subtract(value);
    }

    private void handleDivision() {
        System.out.print("Type a number: ");
        String stringValue = scanner.nextLine();

        BigDecimal value = validateBigDecimal(stringValue).orElseThrow(() -> new IllegalArgumentException("The input must be a number"));

        calculator.divide(value);
    }

    private void handleMultiplication() {
        System.out.print("Type a number: ");
        String stringValue = scanner.nextLine();

        BigDecimal value = validateBigDecimal(stringValue).orElseThrow(() -> new IllegalArgumentException("The input must be a number"));

        calculator.multiply(value);
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\tResult: " + calculator.getResult());
            System.out.print("Input the option: ");

            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "exit":
                        running = false;
                        break;
                    case "+":
                        handleAddition();
                        break;
                    case "-":
                        handleSubtraction();
                        break;
                    case "*":
                        handleMultiplication();
                        break;
                    case "/":
                        handleDivision();
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        }
    }

}
