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
	var Cols1 = [
			{Header : "No", Type:"Seq",Width:45,Align:"center"},
			{Header : "STS", Type:"Status",Hidden:"1", Width:60,SaveName:"SSTATUS"},
        	{Header : "Del", Type:"DelCheck",Width:60},
  			{Header : "SysCd", Type:"Text", Width:80,SaveName:"SYS_CD", InsertEdit:0, UpdateEdit:0},
  			{Header : "Code", Type:"Text",Width:100,SaveName:"CLASS_CD", KeyField:1},
  			{Header : "CodeNm", Type:"Text",Width:200,SaveName:"CLASS_NM", KeyField:1},
  			{Header : "CodeENm", Type:"Text",Width:200,SaveName:"CLASS_E_NM"},
  			{Header : "CodeCNm", Type:"Text",Width:200,SaveName:"CLASS_C_NM"},
  			{Header : "Desc", Type:"Text",Width:150,SaveName:"CLASS_DESC"},
  			{Header : "Order", Type:"Int",Width:80,SaveName:"ORDER_NO"},
  			{Header : "UseYN", Type:"Combo",Width:60,SaveName:"USE_YN",ComboText:"Y|N",ComboCode:"Y|N",PopupText:"Y|N",PopupCode:"Y|N"},
  			{Header : "RegNm", Type:"Text",Width:120,SaveName:"REG_NM", InsertEdit:0, UpdateEdit:0},
  			{Header : "RegTm", Type:"Text",Width:120,SaveName:"REG_TM", InsertEdit:0, UpdateEdit:0},
  			{Header : "UptNm", Type:"Text",Width:120,SaveName:"UPDATE_NM", InsertEdit:0, UpdateEdit:0},
  			{Header : "UptTm", Type:"Text",Width:120,SaveName:"UPDATE_TM", InsertEdit:0, UpdateEdit:0}
	];
	
	var initData1 = {"cfg":{"AutoFitColWidth": "search|resize|init|colhidden|rowtransaction", "DeferredVScroll": 1}
    , "HeaderMode" : {Sort:1,ColMove:1,ColResize:1,HeaderCheck:1}
    , "Cols" : Cols1
    };
	
	// IBSheet 생성
	createIBSheet2($('#div_CodeMSheet')[0], 'CodeMSheet', '100%', windowHeight/2-50+"px");
	IBS_InitSheet(CodeMSheet, initData1);
	CodeMSheet.SetCountPosition(4);	

	var Cols2 = [
			{Header : "No", Type:"Seq",Width:45,Align:"center"},
			{Header : "STS", Type:"Status",Hidden:"1",Width:60,SaveName:"SSTATUS"},
        	{Header : "Del", Type:"DelCheck",Width:60},
  			{Header : "SysCd", Type:"Text",Edit:0,Width:80,SaveName:"SYS_CD", InsertEdit:0, UpdateEdit:0},
  			{Header : "Code", Type:"Text",Width:100,SaveName:"CLASS_CD", InsertEdit:0, UpdateEdit:0},
  			{Header : "CodeNm", Type:"Text",Width:200,SaveName:"CLASS_NM", InsertEdit:0, UpdateEdit:0},
  			{Header : "Order", Type:"Int",Width:80,SaveName:"ORDER_NO"},
  			{Header : "Detail Cd", Type:"Text",Width:120,SaveName:"CLASS_DTL_CD", KeyField:1},
  			{Header : "Detail CdNm", Type:"Text",Width:200,SaveName:"CLASS_DTL_NM", KeyField:1},
  			{Header : "Detail CdENm", Type:"Text",Width:200,SaveName:"CLASS_DTL_ENM", KeyField:1},
  			{Header : "Detail CdCNm", Type:"Text",Width:200,SaveName:"CLASS_DTL_CNM", KeyField:1},
  			{Header : "Etc1", Type:"Text",Width:200,SaveName:"ETC_CD1"},
  			{Header : "Etc1", Type:"Text",Width:200,SaveName:"ETC_CD2"},
  			{Header : "Etc1", Type:"Text",Width:200,SaveName:"ETC_CD3"},
  			{Header : "Etc1", Type:"Text",Width:200,SaveName:"ETC_CD4"},
  			{Header : "UseYN", Type:"Combo",Width:60,SaveName:"USE_YN",ComboText:"Y|N",ComboCode:"Y|N",PopupText:"Y|N",PopupCode:"Y|N"},
  			{Header : "RegNm", Type:"Text",Width:120,SaveName:"REG_NM", Hidden:"1", InsertEdit:0, UpdateEdit:0},
  			{Header : "RegTm", Type:"Text",Width:120,SaveName:"REG_TM", Hidden:"1", InsertEdit:0, UpdateEdit:0},
  			{Header : "UptNm", Type:"Text",Width:120,SaveName:"UPDATE_NM", Hidden:"1", InsertEdit:0, UpdateEdit:0},
  			{Header : "UptTm", Type:"Text",Width:120,SaveName:"UPDATE_TM", Hidden:"1", InsertEdit:0, UpdateEdit:0}
	];
	var initData2 = {"cfg":{"AutoFitColWidth": "search|resize|init|colhidden|rowtransaction", "DeferredVScroll": 1}
    , "HeaderMode" : {Sort:1,ColMove:1,ColResize:1,HeaderCheck:1}
    , "Cols" : Cols2
    };
	
	// IBSheet 생성
	createIBSheet2($('#div_CodeDSheet')[0], 'CodeDSheet', '100%', windowHeight/2+50+"px");
	IBS_InitSheet(CodeDSheet, initData2);
	CodeDSheet.SetCountPosition(4);	
}

function doAction(sAction)
{
	switch(sAction)
	{
    case "R_CodeMR":      //조회
      	jsonParam = { param : APPz.ui.convertFormToJson("frm01") };
   		var url = "${pageContext.request.contextPath}/api/za/zaa/searchZ01CodeMaster.do";
   		APPz.cmn.ApiRequest(url,jsonParam,true,($result)=>{
   			CodeMSheet.RemoveAll();
   			CodeMSheet.LoadSearchData({"data":$result.data}, {
   		        Sync: 1
   		    });
   			CodeMSheet.FitSize(false, true);
       	});       	
      break;
      
	case "codeMInsert" :
		var row = CodeMSheet.DataInsert();
		CodeMSheet.SetCellValue(row, "SYS_CD", $("#SYS_CD").val());
		CodeMSheet.SetCellValue(row, "USE_YN", "Y");
		break;
		
	case "saveMaster":
		if(CodeMSheet.GetSaveString() != "KeyFieldError"){ //저장처리 할때 시트에서 걸러지면 무조건  KeyFieldError를 반환한다.
			var url = "${pageContext.request.contextPath}/api/za/zaa/saveZ01CodeMaster.do";
			duDialog(null, '저장 하시겠습니까 <i class="fas fa-question"></i>', {
				buttons: duDialog.OK_CANCEL,
					callbacks: {
						okClick: function(e) {
							this.hide()
							jsonParam = {param :{gridData : CodeMSheet.GetSaveJson(0).data}};
						APPz.cmn.ApiRequest(url,jsonParam,true,function(result){
		   				if(result.rtnCode>=0){
		   					duDialog(null,"저장되었습니다.",{callbacks: {
			   						okClick: function(e) {
			   							this.hide();
			   							doAction('R_CodeMR');
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
		
    case "R_CodeDR":      //조회
		jsonParam = { param : APPz.ui.convertFormToJson("frm01") };
   		var url = "${pageContext.request.contextPath}/api/za/zaa/searchZ01CodeDetail.do";
   		APPz.cmn.ApiRequest(url,jsonParam,true,($result)=>{
   			CodeDSheet.RemoveAll();
   			CodeDSheet.LoadSearchData({"data":$result.data}, {
   		        Sync: 1
   		    });
   			CodeDSheet.FitSize(false, true);
       	});         	
      break;
	case "codeDInsert" :
		var row = CodeDSheet.DataInsert(-1);
		CodeDSheet.SetCellValue(row, "SYS_CD", $("#SYS_CD").val());
		CodeDSheet.SetCellValue(row, "CLASS_CD", $("#CLASS_CD").val());
		CodeDSheet.SetCellValue(row, "CLASS_NM", $("#CLASS_NM").val());
		break;
	case "saveDetail":
		if(CodeDSheet.GetSaveString() != "KeyFieldError"){ //저장처리 할때 시트에서 걸러지면 무조건  KeyFieldError를 반환한다.
			var url = "${pageContext.request.contextPath}/api/za/zaa/saveZ01CodeDetail.do";
			duDialog(null, '저장 하시겠습니까 <i class="fas fa-question"></i>', {
				buttons: duDialog.OK_CANCEL,
					callbacks: {
						okClick: function(e) {
							this.hide()
							jsonParam = {param :{gridData : CodeDSheet.GetSaveJson(0).data}};
						APPz.cmn.ApiRequest(url,jsonParam,true,function(result){
		   				if(result.rtnCode>=0){
		   					duDialog(null,"저장되었습니다.",{callbacks: {
			   						okClick: function(e) {
			   							this.hide();
			   							doAction('R_CodeDR');
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
	case "DownExcel1":
		var params = {FileName:"codeM.xls",SheetName:"sub Total",Merge:1,SheetDesign:1 };
		CodeMSheet.Down2Excel(params);
		break;
		
	case "DownExcel2":
		var params = {FileName:"codeD.xls",SheetName:"sub Total",Merge:1,SheetDesign:1 };
		CodeDSheet.Down2Excel(params);
		break;
		
	case "LoadExcel":
		var params = { Mode : "HeaderMatch", StartRow: "1"} ;
		CodeDSheet.LoadExcel();
		
		break;
	}
}

function ChangeSize1(size){
	mySheet1.FitSize(false, true);
}

function CodeMSheet_OnDblClick(Row, Col, CellX, CellY, CellW, CellH) {
	//행을 더블 클릭했을 때 다른 페이지로 이동하도록 처리
	
	$("#CLASS_CD").val(CodeMSheet.GetCellValue(Row, "CLASS_CD"));
	$("#CLASS_NM").val(CodeMSheet.GetCellValue(Row, "CLASS_NM"));
	if(!APPz.ui.isNull($("#CLASS_CD").val())){
		doAction('R_CodeDR');   
	}
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
			<h2 class="h2">코드목록</h2>
			<div class="align right">
			<a href="javascript:doAction('R_CodeMR');" class="btn blue regular">조회</a>
			<a href="javascript:doAction('codeMInsert');" class="btn blue regular">신규</a>
			<a href="javascript:doAction('saveMaster');" class="btn blue regular">저장</a>
			<a href="javascript:doAction('DownExcel1');" class="btn blue regular">Excel</a>
			</div>
		</div>
        <div class="mt10">
	        <div id="div_CodeMSheet" style="width:100%">
	        </div>				
		</div>
		<div class="align both vm">
			<h2 class="h2">코드상세목록</h2>
			<div class="align right">
			<a href="javascript:doAction('R_CodeDR');" class="btn blue regular">조회</a>
			<a href="javascript:doAction('codeDInsert');" class="btn blue regular">신규</a>
			<a href="javascript:doAction('saveDetail');" class="btn blue regular">저장</a>
			<a href="javascript:doAction('DownExcel2');" class="btn blue regular">Excel</a>
			</div>
		</div>
        <div class="mt10">
	        <div id="div_CodeDSheet" style="width:100%">
	        </div>				
		</div>
	</div>
	<!-- //(content) -->

</div>
<!-- //(wrapper) -->

</form>
</body>
</html>