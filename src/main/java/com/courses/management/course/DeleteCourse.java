package com.courses.management.course;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.InputValueValidator;
import com.courses.management.common.View;

public class DeleteCourse implements Command {
    View view;
    CourseDAOImpl courseDAO;

    public DeleteCourse(View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
        return "delete_course";
    }

    @Override
    public void process() {
        view.write("To delete course by title, type 'title'");
        view.write("To delete course by ID, type 'id'");
            Course course = new Course();
            try {
                switch (view.read()) {
                    case "title":
                        System.out.println("Enter course title: ");
                        String title = InputValueValidator.validateString(view);
                        course = courseDAO.get(title);
                        break;
                    case "id":
                        System.out.println("Enter course id: ");
                        int id = InputValueValidator.validateInt(view);
                        course = courseDAO.get(id);
                        break;
                    default:
                        System.out.println("Invalid command. Try again");
                        process();
                        break;
                }
                courseDAO.delete(course.getId());
            }

       catch (NullPointerException e) {
            System.out.println("Course was not found" + e);
        }

        view.write(String.format("Course deleted: course.title - %s", course.getTitle()));
    }
}
