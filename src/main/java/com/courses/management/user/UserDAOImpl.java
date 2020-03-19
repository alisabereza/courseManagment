package com.courses.management.user;

import com.courses.management.common.exceptions.SQLCourseException;
import com.courses.management.course.CourseDAOImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    private final static Logger LOG = LogManager.getLogger(UserDAOImpl.class);

    private static final String FIND_USER_BY_ID = "SELECT id, first_name, last_name, email, user_role, status " +
            "FROM users WHERE id=?;";

    private final static String INSERT = "INSERT INTO users (first_name, last_name, email, user_role, status, course_id) " +
            "VALUES(?,?,?,?,?,(select id from course where title = ?));";

    private final static String EMAIL = "select id from users where email = ?;";

    private final static String FIND_USER_BY_EMAIL = "SELECT id, first_name, last_name, email, user_role, status, course_id from users where email = ?;";

    private final static String UPDATE = "UPDATE users set first_name=?, last_name=?, email=?, user_role=?, status=?, course_id=(select id from course where title = ?)  where id = ?;";

    private static final String DELETE = "DELETE FROM users WHERE id=?;";

    private static final String FIND_ALL_USERS = "SELECT id, first_name, last_name, email, user_role, status " +
            "FROM users;";

    private DataSource dataSource;

    public UserDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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
        LOG.debug(String.format("delete: user.id=%s ", id));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            LOG.error(String.format("delete: user.id=%s", id), e);
            throw new SQLCourseException("Error occurred when removing user");
        }
    }

    @Override
    public User get(int id) {
        LOG.debug(String.format("get: user.id=%s ", id));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID)) {
            statement.setInt(1, id);
            return getUser(statement.executeQuery());
        } catch (SQLException e) {
            LOG.error(String.format("get: user.id=%s", id), e);
            throw new SQLCourseException("Error occurred when retrieving user");
        }
    }

    private User getUser(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return mapUserFromRS(rs);
        }
        return null;
    }

    private User mapUserFromRS(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setUserRole(UserRole.getUserRole(rs.getString("user_role")).get());
        user.setStatus(UserStatus.getUserStatus(rs.getString("status")).get());
        return user;
    }

    @Override
    public List<User> getAll() {
        LOG.debug("getAll: ");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS)) {
            return getUserList(statement.executeQuery());
        } catch (SQLException e) {
            LOG.error("getAll: ", e);
            throw new SQLCourseException("Error occurred when retrieving all user");
        }
    }

    private List<User> getUserList(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            users.add(mapUserFromRS(rs));
        }
        return users;
    }

    @Override
    public User get(String email) {
        LOG.debug(String.format("get: user.email=%s ", email));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL)) {
            statement.setString(1, email);
            return getUser(statement.executeQuery());
        } catch (SQLException e) {
            LOG.error(String.format("get: user.email=%s", email), e);
            throw new SQLCourseException("Error occurred when retrieving user");
        }
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

    public DataSource getDataSource() {
        return this.dataSource;
    }
}