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
$(()=>{
	
	let defaultUrl = "${pageContext.request.contextPath}/api/cmn/common/checkGOtpVal.do"
	if (opener != null && !opener.closed) {
		opener.location.href = defaultUrl;
		self.close();
	} else if (parent.opener != null && !parent.opener.closed) {
		parent.opener.location.href = defaultUrl;
		self.close();
	}
	if(_isInIframe){
		top.location.href = defaultUrl
	} else{
		if(self.location.href != window.location.href){
			self.location.href = defaultUrl;
		}
	}


	$("form.form-signin").keydown(function(key) {//사용자명검색시
		if (key.keyCode == 13) {//검색텍스트 입력후 엔터시
			$("#checkOptBtn").trigger("click");// 자동조회
		}
	});

	$("#checkOptBtn").click(function(event){
		debugger;
		if($("#optCode").val()==""){
			alert("OPT Code를 입력하세요");
			$("#optCode").focus();
			return;
		}
		var jsonParam = { param : fn_getFormJson('frm')};//근무상태,부서코드,권한
		APPz.cmn.ApiRequest("${pageContext.request.contextPath}/api/cmn/common/checkGOtpVal.do"
				, jsonParam,true
				, function($result){
					debugger;
					alert($result.rslt);
					if($result.rslt=="false"){
						duDialog(null,"<i class='fas fa-exclamation-triangle' style='font-size:20px;color:red;'></i>&emsp;"+$result.message,{
	       					callbacks: {
	       						okClick: function(e) {
	       							this.hide();
	       							$("#optCode").val('');
	       							$("#optCode").focus();
	       						}
	       					}
	       				});
					} else {
						alert('1');
						document.location.href = "${pageContext.request.contextPath}/"+$result.url;
		//				APPz.goPage("${pageContext.request.contextPath}/"+response.url,null);
					}
				}
			);
		/*
		$.ajax({
			url:"${pageContext.request.contextPath}/api/cmn/common/checkGOtpVal.do",
			type :  "POST",
			dataType : "json",
			data : $("form.form-signin").serialize(),
			beforeSend : function(xhr){
				if($("meta[name='_csrf_header']").attr("content") && $("meta[name='_csrf']").attr("content")){
					xhr.setRequestHeader($("meta[name='_csrf_header']").attr("content"), $("meta[name='_csrf']").attr("content"));// csrf 관련설정.
				}
       			APPz.cmn._topLoading(true);
			},
			success : function(response){
				debugger;
				if(response.rslt=="false"){
					duDialog(null,"<i class='fas fa-exclamation-triangle' style='font-size:20px;color:red;'></i>&emsp;"+response.message,{
       					callbacks: {
       						okClick: function(e) {
       							this.hide()
       							$("#optCode").focus();
       						}
       					}
       				});
				} else {
					document.location.href = "${pageContext.request.contextPath}/"+response.url;
	//				APPz.goPage("${pageContext.request.contextPath}/"+response.url,null);
				}
			},
			error : function(xhr,status,error){
				APPz.cmn._ajaxError(xhr,status,error,"${pageContext.request.contextPath}/login.do");
			},
			complete : function() {
    			// 통신이 완료된 후 처리
				APPz.cmn._topLoading(false);
    		}
		})
		*/
	});
	
	fn_getFormJson  = function(form){
		debugger;
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
    <form id="frm" class="form-signin border rounded p-3 bg-secondary bg-gradient bg-opacity-10" method="post" onsubmit='return false'>
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
