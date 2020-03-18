package com.courses.management.user;

import com.courses.management.common.*;
import com.courses.management.common.commands.Commands;
import com.courses.management.common.commands.utils.InputString;
import com.courses.management.course.*;

public class UpdateUser implements Command {

    private final View view;
    private UserDAOImpl userDAO;

    public UpdateUser(View view, UserDAO dao) {
        this.view = view;
        userDAO = (UserDAOImpl) dao;
    }

    @Override
    public String command() {
        return Commands.UPDATE_USER;
    }

    @Override
    public void process(InputString input) {
        view.write("Enter user email");
        String email = validateString(view);
        try {
            User user = userDAO.get(email);
            System.out.println(user.toString());
              System.out.println("To update user first name, type 'update_user_first_name'");
            System.out.println("To update user last name, type 'update_user_last_name'");
            System.out.println("To update user email, type 'update_user_email'");
            System.out.println("To update user role, type 'update_user_role'");
            System.out.println("To update user status, type 'update_user_status'");
            System.out.println("To update user course, type 'update_user_course'");

            switch (view.read()) {
                case "update_user_first_name":
                    System.out.println("Enter new first name: ");
                    String firstName = validateString(view);
                    user.setFirstName(firstName);
                    userDAO.update(user);
                    break;
                case "update_user_last_name":
                    System.out.println("Enter new last name: ");
                    String lastName = validateString(view);
                    user.setLastName(lastName);
                    break;
                case "update_user_email":
                    System.out.println("Enter new email ");
                    String newEmail = validateEmail(view);
                    user.setEmail(newEmail);
                    break;
                case "update_user_role":
                    System.out.println("Enter new role: ");
                    String newRole = validateUserRole(view);
                    user.setUserRole(UserRole.valueOf(newRole));
                    break;
                case "update_user_status":
                    System.out.println("Enter new status: ");
                    String newStatus = validateUserStatus(view);
                    user.setStatus(UserStatus.valueOf(newStatus));
                    break;
                case "update_user_course":
                    System.out.println("Enter new course: ");
                    Course newCourse = validateCourse(view);
                    user.setCourse(newCourse);
                    break;
                default:
                    System.out.println("Invalid command. Try again");
                    break;
            }
            userDAO.update(user);
            view.write(String.format("User updated: %s", user.toString()));

        }
        catch (NullPointerException e) {
            System.out.println(String.format("User was not found: user.email=%s, ", email) + e);
        }


    }

    public  String validateString(View view) {
        String value = view.read();
        while (value.trim().isEmpty()) {
            view.write("Please enter the correct title");
            value = view.read();
        }
        return value;
    }

    public  String validateUserRole (View view) {
        String value = view.read();
        boolean trueRole = false;
        while (!trueRole) {
            try {
                UserRole.valueOf(value);
                trueRole = true;
            } catch (IllegalArgumentException e) {

                view.write("Please enter the correct value");
                value = view.read();
            }
        }
        return value;
    }

    public  String validateUserStatus (View view) {
        String value = view.read();
        boolean trueStatus = false;
        while (!trueStatus) {
            try {
                UserStatus.valueOf(value);
                trueStatus = true;
            } catch (IllegalArgumentException e) {

                view.write("Please enter the correct value");
                value = view.read();
            }
        }
        return value;
    }

    public  String validateEmail(View view) {
        String email = view.read();
        boolean emailExists = userDAO.checkEmail(email);
        while (emailExists) {
            System.out.println("This email already exists in database. Enter different email.");
            email = view.read();
            emailExists = userDAO.checkEmail(email);
        }
        return email;
    }


    public  Course validateCourse(View view) {
        boolean courseValidated = false;
        Course course = new Course();
        while (!courseValidated) {
            try {
                course = new CourseDAOImpl(userDAO.getDataSource()).get(view.read());
                courseValidated = true;
            } catch (NullPointerException e) {
                System.out.println("There is no such course. Make another try: ");
            }
        }
        return course;
    }
}

