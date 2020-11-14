package captura.infra;

import captura.infra.entities.ArticleEntity;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.io.Serializable;

public class ArticleDAO extends AbstractDAO<ArticleEntity>{


    @Inject
    public ArticleDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public ArticleEntity persist(ArticleEntity entity) throws HibernateException {
        return super.persist(entity);
    }

    @Override
    public ArticleEntity get(Serializable id) {
        return super.get(id);
    }

    public Session getSession() {
        return currentSession().getSession();
    }
}
