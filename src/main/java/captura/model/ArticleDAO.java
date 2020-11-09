package captura.model;

import captura.entities.ArticleEntity;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class ArticleDAO extends AbstractDAO<ArticleEntity> {

    @Inject
    public ArticleDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
