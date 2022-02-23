package com.tora;


public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Console console = new Console(calculator);

        console.run();
    }
}
