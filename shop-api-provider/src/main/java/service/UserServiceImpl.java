package service;

import dao.UserDao;
import com.shop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.shop.service.user.UserService;

import java.util.List;

/**
 *
 *
 * @author:HM
 * @date: 17-12-08 10:18:34
 * @since v1.0.0
 */
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Override
	public User selectByUserPhone(String phone) throws Throwable {
		return userDao.selectByUserPhone(phone);
	}

	@Override
	public List<User> getUserInfo() throws Throwable {
		return userDao.getUserInfo();
	}
}
