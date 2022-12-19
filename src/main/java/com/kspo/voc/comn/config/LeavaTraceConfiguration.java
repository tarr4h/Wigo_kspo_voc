package com.kspo.voc.comn.config;

import org.egovframe.rte.fdl.cmmn.trace.LeaveaTrace;
import org.egovframe.rte.fdl.cmmn.trace.handler.DefaultTraceHandler;
import org.egovframe.rte.fdl.cmmn.trace.handler.TraceHandler;
import org.egovframe.rte.fdl.cmmn.trace.manager.DefaultTraceHandleManager;
import org.egovframe.rte.fdl.cmmn.trace.manager.TraceHandlerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

/**
 * EgovAbstractServiceImpl 클래스 상속에 필요한 Configuration
 */
@Configuration
public class LeavaTraceConfiguration {

    @Bean
    LeaveaTrace leaveaTrace(DefaultTraceHandleManager traceHandleManager){
        LeaveaTrace leaveaTrace = new LeaveaTrace();
        leaveaTrace.setTraceHandlerServices(new TraceHandlerService[]{traceHandleManager});
        return leaveaTrace;
    }

    @Bean
    DefaultTraceHandleManager traceHandleManager(AntPathMatcher antPathMatcher, DefaultTraceHandler defaultTraceHandler) {
        DefaultTraceHandleManager defaultTraceHandleManager = new DefaultTraceHandleManager();
        defaultTraceHandleManager.setReqExpMatcher(antPathMatcher);
        defaultTraceHandleManager.setPatterns(new String[]{"*"});
        defaultTraceHandleManager.setHandlers(new TraceHandler[]{defaultTraceHandler});
        return defaultTraceHandleManager;
    }

    @Bean(name = "antPathMater")
    AntPathMatcher antPathMatcher() {
//        AntPathMatcher antPathMatcher = new AntPathMatcher();
//        return antPathMatcher;
        return new AntPathMatcher();
    }

    @Bean
    DefaultTraceHandler defaultTraceHandler() {
//        DefaultTraceHandler defaultTraceHandler = new DefaultTraceHandler();
//        return defaultTraceHandler;
        return new DefaultTraceHandler();
    }

}
