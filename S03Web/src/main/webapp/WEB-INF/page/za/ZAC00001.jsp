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
				{Header : "CHK", Type:"CheckBox",Width:45,Align:"center",SaveName:"CHECK"},
      			{Header : "System", Type:"Text",Edit:0,Width:80,SaveName:"sysCd", Edit:false},
      			{Header : "Item Clss", Type:"Text",Width:80,SaveName:"itemClss", Edit:false},
      			{Header : "WinCd", Type:"Text",Width:80,SaveName:"itemCd", Edit:false},
      			{Header : "WinNm", Type:"Text",Width:80,SaveName:"itemNm", TreeCol:1, Edit:false, LevelSaveName:"TREELEVEL"},
      			{Header : "URL", Type:"Text",Width:80,SaveName:"url", Edit:false},
      			{Header : "Menu CD", Type:"Text",Width:80,SaveName:"menuCd", Edit:false},
      			{Header : "CLSS", Type:"Text",Width:80,SaveName:"clss", Edit:false},
      			{Header : "EXISTYN", Type:"Text",Width:80,SaveName:"existYn", Edit:false},
      			{Header : "level", Type:"Int",           Width:200,SaveName:"level", Hidden:1, Edit:false},
		];
		
		var initData2 = {"cfg":{"AutoFitColWidth": "search|resize|init|colhidden|rowtransaction", "DeferredVScroll": 1}
	    , "HeaderMode" : {Sort:1,ColMove:1,ColResize:1,HeaderCheck:1}
	    , "Cols" : Cols2
	    };		
		// IBSheet 생성
		createIBSheet2($('#div_MenuSheet')[0], 'MenuSheet', '100%', windowHeight/2+100+"px");
		IBS_InitSheet(MenuSheet, initData2);
		MenuSheet.SetCountPosition(4);
		
		var Cols3 = [
				{Header : "No", Type:"Seq",Width:45,Align:"center"},
				{Header : "STS", Type:"Status",Width:60,SaveName:"SSTATUS",Hidden:1},
				{Header : "DEL", Type:"DelCheck",Width:50},
      			{Header : "System", Type:"Text",Edit:0,Width:80,SaveName:"SYS_CD",Hidden:true, InsertEdit:0, UpdateEdit:0},
      			{Header : "Role CD", Type:"Text",Width:80,SaveName:"ROLE_CD",Hidden:false, InsertEdit:0, UpdateEdit:0},
      			{Header : "Item NM", Type:"Text",Width:80,SaveName:"ITEM_CLSS_NM",Hidden:true, InsertEdit:0, UpdateEdit:0},
      			{Header : "Win Priv", Type:"Text",Width:80,SaveName:"WIN_PRIV",Hidden:false, InsertEdit:0, UpdateEdit:0},
      			{Header : "Role NM", Type:"Text",Width:80,SaveName:"ROLE_NM",Hidden:false, InsertEdit:0, UpdateEdit:0},
      			{Header : "URL", Type:"Text",Width:160,SaveName:"URL", InsertEdit:0, UpdateEdit:0},
      			{Header : "M/W", Type:"Text",Width:160,SaveName:"ITEM_CLSS", InsertEdit:0, UpdateEdit:0},
      			{Header : "Item", Type:"Text",Width:80,SaveName:"ROLE_ITEM", InsertEdit:0, UpdateEdit:0},      			
      			{Header : "MENU명", Type:"Text",Width:80,SaveName:"MENU_NM", InsertEdit:0, UpdateEdit:0},
      			{Header : "C\nCreate", Type:"CheckBox",Width:80,SaveName:"C"},//CREATE
      			{Header : "R\nRead", Type:"CheckBox",Width:80,SaveName:"R"},//READ
      			{Header : "U\nUpdate", Type:"CheckBox",Width:80,SaveName:"U"},//UPDATE
      			{Header : "D\nDelete", Type:"CheckBox",Width:80,SaveName:"D"},//DELETE
      			{Header : "E\nExcel", Type:"CheckBox",Width:80,SaveName:"E"},//Excel
      			{Header : "A\nApprove", Type:"CheckBox",Width:80,SaveName:"A"},//APPROVE
      			{Header : "J\nReject", Type:"CheckBox",Width:80,SaveName:"J"}//REJECT
		];
		
		var initData3 = {"cfg":{"AutoFitColWidth": "search|resize|init|colhidden|rowtransaction", "DeferredVScroll": 1}
	    , "HeaderMode" : {Sort:1,ColMove:1,ColResize:1,HeaderCheck:1}
	    , "Cols" : Cols3
	    };		
		// IBSheet 생성
		createIBSheet2($('#div_RoleItemSheet')[0], 'RoleItemSheet', '100%', windowHeight+100+"px");
		IBS_InitSheet(RoleItemSheet, initData3);
		RoleItemSheet.SetCountPosition(4);
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
       			//debugger;
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

		case "R_menuListR":
			jsonParam = { param : APPz.ui.convertFormToJson("frm01") };
       		var url = "${pageContext.request.contextPath}/api/za/zaa/searchSysMenuForRole.do";
       		APPz.cmn.ApiRequest(url,jsonParam,true,($result)=>{
       			//debugger;
       			MenuSheet.RemoveAll();
       			MenuSheet.SetTreeCheckActionMode(1);
       			//console.log(JSON.stringify($result.data));
       			MenuSheet.LoadSearchData({"data":$result.data}, {
       		        Sync: 1
       		    });
       			MenuSheet.FitSize(false, true);
           	}); 			
			break;			
			
		case "addMenu":
			//currencySheet.RemoveAll();		
			var row;
			
			if(APPz.ui.isNull($("#ROLE_CD").val())){
				duDialog(null,APPz.getMessage("E","data.Select.Required","Role 조건을"));
				return;
			}
			var sRow = MenuSheet.FindCheckedRow("CHECK");
			var arrRow = sRow.split("|");

			for(idx=0; idx<=arrRow.length-1; idx++){ 
				row = RoleItemSheet.DataInsert();
				RoleItemSheet.SetCellValue( row, "SYS_CD", $("#SYS_CD").val());
				RoleItemSheet.SetCellValue( row, "ROLE_CD", $("#ROLE_CD").val());
				RoleItemSheet.SetCellValue( row, "STATUS", "List");
				RoleItemSheet.SetCellValue( row, "ITEM_CLSS", MenuSheet.GetCellValue( arrRow[idx], "itemClss"));
				RoleItemSheet.SetCellValue( row, "ROLE_ITEM", MenuSheet.GetCellValue( arrRow[idx], "itemCd"));
				RoleItemSheet.SetCellValue( row, "ROLE_ITEM_NM", MenuSheet.GetCellValue( arrRow[idx], "itemNm"));
				RoleItemSheet.SetCellValue( row, "WIN_PRIV", '1');			
				RoleItemSheet.SetCellValue( row, "R", '1');			
			}
			break;		
			
		case "R_roleItemR":
			jsonParam = { param : APPz.ui.convertFormToJson("frm01") };
       		var url = "${pageContext.request.contextPath}/api/za/zaa/searchZ01RoleItem.do";
       		APPz.cmn.ApiRequest(url,jsonParam,true,($result)=>{
       			//debugger;
       			RoleItemSheet.RemoveAll();
       			//console.log(JSON.stringify($result.data));
       			RoleItemSheet.LoadSearchData({"data":$result.data}, {
       		        Sync: 1
       		    });
       			RoleItemSheet.FitSize(false, true);
           	}); 				
			break;
		case "saveRoleItem":
			if(RoleItemSheet.GetSaveString() != "KeyFieldError"){ //저장처리 할때 시트에서 걸러지면 무조건  KeyFieldError를 반환한다.
				var url = "${pageContext.request.contextPath}/api/za/zaa/saveZ01RoleItem.do";
				duDialog(null, '저장 하시겠습니까 <i class="fas fa-question"></i>', {
					buttons: duDialog.OK_CANCEL,
						callbacks: {
							okClick: function(e) {
								this.hide()
								jsonParam = {param :{gridData : RoleItemSheet.GetSaveJson(0).data}};
							APPz.cmn.ApiRequest(url,jsonParam,true,function(result){
			   				if(result.rtnCode>=0){
			   					duDialog(null,"저장되었습니다.",{callbacks: {
				   						okClick: function(e) {
				   							this.hide();
				   							doAction('R_roleItemR');
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

function MenuSheet_OnSearchEnd(code, message) {

	for ( var i = 1; i <= MenuSheet.RowCount(); i++ ) {
		if ("Y" == MenuSheet.GetCellValue( i, "existYn" )) {
			MenuSheet.SetRowEditable(i, 'false');
		}
	}

}


function RoleSheet_OnDblClick(Row, Col, CellX, CellY, CellW, CellH) {
	//행을 더블 클릭했을 때 다른 페이지로 이동하도록 처리
	$('#SYS_CD').val(RoleSheet.GetCellValue(Row, "SYS_CD"));
	$('#ROLE_CD').val(RoleSheet.GetCellValue(Row, "ROLE_CD"));
	doAction('R_roleItemR');   
}

function RoleItemSheet_OnChange(Row, Col, Value, OldValue, RaiseFlag) {
	var colnm = RoleItemSheet.ColSaveName(Col);
	var tempVal = '';
	if('C'==colnm||'R'==colnm||'U'==colnm||'D'==colnm||'E'==colnm||'A'==colnm||'J'==colnm){
    	var curMap = RoleItemSheet.GetRowJson(RoleItemSheet.GetSelectRow());

    	tempVal = ((curMap.C==1) ? "C" : "")
		        + ((curMap.R==1) ? "R" : "")
		        + ((curMap.U==1) ? "U" : "")
    	        + ((curMap.D==1) ? "D" : "")
    	        + ((curMap.E==1) ? "E" : "")
		        + ((curMap.A==1) ? "A" : "")
		        + ((curMap.J==1) ? "J" : "");
    	RoleItemSheet.SetCellValue(Row, "WIN_PRIV", tempVal);
    	/*
    	*/
	}
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
				<a href="javascript:doAction('saveRoleItem');" class="btn blue regular">저장</a>
			</td>
		</tr>
		<tr>
			<td class='TDL' align='left' style="height:120px">
		        <div id="div_RoleSheet" style="width:100%">
		        </div>				
			</td>
			<td class='TDL' align='left' style="height:360px" rowspan="3">
		        <div id="div_RoleItemSheet" style="width:100%">
		        </div>				
			</td>
			
		</tr>
		<tr>
			<th class='TDL' align='right' style="height:20px" width="100%">
				<input type="hidden" name="clss" value="1">
				<input name="rd_test" type="radio" value="1" onclick="evRdTest(this);" checked="true" />Window  
				<input name="rd_test" type="radio" value="2" onclick="evRdTest(this);" />Menu

				<input type="text" name="cond" style="width:100px" value="" onkeypress="if(event.keyCode == 13) doAction('R_menuListR');">
				<a href="javascript:doAction('R_menuListR');" class="btn blue regular">조회</a>
				<a href="javascript:doAction('addMenu');" class="btn blue regular">추가</a>
			</th>
		</tr>	
		<tr>
			<td class='TDL' align='left' style="height:200px">
		        <div id="div_MenuSheet" style="width:100%">
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