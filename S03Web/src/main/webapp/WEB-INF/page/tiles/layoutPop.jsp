<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>APP 시스템관리Poplayouts</title>
<script type="text/javascript"></script>
</head>
<body>
<!-- wrap -->
<div id="wrap">
	<!-- header -->
	<div id="header">Poplayouts_header
		<tiles:insertAttribute name="header" />
	</div>
	<!-- container -->
	<div id="container">
		<!-- content -->
		<div id="content">
			<tiles:insertAttribute name="content" />
		</div>
		<!-- //content -->
	</div>
	<!-- //container -->
</div>
<!-- //wrap -->
</body>
</html>