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
				APPz.ui.setCombo($('#SYS_CD'), cmmonCdList,"SYS_CD","SYS_NM", "S");
	   		  }
		);
		
		let _toDay = APPz.sysdate();
		$("#FROM_YMD").val(APPz.adddays(_toDay,-30,'-'));
		$("#TO_YMD").val(_toDay);
		APPz.ui.jqueryCalenderRange("FROM_YMD", "TO_YMD");	    
}

/*Sheet 기본 설정 */
function LoadPage(){
	var Cols = [
		{Header : "No", Type:"Seq",MinWidth:45,Align:"center"},
		{Header : "CREATE_DT", Type:"Text",      MinWidth:80,   SaveName:"CREATE_DT",            Edit: false, ColMerge :false, Hidden:true}, //발생일자
		{Header : "발생일자", Type:"Text",      MinWidth:120,  SaveName:"ISSUE_DATE",           Edit: false, ColMerge :false}, //발생일자
		{Header : "MENU", Type:"Text",      MinWidth:60,   SaveName:"MENU_CD",              Edit: false, ColMerge :false, Hidden:true}, //화면명
		{Header : "MENU NM", Type:"Text",      MinWidth:60,   SaveName:"MENU_NM",              Edit: false, ColMerge :false}, //화면명
		{Header : "사용자ID", Type:"Text",      MinWidth:100,  SaveName:"REG_ID",               Edit: false, ColMerge :false, Hidden:true}, //USERID
		{Header : "사용자", Type:"Text",      MinWidth:60,   SaveName:"REG_NM",               Edit: false, ColMerge :false}, //USERNAME
		{Header : "요청API", Type:"Text",      MinWidth:200,  SaveName:"REQ_URI",              Edit: false, ColMerge :false},//API
		{Header : "오류내용", Type:"Text",      MinWidth:500,  SaveName:"MSG_TXT",              MultiLineText:true, Edit: false, ColMerge :false}//오류내용
	];
	
	var initData1 = {"cfg":{"AutoFitColWidth": "search|resize|init|colhidden|rowtransaction", "DeferredVScroll": 1}
    , "HeaderMode" : {Sort:1,ColMove:1,ColResize:1,HeaderCheck:1}
    , "Cols" : Cols
    };
	
	// IBSheet 생성
	createIBSheet2($('#div_mySheet1')[0], 'mySheet1', '100%', $(window).height()-200+'px');
	IBS_InitSheet(mySheet1, initData1);
	mySheet1.SetCountPosition(4);	
}

function doAction(sAction)
{
	switch(sAction)
	{
	case "R_main" :      //조회
		if(APPz.ui.isNull($("#SYS_CD").val())){
			duDialog(null,APPz.getMessage("E","data.Select.Required","시스템 조건을"));
			return;
		}
   		jsonParam = { param : APPz.ui.convertFormToJson("frm01") };
   		var url = "${pageContext.request.contextPath}/api/za/zaa/searchZ01SystemLog.do";
   		APPz.cmn.ApiRequest(url,jsonParam,true,($result)=>{
   			//debugger;
   			mySheet1.RemoveAll();
   			mySheet1.SetTreeCheckActionMode(1);
   			mySheet1.LoadSearchData({"data":$result.data}, {
   		        Sync: 1
   		    });
   			mySheet1.FitSize(false, true);
       	}); 
		break;
	case "Excel" :
		//var params = {"FileName":"excel2", "SheetName":"user", "HiddenColumn":0};
		//MenuSheet.Down2Excel(params);
		var params = {FileName:"SystemLog.xlsx",SheetName:"User",SheetDesign:1,Merge:1,OnlyHeaderMerge:1,DownRows:"",DownCols:""};
		MenuSheet.Down2Excel(params);
		break;
	}
}

function ChangeSize1(size){
	mySheet1.FitSize(false, true);
}


function mySheet1_OnSaveEnd(code, msg) {
	if(code<0){
		alert(msg);
	}else{
		mySheet1.FitSize(0,1);
	}
}
var windowHeight = $(window).height()-200;
</script>

	
<!--조회함수를 이용하여 조회 완료되었을때 발생하는 이벤트-->


<body class="bg-appz-body">
<form id="frm01" name="frm01" method="post">
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
                    <col style="width:8rem;">
                    <col>
                </colgroup>
                <tbody>
                    <tr>
                        <th>시스템목록</th>
                        <td>
                            <select name="SYS_CD" id="SYS_CD" class="wauto">
                                <option value="">선택</option>
                            </select>
                        </td>
                        <th>기간</th>
                        <td>
                        	<input type="text" class="datepicker" id="FROM_YMD" name="FROM_YMD" value="" maxlength="10" style="width:100px;" placeholder="시작일" readonly/>
                        	&nbsp;&nbsp;~&nbsp;&nbsp;
                        	<input type="text" class="datepicker" id="TO_YMD" name="TO_YMD" value="" maxlength="10" style="width:100px;" placeholder="종료일" readonly/>
                        
<!-- 		        			<table>
		        				<tr>
		        					<td><input type="text" class="input-jsys text-center border bg-secondary bg-opacity-10" data-toggle="datepicker" id="FROM_YMD" name="FROM_YMD" placeholder="시작일" readonly size="10"></td>
		        					<td>~</td>
		        					<td><input type="text" class="input-jsys text-center border bg-secondary bg-opacity-10" data-toggle="datepicker" id="TO_YMD" name="TO_YMD" placeholder="종료일" readonly size="10"></td>
		        				</tr>
		        			</table> -->

                        </td>
                    </tr>
                </tbody>
            </table>
        </div>		
		<div class="align both vm">
			<h2 class="h2">메뉴목록</h2>
			<div class="align right">
			<a href="javascript:doAction('R_main');" class="btn blue regular">조회</a>
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