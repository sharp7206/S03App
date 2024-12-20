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
<%@page import="com.app.s03.cmn.utils.DateTime"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sys" uri="/WEB-INF/tld/systld.tld"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title><APPz:jsptitle prgmcd="${_prgmcd_}"/></title>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<!-- jQuery--> 
  <link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
  <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
  <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
<script type="text/javascript">
//===============================================================================================================
//jQuery ready
//===============================================================================================================
let jsonObjParam = JSON.parse(decodeURI("${param._jsonparam}"));

let cmmonCdList;
$(()=>{
	initPage();
	
});

function initPage(){
	  $('ul.tabs li').click(function(){
		    var tab_id = $(this).attr('data-tab');
		    
		    $('ul.tabs li').removeClass('current');
		    $('.tab-content').removeClass('current');
		 
		    $(this).addClass('current');
		    $("#"+tab_id).addClass('current');
		    
		    if(tab_id =='tab-1' ){
		    }else if(tab_id =='tab-2' ){
		    }
		  });
		  
	/**공통코드작업 START**/
	jsonParam = { param : {
		          codeStr : "LOAN_CLSS,BANK_CD,REPAY_CD" 
				}};//근무상태,부서코드,권한
	APPz.cmn.ApiRequest("${pageContext.request.contextPath}/api/cmn/common/getCodeList.do"
		, jsonParam,true
		, function($result){
			debugger;
			cmmonCdList = $result.list;
			
			APPz.ui.setCombo($('#GOOD_CLSS'), cmmonCdList.filter(cmmonCdList => cmmonCdList.classCd==='LOAN_CLSS'), "classVal", "classDtlNm","S");
			APPz.ui.setCombo($('#BANK_CD'), cmmonCdList.filter(cmmonCdList => cmmonCdList.classCd==='BANK_CD'), "classVal", "classDtlNm","S");
			APPz.ui.setCombo($('#PAY_MTH'), cmmonCdList.filter(cmmonCdList => cmmonCdList.classCd==='REPAY_CD'), "classVal", "classDtlNm","S");
			var _toDay = APPz.sysdate();
			jqueryCalender2("EXPIRE_YMD");
			jqueryCalender2("LOAN_YMD");

		    getPostNo = function(POST_CD, ADDR1, ADDR2){
		        new daum.Postcode({
		            oncomplete: function(data) { //선택시 입력값 세팅
		            	
		            	document.getElementById(POST_CD).value = data.zonecode; // zonecode
		                document.getElementById(ADDR1).value = data.address; // 주소 넣기
		                document.querySelector("input[name="+ADDR2+"]").focus(); //상세입력 포커싱
		                return;
		            }
		        }).open();
		    } ;   
		    
		    APPz.ui.setDivDisableMode('contactDiv', true);
		    document.getElementById('contactDiv').addEventListener('change', function(event) {
		    	console.log('event.target.tagName=='+event.target.tagName);
		        if (event.target.tagName.toLowerCase() === 'input' || event.target.tagName.toLowerCase() === 'select') {
		            console.log('변경된 요소의 이름:', event.target.name);
		        }
		    });			
			LoadPage();
   		}
	);

}

function jqueryCalender2(field){
	$('#'+field ).datepicker({
		  dateFormat: "yy-mm-dd",	
	      prevText: '이전 달',
	      nextText: '다음 달',
	      monthNames: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
	      monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	      dayNames: ['일', '월', '화', '수', '목', '금', '토'],
	      dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
	      dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
	      showMonthAfterYear: true,
	      yearSuffix: '년',
	      changeMonth: true,
	      changeYear: true,
	      numberOfMonths: 1,
	      showButtonPanel: true
	      });
}

/*Sheet 기본 설정
 */
 LoadPage = function (){
	var Cols = [
		{Header : "No", Type:"Seq",       MinWidth:45,Align:"center"},
		{Header : "STS", Type:"Status",    MinWidth:40,SaveName:"SSTATUS"},
    	{Header : "DEL", Type:"DelCheck",  MinWidth:60},
		{Header : "LoanId", Type:"Text",      MinWidth:60,    SaveName:"LOAN_ID",              Edit: false}, //화면명
		{Header : "채무자ID", Type:"Text",      MinWidth:60,    SaveName:"DEBTOR_ID",            Edit: false}, //채무자ID
		{Header : "채무자", Type:"Text",      MinWidth:100,   SaveName:"DEBTOR_NM",            Edit: true, KeyField:1}, //채무자ID
		{Header : "주민번호", Type:"Text",      MinWidth:140,   SaveName:"JUMIN_NO",             Edit: true, Format:"IdNo", KeyField:1}, //주민번호ID
		{Header : "전화번호", Type:"Text",      MinWidth:140,   SaveName:"TEL1",                 Edit: true, Format:"PhoneNo", KeyField:1}, //전화번호ID1
		{Header : "은행", Type:"Combo",     MinWidth:140,   SaveName:"BANK_CD",              Edit: true, Align:"Center", ComboText:APPz.ui.generateCode(cmmonCdList, "BANK_CD", "classDtlNm"), ComboCode:APPz.ui.generateCode(cmmonCdList, "BANK_CD", "classVal"), KeyField:1},
		{Header : "계좌번호", Type:"Text",      MinWidth:200,   SaveName:"ACNT_NO",              Edit: true, Format:"NullInteger", KeyField:1}, //계좌번호
		{Header : "예금주명", Type:"Text",      MinWidth:100,   SaveName:"ACNT_NM",              Edit: true, KeyField:1}, //예금주
		{Header : "우편번호", Type:"Popup",     MinWidth:100,   SaveName:"POSTCD",               Edit: true, Hidden:false, Align: "center"},
		{Header : "기본주소", Type:"Text",      MinWidth:200,   SaveName:"ADDR1",                Edit: true}, //요청금액
		{Header : "상세주소", Type:"Text",      MinWidth:100,   SaveName:"ADDR2",                Edit: true}, //USERID
		{Header : "EMAIL", Type:"Text",      MinWidth:100,   SaveName:"EMAIL",                Edit: true}, //USERID
		{Header : "작성자", Type:"Text",      MinWidth:200,   SaveName:"REG_ID",               Edit: false},//API
	];
	var initData1 = {"cfg":{"AutoFitColWidth": "search|resize|init|colhidden|rowtransaction", "DeferredVScroll": 1}
			       , "HeaderMode" : {Sort:1,ColMove:1,ColResize:1,HeaderCheck:1}
			       , "Cols" : Cols
			       };
	// IBSheet 생성
	createIBSheet2($('#div_mySheet1')[0], 'mySheet1', '100%', '100%');
	IBS_InitSheet(mySheet1, initData1);
	mySheet1.SetCountPosition(4);
	
	var Cols2 = [
		{Header : "No",     Type:"Seq",       MinWidth:45,Align:"center"},
		{Header : "STS",    Type:"Status",    MinWidth:60, Hidden:"1", SaveName:"SSTATUS"},
    	{Header : "DEL",    Type:"DelCheck",  MinWidth:60, Hidden:true},
		{Header : "LoanId", Type:"Text",      MinWidth:60,    SaveName:"LOAN_ID",             Edit: false, Hidden:true},
		{Header : "납부일",   Type:"Date",      MinWidth:80,    SaveName:"DUE_YMD",             Edit: false, Hidden:false, Format: "yyyy-MM-dd", Align: "center"},
		{Header : "납부완료일", Type:"Date",      MinWidth:80,    SaveName:"PAY_DAY",             Edit: true,  Hidden:false, Format: "yyyy-MM-dd", Align: "center"},
		{Header : "이자원금",  Type:"AutoSum",   MinWidth:100,   SaveName:"PAY_AMT",             Edit: true,  Hidden:false},
		{Header : "원금상환",  Type:"AutoSum",   MinWidth:100,   SaveName:"REPAY_AMT",           Edit: true,  Hidden:false},
		{Header : "작성자",   Type:"Text",      MinWidth:200,   SaveName:"REG_ID",              Edit: false},//API
	];
	
	var initData2 = {"cfg":{"AutoFitColWidth": "search|resize|init|colhidden|rowtransaction"}
    , "HeaderMode" : {Sort:1,ColMove:1,ColResize:1,HeaderCheck:1}
    , "Cols" : Cols2
    };	
	// IBSheet 생성
	createIBSheet2($('#div_mySheet2')[0], 'mySheet2', '100%', '280px');
	IBS_InitSheet(mySheet2, initData2);
	mySheet2.SetCountPosition(4);
	
	$("#LOAN_ID").val(jsonObjParam.param.LOAN_ID);
	doAction("R_main");
}
function doAction(sAction)
{
	switch(sAction)
	{
	case "R_main" :      //조회
		if(APPz.ui.isNull($("#LOAN_ID").val())){
			return;
		}
   		jsonParam = { param : APPz.ui.convertFormToJson("frm01") };
   		var url = "${pageContext.request.contextPath}/api/ba/baa/selectS03MortgageLoan.do";
   		APPz.cmn.ApiRequest(url,jsonParam,true,($result)=>{
   			
   			var obj = $result.data,
   	    	elem = null;
   			for (elem in obj) {
	   		    if ($("#" + elem)[0]) {
	   		    	if(elem.indexOf('AMT')>=0){
	   		    		$("#" + elem).val(APPz.ui.setComma(obj[elem]));
	   		    	}else{
	   		    		$("#" + elem).val(obj[elem]);	
	   		    	}
	   		        
	   		    }
   			}
   			//_loanYmd.selectDate($("#LOAN_YMD").val());
   			//_expireYmd.selectDate($("#EXPIRE_YMD").val());
   			mySheet1.RemoveAll();
   			mySheet1.LoadSearchData({"data":$result.data.contactList}, {
   		        Sync: 1
   		    });
   			
   			mySheet2.RemoveAll();
   			mySheet2.LoadSearchData({"data":$result.data.payList}, {
   		        Sync: 1
   		    });
   			$("#FILE_GIDStr").val(JSON.stringify($result.data.fileList));
   			fn_fileView($result.data.fileList);
   			mySheet1.FitSize(false, true);
   			calcInterest();
       	}); 
		break;
	case "U_Main" :
		if(!validForm("frm01")) return;
		//var SaveJson = JSON.stringify(mySheet1.GetSaveJson(0));
		if(mySheet1.GetSaveString() == "KeyFieldError"){ //저장처리 할때 시트에서 걸러지면 무조건  KeyFieldError를 반환한다.
			return;
		}
		
		jsonParam = {
				param : APPz.ui.convertFormToJson("frm01")
   	    };
		jsonParam.param.gridData = mySheet1.GetSaveJson(0).data;
		jsonParam.param.payList = mySheet2.GetSaveJson(0).data;
		var url = "${pageContext.request.contextPath}/api/ba/baa/saveS03MortgageLoan.do";
			duDialog(null,APPz.getMessage("C","confirm.DoProcess","저장"), {
				buttons: duDialog.OK_CANCEL,
				callbacks: {
					okClick:function(e){
						this.hide()
						APPz.cmn.ApiRequest(url,jsonParam,true,function(result){//메뉴저장
							
		   				if(result.rtnCode>=0){
		   					duDialog(null,"저장되었습니다.",{
			   					callbacks: {
			   						okClick: function(e) {
			   							this.hide();
			   							$("#LOAN_ID").val(result.data);
			   							doAction('R_main');
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
	case "D_Main" :
		//var SaveJson = JSON.stringify(mySheet1.GetSaveJson(0));
		if(mySheet1.GetSaveString() == "KeyFieldError"){ //저장처리 할때 시트에서 걸러지면 무조건  KeyFieldError를 반환한다.
			return;
		}
		
		jsonParam = {
				param : APPz.ui.convertFormToJson("frm01")
   	    };
		jsonParam.param.gridData = mySheet1.GetSaveJson(0).data;

		var url = "${pageContext.request.contextPath}/api/ba/baa/deleteS03MortgageLoan.do";
			duDialog(null,APPz.getMessage("C","confirm.DoProcess","삭제"), {
				buttons: duDialog.OK_CANCEL,
				callbacks: {
					okClick:function(e){
						this.hide()
						APPz.cmn.ApiRequest(url,jsonParam,true,function(result){//메뉴저장
							
		   				if(result.rtnCode>=0){
		   					duDialog(null,"삭제되었습니다.",{
			   					callbacks: {
			   						okClick: function(e) {
			   							this.hide();
			   							doAction('Close');
			   						}
			   					}
			   				});
		   				}else{
		   					duDialog("삭제실패",result.message,{callbacks: {okClick: function(e) {this.hide()}}
		   					});
		   				}
		   			});

					}
				}
			});
		break;
	case "Add" :      //조회
    	var insRow = mySheet1.DataInsert(-1);
    	APPz.ui.setDivDisableMode('contactDiv', false);
		break;
	case "Close" :      //조회
    	var jsonParam = APPz.ui.convertFormToJson("frm01");
    	APPz.ui.modalCallback(JSON.stringify(jsonParam));//콜백호출
    	top.$("#"+jsonObjParam._modalid).modal('hide');
		break;
	case "Excel" :
		//var params = {"FileName":"excel2", "SheetName":"user", "HiddenColumn":0};
		//MenuSheet.Down2Excel(params);
		var params = {FileName:"SystemLog.xlsx",SheetName:"User",SheetDesign:1,Merge:1,OnlyHeaderMerge:1,DownRows:"",DownCols:""};
		MenuSheet.Down2Excel(params);
		break;
    		
	}
}

function validForm(_formNm){
	
    var form = document.getElementById(_formNm);
	var isValid = true;
	var fldNm = "";
    var formElements = form.elements;
    for (var i = 0; i < formElements.length; i++) {
        if (formElements[i].hasAttribute('required') && formElements[i].value.trim() === '') {
        	fldNm = formElements[i].placeholder;
            isValid = false;
            break;
        }
    }
    
    if (!isValid) {
        duDialog(null,APPz.getMessage("E","data.Input.Required",fldNm));
       // event.preventDefault(); // 제출을 취소합니다.
        return false;
    }
    return true;
}

function fn_fileView(fileListJson){//fileView_div
	var fileViewDiv = document.getElementById("fileView_div");
	// 기존에 표시된 내용이 있다면 초기화
    fileViewDiv.innerHTML = "";
    // 파일 정보를 순회하며 div에 추가
    if(APPz.ui.isNull(fileListJson)) return;
    fileListJson.forEach(function(fileInfo) {
        var fileDiv = document.createElement("div");
        var fileLink = document.createElement("a");
        fileLink.href = "javascript:void(0);";
        fileLink.textContent = "File Name: " + fileInfo.FILE_ORG_NM + ", File Size: " + fileInfo.FILE_SZ + ", File Type: " + fileInfo.FILE_TYPE;
        fileLink.onclick = function() {
            APPz.filedownN(fileInfo.FILE_PATH+'/'+fileInfo.FILE_NM, fileInfo.FILE_ORG_NM);
        };
        fileDiv.appendChild(fileLink);
        fileViewDiv.appendChild(fileDiv);        
    });   
}

function fn_fileDown(filePath, fileNm){
	
}

function calcInterest(){
	var paymth = $("#PAY_MTH").val();
	if($("#GOOD_CLSS").val()=='003'){
		var loanAmt = (APPz.ui.isNull($("#LOAN_AMT").val()) ? "0" : $("#LOAN_AMT").val()).replaceAll(',', '');
		var interestRate = (APPz.ui.isNull($("#INTEREST_RATE").val()) ? "0" : $("#INTEREST_RATE").val()).replaceAll(',', '');
		var repayAmt = (APPz.ui.isNull($("#REPAY_AMT").val()) ? "0" : $("#REPAY_AMT").val()).replaceAll(',', '');
		$("#INTEREST_AMT").val(APPz.ui.setComma((loanAmt-repayAmt)*interestRate/7/100));
		$("#SERVICE_AMT").val(APPz.ui.setComma((loanAmt-repayAmt)*interestRate/7/100/10));
	}else{
		var loanAmt = (APPz.ui.isNull($("#LOAN_AMT").val()) ? "0" : $("#LOAN_AMT").val()).replaceAll(',', '');
		var interestRate = (APPz.ui.isNull($("#INTEREST_RATE").val()) ? "0" : $("#INTEREST_RATE").val()).replaceAll(',', '');
		var repayAmt = (APPz.ui.isNull($("#REPAY_AMT").val()) ? "0" : $("#REPAY_AMT").val()).replaceAll(',', '');
		$("#INTEREST_AMT").val(APPz.ui.setComma((loanAmt-repayAmt)*interestRate/100));
		$("#SERVICE_AMT").val(APPz.ui.setComma((loanAmt-repayAmt)*interestRate/100/10));
	}
}


uploadCallBackFn = function(data){
	
	var dataJson = JSON.parse(data);
	$("#FILE_GID").val(dataJson.FILE_GRP_ID);
	$("#FILE_GIDStr").val(JSON.stringify(dataJson.fileList));
	fn_fileView(dataJson.fileList);	
}

function mySheet1_OnPopupClick(Row, Col) {
    var colnm = mySheet1.ColSaveName(Col);
    //var RowJson = ItemShareSheet.GetRowJson(Row);
    if (colnm == "POSTCD"){
            new daum.Postcode({
                oncomplete: function(data) { //선택시 입력값 세팅
                	mySheet1.SetCellValue(Row, "POSTCD", data.zonecode);
                	mySheet1.SetCellValue(Row, "ADDR1", data.address);
                	mySheet1.SelectCell(Row, "ADDR2");
                	return;
                }
            }).open();
	}
}


function mySheet1_OnSelectCell(oldRow, oldCol, row, col, isDelete) {
	if (row == 0) return; //헤더행일때는 폼에 반영 안함.
	var obj = mySheet1.GetRowData(row),
    	elem = null;
		for (elem in obj) {
	    if ($("#" + elem)[0]) {
	        $("#" + elem).val(obj[elem]);
	    }
	}
	APPz.ui.setDivDisableMode('contactDiv', false);
}

//값을 편집한 직후 이벤트가 발생한다.

function mySheet1_OnAfterEdit(Row, Col) {
	var colnm = mySheet1.ColSaveName(Col);
    //var RowJson = ItemShareSheet.GetRowJson(Row);
    if (colnm == "JUMIN_NO"){
    	if(fn_juminNum('N', mySheet1.GetCellValue( Row, "JUMIN_NO" ))){
	        // 형식에 맞게 주민등록번호를 포맷
	        var formattedNumber = numbersOnly.replace(/(\d{6})(\d{7})/, '$1-$2');

	        console('formattedNumber===='+formattedNumber);
	        mySheet1.SetCellValue( Row, "JUMIN_NO", formattedNumber);
	    }else{
	    	mySheet1.SetCellValue( Row, "JUMIN_NO", '');
	    }
    }
}

//값을 편집한 직후 이벤트가 발생한다.

function mySheet2_OnAfterEdit(Row, Col) {
	var colnm = mySheet2.ColSaveName(Col);
    //var RowJson = ItemShareSheet.GetRowJson(Row);
    //if (colnm == "REPAY_AMT"){
    	$("#REPAY_AMT").val(APPz.ui.setComma(mySheet2.GetSumValue(0, "REPAY_AMT")));
    	calcInterest();
    //}
}


//주민등록번호 입력값을 체크하고 형식을 지정하는 함수
fn_juminNum = function(objyn, input) {
    // 입력값에서 숫자만 추출
    if(objyn=='Y'){
        var checkRtn = false;
        var numbersOnly = input.value.replace(/\D/g, '');

    	let jumin = input.value.replace('-', '').split('');
        const bits = [2,3,4,5,6,7,8,9,2,3,4,5];
        let sum = 0;
        for (let i =0; i<bits.length; i++){
            sum += Number(jumin[i])*bits[i];
        }
        let lastNum = Number(jumin[jumin.length-1]);
        let resultNum = (11-(sum%11))%10;

        checkRtn = (lastNum == resultNum) ? true : false;
        
        // 숫자가 13자리가 아니면 잘못된 형식으로 간주
        if (numbersOnly.length !== 13 || !checkRtn) {
            alert('주민등록번호 형식이 올바르지 않습니다.');
            //input.value = ''; // 입력값 초기화
            input.focus();
            return;
        }

        
        // 형식에 맞게 주민등록번호를 포맷
        var formattedNumber = numbersOnly.replace(/(\d{6})(\d{7})/, '$1-$2');
        
        // 입력값 업데이트
        input.value = formattedNumber;
    }else{
        var checkRtn = false;
        var numbersOnly = input.replace(/\D/g, '');

    	let jumin = input.replace('-', '').split('');
        const bits = [2,3,4,5,6,7,8,9,2,3,4,5];
        let sum = 0;
        for (let i =0; i<bits.length; i++){
            sum += Number(jumin[i])*bits[i];
        }
        let lastNum = Number(jumin[jumin.length-1]);
        let resultNum = (11-(sum%11))%10;

        checkRtn = (lastNum == resultNum) ? true : false;
        
        // 숫자가 13자리가 아니면 잘못된 형식으로 간주
        if (numbersOnly.length !== 13 || !checkRtn) {
            alert('주민등록번호 형식이 올바르지 않습니다.');
            return false;
        }
        return true;
    }
}

var windowHeight = $(window).height()-200;

</script>
<style type='text/css'>
ul.tabs{
  margin: 0px;
  width:100%;
  padding: 0px;
  list-style: none;
}
ul.tabs li{
  background: none;
  color: #222;
  display: inline-block;
  padding: 10px 15px;
  cursor: pointer;
}

ul.tabs li.current{
  background: #ededed;
  color: #222;
}

.tab-content{
  display: none;  
  padding: 5px 0;
  border-top:3px solid #eee;
}

.tab-content.current{
  display: inherit;
}

</style>
	
<!--조회함수를 이용하여 조회 완료되었을때 발생하는 이벤트-->


<body class="">
<form id="frm01" name="frm01" method="post" onsubmit='return false'>

<div class="container-fluid mt-2 h-100">
	<div id="section">
		<div class="align both vm">
			<h2 class="h2">담보물권정보</h2>
			<div class="align right">
			<a href="javascript:doAction('R_main');" class="btn blue regular">조회</a>
			<a href="javascript:doAction('U_Main');" class="btn blue regular">저장</a>
			<a href="javascript:doAction('D_Main');" class="btn blue regular">삭제</a>
			<a href="javascript:doAction('Close');" class="btn blue regular">닫기</a>
			</div>
		</div>
		<div class="table-type1">
			<table class="type2">
			<colgroup>
			<col style="border:0px solid #999;" width="9%">
			<col style="border:0px solid #999;" width="23%">
			<col style="border:0px solid #999;" width="9%">
			<col style="border:0px solid #999;" width="23%">
			<col style="border:0px solid #999;" width="9%">
			<col style="border:0px solid #999;" width="*">
			</colgroup>
				<tbody>
					<tr>
						<th>문서번호<span aria-label="필수입력"></span></th>
						<td><input type="text" name="LOAN_ID" id="LOAN_ID" readonly="readonly" ></td>
						<th>담보종류<span aria-label="필수입력"></span></th>
						<td><select name="GOOD_CLSS" id="GOOD_CLSS" class="wauto"></select>
						</td>
						<th>물건시장가격<span aria-label="필수입력"></span></th>
						<td><input type="text" name="GOOD_AMT" id="GOOD_AMT" fld
							style="text-align: right;" onKeyDown="APPz.ui.onlyNumber()"
							onkeyup='APPz.ui.setCommaObj($("#GOOD_AMT"))'>
						</td>
					</tr>
					<tr>
						<th class="vt">담보물권주소<span aria-label="필수입력"></span></th>
						<td colspan="5">
							<div class="form-address">
								<input type="number" id="GOOD_POSTCD" name="GOOD_POSTCD"
									placeholder="우편번호"> <a href="javascript:getPostNo('GOOD_POSTCD', 'GOOD_ADDR1', 'GOOD_ADDR2');"
									class="btn blue regular">우편번호</a> <input type="text"
									title="기본주소" id="GOOD_ADDR1" name="GOOD_ADDR1" placeholder="기본주소">
								<input type="text" title="상세주소" id="GOOD_ADDR2" name="GOOD_ADDR2"
									placeholder="상세주소">
								
							</div>
						</td>
					</tr>
					<tr>
						<th>물건특이사항<span aria-label="필수입력"></span></th>
						<td colspan="5">
							<input type="text" title="특이사항" id="GOOD_REM" name="GOOD_REM"
									placeholder="특이사항" style="width:100%">	
						</td>
					</tr>		
					<tr>
						<th>대출일<span aria-label="필수입력"></span></th>
						<td>
							<input type="text"
							class="input-appz text-center border bg-secondary bg-opacity-10"
							data-toggle="datepicker" id="LOAN_YMD" name="LOAN_YMD" required
							placeholder="대출일" size="10">
						</td>
						<th>상환만기일<span aria-label="필수입력"></span></th>
						<td><input type="text"
							class="input-appz text-center border bg-secondary bg-opacity-10"
							data-toggle="datepicker" id="EXPIRE_YMD" name="EXPIRE_YMD" required
							placeholder="상환만기일" size="10">
						</td>
						<th>서비스금액</th>
						<td>
							<input type="text" name="SERVICE_AMT" id="SERVICE_AMT"	style="text-align: right;" onKeyDown="APPz.ui.onlyNumber()"
									onkeyup='javascript:APPz.ui.setCommaObj($("#SERVICE_AMT"))' placeholder="서비스금액" required>원
						</td>
					</tr>
					
					<tr>
						<th>대출요청금액<span aria-label="필수입력"></span></th>
						<td>
							<div class="">
								<input type="text" name="REQ_AMT" id="REQ_AMT"
									style="text-align: right;" onKeyDown="APPz.ui.onlyNumber()"
									onkeyup='javascript:APPz.ui.setCommaObj($("#REQ_AMT"))' placeholder="대출요청금액" required>
							</div>
						</td>
						<th>대출금액<span aria-label="필수입력"></span></th>
						<td><input type="text" name="LOAN_AMT" id="LOAN_AMT"
							style="text-align: right;" onKeyDown="APPz.ui.onlyNumber()"
							onkeyup='APPz.ui.setCommaObj($("#LOAN_AMT"))' required placeholder="대출금액"></td>
						<th>상환금액<span aria-label=""></span></th>
						<td>
							<input type="text" name="REPAY_AMT" id="REPAY_AMT" style="text-align: right;" readonly="readonly">
					    </td>
					</tr>
					<tr>
						<th>이자율<span aria-label="필수입력"></span></th>
						<td>
							<input type="text" name="INTEREST_RATE" id="INTEREST_RATE"  required placeholder="이자율" style="width:100px; text-align: right;" onkeyup='APPz.ui.floatInput(event, $("INTEREST_RATE"), 2)' onchange="javascript:calcInterest()">부 
							<input	type="text" name="INTEREST_AMT" id="INTEREST_AMT" style="text-align: right;" readonly="readonly">원
					    </td>
						<th>채무방식
						</th>
						<td><select name="PAY_MTH" id="PAY_MTH" class="wauto" onchange="javascript:calcInterest()">
                            </select>
						</td>
						<th>투자자</th>
						<td>
							<input type="text" name="INVEST_ID" id="INVEST_ID" >
						</td>
					</tr>					
					<tr>
						<th>첨부파일<span aria-label="필수입력"></span>
							<a href='javascript:APPz.ui.openFileUpload($("#FILE_GID").val(), "S03_MORTGAGE_LOAN", $("#LOAN_ID").val(), "", 10, "", $("#FILE_GIDStr").val(), "uploadCallBackFn");' class="btn blue regular">파일</a></th>
						<td colspan="3">
							<div id='fileView_div'></div>
							
							<input type="hidden" name="FILE_GID" id="FILE_GID" >
							<input type="hidden" id="FILE_GIDStr" name="FILE_GIDStr" value="" readonly="readonly"/>
						</td>
						<th></th>
						<td>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
  <ul class="tabs">
    <li class="tab-link current" data-tab="tab-1">채권자정보</li>
    <li class="tab-link" data-tab="tab-2">월이자금액</li>
  </ul>
  <div id="tab-1" class="tab-content current">				
		<div class="align both vm">
			<h2 class="h2">채권자정보</h2>
			<div class="align right">
			<a href="javascript:doAction('Add');" class="btn blue regular">추가</a>
			</div>
		</div>		
        <div class="layout-type1 mt10">
            <div id="div_mySheet1" style="width:100%">
            </div>
			<div class="table-type1" id="contactDiv">
				<table>
					<colgroup>
						<col style="width: 14%">
						<col style="width: 37%">
						<col style="width: 13%">
						<col style="width: *">
					</colgroup>
					<tbody>
						<tr>
							<th>채무자이름<span aria-label="필수입력"></span></th>
							<td>
								<div class=""><input type="text" name="DEBTOR_NM" id="DEBTOR_NM" ></div>
							</td>
							<th>주민번호<span aria-label="필수입력"></span></th>
							<td>
								<div class=""><input type="text" name="JUMIN_NO" id="JUMIN_NO" onchange="fn_juminNum('Y', this)"></div>
							</td>
						</tr>
						<tr>
							<th>연락처</th>
							<td>
								<input type="text" name="TEL1" id="TEL1">
							</td>
							<th>이메일<span aria-label="필수입력"></span></th>
							<td><input type="text" name="EMAIL" id="EMAIL">
							</td>
						</tr>
						<tr>
							<th class="vt">거래은행/계좌번호<span aria-label="필수입력"></span></th>
							<td>
								<select name="BANK_CD" id="BANK_CD" class="wauto"></select>
								<input type="text" name="ACNT_NO" id="ACNT_NO">
							</td>
							<th class="vt">예금주명<span aria-label="필수입력"></span></th>
							<td>
								<input type="text" name="ACNT_NM" id="ACNT_NM">
							</td>
						</tr>
						
						<tr>
							<th class="vt">주소<span aria-label="필수입력"></span></th>
							<td colspan="5">
								<div class="form-address">
									<input type="number" id="POSTCD" name="POSTCD" placeholder="우편번호"> <a href="javascript:getPostNo('POSTCD', 'ADDR1', 'ADDR2');" class="btn blue regular">우편번호</a> 
									<input type="text" title="기본주소" id="ADDR1" name="ADDR1" placeholder="기본주소">
									<input type="text" title="상세주소" id="ADDR2" name="ADDR2" placeholder="상세주소">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
							            
        </div>    		
  </div>	
  <div id="tab-2" class="tab-content">				
  		<div class="align both vm">
			<h2 class="h2">이자지급확인</h2>
<!-- 			<div class="align right">
			<a href="javascript:doAction('U_PaySave');" class="btn blue regular">저장</a>
			</div> -->
		</div>		
        <div class="layout-type1 mt10">
            <div id="div_mySheet2" style="width:100%">
            </div>
        </div>    
  </div>	
				<!----------------------- 본문내용 종료 -------------------------------------------------->
	</div>
</div>	
</form>	
</body>
</html>