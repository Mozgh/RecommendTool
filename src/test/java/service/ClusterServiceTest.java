package service;

import com.recommend.operation.core.dao.model.ClusterTask;
import com.recommend.operation.core.service.business.impl.ClusterServiceImpl;
import com.recommend.operation.core.service.business.interfaces.IClusterSV;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author zhanggh
 */
@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class ClusterServiceTest {

    @Autowired
    IClusterSV clusterSV;

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

}
