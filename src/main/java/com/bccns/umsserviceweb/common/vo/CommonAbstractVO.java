package com.bccns.umsserviceweb.common.vo;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public abstract class CommonAbstractVO {

    private static final Logger log = LoggerFactory.getLogger(CommonAbstractVO.class);

    @JsonIgnore
    public String getLoginUserId() {

        String userId = null;

        try {
            userId =  (String) RequestContextHolder.currentRequestAttributes().getAttribute("sessionGUID", RequestAttributes.SCOPE_SESSION);
            //log.debug("## userId=" + userId);
        } catch ( Exception e ) {
            log.error(e.getLocalizedMessage(), e);
        }
        //log.debug("## userId=" + userId);

        return userId;
    }
}
