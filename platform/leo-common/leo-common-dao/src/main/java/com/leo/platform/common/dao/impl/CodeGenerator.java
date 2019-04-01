package com.leo.platform.common.dao.impl;

public interface CodeGenerator<T> {
  
  public String generate(T entity, Long seq);
  
}
