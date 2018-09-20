package news.ssp.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * properties������
 * @author user
 *
 */
public class PropertiesUtil {

	/**
	 * ����key��ȡvalueֵ
	 * @param key
	 * @return
	 */
	public static String getValue(String key){
		Properties prop=new Properties();
		InputStream in=new PropertiesUtil().getClass().getResourceAsStream("/crawler.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}
}