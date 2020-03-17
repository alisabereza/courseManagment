package com.courses.management.user;

import com.courses.management.common.View;
import com.courses.management.common.commands.utils.InputString;
import com.courses.management.course.Course;
import com.courses.management.course.CourseDAO;
import com.courses.management.course.CourseDAOImpl;
import com.courses.management.course.CourseStatus;

public class Users {
    public static User mapUser(InputString input) {
        User user = new User();
        String[] userParameters = input.getParameters();
        user.setFirstName(userParameters[1]);
        user.setLastName(userParameters[2]);
        user.setEmail(validateEmail(userParameters[3]));
        user.setUserRole(UserRole.NEWCOMER);
        user.setStatus(UserStatus.ACTIVE);
        if (userParameters.length == 5) {
            CourseDAO courseDAO = new CourseDAOImpl();
            try {
                Course course = courseDAO.get(userParameters[4]);
                if (course.getCourseStatus() != CourseStatus.DELETED) {
                    user.setCourse(course);
                }
            }
            catch (NullPointerException e) {
                System.out.println(String.format("Course with the name %s was not found", userParameters[3]) + e);
            }
            }
        return user;
    }

    public static void printUser(View view, User user) {
        view.write("User:");
        view.write(String.format("\t Name = %s", user.getFirstName()));
        view.write(String.format("\t Last Name = %s", user.getLastName()));
        view.write(String.format("\t Email = %s", user.getEmail()));
        view.write(String.format("\t Role = %s", user.getUserRole().getRole()));
        view.write(String.format("\t Status = %s", user.getStatus().getStatus()));
        view.write(String.format("\t Course = %s", (user.getCourse()!=null)?user.getCourse().getTitle():""));

    }

    public static String validateEmail(String email) {
        UserDAOImpl userDAO = new UserDAOImpl();
        boolean emailExists = userDAO.checkEmail(email);
        if (emailExists) {
            throw new IllegalArgumentException(String.format("The email %s already exists in Database", email));
        }
        return email;
    }
}

