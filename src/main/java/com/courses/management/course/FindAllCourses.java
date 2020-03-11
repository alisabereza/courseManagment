package com.courses.management.course;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.InputValueValidator;
import com.courses.management.common.View;

import java.util.List;

public class FindAllCourses implements Command {
    View view;
    DataAccessObject<Course> courseDAO;

    public FindAllCourses(View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
            return "all_courses";
        }

    @Override
    public void process() {
        List<Course> courses = courseDAO.getAll();
        courses.forEach(System.out::println);
    }
}
