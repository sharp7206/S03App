<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Invalid Session</title>
<script type="text/javascript">
if((window.location != window.parent.location)){
	top.location.href = "${pageContext.request.contextPath}/";
} else{
	self.location.href = "${pageContext.request.contextPath}/";
}
</script>
</head>
<body>
</body>
</html>