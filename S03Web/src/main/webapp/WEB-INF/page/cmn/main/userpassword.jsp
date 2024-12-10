<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>비번번호 변경 페이지 </title>
<script type="text/javascript">
let _jsonpopupparam = JSON.parse(decodeURI("${param._jsonpopupparam}"));
$.validator.addMethod("checkeng", function(value) {
	return /[a-zA-Z]/.test(value);
});
$.validator.addMethod("checkspc", function(value) {
	return /[$@!%*#?&]/.test(value);
});
$.validator.addMethod("checkdigit", function(value) {
	return /[0-9]/.test(value);
});
$.validator.addMethod("pwcheck", function(value) {
	return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@!%*#?&])[A-Za-z\d$@!%*#?&]{7,15}$/.test(value);
});
$.validator.addMethod("privcheck", function(value) {
	let jsonData = { param : {privpassword:value} };
	return "Y"== APPz.cmn.ApiRequest("${pageContext.request.contextPath}/api/co/main/validPrivpasswordYn.do",jsonData,true).validYn;
});
$.validator.addMethod("privsame", function(value) {
	return $("#privpassword").val() !== $("#password").val();
});

$(()=>{

	$("#form-user").validate({
		rules:{
			privpassword:{required:true,privcheck:true},
			password:{required:true,minlength:7,checkeng:true,checkspc:true,checkdigit:true,pwcheck:true,privsame:true},
			repassword:{ required:true,equalTo:password }
		},
		messages:{
		    privpassword:{
				required:APPz.getMessage("V","data.Input.Required","현재 비밀번호를"),
				privcheck:APPz.getMessage("V","password.equalTo","현재 비밀번호가")
		    },
		    password:{
				required:APPz.getMessage("V","data.Input.Required","신규 비밀번호를"),
			   	minlength:'최소 {0}글자여야 합니다.',
			   	checkeng:APPz.getMessage("V","password.include","영문자가"),
			   	checkspc:APPz.getMessage("V","password.include","특수문자($@!%*#?&)가"),
			   	checkdigit:APPz.getMessage("V","password.include","숫자가"),
				pwcheck:APPz.getMessage("V","password.pwcheck",""),
				privsame:APPz.getMessage("V","password.privsame","")
			},
			repassword:{
				required:APPz.getMessage("V","data.Input.Required","비밀번호 확인을"),
			   	equalTo:APPz.getMessage("V","password.equalTo","신규 비밀번호가")
			}
		}
	});

	$("#btnSave").click(function(){
		if($("#form-user").valid()== false ){
			return false;
		} else {
			duDialog(null,APPz.getMessage("C","confirm.DoProcess","저장"), {
				buttons: duDialog.OK_CANCEL,
				callbacks: {
					okClick: function(e) {
						this.hide()
						let jsonData = { user : $("#form-user").inputValues() };
			     		APPz.cmn.ApiRequest("${pageContext.request.contextPath}/api/co/main/saveUserPassword.do",jsonData,true,function($result){
			     			APPz.ui.modalCallback("${param._callback}","OK");//콜백호출
			     			parent.$("#"+_jsonpopupparam._modalid).modal("hide");//모달닫기...
			         	});
					}
				}
			});
		}
	});

});

</script>
</head>
<body>
<div class="container-fluid border p-2 shadow-sm rounded">
	<form id="form-user" method="post" action="" onsubmit="return false;">
		<div class="m-1">
			<input type="password" id="privpassword" name="privpassword" class="input-xs border" placeholder="현재비밀번호" required size="12">
		</div>
		<div class="m-1">
			<input type="password" id="password" name="password" class="input-xs border" placeholder="신규비밀번호" required size="12" maxlength="15">
		</div>
		<div class="m-1">
			<input type="password" id="repassword" name="repassword" class="input-xs border" placeholder="비밀번호확인" required size="12">
		</div>
		<div class="m-1">
			<button class="form-control btn btn-secondary btn-xs" id="btnSave" type="button"><i class="fa fa-save"></i> 저장</button>
		</div>
	</form>
</div>
</body>
</html>