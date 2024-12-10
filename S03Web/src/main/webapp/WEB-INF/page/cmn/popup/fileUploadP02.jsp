<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%--------------------------------------------------------------------
 * 프로젝트	: Aftermarket 판매관리 시스템
 * 프로그램명	: SearchItemPop
 * 설명		: 제품 조회
 * 작성자		: 이호성
 * 작성일자	: 2022/11/23
 * 수정자		:
 * 수정일자	:
 * 수정내용	:
 ---------------------------------------------------------------------%>
  <head>

 <!-- jQuery
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    ====================================================================== -->

    <!-- Fine Uploader New/Modern CSS file
    ====================================================================== -->
    <link href="${pageContext.request.contextPath}/js/fine-uploader/fine-uploader-new.css" rel="stylesheet">

    <!-- Fine Uploader jQuery JS file
    ====================================================================== -->
    <script src="${pageContext.request.contextPath}/js/fine-uploader/jquery.fine-uploader.js"></script>

    <!-- Fine Uploader Thumbnails template w/ customization
    ====================================================================== -->
    <script type="text/template" id="qq-template-manual-trigger">
        <div class="qq-uploader-selector qq-uploader" qq-drop-area-text="Drop files here">
            <div class="qq-total-progress-bar-container-selector qq-total-progress-bar-container">
                <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-total-progress-bar-selector qq-progress-bar qq-total-progress-bar"></div>
            </div>
            <div class="qq-upload-drop-area-selector qq-upload-drop-area" qq-hide-dropzone>
                <span class="qq-upload-drop-area-text-selector"></span>
            </div>
            <div class="buttons">
                <div class="qq-upload-button-selector qq-upload-button">
                    <div>FILE</div>
                </div>
                <button type="button" id="trigger-upload" class="btn btn-primary">
                    <i class="icon-upload icon-white"></i> Upload
                </button>
            </div>
            <span class="qq-drop-processing-selector qq-drop-processing">
                <span>Processing dropped files...</span>
                <span class="qq-drop-processing-spinner-selector qq-drop-processing-spinner"></span>
            </span>
            <ul class="qq-upload-list-selector qq-upload-list" aria-live="polite" aria-relevant="additions removals">
                <li>
                    <div class="qq-progress-bar-container-selector">
                        <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-progress-bar-selector qq-progress-bar"></div>
                    </div>
                    <span class="qq-upload-spinner-selector qq-upload-spinner"></span>
                    <img class="qq-thumbnail-selector" qq-max-size="100" qq-server-scale>
                    <span class="qq-upload-file-selector qq-upload-file"></span>
                    <span class="qq-edit-filename-icon-selector qq-edit-filename-icon" aria-label="Edit filename"></span>
                    <input class="qq-edit-filename-selector qq-edit-filename" tabindex="0" type="text">
                    <span class="qq-upload-size-selector qq-upload-size"></span>
                    <button type="button" class="qq-btn qq-upload-cancel-selector qq-upload-cancel">Cancel</button>
                    <button type="button" class="qq-btn qq-upload-retry-selector qq-upload-retry">Retry</button>
                    <button type="button" class="qq-btn qq-upload-delete-selector qq-upload-delete">Delete</button>
                    <span role="status" class="qq-upload-status-text-selector qq-upload-status-text"></span>
                </li>
            </ul>

            <dialog class="qq-alert-dialog-selector">
                <div class="qq-dialog-message-selector"></div>
                <div class="qq-dialog-buttons">
                    <button type="button" class="qq-cancel-button-selector">Close</button>
                </div>
            </dialog>

            <dialog class="qq-confirm-dialog-selector">
                <div class="qq-dialog-message-selector"></div>
                <div class="qq-dialog-buttons">
                    <button type="button" class="qq-cancel-button-selector">No</button>
                    <button type="button" class="qq-ok-button-selector">Yes</button>
                </div>
            </dialog>

            <dialog class="qq-prompt-dialog-selector">
                <div class="qq-dialog-message-selector"></div>
                <input type="text">
                <div class="qq-dialog-buttons">
                    <button type="button" class="qq-cancel-button-selector">Cancel</button>
                    <button type="button" class="qq-ok-button-selector">Ok</button>
                </div>
            </dialog>
        </div>
    </script>

    <style>
        #trigger-upload {
            color: white;
            background-color: #00ABC7;
            font-size: 14px;
            padding: 7px 20px;
            background-image: none;
        }

        #fine-uploader-manual-trigger .qq-upload-button {
            margin-right: 15px;
        }

        #fine-uploader-manual-trigger .buttons {
            width: 36%;
        }

        #fine-uploader-manual-trigger .qq-uploader .qq-total-progress-bar-container {
            width: 60%;
        }
    </style>
<script type="text/javascript">
function retName(nm)  { return eval("frm01." + nm); }
function retValue(nm) { return eval("frm01." + nm + ".value"); }
function retFocus(nm) { return eval("frm01." + nm + ".focus()"); }
var jsonObjParam = ${param._jsonpopupparam};
let uploadType;
let manualUploader;
let v_maxFileCnt = 0;
$(document).ready(function(){
	
	$("#"+_jsonpopupparam._modalid).draggable({
	  handle: ".modal-header",
	});
	$("#"+_jsonpopupparam._modalid).resizable();
	
	$("#FILE_GID").val(jsonObjParam.param.fileGid);
	$("#REF_TABLE_ID").val(jsonObjParam.param.refTableId);
	$("#REF_DOC_ID").val(jsonObjParam.param.refDocId);
	$("#REF_DOC_TYPE").val(jsonObjParam.param.refDocType);
	$("#BOARD_TYPE").val(jsonObjParam.param.boardType);
	if(APPz.ui.isNull(jsonObjParam.param.uploadType)){
		uploadType = ['gif', 'jpg', 'jpeg', 'bmp', 'png', 'zip', 'txt', 'ppt', 'pptx', 'xls', 'xlsx', 'xlsm', 'doc', 'docx', 'xml', 'csv', 'pdf', 'hwp'];	
	}else{
		uploadType = (jsonObjParam.param.uploadType).split('|');
	}
	LoadPage();
	manualUploader = fileUploadCreate();
});


/*Sheet 기본 설정 */
function LoadPage(){
	var cfg = {SearchMode:smLazyLoad,Page:30,MergeSheet:msNone, FrozenCol:0};
	FileSheet.SetConfig(cfg);
	var headers = [ {Text:"No|STS|DEL|FILEGID|SEQ|파일경로|파일명|FILE_NM|파일Size|파일유형|보기|FILE_TMNAIL_NM|REF_TABLE_ID|REF_DOC_ID|REF_DOC_TYPE|등록자|등록일시"} ];

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
			{Type:"Int",       MinWidth:80,  SaveName:"FILE_SIZE",   Edit: false},
			{Type:"Text",      MinWidth:60,  SaveName:"FILE_TYPE",   Edit: true, Align: "center"},
			{Type:"Image",     MinWidth:60,  SaveName:"FILE_VIEW",   Edit: true, Align: "center"},
			{Type:"Text",      MinWidth:300,  SaveName:"FILE_TMNAIL_NM", Edit: false, Hidden:true},
			{Type:"Text",      MinWidth:80,   SaveName:"REF_TABLE_ID",  Edit: false, Hidden:true},
			{Type:"Text",      MinWidth:80,   SaveName:"REF_DOC_ID",  Edit: false, Hidden:true},
			{Type:"Text",      MinWidth:80,   SaveName:"REF_DOC_TYPE",  Edit: false, Hidden:true},
			{Type:"Text",      MinWidth:80,   SaveName:"REG_NM", Edit: false, Align: "center"},
			{Type:"Date",      MinWidth:100,  SaveName:"REG_DT",      Edit: false, Align: "center"}
	];
	
	FileSheet.SetImageList(0,  "");//첨부파일
	FileSheet.SetImageList(1,  "/s01/design/images/ico_img.gif");//다운파일
	
	FileSheet.InitColumns(cols);

	FileSheet.SetCountPosition(4);
	doAction('SearchFileList');
	
}

var starTime = null; 

/*Sheet 각종 처리*/
function doAction(sAction)
{
  switch(sAction)
  {
	case "SearchFileList" :      //조회
		FileSheet.RemoveAll();
		FileSheet.DoSearch("<c:url value="/file/searchFileUploadInfo.do"/>", getFormJson("frm01"), {ReqHeader : {"Content-Type":"application/json"}});
		break;
	case "SaveFile" :      //조회
		
		jsonParam  = getFormJson("frm01");
		jsonParam.gridData = FileSheet.GetSaveJson(0).data;
		
		/*
		jsonParam = {
			param : getFormJson("frm01")
   		  , gridData : {
   				fileInfoJson : FileSheet.GetSaveJson(0).data
   			}};
		*/
		jSYS.co.ApiRequest("<c:url value="/file/saveFileUploadInfo.do"/>",jsonParam,true,function(result){//메뉴저장
			if(result.Code>0){
				$("#FILE_GID").val(result.FILE_GID);
				doAction('SearchFileList');
							//$('[data-btn-control=btn-search]').trigger("click");// 자동조회
			}else{
				duDialog("저장실패",result.Message,{callbacks: {okClick: function(e) {this.hide();	}}});
			}
		});
		break;
	case "Close": //적용
    	try{
	    	var jsonParam = getFormJson("frm01");
	    	jsonParam.data = FileSheet.GetSaveJson({AllSave:1});
	    	/*
	    	jSYS.ui.modalCallback("${param._callback}", jsonParam);//콜백호출
	    	if(APPz.ui.isNull(jsonObjParam._modalid)){
	    		self.close();
	    	}else{
		    	parent.$("#"+jsonObjParam._modalid).modal('hide');
	    	}
	    	*/
    	} catch (e) {
    		self.close();
    	}	  		
	    break;
  }
}

doUploadToSheetData = function(jsonData){
	
	var row = FileSheet.DataInsert(-1);
	jsonData.REF_TABLE_ID = jsonObjParam.param.refTableId;
	jsonData.REF_DOC_ID = jsonObjParam.param.refDocId;
	if(''!=jsonObjParam.param.refDocType){
		jsonData.REF_DOC_TYPE = jsonObjParam.param.refDocType;
	}
	jsonData.FILE_GID = jsonObjParam.param.fileGid;
	FileSheet.SetRowData(row, jsonData);
}


function FileSheet_OnSearchEnd(code,msg){
	if (msg!='') {
		alert(msg);
	}else{
		v_maxFileCnt = jsonObjParam.param.maxFileCnt-FileSheet.RowCount()-1;
		fn_setFileMax(v_maxFileCnt);
	}
}

function FileSheet_OnSaveEnd(code, msg) {
	if (msg!='') {
		alert(msg);
	} else {
		doAction("SearchFileList");
	}
}

/**S01_FILE_INFO
 * fileGid : FILE_GID
 * refTableId : REF_TABLE_ID (S01_CATALOGIMG, S01_STAMP, S01_BOARD, ETC 에 따라서 업로드 경로가 트림 
 * maxFileCnt : FILE LIMIT 
 * uploadType : STRING ex)  'gif|JPG|jpeg'
 * callBack : POPUp Close 시에 호출 method
 */
fn_imageVw = function(fileGid, seq){
		//var url = "../../file/imageView.do";
		
	var url = "${pageContext.request.contextPath}/file/imageView.do?fileGid="+fileGid+'&seq='+seq;
	jSYS.Popup.openDialog(url,{param : {FILE_GID : fileGid
		                              , SEQ : seq
		                              } 
		                      }, "800", "600", null);
};

function FileSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH, rowType) { 
	var colnm = FileSheet.ColSaveName(Col);
	if (colnm == "FILE_VIEW" && ''!=FileSheet.GetCellValue(Row, "FILE_VIEW")){
		var rowJson = FileSheet.GetRowJson(Row);
		fn_imageVw(rowJson.FILE_GID, rowJson.SEQ);
	}
}



var windowHeight = $(window).height()-340;
if(windowHeight < 200) windowHeight = 200;
</script>
</head>
<body>

    <!-- Fine Uploader DOM Element
    ====================================================================== -->
    <div id="fine-uploader-manual-trigger"></div>

    <!-- Your code to create an instance of Fine Uploader and bind to the DOM/template
    ====================================================================== -->
    <script>
 // 나중에 옵션 변경
 	function fn_setFileMax(fileCnt){
 		manualUploader.setItemLimit(fileCnt);
 	}
    	function fileUploadCreate(){
    		
    		v_maxFileCnt = jsonObjParam.param.maxFileCnt-FileSheet.RowCount();
    		console.log('v_maxFileCnt==='+v_maxFileCnt);
	        manualUploader = new qq.FineUploader({
	            element: document.getElementById('fine-uploader-manual-trigger'),
	            template: 'qq-template-manual-trigger'
	            ,
	            request: {
	                endpoint: "<c:url value="/file/uploadFileInfo.do"/>"
	            },
	            thumbnails: {
	                placeholders: {
	                    waitingPath: '${pageContext.request.contextPath}/js/fine-uploader/placeholders/waiting-generic.png',
	                    notAvailablePath: '${pageContext.request.contextPath}/js/fine-uploader/placeholders/not_available-generic.png'
	                }
	            },
	            callbacks: {
	            	onComplete: function(id, fileName, responseJSON) {	            		
	                	if (responseJSON.success) {
	                    	console.log('resJSON==='+responseJSON.FILEJSON);
	                    	doUploadToSheetData(responseJSON.FILEJSON);
	                      $('#thumbnail-fine-uploader').append('<img src="img/success.jpg" alt="' + fileName + '">');
	                    }else{
	                    	alert(responseJSON.MSG);
	                    }
	                  },                
	                onAllComplete: function(id, fileName, responseJSON) {
	                    //location.reload();
	                    //alert('UploadEnd');
	                	
	                	//manualUploader.clearStoredFiles();
	                    //manualUploader.reset();
	                    doAction('SaveFile');
	                }
	            },
	            validation: {
	                //allowedExtensions: ['gif', 'jpg', 'jpeg', 'bmp', 'png', 'zip', 'txt', 'ppt', 'pptx', 'xls', 'xlsx', 'xlsm', 'doc', 'docx', 'xml', 'csv', 'pdf', 'hwp']
	            	allowedExtensions : uploadType//['gif', 'jpg', 'jpeg']
	              , sizeLimit : 10*1024*1024 //2MB
	              , itemLimit : v_maxFileCnt
	             // , itemLimit: (jsonObjParam.param.maxFileCnt-FileSheet.RowCount())
	            }
	            , autoUpload: false
	            , debug: true
	        });

	        qq(document.getElementById("trigger-upload")).attach("click", function() {
	        	var uploads = manualUploader.getUploads();
	            manualUploader.uploadStoredFiles();
	        });
			return manualUploader;
    	}
    </script>
    
<form id="frm01" name="frm01" method="post">
<input type="hidden" id="sheetVal" name="sheetVal" value="" />
<input type="hidden" name="crud" value="">
<input type="hidden" id="FILE_GID" name="FILE_GID" value=""><input type="hidden" id="REF_TABLE_ID" name="REF_TABLE_ID" value="">

<input type="hidden" id="REF_DOC_ID" name="REF_DOC_ID" value="">
<input type="hidden" id="REF_DOC_TYPE" name="REF_DOC_TYPE" value="">
<input type="hidden" id="BOARD_TYPE" name="BOARD_TYPE" value="">
<input type="hidden" id="SysClss" name="SysClss" value="">
	<div id="section">
		<ul class="btns" style="">
			<li class="fr">
				<a href="javascript:doAction('SearchFileList');" class="btnl"><span>조회</span></a>
				<a href="javascript:doAction('SaveFile');" class="btnl"><span>저장</span></a>
				<a href="javascript:doAction('Close');" class="btnl"><span>닫기</span></a>
			</li>	
		</ul>	
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
</body>
</html>
