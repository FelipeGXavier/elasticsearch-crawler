package envio.infra.persistence;

import envio.domain.User;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

@RegisterBeanMapper(UserBean.class)
public interface UserRepository {

    @SqlQuery("select * from users")
    List<User> findAll();
}
