<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="appz" uri="/WEB-INF/tld/systld.tld"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>부서검색</title>
<script type="text/javascript">
//===============================================================================================================
//페이지 변수선언
//===============================================================================================================
let grid1;//목록 그리드 변수
let _jsonpopupparam = JSON.parse(decodeURI("${param._jsonpopupparam}"));
//===============================================================================================================
//jQuery ready
//===============================================================================================================
$(()=>{
	initGrid();//그리드 초기설정
	initPage();//페이지 초기설정
});

//===============================================================================================================
//페이지 초기설정
//===============================================================================================================
function initPage(){

	$("form.schform input").keydown(function(key) {//사용자명검색시
		if (key.keyCode == 13) {//검색텍스트 입력후 엔터시
			$('[data-btn-control=btn-search]').trigger("click");// 자동조회
		}
	});

	let jsonParam;
	$('[data-btn-control]').click(function(){
    	$(this).blur();
    	switch (this.getAttribute("data-btn-control")) {
       	case "btn-search"://조회
       		jsonParam = { param : $("form.schform").inputValues() };
       		APPz.cmn.ApiRequest("${pageContext.request.contextPath}/api/co/common/getDeptList.do",jsonParam,true,function($result){
           		AUIGrid.setGridData(grid1, $result.list);//데이타매핑
           		AUIGrid.setSelectionByIndex(grid1, 0,0);//행선택
           	});
           	break;
        }
    });

	$('[data-btn-control=btn-search]').trigger("click");// 자동조회
}

//===============================================================================================================
//그리드 초기설정
//===============================================================================================================
function initGrid(){
	//그리드1
	let grid1ColumnLayout = [ // 그리드 칼럼 레이아웃 작성
		{headerText:"상위부서코드",	dataField:"updeptcd",		width:0},
		{headerText:"부서코드",		dataField:"deptcd",	width:"20%"},
		{headerText:"부서명",		dataField:"deptnm",	width:"70%"	,style:"left_style"},
		{headerText:"사용",			dataField:"useyn",	width:"8%",
			renderer:{
				type:"CheckBoxEditRenderer",
				showLabel:false,// 참, 거짓 텍스트 출력여부( 기본값 false )
				editable:false, 	// 체크박스 편집 활성화 여부(기본값 : false)
				checkValue:"Y", // true, false 인 경우가 기본
				unCheckValue:"N"
			}
		},
	];

	let grid1Prop = {//그리드 속성정의
		showAutoNoDataMessage :false,
		showRowNumColumn:false,
		liveVScrolling:false,
		rowIdField:"deptcd",
		editable:false,
		width:575,
		height:340,
	};
	grid1 = AUIGrid.create("#grid1Wrap", grid1ColumnLayout,grid1Prop);//그리드1 생성
	//그리드1 에디팅 시작 이벤트 등록(바인딩)
	AUIGrid.bind(grid1, "cellDoubleClick", function(event) {
		APPz.ui.modalCallback("${param._callback}",event.item);//콜백호출
		top.$("#"+_jsonpopupparam._modalid).modal('hide');
	});
}


</script>
</head>
<body>
<div class="container-fluid mt-2 h-100">
    <form class="schform" name="schform" method="post" onsubmit='return false'>
   		<div class="container-fluid border rounded bg-light shadow-lg">
    		<div class="container-fluid row p-1">
				<div class="col-auto jsmall pt-1 fw-bold text-secondary">부서명</div>
				<div class="col-auto"><input type="text" class="input-appz border" id="schtext" name="schtext" placeholder="부서명" data-ax-path="schtext"></div>
				<div class="col-auto"><button type="button" id="btnSearch" class="btn btn-outline-primary btn-appz" data-btn-control="btn-search"><i class="fas fa-search"></i> 조회</button></div>
    		</div>
		</div>
    </form>
    <div id="grid1Wrap" class="border mt-1 shadow-lg" style="height:400;"></div>
</div>
</body>
</html>