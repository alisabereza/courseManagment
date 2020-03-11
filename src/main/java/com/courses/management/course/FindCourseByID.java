package com.courses.management.course;

import com.courses.management.common.Command;
import com.courses.management.common.InputValueValidator;
import com.courses.management.common.View;

public class FindCourseByID implements Command {

    private final View view;
    private CourseDAO courseDAO;

    public FindCourseByID(View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
        return "find_course_by_id";
    }

    @Override
    public void process() {
        view.write("Enter a course ID");
        int id = InputValueValidator.validateInt(view);

        Course course = courseDAO.get(id);

        view.write(String.format("Course found: %s", course.toString()));
    }

}
