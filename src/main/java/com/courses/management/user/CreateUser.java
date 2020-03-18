package com.courses.management.user;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.View;
import com.courses.management.common.commands.Commands;
import com.courses.management.common.commands.utils.InputString;

public class CreateUser implements Command {

    private final View view;
    private DataAccessObject<User> userDAO;
    public CreateUser(View view, UserDAO dao) {
        this.view = view;
        userDAO = dao;
    }


    @Override
    public String command() {
        return Commands.CREATE_USER;
    }

    @Override
    public void process(InputString input) {
        User user = Users.mapUser(input);
        userDAO.create(user);
        view.write(String.format("User created: %s", user.toString() ));
    }
}
