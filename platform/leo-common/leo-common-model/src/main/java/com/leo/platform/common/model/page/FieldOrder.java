package com.leo.platform.common.model.page;

/**
 * 排序
 */
public class FieldOrder {
  
  /**
   * 排序字段
   */
  public String field;
  /**
   * 排序类型
   */
  private OrderType orderType = OrderType.DESC;
  
  
  public FieldOrder(){}
  
  
  public FieldOrder(String field){
    this.field = field;
  }
  
  public FieldOrder(String field, OrderType orderType){
    this.field = field;
    this.orderType = orderType;
  }
  
  /**
   * @return the field
   */
  public String getField() {
    return field;
  }
  /**
   * @return the orderType
   */
  public OrderType getOrderType() {
    return orderType;
  }
  /**
   * @param field the field to set
   */
  public void setField(String field) {
    this.field = field;
  }
  /**
   * @param orderType the orderType to set
   */
  public void setOrderType(OrderType orderType) {
    this.orderType = orderType;
  }
  
}
