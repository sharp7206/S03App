<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>APP관리시스템(OTP)</title>
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
	$("#loginBtn").hide();
	$("#otpBtn").show();
	let defaultUrl = "${pageContext.request.contextPath}/login.do"
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

	$("#password,#userid").keypress(function(event){
		let s = String.fromCharCode( event.which );
		if ( s.toUpperCase() === s && s.toLowerCase() !== s && !event.shiftKey ) {
			$("#capslockdiv").addClass("visible");
			$("#capslockdiv").removeClass("invisible");
		} else {
			$("#capslockdiv").addClass("invisible");
			$("#capslockdiv").removeClass("visible");
		}
	});
/*
	$("form.form-signin").validate({
		rules:{
			userid:{required:true},
			password:{required:true}
		},
	    messages:{
		    userid:{required:APPz.getMessage("V","data.Input.Required","아이디를")},
			password:{required:APPz.getMessage("V","data.Input.Required","비밀번호를")}
		},
	});
*/
	$("form.form-signin").keydown(function(key) {//사용자명검색시
		if (key.keyCode == 13) {//검색텍스트 입력후 엔터시
			$("#otpBtn").trigger("click");// 자동조회
		}
	});
	$("#otpBtn").click(function(event){
		doAction('doOtp');
	});
	
	otpCallBack = function(response){
		var jsonRslt = JSON.parse(decodeURI(response));
		console.log('response.rslt==='+response+':verify==');
		if(jsonRslt.Data.verify==false){  
			duDialog(null,"<i class='fas fa-exclamation-triangle' style='font-size:20px;color:red;'></i>&emsp;"+jsonRslt.message,{
					callbacks: {
						okClick: function(e) {
							this.hide()
							$("#optCode").focus();
						}
					}
				});
		} else {
			$("#loginBtn").click();
		}
	}
	$("#loginBtn").click(function(event){
		if($("form.form-signin").valid()== false){
			if($("#userid").val()==""){
				$("#userid").focus();
			} else {
				$("#password").focus();
			}
			return false;
		}

		$.ajax({
			url:"${pageContext.request.contextPath}/login.do",
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
				if(response.error=="true"){
					duDialog(null,"<i class='fas fa-exclamation-triangle' style='font-size:20px;color:red;'></i>&emsp;"+response.message,{
       					callbacks: {
       						okClick: function(e) {
       							this.hide()
       							$("#password").focus();
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
	});
});

	/*Sheet 각종 처리*/
	function doAction(sAction)
	{
	  switch(sAction)
	  {
		case "doLogin": 	//신규행 추가
			break;
		case "doOtp":   //조회
			var jsonParam = {param:fn_getFormJson("frm")};
			APPz.cmn.ApiRequest("${pageContext.request.contextPath}/api/cmn/common/checkOPTUserInfo.do"
					, jsonParam,true
					, function($result){
						if($result.rtnCode>=0){
							debugger;
							//parent.$("#"+_jsonpopupparam._modalid).modal('hide');
							jsonParam.param.otpSecrKey = $result.Data.otpSecrKey;  
							if($result.Data.otpSecrKey ==''){
								APPz.ui.openModal("${pageContext.request.contextPath}/otpLogin.do","OTP입력",jsonParam,800, 600,false,"otpCallBack");
							}else{
								APPz.ui.openModal("${pageContext.request.contextPath}/otpLogin.do","OTP입력",jsonParam,400, 600,false,"otpCallBack");
							}
						
						}else{
							$("#optCode").val('');  
							duDialog(null,APPz.getMessage("E","biz.message",$result.message));
						}
			   		}
				);
			//var jsonParam = {param:fn_getFormJson("frm")};
			var jsonParam = {param:fn_getFormJson("frm")};
		    break;
	  }
	}
	otpCallBack = function(response){
		alert('response.rslt=='+response.rslt);
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
			$("#loginBtn").click();
		}
	}
	
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
</script>
  <body class="text-center">
    <form id='frm' class="form-signin border rounded p-3 bg-secondary bg-gradient bg-opacity-10" method="post" onsubmit='return false'>
      <img class="mb-4" src="https://getbootstrap.com/docs/4.0/assets/brand/bootstrap-solid.svg" alt="" width="72" height="72">
      <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
      <label for="userid" class="sr-only">사용자id</label>
      <input type="text" id="userid" name="userid" class="input-xs rounded w-50 bg-primary bg-opacity-10 border" placeholder="아이디" value="" required autofocus>
      <label for="inputPassword" class="sr-only">Password</label>
      <input type="password" id="password" name="password" class="input-xs rounded w-50 bg-primary bg-opacity-10 border" placeholder="Password" required>
      <!-- 
      <label for="otp" class="sr-only">OTP</label>
      <input type="text" id="otp" name="otp" class="input-xs rounded w-50 bg-primary bg-opacity-10 border" placeholder="OTP" required>
       -->
	  <sec:csrfInput />
      <button class="btn btn-secondary btn-block w-50" type="button" id="loginBtn" style="display:none"><i class="fa fa-key"></i> 로그인</button>
      <button class="btn btn-secondary btn-block w-50" type="button" id="otpBtn"><i class="fa fa-key"></i> OTP로그인</button>
      <p class="mt-5 mb-3 text-muted">&copy; APP</p>
    </form>
  </body>
</html>
