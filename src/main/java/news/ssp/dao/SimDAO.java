package news.ssp.dao;

import news.ssp.dbm.Dbmanage;
import news.ssp.rec_engin.map_mix;
import news.ssp.rec_engin.similarity;
import news.ssp.rec_engin.split;
import org.ansj.recognition.impl.StopRecognition;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
     * Created by sunyang on 2016/12/8.
 * 这个类用于处理计算相似度时需要连接数据库的操作.
 */
public class SimDAO {
    public Map<String, double[]> getUserVector(int id, String column, Set<String> expectedNature, StopRecognition filter) {//得到用户的向量空间,
        map_mix mix=new map_mix();
        similarity sim=new similarity();
        LikeDAO likeDAO=new LikeDAO();
        ArrayList<String> result=new ArrayList<String>();
        ArrayList<Integer> userLikeList=likeDAO.queryLike(id);
        Map<String, double[]> vectorSpace = new HashMap<String, double[]>();
        split sw=new split();

        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        Dbmanage dbmanage = new Dbmanage();

        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            for (int i = 0; i < userLikeList.size(); i++) {
                String sql = "SELECT `菜谱`.`"+column+"` FROM `菜谱` WHERE `菜谱`.`菜谱ID`="+userLikeList.get(i);
                rs = sta.executeQuery(sql);
                while (rs.next()) {
                    String handle = rs.getString(column);

                    if(handle!=null){//注意有为空的情况
//                        sw.splitWord(handle);
//                        result=sw.splitWordtoArrDelUseless(handle);
                        if(filter!=null){
                            result=sw.getFilterWord(handle,expectedNature,filter);
                        }else{
                            result=sw.getFilterWord(handle,expectedNature);
                        }
                        mix.mapMix(vectorSpace,sim.getVector(result,0));
                    }else{
                        System.out.println("null");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        return vectorSpace;
    }

    public Map<String, double[]> getRecipeVector(int i, String column, Set<String> expectedNature, StopRecognition filter) {//得到某个菜谱的向量空间,i为第几个菜谱，并非菜谱id
        similarity sim=new similarity();
        ArrayList<String> result = new ArrayList<String>();

        split sw = new split();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        Dbmanage dbmanage = new Dbmanage();
        DisDAO disDAO=new DisDAO();


        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            String sql = "SELECT `菜谱`.`"+column+"` FROM `菜谱` WHERE `菜谱`.`菜谱ID`=" + disDAO.idlist.get(i);
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                String handle = rs.getString(column);
                if (handle != null) {//注意有为空的情况
//                    sw.splitWord(handle);
//                    result = sw.splitWordtoArrDelUseless(handle);
                    if(filter!=null){
                        result=sw.getFilterWord(handle,expectedNature,filter);
                    }else{
                        result=sw.getFilterWord(handle,expectedNature);
                    }
                } else {
                    System.out.println("null");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        return sim.getVector(result,1);
    }

    /*
    得到某个菜谱某一列,id为第几个
     */
    public String getColumn(String column,int id){
        String result = null;
        DisDAO disDAO=new DisDAO();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        Dbmanage dbmanage = new Dbmanage();


        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            String sql = "SELECT `菜谱`.`"+column+"` FROM `菜谱`WHERE `菜谱`.`菜谱ID`=" + disDAO.idlist.get(id);
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                String handle = rs.getString(column);
                if (handle != null) {//注意有为空的情况
                    result=handle;
                } else {
                    System.out.println("null");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        return result;
    }
    /*
    得到全部菜谱的某一列
     */
    public ArrayList<String> getColumn(String column){
        ArrayList<String> result = new ArrayList<String>();


        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        Dbmanage dbmanage = new Dbmanage();


        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            String sql = "SELECT `菜谱`.`"+column+"` FROM `菜谱`";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                String handle = rs.getString(column);
                //为空也要穿
                result.add(handle);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        return result;
    }
    /*
    得到某个菜谱某一列,id为菜谱id
     */
    public String getColumnWithId(String column,int id){
        String result = null;
        DisDAO disDAO=new DisDAO();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        Dbmanage dbmanage = new Dbmanage();


        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            String sql = "SELECT `菜谱`.`"+column+"` FROM `菜谱`WHERE `菜谱`.`菜谱ID`=" + id;
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                String handle = rs.getString(column);
                if (handle != null) {//注意有为空的情况
                    result=handle;
                } else {
                    System.out.println("null");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        return result;
    }
}
