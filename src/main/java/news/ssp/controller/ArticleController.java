package news.ssp.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import news.ssp.entity.Article;
import news.ssp.lucene.ArticleIndex;
import news.ssp.service.ArticleService;
import news.ssp.util.StringUtil;

/**
 * 新闻Controller类
 * @author user
 *
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

	@Resource
	private ArticleService articleService;
	private ArticleIndex articleIndex=new ArticleIndex();
	
	@RequestMapping("/{id}")
	public ModelAndView details(@PathVariable("id") Integer id,HttpServletRequest request)throws Exception{
		ModelAndView mav=new ModelAndView();
		Article article=articleService.findById(id);
		article.setClickHit(article.getClickHit()+1);
		articleService.update(article); // 新闻点击次数+1
		String tags=article.getTags();
		if(StringUtil.isNotEmpty(tags)){
			String arr[]=tags.split(" ");
			mav.addObject("tags", StringUtil.filterWhite(Arrays.asList(arr)));
		}else{
			mav.addObject("tags", tags);
		}
		mav.addObject("article", article);
		mav.addObject("pageTitle",article.getTitle()+"_技术博客网");
		mav.addObject("pageCode", this.getUpAndDownPageCode(articleService.getLastArticle(id), articleService.getNextArticle(id), request.getServletContext().getContextPath()));
		mav.addObject("mainPage", "foreground/article/view.jsp");
		mav.setViewName("mainTemp");
		return mav;
	}
	
	
	/**
	 * 获取上一篇和下一篇代码
	 * @param lastArticle
	 * @param netArticle
	 * @param projectContext
	 * @return
	 */
	public String getUpAndDownPageCode(Article lastArticle,Article netArticle,String projectContext){
		StringBuffer pageCode=new StringBuffer();
		if(lastArticle==null || lastArticle.getId()==null){
			pageCode.append("<p>上一篇：没有了</p>");
		}else{
			pageCode.append("<p>上一篇：<a href='"+projectContext+"/article/"+lastArticle.getId()+".html'>"+lastArticle.getTitle()+"</a></p>");
		}
		if(netArticle==null || netArticle.getId()==null){
			pageCode.append("<p>下一篇：没有了</p>");
		}else{
			pageCode.append("<p>下一篇：<a href='"+projectContext+"/article/"+netArticle.getId()+".html'>"+netArticle.getTitle()+"</a></p>");
		}
		return pageCode.toString();
	}
	/**
	 * 根据关键字查询相关博客信息
	 * @param q
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/q")
	public ModelAndView search(@RequestParam(value="q",required=false) String q,@RequestParam(value="page",required=false) String page,HttpServletRequest request)throws Exception{
		int pageSize=3;
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		ModelAndView mav=new ModelAndView();
		mav.addObject("pageTitle", "搜索关键字'"+q+"'结果页面_技术博客网");
		mav.addObject("mainPage", "foreground/article/result.jsp");
		List<Article> articleList=articleIndex.searchArticle(q);
		Integer toIndex=articleList.size()>=Integer.parseInt(page)*pageSize?Integer.parseInt(page)*pageSize:articleList.size();
		mav.addObject("articleList",articleList.subList((Integer.parseInt(page)-1)*pageSize, toIndex));
		mav.addObject("pageCode",this.genUpAndDownPageCode(Integer.parseInt(page),articleList.size(), q, pageSize, request.getServletContext().getContextPath()));
		mav.addObject("q", q);
		mav.addObject("resultTotal",articleList.size());
		mav.setViewName("mainTemp");
		return mav;
	}
	/**
	 * 获取上一页，下一页代码 
	 * @param page
	 * @param totalNum
	 * @param q
	 * @param pageSize
	 * @param projectContext
	 * @return
	 */
	private String genUpAndDownPageCode(Integer page,Integer totalNum,String q,Integer pageSize,String projectContext){
		long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		StringBuffer pageCode=new StringBuffer();
		if(totalPage==0){
			return "";
		}else{
			pageCode.append("<nav>");
			pageCode.append("<ul class='pager'>");
			if(page>1){
				pageCode.append("<li><a href='"+projectContext+"/article/q.html?page="+(page-1)+"&q="+q+"'>上一页</a></li>");
			}else{
				pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
			}
			if(page<totalPage){
				pageCode.append("<li><a href='"+projectContext+"/article/q.html?page="+(page+1)+"&q="+q+"'>下一页</a></li>");
			}else{
				pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
			}
			pageCode.append("</ul>");
			pageCode.append("</nav>");
		}
		return pageCode.toString();
	}
	
}
