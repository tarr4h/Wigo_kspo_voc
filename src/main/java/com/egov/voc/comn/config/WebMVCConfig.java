package com.egov.voc.comn.config;

import org.apache.catalina.Context;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import java.util.concurrent.TimeUnit;
/**
 * 
* <pre>
* com.wigo.crm.config
*	- WebMVCConfig.java
* </pre>
*
* @ClassName	: WebMVCConfig 
* @Description	: WebMVC 설정 
* @author 		: 김성태
* @date 		: 2021. 1. 5.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */

@Configuration
@EnableWebMvc
@ComponentScan({"com.egov"})
 class WebMVCConfig implements WebMvcConfigurer  {
	 @Autowired
	    @Qualifier(value = "authInterceptor")
		private HandlerInterceptor authInterceptor;
	    
	    @Autowired
	    @Qualifier(value = "menuInterceptor")
		private HandlerInterceptor menuInterceptor;

//	    @Autowired
//	    @Qualifier(value = "apiAuthIntercepter")
//		private HandlerInterceptor apiAuthIntercepter;
	    
	    
		  @Bean
		   TomcatServletWebServerFactory tomcatFactory() {
		    return new TomcatServletWebServerFactory() {
		      @Override
		      protected void postProcessContext(Context context) {
		        ((StandardJarScanner) context.getJarScanner()).setScanManifest(false);
		      }
		    };
		  }
	    
		//뷰 세팅1
	    @Bean
	    TilesConfigurer tilesConfigurer() {
	        final TilesConfigurer configurer = new TilesConfigurer();
	        //타일즈 설정파일이 위치하는 디렉토리+파일명
	        configurer.setDefinitions(new String[]{"classpath:/config/tiles/tiles-*.xml"});  
	        configurer.setCheckRefresh(true);
	        return configurer;
	    }
		 //뷰 세팅2
	    @Bean
	    ViewResolver tilesViewResolver() {
	        final TilesViewResolver tilesViewResolver = new TilesViewResolver();
	        tilesViewResolver.setViewClass(TilesView.class);
	        tilesViewResolver.setOrder(1);  //뷰 우선순위
	        return tilesViewResolver;
	    }

	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        // Packaging resources
	        registry.addResourceHandler("/static/**")
	                .addResourceLocations("/static/")
	                .setCacheControl(CacheControl.maxAge(1, TimeUnit.DAYS));
	    }

	    
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			 registry.addInterceptor(menuInterceptor)
	                 .addPathPatterns("/**")
	                 .excludePathPatterns("/static/**")
	                 .excludePathPatterns("/login/**")
	                 .excludePathPatterns("/commCode/**")
	                 .excludePathPatterns("/api/**")
	                 ;
			 
			 registry.addInterceptor(authInterceptor)
			         .addPathPatterns("/**")
			         .excludePathPatterns("/static/**")
			         .excludePathPatterns("/login/**")
			         .excludePathPatterns("/genGrid/**")
			         .excludePathPatterns("/error/**")
			         .excludePathPatterns("/commCode/**")
			         .excludePathPatterns("/api/**")
			         .excludePathPatterns("/tutor/**")
			         .excludePathPatterns("/util/**")
			         .excludePathPatterns("/example/**")
	         ;
			 
//			 List<String> patterns = new ArrayList<String>();
//			 patterns.add("/api/**");
//			 patterns.add("/tutor/**");
//			 registry.addInterceptor(apiAuthIntercepter)
//	         .addPathPatterns(patterns);
		}
    
}