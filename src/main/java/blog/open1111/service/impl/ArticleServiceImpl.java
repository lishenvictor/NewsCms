package blog.open1111.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import blog.open1111.dao.ArticleDao;
import blog.open1111.entity.Article;
import blog.open1111.service.ArticleService;

/**
 * 帖子Service实现类
 * @author user
 *
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService{

	@Resource
	private ArticleDao articleDao;
	
	public Integer getNumByTypeId(Integer typeId) {
		return articleDao.getNumByTypeId(typeId);
	}

	public List<Article> list(Map<String, Object> map) {
		return articleDao.list(map);
	}

	public Long getTotal(Map<String, Object> map) {
		return articleDao.getTotal(map);
	}

	public List<Article> getHot() {
		return articleDao.getHot();
	}

	public Integer add(Article article) {
		return articleDao.add(article);
	}

	public Integer delete(Integer id) {
		return articleDao.delete(id);
	}

	public Integer update(Article article) {
		return articleDao.update(article);
	}

	public Article findById(Integer id) {
		return articleDao.findById(id);
	}

	public List<Article> list2(Map<String, Object> map) {
		return articleDao.list2(map);
	}

	public Long getTotal2(Map<String, Object> map) {
		return articleDao.getTotal2(map);
	}

	public Article getLastArticle(Integer id) {
		return articleDao.getLastArticle(id);
	}

	public Article getNextArticle(Integer id) {
		return articleDao.getNextArticle(id);
	}

}
