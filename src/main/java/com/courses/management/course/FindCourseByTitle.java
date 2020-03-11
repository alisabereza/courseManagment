package com.courses.management.course;

import com.courses.management.common.Command;
import com.courses.management.common.View;

public class FindCourseByTitle implements Command {

    private final View view;
    private CourseDAO courseDAO;

    public FindCourseByTitle(View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
        return "find_course_by_title";
    }

    @Override
    public void process() {
        view.write("Enter a course title");
        String title = validate(view.read());

        Course course = courseDAO.get(title);
        System.out.println(course.getTitle());
        view.write(String.format("Course found: %s", course.toString()));
    }

    private String validate(String value) {
        while (value.trim().isEmpty()) {
            view.write("Please enter the correct title");
            value = view.read();
        }
        return value;
    }
}
