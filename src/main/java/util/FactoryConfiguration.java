package util;

import entity.Reservation;
import entity.Room;
import entity.Tenant;
import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;

    private final SessionFactory sessionFactory;

    private FactoryConfiguration() {
        StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
        standardServiceRegistryBuilder.loadProperties("hibernate.properties");
        MetadataSources metadataSources = new MetadataSources(standardServiceRegistryBuilder.build());
        metadataSources.
                addAnnotatedClass(Tenant.class).
                addAnnotatedClass(Room.class).
                addAnnotatedClass(Reservation.class).
                addAnnotatedClass(User.class);
        Metadata metadata = metadataSources.getMetadataBuilder().build();
        sessionFactory =  metadata.getSessionFactoryBuilder().build();
    }

    public static FactoryConfiguration getFactoryConfiguration() {
        return factoryConfiguration == null ? factoryConfiguration = new FactoryConfiguration() : factoryConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}