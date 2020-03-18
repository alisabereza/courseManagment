package com.courses.management.course;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.commands.Commands;
import com.courses.management.common.commands.utils.InputString;

import javax.sql.DataSource;

public class FindCourseByTitle implements Command {

    private final View view;
    private CourseDAO courseDAO;

    public FindCourseByTitle(View view, CourseDAO dao) {
        this.view = view;
        courseDAO = dao;
    }

    @Override
    public String command() {
        return Commands.FIND_COURSE;
    }

    @Override
    public void process(InputString input) {
        String title = input.getParameters()[1];
        Course course = courseDAO.get(title);
        if (course ==null) {
            System.out.println(String.format("Course with the name %s was not found", title));
        }
        else Courses.printCourse(view, course);
    }
}
