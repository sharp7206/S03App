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
	let cmmonCdList;
	initPage();
});

function initPage(){
	document.querySelector('.search').addEventListener('keypress', function(event) {
	    if (event.key === 'Enter') {
	        doAction('R_Main');
	    }
	});
	
	/**공통코드작업 START**/
	jsonParam = { param : {
		          codeStr : "LOAN_CLSS" 
				}};//근무상태,부서코드,권한
	APPz.cmn.ApiRequest("${pageContext.request.contextPath}/api/cmn/common/getCodeList.do"
		, jsonParam,true
		, function($result){
			cmmonCdList = $result.list;
			APPz.ui.setCombo($('#GOOD_CLSS'), cmmonCdList, "classVal", "classDtlNm","S");
			
			LoadPage();
   		}
	);	
	let _toDay = APPz.sysdate();
	$("#FROM_YMD").val(APPz.adddays(_toDay,-360,'-'));
	$("#TO_YMD").val(_toDay);
	APPz.ui.jqueryCalenderRange("FROM_YMD", "TO_YMD");
}


/*Sheet 기본 설정 */
function LoadPage(){
	var Cols = [
		{Header : "No", Type:"Seq",MinWidth:45,Align:"center"},
		{Header : "LoanId",   Type:"Text",      MinWidth:60,   SaveName:"LOAN_ID",             Edit: false, ColMerge :false}, 
		{Header : "대출일",     Type:"Date",      Width:80,      SaveName:"LOAN_YMD",             Edit: false, Hidden:false, Format: "yyyy-MM-dd", Align: "center"},
		{Header : "만료일",     Type:"Date",      Width:80,      SaveName:"EXPIRE_YMD",           Edit: false, Hidden:false, Format: "yyyy-MM-dd", Align: "center"},
		{Header : "담보구분",    Type:"Combo",     Width:80,      SaveName:"GOOD_CLSS",            Edit: false, Align:"Center", ComboText:APPz.ui.generateCode(cmmonCdList, "LOAN_CLSS", "classDtlNm"), ComboCode:APPz.ui.generateCode(cmmonCdList, "LOAN_CLSS", "classVal")},
		{Header : "요청금액",    Type:"Int",       MinWidth:60,   SaveName:"REQ_AMT",              Edit: false, ColMerge :false},
		{Header : "대출금액",    Type:"Int",       MinWidth:60,   SaveName:"LOAN_AMT",             Edit: false, ColMerge :false},
		{Header : "상환금액",    Type:"Int",       MinWidth:60,   SaveName:"REPAY_AMT",            Edit: false, ColMerge :false},
		{Header : "잔액",      Type:"Int",       MinWidth:60,   SaveName:"LEFT_AMT",             Edit: false, ColMerge :false},
		{Header : "당월이자",    Type:"Int",       MinWidth:100,  SaveName:"PAY_AMT",              Edit: false, ColMerge :false},
		//{Header : "총이자",    Type:"Int",       MinWidth:100,  SaveName:"T_PAY_AMT",              Edit: false, ColMerge :false},
		{Header : "연락처",     Type:"Text",      MinWidth:140,  SaveName:"TEL",                  Edit: false, Format:"PhoneNo"}, //전화번호ID1
		{Header : "담보물 주소",  Type:"Text",      MinWidth:100,  SaveName:"ADDR",                 Edit: false, ColMerge :false},
		{Header : "채무자",     Type:"Text",      MinWidth:100,  SaveName:"DEBTOR_NM",            Edit: false, ColMerge :false},
		{Header : "작성자",     Type:"Text",      MinWidth:200,  SaveName:"REG_ID",               Edit: false, ColMerge :false},
	];
	var initData1 = {"cfg":{"AutoFitColWidth": "search|resize|init|colhidden|rowtransaction", "DeferredVScroll": 1}
    , "HeaderMode" : {Sort:1,ColMove:1,ColResize:1,HeaderCheck:1}
    , "Cols" : Cols
    };
	// IBSheet 생성
	createIBSheet2($('#div_mySheet1')[0], 'mySheet1', '100%', windowHeight+"px");
	IBS_InitSheet(mySheet1, initData1);
	mySheet1.SetCountPosition(4);
}

function doAction(sAction)
{
	switch(sAction)
	{
	case "R_Main" :      //조회
   		jsonParam = { param : APPz.ui.convertFormToJson("frm01") };
   		var url = "${pageContext.request.contextPath}/api/ba/baa/selectS03MortgageLoanList.do";
   		APPz.cmn.ApiRequest(url,jsonParam,true,($result)=>{
   			//debugger;
   			mySheet1.RemoveAll();
   			mySheet1.SetTreeCheckActionMode(1);
   			mySheet1.LoadSearchData({"data":$result.data}, {
   		        Sync: 1
   		    });
   			for ( var i = 1; i <= mySheet1.RowCount(); i++ ) {
   				if(mySheet1.GetCellValue( i, "PAY_DAY")== '이자납부요청'){
   					mySheet1.SetCellBackColor(i,"PAY_DAY", "#FF0000");
   				}
   			}
   			mySheet1.FitSize(false, true);
       	}); 
		break;
	case "R_RegiPop" :
		jsonParam = { param : {} };
		APPz.ui.openModal("${pageContext.request.contextPath}/page/ba/popup/BAA00002.do","대부등록",jsonParam,$(window).width()-100,$(window).height()-50,false,"regiCallback");
		break;
	case "Excel" :
		//var params = {"FileName":"excel2", "SheetName":"user", "HiddenColumn":0};
		//MenuSheet.Down2Excel(params);
		var params = {FileName:"거래내역.xlsx",SheetName:"User",SheetDesign:1,Merge:1,OnlyHeaderMerge:1,DownRows:"",DownCols:""};
		mySheet1.Down2Excel(params);
		break;
	}
}


function regiCallback(data){
	doAction('R_Main');
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

function mySheet1_OnDblClick(Row, Col, Value, CellX, CellY, CellW, CellH) {    

	var selectRowJson = mySheet1.GetRowData(Row);
	jsonParam = { param : selectRowJson };
	APPz.ui.openModal("${pageContext.request.contextPath}/page/ba/popup/BAA00002.do","대부등록", jsonParam, $(window).width()-100,$(window).height()-50,false,"regiCallback");
	return 
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
			<table class="type2">
			<colgroup>
			<col style="border:0px solid #999;" width="10%">
			<col style="border:0px solid #999;" width="40%">
			<col style="border:0px solid #999;" width="10%">
			<col style="border:0px solid #999;" width="*">
			</colgroup>
                <tbody>
                    <tr>
                        <th>기간</th>
                        <td>
                        	<input type="text" class="datepicker" id="FROM_YMD" name="FROM_YMD" value="" maxlength="10" style="width:100px;" placeholder="시작일"/>
                        	&nbsp;&nbsp;~&nbsp;&nbsp;
                        	<input type="text" class="datepicker" id="TO_YMD" name="TO_YMD" value="" maxlength="10" style="width:100px;" placeholder="종료일"/>
<!-- 		        			<table width='100%'>
		        				<tr>
		        					<td><input type="text" class="input-jsys text-center border bg-secondary bg-opacity-10" data-toggle="datepicker" id="FROM_YMD" name="FROM_YMD" placeholder="시작일" readonly size="10"></td>
		        					<td>~</td>
		        					<td><input type="text" class="input-jsys text-center border bg-secondary bg-opacity-10" data-toggle="datepicker" id="TO_YMD" name="TO_YMD" placeholder="종료일" readonly size="10"></td>
		        					
		        				</tr>		        				
		        			</table> -->
                        </td>
                        <th>대부구분/지급방법</th>
                        <td>
                            <select name="GOOD_CLSS" id="GOOD_CLSS" class="wauto">
                            </select>
                        </td>                        
                    </tr>
                </tbody>
            </table>
        </div>		
		<div class="align both vm">
			<h2 class="h2">메뉴목록</h2>
			<div class="align right">
			<a href="javascript:doAction('R_Main');" class="btn blue regular">조회</a>
			<a href="javascript:doAction('R_RegiPop');" class="btn blue regular">신규등록</a>
			<a href="javascript:doAction('Excel');" class="btn blue regular">Excel</a>
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