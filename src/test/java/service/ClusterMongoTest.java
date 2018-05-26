package service;

import com.recommend.operation.core.dao.mongo.interfaces.ClusterEntityDao;
import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanggh
 */


@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class ClusterMongoTest {

    @Autowired
    ClusterEntityDao clusterEntityDao;

    @Test
    public void insertEntity() {
        /*ConfigurableApplicationContext context = null;
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ClusterEntityDao clusterEntityDao = context.getBean(ClusterEntityDaoImpl.class);
*/
        ClusterEntityBean entity = new ClusterEntityBean();
        Map<String, String> attrType = new HashMap<>();
        attrType.put("genre", "3");
        attrType.put("rating", "1");

        Map<String, Object> attrValue = new HashMap<>();
        attrValue.put("genre", 3D);
        attrValue.put("rating", 2D);

//        entity.setAttrType(attrType);
        entity.setAttrValue(attrValue);
        entity.setIsCenter(0);
        entity.setTaskId(1001);
        entity.setCode("1");

        String id = clusterEntityDao.insertEntity(entity);

        System.out.println(id);
    }

    @Test
    public void queryEntity() {
        String id = "5aebcca2288890047cb6061e";
        ClusterEntityBean bean = clusterEntityDao.queryEntityById(id);

        System.out.println(bean.getId());
    }

    @Test
    public void updateEntity() {
        ClusterEntityBean entity = clusterEntityDao.queryEntityById("5b05943d2c965c2a8c60475f");

        entity.setCode("upsert");
        entity.setIsCenter(0);

        int result = 0;
        try {
//            result = clusterEntityDao.updateEntity();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(result);
    }
}
