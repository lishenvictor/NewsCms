package news.ssp.crawler;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Status;
import news.ssp.util.DateUtil;
import news.ssp.util.DbUtil;
import news.ssp.util.PropertiesUtil;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NewsCrawlerService {

	private static Logger logger = Logger.getLogger(NewsCrawlerService.class);

	private  String URL = "";

	private static Connection con = null;

	private static CacheManager manager = null; // cache������
	private static Cache cache = null; // cache�������

	private int count = 0;
	/**
	 * ������ҳ
	 */
	private void parseHomePage(String type) {
//		while (true) {
			if("1".equals(type)) {
				URL = PropertiesUtil.getValue2("wangyiUrl");
			}else if("2".equals(type)){
				URL = PropertiesUtil.getValue2("tengxunUrl");
			}else{
				URL = PropertiesUtil.getValue2("souhuUrl");
			}
			logger.info("��ʼ��ȡ" + URL + "��ҳ");
			System.setProperty(CacheManager.ENABLE_SHUTDOWN_HOOK_PROPERTY, "true");
			CacheManager cm = new CacheManager(PropertiesUtil.getValue("cacheFilePath"));
			manager = CacheManager.getCacheManager(cm.getName());
			if (manager == null) {
				manager = CacheManager.create();
			}
			cache = manager.getCache("news");
			cache.flush();
			CloseableHttpClient httpClient = HttpClients.createDefault(); // ��ȡHttpClientʵ��
			HttpGet httpget = new HttpGet(URL); // ����httpgetʵ��
			RequestConfig config = RequestConfig.custom().setSocketTimeout(100000) // ���ö�ȡ��ʱʱ��
					.setConnectTimeout(5000) // �������ӳ�ʱʱ��
					.build();
			httpget.setConfig(config);
			CloseableHttpResponse response = null;
			try {
				response = httpClient.execute(httpget);
			} catch (ClientProtocolException e) {
				logger.error(URL + "-ClientProtocolException", e);
			} catch (IOException e) {
				logger.error(URL + "-IOException", e);
			}
			if (response != null) {
				HttpEntity entity = response.getEntity(); // ��ȡ����ʵ��
				// �жϷ���״̬�Ƿ�Ϊ200
				if (response.getStatusLine().getStatusCode() == 200) {
					String webPageContent = null;
					try {
						webPageContent = EntityUtils.toString(entity, "utf-8");
						parseHomeWebPage(webPageContent,type);
					} catch (ParseException e) {
						logger.error(URL + "-ParseException", e);
					} catch (IOException e) {
						logger.error(URL + "-IOException", e);
					}
				} else {
					logger.error(URL + "-����״̬��200");
				}
			} else {
				logger.error(URL + "-���ӳ�ʱ");
			}
			try {
				if (response != null) {
					response.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (Exception e) {
				logger.error(URL + "Exception", e);
			}
			if (cache.getStatus() == Status.STATUS_ALIVE) {
				cache.flush(); // �ѻ���д���ļ�
			}
			manager.shutdown();
//			try {
//				Thread.sleep(1 * 60 * 1000); // ÿ��10����ץȡһ����ҳ����
//			} catch (InterruptedException e) {
//				logger.error("InterruptedException", e);
//			}
			logger.info("������ȡ" + URL + "��ҳ");
		}
//	}

	/**
	 * ������ҳ���� ��ȡ����link
	 * 
	 * @param webPageContent
	 */
	private void parseHomeWebPage(String webPageContent,String type) {
		if ("".equals(webPageContent)) {
			return;
		}
		Document doc = Jsoup.parse(webPageContent);
		Elements links = null;
		if("1".equals(type)) {
			links = doc.select(PropertiesUtil.getValue2("wangyilink"));
		}else if("2".equals(type)){
			links = doc.select(PropertiesUtil.getValue2("tengxunlink"));
		}else{
			links = doc.select(PropertiesUtil.getValue2("souhulink"));
		}

		for (int i = 0; i < links.size(); i++) {
			Element link = links.get(i);
			String url = link.attr("href");
			if(URL.contains("http://news.sohu.com/")){
				if(url != null && url != ""){
					if(!url.contains("http:") && !url.contains("https:")){
						url = "http:" + url;
					}
				}
			}
			System.out.println(url);
			cache.flush();
			if (cache.get(url) != null) { // ��������д��ھͲ�����
				logger.info(url + "-�����д���");
				continue;
			}
			parseBlogLink(url,type);
		}

	}

	/**
	 * �����������ӵ�ַ ��ȡ��������
	 * 
	 * @param link
	 */
	private void parseBlogLink(String link,String type) {
		logger.info("��ʼ��ȡ" + link + "��ҳ");
		CloseableHttpClient httpClient = HttpClients.createDefault(); // ��ȡHttpClientʵ��
		HttpGet httpget = new HttpGet(link); // ����httpgetʵ��
		RequestConfig config = RequestConfig.custom().setSocketTimeout(100000) // ���ö�ȡ��ʱʱ��
				.setConnectTimeout(5000) // �������ӳ�ʱʱ��
				.build();
		httpget.setConfig(config);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpget);
		} catch (ClientProtocolException e) {
			logger.error(URL + "-ClientProtocolException", e);
		} catch (IOException e) {
			logger.error(URL + "-IOException", e);
		}
		if (response != null) {
			HttpEntity entity = response.getEntity(); // ��ȡ����ʵ��
			// �жϷ���״̬�Ƿ�Ϊ200
			if (response.getStatusLine().getStatusCode() == 200) {
				String blogContent = null;
				try {
					blogContent = EntityUtils.toString(entity, "utf-8");
					parseBlogPage(blogContent, link, type);
				} catch (ParseException e) {
					logger.error(URL + "-ParseException", e);
				} catch (IOException e) {
					logger.error(URL + "-IOException", e);
				}
			} else {
				logger.error(URL + "-����״̬��200");
			}
		} else {
			logger.error(URL + "-���ӳ�ʱ");
		}
		try {
			if (response != null) {
				response.close();
			}
			if (httpClient != null) {
				httpClient.close();
			}
		} catch (Exception e) {
			logger.error(URL + "Exception", e);
		}
		logger.info("������ȡ" + link + "��ҳ");
	}

	/**
	 * �����������ݣ���ȡ��Ч��Ϣ
	 * 
	 * @param blogContent
	 * @param link
	 */
	private void parseBlogPage(String blogContent, String link, String type) {
		if ("".equals(blogContent)) {
			return;
		}
		Document doc = Jsoup.parse(blogContent);
		Elements titleElements = null;
		if("1".equals(type)) {
			titleElements = doc.select(PropertiesUtil.getValue2("wangyititle")); // ��ȡ���ű���
		}else if("2".equals(type)){
			titleElements = doc.select(PropertiesUtil.getValue2("tengxuntitle")); // ��ȡ���ű���
		}else{
			titleElements = doc.select(PropertiesUtil.getValue2("souhutitle")); // ��ȡ���ű���
		}
		if (titleElements.size() == 0) {
			logger.error(link + "-δ��ȡ�����ű���");
			return;
		}
		String title = titleElements.get(0).text();
		System.out.println("���ű��⣺" + title);
		Elements contentElements = null;
		if("1".equals(type)) {
			contentElements = doc.select(PropertiesUtil.getValue2("wangyicontent")); // ��ȡ��������
		}else if("2".equals(type)){
			contentElements = doc.select(PropertiesUtil.getValue2("tengxuncontent")); // ��ȡ��������
		}else{
			contentElements = doc.select(PropertiesUtil.getValue2("souhucontent")); // ��ȡ��������
		}
		Elements imgElements = contentElements.select("img"); // ��ȡ����ͼƬԪ��
		if (contentElements.size() == 0) {
			logger.error(link + "-δ��ȡ����������");
			return;
		}
		String content = contentElements.get(0).html();
		System.out.println("�������ݣ�" + content);

//		List<String> imgUrlList = new LinkedList<String>();
//		for (int i = 0; i < imgElements.size(); i++) {
//			Element imgEle = imgElements.get(i);
//			String url = imgEle.attr("src");
//			imgUrlList.add(url);
//			System.out.println(url);
//		}

//		if (imgUrlList.size() > 0) {
//			Map<String, String> replaceImgMap = downLoadImages(imgUrlList);
//			String newContent = replaceWebPageImages(content, replaceImgMap);
//			content = newContent;
//		}
			try {
				String currentDatePath = DateUtil.getCurrentDatePath();
				InputStream inputStream = new ByteArrayInputStream(getTextFromHtml(content).getBytes());
				if("1".equals(type)) {
					FileUtils.copyToFile(inputStream, new File(
							PropertiesUtil.getValue2("wangyifilePath") + currentDatePath + "/" + title.trim().replace(":","-")
									.replace("?"," ")+ ".txt"));
				}else if("2".equals(type)){
					FileUtils.copyToFile(inputStream, new File(
							PropertiesUtil.getValue2("tengxunfilePath") + currentDatePath + "/" + title.trim().replace(":","-")
									.replace("?"," ")+ ".txt"));
				}else{
					FileUtils.copyToFile(inputStream, new File(
							PropertiesUtil.getValue2("souhufilePath") + currentDatePath + "/" + title.trim().replace(":","-")
									.replace("?"," ")+ ".txt"));
				}

			}catch (Exception e){
				e.printStackTrace();;
			}



		// �������ݿ�
		String sql = "insert into t_article values(null,?,?,null,now(),0,0,null,?,0,null,?,?)";
		try {
			String source = "";
			if("1".equals(type)) {
				source= PropertiesUtil.getValue2("wangyisource");
			}else if("2".equals(type)){
				source= PropertiesUtil.getValue2("tengxunsource");
			}else{
				source= PropertiesUtil.getValue2("souhusource");
			}
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, link);
			pstmt.setString(4,source);
			pstmt.setString(5,getTextFromHtml(content));
			if (pstmt.executeUpdate() == 1) {
				logger.info(link + "-�ɹ��������ݿ�");
				cache.put(new net.sf.ehcache.Element(link, link));
				cache.flush();
				logger.info(link + "-�Ѽ��뻺��");
				count++;
			} else {
				logger.info(link + "-�������ݿ�ʧ��");
			}
		} catch (SQLException e) {
			logger.error("SQLException", e);
		}
	}

//	/**
//	 * ��ԭ������ҳͼƬ��ַ���ɱ����µ�
//	 *
//	 * @param content
//	 * @param replaceImgMap
//	 * @return
//	 */
//	private static String replaceWebPageImages(String content, Map<String, String> replaceImgMap) {
//		for (String url : replaceImgMap.keySet()) {
//			String newPath = replaceImgMap.get(url);
//			content = content.replace(url, newPath);
//		}
//		return content;
//	}

//	/**
//	 * ����ͼƬ������
//	 *
//	 * @param imgUrlList
//	 * @return
//	 */
//	private static Map<String, String> downLoadImages(List<String> imgUrlList) {
//		Map<String, String> replaceImgMap = new HashMap<String, String>();
//
//		RequestConfig config = RequestConfig.custom().setSocketTimeout(10000) // ���ö�ȡ��ʱʱ��
//				.setConnectTimeout(5000) // �������ӳ�ʱʱ��
//				.build();
//		CloseableHttpClient httpClient = HttpClients.createDefault(); // ��ȡHttpClientʵ��
//		for (int i = 0; i < imgUrlList.size(); i++) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			String url = imgUrlList.get(i);
//			logger.info("��ʼ��ȡ" + url + "ͼƬ");
//
//			CloseableHttpResponse response = null;
//
//			try {
//				HttpGet httpget = new HttpGet(url); // ����httpgetʵ��
//				httpget.setConfig(config);
//				response = httpClient.execute(httpget);
//			} catch (ClientProtocolException e) {
//				logger.error(url + "-ClientProtocolException");
//			} catch (IOException e) {
//				logger.error(url + "-IOException");
//			}
//			if (response != null) {
//				HttpEntity entity = response.getEntity(); // ��ȡ����ʵ��
//				// �жϷ���״̬�Ƿ�Ϊ200
//				if (response.getStatusLine().getStatusCode() == 200) {
//					try {
//						InputStream inputStream = entity.getContent();
//						String imageType = entity.getContentType().getValue();
//						String urlB = imageType.split("/")[1];
//						String uuid = UUID.randomUUID().toString();
//						String currentDatePath = DateUtil.getCurrentDatePath();
//						String newPath = PropertiesUtil.getValue("imagePath") + currentDatePath + "/" + uuid + "."
//								+ urlB;
//						FileUtils.copyToFile(inputStream, new File(
//								PropertiesUtil.getValue("imageFilePath") + currentDatePath + "/" + uuid + "." + urlB));
//						replaceImgMap.put(url, newPath);
//					} catch (UnsupportedOperationException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				} else {
//					logger.error("����״̬��200");
//				}
//			} else {
//				logger.error("���ӳ�ʱ");
//			}
//			try {
//				if (response != null) {
//					response.close();
//				}
//			} catch (Exception e) {
//				logger.error("Exception", e);
//			}
//			logger.info("������ȡ" + url + "ͼƬ");
//		}
//
//		return replaceImgMap;
//	}

	public Map<String,Object> start(String type) {
		Map<String,Object> map = new HashMap<String, Object>();
		DbUtil dbUtil = new DbUtil();
		try {
			con = dbUtil.getCon();
		} catch (Exception e) {
			logger.error("�������ݿ�����ʧ��", e);
			map.put("result",false);
			return map;
		}
		parseHomePage(type);
		map.put("result",true);
		map.put("count",count);
		return map;
	}


    public String delHtmlTags(String htmlStr) {
        //����script��������ʽ��ȥ��js���Է�ֹע��
        String scriptRegex="<script[^>]*?>[\\s\\S]*?<\\/script>";
        //����style��������ʽ��ȥ��style��ʽ����ֹcss�������ʱֻ��ȡ��css��ʽ����
        String styleRegex="<style[^>]*?>[\\s\\S]*?<\\/style>";
        //����HTML��ǩ��������ʽ��ȥ����ǩ��ֻ��ȡ��������
        String htmlRegex="<[^>]+>";
        //����ո�,�س�,���з�,�Ʊ��
        String spaceRegex = "\\s*|\t|\r|\n";

        // ����script��ǩ
        htmlStr = htmlStr.replaceAll(scriptRegex, "");
        // ����style��ǩ
        htmlStr = htmlStr.replaceAll(styleRegex, "");
        // ����html��ǩ
        htmlStr = htmlStr.replaceAll(htmlRegex, "");
        // ���˿ո��
        htmlStr = htmlStr.replaceAll(spaceRegex, "");
        return htmlStr.trim(); // �����ı��ַ���
    }
    /**
     * ��ȡHTML�����������
     * @param htmlStr
     * @return
     */
    public String getTextFromHtml(String htmlStr){
		if(URL.contains("http://news.sohu.com/")) {
			if (htmlStr.contains("data-role=\"original-title\"")) {
				htmlStr = htmlStr.substring(htmlStr.indexOf("</p>") + 6);
			}
		}
        //ȥ��html��ǩ
        htmlStr = delHtmlTags(htmlStr);
        //ȥ���ո�" "
        htmlStr = htmlStr.replaceAll(" ","");
		htmlStr = htmlStr.replaceAll("�����Ѻ����鿴�������α༭��","");
		return htmlStr;
    }
}
