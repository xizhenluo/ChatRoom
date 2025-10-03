package com.lxz.chatroom.common.common.config;

import com.lxz.chatroom.common.common.interceptor.CollectorInterceptor;
import com.lxz.chatroom.common.common.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/2
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    TokenInterceptor tokenInterceptor;

    @Autowired
    CollectorInterceptor collectorInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // order matters!!!
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/capi/**"); // active to paths starts with /capi/
        registry.addInterceptor(collectorInterceptor)
                .addPathPatterns("/capi/**");
    }
}
