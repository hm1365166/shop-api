package dao;

import com.shop.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
	/**
	 * 登录查询
	 * @param userId
	 * @return
	 * @throws Throwable
	 * @author:HM
	 * @date: 2017/9/30 09:39:35
	 */
	User selectByUserPhone(String userId) throws Throwable;

	/**
	 * 得到用户信息
	 * @return
	 * @throws Throwable
	 * @author:HM
	 * @date: 2017/9/30 09:40:31
	 */
	List<User> getUserInfo() throws Throwable;

}
