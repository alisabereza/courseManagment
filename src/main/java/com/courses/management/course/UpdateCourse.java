package com.courses.management.course;

import com.courses.management.common.Command;
import com.courses.management.common.View;

public class UpdateCourse implements Command {

    private final View view;
    private CourseDAO courseDAO;

    public UpdateCourse(View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }
    @Override
    public String command() {
        return "update_course";
    }

    @Override
    public void process() {
        view.write("Enter a course title");
        String title = validate(view.read());
        try {
            Course course = courseDAO.get(title);
            System.out.println("Course found. To update course title, enter command 'update_course_title'");
            System.out.println("To update course status, enter command 'update_course_status");
            switch (view.read()) {
                case "update_course_title":
                    System.out.println("Enter new title: ");
                    String newTitle = validate(view.read());
                    course.setTitle(newTitle);
                    break;
                case "update_course_status":
                    System.out.println("Enter new status: ");
                    String newStatus = view.read();
                    course.setCourseStatus(CourseStatus.valueOf(newStatus));
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
            courseDAO.update(course);

        }
        catch (NullPointerException e) {
            System.out.println(String.format("Course was not found: course.title=%s, ", title) + e);
    }

        view.write(String.format("Course updated: course.title - %s", title));

    }

    private String validate(String value) {
        while (value.trim().isEmpty()) {
            view.write("Please enter the correct title");
            value = view.read();
        }
        return value;
    }
}
