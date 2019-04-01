package com.leo.platform.common.model.page;

import java.util.List;

import com.google.common.collect.Lists;

public class BasePageModel {

  /**
   * 是否使用模糊查询， 实现中可能并非所有的字段都支持模糊查询
   */
  private boolean like=true;
  /**
   * 当前页码
   */
  private int pageNumber = 1;
  /**
   * 每页记录数 page size
   */
  private int objectsPerPage = PageUtil.defaultPageSize;

  private String nameOrCode;
  
  private String nameOrCodeEqual;

  
  
  /**
   * 排序字段
   */
  private List<FieldOrder> orderFields;

  /**
   * 是否需要取数据列表回来 用来处理某些查询只需要返回记录总条数的情况
   */
  private boolean withoutListData = false;

  /**
   * 分页查询数据类型
   */
  private PageQueryType pageQueryType = PageQueryType.BOTH;
  
  public BasePageModel() {}

  public BasePageModel(String orderFieldName) {
    this.addOrderFields(orderFieldName);
  }

  public BasePageModel(String orderFieldName, OrderType orderType) {
    this.addOrderFields(orderFieldName, orderType);
  }

  public BasePageModel(int start, int limit) {
    super();
    this.pageNumber = PageUtil.getPageNumFromStartAndLimit(start, limit);
    this.objectsPerPage = limit;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    if (pageNumber <= 0) {
      pageNumber = 1;
    }
    this.pageNumber = pageNumber;
  }

  public int getObjectsPerPage() {
    return objectsPerPage;
  }

  public void setObjectsPerPage(int objectsPerPage) {
    this.objectsPerPage = objectsPerPage;
  }

  public boolean getWithoutListData() {
    return withoutListData;
  }

  public void setWithoutListData(boolean withoutListData) {
    this.withoutListData = withoutListData;
  }

  /**
   * @return the like
   */
  public boolean isLike() {
    return like;
  }

  /**
   * @param like the like to set
   */
  public void setLike(boolean like) {
    this.like = like;
  }

  /**
   * @return the nameOrCode
   */
  public String getNameOrCode() {
    return nameOrCode;
  }

  /**
   * @param nameOrCode the nameOrCode to set
   */
  public void setNameOrCode(String nameOrCode) {
    this.nameOrCode = nameOrCode;
  }

  
  public BasePageModel addOrderFields(String field){
    this.addOrderFields(field, OrderType.DESC);
    return this;
  }
  
  public BasePageModel addOrderFields(String field, OrderType orderType){
    if(this.orderFields == null){
      this.orderFields = Lists.newArrayList();
    }
    FieldOrder fieldOrder = new FieldOrder(field, orderType);
    this.orderFields.add(fieldOrder);
    return this;
  }
  /**
   * @return the orderFields
   */
  public List<FieldOrder> getOrderFields() {
    return orderFields;
  }

  /**
   * @param orderFields the orderFields to set
   */
  public void setOrderFields(List<FieldOrder> orderFields) {
    this.orderFields = orderFields;
  }

  public PageQueryType getPageQueryType() {
    return pageQueryType;
  }

  public void setPageQueryType(PageQueryType pageQueryType) {
    this.pageQueryType = pageQueryType;
  }

  /**
   * @return the nameOrCodeEqual
   */
  public String getNameOrCodeEqual() {
    return nameOrCodeEqual;
  }

  /**
   * @param nameOrCodeEqual the nameOrCodeEqual to set
   */
  public void setNameOrCodeEqual(String nameOrCodeEqual) {
    this.nameOrCodeEqual = nameOrCodeEqual;
  }
}
