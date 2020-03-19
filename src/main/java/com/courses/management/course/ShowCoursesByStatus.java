package com.courses.management.course;


import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.commands.utils.Commands;
import com.courses.management.common.commands.utils.InputString;

import java.util.List;
import java.util.Optional;

public class ShowCoursesByStatus implements Command {
    private final View view;
    private CourseDAOImpl courseDAO;

    public ShowCoursesByStatus(View view, CourseDAO dao) {
        this.view = view;
        courseDAO = (CourseDAOImpl) dao;
    }

    @Override
    public String command() {
        return Commands.SHOW_COURSES_BY_STATUS;
    }

    @Override
    public void process(InputString input) {
        String newStatus = input.getParameters()[1];
        List<Course> courses = courseDAO.getAllByStatus(getCourseStatus(newStatus).getStatus());
        courses.forEach(course -> Courses.printCourse(view, course));
    }

    private CourseStatus getCourseStatus (String status) {
        Optional<CourseStatus> courseStatus = CourseStatus.getCourseStatus(status);
        return courseStatus.orElseThrow(() -> new IllegalArgumentException("Status value is wrong, choose the correct one"));
    }
}
