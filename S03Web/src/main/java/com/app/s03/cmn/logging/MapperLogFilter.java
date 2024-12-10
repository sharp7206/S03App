package com.app.s03.cmn.logging;

import org.slf4j.Marker;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;

public class MapperLogFilter extends TurboFilter {

    // Mapper 파일 정규표현식
    private static final String MAPPER_REGEX = "com\\.app\\..+\\.mapper\\..+Mapper.+";

    @Override
    public FilterReply decide(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t) {
        // egovframework.rte.psl.dataaccess.mapper.Mapper 로그는 SQL 로그로 대체
        if (logger.getName().matches(MAPPER_REGEX))
            return FilterReply.DENY;
        return FilterReply.NEUTRAL;
    }

}
