package news.ssp.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * Md5���ܹ�����
 * @author user
 *
 */
public class Md5Util {

	public static final String SALT="lishenvictor";
	
	/**
	 * Md5����
	 * @param str
	 * @param salt
	 * @return
	 */
	public static String md5(String str,String salt){
		return new Md5Hash(str, salt).toString();
	}
	
	public static void main(String[] args) {
		System.out.println(md5("12345",SALT));
	}
}
