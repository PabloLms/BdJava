package util;

import dto.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EntityManagerUtil {

  public static EntityManager buildEntityManager() {
    return Persistence.createEntityManagerFactory("default").createEntityManager();
  }

  public static void main(String[] args) {
    EntityManager em = EntityManagerUtil.buildEntityManager();
//------------------ Create User---------------------
    User user = User.builder().email("email@test.com").password("password").build();
    System.out.println(new UtilQuery<>(User.class).save(user, em));
//--------------------------------------------------

//------------------Find User----------------------
    System.out.println(new UtilQuery<User, Long>(User.class).find(1L, em));
//-------------------------------------------------
//-----------------Update User------------------
    new UtilQuery<User, Long>(User.class).find(1L, em).setEmail("update@test.com");
//----------------------------------------

//-----------------List Users----------------------
    List<User> users = new UtilQuery<User, Long>(User.class).getList(em);
    users.forEach(System.out::println);
//-------------------------------------------------
    em.close();
  }
}
