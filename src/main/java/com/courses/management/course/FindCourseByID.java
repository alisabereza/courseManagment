package com.courses.management.course;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.commands.utils.Commands;
import com.courses.management.common.commands.utils.InputString;

public class FindCourseByID implements Command {

    private final View view;
    private CourseDAO courseDAO;

    public FindCourseByID(View view, CourseDAO dao) {
        this.view = view;
        courseDAO = dao;
    }

    @Override
    public String command() {
        return Commands.FIND_COURSE_BY_ID;
    }

    @Override
    public void process(InputString input) {
        view.write("Enter a course ID");
        int id = Courses.validateID(view);

        Course course = courseDAO.get(id);

        view.write(String.format("Course found: %s", course.toString()));
    }
}
