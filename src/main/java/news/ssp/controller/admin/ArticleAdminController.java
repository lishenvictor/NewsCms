package news.ssp.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import news.ssp.util.ResponseUtil;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;

import news.ssp.entity.Article;
import news.ssp.entity.PageBean;
import news.ssp.lucene.ArticleIndex;
import news.ssp.service.ArticleService;
import news.ssp.service.impl.InitComponent;
import news.ssp.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 后台管理博客Controller类
 * @author user
 *
 */
@Controller
@RequestMapping("/admin/article")
public class ArticleAdminController {

	@Resource
	private ArticleService articleService;
	
	@Resource
	private InitComponent initComponent;
	
	private ArticleIndex articleIndex=new ArticleIndex();
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false); // 严格解析
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	
	/**
	 * 分页查询博客信息
	 * @param page
	 * @param rows
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,Article s_article,HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		map.put("title", StringUtil.formatLike(s_article.getTitle()));
		List<Article> articleList=articleService.list(map);
		Long total=articleService.getTotal(map);
		JSONObject result=new JSONObject();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONArray jsonArray=JSONArray.fromObject(articleList,jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 添加或者修改新闻
	 * @param article
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(Article article,HttpServletResponse response)throws Exception{
		int resultTotal=0; // 操作的记录条数
		if(article.getId()==null){
			if(article.getState()==1){
				article.setReleaseDate(new Date());				
			}
			resultTotal=articleService.add(article);
			
			articleIndex.addIndex(article);
		}else{
			if(article.getState()==1){
				article.setReleaseDate(new Date());
			}else{
				article.setReleaseDate(null);
			}		
			System.out.println("外面 "+articleService.findById(article.getId()).getReleaseDate());
			
			if(articleService.findById(article.getId()).getReleaseDate()==null){
				System.out.println("里面");
				System.out.println(article.getContentNoTag());
				articleIndex.addIndex(article);
				System.out.println(" 完成");
			}else{
				articleIndex.updateIndex(article);
			}
			resultTotal=articleService.update(article);
		}
		JSONObject result=new JSONObject();
		if(resultTotal>0){
			initComponent.refreshSystem(ContextLoader.getCurrentWebApplicationContext().getServletContext());
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 删除新闻信息
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="ids",required=false)String ids,HttpServletResponse response)throws Exception{
		System.out.println("delete");
		String []idsStr=ids.split(",");
		JSONObject result=new JSONObject();
		for(int i=0;i<idsStr.length;i++){
			System.out.println("12345"+articleService.findById(Integer.parseInt(idsStr[i])));
			if (articleService.findById(Integer.parseInt(idsStr[i])).getState() == 1) {
				articleIndex.deleteIndex(idsStr[i]);
			}
			System.out.println("delete"+idsStr[i]);
			articleService.delete(Integer.parseInt(idsStr[i]));
		}
		initComponent.refreshSystem(ContextLoader.getCurrentWebApplicationContext().getServletContext());
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 通过ID查找实体
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findById")
	public String findById(@RequestParam(value="id")Integer id,HttpServletResponse response)throws Exception{
		Article article=articleService.findById(id);
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject jsonObject=JSONObject.fromObject(article,jsonConfig);
		ResponseUtil.write(response, jsonObject);
		return null;
	}
}
