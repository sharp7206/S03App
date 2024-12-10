// csrf 관련설정.
var _csrf_token = $("meta[name='_csrf']").attr("content");// csrf 관련설정.
var _csrf_header = $("meta[name='_csrf_header']").attr("content");// csrf 관련설정.
var _csrf_parameter = $("meta[name='_csrf_parameter']").attr("content");// csrf 관련설정.
var _isInIframe = (window.location != window.parent.location) ? true : false;// 현재화면이 iframe 내인지체크.
let _SMCP_CALLBACK ="_SMCP_MODAL_CALLBACK_PARAM_";
APPz = {
	_getUniqueId:()=>{// 유일값생성
        return ("_" + new Date().getTime() + Math.random()).replace(/\./gi, "_");
    },
    redirectPage:(_winname, _popupurl, _jsonparam)=>{// 팝업 파라메타 전송
		var _form = document.getElementById("_APPzHiddenForm_");
		if(_form !== null){
            _form.remove();
        }
        $("body").append("<form id='_APPzHiddenForm_'><input type='hidden' name='_jsonparam'><input type='hidden' name='"+_csrf_parameter+"' value='"+_csrf_token+"' /></form>");
        _form = document.getElementById("_APPzHiddenForm_");
        _form._jsonparam.value = encodeURI(_jsonparam);
        _form.action = _popupurl;
        _form.method = "post";
        _form.target = _winname;
        _form.submit();
        _form.remove();
    },
    goPage:(_gourl, _jsonparam)=>{//페이지 이동 처리 post
		
		if(_jsonparam== undefined) _jsonparam = {};
		_jsonparam = JSON.stringify(_jsonparam);
		var _form = document.getElementById("_APPzHiddenForm_");
		if(_form !== null){
            _form.remove();
        }
        $("body").append("<form id='_APPzHiddenForm_'><input type='hidden' name='_jsonparam'><input type='hidden' name='"+_csrf_parameter+"' value='"+_csrf_token+"' /></form>");
        _form = document.getElementById("_APPzHiddenForm_");
        _form._jsonparam.value = encodeURI(_jsonparam);
        _form.action = _gourl;
        _form.method = "post";
        _form.submit();
        _form.remove();
    },
    context:()=>{
		return sessionStorage.getItem("contextpath");
	},
	isvalid:(_param)=>{
		return _param != undefined && _param != null && _param !=="" && _param !== 'null';
	},
	sysyear:()=>{// 현재년도
		return APPz.cmn.ApiRequest(APPz.context()+"/api/cmn/date/sysyear.do",null,false).data;
	},
	sysmonth:(_gubun)=>{// 현재월
		if(_gubun==undefined || _gubun =="") _gubun = '-';
		return APPz.cmn.ApiRequest(APPz.context()+"/api/cmn/date/sysmonth.do",{param:{dateformat:'yyyy'+_gubun+'MM'}},false).data;
	},
	sysdate:(_gubun)=>{// 현재일자
		if(_gubun==undefined || _gubun =="") _gubun = '-';
		return APPz.cmn.ApiRequest(APPz.context()+"/api/cmn/date/sysdate.do",{param:{dateformat:'yyyy'+_gubun+'MM'+_gubun+'dd'}},false).data;
	},
	lastday:(_date,_gubun)=>{//특정일자의 마작말날짜
		if(_gubun==undefined || _gubun =="") _gubun = '-';
		return APPz.cmn.ApiRequest(APPz.cmnntext()+"/api/cmn/date/lastday.do",{param:{date:_date,dateformat:'yyyy'+_gubun+'MM'+_gubun+'dd'}},false).data;
	},
	adddays:(_date,_adddays,_gubun)=>{//특정일자 날짜 더하기
		if(_gubun==undefined || _gubun =="") _gubun = '-';
		return APPz.cmn.ApiRequest(APPz.context()+"/api/cmn/date/adddays.do",{param:{date:_date,adddays:_adddays,dateformat:'yyyy'+_gubun+'MM'+_gubun+'dd'}},false).data;
	},
	addmonths:(_date,_addmonths,_gubun)=>{//특정일자 월 더하기
		if(_gubun==undefined || _gubun =="") _gubun = '-';
		return APPz.cmn.ApiRequest(APPz.context()+"/api/cmn/date/addmonths.do",{param:{date:_date,addmonths:_addmonths,dateformat:'yyyy'+_gubun+'MM'+_gubun+'dd'}},false).data;
	},
	systimestamp:(_gubun)=>{//현재 일시
		if(_gubun==undefined || _gubun =="") _gubun = '-';
		return APPz.cmn.ApiRequest(APPz.context()+"/api/cmn/date/systimestamp.do",{param:{dateformat:'yyyy'+_gubun+'MM'+_gubun+'dd HH:mm:ss'}},false).IMG64;
	},
	userphoto:(_userid)=>{// 사용자사진
		return APPz.cmn.ApiRequest(APPz.context()+"/api/cmn/common/getUserPhoto.do",{param:{context:APPz.context(),userid:_userid}},false).photo;
	},
	getFileImage:(_fileGid, _seq)=>{// 사용자사진
		return APPz.cmn.ApiRequest(APPz.context()+"/api/file/imageViewBase64.do",{param:{SYS_CD : 's03',FILE_GRP_ID:_fileGid, FILE_SEQ:_seq}},false).IMG64;
	},
    getCodeList:(_grpcd)=>{//그룹코드로 공통코드조회
		return APPz.cmn.ApiRequest(APPz.context()+"/api/cmn/common/getCodeList.do",{param:{grpcd:_grpcd,useyn:'Y'}},false).list;
	},
    getCodeByRef:(_jsonParam)=>{//그룹코드,참조값으로 공통코드조회
		return APPz.cmn.ApiRequest(APPz.context()+"/api/cmn/common/getCodeList.do",{param:_jsonParam},false).list;
	},
    filedown:(_fileno,_fileseq)=>{//그룹코드,참조값으로 공통코드조회
    	var requestUrl = APPz.context()+"/api/cmn/file/download.do";
    	$.ajax({
    		url:requestUrl,
    		beforeSend :(xhr)=>{
 		   		// 통신을 시작할때 처리
 		   		if(_csrf_header && _csrf_token){
 		   			xhr.setRequestHeader(_csrf_header,_csrf_token);// csrf 관련설정.
				}
 		   		APPz.cmn._topLoading(true);
			},
			contentType: "application/x-www-form-urlencoded;charset=UTF-8",
    		xhr: function() {
	            var xhr = new XMLHttpRequest();
	            xhr.responseType = "blob"; // 바이너리 데이터로 설정
	            return xhr;
	        },
    		data:{fileno:_fileno,fileseq:_fileseq},
    		type: "POST",
    		success:(data, message, xhr)=>{
    			if (xhr.readyState == 4 && xhr.status == 200) {
    				// 성공했을때만 파일 다운로드 처리하고
    				let disposition = xhr.getResponseHeader('Content-Disposition');
    				let filename;
    				if (disposition && disposition.indexOf('attachment') !== -1) {
    					let filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
    					let matches = filenameRegex.exec(disposition);
    					if (matches != null && matches[1]) filename = matches[1].replace(/['"]/g, '');
    				}
    				let blob = new Blob([data]);
    				let link = document.createElement('a');
    				link.href = window.URL.createObjectURL(blob);
    				link.download = decodeURIComponent(filename);
    				link.click();
    			}
    		},
    		error:(xhr,status,error)=>{
       	   		APPz.cmn._ajaxError(xhr,status,error,requestUrl);
       	   		return false;
           	},
    	   	complete:()=>{
    			APPz.cmn._topLoading(false);// 통신이 완료된 후 처리
    		}
    	});
	},
    filedownN:(_filePath,_fileNm)=>{//그룹코드,참조값으로 공통코드조회
    	var requestUrl = APPz.context()+"/api/cmn/file/downloadN.do";
    	$.ajax({
    		url:requestUrl,
    		beforeSend :(xhr)=>{
 		   		// 통신을 시작할때 처리
 		   		if(_csrf_header && _csrf_token){
 		   			xhr.setRequestHeader(_csrf_header,_csrf_token);// csrf 관련설정.
				}
 		   		APPz.cmn._topLoading(true);
			},
			contentType: "application/x-www-form-urlencoded;charset=UTF-8",
    		xhr:()=>{
				let xhr = new XMLHttpRequest();
				xhr.onreadystatechange = ()=>{
                    //response 데이터를 바이너리로 처리한다. 세팅하지 않으면 default가 text
					xhr.responseType = "blob";
				};
				return xhr;
    		},
    		data:{filePath:_filePath,fileNm:_fileNm},
    		type: "POST",
    		success:(data, message, xhr)=>{
    			if (xhr.readyState == 4 && xhr.status == 200) {
    				// 성공했을때만 파일 다운로드 처리하고
    				let disposition = xhr.getResponseHeader('Content-Disposition');
    				let filename;
    				if (disposition && disposition.indexOf('attachment') !== -1) {
    					let filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
    					let matches = filenameRegex.exec(disposition);
    					if (matches != null && matches[1]) filename = matches[1].replace(/['"]/g, '');
    				}
    				let blob = new Blob([data]);
    				let link = document.createElement('a');
    				link.href = window.URL.createObjectURL(blob);
    				link.download = decodeURIComponent(filename);
    				document.body.appendChild(link);
	                link.click();
	                document.body.removeChild(link);
    			}
    		},
    		error:(xhr,status,error)=>{
       	   		APPz.cmn._ajaxError(xhr,status,error,requestUrl);
       	   		return false;
           	},
    	   	complete:()=>{
    			APPz.cmn._topLoading(false);// 통신이 완료된 후 처리
    		}
    	});
	},
	getMessage:(_gubun,_key,_param)=>{//리소스 메세지
	
        let _icon = "fa-info-circle";// info
        switch(_gubun){
        case "I":// info message
        	break;
        case "C":// confirm message
        	_icon = "fa-question-circle";
        	break;
        case "E":// error message
        	_icon = "fa-exclamation-triangle";
        	break;
        }
        let _param_ = {param:{key:_key,param:_param}};
        let msg;
        try{
	        msg = APPz.cmn.ApiRequest(APPz.context()+"/api/cmn/common/message.do",_param_).message;
		}catch(err){
					
		}
        if(_gubun=="V"){//validate용
			return " <i class='fas "+_icon+"'></i> "+msg+' ';
        } else if(_gubun=="W"){//validate용
			return " <span class='pt-2 fs-6'><i class='fas "+_icon+"'></i> "+msg+'</span>';
		} else {
			return "<i class='fas "+_icon+" jmsg'></i>"+msg;
		}
    }
};
APPz.ui={
    setCombo:(_element,_jsonData,_value,_text,_prepend,_default)=>{//콤보값세팅
		_prepend = (_jsonData.length == 0) ? "E": _prepend;
    	_element.empty();
    	if(typeof _prepend == "string"){
    		switch(_prepend){
    		case "A":
    			if(_jsonData.length !== 1) _element.append("<option value=''>전체</option>");
    			break;
    		case "S":
    			_element.append("<option value=''>선택</option>");
    			break;
    		case "E":
    			_element.append("<option value=''> </option>");
    			break;
    		}
    	}
      	$.each(_jsonData,(key,data)=>{
      		_element.append("<option value='"+data[_value]+"'>"+data[_text]+"</option>");
      	});

      	if(typeof _default=="string"){
      		_element.val(_default);
      	}
    },
    setComboByKey:(_element, _grpCd, _jsonData, _value, _text, _prepend, _default)=>{//콤보값세팅
    	_jsonData = _jsonData.filter(item => item.classCd == _grpCd);
		_prepend = (_jsonData.length == 0) ? "E": _prepend;
    	_element.empty();
    	if(typeof _prepend == "string"){
    		switch(_prepend){
    		case "A":
    			if(_jsonData.length !== 1) _element.append("<option value=''>전체</option>");
    			break;
    		case "S":
    			_element.append("<option value=''>선택</option>");
    			break;
    		case "E":
    			_element.append("<option value=''> </option>");
    			break;
    		}
    	}
      	$.each(_jsonData,(key,data)=>{
      		_element.append("<option value='"+data[_value]+"'>"+data[_text]+"</option>");
      	});

      	if(typeof _default=="string"){
      		_element.val(_default);
      	}
    },
    openModal:(_modalurl,_modal_title,_jsonparam, _width, _height,_hide_close,_callback)=>{//부트스트랩방식 모달팝업
    	const jsonStr = JSON.stringify(_jsonparam);
		if (jsonStr != "" && jsonStr.toLowerCase().includes("<script")) {//이호성 2023/07/31 보완수정
		    return;
		}
		if(self==top){
			window.top.APPz.ui._modalTabId = null;
		} else {
			window.top.APPz.ui._modalTabId = top.$(".tab-pane.fade.active.show").attr("id");
		}
		//console.log('window.top.APPz.ui._modalTabId==>',window.top.APPz.ui._modalTabId);
		var _modalid = "_modal_" + APPz._getUniqueId();
		var _iframename = "_iframe_" + APPz._getUniqueId();
		var _modaltag = '<div class="modal fade" id="'+_modalid+'" data-bs-keyboard="false" data-bs-backdrop="static">';
		_modaltag +='<div class="modal-dialog" style="max-width: 100%; width: auto; display: table;-webkit-transform: translate(0,-50%);-o-transform: translate(0,-50%);transform: translate(0,-50%);top: 50%;margin: 0 auto;">';
		_modaltag +='<div class="modal-content">';
		_modaltag +='<div class="modal-header p-1">';
		_modaltag +='<span class="modal-title pl-2 text-info fs-6 fw-bold mt-1">';
		if(_modal_title.startsWith("<")==false){
			_modaltag +='<i class="fas fa-chalkboard"></i> ';
		}
		_modaltag +=_modal_title;
		_modaltag +='</span>';
		if(_hide_close!= undefined && _hide_close == false){
			_modaltag +='<button type="button" class="btn-close m-n1" data-bs-dismiss="modal" aria-label="Close"></button>';
		}
		_modaltag +='</div>';
		_modaltag +='<div class="modal-body p-1">';
		_modaltag +='<iframe src="" name="'+_iframename+'" width="'+_width+'" height="'+_height+'" style="border:none"></iframe>';
		_modaltag +='</div>';
		//_modaltag +='<div class="modal-footer">';
		//_modaltag +='<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>';
		//_modaltag +='</div>';
		_modaltag +='</div>';
		_modaltag +='</div>';
		top.$("body").append(_modaltag);
		_jsonparam._modalid = _modalid;//모달id 세팅
		APPz.redirectPage(_iframename, _modalurl, JSON.stringify(_jsonparam));
		top.$("#"+_modalid).modal("show");
		//top.$("#"+_modalid).resizable({"minWidth":_width,"minHeight":_height,"handles":'n, e, s, w, ne, sw, se, nw'});//resizable 처리(안됨...ㅠㅠ.)
		top.$("#"+_modalid).draggable({"handle":".modal-header"});// dragable 처리...
		var _interval = setInterval(()=>{
            if (top.$("#"+_modalid).is(':visible')==false) {
                top.$("#"+_modalid).remove();
            } else {
				top.clearInterval(_interval);
			}
        }, 300);
        top.$("#"+_modalid).on('hide.bs.modal', function(){
		    if( APPz.isvalid(_callback)){
		    	if( APPz.isvalid( sessionStorage.getItem(_SMCP_CALLBACK) ) ){
					eval(_callback)(sessionStorage.getItem(_SMCP_CALLBACK));
					sessionStorage.setItem(_SMCP_CALLBACK,null)
				}
			}
		});
	 },
	 modalCallback:(_callbackdata)=>{//부트스트랩 모달 콜백
	 	sessionStorage.setItem(_SMCP_CALLBACK,_callbackdata)
	 },
	 checkFileSize:(_fileId,_maxSize,_sizeType)=>{//첨부파일 크기체크
		if(_sizeType == undefined || _sizeType == null) _sizeType = "M";
		let maxSize = 1 * 1024 * 1024; // 1MB
		switch(_sizeType){
		case "M" :
			maxSize = _maxSize*1024*1024;//MB
		case "K" :
			maxSize = _maxSize*1024;//KB
		}
	    if($("#"+_fileId).val()!=="" && $("#"+_fileId).val() != null){
		    let fileSize = $("#"+_fileId)[0].files[0].size;
		    if(fileSize > maxSize){
		    	duDialog(null,APPz.getMessage("E","file.FileSizeCheck",_maxSize+"|"+_sizeType));
		    	$("#"+_fileId).val("");
		    	return false;
		    }
	    }
	    return true;
	 },
	  convertFormToJson:(_formNm)=>{
	    var form = document.getElementById(_formNm);
	    var formData = new FormData(form);
	    var jsonObject = {};
	
	    formData.forEach(function (value, key) {
	        // Check if the key already exists in the JSON object
	        if (jsonObject[key]) {
	            // If the key already exists, make it an array
	            if (!Array.isArray(jsonObject[key])) {
	                jsonObject[key] = [jsonObject[key]];
	            }
	            jsonObject[key].push(value);
	        } else {
	            // If the key does not exist, set the value
	            jsonObject[key] = value;
	        }
	    });
	
	    //var jsonString = JSON.stringify(jsonObject, null, 2);
	    //console.log(jsonString);
		return jsonObject;
	},isNull:(str)=>{
	 	if (str+"" == "undefined" || str+"" == "NaN" || str+"" == "null" || str+"" == "")
	 		return true;
		return false;
	}, generateCode : (list, filterKey, valname)=>{
		var retVal = '|';
		listP = list.filter(list => list.classCd===filterKey);
		$.each(listP,function(key,data){
			retVal = retVal + data[valname]+"|";
		});
		return retVal.substring(0, retVal.length-1);
	}, filterJson : (list, filterKey, filterVal)=>{
		// 유효성 검사
	    if (!list || !Array.isArray(list) || !filterKey) {
	        console.error("Invalid input data");
	        return [];
	    }
	
	    // 필터링 로직
	    return list.filter(item => {
	        // item이 객체인지 확인하고 filterKey와 filterVal 일치 여부 확인
	        return item && item[filterKey] === filterVal;
	    });
    }, setComma : (str) =>{
		//debugger;		
		if(APPz.ui.isNull(str)){
			str = '0';
		}else{
			str = ''+str;
		}
		
	    var numberWithoutComma = parseFloat(str.replace(/,/g, '')); // 쉼표 제거 후 숫자로 변환
	    var numberWithCommas = numberWithoutComma.toLocaleString(); // 천 단위마다 쉼표 추가
	    return numberWithCommas;
    }, setCommaObj : (obj) =>{
	    var str = obj.val(); // 입력 필드의 값
		if(APPz.ui.isNull(str)){
			str = '0';
		}
		if (!/^\d*\.?\d*$/.test(str)) { // 입력 값이 숫자가 아닌 경우
        	// 숫자가 아닌 문자 제거
        	str = str.replace(/[^\d.]/g, '');
    	}
	    var numberWithoutComma = parseFloat(str.replace(/,/g, '')); // 쉼표 제거 후 숫자로 변환
	    var numberWithCommas = numberWithoutComma.toLocaleString(); // 천 단위마다 쉼표 추가
	    obj.val(numberWithCommas); // 쉼표가 추가된 값을 입력 필드에 할당
	    return obj;
	}, onlyNumber : () =>{
		if(!( event.keyCode == 8 || //백스페이스
		   event.keyCode == 9 ||    //탭키
	  	   event.keyCode == 16 ||   //시프트
		   event.keyCode == 35 ||   //end
		   event.keyCode == 36 ||   //home
		   event.keyCode == 45 ||   //insert
	   	   event.keyCode == 46 ||   //delete
	   	   event.keyCode == 144 ||  //numlock
	      (event.keyCode >= 37 && event.keyCode <= 40) ||  // 화살표
	      (event.keyCode >= 48 && event.keyCode <= 57) ||  // 숫자
	      (event.keyCode >= 96 && event.keyCode <= 105)))    // 우측 키패드 숫자      
		{
			event.returnValue=false;
		}
	}, floatInput : (event, obj, pnt) =>{
		var inputValue = event.target.value;
		// 입력값에서 숫자와 소수점 이외의 문자 제거
	    var cleanedValue = inputValue.replace(/[^\d.]/g, '');
	    
	    // 소수점 이하 2자리까지만 유지
	    var decimalIndex = cleanedValue.indexOf('.');
	    if (decimalIndex !== -1) {
	        var integerPart = cleanedValue.slice(0, decimalIndex);
	        var decimalPart = cleanedValue.slice(decimalIndex + 1, decimalIndex  + 1 + pnt);
	        cleanedValue = integerPart + '.' + decimalPart;
	    }
	    
	    // 잘라낸 값으로 입력 필드 갱신
	    event.target.value = cleanedValue;
	}, phoneInput : (event)=>{
		var input = event.target;
	    var inputValue = input.value.replace(/\D/g, ''); // 숫자 이외의 문자 제거
	
	    var formattedValue = '';
	    if (inputValue.length > 3) {
	        formattedValue += inputValue.substr(0, 3) + '-';
	    }
	    if (inputValue.length > 7) {
	        formattedValue += inputValue.substr(3, 4) + '-';
	    }
	    formattedValue += inputValue.substr(7, 4);
	
	    input.value = formattedValue;		
	}, openFileUpload : (fileGrpId, refTableId, refDocId, refDocType, maxFileCnt, uploadType, fileJsonStr, callBackFn)=>{
		var url = "/s03/page/cmn/popup/fileUploadP01.do";
		var jsonObj = {param : 
		     {fileGrpId : fileGrpId
	        , refTableId : refTableId
	        , refDocId : refDocId
	        , refDocType : refDocType
	        , maxFileCnt : maxFileCnt
	        , uploadType : uploadType
	        , fileJsonStr : fileJsonStr
	        }
		};
		APPz.ui.openModal(url,"첨부파일",jsonObj,800,500,false, callBackFn);
	}, setDivDisableMode : (divNm, mode)=>{
		// 특정 div 내의 input과 select 요소를 찾습니다.
		var specificDiv = document.getElementById(divNm);
		var inputElements = specificDiv.querySelectorAll('input');
		var selectElements = specificDiv.querySelectorAll('select');
	
		// input 요소를 readOnly로 변경합니다.
		inputElements.forEach(function(input) {
		    input.readOnly = mode;
		});
	
		// select 요소를 readOnly로 변경합니다.
		selectElements.forEach(function(select) {
		    select.disabled = mode;
		});
	}, jqueryCalenderRange : (fromYmd, toYmd)=>{
		var dateFormat = "yy-mm-dd",
	    from = $('#'+fromYmd )
	      .datepicker({
	    	  dateFormat: dateFormat,
	          prevText: '이전 달',
	          nextText: '다음 달',
	          monthNames: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
	          monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	          dayNames: ['일', '월', '화', '수', '목', '금', '토'],
	          dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
	          dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
	          showMonthAfterYear: true,
	          yearSuffix: '년',
	          changeMonth: true,
	          numberOfMonths: 2,
	      })
	      .on( "change", function() {
	        to.datepicker( "option", "minDate", getDate( this ) );
	      }),
	    to = $('#'+toYmd).datepicker({
	      dateFormat: dateFormat,	
	      prevText: '이전 달',
	      nextText: '다음 달',
	      monthNames: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
	      monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	      dayNames: ['일', '월', '화', '수', '목', '금', '토'],
	      dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
	      dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
	      showMonthAfterYear: true,
	      yearSuffix: '년',
	      changeMonth: true,
	      numberOfMonths: 2
	    })
	    .on( "change", function() {
			
	      from.datepicker( "option", "maxDate", getDate( this ) );
	    });
	  $('#'+fromYmd).datepicker("option", "maxDate", $('#'+toYmd).val());
	  $('#'+toYmd).datepicker("option", "minDate", $('#'+fromYmd).val());
	
	  function getDate( element ) {
	    var date;
	    try {
	      date = $.datepicker.parseDate( dateFormat, element.value );
	    } catch( error ) {
	      date = null;
	    }
	    //console.log('date:'+date);
	
	    return date;
	  }
	}, jqueryCalender : (field) =>{
		$('#'+field ).datepicker({
		  	  dateFormat: 'yy/MM/dd',
		      prevText: '이전 달',
		      nextText: '다음 달',
		      monthNames: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
		      monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		      dayNames: ['일', '월', '화', '수', '목', '금', '토'],
		      dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
		      dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
		      showMonthAfterYear: true,
		      yearSuffix: '년',
		      changeYear:true,
		      changeMonth: true
		      });
	}, lpad : (str, length, padChar) =>{
    	str = str.toString();
    	while (str.length < length) {
        	str = padChar + str;
    	}
    	return str;
	}
};

APPz.cmn = {
	// 서버 API call
	// requestUrl : 호출 server url(필수)
	// reqData :  호출 파라메타 (필수 json 형식)
	// showLoading : Loading  처리여부(필)
	// callBack : 콜백처리 함수 (선택 없을 경우 sync 로 호출됨)
	ApiRequest:(requestUrl,reqData,showLoading,callBack)=>{

		_isInIframe = (window.location != window.parent.location) ? true : false;// 현재화면이 iframe 내인지체크.
		//console.log("_isInIframe = "+_isInIframe);
		let isAsync = typeof callBack != undefined && callBack != null && typeof callBack ==='function' ? true :false;
		showLoading = typeof showLoading != undefined && showLoading != null && typeof(showLoading) == typeof(true) ? showLoading :false;
	    if(!isAsync) showLoading = false;

	    let rtn;

		if(isAsync){//async 인경우.
			$.ajax({
	   		 	async: isAsync
	           ,type: "POST"
	           ,url: requestUrl
	           ,data: JSON.stringify(reqData)
	           ,dataType:"json"
	           ,contentType: "application/json"
	       	   ,beforeSend:(xhr)=>{
	       		   	// 통신을 시작할때 처리
	       		   	if(_csrf_header && _csrf_token){
 		   				xhr.setRequestHeader(_csrf_header,_csrf_token);// csrf 관련설정.
					}
	       		   	if(showLoading){
	       			   APPz.cmn._topLoading(true);
	       		   	}
	    		}
	           ,success:(result,status,xhr)=>{
					if(result.rtnCode == "0"){
						rtn = result;
		       	    	callBack(result);
					} else {
						duDialog(null,APPz.getMessage("E","biz.message",result.message));
					}
	            }
	           ,error:(xhr,status,error)=>{
	        	   APPz.cmn._ajaxError(xhr,status,error,requestUrl);
	            }
			   ,complete:()=>{
				   if(showLoading){
					   APPz.cmn._topLoading(false);// 통신이 완료된 후 처리
				   }
	    		}
	       });
		} else {
			$.ajax({
	   		 	async: isAsync
	           ,type: "POST"
	           ,url : requestUrl
	           ,data : JSON.stringify(reqData)
	           ,dataType:"json"
	           ,contentType:"application/json"
	           ,beforeSend :(xhr)=>{
	       		   	if(_csrf_header && _csrf_token){
 		   				xhr.setRequestHeader(_csrf_header,_csrf_token);// csrf 관련설정.
					}
	    		}
	           ,success:(result,status,xhr) =>{
				   if(result.rtnCode == "0"){
						rtn = result;
					} else {
						duDialog(null,APPz.getMessage("E","biz.message",result.message));
					}
	            }
	           ,error:(xhr,status,error)=>{
	        	   APPz.cmn._ajaxError(xhr,status,error,requestUrl);
	            }
	           ,complete:()=>{// 통신이 완료된 후 처리
	    		}
	       });
		}
		return rtn;
	},
	_ajaxError:(xhr,status,error,requestUrl)=>{
	   	var ct = xhr.getResponseHeader("content-type") || "";
	    if (ct.indexOf('html') > -1 && xhr.status == 200) {
			if(_isInIframe) {
	    		top.location.href = APPz.context();
	    	} else {
	    		self.location.href = APPz.context();
	    	}
	    	return;
	    }
	    if (ct.indexOf('json') > -1) {
	    	console.log("json error msg="+error);
	    }
	   	switch (xhr.status) {
   		case 200 :
   			break;
   		case 403 :
   			duDialog(null,APPz.getMessage("E","user.AccessDeniedException",""));
   			break;
   		case 404 :
   			duDialog(null,APPz.getMessage("E","error.404",""));
   			break;
   		default  :
   			duDialog(null,APPz.getMessage("E","error.500",""));
   			break;
	   	}
	},
	_topLoading:(flag)=>{//탑 로딩 on/off
	    if(flag){
	        if(!$(".topLoading")[0]){
	            $("body").append("<div class='topLoading'></div>");
	        }
	        $(".topLoading").addClass("active");
	    } else {
	        $(".topLoading").removeClass("active");
	    }
	}
}