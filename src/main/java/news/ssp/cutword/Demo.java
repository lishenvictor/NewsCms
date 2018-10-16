package news.ssp.cutword;

import com.sun.jna.Native;

import java.math.BigDecimal;

public class Demo {

    public static void main(String[] args) throws Exception {
        //��ʼ��
        CLibrary instance = (CLibrary) Native.loadLibrary(System.getProperty("user.dir")+"\\source\\NLPIR", CLibrary.class);
        int init_flag = instance.NLPIR_Init("", 1, "0");
        String resultString = null;
        if (0 == init_flag) {
            resultString = instance.NLPIR_GetLastErrorMsg();
            System.err.println("��ʼ��ʧ�ܣ�\n"+resultString);
            return;
        }

        String sInput = "�������Ƿֲ�ʽ���ݴ洢����Ե㴫�䡢��ʶ���ơ������㷨�ȼ��������������Ӧ��ģʽ��"
                + "��ν��ʶ������������ϵͳ��ʵ�ֲ�ͬ�ڵ�֮�佨�����Ρ���ȡȨ�����ѧ�㷨��"
                + "��������Blockchain���Ǳ��رҵ�һ����Ҫ�������������һ��ȥ���Ļ������ݿ⣬ͬʱ��Ϊ���رҵĵײ㼼����"
                + "��������һ��ʹ������ѧ������������������ݿ飬ÿһ�����ݿ��а�����һ�α��ر����罻�׵���Ϣ��������֤����Ϣ����Ч�ԣ���α����������һ�����顣";

        try {
            // ��Ƶ�Ӹߵ������򣬰����˷ֳ��������дʣ��������
            resultString = instance.NLPIR_WordFreqStat(sInput);
            System.out.println("��Ƶ�������� " + resultString + "\n");

            resultString = instance.NLPIR_ParagraphProcess(sInput, 1);
            System.out.println("�ִʽ��Ϊ��\n " + resultString);

            instance.NLPIR_AddUserWord("���Ȧ");
            instance.NLPIR_AddUserWord("�����խ");
            resultString = instance.NLPIR_ParagraphProcess(sInput, 1);
            System.out.println("�����û��ʵ��ִʽ��Ϊ��\n" + resultString);

            instance.NLPIR_DelUsrWord("�����խ");
            resultString = instance.NLPIR_ParagraphProcess(sInput, 1);
            System.out.println("ɾ���û��ʵ��ִʽ��Ϊ��\n" + resultString);

            instance.NLPIR_ImportUserDict(System.getProperty("user.dir")+"\\source\\userdic.txt");
            resultString = instance.NLPIR_ParagraphProcess(sInput, 1);
            System.out.println("�����û��ʵ��ļ���ִʽ��Ϊ��\n" + resultString);

            resultString = instance.NLPIR_GetKeyWords(sInput,10,false);
            System.out.println("�Ӷ�������ȡ�Ĺؼ��ʣ�\n" + resultString);

            resultString = instance.NLPIR_GetNewWords(sInput, 10, false);
            System.out.println("�´���ȡ���Ϊ��\n" + resultString);

            Double d = instance.NLPIR_FileProcess("D:\\1.txt", "D:\\2.txt", 1);

            System.out.println("���ļ����ݽ��зִʵ������ٶ�Ϊ�� " );
            if(d.isInfinite())
                System.out.println("�޽��");
            else{
                BigDecimal b = new BigDecimal(d);
                System.out.println(b.divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP)+"��");
            }
            resultString = instance.NLPIR_GetFileKeyWords("D:\\3.txt", 10,false);
            System.out.println("���ļ�����ȡ�ؼ��ʵĽ��Ϊ��\n" + resultString);

            instance.NLPIR_Exit();
        } catch (Exception e) {
            System.out.println("������Ϣ��");
            e.printStackTrace();
        }
    }
}
