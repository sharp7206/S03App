package com.app.s03.cmn.utils;

import java.util.Locale;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class MessageUtils {

	private static MessageSourceAccessor messageSourceAccessor = null;
	
	private ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;
	
    public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
        MessageUtils.messageSourceAccessor = messageSourceAccessor;
    }

    public static String getMessage(String code) {
        return messageSourceAccessor.getMessage(code, Locale.getDefault());
    }

    public static String getMessage(String code, Object[] objs) {
        return messageSourceAccessor.getMessage(code, objs, Locale.getDefault());
    }
    
    
}
