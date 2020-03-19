package com.courses.management.course;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.commands.utils.Commands;
import com.courses.management.common.commands.utils.InputString;

public class UpdateCourseTitle implements Command {

    private final View view;
    private CourseDAO courseDAO;

    public UpdateCourseTitle(View view, CourseDAO dao) {
        this.view = view;
        this.courseDAO = dao;
    }

    @Override
    public String command() {
        return Commands.UPDATE_COURSE_TITLE;
    }

    @Override
    public void process(InputString input) {
        String oldTitle = input.getParameters()[1];
        String newTitle = input.getParameters()[2];
        Course course = courseDAO.get(oldTitle);
        if (course.getId()==0) {
            throw new IllegalArgumentException(String.format("Course with the title %s does not exist", oldTitle));
        }
        validateTitle(newTitle);
        course.setTitle(newTitle);
        courseDAO.update(course);
        System.out.println("Course updated: " + course.toString());
    }

    private void validateTitle(String title) {
        Course course = courseDAO.get(title);
        if (course.getId()>0) {
            throw new IllegalArgumentException(String.format("Course with title %s already exists", title));
        }
    }
}

