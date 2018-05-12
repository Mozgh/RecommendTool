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

        Map<String, Double> attrValue = new HashMap<>();
        attrValue.put("genre", 3D);
        attrValue.put("rating", 2D);

        entity.setAttrType(attrType);
        entity.setAttrValue(attrValue);
        entity.setIsCenter(0);
        entity.setTaskId(1001);
        entity.setName("1");

        String id = clusterEntityDao.insertEntity(entity);

        System.out.println(id);
    }

    @Test
    public void queryEntity() {
        String id = "5aebcca2288890047cb6061e";
        ClusterEntityBean bean = clusterEntityDao.queryEntityById(id);

        System.out.println(bean.getId());
    }
}
