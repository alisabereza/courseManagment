package com.courses.management.user;

  import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.View;
import com.courses.management.common.commands.utils.InputString;

    public class CreateUserWithCourse implements Command {

        private final View view;
        private DataAccessObject<User> userDAO;
        public CreateUserWithCourse(View view) {
            this.view = view;
            userDAO = new UserDAOImpl();
        }


        @Override
        public String command() {
            return "create_user_course|first_name|last_name|email|course";
        }

        @Override
        public void process(InputString input) {
            input.validateParameters(command());
            User user = Users.mapUser(input);
            userDAO.create(user);
            view.write(String.format("User created: %s", user.toString() ));
        }
}
