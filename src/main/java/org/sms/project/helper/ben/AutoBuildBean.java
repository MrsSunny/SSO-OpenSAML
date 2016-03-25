package org.sms.project.helper.ben;

import java.lang.reflect.Field;
import java.sql.ResultSet;
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
    BeanWrapper beanWrapper = new BeanWrapperImpl(object);
    Class<?> zclass = object.getClass();
    Field[] fields = zclass.getDeclaredFields();
    final int fSize = fields.length;
    String fieldName = null;
    try {
      for (int f = 0; f < fSize; f++) {
        fieldName = fields[f].getName();
        int find = rs.findColumn(fieldName);
        if (find != 0) {
          String target = null;
          if (null != fieldMapping) {
            target = fieldMapping.get(fieldName);
          }
          if (target == null) {
            if (null != rs.getObject(fieldName)) {
              beanWrapper.setPropertyValue(fieldName, rs.getObject(fieldName));
            }
          } else {
            if (null != rs.getObject(fieldName)) {
              beanWrapper.setPropertyValue(fieldName, rs.getObject(target));
            }
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
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