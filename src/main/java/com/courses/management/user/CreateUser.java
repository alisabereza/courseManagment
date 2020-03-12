package com.courses.management.user;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.InputValueValidator;
import com.courses.management.common.View;
import com.courses.management.course.Course;
import com.courses.management.course.CourseStatus;

public class CreateUser implements Command {

    View view;
    DataAccessObject<User> userDAO;
    public CreateUser(View view) {
        this.view = view;
        userDAO = new UserDAOImpl();
    }


    @Override
    public String command() {
        return "create_user";
    }

    @Override
    public void process() {
        view.write("Enter first name: ");
        String firstName = InputValueValidator.validateString(view);
        view.write("Enter last name: ");
        String lastName = InputValueValidator.validateString(view);
        view.write("Enter email: ");
        String email = InputValueValidator.validateEmail(view);

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUserRole(UserRole.NEWCOMER);
        user.setStatus(UserStatus.ACTIVE);
        System.out.println("Do you want to add Course to user record? type 'yes' or 'no': ");
        switch (view.read()) {
            case "yes":
                System.out.println("Enter course title: ");
                Course course = InputValueValidator.validateCourse(view);
                user.setCourse(course);
            break;
            case "no": break;
            default:
                System.out.println("Incorrect input");
                process (); break;
        }



        userDAO.create(user);
        view.write(String.format("User created: u%s", user.toString() ));
    }
}
