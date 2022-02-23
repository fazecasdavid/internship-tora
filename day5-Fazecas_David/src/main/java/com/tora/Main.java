package com.tora;


import com.tora.calculator.Calculator;
import com.tora.ui.Console;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Console console = new Console(calculator);

        console.run();
    }
}
