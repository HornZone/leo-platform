package com.leo.platform.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Base class for Model objects. Child objects should implement toString(), equals() and hashCode().
 *   
 */
public abstract class BaseEntity<PK extends Serializable> implements Serializable {
  /**
     * Fully self annotation
     */
    private static final long serialVersionUID = 1L;
    
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private PK id;
  @Column(name = "crudType")
  private String crudType;

  public PK getId() {
    return id;
  }

  public void setId(PK id) {
    this.id = id;
  }


  public String getCrudType() {
    return crudType;
  }

  public void setCrudType(String crudType) {
    this.crudType = crudType;
  }

  /**
   * Returns a multi-line String with key=value pairs.
   * 
   * @return a String representation of this class.
   */
  public String toString() {
    ToStringBuilder.setDefaultStyle(ToStringStyle.DEFAULT_STYLE);
    return ToStringBuilder.reflectionToString(this);
  }

  /**
   * Compares object equality. When using Hibernate, the primary key should not be a part of this
   * comparison.
   * 
   * @param o object to compare to
   * @return true/false based on equality tests
   */
  public boolean equals(Object o) {
    return super.equals(o);
  }

  /**
   * When you override equals, you should override hashCode. See "Why are equals() and hashCode()
   * importation" for more information: http://www.hibernate.org/109.html
   * 
   * @return hashCode
   */
  public int hashCode() {
    return super.hashCode();
  }


}
