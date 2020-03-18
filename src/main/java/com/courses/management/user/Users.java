package com.courses.management.user;

import com.courses.management.common.View;
import com.courses.management.common.commands.utils.InputString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Users {
    private static final Logger LOG = LogManager.getLogger(Users.class);

    private static final String EMAIL_REGEXP = "^[-a-z0-9!#$%&'*+/=?^_`{|}~]+(?:\\.[-a-z0-9!#$%&'*+/=?^_`{|}~]+)*@(?:[a-z0-9](" +
            "[-a-z0-9]{0,61}[a-z0-9])?\\.)*(?:aero|arpa|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|" +
            "net|org|pro|tel|travel|[a-z][a-z])$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEXP);
    private static final int FIRST_NAME_INDEX = 1;
    private static final int LAST_NAME_INDEX = 2;
    private static final int EMAIL_INDEX = 3;

    public static void validateEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            LOG.warn(String.format("validateEmail: email %s is incorrect", email));
            throw new IllegalArgumentException(String.format("Wrong email address %s", email));
        }
    }

    public static User mapUser(InputString input) {
        String[] parameters = input.getParameters();
        User user = new User();
        user.setFirstName(parameters[FIRST_NAME_INDEX]);
        user.setLastName(parameters[LAST_NAME_INDEX]);
        String email = parameters[EMAIL_INDEX];
        validateEmail(email);
        user.setEmail(email);
        user.setStatus(UserStatus.NOT_ACTIVE);
        user.setUserRole(UserRole.NEWCOMER);
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

}

