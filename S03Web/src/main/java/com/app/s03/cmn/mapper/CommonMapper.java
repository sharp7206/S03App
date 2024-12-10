package com.app.s03.cmn.mapper;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.app.s03.cmn.security.ComUserDetails;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class CommonMapper extends SqlSessionDaoSupport {
	//private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * SqlSessionFactory 세터
	 */
	public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
		super.setSqlSessionFactory(sqlSession);
	}

	/**
	 * 추가나 수정시 처리자 세팅위한 맵타입 파라메타인 경우 로그인 사용자 아이디 세팅처리
	 * @param parameterObject
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setSessionInfo(Object parameterObject) {
        if(parameterObject instanceof Map) {
        	try {
	        	ComUserDetails userDetails = (ComUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        	((Map) parameterObject).put("_SES", userDetails);
	        	log.debug("CommonMapper.setSessionInfo={}",((Map) parameterObject).get("_SES"));
        	}catch(Exception e) {
        		
        	}
        }
	}

	/**
	 * insert 처리
	 * @param queryId
	 * @return
	 */
	public int insert(String queryId) {
		return getSqlSession().insert(queryId);
	}

	/**
	 * insert 처리
	 * @param queryId
	 * @param parameterObject
	 * @return
	 */
	public int insert(String queryId, Object parameterObject) {
		setSessionInfo(parameterObject);
		return getSqlSession().insert(queryId, parameterObject);
	}

	/**
	 * update 처리
	 * @param queryId
	 * @return
	 */
	public int update(String queryId) {
		return getSqlSession().update(queryId);
	}

	/**
	 * update 처리
	 * @param queryId
	 * @param parameterObject
	 * @return
	 */
	public int update(String queryId, Object parameterObject) {
		setSessionInfo(parameterObject);
		return getSqlSession().update(queryId, parameterObject);
	}

	/**
	 * delete 처리
	 * @param queryId
	 * @return
	 */
	public int delete(String queryId) {
		return getSqlSession().delete(queryId);
	}

	/**
	 * delete 처리
	 * @param queryId
	 * @param parameterObject
	 * @return
	 */
	public int delete(String queryId, Object parameterObject) {
		setSessionInfo(parameterObject);
		return getSqlSession().delete(queryId, parameterObject);
	}

	/**
	 * select 1 건처리
	 * @param <T>
	 * @param queryId
	 * @return
	 */
	public <T> T selectOne(String queryId) {
		return getSqlSession().selectOne(queryId);
	}

	/**
	 * select 1 건처리
	 * @param <T>
	 * @param queryId
	 * @param parameterObject
	 * @return
	 */
	public <T> T selectOne(String queryId, Object parameterObject) {
		setSessionInfo(parameterObject);
		return getSqlSession().selectOne(queryId, parameterObject);
	}

	/**
	 * @param <K>
	 * @param <V>
	 * @param queryId
	 * @param mapKey
	 * @return
	 */
	public <K, V> Map<K, V> selectMap(String queryId, String mapKey) {
		return getSqlSession().selectMap(queryId, mapKey);
	}

	/**
	 * @param <K>
	 * @param <V>
	 * @param queryId
	 * @param parameterObject
	 * @param mapKey
	 * @return
	 */
	public <K, V> Map<K, V> selectMap(String queryId, Object parameterObject, String mapKey) {
		setSessionInfo(parameterObject);
		return getSqlSession().selectMap(queryId, parameterObject, mapKey);
	}

	/**
	 * @param <K>
	 * @param <V>
	 * @param queryId
	 * @param parameterObject
	 * @param mapKey
	 * @param rowBounds
	 * @return
	 */
	public <K, V> Map<K, V> selectMap(String queryId, Object parameterObject, String mapKey, RowBounds rowBounds) {
		setSessionInfo(parameterObject);
		return getSqlSession().selectMap(queryId, parameterObject, mapKey, rowBounds);
	}

	/**
	 * 여러건 조회
	 * @param <E>
	 * @param queryId
	 * @return
	 */
	public <E> List<E> selectList(String queryId) {
		return getSqlSession().selectList(queryId);
	}

	/**
	 * 여러건 조회
	 * @param <E>
	 * @param queryId
	 * @param parameterObject
	 * @return
	 */
	public <E> List<E> selectList(String queryId, Object parameterObject) {
		setSessionInfo(parameterObject);
		return getSqlSession().selectList(queryId, parameterObject);
	}

	/**
	 * 여러건 조회
	 * @param <E>
	 * @param queryId
	 * @param parameterObject
	 * @param rowBounds
	 * @return
	 */
	public <E> List<E> selectList(String queryId, Object parameterObject, RowBounds rowBounds) {
		setSessionInfo(parameterObject);
		return getSqlSession().selectList(queryId, parameterObject, rowBounds);
	}

	/**
	 * 페이징 조회
	 * @param queryId
	 * @param parameterObject
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<?> listWithPaging(String queryId, Object parameterObject, int pageIndex, int pageSize) {
		setSessionInfo(parameterObject);
		int skipResults = pageIndex * pageSize;
		RowBounds rowBounds = new RowBounds(skipResults, pageSize);
		return getSqlSession().selectList(queryId, parameterObject, rowBounds);
	}

	/**
	 * @param queryId
	 * @param handler
	 */
	@SuppressWarnings("rawtypes")
	public void listToOutUsingResultHandler(String queryId, ResultHandler handler) {
		getSqlSession().select(queryId, handler);
	}
}