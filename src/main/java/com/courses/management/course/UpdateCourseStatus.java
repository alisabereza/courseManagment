package com.courses.management.course;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.commands.utils.InputString;

public class UpdateCourseStatus implements Command {
    private final View view;
    private CourseDAO courseDAO;

    public UpdateCourseStatus(View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
        return "update_course_status|title|status";
    }

    @Override
    public void process(InputString input) {
        input.validateParameters(command());
        String title = input.getParameters()[1];
        String newStatus = input.getParameters()[2];
        Course course = courseDAO.get(title);
        if (course.getId()==0) {
            throw new IllegalArgumentException(String.format("Course with the title %s does not exist", title));
        }
        course.setCourseStatus(CourseStatus.valueOf(validateCourseStatus(newStatus)));
        courseDAO.update(course);
        System.out.println("Course updated: " + course.toString());
    }

    public  String validateCourseStatus(String newStatus) {
        String status = newStatus;
        boolean trueStatus = false;
        while (!trueStatus) {
            try {
                CourseStatus.valueOf(newStatus);
                trueStatus = true;
            } catch (IllegalArgumentException e) {

                view.write("Please enter the correct value");
                status = view.read();
            }
        }
        return status;
    }
}
