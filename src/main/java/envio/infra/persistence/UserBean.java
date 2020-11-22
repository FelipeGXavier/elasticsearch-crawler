package envio.infra.persistence;

import envio.domain.User;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBean implements RowMapper<User> {

    @Override
    public User map(ResultSet rs, StatementContext ctx) throws SQLException {
        return User.of(rs.getInt("id"), rs.getInt("company"), rs.getString("email"), rs.getString("config"));
    }
}
