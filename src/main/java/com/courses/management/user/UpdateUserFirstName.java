package com.courses.management.user;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.commands.utils.InputString;

public class UpdateUserFirstName implements Command {
    private final View view;
    private UserDAOImpl userDAO;

    public UpdateUserFirstName(View view, UserDAO dao) {
        this.view = view;
        userDAO = (UserDAOImpl) dao;
    }


    @Override
    public String command() {
        return "update_user_first_name|user_email|new_first_name";
    }

    @Override
    public void process(InputString input) {
        String email = validateEmail(input.getParameters()[1]);
        User user = userDAO.get(email);
        user.setFirstName(input.getParameters()[2]);
        userDAO.update(user);
        view.write(String.format("User first name updated: %s", user.toString()));

    }

    public String validateEmail(String email) {
        boolean emailExists = userDAO.checkEmail(email);
        if (!emailExists) {
throw new IllegalArgumentException("Such email doesn't exist in database");
        }
        return email;
    }
}
