package com.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = "com.coroapi.mapper.oracle", sqlSessionFactoryRef = "orcaleSqlSessionFactory")
/**
 * 
 * @author 86136
 *
 */
public class DatasourceOracleConfig {
	
	
	@Bean(name = "orcaleDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.orcl")
    public DataSource orcaleDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "orcaleSqlSessionFactory")
    public SqlSessionFactory orcaleSqlSessionFactory(@Qualifier("orcaleDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name = "orcaleTransactionManager")
    public DataSourceTransactionManager orcaleTransactionManager(@Qualifier("orcaleDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "orcaleSqlSessionTemplate")
    public SqlSessionTemplate orcaleSqlSessionTemplate(@Qualifier("orcaleSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
