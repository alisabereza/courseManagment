package com.courses.management.common.commands;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.commands.utils.Commands;
import com.courses.management.common.commands.utils.InputString;

public class Exit implements Command {
    private View view;

    public Exit(View view) {
        this.view = view;
    }

    @Override
    public String command() {
        return Commands.EXIT;
    }

    @Override
    public void process(InputString input) {
        view.write("Good bye!");
        System.exit(0);
    }
}