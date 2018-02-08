package com.shop.service.user;

import com.shop.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
	/**
	 * 根据电话查询用户
	 *
	 * @param phone
	 * @return
	 * @throws Throwable
	 * @author:HM
	 * @date: 17-12-14 17:09:52
	 * @since v1.0.0
	 */
	User selectByUserPhone(String phone) throws Throwable;

	/**
	 * 得到所有用户的信息
	 *
	 * @return
	 * @throws Throwable
	 * @author:HM
	 * @date: 17-12-14 17:10:20
	 * @since v1.0.0
	 */
	List<User> getUserInfo() throws Throwable;

}
