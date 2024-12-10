package com.app.s03.cmn.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.s03.cmn.mapper.CommonMapper;


/**
-===============================================================================================================
- 아래 프로그램에 대한 저작권을 포함한 지적재산권은 APP에 있으며,
- APP가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
- APP의 지적재산권 침해에 해당됩니다.
- (Copyright ⓒ HSL Cof., Ltd., Ltd. All Rights Reserved| Confidential)
-===============================================================================================================
- 메뉴코드 서비스
-===============================================================================================================
*/
@Service("cmn.MenuService")
public class MenuService {

	@Resource(name = "commonMapper")
	private CommonMapper commonMapper;

	/**
	 * 시스템메뉴트리
	 * @param param
	 * @return
	 */
	public List<?> getMenuTree(Map<String,?> param){
		return commonMapper.selectList("cmn.Menu.selectMenuTree",param);
	}

	/**
	 * 페이지 헤더 정보 조회
	 * @param programcd
	 * @return
	 */
	public Map<String,?> getHeadInfo(Map<String,?> param){
		return commonMapper.selectOne("cmn.Menu.selectHeadInfo",param);
	}

	/**
	 * 메뉴저장
	 * @param gridData
	 */
	@SuppressWarnings("unchecked")
	public void saveMenuList(Map<String, ?> gridData) {
		List<Map<String, Object>> createList = (List<Map<String, Object>>) gridData.get("create");// 추가목록
		for (Map<String, Object> data : createList) {// 신규추가 작업
			commonMapper.insert("cmn.Menu.insertMenu",data);
		}
		List<Map<String, Object>> updateList = (List<Map<String, Object>>) gridData.get("update");// 수정목록
		for (Map<String, Object> data : updateList) {// 수정 작업
			commonMapper.update("cmn.Menu.updateMenu",data);
		}
	}

	/**
	 * 메뉴 삭제
	 * @param gridData
	 */
	@SuppressWarnings("unchecked")
	public void removeMenuList(Map<String, ?> gridData) {
		List<Map<String, Object>> removeList = (List<Map<String, Object>>) gridData.get("remove");// 삭제목록
		for (Map<String, Object> data : removeList) {// 삭제 작업
			commonMapper.delete("cmn.Menu.deleteMenu",data);
		}
	}

}