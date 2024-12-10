<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title><APPz:jsptitle prgmcd="${_prgmcd_}"/></title>
<script type="text/javascript">
//===============================================================================================================
//jQuery ready
//===============================================================================================================
    var canvas;
    var ctx;
    var equipment =[];
    var isDragging = false;
    var dragEquip = null;
    var offsetX, offsetY;
    var factoryPlan = new Image();
    var circleImage = new Image();
    var squareImage = new Image();
    var selectedEquipment = [];
$(()=>{
	let cmmonCdList;
	initPage();
});
function initPage(){
	canvas = document.getElementById('factoryCanvas');
    ctx = canvas.getContext('2d');
    
    factoryPlan.src = '/s03/res/img/mornitoring/factory-plan.png';
    factoryPlan.onload = function() {
        drawEquipment(equipment);
    };
    
	/**공통코드작업 START**/
	jsonParam = { param : {
		          codeStr : "SITE_CD,FAB_CD" 
				}};//근무상태,부서코드,권한
	APPz.cmn.ApiRequest("${pageContext.request.contextPath}/api/cmn/common/getCodeList.do"
		, jsonParam,true
		, function($result){
			cmmonCdList = $result.list;
			APPz.ui.setComboByKey($('#SITE_CD'), 'SITE_CD', cmmonCdList, "classDtlCd", "classDtlNm","S");
			//APPz.ui.setCombo($('#GOOD_CLSS'), cmmonCdList, "classVal", "classDtlNm","S");
   		}
	);	
			
				
	drawEquipment = function(equipmentList) {
		debugger;
	    ctx.clearRect(0, 0, canvas.width, canvas.height);
	    ctx.drawImage(factoryPlan, 0, 0, canvas.width, canvas.height);

	    equipmentList.forEach(function(equip) {
	    	debugger;
	    	var locInfo = JSON.parse(equip.LOC_INFO);
	        if (locInfo.x == null || locInfo.y == null) {
	        	equip.x = 0;
	        	equip.y = 0;
	        }else{
	        	equip.x = locInfo.x;
	        	equip.y = locInfo.y;
	        }

	        // Dynamically load the image from equip.pathB
	        const equipImage = new Image();
	        equipImage.src = equip.pathB;

	        equipImage.onload = function() {
	            const circleX = equip.x - equipImage.width / 2;
	            const circleY = equip.y - equipImage.height / 2;

	            // 장비 상태가 'error'일 때 깜빡이도록 처리
	            if (equip.status === 'error') {
	                if (blinkState) {
	                    ctx.strokeStyle = 'red';
	                    ctx.lineWidth = 20;
	                    ctx.strokeRect(circleX, circleY, equipImage.width, equipImage.height);
	                }
	            }

	            // 장비 이미지 그리기
	            ctx.drawImage(equipImage, circleX, circleY);

	            // ID와 Name 그리기
	            ctx.fillStyle = 'black';
	            ctx.font = '12px Arial';
	            ctx.fillText('ID: ' + equip.id, equip.x + 35, equip.y - 35);
	            ctx.fillText('Name: ' + equip.name, equip.x + 35, equip.y - 20);

	            // 선택된 장비 파란색 테두리 표시
	            if (selectedEquipment.includes(equip)) {
	                ctx.strokeStyle = 'blue';
	                ctx.lineWidth = 2;
	                ctx.strokeRect(circleX, circleY, equipImage.width, equipImage.height);
	            }
	        };
	    });
	};				

	selectSiteCdChanged = function(val){
		var codeList = APPz.ui.filterJson(cmmonCdList, 'etcCd1', val);
		APPz.ui.setComboByKey($('#FAB_CD'), 'FAB_CD', codeList, "classDtlCd", "classDtlNm","S");
	}

	selectFabCdChanged = function(val){
			
		jsonParam = { param : APPz.ui.convertFormToJson("frm01") };
		var ApiUrl = "${pageContext.request.contextPath}/api/za/zaa/searchZ01IOTList.do";
		APPz.cmn.ApiRequest(ApiUrl,jsonParam,true,(e)=>{
			if(e.data.length>0){
				//clearCanvas();
				var iotList = e.data;
				if (iotList.length>0) {
					// 배경 이미지가 있다면 공장 배경 이미지를 업데이트
		        	//canvas.src = factoryImg;
		        	clearCanvas2(iotList);
				}
			}
	   	}); 
	}

				
}

clearCanvas = function() {
    // Clear the canvas
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    // Clear the equipment array
    equipment = [];
    // Redraw the canvas background (factory plan)
    ctx.drawImage(factoryPlan, 0, 0, canvas.width, canvas.height);
};

clearCanvas2 = function(iotList) {
	debugger;
	canvas = document.getElementById('factoryCanvas');
	var factoryImg = iotList[0].FILE_PATHA;
	if(canvas.getContext){
	    ctx = canvas.getContext('2d');
	    factoryPlan = new Image();
	    factoryPlan.src = factoryImg;
	    factoryPlan.onload = function(){                
	    	//이미지의 원하는 부분만 잘라서 그리기                
	    	//drawImage(이미지객체,                 
	    	//        이미지의 왼위 부분x, 이미지의 왼위 부분y, 이미지의 원하는 가로크기, 이미지의 원하는 세로크기,                
	    	//        사각형 부분x, 사각형 부분y, 가로크기, 세로크기)                
	    	//draw.drawImage(img, 100,100, 300,300, 50,50, 250,300);                                
	    	//전체 이미지 그리기                //drawImage(이미지객체, 사각형 왼위 x, 사각형 왼위 y, 가로크기, 세로크기)                
	    	ctx.drawImage(factoryPlan, 0, 0, canvas.width, canvas.height);
	    	drawEquipment(iotList);
	    }
	}
};



document.addEventListener('DOMContentLoaded', () => {
    const canvas = document.getElementById('factoryCanvas');
    const ctx = canvas.getContext('2d');

    const factoryPlan = new Image();
    factoryPlan.src = '/s03/res/img/mornitoring/factory-plan.png';

    let equipment = [
        { id: 1, x: 100, y: 100, status: 'normal' },
        { id: 2, x: 300, y: 100, status: 'normal' },
        { id: 3, x: 500, y: 100, status: 'error' },
        { id: 4, x: 700, y: 100, status: 'normal' }
    ];

    // 로컬 스토리지에서 저장된 위치 불러오기
    const savedEquipment = localStorage.getItem('equipment');
    if (savedEquipment) {
        equipment = JSON.parse(savedEquipment);
    }

    let isDragging = false;
    let dragEquip = null;
    let offsetX, offsetY;

    function drawEquipment(equipment) {
        equipment.forEach(equip => {
            // 원 그리기
            ctx.beginPath();
            ctx.arc(equip.x, equip.y, 30, 0, 2 * Math.PI);
            ctx.fillStyle = equip.status === 'normal' ? 'green' : 'red';
            ctx.fill();
            ctx.stroke();

            // 사각형 그리기
            ctx.beginPath();
            ctx.rect(equip.x - 20, equip.y - 50, 40, 40);
            ctx.fillStyle = equip.status === 'normal' ? 'lightgreen' : 'lightcoral';
            ctx.fill();
            ctx.stroke();

            // ID 텍스트 그리기
            ctx.font = '16px Arial';
            ctx.fillStyle = 'black';
            ctx.fillText(`ID: ${equip.id}`, equip.x - 20, equip.y + 70);
        });
    }
    
    function updateJSONOutput(){
    	equipment.forEach(function(equip) {
            var equipElement = document.querySelector('[data-id="' + equip.id + '"]');
            if (equipElement) {
                // Update the equipment array with the current x and y positions
                equip.x = parseInt(equipElement.style.left, 10);
                equip.y = parseInt(equipElement.style.top, 10);
            }
        });
    	
    	// Display updated equipment array as JSON (assuming txt_jsonOutput is a text box for showing JSON)
        if (txt_jsonOutput) {
            txt_jsonOutput.setValue(JSON.stringify(equipment, null, 2));
        }
    	try{
    	// Update the dlt_equipment with the new x, y position for each equipment
	        equipment.forEach(function(equip) {
	            for (var idx = 0; idx < scwin.equipment.length; idx++) {
	                var rowJson = scwin.equipment[idx];
	                if (equip.id == rowJson.IOT_ID) {
	                    var dataInfo = JSON.stringify({ x: equip.x, y: equip.y });
	                    dlt_equipment.setCellData(idx, "LOC_INFO", dataInfo);
	                }
	            }
	        });
        }catch(Err){
        }
    } 

    function getMousePos(canvas, evt) {
        const rect = canvas.getBoundingClientRect();
        return {
            x: evt.clientX - rect.left,
            y: evt.clientY - rect.top
        };
    }

    function isInsideCircle(pos, equip) {
        const dx = pos.x - equip.x;
        const dy = pos.y - equip.y;
        return dx * dx + dy * dy <= 30 * 30; // 원 내부에 있는지 확인
    }

    function isInsideRect(pos, equip) {
        return pos.x >= equip.x - 20 && pos.x <= equip.x + 20 &&
               pos.y >= equip.y - 50 && pos.y <= equip.y - 10; // 사각형 내부에 있는지 확인
    }

    function handleCircleDblClick(equip) {
        alert(`Circle double-clicked: ID id_Circle_${equip.id}`);
        // 추가적인 로직을 여기에 추가할 수 있습니다.
    }

    function handleRectDblClick(equip) {
        alert(`Rectangle double-clicked: ID id_Square_${equip.id}`);
        // 추가적인 로직을 여기에 추가할 수 있습니다.
    }

    canvas.addEventListener('dblclick', (evt) => {
        const mousePos = getMousePos(canvas, evt);
        equipment.forEach(equip => {
            if (isInsideCircle(mousePos, equip)) {
                handleCircleDblClick(equip); // 원 더블클릭 처리
            } else if (isInsideRect(mousePos, equip)) {
                handleRectDblClick(equip); // 사각형 더블클릭 처리
            }
        });
    });

    canvas.addEventListener('mousedown', (evt) => {
        const mousePos = getMousePos(canvas, evt);
        equipment.forEach(equip => {
            if (isInsideCircle(mousePos, equip) || isInsideRect(mousePos, equip)) {
                isDragging = true;
                dragEquip = equip;
                offsetX = mousePos.x - equip.x;
                offsetY = mousePos.y - equip.y;
            }
        });
    });

    canvas.addEventListener('mousemove', (evt) => {
        if (isDragging) {
            const mousePos = getMousePos(canvas, evt);
            dragEquip.x = mousePos.x - offsetX;
            dragEquip.y = mousePos.y - offsetY;
            drawEquipment();
        }
    });

    canvas.addEventListener('mouseup', () => {
        if (isDragging) {
            isDragging = false;
            dragEquip = null;
            // 이동된 위치를 로컬 스토리지에 저장
            localStorage.setItem('equipment', JSON.stringify(equipment));
        }
    });

    canvas.addEventListener('mouseout', () => {
        if (isDragging) {
            isDragging = false;
            dragEquip = null;
            // 이동된 위치를 로컬 스토리지에 저장
            localStorage.setItem('equipment', JSON.stringify(equipment));
        }
    });

        setInterval(() => {
        equipment.forEach(equip => {
            equip.status = Math.random() > 0.8 ? 'error' : 'normal';
        });
        drawEquipment();
    }, 5000);
});
</script>
		<style>
			.circle {
			position: absolute;
			width: 60px;
			height: 60px;
			left: -30px;
			top: -30px;
			}
			.square {
			position: absolute;
			width: 60px;
			height: 60px;
			left: -20px;
			top: -50px;
			}
			.error-blink {
			animation: blink 1s infinite;
			border: 10px solid red;
			}
			@keyframes blink {
			0% { border-color: red; }
			50% { border-color: transparent; }
			100% { border-color: red; }
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
                            <select name="FAB_CD" id="FAB_CD" class="wauto" onchange="selectFabCdChanged(this.value)">
                            </select>
                        </td>                        
                    </tr>
                </tbody>
            </table>
        </div>		
		<div class="align both vm">
			<h2 class="h2">메뉴목록</h2>
			<div class="align right">
			</div>
		</div>
		<div class="wrap">
		<canvas id="factoryCanvas" name="factoryCanvas" width="800" height="600"></canvas>
   		<textarea id="txt_jsonOutput" name="txt_jsonOutput" rows="5" cols="100%">
		</textarea>
		</div>
	</div>
	<!-- //(content) -->

</div>
<!-- //(wrapper) -->

</form>
</body>
</html>