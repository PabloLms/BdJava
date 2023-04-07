package util;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class UtilQuery<T, ID> {

  private Class<T> clazz;
  private final String name;

  public UtilQuery(Class<T> clazz) {
    this.name = clazz.getTypeName();
    this.clazz = clazz;
  }

  public List<T> getList(final String entityUnity) {
    EntityManager entityManager = this.getEntityManager(entityUnity);
    Query query = entityManager.createQuery("select x from " + this.name + " x");
    return query.getResultList();
  }

  public T save(final String entityUnity, final T entity) {
    EntityManager entityManager = this.getEntityManager(entityUnity);
    try {
      entityManager.getTransaction().begin();
      T t = entityManager.merge(entity);
      entityManager.getTransaction().commit();
      return t;
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      entityManager.close();
    }
    return null;
  }

  public T find(final String entityUnity, final ID id) {
    EntityManager entityManager = this.getEntityManager(entityUnity);
    return entityManager.find(clazz, id);
  }

  public EntityManager getEntityManager(final String entity) {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory(entity);
    return factory.createEntityManager();
  }
}
