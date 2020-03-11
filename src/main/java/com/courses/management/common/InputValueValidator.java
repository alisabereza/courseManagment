package com.courses.management.common;

import com.courses.management.course.CourseStatus;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class InputValueValidator {
    public static String value = "";

    public static String validateString(View view) {
        value = view.read();
        while (value.trim().isEmpty()) {
            view.write("Please enter the correct title");
            value = view.read();
        }
        return value;
    }

    public static String validateCourseStatus(View view) {
        value = view.read();
        boolean trueStatus = false;
        while (!trueStatus) {
            try {
                CourseStatus.valueOf(value);
                trueStatus = true;
            } catch (IllegalArgumentException e) {

                view.write("Please enter the correct value");
                value = view.read();
            }
        }
        return value;
    }

    public static int validateInt(View view) {
        value = view.read();
        while (!value.matches("[0-9]*")) {
            System.out.println("Please enter correct value (number)");
            value = view.read();
        }
        return Integer.valueOf(value);
    }

    public static LocalDate validateDate(View view) {
        LocalDate localDate = null;
        while (localDate==null) {
            try {
                localDate = LocalDate.parse(view.read());
            } catch (DateTimeParseException e) {
                System.out.println("Please enter correct value (date)");
                localDate = LocalDate.parse(view.read());
            }
        }
        return localDate;
    }
}
