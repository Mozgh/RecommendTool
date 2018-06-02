package com.recommend.web.controller;

import com.recommend.operation.core.dao.model.ClusterObjCount;
import com.recommend.operation.core.dao.model.ClusterTask;
import com.recommend.operation.core.service.business.interfaces.IClusterObjectSV;
import com.recommend.operation.core.service.business.interfaces.IClusterTaskSV;
import com.recommend.web.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by feir4 on 2018/5/31.
 */
@Controller
@RequestMapping("/task")
public class TaskController extends ApplicationController {

    @Autowired
    IClusterTaskSV taskSV;

    @Autowired
    IClusterObjectSV objectSV;

    @RequestMapping(value = "/queryTaskList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse queryTaskList() {
        BaseResponse response = new BaseResponse();

        Integer userId = 1000;

        List<ClusterTask> taskList = taskSV.queryTaskByUserId(userId);

        if (CollectionUtils.isEmpty(taskList)) {
            response.fail("未查询到任务");
        } else {
            response.success(taskList);
        }
        return response;
    }

    @RequestMapping(value = "/openTask")
    @ResponseBody
    public BaseResponse openTask(HttpServletRequest request) {
        BaseResponse response = new BaseResponse();
        String[] taskId = request.getParameterValues("taskId[]");
        String message = "";

        for (String s: taskId) {
            try {
                if (!StringUtils.isEmpty(s)) {
                    Integer result = taskSV.openTask(Integer.parseInt(s));
                    if (1 == result) {
                        message += s + (" open success \n");
                    } else {
                        message += s + ("open failed \n");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.success(200000, message, 1);
        return response;
    }

    @RequestMapping(value = "/closeTask")
    @ResponseBody
    public BaseResponse closeTask(HttpServletRequest request) {
        String[] taskId = request.getParameterValues("taskId[]");
        String message = "";
        BaseResponse response = new BaseResponse();

        List<Integer> taskIdList = new ArrayList<>();
        for (String s : taskId) {
            try {
                if (!StringUtils.isEmpty(s)) {
                    Integer result = taskSV.closeTask(Integer.parseInt(s));
                    if (1 == result) {
                        message += (s + " close success \n");
                    } else {
                        message += (s + " close failed \n");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.success(200000, message, 1);
        return response;
    }

    @RequestMapping(value = "/createTask")
    @ResponseBody
    public BaseResponse createTask(HttpServletRequest request) {
        BaseResponse response = new BaseResponse();

        String taskName = request.getParameter("taskName");
        String method = request.getParameter("method");
        String description = request.getParameter("description");
        String centerCount = request.getParameter("centerCount");
        String dbHost = request.getParameter("dbHost");
        String dbPort = request.getParameter("dbPort");
        String dbName = request.getParameter("dbName");
        String dbUser = request.getParameter("dbUser");
        String dbPassword = request.getParameter("dbPassword");
        String querySql = request.getParameter("querySql");

        ClusterTask task = new ClusterTask();
        task.setDelFlag(0);
        task.setName(taskName);
        task.setMethod(Integer.parseInt(method));
        task.setDescribes(description);
        task.setCenter(Integer.parseInt(centerCount));
        task.setDbHost(dbHost);
        task.setDbPort(dbPort);
        task.setDbName(dbName);
        task.setDbUser(dbUser);
        task.setDbPassword(dbPassword);
        task.setQuerySql(querySql);
        task.setUserId(1000);
        task.setState(1);

        try {
            taskSV.createTask(task);
            response.success(200000, "create success", null);
        } catch (Exception e) {
            e.printStackTrace();
            response.fail(999999, "create failed", null);
        }
        return response;
    }

    @RequestMapping(value = "/clusterResult")
    @ResponseBody
    public BaseResponse clusterResult(HttpServletRequest request) {
        BaseResponse response = new BaseResponse();
        String taskId = request.getParameter("taskId");
        try {
            List<ClusterObjCount> result = objectSV.queryClusterResult(Integer.parseInt(taskId));
            response.success(200000, "query success", result);
        } catch (Exception e) {
            e.printStackTrace();
            response.fail(999999, "query faild", null);
        }
        return response;
    }

    @RequestMapping(value = "/recommendResult")
    @ResponseBody
    public BaseResponse recommendResult(HttpServletRequest request) {
        BaseResponse response = new BaseResponse();
        String code = request.getParameter("code");

        try {
            List<Object> result = objectSV.getRecommendResult(code);
            response.success(200000, "success", result);
        } catch (Exception e) {
            e.printStackTrace();
            response.fail();
        }
        return response;
    }
}
