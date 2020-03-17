package com.courses.management.user;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.InputValueValidator;
import com.courses.management.common.View;
import com.courses.management.common.commands.utils.InputString;
import com.courses.management.course.Course;
import com.courses.management.course.CourseStatus;

public class UpdateUser implements Command {

    private final View view;
    private UserDAOImpl userDAO;

    public UpdateUser(View view) {
        this.view = view;
        userDAO = new UserDAOImpl();
    }

    @Override
    public String command() {
        return "update_user";
    }

    @Override
    public void process(InputString input) {
        view.write("Enter user email");
        String email = InputValueValidator.validateString(view);
        try {
            User user = userDAO.findUserByEmail(email);
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
                    String firstName = InputValueValidator.validateString(view);
                    user.setFirstName(firstName);
                    userDAO.update(user);
                    break;
                case "update_user_last_name":
                    System.out.println("Enter new last name: ");
                    String lastName = InputValueValidator.validateString(view);
                    user.setLastName(lastName);
                    break;
                case "update_user_email":
                    System.out.println("Enter new email ");
                    String newEmail = InputValueValidator.validateEmail(view);
                    user.setEmail(newEmail);
                    break;
                case "update_user_role":
                    System.out.println("Enter new role: ");
                    String newRole = InputValueValidator.validateUserRole(view);
                    user.setUserRole(UserRole.valueOf(newRole));
                    break;
                case "update_user_status":
                    System.out.println("Enter new status: ");
                    String newStatus = InputValueValidator.validateUserStatus(view);
                    user.setStatus(UserStatus.valueOf(newStatus));
                    break;
                case "update_user_course":
                    System.out.println("Enter new course: ");
                    Course newCourse = InputValueValidator.validateCourse(view);
                    user.setCourse(newCourse);
                    break;
                default:
                    System.out.println("Invalid command. Try again");
                   // process();
                    break;
            }
            userDAO.update(user);
            view.write(String.format("User updated: %s", user.toString()));

        }
        catch (NullPointerException e) {
            System.out.println(String.format("User was not found: user.email=%s, ", email) + e);
        }


    }
    }

