<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>접근 권한이 없습니다.</title>
</head>
<body class="bg-appz-body">
<div class="container-fluid mt-2">
	<div class="p-3">
		<div class="col text-danger fw-bold m-3">
			<h4><i class="fas fa-ban"></i> 접근 권한이 없습니다!</h4>
		</div>
        <c:if test="${ not empty message }">
			<div class="alert alert-danger m-3 shadow-lg" role="alert">${ message }</div>
        </c:if>
	</div>
</div>
</body>
</html>