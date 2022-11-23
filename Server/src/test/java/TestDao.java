import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.menegment.dao.UserDao;
import org.menegment.models.User;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestDao {

    private boolean isTrue;

    private UserDao userDao =  new UserDao();;

    @Test
    @Order(2)
    public void getresult() {
        User user = new User();
        UserDao userDao = new UserDao();
        user = userDao.findEntity("login");
        System.out.println(user);
        System.out.println(user.getDepartmentList());
        System.out.println(user.getWorkerList());
    }

    @Test
    @Order(1)
    public void update()
    {
        User user = userDao.findEntity("A");
        user.setLogin("AAAAAAAAA");
        userDao.updateEntity(user);
        System.out.println(user);
    }
}