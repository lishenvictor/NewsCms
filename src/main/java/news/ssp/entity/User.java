package news.ssp.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户实体
 * @author Administrator
 *
 */

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id; // 编号

	private String userName; // 用户名

	private String password; // 密码

	private String email; // 验证邮箱地址

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.trim();
	}


	
}
