package blog.open1111.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import blog.open1111.entity.Manager;
import blog.open1111.service.ManagerService;
import blog.open1111.util.Md5Util;
import blog.open1111.util.ResponseUtil;
import net.sf.json.JSONObject;

/**
 * 管理员Controller类
 * @author user
 *
 */
@Controller
@RequestMapping("/manager2")
public class ManagerController {

	@Resource
	private ManagerService managerService;
	
	/**
	 * 用户登录
	 * @param manager
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public String login(Manager manager,HttpServletResponse response)throws Exception{
		Subject subject=SecurityUtils.getSubject();
		UsernamePasswordToken token=new UsernamePasswordToken(manager.getUserName(), Md5Util.md5(manager.getPassword(), Md5Util.SALT));
		JSONObject result=new JSONObject();
		try{
			subject.login(token);	
			result.put("success", true);
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("errorInfo", "用户名或者密码错误！");
		}
		ResponseUtil.write(response, result);
		return null;
	}
}
