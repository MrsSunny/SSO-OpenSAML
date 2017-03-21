package org.sms.project.helper.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author Sunny
 */
public class SysResultSetExtractor implements ResultSetExtractor<Object> {
  private final int start;
  private final int len;
  private final RowMapper<?> rowMapper;

  public SysResultSetExtractor(RowMapper<?> rowMapper, int start, int len) {
    this.rowMapper = rowMapper;
    this.start = start;
    this.len = len;
  }

  /**
   * 处理结果集合,被接口自动调用，该类外边不应该调用
   */
  public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
    List<Object> result = new ArrayList<>();
    int rowNum = 0;
    int end = start + len;
    point: while (rs.next()) {
      ++rowNum;
      if (rowNum < start) {
        continue point;
      } else if (rowNum >= end) {
        break point;
      } else {
        result.add(this.rowMapper.mapRow(rs, rowNum));
      }
    }
    return result;
  }
}