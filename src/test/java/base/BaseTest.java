package base;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 
 * @author Sunny
 * @since 1.8.0
 */
public class BaseTest {

  private static String[] CONFIG_FILES = { "WebContent/WEB-INF/config/spring/applicationContext-mvc.xml" };

  protected ApplicationContext aCtx = new FileSystemXmlApplicationContext(CONFIG_FILES);
}
