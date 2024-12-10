package com.app.s03.cmn.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.s03.cmn.mapper.CommonMapper;


@Service("cmn.CommonTransactionService")
public class CommonTransactionService {

	@Resource(name = "commonMapper")
	private CommonMapper commonMapper;

	/**
	 * SQL STRING 배분(excuteSql)
	 * @param param
	 * @param principal
	 * @return
	 */
	public Map<String, Object> excuteSql(Map param) throws Exception {
	    Map<String, Object> returnMap = new HashMap();
	    List queryList = new ArrayList();
	    queryList = (List)param.get("sqlList");
	    param.put("queryList", queryList);
	    Map parametersMap = (Map)param.get("PARAMETER");
        List<Map<String, Object>> sqlList = commonMapper.selectList("cmn.CommonSql.selectInfoBySqlId", param);
        StringBuffer sb = new StringBuffer();
        Map<String, ?> tempMap = new HashMap();
        int rslt = 0;
        for (Map<String, ?> sqlMap : sqlList) {// sqlList 조회 및 처리
            param = queryUtil(sqlMap, (Map)param.get("PARAMETER"));
            if("SELECT".equals(param.get("SQL_TYPE"))) {
                if("map".equals(param.get("RSLT_TYPE"))) {
                    tempMap = commonMapper.selectOne("cmn.CommonSql.SELECT", param);
                    returnMap.put((String)param.get("SQL_ID"), tempMap);
                }else {
                    returnMap.put((String)param.get("SQL_ID"), commonMapper.selectList("cmn.CommonSql.SELECT", param));
                }
            }else if("INSERT".equals(param.get("SQL_TYPE"))) {
                if(commonMapper.insert("cmn.CommonSql.INSERT", param)<0) {
                    throw new Exception("SQL Error ["+(String)param.get("SQL_ID")+"]");
                }
                
            }else if("UPDATE".equals(param.get("SQL_TYPE"))) {
                if(commonMapper.update("cmn.CommonSql.UPDATE", param)<0) {
                    throw new Exception("SQL Error ["+(String)param.get("SQL_ID")+"]");
                }
            }else if("DELETE".equals(param.get("SQL_TYPE"))) {
                if(commonMapper.delete("cmn.CommonSql.DELETE", param)<0) {
                    throw new Exception("SQL Error ["+(String)param.get("SQL_ID")+"]");
                }
            }
           // if(sqlMap.get("") sqlMap.get("SQL_CONT")
           // returnMap.put(arrSqlId[1], commonMapper.selectList(arrSqlId[1], param));
        }
        return returnMap;
	}

    /**
     * 2023-11-20 이상근 (OU14480) 
     * 마이바티스 쿼리를 파라메터를 입력후 반환합니다.
     * 
     * %% 파라메터가 다 없을경우 예외처리를 하지않았습니다.
     * @param  Map qryData  - 파라메터를 받습니다.
     * @return Map - 결과값 반환
     * @throws ""
     */
    private Map queryUtil(Map qryData, Map parameters) {
        // 쿼리를 호출합니다.
        String sql = (String) qryData.get("SQL_CONT");
        // 파라메터 칸에 빈칸이 있을경우 삭제
        Pattern pattern = Pattern.compile("#\\{\\s*(\\w+)\\s*\\}");
        Matcher matcher = pattern.matcher(sql);
        StringBuffer qryStrBuffer = new StringBuffer();
        while(matcher.find()) {
            String replacement = "#{" + matcher.group(1) + "}";
            matcher.appendReplacement(qryStrBuffer, replacement);
        }
        matcher.appendTail(qryStrBuffer);
        // 파라메터칸에 빈칸이 삭제되어진것을 반영
        sql = qryStrBuffer.toString();
        // 쿼리에 입력되어진 파라메터를 실행할수록 수정합니다
        if(parameters!=null) {
            for(Object key : parameters.keySet()) {
                // 쿼리에 파라메터를 변화합니다.
                sql = sql.replace(String.format("#{%s}", key), String.format("'%s'",parameters.get(key)));
            };  
        }
        qryData.put("excuteSql", sql);
        return qryData;
    }
 
    /**
     * SQL STRING 배분(excuteSql)
     * @param param
     * @param principal
     * @return
     */
    public Map<String, Object> makeSqlXml(Map param) throws Exception {
        Map<String, Object> returnMap = new HashMap();
        List queryList = new ArrayList();
        queryList = (List)param.get("sqlList");
        param.put("queryList", queryList);
        Map parametersMap = (Map)param.get("PARAMETER");
        List<Map<String, Object>> sqlList = commonMapper.selectList("cmn.CommonSql.selectInfoBySqlId", param);
        StringBuffer sb = new StringBuffer();
        Map<String, ?> tempMap = new HashMap();
        int rslt = 0;
        for (Map<String, ?> sqlMap : sqlList) {// sqlList 조회 및 처리
            param = queryUtil(sqlMap, (Map)param.get("PARAMETER"));
            if("SELECT".equals(param.get("SQL_TYPE"))) {
                if("map".equals(param.get("RSLT_TYPE"))) {
                    tempMap = commonMapper.selectOne("cmn.CommonSql.SELECT", param);
                    returnMap.put((String)param.get("SQL_ID"), tempMap);
                }else {
                    returnMap.put((String)param.get("SQL_ID"), commonMapper.selectList("cmn.CommonSql.SELECT", param));
                }
            }else if("INSERT".equals(param.get("SQL_TYPE"))) {
                if(commonMapper.insert("cmn.CommonSql.INSERT", param)<0) {
                    throw new Exception("SQL Error ["+(String)param.get("SQL_ID")+"]");
                }
                
            }else if("UPDATE".equals(param.get("SQL_TYPE"))) {
                if(commonMapper.update("cmn.CommonSql.UPDATE", param)<0) {
                    throw new Exception("SQL Error ["+(String)param.get("SQL_ID")+"]");
                }
            }else if("DELETE".equals(param.get("SQL_TYPE"))) {
                if(commonMapper.delete("cmn.CommonSql.DELETE", param)<0) {
                    throw new Exception("SQL Error ["+(String)param.get("SQL_ID")+"]");
                }
            }
           // if(sqlMap.get("") sqlMap.get("SQL_CONT")
           // returnMap.put(arrSqlId[1], commonMapper.selectList(arrSqlId[1], param));
        }
        return returnMap;
    }

}