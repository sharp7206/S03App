<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="appz" uri="/WEB-INF/tld/systld.tld"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/page/cmn/include/page_header.do"/>
<!-- ckeditor -->
<script src="${pageContext.request.contextPath}/plug/ckeditor/ckeditor.js"></script>
<script type="text/javascript" >

$(()=>{
	const watchdog = new CKSource.EditorWatchdog();

	window.watchdog = watchdog;

	watchdog.setCreator( ( element, config ) => {
		return CKSource.Editor
			.create( element, config )
			.then( editor => {
	        	editor.enableReadOnlyMode( 'feature-id' );
	        	let jsonParam = { param : JSON.parse(decodeURI("${param._jsonpopupparam}"))};
	        	APPz.cmn.ApiRequest("${pageContext.request.contextPath}/api/cmn/main/notice.do",jsonParam,true,function($jsonData){//게시내용
	        		editor.setData($jsonData.notice.content);
	        		APPz.ui.modalCallback("${param._callback}","OK");//콜백호출
	        	});
				return editor;
			})
	});
	watchdog.setDestructor( editor => {
		return editor.destroy();
	});

	watchdog.on( 'error', handleError );

	watchdog.create( document.querySelector( '.editor' ), {
			licenseKey: '',
	}).catch( handleError );

	function handleError( error ) {
		console.error( 'Oops, something went wrong!' );
		console.error( 'Please, report the following error on https://github.com/ckeditor/ckeditor5/issues with the build id and the error stack trace:' );
		console.warn( 'Build id: w6uboppsjsx9-g8f5qtmd6oy5' );
		console.error( error );
	}
});

</script>
</head>
<body>
<div class="container-fluid m-0 p-0">
	<div id="content" class="editor"></div>
</div>
</body>
</html>