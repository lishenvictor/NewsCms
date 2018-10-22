package news.ssp.service;

import news.ssp.entity.Manager;
import news.ssp.entity.User;

/**
 * 管理员Service接口
 * @author user
 *
 */
public interface UserService {

	public Integer add(User user);


	/**
	 * 通过用户名查找用户实体
	 * @param userName
	 * @return
	 */
	public User getByUserName(String userName);

	public User getByUserNameAndPassWord(String userName,String password);


	/**
	 * 更新管理员信息
	 * @param user
	 * @return
	 */
	public Integer update(User user);
}
