package com.leo.platform.common.model.page;

/**
 * 分页查询类型
 * <p>引入该参数主要是针对分页查询效率问题：有的分页查询统计数据总量特别耗时，引入该参数用于将数量统计与分页列表查询分离</p>
 */
public enum PageQueryType {

  /**
   * 同时查询分页数据列表并统计数据总数量
   */
  BOTH ,
  
  /**
   * 支持查询当前页数据列表
   */
  LIST ,
  
  /**
   * 查询数据总数量
   */
  COUNT 
}
