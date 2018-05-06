package service;

import com.recommend.operation.core.dao.model.ClusterAttr;
import com.recommend.operation.core.dao.model.ClusterTask;
import com.recommend.operation.core.dao.mongo.bean.ClusterEntityBean;
import com.recommend.operation.core.service.business.interfaces.IClusterSV;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

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
        ClusterEntityBean entity = new ClusterEntityBean();
        Map<String, String> attrType = new HashMap<>();
        attrType.put("genre", "3");
        attrType.put("rating", "1");

        Map<String, Object> attrValue = new HashMap<>();
        attrValue.put("genre", 3);
        attrValue.put("rating", 2);

        entity.setAttrType(attrType);
        entity.setAttrValue(attrValue);
        entity.setIsCenter(0);
        entity.setTaskId(1001);
        entity.setName("1");

        List<ClusterEntityBean> entityList = new ArrayList<>();
        entityList.add(entity);

        int count = clusterSV.importEntity(entityList);
        System.out.println(count);
    }
}
