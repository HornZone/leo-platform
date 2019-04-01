package com.leo.platform.common.model.page;

public class PageUtil {
    static int defaultPageSize = 50;
    /**
     * 根据页码和每页显示数量计算第一个对象的index
     * @param model
     * @return
     */
    static public Integer getFirstResult(BasePageModel model){
        Integer result = (model.getPageNumber() - 1 ) * model.getObjectsPerPage();
        if(result < 0){
            result = 0;
        }
        return result;
    }
    /**
     * 根据开始记录的index和每页显示条数来计算当前页码
     * @param start
     * @param limit
     * @return
     */
    static public Integer getPageNumFromStartAndLimit(int start, int limit){
        Integer result = start/limit + 1;
        if(result < 0){
            result = 1;
        }
        return result;
    }
}
