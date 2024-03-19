package dao;

import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class UserDaoTest {

    private final UserDao userDao = new UserDao();

    @Test
    public void connection() throws SQLException {
        try (final var connection = userDao.getConnection()) {
            Assertions.assertThat(connection).isNotNull();
        }
    }

    @Test
    public void addUser() {
        final var user = new User("testUserId", "testUser");
        userDao.addUser(user);
    }

    @Test
    public void findByUserId() {
        final var user = userDao.findByUserId("testUserId");

        Assertions.assertThat(user).isEqualTo(new User("testUserId", "testUser"));
    }
}
