package addressbook.appmanager;

import addressbook.model.ContactData;
import addressbook.model.GroupData;
import addressbook.model.Items;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class DbHelper {

    private final SessionFactory sessionFactory;

    public DbHelper() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();

        sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
    }

    public Items<GroupData> groups() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<GroupData> result = session.createQuery("from GroupData").list();
        session.getTransaction().commit();
        session.close();

        return (new Items<>(result));
    }

    public Items<ContactData> contacts() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ContactData> result = session.createQuery("from ContactData where deprecated = '0000-00-00'").list();
        session.getTransaction().commit();
        session.close();

        return (new Items<>(result));
    }

    public GroupData getGroup(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<GroupData> groups = session.createQuery("from GroupData").list();
        for (GroupData group : groups) {
            if (group.getId() == id) {
                session.getTransaction().commit();
                session.close();
                return group;
            }
        }
        session.getTransaction().commit();
        session.close();

        return null;
    }

    public ContactData getContact(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ContactData> contacts = session.createQuery("from ContactData where deprecated = '0000-00-00'").list();
        for (ContactData contact : contacts) {
            if (contact.getId() == id) {
                session.getTransaction().commit();
                session.close();
                return contact;
            }
        }
        session.getTransaction().commit();
        session.close();

        return null;
    }
}
