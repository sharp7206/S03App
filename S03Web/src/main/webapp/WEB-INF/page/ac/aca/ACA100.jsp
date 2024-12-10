<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="appz" uri="/WEB-INF/tld/systld.tld"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title><appz:jsptitle prgmcd="${ _prgmcd_ }"/></title>
<script type="text/javascript">
//===============================================================================================================
//페이지 변수선언
//===============================================================================================================
let grid1;//목록 그리드 변수

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

	$("form.schform input").keydown((key)=>{//사용자명검색시
		if (key.keyCode == 13) {//검색텍스트 입력후 엔터시
			$('[data-btn-control=btn-search]').trigger("click");// 자동조회
		}
	});

	let addedRowItems;// 추가된 행 아이템들(배열)
	let editedRowItems;//수정된 행 아이템들(배열) : 수정된 필드와 수정안된 필드 모두를 얻음.
	let removedRowItems;// 삭제된 행 아이템들(배열)
	let jsonParam;

	$('[data-btn-control]').click(function(){
    	$(this).blur();
    	switch (this.getAttribute("data-btn-control")) {
       	case "btn-search"://조회
       		jsonParam = { param : $("form.schform").inputValues() };
       		APPz.cmn.ApiRequest("${pageContext.request.contextPath}/api/ac/ACA100/getSystemList.do",jsonParam,true,($result)=>{
       			debugger;
       			AUIGrid.clearGridData(grid1);
           		AUIGrid.setGridData(grid1, $result.systemList);//데이타매핑
           		AUIGrid.setSelectionByIndex(grid1, 0,0);//행선택
           		AUIGrid.resize(grid1);
           	});
           	break;
       	case "grid-btn-add":
       		let item = {useyn:"N",syscd:"",sysnm:"",iconnm:"",menucnt:0,programcnt:0};
       		AUIGrid.addRow(grid1, item, "last");
       		break;
       	case "grid-btn-del":
       		let checkedRowItems = AUIGrid.getCheckedRowItems(grid1);
   			if(checkedRowItems.length == 0){
       			duDialog(null,APPz.getMessage("E","data.Select.Required","삭제할 행을"));
   				return;
   			}
   			let isNotValid = false;
   			$.each(checkedRowItems, function(index, item){
   				if(item.item.menucnt + item.item.prgmcnt + item.item.grpcdcnt > 0 ){
   					duDialog(null,APPz.getMessage("E","check.Syscd.Delete",item.item.sysnm));
   					isNotValid = true;
   					return;
   				}
   			});
   			if(isNotValid) return;

   			duDialog(null,APPz.getMessage("C","confirm.DoProcess","삭제"), {
   				buttons: duDialog.OK_CANCEL,
   				callbacks: {
   					okClick:(e)=>{
   						this.hide()

			   			let checkedList = new Array();
			   			$.each(checkedRowItems,(index, item)=>{
			   				checkedList.push(item.rowIndex);
			   			});
			   			AUIGrid.removeRow(grid1,checkedList); // 현재 선택된 행(들) 삭제
			   			removedRowItems = AUIGrid.getRemovedItems(grid1);// 삭제된 행 아이템들(배열)
			   			jsonParam = {
			       			gridData : {
			       				remove : removedRowItems
			       			}
			   			};
			   			APPz.cmn.ApiRequest("${pageContext.request.contextPath}/api/ac/ACA100/removeSystemList.do",jsonParam,true,($result)=>{//메뉴저장
			   				duDialog(null,APPz.getMessage("I","data.Processed","삭제처리"),{
			   					callbacks: {
			   						okClick:(e)=>{
			   							this.hide()
			   							$('[data-btn-control=btn-search]').trigger("click");// 자동조회
			   						}
			   					}
			   				});
			   			});
   					}
   				}
   			});
       		break;
       	case "grid-btn-save":
       		addedRowItems = AUIGrid.getAddedRowItems(grid1);// 추가된 행 아이템들(배열)
       		editedRowItems = AUIGrid.getEditedRowItems(grid1);//수정된 행 아이템들(배열) : 수정된 필드와 수정안된 필드 모두를 얻음.
   			if(addedRowItems.length + editedRowItems.length == 0 ){
   				duDialog(null,APPz.getMessage("E","data.NotChanged",""));
       			return;
   			}
       		let isValid = AUIGrid.validateGridData(grid1, ["syscd", "sysnm","iconnm","ordno"], "필수 필드는 반드시 값을 직접 입력해야 합니다.");
   			if(!isValid) return;

   			duDialog(null,APPz.getMessage("C","confirm.DoProcess","저장"), {
   				buttons: duDialog.OK_CANCEL,
   				callbacks: {
   					okClick:function(e){
   						this.hide()

   						jsonParam = {
			       			gridData : {
				       			create : addedRowItems,
				       			update : editedRowItems
			       			}
			   			};
			   			APPz.cmn.ApiRequest("${pageContext.request.contextPath}/api/ac/ACA100/saveSystemList.do",jsonParam,true,($result)=>{
			   				duDialog(null,APPz.getMessage("I","data.Processed","저장"),{
			   					callbacks: {
			   						okClick:function(e){
			   							this.hide()
			   							$('[data-btn-control=btn-search]').trigger("click");// 자동조회
			   						}
			   					}
			   				});
			   			});

   					}
   				}
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
		{headerText:"시스템코드",dataField:"syscd"		,width:"100"	,headerStyle:"necessary_style"},
		{headerText:"시스템명"	,dataField:"sysnm"		,width:"200"	,headerStyle:"necessary_style"	,style:"left_style"},
		{headerText:"아이콘"	,dataField:"iconnm"		,width:"200"	,headerStyle:"necessary_style"	,style:"left_style",
			renderer:{ // HTML 템플릿 렌더러 사용
				type:"TemplateRenderer"
			},
			labelFunction:(rowIndex, columnIndex, value, headerText, item )=>{ // HTML 템플릿 작성
				let template = '<i class="'+value+'"> '+value+' </i>';
				return template; // HTML 형식의 스트링
			}
		},
		{headerText:"사용",	dataField:"useyn",	width:"35",
			renderer:{
				type:"CheckBoxEditRenderer",
				showLabel:false,// 참, 거짓 텍스트 출력여부( 기본값 false )
				editable:true,	// 체크박스 편집 활성화 여부(기본값 : false)
				checkValue:"Y",	// true, false 인 경우가 기본
				unCheckValue:"N"
			}
		},
		{headerText:"정렬순서"	,dataField:"ordno"		,width:"60"		,dataType:"numeric"		,formatString:"#,##0"	,headerStyle:"necessary_style"	,style:"right_style"},
		{headerText:"메뉴"		,dataField:"menucnt"	,width:"60"		,formatString:"#,##0"	,style:"right_style"	,editable:false},
		{headerText:"화면"		,dataField:"prgmcnt"	,width:"60"		,formatString:"#,##0"	,style:"right_style"	,editable:false},
		{headerText:"그룹코드"	,dataField:"grpcdcnt"	,width:"60"		,formatString:"#,##0"	,style:"right_style"	,editable:false},
		{headerText:"처리자ID"	,dataField:"chguserid"	,width:"0"		,editable:false},
		{headerText:"처리자"	,dataField:"chgusernm"	,width:"60"		,editable:false},
		{headerText:"처리일시"	,dataField:"chgdttm"	,width:"*"	,editable:false}
	];

	let grid1Prop = {//그리드 속성정의
		enableFilter:true,
		showFooter:false,
		showAutoNoDataMessage:false,
		showRowNumColumn:false,
		liveVScrolling:false,
		rowIdField:"rowId",
		editable:true,
		showStateColumn:true,
		softRemovePolicy:"exceptNew",// 사용자가 추가한 새행은 softRemoveRowMode 적용 안함. // 즉, 바로 삭제함.
		showRowCheckColumn:true,
		showRowAllCheckBox:false	// 전체선택 미표시
	};
	$("#grid1Wrap").height(self.innerHeight-160);
	grid1 = AUIGrid.create("#grid1Wrap", grid1ColumnLayout,grid1Prop);//그리드1 생성
	//그리드1 에디팅 시작 이벤트 등록(바인딩)
	AUIGrid.bind(grid1, "cellEditBegin",(event)=>{
		if(event.columnIndex == 0 && event.item.rowId == event.item.syscd){//추가행이 아니면.
			return false;
		}
		return true;
	});
}

//===============================================================================================================
//리사이즈 처리
//===============================================================================================================
$(window).resize(()=>{
	resizeGrid();
});

function resizeGrid(){
	$("#grid1Wrap").height(self.innerHeight-160);
	AUIGrid.resize(grid1);//그리드
}

</script>
</head>
<body class="bg-appz-body">
<div class="container-fluid mt-2">
    <appz:headinfo prgmcd="${ _prgmcd_ }"/>
    <form class="schform" name="schform" method="post" onsubmit='return false'>
   		<div class="container-fluid border rounded bg-light shadow-sm">
    		<div class="container-fluid row p-1">
				<div class="col-auto jsmall pt-1 fw-bold text-secondary">시스템명</div>
				<div class="col-auto"><input type="text" class="input-appz border" id="schtext" name="schtext" placeholder="시스템명" data-ax-path="schtext"></div>
				<div class="col-auto"><button type="button" id="btnSearch" class="btn btn-outline-primary btn-appz" data-btn-control="btn-search"><i class="fas fa-search"></i> 조회</button></div>
    		</div>
		</div>
    </form>
    <div class="p-1 mt-1 border rounded shadow-lg">
	    <div class="d-flex justify-content-end">
			<div>
	    		<appz:buttonauth prgmcd="${ _prgmcd_ }" prefix="grid" viewbtns="CUD"/>
			</div>
	    </div>
    	<div id="grid1Wrap" style="width:100%; height:380px;"></div>
    </div>
</div>
</body>
</html>