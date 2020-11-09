package app;

import com.google.inject.AbstractModule;
import org.hibernate.SessionFactory;

public class HbnModule extends AbstractModule {

    private final HbnBundle bundle;

    public HbnModule(HbnBundle bundle) {
        this.bundle = bundle;
    }

    @Override
    protected void configure() {
        this.bind(SessionFactory.class).toInstance(this.bundle.getSessionFactory());
    }
}
