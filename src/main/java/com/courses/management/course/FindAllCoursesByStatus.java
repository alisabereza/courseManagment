package com.courses.management.course;


import com.courses.management.common.Command;
import com.courses.management.common.InputValueValidator;
import com.courses.management.common.View;

import java.util.List;

public class FindAllCoursesByStatus implements Command {
    private final View view;
    private CourseDAOImpl courseDAO;

    public FindAllCoursesByStatus (View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
        return "courses_by_status";
    }

    @Override
    public void process() {
        view.write("Enter Status: ");
        String newStatus = InputValueValidator.validateCourseStatus(view);
        view.write(String.format("List of courses with status: %s", newStatus));
        List<Course> courses = courseDAO.getAllByStatus(newStatus);
        courses.forEach(System.out::println);
    }
}
