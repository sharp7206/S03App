package com.app.s03.cmn.service;

import java.util.List; 
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.s03.cmn.exception.CommonBusinessException;
import com.app.s03.cmn.mapper.CommonMapper;
import com.app.s03.cmn.utils.ComProperties;
import com.app.s03.cmn.utils.ConChar;


/**
-===============================================================================================================
- 아래 프로그램에 대한 저작권을 포함한 지적재산권은 APP에 있으며,
- APP가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
- APP의 지적재산권 침해에 해당됩니다.
- (Copyright ⓒ HSL Cof., Ltd., Ltd. All Rights Reserved| Confidential)
-===============================================================================================================
- 시스템관리 처리 서비스
-===============================================================================================================
*/
@Service("cmn.SystemService")
public class SystemService {
    private final static String FILE_SEPERATOR = "/";

    public static final int CHUNK_SIZE = 10;
	@Autowired
	private CommonMapper commonMapper;

	/**
	 * 시스템코드목록
	 * @param param
	 * @return
	 */
	public List<?> getSystemList(Map<String,?> param){
		return commonMapper.selectList("cmn.System.selectSystemList",param);
	}

	/**
	 * 시스템코드 저장
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void saveSystemList(Map<String, ?> gridData) {
		List<Map<String, Object>> createList = (List<Map<String, Object>>) gridData.get("create");// 추가목록
		for (Map<String, Object> data : createList) {// 신규추가 작업
			commonMapper.insert("cmn.System.insertSystem",data);//시스템 코드 추가
			commonMapper.insert("cmn.System.insertSysMenu",data);// 시스템 메뉴 추가
			commonMapper.insert("cmn.System.insertSysGrpcd",data);// 시스템 그룹코드 추가
		}
		List<Map<String, Object>> updateList = (List<Map<String, Object>>) gridData.get("update");// 수정목록
		for (Map<String, Object> data : updateList) {// 수정 작업
			commonMapper.update("cmn.System.updateSystem",data);//시스템코드 수정
			commonMapper.insert("cmn.System.insertSysMenu",data);// 시스템 메뉴 수정
			commonMapper.insert("cmn.System.insertSysGrpcd",data);// 시스템 그룹코드 수정.
		}
	}

	/**
	 * 시스템코드삭제
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void removeSystemList(Map<String, ?> gridData) {
		List<Map<String, Object>> removeList = (List<Map<String, Object>>) gridData.get("remove");// 삭제목록
		for (Map<String, Object> data : removeList) {// 삭제 작업
			commonMapper.delete("cmn.System.deleteSysMenu",data);//시스템 메뉴 삭제
			commonMapper.delete("cmn.System.deleteSysGrpcd",data);//시스템 그룹코드 삭제
			commonMapper.delete("cmn.System.deleteSystem",data);// 시스템코드 삭제
		}
	}
}