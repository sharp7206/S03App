<%--
 -===============================================================================================================
 - 시스템공통 - 공통포함용
 -===============================================================================================================
 - 2023/11/19 - 이호성 - 최종수정
 -===============================================================================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:csrfMetaTags/>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!-- Font Awesome -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/plug/fontawesome-free/css/all.min.css">
<link href="${pageContext.request.contextPath}/plug/bootstrap-5.3.2-dist/css/bootstrap.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/plug/bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>

<!-- jQuery -->
<script src="${pageContext.request.contextPath}/plug/jquery/jquery.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<script src="${pageContext.request.contextPath}/plug/jquery-ui/jquery-ui.min.js"></script>
<!--  jQuery Validation Plugin  -->
<script src="${pageContext.request.contextPath}/plug/jquery.validate.min.js"></script>
<!-- overlayScrollbars -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/plug/overlayScrollbars/css/OverlayScrollbars.min.css">
<!-- duDialog -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plug/duDialog/duDialog.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/plug/duDialog/duDialog.js"></script>
<!--inputvalues -->
<script type="text/javascript" src="${pageContext.request.contextPath}/plug/inputvalues/input-values.jquery.js"></script>

<!-- air-datepicker-3.3.5 -->
<link href="${pageContext.request.contextPath}/plug/air-datepicker-3.3.5/air-datepicker.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/plug/air-datepicker-3.3.5/air-datepicker.min.js"></script>

<script src="${pageContext.request.contextPath}/plug/base64img/jquery.base64img.js"></script>
<!-- APPz.common -->
<script type="text/javascript" charset="utf-8">
	sessionStorage.setItem("contextpath", "${pageContext.request.contextPath}");
</script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/APPz.common.css?ver=2023.09.19">
<script src="${pageContext.request.contextPath}/res/js/APPz.common.js?ver=2023.09.19"></script>

<!-- AUIGrid 테마 CSS 파일입니다. 그리드 출력을 위해 꼭 삽입하십시오. -->
<!-- 원하는 테마가 있다면, 다른 파일로 교체 하십시오. -->
<link href="${pageContext.request.contextPath}/plug/AUIGrid/AUIGrid_blue_style.css" rel="stylesheet">
<!-- AUIGrid 라이센스 파일입니다. 그리드 출력을 위해 꼭 삽입하십시오. -->
<script type="text/javascript" src="${pageContext.request.contextPath}/plug/AUIGrid/AUIGridLicense.js"></script>
<!-- 실제적인 AUIGrid 라이브러리입니다. 그리드 출력을 위해 꼭 삽입하십시오.-->
<script type="text/javascript" src="${pageContext.request.contextPath}/plug/AUIGrid/AUIGrid.js"></script>
<!-- AUIGrid PDF 저장 JS -->
<script type="text/javascript" src="${pageContext.request.contextPath}/plug/AUIGrid/pdfkit/AUIGrid.pdfkit.js"></script>

 <style type="text/css">

</style>
<script type="text/javascript">
$().inputValues.config({
    attr: "id"
   ,includeDisabled:true//disabed포함
});
</script>
