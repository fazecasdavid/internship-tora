package com.tora.ui;

import com.tora.calculator.Calculator;
import com.tora.calculator.history.CalculatorHistoryCaretaker;
import org.apache.commons.validator.routines.BigDecimalValidator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

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
//        s-ar putea extrage maparile de aici intr-un enum
        commandsMap = new HashMap<>();
        commandsMap.put("+", () -> handleBinaryOperation(calculator::add));
        commandsMap.put("-", () -> handleBinaryOperation(calculator::subtract));
        commandsMap.put("*", () -> handleBinaryOperation(calculator::multiply));
        commandsMap.put("/", () -> handleBinaryOperation(calculator::divide));
        commandsMap.put("min", () -> handleBinaryOperation(calculator::min));
        commandsMap.put("max", () -> handleBinaryOperation(calculator::max));
        commandsMap.put("pow", () -> handleBinaryOperation(calculator::powerOf));
        commandsMap.put("round", () -> handleUnaryOperation(calculator::round));
        commandsMap.put("sqrt", () -> handleUnaryOperation(calculator::sqrt));
        commandsMap.put("undo", this::undoOperation);
    }

    private void printMenu() {
        System.out.println("Options:");
//        commandsMap.keySet().forEach((key) -> System.out.println("\t\t" + key + "\t"));
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

    private void handleBinaryOperation(Consumer<BigDecimal> calculatorOperationConsumer) {
        System.out.print("Type a number: ");
        String stringValue = scanner.nextLine();

        BigDecimal value = validateBigDecimal(stringValue)
            .orElseThrow(() -> new IllegalArgumentException("The input must be a number"));

        calculatorHistoryCaretaker.addMemento(calculator.createMemento());
        calculatorOperationConsumer.accept(value);
    }

    private void handleUnaryOperation(Runnable calculatorOperationRunnable) {
        calculatorHistoryCaretaker.addMemento(calculator.createMemento());
        calculatorOperationRunnable.run();
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
