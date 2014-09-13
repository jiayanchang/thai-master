package id.or.linuxjak.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.magic.thai.db.domain.User;
import com.magic.thai.db.service.UserService;
import com.magic.thai.db.service.impl.BaseTest;

//@Ignore
public class UserServiceTest extends BaseTest {

	@Autowired
	UserService userService;

	@Test
	// @Rollback(value=false)
	public void testAddUser() {
		// TODO Auto-generated method stub
		User user = new User();

		user.setLoginName("tirta1");
		user.setPassword("gepeng");

		// userInfoService.create(ui);
		// userService.create(user);

	}

}
