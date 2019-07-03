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
@MapperScan(basePackages = {"com.sczhaoqi.asbackend.dao.mapper2"}, sqlSessionFactoryRef = "sqlSessionFactory2")
public class Mybatis2Config
{

    private final DataSource datasource;

    @Autowired
    public Mybatis2Config(@Qualifier("secondaryDataSource") DataSource datasource) {this.datasource = datasource;}

    @Bean(name = "sqlSessionFactory2")
    @ConfigurationProperties(prefix = "mybatis2")
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(datasource);
        return sqlSessionFactoryBean;
    }
}