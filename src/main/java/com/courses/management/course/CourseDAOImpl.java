package com.courses.management.course;

import com.courses.management.common.DatabaseConnector;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CourseDAOImpl implements  CourseDAO {
    private final static Logger LOG = LogManager.getLogger(CourseDAOImpl.class);
    private final static String INSERT = "INSERT INTO course(title, status) " +
            "VALUES(?, ?);";
    private final static String SELECT_BY_ID = "select id, title, status from course where id = ?;";
    private final static String SELECT_BY_TITLE = "select id, title, status from course where title = ?;";

    private final static String UPDATE = "update course set title = ?, status = ? where id = ?;";

    private HikariDataSource dataSource = DatabaseConnector.getConnector();

    @Override
    public void create(Course course) {
        LOG.debug(String.format("create: course.title=%s", course.getTitle()));

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, course.getTitle());
            statement.setString(2, course.getCourseStatus().getStatus());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(String.format("create: course.title=%s", course.getTitle()), e);
        }
    }

    @Override
    public void update(Course course) {
        LOG.debug(String.format("update: course.title=%s", course.getTitle()));

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, course.getTitle());
            statement.setString(2, course.getCourseStatus().getStatus());
            statement.setInt(3, course.getId());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(String.format("update: course.title=%s", course.getTitle()), e);
        }
    }

    @Override
    public void delete(int id) {

    }


    @Override
    public Course get(int id) {
        LOG.debug(String.format("get: course.id=%s", id));

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            Course course = new Course();
            while (resultSet.next()) {
                course.setId(resultSet.getInt("id"));
                course.setTitle(resultSet.getString("title"));
                course.setCourseStatus(CourseStatus.valueOf(resultSet.getString("status")));

            }
            return course;
        } catch (SQLException e) {
            LOG.error(String.format("get: course.id=%s", id), e);
            return null;
        }

    }
    @Override
    public Course get (String title) {
        LOG.debug(String.format("get: course.title=%s", title));

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_TITLE)) {
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();

            Course course = new Course();
            while (resultSet.next()) {
                course.setId(resultSet.getInt("id"));
                course.setTitle(resultSet.getString("title"));
                course.setCourseStatus(CourseStatus.valueOf(resultSet.getString("status")));

            }
            return course;
        } catch (SQLException e) {
            LOG.error(String.format("get: course.title=%s", title), e);
            return null;
        }

    }
        @Override
    public List<Course> getAll() {
        return null;
    }

}
