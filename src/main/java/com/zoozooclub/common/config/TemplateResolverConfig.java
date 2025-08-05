package com.zoozooclub.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class TemplateResolverConfig {
	
	@Bean
	public ClassLoaderTemplateResolver orderResolver() {
		ClassLoaderTemplateResolver oResolver = new ClassLoaderTemplateResolver();
		
		oResolver.setPrefix("templates/views/admin/");
		oResolver.setSuffix(".html");
		oResolver.setTemplateMode(TemplateMode.HTML);
		oResolver.setCharacterEncoding("UTF-8");
		oResolver.setCacheable(false); // 안해놔도 잘됨.
		oResolver.setCheckExistence(true); // Resolver가 연쇄적으로 작동할 수 있게 해주는 설정(없으면 다음으로 넘어가서 탐색)
		
		return oResolver;
	}
}
