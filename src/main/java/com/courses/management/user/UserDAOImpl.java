package com.courses.management.user;

import com.courses.management.common.DataAccessObject;
import com.courses.management.common.DatabaseConnector;
import com.courses.management.course.CourseDAOImpl;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements DataAccessObject<User> {
    private final static Logger LOG = LogManager.getLogger(UserDAOImpl.class);
    private final static String INSERT = "INSERT INTO users (first_name, last_name, email, user_role, status, course_id) " +
            "VALUES(?,?,?,?,?,(select id from course where title = ?));";
    private final static String EMAIL = "select id from users where email = ?;";
    private final static String FIND_BY_EMAIL = "SELECT id, first_name, last_name, email, user_role, status, course_id from users where email = ?;";
    private final static String UPDATE = "UPDATE users set first_name=?, last_name=?, email=?, user_role=?, status=?, course_id=(select id from course where title = ?)  where id = ?;";

    private HikariDataSource dataSource = DatabaseConnector.getConnector();

    @Override
    public void create(User user) {
        LOG.debug(String.format("create: user: %s", user));
        String courseTitle =  (user.getCourse()!=null)?user.getCourse().getTitle():"";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getUserRole().getRole());
            statement.setString(5, user.getStatus().getStatus());
            statement.setString(6, courseTitle);
            statement.execute();
        } catch (SQLException e) {
            LOG.error(String.format("create: user: %s", user.toString()), e);
        }

    }

    @Override
    public void update(User user) {

        LOG.debug(String.format("update user: %s", user.toString()));
        Optional<String> optionalCourse = Optional.ofNullable(user.getCourse().getTitle());

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getUserRole().getRole());
            statement.setString(5, user.getStatus().getStatus());
            statement.setString(6, optionalCourse.orElse(""));
            statement.setInt(7, user.getId());
            statement.execute();
            ResultSet resultSet = statement.executeQuery();

        } catch (SQLException e) {
            LOG.error(String.format("update user: %s", user.toString()), e);
        }
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public User get(int id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    public  boolean checkEmail (String email) {
        boolean emailExists = false;
        LOG.debug(String.format("check email=%s", email));

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                emailExists = true;
            }
        } catch (SQLException e) {
            LOG.error(String.format("check email=%s", email), e);
        }

        return emailExists;
    }

    public  User findUserByEmail (String email) {


        LOG.debug(String.format("find user by email: %s", email));
        User user = new User();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));

                user.setFirstName(resultSet.getString("first_name"));

                user.setLastName(resultSet.getString("last_name"));

                user.setEmail(resultSet.getString("email"));

                user.setStatus(UserStatus.valueOf(resultSet.getString("status")));

                user.setUserRole(UserRole.valueOf(resultSet.getString("user_role")));

              if (resultSet.getInt("course_id")==0) {
                    user.setCourse(new CourseDAOImpl().get(resultSet.getInt("course_id")));
                    System.out.println("Course is empty");
                }
              else {user.setCourse(new CourseDAOImpl().get(resultSet.getInt("course_id")));
                  System.out.println("Course set: " + user.getCourse().getTitle());}


            }

        } catch (SQLException e) {
            LOG.error(String.format("find user by email email=%s", email), e);

        }
        return user;
    }
}