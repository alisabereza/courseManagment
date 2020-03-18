package com.courses.management.course;

import com.courses.management.common.View;
import com.courses.management.common.commands.utils.InputString;

public class Courses {
    public static Course mapCourse(InputString input) {
        String[] parameters = input.getParameters();
        String title = parameters[1];
        Course course = new Course();
        course.setTitle(title);
        course.setCourseStatus(CourseStatus.NOT_STARTED);
        return course;
    }

    public static void printCourse(View view, Course course) {
        view.write("Course:");
        view.write(String.format("\t title = %s", course.getTitle()));
        view.write(String.format("\t status = %s", course.getCourseStatus()));
    }

    public static int validateID(View view) {
        String value = view.read();
        while (!value.matches("[0-9]*")) {
            System.out.println("Please enter correct value (number)");
            value = view.read();
        }
        return Integer.parseInt(value);
    }


}
