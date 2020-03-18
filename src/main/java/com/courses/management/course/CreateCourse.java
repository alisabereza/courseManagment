package com.courses.management.course;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.commands.Commands;
import com.courses.management.common.commands.utils.InputString;

public class CreateCourse implements Command {
    private final View view;
    private CourseDAO courseDAO;

    public CreateCourse(View view, CourseDAO dao) {
        this.view = view;
        courseDAO = dao;
    }

    @Override
    public String command() {
        return Commands.CREATE_COURSE;
    }

    @Override
    public void process(InputString input) {

        Course course = Courses.mapCourse(input);
        try {courseDAO.create(course);
            view.write(String.format("Course created with title - %s", course.getTitle()));}
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }

}
