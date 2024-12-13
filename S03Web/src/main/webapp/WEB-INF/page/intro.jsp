<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<security:csrfMetaTags/>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>APP관리시스템 - <security:authentication property="principal.username" /></title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/res/img/favicon.ico">
<!-- ./wrapper -->
<!-- jQuery 3.6.0 -->
<script src="${pageContext.request.contextPath}/plug/jquery/jquery.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<script src="${pageContext.request.contextPath}/plug/jquery-ui/jquery-ui.min.js"></script>

<!-- bootstrap5.3.2 -->
<link href="${pageContext.request.contextPath}/plug/bootstrap-5.3.2-dist/css/bootstrap.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/plug/bootstrap-5.3.2-dist/js/bootstrap.bundle.min.js"></script>


<!-- Font Awesome -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/plug/fontawesome-free/css/all.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/plug/adminlte/css/adminlte.css">
<!-- overlayScrollbars -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/plug/overlayScrollbars/css/OverlayScrollbars.min.css">
<!-- APPz.common -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/APPz.common.css?ver=2023.09.19">
<!-- duDialog -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plug/duDialog/duDialog.css" />

<!-- overlayScrollbars -->
<script src="${pageContext.request.contextPath}/plug/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
<!-- AdminLTE App -->
<script src="${pageContext.request.contextPath}/plug/adminlte/js/adminlte.js"></script>
<script src="${pageContext.request.contextPath}/plug/base64img/jquery.base64img.js"></script>
<!-- duDialog -->
<script type="text/javascript" src="${pageContext.request.contextPath}/plug/duDialog/duDialog.js"></script>
<!-- APPz.common -->
<script src="${pageContext.request.contextPath}/res/js/APPz.common.js?ver=2023.09.19"></script>

<!-- APPz.common -->
<script type="text/javascript" charset="utf-8">

	sessionStorage.setItem("contextpath", "${pageContext.request.contextPath}");
    // Custom script for tab reload
    $(document).ready(function () {
      // Initial content load
      reloadTabContent();

      // Tab shown event
      $('#myTabs a').on('shown.bs.tab', function (e) {
        // Reload content of the active tab
        reloadTabContent();
      });

      // Function to reload the content of the active tab
      function reloadTabContent() {
        var activeTabId = $('#myTabs .active a').attr('href');
        var $activeTabContent = $(activeTabId + 'Content');

        // Simulate content reload (you can replace this with your actual content reload logic)
        $activeTabContent.html('Content of ' + activeTabId);
      }
      
  	//////////////////////// Prevent closing from click inside dropdown
      $(document).on('click', '.dropdown-menu', function (e) {
        e.stopPropagation();
      });

      // make it as accordion for smaller screens
      if ($(window).width() < 992) {
  	  	$('.dropdown-menu a').click(function(e){
  	  		e.preventDefault();
  	        if($(this).next('.submenu').length){
  	        	$(this).next('.submenu').toggle();
  	        }
  	        $('.dropdown').on('hide.bs.dropdown', function () {
  			   $(this).find('.submenu').hide();
  			})
  	  	});
  	}      
    });	
</script>
</head>

<style type="text/css">
	@media (min-width: 992px){
		.dropdown-menu .dropdown-toggle:after{
			border-top: .3em solid transparent;
		    border-right: 0;
		    border-bottom: .3em solid transparent;
		    border-left: .3em solid;
		}

		.dropdown-menu .dropdown-menu{
			margin-left:0; margin-right: 0;
		}

		.dropdown-menu li{
			position: relative;
		}
		.nav-item .submenu{ 
			display: none;
			position: absolute;
			left:100%; top:-7px;
		}
		.nav-item .submenu-left{ 
			right:100%; left:auto;
		}

		.dropdown-menu > li:hover{ background-color: #f1f1f1 }
		.dropdown-menu > li:hover > .submenu{
			display: block;
		}
	}
</style>
<body class="sidebar-mini layout-fixed layout-footer-fixed" data-panel-auto-height-mode="height">
<div class="wrapper">

	<!-- Navbar -->
  	<nav class="main-header navbar navbar-expand-md navbar-white navbar-light">
  		<div class="container-fluid">
    	<!-- Left navbar links -->
    	<ul class="navbar-nav">
      		<li class="nav-item dropdown">
        		<a class="nav-link pt-2" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
      		</li>
      		<li class="nav-item d-none d-sm-inline-block align-bottom text-secondary">
        		<a id="_home_" href="${pageContext.request.contextPath}/page/cmn/main/home.do" class="nav-link pt-2"><i class="fa fa-home"></i> Home</a>
      		</li>      		
			<c:forEach items="${sysMenu }" var="list">
	   			<c:if test="${list.menulvl eq '1' && list.leafYn eq 'N'}">
      		<li class="nav-item d-none d-sm-inline-block align-bottom text-secondary">
        		<a id="${list.MENU_CD }" href="${pageContext.request.contextPath}${list.viewurl }" class="nav-link pt-2">${list.MENU_NM }</a>
      		</li> 
	   			</c:if>
	   			<c:if test="${list.menulvl eq '1' && list.leafYn eq 'Y'}">
	      		<li class="nav-item dropdown">
					<li class="nav-item dropdown">
						<a class="nav-link  dropdown-toggle" href="#" data-bs-toggle="dropdown"> ${list.menuNm } </a>
						<ul class="dropdown-menu">
		      			<c:forEach items="${sysMenu }" var="list2">
		      			<c:if test="${list2.menulvl eq '2' && list.MENU_CD eq list2.upMenuCd && list2.leafYn eq 'Y'}">
		      				<li><a class="dropdown-item" href="#"> ${list2.menuNm } &raquo </a>
		      					<ul class="submenu dropdown-menu">
				      				<c:forEach items="${sysMenu }" var="list3">
		      					<c:if test="${list3.menulvl eq '3' && list2.MENU_CD eq list3.upMenuCd}">
		      						<li><a class="dropdown-item" href="${pageContext.request.contextPath}${list3.viewurl }"> ${list3.menuNm } </a></li>
		      					</c:if>
				      				</c:forEach>
		      					</ul>
		      				</li>
		      			</c:if>		      			
		      			<c:if test="${list2.menulvl eq '2' && list.menuCd eq list2.upMenuCd && list2.leafYn eq 'N'}">
		      				<li><a class="dropdown-item" href="${pageContext.request.contextPath}${list2.viewurl }" onclick="javascript:userMenuInit('${list2.upMenuCd}', '${list2.menuCd}');"> ${list2.menuNm } </a></li>
		      			</c:if>		      			
						</c:forEach>							
						</ul>		      			
					</li>
				</c:if>
			</c:forEach>
			</li>
    	</ul>
    	<!-- Right navbar links -->
    	<ul class="navbar-nav ml-auto">
      		<li class="nav-item pr-3 pl-3">
      			<div class="dropdown-center">
  					<a class="dropdown-toggle border border-0 pt-2" data-bs-toggle="dropdown" aria-expanded="false">
    					<img id="_user_photo" src="${pageContext.request.contextPath}/res/img/profile_user.png" width="35" height="35" class="img-circle elevation-2" alt="UsePhoto"><b class="text-info pl-2 fs-6"> <security:authentication property="principal.username" /> 님 </b>
  					</a>
  					<ul class="dropdown-menu">
    					<li><a class="dropdown-item" href="#" id="_userphoto" style="cursor: pointer;"><i class="far fa-address-card"></i> 나의 등록정보</a></li>
    					<li><hr class="dropdown-divider"></li>
    					<li><a class="dropdown-item" href="#" id="_userpassword" style="cursor: pointer;"><i class="fab fa-expeditedssl"></i> 비밀번호변경</a></li>
    					<li><hr class="dropdown-divider"></li>
    					<li>
          					<form action="${pageContext.request.contextPath}/logout" method="post">
          						<security:csrfInput/>
			   					<button class="border-white pe-0 text-secondary dropdown-item"><i class="fas fa-sign-out-alt"></i> logout</button>
          					</form>
      					</li>
  					</ul>

				</div>
			</li>
			<li class="nav-item">
          		<form action="${pageContext.request.contextPath}/logout" method="post">
          			<security:csrfInput/>
			   		<button class="btn border-white pl-0 pe-0 text-secondary"><i class="fas fa-sign-out-alt fa-lg"></i></button>
          		</form>
      		</li>
      		<li class="nav-item">
        		<a class="nav-link pt-2 pr-2 pl-4" data-widget="fullscreen" href="#" role="button">
          			<i class="fas fa-expand-arrows-alt"></i>
        		</a>
      		</li>
    	</ul>
        </div>	
	</nav>
  	<!-- /.navbar -->
		
  	<!-- Main Sidebar Container -->
  	<aside class="main-sidebar sidebar-dark-primary elevation-4">
    	<!-- Brand Logo -->
    	<a href="${pageContext.request.contextPath}/intro.do" class="brand-link">
      		<img src="${pageContext.request.contextPath}/res/img/logo/mlogo.png" alt="Mini Logo" class="brand-image" style="opacity:1.0">
      		<span class="brand-text font-weight-light"><b>통합관리시스템</b></span>
    	</a>
    	<!-- Sidebar -->
    	<div class="sidebar">
       		<nav class="m-1">
        		<ul id="menu_root" class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
	        		<c:set var="_privmenulvl" value="0"/>
	        		<c:set var="_tagOpenCnt" value="0"/>
	        		<c:set var="_data_cnt" value="${ fn:length(userMenu) }"/>
	         		<c:forEach var="menu" items="${ userMenu }">
	         			<c:if test="${ _privmenulvl > menu.menulvl }">
	         				<c:forEach begin="0" end="${ _privmenulvl - menu.menulvl}" step="1">
	         					<c:if test="${_tagOpenCnt > 0 }">
									</ul></li>
									<c:set var="_tagOpenCnt" value="${ _tagOpenCnt-1 }"/>
								</c:if>
							</c:forEach>
	         			</c:if>
	         			<c:choose>
		         			<c:when test="${ fn:length(menu.menuCd ) eq 2 }">
		         				<li class="nav-header"><b><font class="text-info" size="3"> <i class="nav-icon ${menu.iconnm}"></i> ${menu.menuNm}</font></b></li>
		         			</c:when>
		         			<c:otherwise>
		         				<li class="nav-item">
		         				<c:choose>
		         					<c:when test="${ not empty menu.viewurl }">
		         						<a href="${pageContext.request.contextPath}${ menu.viewurl }" class="nav-link">
		         					</c:when>
		         					<c:otherwise>
		         						<a href="#" class="nav-link">
		         					</c:otherwise>
		         				</c:choose>
		         				<i class="nav-icon ${ menu.iconnm }"></i>
		         				<p>${ menu.menuNm }</p>
		         				<c:choose>
		         					<c:when test="${ menu.leafYn eq 'N' }">
		         						<i class="fas fa-angle-left right"></i></a><ul class="nav nav-treeview">
		         						<c:set var="_tagOpenCnt" value="${ _tagOpenCnt+1 }"/>
		         					</c:when>
		         					<c:otherwise>
		         						</a><c:if test="${ status.index+1 ne _data_cnt }"></li></c:if>
		         					</c:otherwise>
		         				</c:choose>
		         			</c:otherwise>
	         			</c:choose>
	         			<c:if test="${ status.index+1 eq _data_cnt }">
	         				<c:forEach begin="1" end="${ _tagOpenCnt }" step="1">
								</ul></li>
							</c:forEach>
							<c:set var="_tagOpenCnt" value="0"/>
	         				</li>
	         			</c:if>
	         			<c:set var="_privmenulvl" value="${ menu.menulvl }"/>
	      			</c:forEach>
        		</ul>
      		</nav>
    	</div>
    	<!-- /.sidebar -->  	
  	<!-- 
		 -->
  	</aside>

  	<!-- Content Wrapper. Contains page content -->
  	<div class="content-wrapper iframe-mode" data-widget="iframe" data-loading-screen="750">
    	<div class="nav navbar navbar-expand navbar-white navbar-light border-bottom p-0">
			<div class="nav-item dropdown bg-secondary bg-gradient">
        		<a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">탭닫기</a>
        		<ul class="dropdown-menu p-0">
          			<li><a class="dropdown-item bg-secondary" href="#" data-widget="iframe-close" data-type="all">모든탭닫기</a></li>
          			<li><a class="dropdown-item bg-secondary" href="#" data-widget="iframe-close" data-type="all-other">다른모든탭닫기</a></li>
        		</ul>
      		</div>
      		<a class="nav-link bg-light" href="#" data-widget="iframe-scrollleft"><i class="fas fa-angle-double-left"></i></a>
      		<ul class="navbar-nav overflow-hidden" role="tablist"></ul>
      		<a class="nav-link bg-light" href="#" data-widget="iframe-scrollright"><i class="fas fa-angle-double-right"></i></a>
      		<a class="nav-link bg-light" href="#" data-widget="iframe-fullscreen"><i class="fas fa-expand"></i></a>
    	</div>
    	<div class="tab-content">
      		<div class="tab-empty">
      		</div>
      		<div class="tab-loading">
      		</div>
    	</div>
  	</div>
  	<!-- /.content-wrapper
  	<footer class="main-footer bg-light bg-gradient">
	  	<div class="text-secondary">
	   		<strong class="text-primary">Copyright &copy; APP All rights reserved.</strong>
	  	</div>
  	</footer> -->
  	<!-- Control Sidebar -->
  	<aside class="control-sidebar control-sidebar-dark">
    	<!-- Control sidebar content goes here -->
  	</aside>
	<!-- /.control-sidebar -->
</div>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
  	$.widget.bridge('uibutton', $.ui.button)
</script>
<script type="text/javascript">
let _openType;
	$(document).ready(function() {
		  $('.nav-link').on('click', function() {
		  });
		});
$(()=>{
	//$("#_user_photo").attr("src",APPz.userphoto('<security:authentication property="principal.username" />'));// 사용자사진
	let response = APPz.cmn.ApiRequest("${pageContext.request.contextPath}/api/cmn/main/dummy.do",{ param : {} },false);
	$("#_userpassword").click(function(){
		_openType = '_userpassword';//비밀번호변경.
		APPz.ui.openModal("${pageContext.request.contextPath}/page/cmn/main/userpassword.do",$(this).html(),{},480, 180,false,"modalCallback");
	});
	$("#_userphoto").click(()=>{
		_openType = '_userinfo';//사용자등록정보
		APPz.ui.openModal("${pageContext.request.contextPath}/page/cmn/main/userinfo.do","나의 등록정보",{},650, 400,false,"modalCallback");
	});
    if("N" == "<security:authentication property="principal.pwsetyn" />"){
    	duDialog(null,APPz.getMessage("E","user.ChangePassword",""),{
			callbacks: {
				okClick:function(e){
					this.hide();
					_openType = '_userpasswordset';//비밀번호변경.
					APPz.ui.openModal("${pageContext.request.contextPath}/page/cmn/main/userpassword.do","신규 비밀번호 등록",{},480, 180,true,"modalCallback");
				}
			}
		});
    }
   	setTimeout(()=>{
   		$("#_home_").trigger('click');
   	},100);

   	$("#syscd").change(()=>{
   		$("#systemform").submit();
   	});
});

//openModal 콜백처리.
function modalCallback(returnVal){
	//console.info("openType = %s, callback is called!!, returnVal = %s",_openType,returnVal);
	switch(_openType){
	case '_userpassword'://비밀번호변경
		//console.info(_openType," callback is called!!");
		break;
	case '_userpasswordset'://비밀번호변경
		//console.info(_openType," callback is called!!");
		break;
	case '_userinfo'://사용자등록정보
		//console.info(_openType," callback is called!!");
		if(returnVal !== undefined && returnVal != null){
			$("#_user_photo").attr("src",returnVal);
		}
		break;
	}
}

function userMenuInit(sideMenuL, curMenu){
	
	g_curMenu = curMenu;
	/**공통코드작업 START**/
	jsonParam = { param : {
					upMenuCd:sideMenuL 
				}};//
	let userMenu; 
	APPz.cmn.ApiRequest("${pageContext.request.contextPath}/api/cmn/main/selectSideMenu.do"
		, jsonParam,true
		, function($result){
			userMenu = $result.Data;
			let menuHtml = '',_privmenulvl = 0,_tagOpenCnt = 0,_data_cnt=userMenu.length;
			$.each(userMenu, function (index, el) {
				if(_privmenulvl > parseInt(el.menulvl) ){
					for(var i = 0 ; i <= (_privmenulvl-parseInt(el.menulvl));i++){
						if(_tagOpenCnt > 0) {
							menuHtml += '</ul></li>';
							_tagOpenCnt--;
						}
					}
				}
				
				if (el.menulvl==0) {
					menuHtml += '<li class="nav-header"><b><font class="text-info" size="3"><i class="nav-icon "></i> '
									+el.menuNm
									+'</font></b></li>';
				} else {
					menuHtml += '<li class="nav-item">\n';
					if(el.viewurl){
						if(el.menuCd==g_curMenu){
							menuHtml += '<a href="'+APPz.context()+el.viewurl+'" class="nav-link active">';
						}else{
							menuHtml += '<a href="'+APPz.context()+el.viewurl+'" class="nav-link">';
						}
					} else {
						menuHtml += '<a href="#" class="nav-link">';
					}
					menuHtml += '<i class="nav-icon "></i> <p>'+el.menuNm+'</p>';
					if(el.leafYn ==='Y'){
						menuHtml += '<i class="right fas fa-angle-left"></i></a><ul class="nav nav-treeview">';
						_tagOpenCnt++;
					} else {
						menuHtml += '</a>';
						if((index+1) !== _data_cnt) menuHtml += '</li>';
					}
				}
				if(index+1 == _data_cnt){
					for(var i = 1 ; i <= _tagOpenCnt ;i++){
						menuHtml += '</li></ul>';
					}
					_tagOpenCnt = 0;
					menuHtml += '</li>\n';
				}
				_privmenulvl = parseInt(el.menulvl);
			});
			$("#menu_root").html(menuHtml);				
   		}
	);
	

}	

</script>
</body>
</html>