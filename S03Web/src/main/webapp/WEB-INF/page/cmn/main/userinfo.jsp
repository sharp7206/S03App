<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>사용자정보 변경 페이지 </title>
<c:import url="/page/cmn/include/page_header.do"/>
<script type="text/javascript">
let _jsonpopupparam = JSON.parse(decodeURI("${param._jsonpopupparam}"));
debugger;
$(()=>{
	APPz.cmn.ApiRequest("${pageContext.request.contextPath}/api/co/main/getMyInfo.do",{ param :{ context : "${pageContext.request.contextPath}" } },true,
	function(response){
		debugger;
		APPz.ui.setCombo($('#basesyscd'),response.userSys,"syscd","sysnm");//
		$("#form-user").inputValues(response.myInfo);
		$("#_user_photo").attr("src",response.myInfo.photosrc);
	});

	$('#image').on('change', function(e){
		$(this).base64img({
			url: e.target.files[0],
			result: "#result"
		});
		setTimeout(function(){
			if(APPz.ui.checkFileSize("image",50,"K")){//50KB미만이면
				$("#_user_photo").attr("src",$("#result").val());
			} else {
				$("#result").val("");
			}
		},100);
	});
	$("#btnSave").click(function(){
		if(APPz.ui.checkFileSize("image",50,"K")== false ){
			$("#result").val("");
			return false;
		} else {
			duDialog(null,APPz.getMessage("C","confirm.DoProcess","저장"), {
				buttons: duDialog.OK_CANCEL,
				callbacks: {
					okClick: function(e) {
						this.hide()
						let jsonData = { param : $("#form-user").inputValues() };
			     		APPz.cmn.ApiRequest("${pageContext.request.contextPath}/api/co/main/saveMyInfo.do",jsonData,true,function($result){
			     			if($("#result").val()!==""){
			     				APPz.ui.modalCallback("${param._callback}",$("#result").val());//콜백호출
			     			}
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
<div class="container-fluid border p-2 shadow-lg rounded">
	<form id="form-user" method="post" action="" onsubmit="return false;">
		<div class="p-1">
			<img id="_user_photo" src="${pageContext.request.contextPath}/res/img/profile_user.png" width="120" height="120" alt="UsePhoto" class="rounded-circle mx-auto d-block img-thumbnail">
			<input type="file" id="image" class="form-control form-control-sm" accept=".png,.jpg">
			<div class="text-warning jsmall bg-secondary border mt-2 p-1 text-center rounded">사진은 jpg 또는 png 형식의 50KB 미만 용량의 얼굴 포함된 정사각형 이미지 등록 바랍니다.</div>
		</div>
		<div class="p-1">
			<table class="table table-bordered table-xs shadow-sm">
				<colgroup>
					<col width="30%">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<td class="text-center table-light small p-1 fw-semibold text-primary">시작시스템설정</td>
						<td><select name="basesyscd" id="basesyscd" class="input-xs rounded border bg-light text-primary"></select></td>
					</tr>
					<tr>
						<td class="text-center table-light small p-1">최종로그인시각</td>
						<td class="text-center"><input type="text" id="lastlogindttm" class="form-control-plaintext input-xs" disabled></td>
					</tr>
					<tr>
						<td class="text-center table-light small p-1">비밀번호변경일시</td>
						<td><input type="text" id="pwsetdttm" class="form-control-plaintext input-xs" disabled></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="p-1">
			<textarea id="result" style="display:none;"></textarea>
			<button class="form-control btn btn-secondary btn-xs" id="btnSave" type="button"><i class="fa fa-save"></i> 저장</button>
		</div>
	</form>
</div>
</body>
</html>