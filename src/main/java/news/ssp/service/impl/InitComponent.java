package news.ssp.service.impl;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import news.ssp.entity.ArcType;
import news.ssp.entity.Article;
import news.ssp.entity.Link;
import news.ssp.service.ArcTypeService;
import news.ssp.service.ArticleService;
import news.ssp.service.LinkService;

/**
 * 初始化组件
 * @author user
 *
 */
@Component("initComponent")
public class InitComponent implements ServletContextListener,ApplicationContextAware{

	private static ApplicationContext applicationContext;

	/**
	 * 刷新系统缓存
	 * @param application
	 */
	public void refreshSystem(ServletContext application){
		ArcTypeService arcTypeService=(ArcTypeService) applicationContext.getBean("arcTypeService");
		List<ArcType> arcTypeList=arcTypeService.list(null);
		application.setAttribute("arcTypeList", arcTypeList);
		
		LinkService linkService=(LinkService) applicationContext.getBean("linkService");
		List<Link> linkList=linkService.list(null);
		application.setAttribute("linkList", linkList);
		
		ArticleService articleService=(ArticleService) applicationContext.getBean("articleService");
		List<Article> hotArticleList=articleService.getHot();
		application.setAttribute("hotArticleList", hotArticleList);
	}
	
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		ServletContext application=sce.getServletContext();
		refreshSystem(application);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext=applicationContext;
	}
}
