package news.ssp.controller.admin;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import news.ssp.crawler.NewsCrawlerService;
import news.ssp.entity.ArcType;
import news.ssp.entity.PageBean;
import news.ssp.service.ArcTypeService;
import news.ssp.service.ArticleService;
import news.ssp.service.impl.InitComponent;
import news.ssp.util.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理博客类别Controller类
 * @author user
 *
 */
@Controller
@RequestMapping("/admin/crawler")
public class CrawlerAdminController {

	@Resource
	private ArcTypeService arcTypeService;
	
	@Resource
	private ArticleService articleService;
	
	@Resource
	private InitComponent initComponent;

	/**
	 * 删除博客类别信息
	 * @param type
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getNews")
	public String getNews(@RequestParam(value="type")String type,HttpServletResponse response)throws Exception{
		String newstype = type;
		JSONObject result=new JSONObject();
		NewsCrawlerService newsCrawlerService = new NewsCrawlerService();
		Map<String,Object> map = new HashMap<String, Object>();
		map = newsCrawlerService.start(newstype);
		if(map != null && map.size() > 0){
			if((Boolean) map.get("result") == true) {
				initComponent.refreshSystem(ContextLoader.getCurrentWebApplicationContext().getServletContext());
				result.put("success", true);
				result.put("count",String.valueOf((Integer) map.get("count")));
			}
		}else{
			result.put("success", false);

		}
		ResponseUtil.write(response, result);
		return null;
	}
}
