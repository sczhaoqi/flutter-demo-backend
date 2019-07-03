package com.sczhaoqi.asbackend.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author sczhaoqi
 * @date 2019/6/24 21:58
 */
@Configuration
@MapperScan(basePackages = {"com.sczhaoqi.asbackend.dao.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisConfig {

    private final DataSource datasource;

    @Autowired
    public MybatisConfig(@Qualifier("primaryDataSource") DataSource datasource) {this.datasource = datasource;}

    @Bean(name = "sqlSessionFactory")
    @Primary
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(datasource);
        return sqlSessionFactoryBean;
    }
}