package com.courses.management.common.commands.utils;

public class Commands {
    public static final String HELP = "help";
    public static final String EXIT = "exit";
    public static final String CREATE_COURSE = "create_course|title";
    public static final String DELETE_COURSE = "delete_course|title";
    public static final String FIND_COURSE = "find_course|title";
    public static final String FIND_COURSE_BY_ID = "find_course_by_id|id";
    public static final String SHOW_COURSES = "show_courses";
    public static final String SHOW_COURSES_BY_STATUS = "show_courses_by_status|status";
    public static final String UPDATE_COURSE_STATUS = "update_course_status|title|status";
    public static final String UPDATE_COURSE_TITLE = "update_course_title|oldTitle|newTitle";
    public static final String CREATE_USER = "create_user|first_name|last_name|email";
    public static final String CREATE_USER_COURSE = "create_user_course|first_name|last_name|email|course";
    public static final String UPDATE_USER = "update_user";
    public static final String UPDATE_USER_FIRST_NAME = "update_user_first_name";
    public static final String FIND_USER = "find_user|email";

    private Commands() {
        throw new UnsupportedOperationException("Impossible to instantiate util class");
    }
}