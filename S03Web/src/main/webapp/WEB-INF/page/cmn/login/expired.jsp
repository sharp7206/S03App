<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Invalid Session</title>
</head>
<body>
<script type="text/javascript">
duDialog(null,APPz.getMessage("I","user.AnotherLogin",""),{
	callbacks: {
		okClick: function(e) {
			this.hide()
			let _isInIframe = (window.location != window.parent.location) ? true : false;// 현재화면이 iframe 내인지체크.
			if(_isInIframe){
				top.location.href = "${pageContext.request.contextPath}/";
			} else{
				self.location.href = "${pageContext.request.contextPath}/";
			}
		}
	}
});
</script>
</body>
</html>