package com.courses.management.common;

import com.courses.management.course.Course;
import com.courses.management.course.CourseDAOImpl;
import com.courses.management.course.CourseStatus;
import com.courses.management.user.UserDAOImpl;

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
        return Integer.parseInt(value);
    }

    public static LocalDate validateDate(View view) {
        LocalDate localDate = null;
        while (localDate == null) {
            try {
                localDate = LocalDate.parse(view.read());
            } catch (DateTimeParseException e) {
                System.out.println("Please enter correct value (date)");
                localDate = LocalDate.parse(view.read());
            }
        }
        return localDate;
    }

    public static String validateEmail(View view) {
        UserDAOImpl userDAO = new UserDAOImpl();
        String email = view.read();
        boolean emailExists = userDAO.checkEmail(email);
        while (emailExists) {
            System.out.println("This email already exists in database. Enter different email.");
            email = view.read();
            emailExists = userDAO.checkEmail(email);
        }
        return email;
    }


    public static Course validateCourse(View view) {
        boolean courseValidated = false;
        Course course = new Course();
        while (!courseValidated) {
            try {
                course = new CourseDAOImpl().get(view.read());
                courseValidated = true;
            } catch (NullPointerException e) {
                System.out.println("There is no such course. Make another try: ");
            }
        }
        return course;
    }
}
