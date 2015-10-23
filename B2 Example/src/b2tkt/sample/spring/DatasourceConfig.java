package b2tkt.sample.spring;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import blackboard.db.impl.BbDatabaseDataSource;

/**
 * @author jarias
 * @since Aug 19, 2010 2:36:08 PM
 */
@Configuration
public class DatasourceConfig {
   private static final Logger LOGGER = LoggerFactory.getLogger(DatasourceConfig.class);

   @Value("${bb.datasource.key}")
   private String bbDatasourceKey;

   @Bean(name = "dataSource")
   public DataSource getDataSource() {
      try {
         return new BbDatabaseDataSource(bbDatasourceKey);
      } catch (Exception e) {
         LOGGER.error("Couldn't get datasource", e);
      }
      return null;
   }
}
