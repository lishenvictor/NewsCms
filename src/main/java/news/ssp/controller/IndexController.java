package news.ssp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import news.ssp.entity.Article;
import news.ssp.entity.PageBean;
import news.ssp.service.ArticleService;
import news.ssp.util.PageUtil;
import news.ssp.util.StringUtil;

/**
 * ��ҳController��
 * @author user
 *
 */
@Controller
@RequestMapping("/")
public class IndexController {

	@Resource
	private ArticleService articleService;
	
	/**
	 * ������ҳ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index")
	public ModelAndView index(@RequestParam(value="page",required=false)String page,@RequestParam(value="typeId",required=false)String typeId,HttpServletRequest request)throws Exception{
		ModelAndView mav=new ModelAndView();
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),10);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		map.put("typeId", typeId);
		List<Article> articleList=articleService.list2(map);
		Long total=articleService.getTotal2(map);
		mav.addObject("pageTitle", "����������");
		mav.addObject("mainPage", "foreground/article/list.jsp");
		mav.addObject("articleList", articleList);
		StringBuffer param=new StringBuffer();
		if(StringUtil.isNotEmpty(typeId)){
			param.append("typeId="+typeId+"&");
		}
		mav.addObject("pageCode", PageUtil.genPagination(request.getContextPath()+"/index.html", total, Integer.parseInt(page), 10, param.toString()));
		mav.setViewName("mainTemp");
		return mav;
	}
	
	/**
	 * ��������
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/aboutMe")
	public ModelAndView aboutMe()throws Exception{
		ModelAndView mav=new ModelAndView();
		mav.addObject("pageTitle", "��������_����������");
		mav.addObject("mainPage", "foreground/system/aboutMe.jsp");
		mav.setViewName("mainTemp");
		return mav;
	}
}
