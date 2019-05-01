package northwind.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;



@PropertySource("classpath:mysql.properties")
@Configuration
public class MysqlDataSourceProvider{

	@Value( "${mysql.jdbc.url}" )
	private String jdbcUrl;
	
	@Value( "${mysql.jdbc.driver}" )
	private String driver;
	
	@Value( "${mysql.jdbc.username}" )
	private String username;
	
	@Value( "${mysql.jdbc.password}" )
	private String password;
	
	
	@Bean("mysql")
	@Primary
	public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driver);
        dataSourceBuilder.url(jdbcUrl);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
	}
	
	
	

}
