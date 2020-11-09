package model;

import entities.Sample;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class SampleDAO extends AbstractDAO<Sample> {

    @Inject
    public SampleDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void create(Sample sample) {
        this.persist(sample);
    }

    public Sample find(Long id){
        return this.get(id);
    }
}
