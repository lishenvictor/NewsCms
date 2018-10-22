package news.ssp.controller;

import net.sf.json.JSONObject;
import news.ssp.entity.Manager;
import news.ssp.entity.User;
import news.ssp.service.ManagerService;
import news.ssp.service.UserService;
import news.ssp.util.Md5Util;
import news.ssp.util.ResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 后台管理管理员Controller类
 * @author user
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	/**
	 * 注册
	 * @param
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/register")
	public String register(User user, HttpServletResponse response)throws Exception{
		User user1=new User();
		JSONObject result=new JSONObject();
		if(userService.getByUserName(user.getUserName())!=null) {
			result.put("info", "用户名已存在！");
			result.put("success", false);
		}else {
			user1.setUserName(user.getUserName());
			user1.setPassword(Md5Util.md5(user.getPassword(), Md5Util.SALT));
			user1.setEmail(user.getEmail());
			int resultTotal = userService.add(user1);
			if (resultTotal > 0) {
				result.put("info", "注册成功！");
				result.put("success", true);
			} else {
				result.put("info", "注册失败！");
				result.put("success", false);
			}
		}
		ResponseUtil.write(response, result);
		return null;
	}


	/**
	 * 用户登录
	 * @param
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public String login(User user, HttpSession session, HttpServletResponse response)throws Exception{
		JSONObject result=new JSONObject();
		User currentUser = userService.getByUserNameAndPassWord(user.getUserName(),Md5Util.md5(user.getPassword(), Md5Util.SALT));
		if(currentUser!=null) {
			result.put("success", true);
			session.setAttribute("currentUser", currentUser);
		}else {
			result.put("success", false);
			result.put("info", "用户名或者密码错误！");
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 修改管理员密码
	 * @param
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modifyPassword")
	public String modifyPassword(String oldpassword,String password,HttpServletResponse response)throws Exception{
		User user=new User();
		user.setPassword(Md5Util.md5(password, Md5Util.SALT));
		int resultTotal=userService.update(user);
		JSONObject result=new JSONObject();
		if(resultTotal>0){
			result.put("success", true);
		}else{
			result.put("success", false);
			result.put("info", "修改密码失败！");
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 注销
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session)throws Exception{
		session.setAttribute("currentUser", null);
		return "redirect:/index.do";
	}
}
