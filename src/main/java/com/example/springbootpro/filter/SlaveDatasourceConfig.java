package com.example.springbootpro.filter;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by 97434 on 2018/12/27.
 */
@Configuration
@MapperScan(basePackages = "com.example.springbootpro.slaveMapper", sqlSessionTemplateRef = "slaveSqlSessionTemplate")
public class SlaveDatasourceConfig {

    /**
     * 配置数据数据源 slave
     *
     * @return
     */
    @Bean(name = "slaveDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
//    @Primary
    public DataSource slaveDatasource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置session工厂
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "slaveSqlSessionFactory")
//    @Primary
    public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("slaveDatasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapping/*.xml"));
        return bean.getObject();
    }


    /**
     * 配置事务管理器
     *
     * @param dataSource
     * @return
     */
    @Bean(name = "slaveTransactionManger")
//    @Primary
    public DataSourceTransactionManager slaveTransactionManger(@Qualifier("slaveDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    /**
     * 模版
     *
     * @param sqlSessionFactory
     * @return
     * @throws Exception
     */
    @Bean(name = "slaveSqlSessionTemplate")
//    @Primary
    public SqlSessionTemplate slaveSqlSessionTemplate(@Qualifier("slaveSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}

