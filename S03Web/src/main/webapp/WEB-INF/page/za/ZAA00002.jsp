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
	LoadPage();
});


function LoadPage() {
	var Cols = [
			{Header : "No", Type:"Seq",Width:45,Align:"center"},
			{Header : "STS", Type:"Status",Hidden:"1", Width:60,SaveName:"SSTATUS"},
        	{Header : "DEL", Type:"DelCheck",Width:60},
  			{Header : "SysCd", Type:"Text", Width:80,SaveName:"SYS_CD", KeyField:1},
  			{Header : "SysNm", Type:"Text",Width:100,SaveName:"SYS_NM", KeyField:1},
  			{Header : "Ord", Type:"Text",Width:200,SaveName:"DISP_ORD", KeyField:1},
  			{Header : "Open\nYN", Type:"Combo",Width:60,SaveName:"USE_OPEN_YN",ComboText:"Y|N",ComboCode:"Y|N",PopupText:"Y|N",PopupCode:"Y|N"},
  			{Header : "Prevent\nYn", Type:"Combo",Width:60,SaveName:"USE_PREVENT_YN",ComboText:"Y|N",ComboCode:"Y|N",PopupText:"Y|N",PopupCode:"Y|N"},
  			{Header : "DBID", Type:"Text",Width:200,SaveName:"DB_USER_ID"},
  			{Header : "DBPwd", Type:"Text",Width:200,SaveName:"DB_USER_PASSWD"},
  			{Header : "DBSrv\nNm", Type:"Text",Width:200,SaveName:"DB_SVR_NM"},
  			{Header : "Idle\nTime", Type:"Text",Width:200,SaveName:"IDLE_TIME"},
  			{Header : "Dev\nTool", Type:"Combo",Width:60,SaveName:"DEV_TOOL",ComboText:"WEB|CS",ComboCode:"W|C",PopupText:"WEB|CS",PopupCode:"W|C"},
  			{Header : "RemA", Type:"Text",Width:200,SaveName:"A_REM"},
  			{Header : "RemB", Type:"Text",Width:200,SaveName:"B_REM"},
  			{Header : "RemC", Type:"Text",Width:200,SaveName:"C_REM"},
  			{Header : "RemD", Type:"Text",Width:200,SaveName:"D_REM"},
  			{Header : "RemE", Type:"Text",Width:200,SaveName:"E_REM"},
  			{Header : "Rem", Type:"Text",Width:200,SaveName:"REM"},
  			{Header : "SysInfo", Type:"Text",Width:200,SaveName:"SYS_INFO"},
  			{Header : "Login\nInfoYn", Type:"Combo",Width:60,SaveName:"LOGIN_INFO_YN",ComboText:"Y|N",ComboCode:"Y|N",PopupText:"Y|N",PopupCode:"Y|N"},
  			{Header : "Auto\nRole", Type:"Combo",Width:60,SaveName:"AUTO_ROLE",ComboText:"Y|N",ComboCode:"Y|N",PopupText:"Y|N",PopupCode:"Y|N"}
	];
	var initData1 = {"cfg":{"AutoFitColWidth": "search|resize|init|colhidden|rowtransaction", "DeferredVScroll": 1}
    , "HeaderMode" : {Sort:1,ColMove:1,ColResize:1,HeaderCheck:1}
    , "Cols" : Cols
    };
	
	// IBSheet 생성
	createIBSheet2($('#div_mySheet1')[0], 'mySheet1', '100%', $(window).height()-100+'px');
	IBS_InitSheet(mySheet1, initData1);
	mySheet1.SetCountPosition(4);
}

function doAction(sAction)
{
	switch(sAction)
	{
		//조회
		case "Search":
			//retName("crud").value = "R_MainR"; 
			//mySheet1.DoSearch("/api/za/zaa/getSystemList.do", APPz.ui.convertFormToJson(document.forms[0]));
       		//mySheet1.DoSearch("${pageContext.request.contextPath}/api/za/zaa/getSystemList.do"
       		//		, APPz.ui.convertFormToJson("frm01"), {ReqHeader : {"Content-Type":"application/json"}});
       			debugger;
       		jsonParam = { param : $("frm01").inputValues() };
       		var url = "${pageContext.request.contextPath}/api/za/zaa/getSystemList.do";
       		APPz.cmn.ApiRequest(url,jsonParam,true,($result)=>{
       			mySheet1.LoadSearchData({"data":$result.systemList});
           	});
			break;
			
		case "Save":
			//var SaveJson = JSON.stringify(mySheet1.GetSaveJson(0));
			if($("#MakerSheet").val == "KeyFieldError"){ //저장처리 할때 시트에서 걸러지면 무조건  KeyFieldError를 반환한다.
				return;
			}
			jsonParam = {
					param : 
					{
							gridData : mySheet1.GetSaveJson(0).data
					}
	   	    };
			var url = "${pageContext.request.contextPath}/api/za/zaa/saveSystemList.do";
   			duDialog(null,APPz.getMessage("C","confirm.DoProcess","저장"), {
   				buttons: duDialog.OK_CANCEL,
   				callbacks: {
   					okClick:function(e){
   						this.hide()
   						APPz.cmn.ApiRequest(url,jsonParam,true,function(result){//메뉴저장
   							debugger;
			   				if(result.rtnCode>=0){
			   					duDialog(null,"저장되었습니다.",{
				   					callbacks: {
				   						okClick: function(e) {
				   							this.hide();
				   							doAction('Search');
				   						}
				   					}
				   				});
			   				}else{
			   					duDialog("저장실패",result.message,{callbacks: {okClick: function(e) {this.hide()}}
			   					});
			   				}
			   			});

   					}
   				}
   			});
			break;
		//적용 
		case "Add":
			var row = mySheet1.DataInsert(-1);
			var rowcnt = mySheet1.RowCount()+'';
			mySheet1.SetCellValue(row, "DISP_ORD", rowcnt.LPad(2, "0"));
			break;
	}
}

function ChangeSize1(size){
	mySheet1.FitSize(false, true);
}

function mySheet1_OnSearchEnd(code,msg){
	if(code<0){
		alert(msg);
	}else{
		//CodeMSheet.FitColWidth();
		mySheet1.FitSize(false, true);
	}
}

function mySheet1_OnSaveEnd(code, msg) {
	if(code<0){
		alert(msg);
	}else{
		mySheet1.FitSize(0,1);
	}
}
var windowHeight = $(window).height()-150;
</script>

	
<!--조회함수를 이용하여 조회 완료되었을때 발생하는 이벤트-->


<body class="bg-appz-body">
<form id="frm01" name="frm01" method="post">
<input type="hidden" id="crud" name="crud" value="12312">
<input type="hidden" name="classCd" value="">
<input type="hidden" name="classNm" value="">
<input type="hidden" name="classDtlCd" value="">
<input type="hidden" name="classDtlNm" value="">
<input type="hidden" id="mySheet1Val" name="mySheet1Val" value="" readonly="readonly"/>
<sys:headinfo prgmcd="${_prgmcd_}"/>
<!-- (wrapper) -->
<div id="wrap">
	<!-- (content) -->
	<div class="content">
		<div class="align both vm">
			<h2 class="h2">목록</h2>
			<div>
			<a href="javascript:doAction('Search');" class="btn blue regular">조회</a>
			<a href="javascript:doAction('Add');" class="btn blue regular">추가</a>
			<a href="javascript:doAction('Save');" class="btn blue regular">저장</a>
			</div>
		</div>
        <div id="div_mySheet1" style="width:100%">
        </div>	
	</div>
	<!-- //(content) -->

</div>
<!-- //(wrapper) -->

</form>
</body>
</html>