package meena.prashant.conferencebootstrap.configurations;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfiguration {

  @Bean
  public DataSource configureDataSource() {
    DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
    dataSourceBuilder.url("jdbc:postgresql://localhost:5432/conference_app");
    dataSourceBuilder.username("postgres");
    dataSourceBuilder.password("welcome");

    System.out.println("\nConfiguring custom datasource.\n"
        + "It should replace the one already present in the spring context!\n");

    // To prove that it replaces the internal datasource object that it can configure with the available configurations' int the properties file
    // You can comment the username and password setters from above and then try to run the application
    // It will fail, confirming that it is using this configuration instead of the properties file (where we have injected values from env variable)
    // and will say the username and password were not present
    return dataSourceBuilder.build();
  }
}
