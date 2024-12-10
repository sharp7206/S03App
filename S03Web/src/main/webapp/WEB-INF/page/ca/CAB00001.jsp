<%--
 -===============================================================================================================
 - 아래 프로그램에 대한 저작권을 포함한 지적재산권은 APPz에 있으며,
 - APPz가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
 - APPz의 지적재산권 침해에 해당됩니다.
 - (Copyright ⓒ APPz Co., Ltd. All Rights Reserved| Confidential)
 -===============================================================================================================
 - 시스템관리 - 보안관리 - 사용자관리
 -===============================================================================================================
  - 2022/08/19 - 이호성 - 최종수정
 -===============================================================================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sys" uri="/WEB-INF/tld/systld.tld"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title><APPz:jsptitle prgmcd="${_prgmcd_}"/></title>
<script type="text/javascript">
//===============================================================================================================
//jQuery ready
//===============================================================================================================
$(()=>{
	var curCol;
	var curRow;
	initPage();
	LoadPage();
});

function initPage(){
	jsonParam = { param : {
        SYS_CD : ""
		}};//근무상태,부서코드,권한
		let cmmonCdList; 
		APPz.cmn.ApiRequest("${pageContext.request.contextPath}/api/za/zaa/getSystemList.do"
			, jsonParam,true
			, function($result){
				cmmonCdList = $result.data;
				APPz.ui.setCombo($('#C_SYS_CD'), cmmonCdList,"SYS_CD","SYS_NM", "S");
	   		  }
		);
}

/*Sheet 기본 설정 */
function LoadPage(){
		var Cols = [
				{Header : "No", Type:"Seq",           Width:45,Align:"center"},
				{Header : "STS", Type:"Status",        Width:60,SaveName:"SSTATUS"},
	        	{Header : "DEL", Type:"DelCheck",      Width:60},
      			{Header : "SysCd", Type:"Text",          Width:60,SaveName:"sysCd", InsertEdit:0, UpdateEdit:0},
      			{Header : "path", Type:"Text",          Width:260, SaveName:"path", TreeCol:1, InsertEdit:0, UpdateEdit:0, LevelSaveName:"TREELEVEL"},
      			{Header : "pathInfo", Type:"Text",      Width:200,SaveName:"pathInfo", InsertEdit:0, UpdateEdit:0},
      			{Header : "grpCd", Type:"Text",          Width:200,SaveName:"grpCd"},
      			{Header : "CatId", Type:"Text",          Width:200, SaveName:"catId", InsertEdit:1, UpdateEdit:0},
      			{Header : "CatNm", Type:"Text",          Width:200, SaveName:"catNm"},
      			{Header : "상위코드", Type:"Text",          Width:100, SaveName:"upCatId", KeyField:1},
      			{Header : "적용순서", Type:"Text",          Width:80,  SaveName:"orderNo"},
      			{Header : "Use Y/N", Type:"Combo",Width:60,SaveName:"useYn",ComboText:"Y|N",ComboCode:"Y|N",PopupText:"Y|N",PopupCode:"Y|N"},
      			{Header : "Level", Type:"Int",           Width:200,SaveName:"level", Editable:false, Hidden:1},
      			{Header : "이미지1", Type:"PopupEdit",          Width:200,SaveName:"imgGid1",Align:"center"},
      			{Header : "이미지2", Type:"Text",          Width:200,SaveName:"imgGid2"},
      			{Header : "이미지3", Type:"Text",          Width:200,SaveName:"imgGid3"},
      			{Header : "이미지1Str", Type:"Text",       Width:200,SaveName:"imgGid1Str"},
      			{Header : "비고1", Type:"Text",          Width:200,SaveName:"etc1"},
      			{Header : "비고2", Type:"Text",          Width:200,SaveName:"etc2"},
      			{Header : "비고3", Type:"Text",          Width:200,SaveName:"etc3"},
		];

		var initData1 = {"cfg":{"ChildPage": 5, "TreeNodeIcon": 1, "AutoFitColWidth": "search|resize|init|colhidden|rowtransaction", "DeferredVScroll": 1}
	    , "HeaderMode" : {Sort:1,ColMove:1,ColResize:1,HeaderCheck:1}
	    , "Cols" : Cols
	    };
		
		// IBSheet 생성
		createIBSheet2($('#div_mySheet1')[0], 'mySheet1', '100%', $(window).height()-200+'px');
		IBS_InitSheet(mySheet1, initData1);
		mySheet1.SetCountPosition(4);
		
		mySheet1.SetActionMenu("입력|행복사|-|행삭제|Clear|엑셀다운");

}

function doAction(sAction)
{
	switch(sAction)
	{
	case "R_Main" :      //조회 tree구조를 가져올려면 DB에서 대문자로 가져오면 LEVEL을 ibsheet가 인식못하여 commonMap을 사용
		if(APPz.ui.isNull($("#C_SYS_CD").val())){
			duDialog(null,APPz.getMessage("E","data.Select.Required","시스템 조건을"));
			return;
		}
   		jsonParam = { param : APPz.ui.convertFormToJson("frm01") };
   		var url = "${pageContext.request.contextPath}/api/ca/caa/searchZ01ImgCat.do";
   		APPz.cmn.ApiRequest(url,jsonParam,true,($result)=>{
   			
   			mySheet1.RemoveAll();
   			mySheet1.SetTreeCheckActionMode(1);
   			mySheet1.LoadSearchData({"data":$result.data}, {
   		        Sync: 1
   		    });
   			mySheet1.FitSize(false, true);
       	}); 
		break;
	case "insertImgCat" :      //조회
		if(mySheet1.GetSelectRow()<1){
			alert('메뉴를 입력하실 상위메뉴를 선택하세요');
			return;
		}
    	var curInfo = mySheet1.GetRowJson(mySheet1.GetSelectRow());
    	if(curInfo==null){
    		alert('화면 하위로 메뉴나 화면을 추가 할 수 없습니다.');
    		return;
    	}
    	var insRow = mySheet1.DataInsert();
    	var insJson = {"upCatId" : curInfo.catId, "upCatNm" : curInfo.catNm, "sysCd" : $("#C_SYS_CD").val(), "grpCd" : curInfo.grpCd};
    	mySheet1.SetRowData(insRow, insJson);
//    	insJson = {"UP_MENU_NM" : curInfo.MENU_NM};
//    	mySheet1.SetRowData(insRow, insJson);
		break;
	case "saveImgCat" :      //조회
		if(mySheet1.GetSaveString() != "KeyFieldError"){ //저장처리 할때 시트에서 걸러지면 무조건  KeyFieldError를 반환한다.
		var url = "${pageContext.request.contextPath}/api/ca/caa/saveZ01ImgCat.do";
		duDialog(null, '저장 하시겠습니까 <i class="fas fa-question"></i>', {
			buttons: duDialog.OK_CANCEL,
				callbacks: {
					okClick: function(e) {
						
						this.hide()
						jsonParam = {
								param : 
								{
										gridData : mySheet1.GetSaveJson(0).data
								}
				   	    };
					APPz.cmn.ApiRequest(url,jsonParam,true,function(result){//메뉴저장
	   				if(result.rtnCode>=0){
	   					duDialog(null,"저장되었습니다.",{
		   					callbacks: {
		   						okClick: function(e) {
		   							this.hide();
		   							doAction('R_Main');
		   						}
		   					}
		   				});
	   				}else{
	   					duDialog("저장실패",result.Message,{
	   						callbacks: {
		   						okClick: function(e) {
		   							this.hide();
		   						}
		   					}
	   					});
	   				}
	   			});

					}
				}
			});
		}
		break;
	case "ExcelMenu" :
		//var params = {"FileName":"excel2", "SheetName":"user", "HiddenColumn":0};
		//mySheet1.Down2Excel(params);
		var params = {FileName:"MenuList.xlsx",SheetName:"User",SheetDesign:1,Merge:1,OnlyHeaderMerge:1,DownRows:"",DownCols:""};
		mySheet1.Down2Excel(params);
		break;
	}
}

function ChangeSize1(size){
	mySheet1.FitSize(false, true);
}

function mySheet1_OnSearchEnd(code,msg){
	
	if (msg!='') {
		alert(msg);
	}else{
		mySheet1.ShowTreeLevel(-1);
		//mySheet1.FitSize(0,1);
	}
}


function mySheet1_OnSaveEnd(code, msg) {
	if(code<0){
		alert(msg);
	}else{
		mySheet1.FitSize(0,1);
	}
}


function mySheet1_OnPopupClick(Row,Col) {
	
	curCol = Col;
	curRow = Row;
	var rowJson = mySheet1.GetRowJson(mySheet1.GetSelectRow());
	console.log('rowJson=='+rowJson);
	//openFileUpload : (fileGrpId, refTableId, refDocId, refDocType, maxFileCnt, uploadType, fileJsonStr, callBackFn)
	APPz.ui.openFileUpload(rowJson.imgGid1, "Z01_IMG_CAT", rowJson.catId, "", 1, "", "", "uploadCallBackFn");
}

uploadCallBackFn = function(data){
	debugger;
	var dataJson = JSON.parse(data);
	console.log('chk=='+dataJson.fileList);
	mySheet1.SetCellValue(curRow, "imgGid1", dataJson.FILE_GRP_ID);
	mySheet1.SetCellValue(curRow, "imgGid1Str", JSON.stringify(dataJson.fileList));
}


var windowHeight = $(window).height()-200;
</script>

	
<!--조회함수를 이용하여 조회 완료되었을때 발생하는 이벤트-->


<body class="bg-appz-body">
<form id="frm01" name="frm01" method="post">
<input type="hidden" id="GRP_CD" name="GRP_CD" value="IOT_INFO">
<input type="hidden" id="mySheet1Val" name="mySheet1Val" value="" readonly="readonly"/>
<!-- (wrapper) -->
<div id="wrap">
	<!-- (content) -->
	<div class="content">
<sys:headinfo prgmcd="${_prgmcd_}"/>
        <div class="search">
            <table>
                <colgroup>
                    <col style="width:8rem;">
                    <col>
                </colgroup>
                <tbody>
                    <tr>
                        <th>시스템목록</th>
                        <td>
                            <select name="C_SYS_CD" id="C_SYS_CD" class="wauto">
                                <option value="">선택</option>
                            </select>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>		
		<div class="align both vm">
			<h2 class="h2">카테고리 저장</h2>
			<div class="align right">
			<a href="javascript:doAction('R_Main');" class="btn blue regular">조회</a>
			<a href="javascript:doAction('insertImgCat');" class="btn blue regular">신규</a>
			<a href="javascript:doAction('saveImgCat');" class="btn blue regular">저장</a>
			<a href="javascript:doAction('ExcelMenu');" class="btn blue regular">Excel</a>
			</div>
		</div>
        <div class="mt10">
	        <div id="div_mySheet1" style="width:100%">
	        </div>	
		</div>
	</div>
	<!-- //(content) -->

</div>
<!-- //(wrapper) -->

</form>
</body>
</html>