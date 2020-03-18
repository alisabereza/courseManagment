package com.courses.management.course;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.commands.Commands;
import com.courses.management.common.commands.utils.InputString;

import java.util.Optional;

public class UpdateCourseStatus implements Command {
    private final View view;
    private CourseDAO courseDAO;

    public UpdateCourseStatus(View view, CourseDAO dao) {
        this.view = view;
        courseDAO = dao;
    }

    @Override
    public String command() {
        return Commands.UPDATE_COURSE_STATUS;
    }

    @Override
    public void process(InputString input) {
        String title = input.getParameters()[1];
        String newStatus = input.getParameters()[2];
        Course course = courseDAO.get(title);
        if (course.getId()==0) {
            throw new IllegalArgumentException(String.format("Course with the title %s does not exist", title));
        }
        course.setCourseStatus(getStatus(newStatus));
        courseDAO.update(course);
        System.out.println("Course updated: " + course.toString());
    }

    private CourseStatus getStatus(String status) {
        Optional<CourseStatus> courseStatus = CourseStatus.getCourseStatus(status);
        return courseStatus.orElseThrow(() ->
                new IllegalArgumentException("Course status is wrong, choose the correct one"));
    }
}
