<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
function retName(nm)  { return eval("frm01." + nm); }
function retValue(nm) { return eval("frm01." + nm + ".value"); }
function retFocus(nm) { return eval("frm01." + nm + ".focus()"); }
let jsonParam;
let currentRow;
let jsonObjParam = JSON.parse(decodeURI("${param._jsonparam}"));
$(document).ready(function(){
	//jSYS.co.ApiRequest("${pageContext.request.contextPath}/api/co/main/userMenuList.do",{ param : {} },false,function(response){
	$("#FILE_GID").val(_jsonpopupparam.fileGid);
	$("#REF_TABLE_ID").val(_jsonpopupparam.refTableId);
	$("#REF_DOC_ID").val(_jsonpopupparam.refDocId);

	LoadPage();
	btn_ctrl();
});

btn_ctrl = function(){
	//사용자 그룹id (본사 : 1 - 02001, 총판 : 2 - 02002, 판매점 : 3 - 02003)
	if("02001"=="${requestScope.S01UserInfoVO.SHOPTYPECD}"){
	}else if("02002"=="${requestScope.S01UserInfoVO.SHOPTYPECD}"){
	}else if("02003"=="${requestScope.S01UserInfoVO.SHOPTYPECD}"){
	}
};

/*Sheet 기본 설정 */
function LoadPage(){
	var cfg = {SearchMode:smLazyLoad,Page:30,MergeSheet:msNone, FrozenCol:0};
	FileSheet.SetConfig(cfg);
	var headers = [ {Text:"No|STS|선택|FILEGID|SEQ|파일경로|파일명|FILE_NM|파일Size|파일유형|FILE_TMNAIL_NM|REF_TABLE_ID|REF_DOC_ID|등록자|등록일시"} ];

	var info = {Sort:1,ColMove:1,ColResize:1,HeaderCheck:3};
	FileSheet.InitHeaders(headers,info);
	var cols = [
			{Type:"Seq", MInWidth:60,Align:"center"},
			{Type:"Status",    MinWidth:50,  SaveName:"SSTATUS", Hidden:0},
	    	{Type:"DelCheck",  MinWidth:60},
	    	{Type:"Text",      MinWidth:100, SaveName:"FILE_GID",         Edit: true, Hidden:true},
			{Type:"Text",      MinWidth:60,  SaveName:"SEQ",         Edit: true, Hidden:true},
			{Type:"Text",      MinWidth:200,  SaveName:"FILE_PATH",   Edit: true, Hidden:true, KeyField:1},
			{Type:"Text",      MinWidth:180,  SaveName:"FILE_ORG_NM", Edit: false},
			{Type:"Text",      MinWidth:80,   SaveName:"FILE_NM",     Edit: false, Hidden:true, KeyField:1},
			{Type:"Int",      MinWidth:80,  SaveName:"FILE_SIZE",   Edit: false},
			{Type:"Text",      MinWidth:60,  SaveName:"FILE_TYPE",   Edit: true, Align: "center"},
			{Type:"Text",      MinWidth:300,  SaveName:"FILE_TMNAIL_NM", Edit: false, Hidden:true},
			{Type:"Text",      MinWidth:80,   SaveName:"REF_TABLE_ID",  Edit: false, Hidden:true},
			{Type:"Text",      MinWidth:80,   SaveName:"REF_DOC_ID",  Edit: false, Hidden:true},
			{Type:"Text",      MinWidth:80,   SaveName:"REG_NM", Edit: false, Align: "center"},
			{Type:"Date",      MinWidth:100,  SaveName:"REG_DT",      Edit: false, Align: "center"}
	];
	FileSheet.InitColumns(cols);

	FileSheet.SetCountPosition(4);
	doAction('SearchFileList');

}



/*Sheet 각종 처리*/
function doAction(sAction)
{
  switch(sAction)
  {
	case "SearchFileList" :      //조회
		FileSheet.RemoveAll();
		FileSheet.DoSearch("<c:url value="/file/searchFileUploadInfo.do"/>", getFormJson("frm01"), {ReqHeader : {"Content-Type":"application/json"}});
		break;
	case "Close": //적용
    	try{
	    	parent.$("#"+jsonObjParam._modalid).modal('hide');
    	} catch (e) {
    		self.close();
    	}	  
      break;
  }
}

/**S01_FILE_INFO
 * fileGid : FILE_GID
 * refTableId : REF_TABLE_ID (S01_CATALOGIMG, S01_STAMP, S01_BOARD, ETC 에 따라서 업로드 경로가 트림 
 * maxFileCnt : FILE LIMIT 
 * uploadType : STRING ex)  'gif|JPG|jpeg'
 * callBack : POPUp Close 시에 호출 method
 */
fn_imageView = function(fileGid, seq){
	 
 	 var imgBase64 = 'data:image/jpeg;base64,'+jSYS.viewImgBase64(fileGid, seq);
	 $("#viewImg2").attr("src",imgBase64);// 사용자사진
	//var url = "../../file/imageView.do;
	//$('#viewImg2').attr('src', url);
};

	function FileSheet_OnSearchEnd(code,msg){
		
		if(code<0){
			alert(msg);
		}else{
		}
	}
	
	function FileSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH, rowType) { 
		if(Row>0){
			var fileGid = FileSheet.GetCellValue(Row, 'FILE_GID');
			var seq = FileSheet.GetCellValue(Row, 'SEQ');
			//fn_imageView(fileGid, seq);
			/**S01_FILE_INFO
			 * fileGid : FILE_GID
			 * refTableId : REF_TABLE_ID (S01_CATALOGIMG, S01_STAMP, S01_BOARD, ETC 에 따라서 업로드 경로가 트림 
			 * maxFileCnt : FILE LIMIT 
			 * uploadType : STRING ex)  'gif|JPG|jpeg'
			 * callBack : POPUp Close 시에 호출 method
			 */
			
					//var url = "../../file/imageView.do";
					
				var url = "${pageContext.request.contextPath}/file/imageView.do?fileGid="+fileGid+'&seq='+seq;
				jSYS.Popup.openDialog(url,{param : {FILE_GID : fileGid
					                              , SEQ : seq
					                              } 
					                      }, "800", "600", null);
			
		}
	}

	
	
var windowHeight = $(window).height()/2-140;
var imgHeight = $(window).height()/2-140;
if(windowHeight < 200) windowHeight = 200;
</script>
    <style>
.fit-picture {
    width: 400px;
}

    </style>
	
<!--조회함수를 이용하여 조회 완료되었을때 발생하는 이벤트-->

<form id="frm01" name="frm01" method="post">
<input type="hidden" id="sheetVal" name="sheetVal" value="" />
<input type="hidden" name="crud" value="">
<input type="hidden" id="FILE_GID" name="FILE_GID" value="">
<input type="hidden" id="REF_TABLE_ID" name="REF_TABLE_ID" value="">
<input type="hidden" id="REF_DOC_ID" name="REF_DOC_ID" value="">
<input type="hidden" id="SysClss" name="SysClss" value="">
	<div id="section">
		<ul class="btns" style="">
			<li class="fr">
				<a id='BTN_SEARCH' style ='display:'  href="javascript:doAction('SearchFileList');" class="btnl"><span>조회</span></a>
				<a id='BTN_CLOSE' style ='display:' href="javascript:doAction('Close');" class="btnl"><span>닫기</span></a>
			</li>	
		</ul>
		<div id="divImgView"> 
		<table class="type2">
		<colgroup>
		<col style="border:0px solid #999;" width="*">
		</colgroup>
		<tbody>
			<tr>
				<td>
					<img id="viewImg2" class="fit-picture" src="../../img/images/lava.png" alt="Grapefruit slice atop a pile of other slices">
				</td>
			</tr>
		</tbody>
		</table>	
		</div>
		<!-- start -->
		<table class="type2">
		<colgroup>
		<col width="100%">	
		<col width="*">
		</colgroup>
		<tbody>
		<tr>
			<td class='TDL' align='left' style="">
				<script>
				createIBSheet("FileSheet", "100%", windowHeight+"px");
				</script>
			</td>
		</tr>		
		</table>		
<!----------------------- 본문내용 종료 -------------------------------------------------->
	</div>
</form>	
