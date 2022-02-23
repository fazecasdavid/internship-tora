package com.insightfullogic.java8.exercises.chapter2;


import javax.swing.text.DateFormatter;

import java.text.SimpleDateFormat;

import static java.lang.ThreadLocal.withInitial;

public class Question2 {

    public static ThreadLocal<DateFormatter> formatter = withInitial(() -> new DateFormatter(new SimpleDateFormat("dd-MMM-yyyy")));

}
