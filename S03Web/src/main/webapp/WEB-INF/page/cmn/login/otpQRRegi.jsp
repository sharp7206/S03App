<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>APP관리시스템</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/res/img/favicon.ico">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Signin Template for Bootstrap</title>

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/res/css/signin.css" rel="stylesheet">
</head>
<script type="text/javascript">
let _jsonpopupparam;
$(()=>{
	_jsonpopupparam = JSON.parse(decodeURI("${param._jsonparam}"));
	let defaultUrl = "${pageContext.request.contextPath}/api/cmn/common/checkGOtpVal.do"
	
	$("form.form-signin").keydown(function(key) {//사용자명검색시
		if (key.keyCode == 13) {//검색텍스트 입력후 엔터시
			$("#checkOptBtn").trigger("click");// 자동조회
		}
	});

	$("#checkOptBtn").click(function(event){
		var sysdate = APPz.sysyear('yyyy1111');
		alert(sysdate);
		if($("#optCode").val()==""){
			alert("OPT Code를 입력하세요");
			$("#optCode").focus();
			return false;
		}
		var jsonParam = {param:fn_getFormJson("frm")};
		APPz.cmn.ApiRequest("${pageContext.request.contextPath}/api/cmn/common/checkGOtpVal.do"
				, jsonParam,true
				, function($result){
					if($result.rtnCode>=0){
						//parent.$("#"+_jsonpopupparam._modalid).modal('hide');  
						if($result.Data.verify==false){
							$("#optCode").val('');  
							duDialog(null,APPz.getMessage("E","biz.message","유효한 OTPCode를 입력하세요"));
						}else{
							APPz.ui.modalCallback(JSON.stringify($result));//콜백호출
							top.$("#"+_jsonpopupparam._modalid).modal('hide');
						}
					
					}else{
						$("#optCode").val('');  
						duDialog(null,APPz.getMessage("E","biz.message",$result.message));
					}
		   		}
			);
	});
	
	fn_getFormJson  = function(form){
		const formData = new FormData(document.getElementById(form));

	    // Convert FormData to JSON
	    const json = {};
	    formData.forEach((value, key) => {
	        json[key] = value;
	    });

	    console.log(json);
		return json;		
	}
	//$("#checkOptBtn").click();
});
</script>
  <body class="text-center">
    <form id="frm" class="form-signin border rounded p-3 bg-secondary bg-gradient bg-opacity-10" method="post">
      <h1 class="h3 mb-3 font-weight-normal">OTP인증</h1>
      <label for="userid">키 인증 번호</label>
      <input type="text" id="encodedKey" name="encodedKey" class="input-xs rounded w-50 bg-primary bg-opacity-10 border" placeholder="Key" value="${key }" required autofocus>
			<p style="font-weight: bold;">바코드 주소 :</p>
			<input type="text" value="${QRUrl}" readonly="readonly" />
			</br>
			<h1>QR코드의 값을 입력하세요</h1>
      <!-- Replace "your-base64-string-here" with your actual Base64-encoded image string -->
      <img src="${QRUrl}" alt="QR">
      <input type="text" id="kcode" name="kcode" value='test입니다.' />
      <input type="text" id="optCode" name="optCode" placeholder="코드를 입력해주세요" />
	  <sec:csrfInput />
      <button class="btn btn-secondary btn-block w-50" type="button" id="checkOptBtn"><i class="fa fa-key"></i> 로그인</button>
      <p class="mt-5 mb-3 text-muted">&copy; APP</p>
    </form>
  </body>
</html>
