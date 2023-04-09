package application;

import application.Dto.Message;
import java.util.List;
import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class Main {

  public static SessionFactory buildSessionFactory() {
    StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
    serviceRegistryBuilder
//        .applySetting("hibernate.connection.datasource", "myDS")
        .applySetting("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
        .applySetting("hibernate.connection.url", "jdbc:mysql://localhost/Hibernate")
        .applySetting("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
        .applySetting("hibernate.connection.username", "root")
        .applySetting("hibernate.connection.password", "password")
        .applySetting("hibernate.format_sql", "true")
        .applySetting("hibernate.use_sql_comments", "true")
        .applySetting("hibernate.hbm2ddl.auto", "create");
    ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();

    MetadataSources metadataSources = new MetadataSources(serviceRegistry);
    metadataSources.addAnnotatedClass(application.Dto.Message.class);
    MetadataBuilder metadataBuilder = metadataSources.getMetadataBuilder();
    Metadata metadata = metadataBuilder.build();
    return metadata.buildSessionFactory();
  }

  public static void main(String[] args) {
    SessionFactory sessionFactory = Main.buildSessionFactory();
    save(Message.builder().text("test").build(), sessionFactory);
    save(Message.builder().text("test2").build(), sessionFactory);
    update(Message.builder().text("update").build(), 1L, sessionFactory);
    Objects.requireNonNull(list(sessionFactory)).forEach(System.out::println);
    delete(1L, sessionFactory);
  }

  public static void save(final Message message, final SessionFactory sessionFactory) {
    try (Session session = sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();
      session.persist(message);
      System.out.println("Success Stored");
      transaction.commit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void update(final Message message, final Long id,
      final SessionFactory sessionFactory) {
    try (Session session = sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();
      Message messageUpdate = session.find(Message.class, id);
      if (Objects.nonNull(messageUpdate)) {
        messageUpdate.setText(message.getText());
        session.merge(messageUpdate);
        transaction.commit();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void delete(final Long id, final SessionFactory sessionFactory) {
    try (Session session = sessionFactory.openSession()) {
      Message message = session.find(Message.class, id);
      if (Objects.nonNull(message)) {
        Transaction transaction = session.beginTransaction();
        session.remove(message);
        transaction.commit();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static List<Message> list(final SessionFactory sessionFactory) {
    try (Session session = sessionFactory.openSession()) {
      return session.createQuery("FROM Message", Message.class).list();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }
}
