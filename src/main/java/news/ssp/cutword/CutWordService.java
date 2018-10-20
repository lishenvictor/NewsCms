package news.ssp.cutword;

import java.math.BigDecimal;

import com.sun.jna.Native;

public class CutWordService {

	public static StringBuffer getCutWordResult(String content) throws Exception {

		StringBuffer cutWordResult = new StringBuffer();
		//初始化
		CLibrary instance = (CLibrary) Native.loadLibrary(System.getProperty("user.dir")+"\\source\\NLPIR", CLibrary.class);
		int init_flag = instance.NLPIR_Init("", 1, "0");
		String resultString = null;
		if (0 == init_flag) {
			resultString = instance.NLPIR_GetLastErrorMsg();
			System.err.println("初始化失败！\n"+resultString);
			return null;
		}

		try {
			// 词频从高到低排序，包括了分出来的所有词，甚至标点
			resultString = instance.NLPIR_WordFreqStat(content);
			System.out.println("词频排序结果： " + resultString + "\n");

			resultString = instance.NLPIR_ParagraphProcess(content, 1);
			System.out.println("分词结果为：\n " + resultString);

			instance.NLPIR_AddUserWord("金刚圈");
			instance.NLPIR_AddUserWord("左宽右窄");
			resultString = instance.NLPIR_ParagraphProcess(content, 1);
			System.out.println("增加用户词典后分词结果为：\n" + resultString);

			instance.NLPIR_DelUsrWord("左宽右窄");
			resultString = instance.NLPIR_ParagraphProcess(content, 1);
			System.out.println("删除用户词典后分词结果为：\n" + resultString);

			instance.NLPIR_ImportUserDict(System.getProperty("user.dir")+"\\source\\userdic.txt");
			resultString = instance.NLPIR_ParagraphProcess(content, 1);
			System.out.println("导入用户词典文件后分词结果为：\n" + resultString);

			resultString = instance.NLPIR_GetKeyWords(content,10,false);
			System.out.println("从段落中提取的关键词：\n" + resultString);

			resultString = instance.NLPIR_GetNewWords(content, 10, false);
			System.out.println("新词提取结果为：\n" + resultString);

			Double d = instance.NLPIR_FileProcess("D:\\1.txt", "D:\\2.txt", 1);

			System.out.println("对文件内容进行分词的运行速度为： " );
			if(d.isInfinite())
				System.out.println("无结果");
			else{
				BigDecimal b = new BigDecimal(d);
				System.out.println(b.divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP)+"秒");
			}
			resultString = instance.NLPIR_GetFileKeyWords("D:\\3.txt", 10,false);
			System.out.println("从文件中提取关键词的结果为：\n" + resultString);

			instance.NLPIR_Exit();
		} catch (Exception e) {
			System.out.println("错误信息：");
			e.printStackTrace();
		}
		return cutWordResult;
	}
}
