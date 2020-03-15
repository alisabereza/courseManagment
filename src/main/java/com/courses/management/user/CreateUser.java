package com.courses.management.user;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.View;
import com.courses.management.common.commands.utils.InputString;

public class CreateUser implements Command {

    private final View view;
    private DataAccessObject<User> userDAO;
    public CreateUser(View view) {
        this.view = view;
        userDAO = new UserDAOImpl();
    }


    @Override
    public String command() {
        return "create_user|first_name|last_name|email";
    }

    @Override
    public void process(InputString input) {
        input.validateParameters(command());
        User user = Users.mapUser(input);
        userDAO.create(user);
        view.write(String.format("User created: %s", user.toString() ));
    }
}
