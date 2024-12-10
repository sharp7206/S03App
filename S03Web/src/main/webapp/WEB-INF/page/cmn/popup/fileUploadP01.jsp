<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%--------------------------------------------------------------------
 * 프로젝트	: APP 
 * 프로그램명	: fileUploadP01
 * 설명		: 파일업로드
 * 작성자		: 이호성
 * 작성일자	: 2024/02/02
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
    <link href="${pageContext.request.contextPath}/plug/fine-uploader/fine-uploader-new.css" rel="stylesheet">

    <!-- Fine Uploader jQuery JS file
    ====================================================================== -->
    <script src="${pageContext.request.contextPath}/plug/fine-uploader/jquery.fine-uploader.js"></script>

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
let jsonObjParam = JSON.parse(decodeURI("${param._jsonparam}"));
let uploadType;
let manualUploader;
let v_maxFileCnt = 0;
$(()=>{
	let cmmonCdList;
	initPage();
});
function initPage(){
	
	$("#FILE_GRP_ID").val(jsonObjParam.param.fileGrpId);
	$("#REF_TABLE_ID").val(jsonObjParam.param.refTableId);
	$("#REF_DOC_ID").val(jsonObjParam.param.refDocId);
  if(''==(jsonObjParam.param.uploadType)){
		uploadType = ['gif', 'jpg', 'jpeg', 'bmp', 'png', 'zip', 'txt', 'ppt', 'pptx', 'xls', 'xlsx', 'xlsm', 'doc', 'docx', 'xml', 'csv', 'pdf', 'hwp'];	
	}else{
		uploadType = (jsonObjParam.param.uploadType).split('|');
	}
  
	manualUploader = fileUploadCreate();
	LoadPage();
};


/*Sheet 기본 설정 */
function LoadPage(){
	var cfg = {SearchMode:smLazyLoad,Page:30,MergeSheet:msNone, FrozenCol:4};
	FileSheet.SetConfig(cfg);
	var headers = [ {Text:"No|STS|DEL|FILEGRPID|FILESEQ|파일보기|파일경로|파일명|FILE_NM|파일Size|파일유형제품명|REF_DOC_ID|등록자|등록일시"} ];

	var info = {Sort:1,ColMove:1,ColResize:1,HeaderCheck:0};
	FileSheet.InitHeaders(headers,info);
	var cols = [
			{Type:"Seq", MInWidth:60,Align:"center"},
			{Type:"Status",    MinWidth:60,SaveName:"SSTATUS", Hidden:0},
	    	{Type:"DelCheck",  MinWidth:60},
			{Type:"Text",      MinWidth:160,  SaveName:"FILE_GRP_ID",         Edit: true, Hidden:true},
			{Type:"Text",      MinWidth:160,  SaveName:"FILE_SEQ",         Edit: true, Hidden:true},
			{Type:"Image",     MinWidth:60,   SaveName:"FILE_VIEW",   Edit: true, Align: "center"},
			{Type:"Text",      MinWidth:200,  SaveName:"FILE_PATH",   Edit: true, Hidden:false, KeyField:1},
			{Type:"Text",      MinWidth:300,  SaveName:"FILE_ORG_NM", Edit: false},
			{Type:"Text",      MinWidth:80,   SaveName:"FILE_NM",     Edit: false, Hidden:true, KeyField:1},
			{Type:"Text",      MinWidth:150,  SaveName:"FILE_SZ",   Edit: false},
			{Type:"Text",      MinWidth:120,  SaveName:"FILE_TYPE",   Edit: true, Align: "center"},
			{Type:"Text",      MinWidth:80,   SaveName:"REF_DOC_ID",  Edit: false, Hidden:true},
			{Type:"Text",      MinWidth:80,   SaveName:"FST_RGPR_ID", Edit: false},
			{Type:"Text",      MinWidth:100,  SaveName:"FST_REG_ONCE",      Edit: false}
	];
	FileSheet.SetImageList(0,  "");//첨부파일
	FileSheet.SetImageList(1,  "${pageContext.request.contextPath}/res/design/images/ico_img.gif");//다운파일	
	FileSheet.InitColumns(cols);

	FileSheet.SetCountPosition(4);
	
	if(!APPz.ui.isNull(jsonObjParam.param.fileJsonStr)){
		
		FileSheet.LoadSearchData({"data":JSON.parse(jsonObjParam.param.fileJsonStr)}, {
		        Sync: 1
		    });	
	}else{
		doAction('SearchFileList');
	}
}

var starTime = null; 

/*Sheet 각종 처리*/
function doAction(sAction)
{
  switch(sAction)
  {
	case "SearchFileList" :      //조회
	
		FileSheet.RemoveAll();
		FileSheet.SetTreeCheckActionMode(1);
		FileSheet.DoSearch("${pageContext.request.contextPath}/api/cmn/file/searchFileUploadInfo.do", APPz.ui.convertFormToJson("frm01"), {ReqHeader : {"Content-Type":"application/json"}});
		break;
	case "SaveFile" :      //조회
		
		if($("#FileSheet").val != "KeyFieldError"){ //저장처리 할때 시트에서 걸러지면 무조건  KeyFieldError를 반환한다.
			duDialog(null, '저장 하시겠습니까 <i class="fas fa-question"></i>', {
				buttons: duDialog.OK_CANCEL,
					callbacks: {
						okClick: function(e) {
							this.hide()
							jsonParam  = APPz.ui.convertFormToJson("frm01");
							jsonParam.gridData = FileSheet.GetSaveJson(0).data;
							/*
							jsonParam = {
								param : APPz.ui.convertFormToJson("frm01")
				       		  , gridData : {
				       				fileInfoJson : FileSheet.GetSaveJson(0).data
				       			}};
							*/
		   			APPz.cmn.ApiRequest("${pageContext.request.contextPath}/api/cmn/file/saveFileUploadInfo.do",jsonParam,true,function(result){//메뉴저장
		   				
		   				if(result.rtnCode>=0){
		   					$("#FILE_GRP_ID").val(result.fileGrpId);
		   					duDialog(null,"저장되었습니다.",{
			   					callbacks: {
			   						okClick: function(e) {
			   							this.hide();
			   							doAction('SearchFileList');
			   							//$('[data-btn-control=btn-search]').trigger("click");// 자동조회
			   						}
			   					}
			   				});
		   				}else{
		   					duDialog("저장실패",result.Message,{callbacks: {okClick: function(e) {this.hide();	}}});
		   				}
		   			});
		
						}
					}
				});	
		}
		break;
	case "Close": //적용
	
    	var jsonParam = APPz.ui.convertFormToJson("frm01");
    	jsonParam.fileList = FileSheet.GetSaveJson({AllSave:1}).data;
    	APPz.ui.modalCallback(JSON.stringify(jsonParam));//콜백호출
    	top.$("#"+jsonObjParam._modalid).modal('hide');
	    break;				
  }
}

doUploadToSheetData = function(jsonData){
	var row = FileSheet.DataInsert(-1);
	FileSheet.SetRowData(row, jsonData);	
}


function FileSheet_OnSearchEnd(code,msg){
	
	if (code<0) {
		alert(msg);
	}else{
		
		v_maxFileCnt = jsonObjParam.param.maxFileCnt-FileSheet.RowCount();
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
 * fileGid : FILE_GRP_ID
 * refTableId : REF_TABLE_ID (S01_CATALOGIMG, S01_STAMP, S01_BOARD, ETC 에 따라서 업로드 경로가 트림 
 * maxFileCnt : FILE LIMIT 
 * uploadType : STRING ex)  'gif|JPG|jpeg'
 * callBack : POPUp Close 시에 호출 method
 */
fn_imageVw = function(rowJson){
	 
	/*
	var jsonParam = {param : {FILE_PATH : rowJson.FILE_PATH
                                    , FILE_NM : rowJson.FILE_NM
                             } 
                    };
	*/
	var url = "${pageContext.request.contextPath}/api/cmn/file/imageView.do?filePath="
			+(rowJson.FILE_PATH).replaceAll('/', '@')+"&fileNm="+rowJson.FILE_NM;
/* 	APPz.Popup.openDialog(url,{param : {FILE_GRP_ID : fileGrpId
		                              , SEQ : seq
		                              } 
		                      }, "800", "600", null); */
	APPz.ui.openModal(url,"첨부파일",'',800,500,false, null);
};

function FileSheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH, rowType) {
	
	var colnm = FileSheet.ColSaveName(Col);
	if (colnm == "FILE_VIEW"){
		var rowJson = FileSheet.GetRowJson(Row);
		fn_imageVw(rowJson);
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
    	function fn_setFileMax(fileCnt){
     		manualUploader.setItemLimit(fileCnt);
     	}    
    	function fileUploadCreate(){
	        var manualUploader = new qq.FineUploader({
	            element: document.getElementById('fine-uploader-manual-trigger'),
	            template: 'qq-template-manual-trigger',
	            request: {
	                endpoint: "${pageContext.request.contextPath}/api/cmn/file/uploadFileInfo.do"
	            },
	            thumbnails: {
	                placeholders: {
	                    waitingPath: '${pageContext.request.contextPath}/plug/fine-uploader/placeholders/waiting-generic.png',
	                    notAvailablePath: '${pageContext.request.contextPath}/plug/fine-uploader/placeholders/not_available-generic.png'
	                }
	            },
	            callbacks: {
	            	onComplete: function(id, fileName, responseJSON) {
	            		
	                	if (responseJSON.rtnCode>=0) {
	                    	console.log('resJSON==='+responseJSON.FILEJSON);
	                    	doUploadToSheetData(responseJSON.FILEJSON);
	                      $('#thumbnail-fine-uploader').append('<img src="img/success.jpg" alt="' + fileName + '">');
	                    }
	                  },                
	                onAllComplete: function(id, fileName, responseJSON) {
	                    //location.reload();
	                    //alert('UploadEnd');
	                	manualUploader.reset();
	                }
	            },
	            validation: {
	                allowedExtensions: ['gif', 'jpg', 'jpeg', 'bmp', 'png', 'zip', 'txt', 'ppt', 'pptx', 'xls', 'xlsx', 'xlsm', 'doc', 'docx', 'xml', 'csv', 'pdf', 'hwp']
	              // allowedExtensions: ['gif', 'jpg', 'jpeg', 'png']
	              , sizeLimit: 10*1024*1024 //2MB
	              , itemLimit: 5
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
<input type="hidden" id="FILE_GRP_ID" name="FILE_GRP_ID" value="">
<input type="hidden" id="REF_TABLE_ID" name="REF_TABLE_ID" value="">
<input type="hidden" id="REF_DOC_ID" name="REF_DOC_ID" value="">
<input type="hidden" id="SysClss" name="SysClss" value="">
	<div id="section">
		<ul class="btns" style="">
			<li class="fr">
				<a href="javascript:doAction('SearchFileList');" class="btn blue regular">조회</a>
				<a href="javascript:doAction('Close');" class="btn blue regular">닫기</a>
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
