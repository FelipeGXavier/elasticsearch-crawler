package app;

import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;

public class HbnBundle extends HibernateBundle<JournalSearchConfiguration> {

    protected HbnBundle(Class<?> entity, Class<?>... entities) {
        super(entity, entities);
    }

    @Override
    public PooledDataSourceFactory getDataSourceFactory(JournalSearchConfiguration journalSearchConfiguration) {
        return journalSearchConfiguration.getDataSourceFactory();
    }
}
