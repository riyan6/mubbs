package org.mubbs.seata.order.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.nacos.client.utils.StringUtils;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableConfigurationProperties({MybatisPlusProperties.class})
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean
    public DataSourceProxy dataSourceProxy(DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

//    @Bean
//    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSourceProxy dataSourceProxy,
//                                                       MybatisPlusProperties mybatisProperties) {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSourceProxy);
//
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        try {
//            Resource[] mapperLocaltions = resolver.getResources(mybatisProperties.getMapperLocations()[0]);
//            bean.setMapperLocations(mapperLocaltions);
//
//            if (StringUtils.isNotBlank(mybatisProperties.getConfigLocation())) {
//                Resource[] resources = resolver.getResources(mybatisProperties.getConfigLocation());
//                bean.setConfigLocation(resources[0]);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bean;
//    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSourceProxy dataSourceProxy) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSourceProxy);
        return factoryBean.getObject();
    }



}
