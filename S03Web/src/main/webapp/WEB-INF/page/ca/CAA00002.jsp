<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title><APPz:jsptitle prgmcd="${_prgmcd_}"/></title>
<script type="text/javascript">
var canvas;
var ctx;
var selectedEquipment = [];
var equipment =[];
var isDragging = true;
var isReadYn = false;
var dragEquip = null;
var offsetX, offsetY;
var factoryPlan = new Image();
var circleImage = new Image();
var squareImage = new Image();
var cmmonCdList;
//===============================================================================================================
//jQuery ready
//===============================================================================================================
$(()=>{
	initPage();
});
function initPage(){
	/**공통코드작업 START**/
	jsonParam = { param : {
		          codeStr : "SITE_CD,FAB_CD" 
				}};//근무상태,부서코드,권한
	APPz.cmn.ApiRequest("${pageContext.request.contextPath}/api/cmn/common/getCodeList.do"
		, jsonParam,true
		, function($result){
			cmmonCdList = $result.list;
			APPz.ui.setComboByKey($('#SITE_CD'), 'SITE_CD', cmmonCdList, "classDtlCd", "classDtlNm","S");
   		}
	);	
				
    // Add event listener to #factoryLayout to clear selection on outside click
    document.getElementById("factoryLayout").addEventListener("mousedown", function (event) {
        const clickedElement = event.target;

        // If clicked element is not an equipment item, clear the selection
        if (!clickedElement.classList.contains("equipment-item")) {
            clearSelection();
        }
    });
    
	selectSiteCdChanged = function(val){
		var codeList = APPz.ui.filterJson(cmmonCdList, 'etcCd1', val);
		APPz.ui.setComboByKey($('#FAB_CD'), 'FAB_CD', codeList, "classDtlCd", "classDtlNm","S");
	}

	selectFabCdChanged = function(){
		doAction('R_Main');
	}
	
	onMouseDown = function(event) {
		if (isReadYn) return; // Prevent movement if read-only
	    let equipElement = event.target;
	
	    if (event.ctrlKey) { // If Ctrl is held, add to selection
	        if (selectedEquipment.includes(equipElement)) {
	            selectedEquipment = selectedEquipment.filter(item => item !== equipElement);
	            equipElement.classList.remove('selected');
	        } else {
	            selectedEquipment.push(equipElement);
	            equipElement.classList.add('selected');
	        }
	    } else { // Single selection
	        selectedEquipment.forEach(item => item.classList.remove('selected'));
	        selectedEquipment = [equipElement];
	        equipElement.classList.add('selected');
	    }
	
	    isDragging = true;
	    offsetX = event.clientX - equipElement.offsetLeft;
	    offsetY = event.clientY - equipElement.offsetTop;
	    document.addEventListener('mousemove', onMouseMove);
	    updateJSONOutput();
	};	
	
	clearSelection = function() {
        selectedEquipment.forEach(function(item) {
            item.classList.remove("selected");
        });
        selectedEquipment = [];
    };
    
    onMouseMove = function(event) {
    	if (isReadYn) return; // Prevent movement if read-only
    	if (isReadYn || !isDragging || selectedEquipment.length === 0) return;
	    if (isDragging && selectedEquipment.length > 0) {
	        selectedEquipment.forEach(equipElement => {
	            let x = event.clientX - offsetX;
	            let y = event.clientY - offsetY;
	
	            // Restrict movement within #factoryLayout
	            let layout = document.getElementById('factoryLayout');
	            let layoutWidth = layout.clientWidth;
	            let layoutHeight = layout.clientHeight;
	            let equipWidth = equipElement.offsetWidth;
	            let equipHeight = equipElement.offsetHeight;
	
	            // Boundary checks for x and y positions
	            if (x < 0) x = 0;
	            if (y < 0) y = 0;
	            if (x + equipWidth > layoutWidth) x = layoutWidth - equipWidth;
	            if (y + equipHeight > layoutHeight) y = layoutHeight - equipHeight;
	
	            // Set the equipment position
	            equipElement.style.left = x + 'px';
	            equipElement.style.top = y + 'px';
	        });
	        updateJSONOutput();
	    }
	};
	
	// Function to align selected items horizontally
	btn_horizontalAlign = function() {
	    if (selectedEquipment.length > 0) {
	        let yPosition = parseInt(selectedEquipment[0].style.top);
	        selectedEquipment.forEach(equipElement => {
	            equipElement.style.top = yPosition + 'px';
	        });
	        updateJSONOutput();
	    }
	};
	
	// Function to align selected items vertically
	btn_verticalAlign = function() {
	    if (selectedEquipment.length > 0) {
	        let xPosition = parseInt(selectedEquipment[0].style.left);
	        selectedEquipment.forEach(equipElement => {
	            equipElement.style.left = xPosition + 'px';
	        });
	        updateJSONOutput();
	    }
	};


    updateJSONOutput = function() {
        // Loop through each equipment element in the DOM to capture its latest position
        equipment.forEach(function(equip) {
            var equipElement = document.querySelector('[data-id="' + equip.id + '"]');
            if (equipElement) {
                // Update the equipment array with the current x and y positions
                equip.x = parseInt(equipElement.style.left, 10);
                equip.y = parseInt(equipElement.style.top, 10);
                var dataInfo = JSON.stringify({x : equip.x, y : equip.y});
                equip.LOC_INFO = dataInfo;
            }
        });

        // Display updated equipment array as JSON (assuming txt_jsonOutput is a text box for showing JSON)
        if (txt_jsonOutput) {
        	$("#txt_jsonOutput").val(JSON.stringify(equipment, null, 2));
        }
    };
	
	onMouseUp = function() {
	    isDragging = false;
	    //clearSelection();
	};
};

	blinkErrorEquipment = function() {
		equipment.forEach(function(equip) {
			var equipElement = document.querySelector('[data-id="' + equip.id + '"]');
			if (equip.status === 'error') {
				if (equipElement) {
					equipElement.classList.toggle('error-blink');
				}
			}
		});
	};

    sbx_site_onviewchange = function(info) {
        var codeList = cmmonCdList;
        var retJson = codeList.filter(codeList => codeList.GRP_CD==='10001' && codeList.ATTR_VALUE1==='01');
        var codeOptions = [ 
    					{ grpCd : "10001", compID : "sbx_factory", useLocalCache : false, jsonData : retJson, code : "CODE_NM", codeNm : "COM_CD"  } ];
    		com.data.setCommonCodeN(codeOptions);
    };


    renderEquipment = function() {
    	
        let factoryLayout = document.getElementById('factoryLayout');
        factoryLayout.innerHTML = ''; // Clear existing elements

        equipment.forEach(equip => {
        	
            let equipElement = document.createElement('div');
            equipElement.classList.add('equipment-item');
            equipElement.style.left = equip.x + 'px';
            equipElement.style.top = equip.y + 'px';
            equipElement.dataset.id = equip.id;

            // Create a name display at the top of the equipment element
            let nameElement = document.createElement('div');
            nameElement.classList.add('equipment-name');
            nameElement.textContent = equip.name;
            equipElement.appendChild(nameElement);

            // Set background image and properties for the equipment
            let tempSrc  = '${pageContext.request.contextPath}/api/cmn/file/GetImgVw.do?filePath='+equip.imgPath;
            equipElement.style.backgroundImage =  "url('" + tempSrc + "')";
            equipElement.style.backgroundSize = 'cover';

            // Add error or selection styles as needed
            if (equip.status === 'error') {
                equipElement.classList.add('error-blink');
            }

            // Tooltip element for displaying equipment details on hover
            let tooltip = document.createElement('div');
            tooltip.classList.add('tooltip');
            tooltip.style.position = 'absolute';
            tooltip.style.display = 'none';
            tooltip.style.backgroundColor = 'rgba(0, 0, 0, 0.75)';
            tooltip.style.color = '#fff';
            tooltip.style.padding = '10px';
            tooltip.style.borderRadius = '5px';
            console.log('chk=='+`ID: ${equip.id}\n Name: ${equip.name}\n Status: ${equip.status}`);
            tooltip.textContent = `ID: ${equip.id}\n Name: ${equip.name}\n Status: ${equip.status}`;
            equipElement.appendChild(tooltip);

            // Show tooltip on mouseover
            equipElement.addEventListener('mouseover', () => {
                tooltip.style.display = 'block';
                tooltip.style.left = '10px'; // Offset from the equipment item
                tooltip.style.top = '90px';  // Adjust to position tooltip correctly
            });

            // Hide tooltip on mouseout
            equipElement.addEventListener('mouseout', () => {
                tooltip.style.display = 'none';
            });

            // Mouse events for selection and dragging
            equipElement.addEventListener('mousedown', onMouseDown);
            equipElement.addEventListener('mouseup', onMouseUp);
            
            factoryLayout.appendChild(equipElement);
        });
    };    
    
    

    function doAction(sAction)
    {
    	switch(sAction)
    	{
    	case "R_Main" :      //조회
    		jsonParam = { param : APPz.ui.convertFormToJson("frm01") };
    		var ApiUrl = "${pageContext.request.contextPath}/api/ca/caa/searchZ01IOTList.do";
    		APPz.cmn.ApiRequest(ApiUrl,jsonParam,true,(e)=>{
    			if(e.data.length>0){
    				var iotList = e.data;
    				// 첫 번째 아이템의 공장 배경 이미지 경로
    				var factoryImg =  '${pageContext.request.contextPath}/api/cmn/file/GetImgVw.do?filePath='+iotList[0].FAB_IMG;
    	            // 배경 이미지가 있다면 공장 배경 이미지를 업데이트
    	            if (factoryImg) {
        				const factoryLayout = document.getElementById("factoryLayout");
        				// 이미지 객체를 생성하여 사이즈를 가져옴
        		        const img = new Image();
        		        img.src = factoryImg;
        		        img.onload = function () {
        		            // 이미지의 원본 크기를 가져와서 div에 적용
        		            factoryLayout.style.width = img.width + "px";
        		            factoryLayout.style.height = img.height + "px";

        		            // 배경 이미지 설정
        		            factoryLayout.style.backgroundImage = "url('" + factoryImg + "')";
        		        };
        		        
    	            }

    		         // 장비 리스트를 equipment 배열에 추가
    				equipment = [];	
    	            for (var idx = 0; idx < iotList.length; idx++) {
    	                var item = iotList[idx];
    	                var locInfo = item.LOC_INFO ? JSON.parse(item.LOC_INFO) : { x: 0, y: 0 }; // 위치 정보가 없을 경우 기본값 {x:0, y:0}

    	                var equip = {
    		                    id: item.IOT_ID,
    		                    name: item.IOT_NM,
    		                    pathB : item.FILE_PATHB,
    		                    imgPath : item.IMG_PATH,
    		                    fabImg : item.FAB_IMG,
    		                    x: item.LOC_INFO ? (JSON.parse(item.LOC_INFO)).x : 100,  // Use first row's LOC_INFO for all equipment
    		                    y: item.LOC_INFO ? (JSON.parse(item.LOC_INFO)).y : 100,  // Use first row's LOC_INFO for all equipment
    		                    type: item.circle,
    		                    status: item.STATUS,
    		                    factory_cd: item.FACTORY_CD
    		                };

    	                equipment.push(equip); // equipment 배열에 장비 추가
    	            }    				
    	            renderEquipment(equipment);
    	            setInterval(blinkErrorEquipment, 1000);
    			}
    	   	}); 
    		break;
    	case "SaveIOT" :
    		jsonParam = { param : APPz.ui.convertFormToJson("frm01") };
    		jsonParam.param.gridData = $("#txt_jsonOutput").val();
    		
    		
    		var urlAddr = "${pageContext.request.contextPath}/api/ca/caa/saveZ01IOTList.do";
			duDialog(null,APPz.getMessage("C","confirm.DoProcess","저장"), {
				buttons: duDialog.OK_CANCEL,
				callbacks: {
					okClick:function(e){
						this.hide()
						APPz.cmn.ApiRequest(urlAddr,jsonParam,true,function(result){//메뉴저장
							
		   				if(result.rtnCode>=0){
		   					duDialog(null,"저장되었습니다.",{
			   					callbacks: {
			   						okClick: function(e) {
			   							this.hide();
			   							doAction('R_Main');
			   						}
			   					}
			   				});
		   				}else{
		   					duDialog("저장실패",result.message,{callbacks: {okClick: function(e) {this.hide()}}
		   					});
		   				}
		   			});

					}
				}
			});
			
    		break;
    	case "Excel" :
    		//var params = {"FileName":"excel2", "SheetName":"user", "HiddenColumn":0};
    		//MenuSheet.Down2Excel(params);
    		var params = {FileName:"거래내역.xlsx",SheetName:"User",SheetDesign:1,Merge:1,OnlyHeaderMerge:1,DownRows:"",DownCols:""};
    		mySheet1.Down2Excel(params);
    		break;
    	}
    }
</script>
    <style>
.equipment-item {
    position: absolute;
    width: 80px;
    height: 80px;
    text-align: center;
    border-radius: 0%;
    border: 2px solid black;
    background-color: white;
    cursor: pointer;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
}
/* Styling for name displayed at the top */
.equipment-name {
    background-color: rgba(255, 255, 255, 0.7);
    width: 100%;
    font-size: 12px;
    font-weight: bold;
    padding: 2px;
    text-align: center;
    color: black;
}

.error-blink {
    animation: blink 3s infinite;
    border: 5px solid red;
}

.selected {
    border: 3px dashed blue;
}
.tooltip {
    position: absolute;
    background-color: rgba(0, 0, 0, 0.75);
    color: #fff;
    padding: 5px;
    border-radius: 5px;
    font-size: 12px;
    white-space: pre-line;
    z-index: 100;
}
@keyframes blink {
    0%, 100% { border-color: red; } /* Start and end with red */
    50% { border-color: transparent; } /* Halfway through, transparent */
}
		#factoryLayout {
			position: relative;
			width: 1000px;
			height: 500px;
			border: 1px solid #000;
			background-image: '';
			background-size: cover;
		}
	</style>
	
<!--조회함수를 이용하여 조회 완료되었을때 발생하는 이벤트-->


<body class="bg-appz-body">
<form id="frm01" name="frm01" method="post">
<!-- (wrapper) -->
<div id="wrap">
	<!-- (content) -->
	<div class="content">
<sys:headinfo prgmcd="${_prgmcd_}"/>
        <div class="search">
			<table class="type2">
			<colgroup>
			<col style="border:0px solid #999;" width="10%">
			<col style="border:0px solid #999;" width="40%">
			<col style="border:0px solid #999;" width="10%">
			<col style="border:0px solid #999;" width="*">
			</colgroup>
                <tbody>
                    <tr>
                        <th>플랜트</th>
                        <td>
                            <select name="SITE_CD" id="SITE_CD" class="wauto" onchange="selectSiteCdChanged(this.value)">
                            </select>~
                            <select name="FAB_CD" id="FAB_CD" class="wauto" onchange="selectFabCdChanged()">
                            </select>
                        </td>                        
                    </tr>
                </tbody>
            </table>
        </div>		
		<div class="align both vm">
			<h2 class="h2">메뉴목록</h2>
			<div class="align right">
			<a href="javascript:btn_horizontalAlign();" class="btn blue regular">가로정렬</a>
			<a href="javascript:btn_verticalAlign();" class="btn blue regular">세로정렬</a>
			<a href="javascript:doAction('R_Main');" class="btn blue regular">조회</a>
			<a href="javascript:doAction('SaveIOT');" class="btn blue regular">저장</a>
			<a href="javascript:doAction('Excel');" class="btn blue regular">Excel</a>
			</div>
		</div>
		<div class="wrap">
		<div id="factoryLayout" name="factoryLayout" style=""></div>
   		<textarea id="txt_jsonOutput" name="txt_jsonOutput" style="display:none">
		</textarea>
		</div>
	</div>
	<!-- //(content) -->

</div>
<!-- //(wrapper) -->

</form>
</body>
</html>