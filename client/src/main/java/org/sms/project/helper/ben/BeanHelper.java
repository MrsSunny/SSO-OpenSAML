package org.sms.project.helper.ben;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

/**
 * 根据ResultSet对象封装Bean
 * 
 * @author Sunny
 */
public enum BeanHelper {

  INSTANCE;

  /**
   * @param object 封装的主题
   * @param rs ResultSet记录
   * @param fieldMapping bean字段和ResultSet查询字段的对应关系 如果为Null则以object字段名称为准
   * @return
   */
  public Object buildBean(Object object, ResultSet rs, Map<String, String> fieldMapping) {

    ResultSetMetaData rsm = null;
    BeanWrapper beanWrapper = new BeanWrapperImpl(object);
    Class<?> zclass = object.getClass();

    /**
     * 获取object的全部字段
     */
    Field[] fields = zclass.getDeclaredFields();
    try {
      rsm = rs.getMetaData();

      /**
       * 获取总列数
       */
      int count = rsm.getColumnCount();
      for (int i = 0; i < count; i++) {

        /**
         * 获取数据库列的名称
         */
        String fieldName = rsm.getColumnName(i + 1);
        String target = null;
        /**
         * 获取数据库中列名称与实体类对象的对应关系，如果为空，则以数据库中字段为准
         */
        if (null != fieldMapping) {
          target = fieldMapping.get(fieldName);
        }
        /**
         * 获取列的类型
         */
        for (int f = 0; f < fields.length; f ++) {
          if (fieldName.equalsIgnoreCase(fields[f].getName())) {
            this.setValue(fields[f].getType(), rs.getObject(fieldName), beanWrapper, target == null ? fieldName : target, rs);
          }
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("组装Bean错误", e);
    }
    return object;
  }

  /**
   * @param tclass
   * @param typeNo
   * @param object
   * @param beanWrapper
   * @param fieldName
   * @param rs
   */
  private void setValue(Class<?> tclass, Object object, BeanWrapper beanWrapper, String fieldName, ResultSet rs) {
    try {
      if (tclass.equals(String.class)) {
        Object value = rs.getObject(fieldName);
        if (null != value) {
          beanWrapper.setPropertyValue(fieldName, value.toString());
        }
      } else {
        Object value = rs.getObject(fieldName);
        if (null != value) {
          beanWrapper.setPropertyValue(fieldName, value);
        }
      }
    } catch (BeansException | SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param object
   * @param rs
   * @return
   */
  public Object buildBean(Object object, ResultSet rs) {
    return buildBean(object, rs, null);
  }
}