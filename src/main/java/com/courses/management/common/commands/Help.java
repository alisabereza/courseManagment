package com.courses.management.common.commands;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.commands.utils.InputString;
import com.courses.management.course.CourseStatus;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Help implements Command {
    private View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public String command() {
        return "help";
    }

    @Override
    public void process(InputString input) {
        view.write("-----------------------COMMANDS--------------------------");
        view.write("---------------------------------------------------------");
        view.write("\t| create_course|title");
        view.write("\t|\t-> create a course with a title");
        view.write("---------------------------------------------------------");

        view.write("\t| find_course|title");
        view.write("\t|\t-> find course by title");
        view.write("---------------------------------------------------------");

        view.write("\t| update_course_title|oldTitle|newTitle");
        view.write("\t|\t-> update course title. Title is a unique field");
        view.write("---------------------------------------------------------");

        view.write("\t| update_course_status|title|status");
        view.write("\t|\t-> update course status. Possible status values: ");
        view.write("\t|\t-> " + collectCourseStatuses());
        view.write("---------------------------------------------------------");

        view.write("\t| show_courses");
        view.write("\t|\t-> show all courses");
        view.write("---------------------------------------------------------");

        view.write("\t| show_courses_by_status|status");
        view.write("\t|\t-> show courses by status");
        view.write("---------------------------------------------------------");

        view.write("\t| delete_course|title");
        view.write("\t|\t-> move course to a DELETED status");
        view.write("---------------------------------------------------------");

        view.write("\t| create_user|first_name|last_name|email");
        view.write("\t|\t-> create new user (without course assigned)");
        view.write("---------------------------------------------------------");

        view.write("\t| create_user_course|first_name|last_name|email|course");
        view.write("\t|\t-> create new user (with course assigned)");
        view.write("---------------------------------------------------------");

        view.write("\t| update_user_first_name|user_email|new_first_name");
        view.write("\t|\t-> find user by email and update first name");
        view.write("---------------------------------------------------------");

        view.write("\t| exit");
        view.write("\t|\t-> exit application");
        view.write("---------------------------------------------------------");
        view.write("---------------------------------------------------------");
    }

    private String collectCourseStatuses() {
        return Arrays.stream(CourseStatus.values()).map(CourseStatus::getStatus).collect(Collectors.joining(", "));
    }
}
