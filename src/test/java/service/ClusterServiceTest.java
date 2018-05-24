package service;

import com.recommend.operation.core.dao.model.ClusterAttr;
import com.recommend.operation.core.dao.model.ClusterTask;
import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;
import com.recommend.operation.core.service.business.interfaces.IClusterSV;
import com.recommend.operation.core.service.business.interfaces.IKMeansSV;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanggh
 */
@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class ClusterServiceTest {

    @Autowired
    private IClusterSV clusterSV;

    @Autowired
    private IKMeansSV kMeansSV;

    @Test
    public void createTaskTest() throws Exception {
        ClusterTask task = new ClusterTask();
        task.setCenter(3);
        task.setDelFlag(0);
        task.setDescribes("test");
        task.setMethod(1);
        task.setName("testTask");
        task.setUserId(1000);
        task.setState(0);
        Integer newId = clusterSV.createTask(task);
        System.out.println(newId);
    }

    @Test
    public void delTask() throws Exception {
        Integer id = 1001;
        int delCount = clusterSV.deleteTask(id);
        System.out.println(delCount);
    }

    @Test
    public void importAttrs() throws Exception {
        List<String> nameList = new ArrayList<>();
        List<String> codeList = new ArrayList<>();
        Integer taskId = 1001;
        List<Integer> typeList = new ArrayList<>();
        nameList.add("userId");
        nameList.add("movieId");
        nameList.add("genre");
        nameList.add("rating");

        codeList.addAll(nameList);

        typeList.add(3);
        typeList.add(3);
        typeList.add(3);
        typeList.add(1);

        List<ClusterAttr> attrList = new ArrayList<>();

        for (int i = 0 ; i < 4 ; i++) {
            ClusterAttr attr = new ClusterAttr();
            attr.setName(nameList.get(i));
            attr.setCode(codeList.get(i));
            attr.setSort(i+1);
            attr.setType(typeList.get(i));
            attr.setTaskId(taskId);

            attrList.add(attr);
        }

        System.out.println("attr count: " + attrList.size());
        int count = clusterSV.importAttribute(attrList);
        System.out.println("import count: " + count);
    }

    @Test
    public void importEntitys() {
//        ClusterEntityBean entity = new ClusterEntityBean();
//        Map<String, String> attrType = new HashMap<>();
//        attrType.put("genre", "3");
//        attrType.put("rating", "1");
//
//        Map<String, Object> attrValue = new HashMap<>();
//        attrValue.put("genre", 3D);
//        attrValue.put("rating", 2D);
//
//        entity.setAttrType(attrType);
//        entity.setAttrValue(attrValue);
//        entity.setIsCenter(0);
//        entity.setTaskId(1001);
//        entity.setName("1");

        List<ClusterEntityBean> entityList = queryClusterEntityBeans();

        int count = clusterSV.importEntity(entityList);
        System.out.println(count);
    }

    public List<ClusterEntityBean> queryClusterEntityBeans() {

        List<ClusterEntityBean> beanList = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://118.25.35.198:3306/movie_rating?characterEncoding=UTF-8";
        String username = "mozgh";
        String password = "Mysql@0430";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String sql = "select a.id,a.gender,a.occupation,a.age, b.movie_id,b.rating, c.genre  from t_user a, rating b, movie_genres c where a.id = b.user_id and b.movie_id = c.movie_id and a.id < 50";

            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rst = pst.executeQuery();
            ClusterEntityBean bean;
            Map<String, String> attrType = new HashMap<>();
            attrType.put("userId", "1");
            attrType.put("gender", "3");
            attrType.put("occupation", "3");
            attrType.put("age", "1");
            attrType.put("movie_id", "3");
            attrType.put("rating", "1");
            attrType.put("genre", "3");

            Map<String, Object> attrValue ;
            while(rst.next()) {
                int userId = rst.getInt(1);
                String gender = rst.getString(2);
                int occupation = rst.getInt(3);
                int age = rst.getInt(4);
                int movie_id = rst.getInt(5);
                int rating = rst.getInt(6);
                int genre = rst.getInt(7);
                bean = new ClusterEntityBean();
                bean.setTaskId(1001);
                bean.setIsCenter(0);
                bean.setName(String.valueOf(rst.getInt(1)));
                bean.setAttrType(attrType);
                attrValue = new HashMap<>();
                attrValue.put("userId", userId);
                attrValue.put("gender", gender);
                attrValue.put("occupation", occupation);
                attrValue.put("age", age);
                attrValue.put("movie_id", movie_id);
                attrValue.put("rating", rating);
                attrValue.put("genre", genre);
                bean.setAttrValue(attrValue);

                beanList.add(bean);

            }
            System.out.println(beanList.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beanList;
    }

    @Test
    public void testLoadData() {
        ClusterTask task = new ClusterTask();
        task = clusterSV.queryTaskById(1001);
        kMeansSV.execute(task);

    }
}
