<%--
 -===============================================================================================================
 - 시스템공통 - 공통포함용
 -===============================================================================================================
 - 2023/11/19 - 이호성 - 최종수정
 -===============================================================================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sys" uri="/WEB-INF/tld/systld.tld"%>
<sec:csrfMetaTags/>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!-- Font Awesome-->
<link rel="stylesheet" href="${pageContext.request.contextPath}/plug/fontawesome-free/css/all.min.css">
<link href="${pageContext.request.contextPath}/plug/bootstrap-5.3.2-dist/css/bootstrap.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/plug/bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>
 
 <!-- 기존스타일시트-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/design2/css/style.css" media="all"/>

<!-- jQuery -->
<script src="${pageContext.request.contextPath}/plug/jquery/jquery.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/plug/jquery-ui/jquery-ui.css">
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

<script src="${pageContext.request.contextPath}/plug/base64img/jquery.base64img.js"></script>
<!-- APPz.common -->
<script type="text/javascript" charset="utf-8">
	sessionStorage.setItem("contextpath", "${pageContext.request.contextPath}");
</script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/APPz.common.css?ver=2023.09.19">
<script src="${pageContext.request.contextPath}/res/js/APPz.common.js?ver=2023.09.19"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/plug/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script>

<script src="${pageContext.request.contextPath}/plug/flatpickr.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plug/sheet/js/ibleaders.js?pathz"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plug/sheet/js/ibsheetinfo.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plug/sheet/js/ibsheet.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/res/design2/css/style.css"> 
 <style type="text/css">
.ui-datepicker {
    width: 240px !important; /* 너비를 원하는 크기로 조정합니다. */
}
.ui-datepicker-multi {
    width: 440px !important; /* 너비를 원하는 크기로 조정합니다. */
}
duDialog {
    font-size: 20px; /* 적절한 크기로 조절하세요 */
}
</style>
<script type="text/javascript">
$().inputValues.config({
    attr: "id" 
   ,includeDisabled:true//disabed포함
});
</script>
