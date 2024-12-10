package com.app.s03.core.listener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextListener;

import com.app.s03.cmn.context.ContextHolder;
import com.app.s03.cmn.request.HttpRequestHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ComRequestContextListener extends RequestContextListener {

    @Override
    public void requestInitialized(ServletRequestEvent requestEvent) {
        super.requestInitialized(requestEvent);

        HttpServletRequest req = (HttpServletRequest) requestEvent.getServletRequest();
        String srvcId = req.getServletPath();
        MDC.put("srvcId", srvcId);
        MDC.put("remoteIp", HttpRequestHelper.getRemoteIp(req));

        log.debug("##### Initialized #####");
    }

    @Override
    public void requestDestroyed(ServletRequestEvent requestEvent) {
        super.requestDestroyed(requestEvent);
        log.debug("##### Destroyed #####");
        MDC.clear();
        // 비정상적으로 Context Area 가 Clear 되지 않은 경우를 대비하여 처리
        ContextHolder.clear();
    }

}
