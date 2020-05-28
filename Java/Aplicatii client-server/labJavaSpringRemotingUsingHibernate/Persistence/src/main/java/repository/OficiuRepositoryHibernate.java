package repository;

import model.Oficiu;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.Properties;

public class OficiuRepositoryHibernate implements IOficiuRepository {

    static SessionFactory sessionFactory;

    static void initialize() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    public OficiuRepositoryHibernate(Properties properties){
        System.out.println("OficiuRepositoryHibernate starts with properties " + properties);
    }

    @Override
    public void save(Oficiu entity) {

    }

    @Override
    public void update(String s, Oficiu entity) {

    }

    @Override
    public void delete(String s) {

    }

    @Override
    public Oficiu findOne(String s) {
        return null;
    }

    public Oficiu findByUserPass(String username, String password){
        initialize();
        Oficiu oficiu = new Oficiu();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String queryString = "from Oficiu o where o.username like ? and o.password like ?";
                oficiu = (Oficiu) session.createQuery(queryString)
                        .setString(0, username)
                        .setString(1, password)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            } finally {
                close();
            }
        }
        System.out.println(oficiu);
        return oficiu;
    }

    @Override
    public Iterable<Oficiu> findAll() {
        initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String queryString = "from Oficiu";
                List<Oficiu> listaOficiu =
                        session.createQuery(queryString, Oficiu.class)
                        .list();
                tx.commit();
                return listaOficiu;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            } finally {
                close();
            }
        }
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
