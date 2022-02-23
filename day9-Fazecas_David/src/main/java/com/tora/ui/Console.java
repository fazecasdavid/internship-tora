package com.tora.ui;

import com.tora.calculator.BinaryOperations;
import com.tora.calculator.Calculator;
import com.tora.calculator.UnaryOperations;
import com.tora.calculator.history.CalculatorHistoryCaretaker;
import org.apache.commons.validator.routines.BigDecimalValidator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import static java.util.Optional.ofNullable;

public class Console {
    private final Scanner scanner;
    private final Calculator calculator;
    private final CalculatorHistoryCaretaker calculatorHistoryCaretaker = new CalculatorHistoryCaretaker();

    private Map<String, Runnable> commandsMap;

    public Console(Calculator calculator) {
        this.scanner = new Scanner(System.in);
        this.calculator = calculator;
        initCommandsMap();
    }

    private void initCommandsMap() {
        commandsMap = new HashMap<>();
        BinaryOperations.stream()
            .forEach(op -> commandsMap.put(op.getOperator(), () -> handleBinaryOperation(op.getBinaryOperator())));

        UnaryOperations.stream()
            .forEach(op -> commandsMap.put(op.getOperator(), () -> handleUnaryOperation(op.getUnaryOperator())));

        commandsMap.put("undo", this::undoOperation);
    }

    private void printMenu() {
        System.out.println("Options:");
        System.out.println(commandsMap.keySet());
        System.out.println("\t\t-\texit");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\tResult: " + calculator.getResult());
    }

    private Optional<BigDecimal> validateBigDecimal(String value) {
        return ofNullable(BigDecimalValidator.getInstance().validate(value));
    }

    private void undoOperation() {
        calculator.restoreFromMemento(calculatorHistoryCaretaker.getMemento());
    }

    private void handleBinaryOperation(BinaryOperator<BigDecimal> binaryOperator) {
        System.out.print("Type a number: ");
        String stringValue = scanner.nextLine();

        BigDecimal value = validateBigDecimal(stringValue)
            .orElseThrow(() -> new IllegalArgumentException("The input must be a number"));

        calculatorHistoryCaretaker.addMemento(calculator.createMemento());
        calculator.applyBinaryOperator(value, binaryOperator);
    }

    private void handleUnaryOperation(UnaryOperator<BigDecimal> unaryOperator) {
        calculatorHistoryCaretaker.addMemento(calculator.createMemento());
        calculator.applyUnaryOperator(unaryOperator);
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Input the option: ");

            String option = scanner.nextLine();

            if (option.equals("exit")) {
                running = false;
            } else if (commandsMap.containsKey(option)) {
                try {
                    commandsMap.get(option).run();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                System.out.println("Invalid option!");
            }
        }
    }

}
