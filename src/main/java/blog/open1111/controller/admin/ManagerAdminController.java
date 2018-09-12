package blog.open1111.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import blog.open1111.entity.Manager;
import blog.open1111.service.ManagerService;
import blog.open1111.util.Md5Util;
import blog.open1111.util.ResponseUtil;
import net.sf.json.JSONObject;

/**
 * 后台管理管理员Controller类
 * @author user
 *
 */
@Controller
@RequestMapping("/admin/manager")
public class ManagerAdminController {

	@Resource
	private ManagerService managerService;
	
	/**
	 * 修改管理员密码
	 * @param newPassword
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modifyPassword")
	public String modifyPassword(String newPassword,HttpServletResponse response)throws Exception{
		Manager manager=new Manager();
		manager.setPassword(Md5Util.md5(newPassword, Md5Util.SALT));
		int resultTotal=managerService.update(manager);
		JSONObject result=new JSONObject();
		if(resultTotal>0){
			result.put("success", true);
		}else{
			result.put("success", false);
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
	public String logout()throws Exception{
		SecurityUtils.getSubject().logout();
		return "redirect:/login.jsp";
	}
}
