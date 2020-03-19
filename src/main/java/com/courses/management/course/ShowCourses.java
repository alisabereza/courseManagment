package com.courses.management.course;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.View;
import com.courses.management.common.commands.utils.Commands;
import com.courses.management.common.commands.utils.InputString;

import java.util.List;

public class ShowCourses implements Command {
    View view;
    DataAccessObject<Course> courseDAO;

    public ShowCourses(View view, CourseDAO dao) {
        this.view = view;
        courseDAO = dao;
    }

    @Override
    public String command() {
            return Commands.SHOW_COURSES;
        }

    @Override
    public void process(InputString input) {
        List<Course> courses = courseDAO.getAll();
        courses.forEach(course ->Courses.printCourse(view,course));
    }
}
