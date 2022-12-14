package com.egov.voc;

//@Configuration
//public class EgovBootInitialization implements WebMvcConfigurer {

//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/").setViewName("forward:/index.jsp");
//		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//	}
//
//	@Bean
//    SessionLocaleResolver localeResolver() {
//        return new SessionLocaleResolver();
//    }
//
//	@Bean
//    LocaleChangeInterceptor localeChangeInterceptor() {
//        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
//        interceptor.setParamName("language");
//        return interceptor;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(localeChangeInterceptor());
//    }
//
//}
