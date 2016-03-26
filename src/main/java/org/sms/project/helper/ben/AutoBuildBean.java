package org.sms.project.helper.ben;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * 根据ResultSet对象封装Bean
 * @author Sunny
 */
public enum AutoBuildBean {

  INSTANCE;

  /**
   * @param object 封装的主题
   * @param rs ResultSet记录
   * @param fieldMapping bean字段和ResultSet查询字段的对应关系
   * 如果为Null则以object字段名称为准
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
      for (int i = 0; i < count; i ++) {
        
        /**
         * 获取列的名称
         */
        String fieldName = rsm.getColumnName(i);
        /**
         * 获取列的类型
         */
        int typeNo = rsm.getColumnType(i);
        
        
        beanWrapper.setPropertyValue(fieldName, rs.getObject(fieldName));
      }
    } catch (SQLException e) {
      throw new RuntimeException("组装Bean错误");
    }
    return object;
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