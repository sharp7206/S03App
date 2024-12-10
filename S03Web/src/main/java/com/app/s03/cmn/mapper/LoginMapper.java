package com.app.s03.cmn.mapper;

import java.util.List;
import java.util.Map;

import com.app.s03.cmn.security.vo.UserDetailVO;

public interface LoginMapper {
	public UserDetailVO getLogin(String userId);
	
	
	
	public UserDetailVO getLoginNTLM(String userId);
	
	public List<?> searchMenuInfo(Map<String, ?> map);
	
	public List<?> searchSubMenuInfo(Map<String, ?> map);
	
	public int insertLoginInfo(Map<String, ?> map) throws Exception;

	public int checkLogin(Map<String, ?> map) throws Exception;
	
	public Map<String, Object> getCurrURLInfo(Map<String, ?> paramMap);
	
	public int insertS01SystemLog(Map<String, ?> map) throws Exception;
}
