package com.courses.management.course;

import com.courses.management.common.Command;
import com.courses.management.common.InputValueValidator;
import com.courses.management.common.View;
import com.courses.management.common.commands.utils.InputString;

public class FindCourseByTitle implements Command {

    private final View view;
    private CourseDAO courseDAO;

    public FindCourseByTitle(View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
        return "find_course|title";
    }

    @Override
    public void process(InputString input) {

        input.validateParameters(command());
        String title = input.getParameters()[1];
        Course course = courseDAO.get(title);
        if (course ==null) {
            System.out.println(String.format("Course with the name %s was not found", title));
        }
        else Courses.printCourse(view, course);
    }
}
