package com.tora.calculator.history;

import java.util.ArrayDeque;
import java.util.Deque;

public class CalculatorHistoryCaretaker {
    private final Deque<CalculatorStateMemento> history = new ArrayDeque<>();

    public void addMemento(CalculatorStateMemento memento) {
        history.addLast(memento);
    }

    public CalculatorStateMemento getMemento() {
        if (history.isEmpty()) {
            throw new RuntimeException("The history stack is empty");
        }
        return history.pollLast();
    }
}
