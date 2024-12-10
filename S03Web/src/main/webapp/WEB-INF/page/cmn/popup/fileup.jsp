<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="appz" uri="/WEB-INF/tld/systld.tld"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>파일업로드</title>
<c:import url="/page/cmn/include/page_header.do"/>
<script type="text/javascript">
let fileCount = 0;//파일 현재 필드 숫자 totalCount랑 비교값
let fileNum = 0;//파일 고유넘버
let content_files = new Array();//첨부파일 배열
let _fileSize = 0;
let _jsonpopupparam = JSON.parse(decodeURI("${param._jsonpopupparam}"));
let totalCount = _jsonpopupparam.maxcnt;//해당 숫자를 수정하여 전체 업로드 갯수를 정한다.
$(()=>{

	$('#input_file').attr("accept",_jsonpopupparam.accept);

	$('#btn-upload').click(function (e) {//첨부파일로직
    	e.preventDefault();
        $('#input_file').click();
    });

    $("#input_file").on("change",function(e){
    	let files = e.target.files;
    	let filesArr = Array.prototype.slice.call(files);// 파일 배열 담기
    	if (fileCount + filesArr.length > totalCount) {// 파일 개수 확인 및 제한
    		duDialog(null,APPz.getMessage("E","file.MaxCount",totalCount.toString()));
    		return;
    	} else {
    	    fileCount = fileCount + filesArr.length;
    	}
    	let _isNotValid = false;
    	for(let i=0;i<filesArr.length;i++){
    		let _filename = filesArr[i].name;
    		let _extension = _filename.substr( (_filename.lastIndexOf('.') +1) ).toLowerCase();
    		if(_jsonpopupparam.accept.indexOf(_extension) == -1 ){
    			duDialog(null,APPz.getMessage("E","file.NotAllowed",_extension));
    			_isNotValid = true;
    			filesArr[i].is_delete = true;
    			fileCount --;
    			break;
    		}
    	}
    	if(_isNotValid) return;
    	filesArr.forEach(function (f) {// 각각의 파일 배열담기 및 기타
    		let _icon = "far fa-file";
    	    switch(f.type){
	   		case "image/bmp":
	   		case "image/png":
	   		case "image/gif":
	   		case "image/jpeg":
	   			_icon="far fa-file-image";
	   			break;
	   		case "application/vnd.ms-excel":
	   		case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
	   			_icon="far fa-file-excel";
	   			break;
	   		case "application/vnd.ms-powerpoint":
	   			_icon="far fa-file-ppt";
	   			break;
	   		case "application/pdf":
	   			_icon="far fa-file-pdf";
	   			break;
	   		case "application/x-zip-compressed":
	   			_icon="far fa-file-archive";
	   			break;
	   		}
    	    let reader = new FileReader();
    	    reader.onload = function (e) {
    	    	content_files.push(f);
    	    	let _file = '';
    	    	_file += '<div id="file' + fileNum + '" class="d-flex justify-content-between p-1">';
    	    	_file += '<div class="w-75 jsmall text-primary"> <i class="'+_icon+'"></i> ' + f.name + '</div>';
    	    	_file += '<div class="w-25 jsmall text-end" onclick="fileDelete(\'file' + fileNum + '\')">' + _getFileSize(f.size) +' <i class="fas fa-trash"></i> </div>';
    	    	_file += '</div>';
    	        $('#articlefileChange').append(_file);
    	        fileNum ++;
    	    };
    	    _fileSize += f.size;
    	    reader.readAsDataURL(f);
		});
    	//초기화 한다.
    	$("#input_file").val("");
    	setTotal();
    });// input file 파일 첨부시 fileCheck 함수 실행
});

function setTotal(){
	$("#file_cnt").html("총 "+fileCount+" 건");
	if(fileCount == 0){
		$("#file_sum").html("총 0 bytes");
	} else {
		$("#file_sum").html("총 "+_getFileSize(_fileSize));
	}
}

function _getFileSize(x) {
	let s = ['bytes', 'KB', 'MB', 'GB', 'TB', 'PB'];
  	let e = Math.floor(Math.log(x) / Math.log(1024));
  	return (x / Math.pow(1024, e)).toFixed(1) + " " + s[e];
}

// 파일 부분 삭제 함수
function fileDelete(fileNum){
    let no = fileNum.replace(/[^0-9]/g, "");
    _fileSize -= content_files[no].size;
    content_files[no].is_delete = true;
	$('#' + fileNum).remove();
	fileCount --;
    setTotal();
}

/*
 * 폼 submit 로직
 */
function registerAction(){
	let form = $("form")[0];
	let formData = new FormData(form);
 	let fileSizeSum = 0;
	for (let x = 0; x < content_files.length; x++) {
		if(!content_files[x].is_delete){// 삭제 안한것만 담아 준다.
			formData.append("files", content_files[x]);
			fileSizeSum += content_files[x].size;
		}
	}
	if(fileCount == 0){
		duDialog(null,APPz.getMessage("E","data.Select.Required","첨부할 파일을"));
		return false;
	}
	if(fileSizeSum > 20000000){
		duDialog(null,APPz.getMessage("E","file.FileSizeCheck","20|M"));
		return false;
	}
	formData.append("path",_jsonpopupparam.path);
	formData.append("fileno",_jsonpopupparam.fileno);
	let requestUrl = "${pageContext.request.contextPath}/api/co/file/upload.do";
	$.ajax({
  		type: "POST",
  	   	enctype: "multipart/form-data",
  	    url: requestUrl,
      	data : formData,
      	processData: false,
  	    contentType: false,
  	  	beforeSend : function(xhr) {
 		   	// 통신을 시작할때 처리
 		   	 xhr.setRequestHeader(_csrf_header,_csrf_token);// csrf 관련설정.
 			 APPz.cmn._topLoading(true);
		},
  	    success: function (data) {
  	    	APPz.ui.modalCallback("${param._callback}",data.fileno);//콜백호출
  			top.$("#"+_jsonpopupparam._modalid).modal('hide');
  	    },
  	  	error: function (xhr,status,error) {
   	   		APPz.cmn._ajaxError(xhr,status,error,requestUrl);
   	   		return false;
       	},
	   	complete : function() {
			// 통신이 완료된 후 처리
			APPz.cmn._topLoading(false);
		}
	});
  	return false;
}

</script>
</head>
<body>
<div class="container-fluid">
	<form name="dataForm" id="dataForm" onsubmit="return registerAction();">
	  	<div class="m-1 text-end">
	  		<input id="input_file" multiple="multiple" type="file" style="display:none;">
		  	<div class="btn-group" role="group">
		  		<button id="btn-upload" type="button" class="btn btn-secondary btn-xs"><i class='fas fa-plus-circle'></i> 파일추가</button>
		  		<button type="submit" class="btn btn-secondary btn-xs"> <i class='far fa-arrow-alt-circle-up'> </i> 전송</button>
		  	</div>
	  	</div>
	  	<div id="data_file_txt p-3">
			<div id="articlefileChange" class="border rounded p-2 border-primary border-3 rounded-4"></div>
		</div>
		<div class="d-flex justify-content-between p-1">
			<div class="text-danger jsmall w-50" id="file_cnt">총 0 건</div>
			<div class="text-danger jsmall text-end w-50" id="file_sum">총 0 bytes</div>
		</div>
  	</form>
</div>
</body>
</html>