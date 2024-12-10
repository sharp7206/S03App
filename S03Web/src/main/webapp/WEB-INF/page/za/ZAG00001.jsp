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
}

/*Sheet 기본 설정 */
function LoadPage(){
	var Cols = [
			{Header : "No", Type:"Seq",Width:45,Align:"center"},
			{Header : "STS", Type:"Status",Hidden:"1", Width:60,SaveName:"SSTATUS"},
        	{Header : "Del", Type:"DelCheck",Width:60},
  			{Header : "SysCd", Type:"Text", Width:80,SaveName:"SYS_CD", InsertEdit:0, UpdateEdit:0},
  			{Header : "JobId", Type:"Text",Width:100,SaveName:"JOB_ID", InsertEdit:0, UpdateEdit:0},
  			{Header : "JobNm", Type:"Text",Width:200,SaveName:"JOB_NM", KeyField:1},
  			{Header : "cronExp", Type:"Text",Width:200,SaveName:"CRON_EXP", KeyField:1},
  			{Header : "설명", Type:"Text",Width:200,SaveName:"REM"},
  			{Header : "Run Y/N", Type:"Combo",Width:60,SaveName:"RUN_YN",ComboText:"Y|N",ComboCode:"Y|N",PopupText:"Y|N",PopupCode:"Y|N"},
  			{Header : "Use Y/N", Type:"Combo",Width:60,SaveName:"USE_YN",ComboText:"Y|N",ComboCode:"Y|N",PopupText:"Y|N",PopupCode:"Y|N"},
  			{Header : "Register", Type:"Text",Width:120,SaveName:"REG_ID", InsertEdit:0, UpdateEdit:0},
  			{Header : "Register Tm", Type:"Text",Width:120,SaveName:"REG_TM", InsertEdit:0, UpdateEdit:0},
  			{Header : "Modifier", Type:"Text",Width:120,SaveName:"UPDATE_NM", InsertEdit:0, UpdateEdit:0},
  			{Header : "Update Tm", Type:"Text",Width:120,SaveName:"UPDATE_TM", InsertEdit:0, UpdateEdit:0}
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
    case "R_Main":      //조회
      	jsonParam = { param : APPz.ui.convertFormToJson("frm01") };
   		var url = "${pageContext.request.contextPath}/api/za/zaa/searchZ01JobList.do";
   		APPz.cmn.ApiRequest(url,jsonParam,true,($result)=>{
   			mySheet1.RemoveAll();
   			mySheet1.LoadSearchData({"data":$result.data}, {
   		        Sync: 1
   		    });
   			mySheet1.FitSize(false, true);
       	});       	
      break;
      
	case "Insert" :
		var row = mySheet1.DataInsert();
		mySheet1.SetCellValue(row, "SYS_CD", $("#SYS_CD").val());
		mySheet1.SetCellValue(row, "USE_YN", "Y");
		break;
		
	case "C_Main":
		if(mySheet1.GetSaveString() != "KeyFieldError"){ //저장처리 할때 시트에서 걸러지면 무조건  KeyFieldError를 반환한다.
			var url = "${pageContext.request.contextPath}/api/za/zaa/saveZ01SchInfo.do";
			duDialog(null, '저장 하시겠습니까 <i class="fas fa-question"></i>', {
				buttons: duDialog.OK_CANCEL,
					callbacks: {
						okClick: function(e) {
							this.hide()
							jsonParam = {param :{gridData : mySheet1.GetSaveJson(0).data}};
						APPz.cmn.ApiRequest(url,jsonParam,true,function(result){
		   				if(result.rtnCode>=0){
		   					duDialog(null,"저장되었습니다.",{callbacks: {
			   						okClick: function(e) {
			   							this.hide();
			   							doAction('R_Main');
			   						}
			   					}
			   				});
		   				}else{
		   					duDialog("저장실패",result.Message,{callbacks: {
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
	case "E_Main": //스케즐러실행
		if(mySheet1.GetSaveString() != "KeyFieldError"){ //저장처리 할때 시트에서 걸러지면 무조건  KeyFieldError를 반환한다.
			var url = "${pageContext.request.contextPath}/api/cmn/common/updateSchedule.do";
			duDialog(null, '저장 하시겠습니까 <i class="fas fa-question"></i>', {
				buttons: duDialog.OK_CANCEL,
					callbacks: {
						okClick: function(e) {
							this.hide()
							jsonParam = {};
						APPz.cmn.ApiRequest(url,jsonParam,true,function(result){
		   				if(result.rtnCode>=0){
		   					duDialog(null,"반영되었습니다.",{callbacks: {
			   						okClick: function(e) {
			   							this.hide();
			   							doAction('R_Main');
			   						}
			   					}
			   				});
		   				}else{
		   					duDialog("반영실패",result.Message,{callbacks: {
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
	case "DownExcel1":
		var params = {FileName:"codeM.xls",SheetName:"sub Total",Merge:1,SheetDesign:1 };
		mySheet1.Down2Excel(params);
		break;
	}
}

function ChangeSize1(size){
	mySheet1.FitSize(false, true);
}


var windowHeight = $(window).height()-200;
</script>

	
<!--조회함수를 이용하여 조회 완료되었을때 발생하는 이벤트-->


<body class="bg-appz-body">
<form id="frm01" name="frm01" method="post">
<input type="hidden" id="crud" name="crud" value="12312">
<input type="hidden" id="CLASS_CD" name="CLASS_CD" value="">
<input type="hidden" id="CLASS_NM" name="CLASS_NM" value="">
<input type="hidden" name="classDtlCd" value="">
<input type="hidden" name="classDtlNm" value="">
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
                            <select name="SYS_CD" id="SYS_CD" class="wauto">
                                <option value="">선택</option>
                            </select>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>		
		<div class="align both vm">
			<h2 class="h2">스케즐목록</h2>
			<div class="align right">
			<a href="javascript:doAction('R_Main');" class="btn blue regular">조회</a>
			<a href="javascript:doAction('Insert');" class="btn blue regular">신규</a>
			<a href="javascript:doAction('C_Main');" class="btn blue regular">저장</a>
			<a href="javascript:doAction('E_Main');" class="btn red regular">스케즐반영</a>
			<a href="javascript:doAction('DownExcel1');" class="btn blue regular">Excel</a>
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