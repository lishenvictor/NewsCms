package news.ssp.lucene;

import java.io.StringReader;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import news.ssp.entity.Article;
import news.ssp.util.DateUtil;
import news.ssp.util.StringUtil;

/**
 * 博客索引类
 * @author Administrator
 *
 */
public class ArticleIndex {

	private Directory dir;
	
	/**
	 * 获取IndexWriter实例
	 * @return
	 * @throws Exception
	 */
	private IndexWriter getWriter()throws Exception{
		dir=FSDirectory.open(Paths.get("E://project/lucene"));
		System.out.println("1234"+dir);
		SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
		IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
		IndexWriter writer=new IndexWriter(dir, iwc);
		return writer;
	}
	
	/**
	 * 添加博客索引
	 * @param article
	 * @throws Exception
	 */
	public void addIndex(Article article)throws Exception{
		System.out.println("addIndex");
		IndexWriter writer=getWriter();
		Document doc=new Document();
		System.out.println(String.valueOf(article.getId()));
		doc.add(new StringField("id",String.valueOf(article.getId()),Field.Store.YES));
		System.out.println(article.getTitle());
		doc.add(new TextField("title",article.getTitle(),Field.Store.YES));
		System.out.println(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
		doc.add(new StringField("releaseDate",DateUtil.formatDate(new Date(), "yyyy-MM-dd"),Field.Store.YES));
		System.out.println(article.getContentNoTag());
		doc.add(new TextField("content",article.getContentNoTag(),Field.Store.YES));
		writer.addDocument(doc);
		writer.close();
	}
	
	/**
	 * 删除指定博客的索引
	 * @param articleId
	 * @throws Exception
	 */
	public void deleteIndex(String articleId)throws Exception{
			IndexWriter writer=getWriter();
			writer.deleteDocuments(new Term("id",articleId));
			writer.forceMergeDeletes(); // 强制删除
			writer.commit();
			writer.close();
		}
	
	/**
	 * 更新博客索引
	 * @param article
	 * @throws Exception
	 */
	public void updateIndex(Article article)throws Exception{
		IndexWriter writer=getWriter();
		Document doc=new Document();

		doc.add(new StringField("id",String.valueOf(article.getId()),Field.Store.YES));
		doc.add(new TextField("title",article.getTitle(),Field.Store.YES));
		doc.add(new StringField("releaseDate",DateUtil.formatDate(new Date(), "yyyy-MM-dd"),Field.Store.YES));
		doc.add(new TextField("content",article.getContentNoTag(),Field.Store.YES));
		writer.updateDocument(new Term("id",String.valueOf(article.getId())), doc);
		writer.close();
	}
	
	/**
	 * 查询博客信息
	 * @param q
	 * @return
	 * @throws Exception
	 */
	public List<Article> searchArticle(String q)throws Exception{
		dir=FSDirectory.open(Paths.get("E://project/lucene"));
		IndexReader reader=DirectoryReader.open(dir);
		IndexSearcher is=new IndexSearcher(reader);
		BooleanQuery.Builder booleanQuery=new BooleanQuery.Builder();
		SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
		QueryParser parser=new QueryParser("title", analyzer);
		Query query=parser.parse(q);
		
		QueryParser parser2=new QueryParser("content", analyzer);
		Query query2=parser2.parse(q);
		
		booleanQuery.add(query, BooleanClause.Occur.SHOULD);
		booleanQuery.add(query2, BooleanClause.Occur.SHOULD);
		
		TopDocs hits=is.search(booleanQuery.build(), 100);
		QueryScorer scorer=new QueryScorer(query);
		Fragmenter fragmenter=new SimpleSpanFragmenter(scorer);
		SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='red'>", "</font></b>");
		Highlighter highlighter=new Highlighter(simpleHTMLFormatter, scorer);
		highlighter.setTextFragmenter(fragmenter);
		
		List<Article> articleList=new LinkedList<Article>();
		for(ScoreDoc scoreDoc:hits.scoreDocs){
			Document doc=is.doc(scoreDoc.doc);
			Article article=new Article();
			article.setId(Integer.parseInt(doc.get("id")));
			article.setReleaseDateStr(doc.get("releaseDate"));
			String title=doc.get("title");
			String content=StringEscapeUtils.escapeHtml(doc.get("content"));
			if(title!=null){
				TokenStream tokenStream=analyzer.tokenStream("title", new StringReader(title));
				String hTitle=highlighter.getBestFragment(tokenStream, title);
				if(StringUtil.isEmpty(hTitle)){
					article.setTitle(title);
				}else{
					article.setTitle(hTitle);
				}
			}
			
			if(content!=null){
				TokenStream tokenStream=analyzer.tokenStream("content", new StringReader(content));
				String hContent=highlighter.getBestFragment(tokenStream, content);
				if(StringUtil.isEmpty(hContent)){
					if(content.length()<=200){
						article.setContent(content);						
					}else{
						article.setContent(content.substring(0, 200));	
					}
				}else{
					article.setContent(hContent);
				}
			}
			articleList.add(article);
		}
		return articleList;
	}
	
}
