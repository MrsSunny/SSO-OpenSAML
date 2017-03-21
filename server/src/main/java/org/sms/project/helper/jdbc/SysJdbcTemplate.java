package org.sms.project.helper.jdbc;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author Sunny
 */
public class SysJdbcTemplate extends JdbcTemplate {
  
  private DataSource dataSource;

  public SysJdbcTemplate() {
  }

  public SysJdbcTemplate(DataSource dataSource) {
    this.dataSource = dataSource;
    super.setDataSource(dataSource);
  }

  public <T> List<T> queryPage(String sql, int startRow, int rowsCount) throws DataAccessException {
    return queryPage(sql, startRow, rowsCount, getColumnMapRowMapper());
  }

  @SuppressWarnings("unchecked")
  public <T> List<T> queryPage(String sql, int startRow, int rowsCount, RowMapper<?> rowMapper) throws DataAccessException {
    return (List<T>) query(sql, new SysResultSetExtractor(rowMapper, startRow, rowsCount));
  }

  public DataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
    super.setDataSource(dataSource);
  }
}