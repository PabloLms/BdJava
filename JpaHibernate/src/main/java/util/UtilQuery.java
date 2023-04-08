package util;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class UtilQuery<T, ID> {

  private Class<T> clazz;
  private final String name;

  public UtilQuery(Class<T> clazz) {
    this.name = clazz.getTypeName();
    this.clazz = clazz;
  }

  public List<T> getList(final EntityManager entityManager) {
    Query query = entityManager.createQuery("select x from " + this.name + " x");
    return query.getResultList();
  }

  public T save(final T entity, final EntityManager entityManager) {
    try {
      entityManager.getTransaction().begin();
      T t = entityManager.merge(entity);
      entityManager.getTransaction().commit();
      return t;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public T find(final ID id, final EntityManager entityManager) {
    return entityManager.find(clazz, id);
  }

}
