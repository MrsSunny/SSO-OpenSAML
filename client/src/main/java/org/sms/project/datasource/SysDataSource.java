package org.sms.project.datasource;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sms.core.id.ClusterDbFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author Sunny
 */
public class SysDataSource {
  
  private Logger logger = LoggerFactory.getLogger(SysDataSource.class.getName());

  private List<DriverManagerDataSource> dataSourceList;
  
  public SysDataSource(List<DriverManagerDataSource> dataSourceList) {
    this.dataSourceList = dataSourceList;
  }
  
  public SysDataSource() {
  }

  public final List<DriverManagerDataSource> getDataSourceList() {
    return dataSourceList;
  }

  public final void setDataSourceList(List<DriverManagerDataSource> dataSourceList) {
    this.dataSourceList = dataSourceList;
  }
  
  public void init() {
    List<DriverManagerDataSource> list = getDataSourceList();
    logger.debug("ID生成服务器的数量为：" + list.size());
    List<Node> nodeList = new ArrayList<SysDataSource.Node>();
    list.forEach(eachSource -> {
      nodeList.add(new Node(eachSource));
    });
    ClusterDbFactory.INSTANCE.setSourceHash(nodeList);
  }
  
  public static class Node {
    
    private DriverManagerDataSource driverManagerDataSource;
    
    public Node(DriverManagerDataSource driverManagerDataSource) {
      this.driverManagerDataSource = driverManagerDataSource;
    }
    
    public DriverManagerDataSource getDriverManagerDataSource() {
      return this.driverManagerDataSource;
    }

    @Override
    public String toString() {
      return "Node [url=" + this.driverManagerDataSource.getUrl() + ", username=" + this.driverManagerDataSource.getUsername() + "]";
    }
  }
}