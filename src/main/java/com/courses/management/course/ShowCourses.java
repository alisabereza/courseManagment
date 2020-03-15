package com.courses.management.course;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.View;
import com.courses.management.common.commands.utils.InputString;

import java.util.List;

public class ShowCourses implements Command {
    View view;
    DataAccessObject<Course> courseDAO;

    public ShowCourses(View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
            return "show_courses";
        }

    @Override
    public void process(InputString input) {
        input.validateParameters(command());
        List<Course> courses = courseDAO.getAll();
        courses.forEach(course ->Courses.printCourse(view,course));
    }
}
