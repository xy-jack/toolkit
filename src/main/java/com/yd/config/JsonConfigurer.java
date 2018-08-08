package com.yd.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

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
        //fastConf.setCharset(Charset.forName("utf-8"));
        //处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonConverter.setSupportedMediaTypes(fastMediaTypes);

        fastConf.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConverter.setFastJsonConfig(fastConf);
        HttpMessageConverter<?> converter = fastJsonConverter;
        return new HttpMessageConverters(converter);
    }
}
