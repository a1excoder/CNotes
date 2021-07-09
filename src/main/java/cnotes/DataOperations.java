package cnotes;

import cnotes.model.Note;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class DataOperations {

    private Configuration configuration;
    private StandardServiceRegistry registry;
    private SessionFactory sessionFactory;

    public DataOperations() {
        configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Note.class);

        registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(registry);
    }

    public void closeSessionFactory() {
        sessionFactory.close();
    }

    public void newNote(String title, String query, Date date) {
        Note note = new Note(title, query, date);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(note);

        session.getTransaction().commit();
        session.close();
    }

    public void newNote(Note note) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(note);

        session.getTransaction().commit();
        session.close();
    }

    public boolean deleteNote(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Note note = session.find(Note.class, id);
        if (note != null) {
            session.delete(note);
            session.getTransaction().commit();

            session.close();
            return true;
        }

        session.close();
        return false;
    }

    public Note getNote(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Note note = session.find(Note.class, id);

        session.getTransaction().commit();
        session.close();

        return note;
    }

    public List<Note> getNotes() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createNativeQuery("SELECT * FROM `notes`", Note.class);
        List<Note> notes = query.getResultList();

        session.close();

        return notes;
    }

}
