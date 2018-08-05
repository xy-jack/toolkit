package com.yd.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * Json配置类
 *
 * @author Iker
 * @date 2017-09-26 10:32
 */
@Configuration
public class JsonConfigurer {

    /**
     * 使用fastJson做解析
     *
     * @return
     */
    @Bean
    public HttpMessageConverters configureMessageConverters() {
        FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastConf = new FastJsonConfig();
        fastConf.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConverter.setFastJsonConfig(fastConf);
        HttpMessageConverter<?> converter = fastJsonConverter;
        return new HttpMessageConverters(converter);
    }
}
