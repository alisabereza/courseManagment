package com.courses.management.course;

import com.courses.management.common.Command;
import com.courses.management.common.DataAccessObject;
import com.courses.management.common.InputValueValidator;
import com.courses.management.common.View;
import com.courses.management.common.commands.utils.InputString;

public class DeleteCourse implements Command {
    View view;
    CourseDAOImpl courseDAO;

    public DeleteCourse(View view) {
        this.view = view;
        courseDAO = new CourseDAOImpl();
    }

    @Override
    public String command() {
        return "delete_course|title";
    }

    @Override
    public void process(InputString input) {
    input.validateParameters(command());
    String title = input.getParameters()[1];

            Course course = courseDAO.get(title);
        if (course.getId()==0) {
            throw new IllegalArgumentException(String.format("Course with the title %s does not exist", title));
        }
        course.setCourseStatus(CourseStatus.DELETED);
    courseDAO.update(course);


        view.write(String.format("Course deleted: course.title - %s", course.getTitle()));
    }

}
