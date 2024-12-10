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
			{Header : "STS", Type:"Status",Hidden:1,Width:60,SaveName:"SSTATUS"},
        	{Header : "DEL", Type:"DelCheck",Width:60},
  			{Header : "SysCd", Type:"Text", Width:80,SaveName:"SYS_CD", InsertEdit:1, UpdateEdit:0},
  			{Header : "Ord", Type:"Text",Width:80,SaveName:"APPL_ORD"},
  			{Header : "RoleCd", Type:"Text",Width:120,SaveName:"ROLE_CD"},
  			{Header : "RoleNm", Type:"Text",Width:160,SaveName:"ROLE_NM"},
  			{Header : "Reg\nYN", Type:"Text",Hidden:1,Width:80,SaveName:"REGI_ENABLE_YN"},
  			{Header : "Open\nYN", Type:"Combo",Width:60,SaveName:"USE_OPEN_YN",ComboText:"Y|N",ComboCode:"Y|N",PopupText:"Y|N",PopupCode:"Y|N"},
  			{Header : "Rem", Type:"Text",Width:200,SaveName:"REM"},
		];
		var initData1 = {"cfg":{"AutoFitColWidth": "search|resize|init|colhidden|rowtransaction", "DeferredVScroll": 1}
	    , "HeaderMode" : {Sort:1,ColMove:1,ColResize:1,HeaderCheck:1}
	    , "Cols" : Cols
	    };
		
		// IBSheet 생성
		createIBSheet2($('#div_RoleSheet')[0], 'RoleSheet', '100%', windowHeight/2-40+"px");
		IBS_InitSheet(RoleSheet, initData1);
		RoleSheet.SetCountPosition(4);		

		var Cols2 = [
			{Header : "선택", Type:"CheckBox",Width:45,Align:"center",SaveName:"CHECK"},
   			{Header : "CLSS", Type:"Text",Width:80,SaveName:"CLSS", InsertEdit:0, UpdateEdit:0},
   			{Header : "ITEM", Type:"Text",Width:80,SaveName:"ITEM_CLSS", InsertEdit:0, UpdateEdit:0},
   			{Header : "CODE", Type:"Text",Width:80,SaveName:"CODE", InsertEdit:0, UpdateEdit:0},
   			{Header : "이름", Type:"Text",Width:80,SaveName:"KOR_NAME", InsertEdit:0, UpdateEdit:0},
   			{Header : "부서명", Type:"Text",Width:80,SaveName:"DEPT_NM", InsertEdit:0, UpdateEdit:0},
   			{Header : "RoleCd", Type:"Text",Width:80,SaveName:"ROLE_CD", InsertEdit:0, UpdateEdit:0}
		];
		
		var initData2 = {"cfg":{"AutoFitColWidth": "search|resize|init|colhidden|rowtransaction", "DeferredVScroll": 1}
	    , "HeaderMode" : {Sort:1,ColMove:1,ColResize:1,HeaderCheck:1}
	    , "Cols" : Cols2
	    };
		
		// IBSheet 생성
		createIBSheet2($('#div_SearchSheet')[0], 'SearchSheet', '100%', windowHeight/2+100+"px");
		IBS_InitSheet(SearchSheet, initData2);
		SearchSheet.SetCountPosition(4);	

		var Cols3 = [
			{Header : "No", Type:"Seq",Width:45,Align:"center"},
			{Header : "STS", Type:"Status",Hidden:0,Width:60,SaveName:"SSTATUS"},
			{Header : "DEL", Type:"DelCheck",Width:60},
   			{Header : "SysCd", Type:"Text", Width:80,SaveName:"SYS_CD", InsertEdit:0, UpdateEdit:0},
   			{Header : "RoleCd", Type:"Text",Width:80,SaveName:"ROLE_CD", InsertEdit:0, UpdateEdit:0},
   			{Header : "Update Tm", Type:"Text",Width:80,SaveName:"UPDT_DATE", InsertEdit:0, UpdateEdit:0, Hidden:1},
   			{Header : "Item Clss", Type:"Text",Width:80,SaveName:"ITEM_CLSS", InsertEdit:0, UpdateEdit:0, Hidden:1},
   			{Header : "Class", Type:"Text",Width:80,SaveName:"ITEM_CLSS_NM", InsertEdit:0, UpdateEdit:0},
   			{Header : "Code", Type:"Text",Width:80,SaveName:"USER_ITEM", InsertEdit:0, UpdateEdit:0},
   			{Header : "CodeNm", Type:"Text",Width:80,SaveName:"USER_ITEM_NM", InsertEdit:0, UpdateEdit:0},
   			{Header : "I/E", Type:"Text",Width:80,SaveName:"INCL_CLSS", InsertEdit:0, UpdateEdit:0},
   			{Header : "Duty", Type:"Combo",Width:60,SaveName:"SYS_STAFF_YN",ComboText:"Y|N",ComboCode:"Y|N", InsertEdit:0, UpdateEdit:0},
   			{Header : "Rem", Type:"Text",Width:80,SaveName:"REM", InsertEdit:0, UpdateEdit:0}
		];
		
		var initData3 = {"cfg":{"AutoFitColWidth": "search|resize|init|colhidden|rowtransaction", "DeferredVScroll": 1}
	    , "HeaderMode" : {Sort:1,ColMove:1,ColResize:1,HeaderCheck:1}
	    , "Cols" : Cols3
	    };
		
		// IBSheet 생성
		createIBSheet2($('#div_SysUserSheet')[0], 'SysUserSheet', '100%', windowHeight+100+"px");
		IBS_InitSheet(SysUserSheet, initData3);
		SysUserSheet.SetCountPosition(4);		
}

function doAction(sAction)
{
	switch(sAction)
	{
		//조회
		case "roleInsert" :
			var row = RoleSheet.DataInsert();
			RoleSheet.SetCellValue(row, "SYS_CD", document.frm01.sysCd.value);
			
			break;
			
		case "R_roleR":
			jsonParam = { param : APPz.ui.convertFormToJson("frm01") };
       		var url = "${pageContext.request.contextPath}/api/za/zaa/selectZ01Role.do";
       		APPz.cmn.ApiRequest(url,jsonParam,true,($result)=>{
       			//
       			RoleSheet.RemoveAll();
       			//console.log(JSON.stringify($result.data));
       			RoleSheet.LoadSearchData({"data":$result.data}, {
       		        Sync: 1
       		    });
       			RoleSheet.FitSize(false, true);
           	}); 
			break;
		
		case "saveRole":
			if(RoleSheet.GetSaveString() != "KeyFieldError"){ //저장처리 할때 시트에서 걸러지면 무조건  KeyFieldError를 반환한다.
				var url = "${pageContext.request.contextPath}/api/za/zaa/saveZ01Role.do";
				duDialog(null, '저장 하시겠습니까 <i class="fas fa-question"></i>', {
					buttons: duDialog.OK_CANCEL,
						callbacks: {
							okClick: function(e) {
								this.hide()
								jsonParam = {param :{gridData : RoleSheet.GetSaveJson(0).data}};
							APPz.cmn.ApiRequest(url,jsonParam,true,function(result){
			   				if(result.rtnCode>=0){
			   					duDialog(null,"저장되었습니다.",{callbacks: {
				   						okClick: function(e) {
				   							this.hide();
				   							doAction('R_roleR');
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

		case "R_searchPeople":
			//currencySheet.RemoveAll();
			jsonParam = { param : APPz.ui.convertFormToJson("frm01") };
       		var url = "${pageContext.request.contextPath}/api/za/zaa/searchPeoplePersonal.do";
       		APPz.cmn.ApiRequest(url,jsonParam,true,($result)=>{
       			//
       			SearchSheet.RemoveAll();
       			//console.log(JSON.stringify($result.data));
       			SearchSheet.LoadSearchData({"data":$result.data}, {
       		        Sync: 1
       		    });
       			SearchSheet.FitSize(false, true);
           	}); 
			break;			
			
		case "addPeople":
			//currencySheet.RemoveAll();
			var row;
			if(APPz.ui.isNull($("#ROLE_CD").val())){
				alert('Role을 선택하시기 바랍니다.');
				return;
			}
			var Row1;
			for (var i = 1; i <= SearchSheet.RowCount(); i++) {
				if ("1" == SearchSheet.GetCellValue( i, "CHECK")) {
					/*
					var Row1 = SysUserSheet.FindText("USER_ITEM", SearchSheet.GetCellValue( i, "CODE"), 0);
					if(Row1==-1){//찾는 행이 없을 경우
						row = SysUserSheet.DataInsert(-1);
					}else{
						alert('금일자로 등록된 데이터가 있습니다.');
						return;
					}
					*/
					row = SysUserSheet.DataInsert(-1);
					SysUserSheet.SetCellValue( row, "SYS_CD", $("#SYS_CD").val());
					SysUserSheet.SetCellValue( row, "ROLE_CD", $("#ROLE_CD").val());
					SysUserSheet.SetCellValue( row, "ITEM_CLSS", SearchSheet.GetCellValue( i, "ITEM_CLSS"));
					SysUserSheet.SetCellValue( row, "ITEM_CLSS_NM", SearchSheet.GetCellValue( i, "CLSS"));
					SysUserSheet.SetCellValue( row, "USER_ITEM", SearchSheet.GetCellValue( i, "CODE"));
					SysUserSheet.SetCellValue( row, "USER_ITEM_NM", SearchSheet.GetCellValue( i, "KOR_NAME"));
				}
			}
			/*
			var duprows1 = SysUserSheet.ColValueDupRows("USER_ITEM");
			var arrRow = duprows1.split(",");
			if(arrRow.length>0){
				var dupVal = '';
				for (idx=0; idx<arrRow.length; idx++){ 
					dupVal += arrRow[idx]+"|";
				}
				SysUserSheet.RowDelete(dupVal);
			}
			*/
			SysUserSheet.FitColWidth();
			break;			
			
		case "R_sysUserR":
			jsonParam = { param : APPz.ui.convertFormToJson("frm01") };
       		var url = "${pageContext.request.contextPath}/api/za/zaa/searchSysUserList.do";
       		APPz.cmn.ApiRequest(url,jsonParam,true,($result)=>{
       			//
       			SysUserSheet.RemoveAll();
       			//console.log(JSON.stringify($result.data));
       			SysUserSheet.LoadSearchData({"data":$result.data}, {
       		        Sync: 1
       		    });
       			SysUserSheet.FitSize(0, 1);
           	}); 			
			break;
			
		case "saveSysUser":
			if(SysUserSheet.GetSaveString() != "KeyFieldError"){ //저장처리 할때 시트에서 걸러지면 무조건  KeyFieldError를 반환한다.
				var url = "${pageContext.request.contextPath}/api/za/zaa/saveZ01SysUser.do";
				duDialog(null, '저장 하시겠습니까 <i class="fas fa-question"></i>', {
					buttons: duDialog.OK_CANCEL,
						callbacks: {
							okClick: function(e) {
								this.hide()
								jsonParam = {param :{gridData : SysUserSheet.GetSaveJson(0).data}};
							APPz.cmn.ApiRequest(url,jsonParam,true,function(result){
			   				if(result.rtnCode>=0){
			   					duDialog(null,"저장되었습니다.",{callbacks: {
				   						okClick: function(e) {
				   							this.hide();
				   							doAction('R_sysUserR');
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
	}
}

function ChangeSize1(size){
	mySheet1.FitSize(false, true);
}


function RoleSheet_OnDblClick(Row, Col, CellX, CellY, CellW, CellH) {
	//행을 더블 클릭했을 때 다른 페이지로 이동하도록 처리
	$('#SYS_CD').val(RoleSheet.GetCellValue(Row, "SYS_CD"));
	$('#ROLE_CD').val(RoleSheet.GetCellValue(Row, "ROLE_CD"));
	doAction('R_sysUserR');   
}

var windowHeight = $(window).height()-200;
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
<!-- (wrapper) -->
<div id="wrap">
	<!-- (content) -->
	<div class="content">
<sys:headinfo prgmcd="${_prgmcd_}"/>
		<div class="align both vm">
			<h2 class="h2">메뉴목록</h2>
			<div class="align right">
			</div>
		</div>
		<table style="boarder:3px">
		<colgroup>
		<col width="50%">	
		<col width="50%">
		</colgroup>
		<tbody>
		<tr>
			<td class='TDL' align='right' style="height:20px">
                <select name="SYS_CD" id="SYS_CD" class="wauto">
                    <option value="">선택</option>
                </select>
				<a href="javascript:doAction('R_roleR');" class="btn blue regular">조회</a>
				<a href="javascript:doAction('roleInsert');" class="btn blue regular">신규</a>
				<a href="javascript:doAction('saveRole');" class="btn blue regular">저장</a>
			</td>
			<td class='TDL' align='right' style="height:20px">
				<input type="text" id="ROLE_CD" name="ROLE_CD" value="" readonly>
				<a href="javascript:doAction('saveSysUser');" class="btn blue regular">저장</a>
			</td>
		</tr>
		<tr>
			<td class='TDL' align='left' style="height:120px">
		        <div id="div_RoleSheet" style="width:100%">
		        </div>				
			</td>
			<td class='TDL' align='left' style="height:360px" rowspan="3">
		        <div id="div_SysUserSheet" style="width:100%">
		        </div>				
			</td>
			
		</tr>
		<tr>
			<th class='TDL' align='right' style="height:20px" width="100%">
				<input type="hidden" name="clss" value="0">
				<input name="rd_test" type="radio" value="0" onclick="evRdTest(this);" checked="true" />사용자      
				<input name="rd_test" type="radio" value="1" onclick="evRdTest(this);" />팀
				<input name="rd_test" type="radio" value="2" onclick="evRdTest(this);" />부문/담당/실

				<input type="text" name="cond" style="width:100px" value="" onkeypress="if(event.keyCode == 13) doAction('R_searchPeople');">
				<a href="javascript:doAction('R_searchPeople');" class="btn blue regular">조회</a>
				<a href="javascript:doAction('addPeople');" class="btn blue regular">추가</a>
			</th>
		</tr>	
		<tr>
			<td class='TDL' align='left' style="height:200px">
		        <div id="div_SearchSheet" style="width:100%">
		        </div>				
			</td>
		</tr>	
		</table>		
	</div>
	<!-- //(content) -->

</div>
<!-- //(wrapper) -->

</form>
</body>
</html>