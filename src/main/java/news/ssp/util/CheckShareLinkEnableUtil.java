package news.ssp.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 检查分享链接是否有效
 * @author Administrator
 *
 */
public class CheckShareLinkEnableUtil {

	/**
	 * 检查链接是否有效
	 * @param link
	 * @return
	 */
	public static boolean check(String link)throws Exception{
		CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpGet httpget = new HttpGet(link); 
		CloseableHttpResponse response=httpClient.execute(httpget);
		HttpEntity entity=response.getEntity(); // 获取返回实体
		if(EntityUtils.toString(entity, "utf-8").contains("分享的文件已经被取消了")){
			return false;
		}else{
			return true;			
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(CheckShareLinkEnableUtil.check("https://pan.baidu.com/s/1ua8FwnRON8qzGwqVJqGhUA"));
	}
	
}
