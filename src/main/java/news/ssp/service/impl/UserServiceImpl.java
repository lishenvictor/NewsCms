package news.ssp.service.impl;

import news.ssp.dao.ManagerDao;
import news.ssp.dao.UserDao_1;
import news.ssp.entity.Manager;
import news.ssp.entity.User;
import news.ssp.service.ManagerService;
import news.ssp.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 管理员Service实现类
 * @author user
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao_1 userDao;

	public Integer add(User user) {
		return userDao.add(user);
	}

	public User getByUserName(String userName) {
		return userDao.getByUserName(userName);
	}

	public User getByUserNameAndPassWord(String userName,String password) {
		return userDao.getByUserNameAndPassWord(userName,password);
	}


	public Integer update(User user) {
		return userDao.update(user);
	}
}
