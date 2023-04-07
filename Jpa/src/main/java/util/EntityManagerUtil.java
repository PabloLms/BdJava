package util;

import dto.User;
import java.util.List;

public class EntityManagerUtil {

  public static void main(String[] args) {
//------------------ Create User---------------------
    User user = User.builder().email("email@test.com").password("password").build();
    System.out.println(new UtilQuery<>(User.class).save("users", user));
//--------------------------------------------------

//------------------Find User----------------------
    System.out.println(new UtilQuery<User, Long>(User.class).find("users", 1L));
//-------------------------------------------------

//-----------------List Users----------------------
    List<User> users = new UtilQuery<User, Long>(User.class).getList("users");
    users.forEach(System.out::println);
//-------------------------------------------------
  }
}
