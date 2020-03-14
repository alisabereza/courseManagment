package com.courses.management.course;

import com.courses.management.common.Command;
import com.courses.management.common.InputValueValidator;
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
    String title = InputValueValidator.validateString(view);
        try {
        Course course = courseDAO.get(title);
        System.out.println("Course found. To update course title, type 'title'");
        System.out.println("To update course status, type 'status'");
        switch (view.read()) {
            case "title":
                System.out.println("Enter new title: ");
                String newTitle = InputValueValidator.validateString(view);
                course.setTitle(newTitle);
                break;
            case "status":
                System.out.println("Enter new status: ");
                String newStatus = InputValueValidator.validateCourseStatus(view);
                course.setCourseStatus(CourseStatus.valueOf(newStatus));
                break;
            default:
                System.out.println("Invalid command. Try again");
                process();
                break;
        }
        courseDAO.update(course);
            view.write(String.format("Course updated: %s", course.toString()));

    }
        catch (NullPointerException e) {
        System.out.println(String.format("Course was not found: course.title=%s, ", title) + e);
    }


}
}
