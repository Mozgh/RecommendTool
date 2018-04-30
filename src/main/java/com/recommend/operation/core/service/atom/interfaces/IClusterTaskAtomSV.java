package com.recommend.operation.core.service.atom.interfaces;

/**
 * @author zhanggh
 */
public interface IClusterTaskAtomSV {

    /**
     * update task's state
     * @author zhanggh
     * @param taskId    taskId
     * @param state     0-saved 1-created 2-in progress 3-finished
     * @return updateCount  1-update success, 0-update failed
     */
    public int updateTaskState(Integer taskId, Integer state);
}
