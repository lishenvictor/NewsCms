package blog.open1111.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * Md5加密工具类
 * @author user
 *
 */
public class Md5Util {

	public static final String SALT="lishenvictor";
	
	/**
	 * Md5加密
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
