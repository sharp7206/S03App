<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title><APPz:jsptitle prgmcd="${_prgmcd_}"/></title>
<script type="text/javascript">
//===============================================================================================================
//jQuery ready
//===============================================================================================================
$(()=>{
	let cmmonCdList;
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
			//APPz.ui.setCombo($('#GOOD_CLSS'), cmmonCdList, "classVal", "classDtlNm","S");
   		}
	);	
				

	selectSiteCdChanged = function(val){
		var codeList = APPz.ui.filterJson(cmmonCdList, 'etcCd1', val);
		APPz.ui.setComboByKey($('#FAB_CD'), 'FAB_CD', codeList, "classDtlCd", "classDtlNm","S");
	}

	selectFabCdChanged = function(val){
			
		jsonParam = { param : APPz.ui.convertFormToJson("frm01") };
		var ApiUrl = "${pageContext.request.contextPath}/api/za/zaa/searchZ01IOTList.do";
		APPz.cmn.ApiRequest(ApiUrl,jsonParam,true,(e)=>{
			debugger;
			if(e.data.length>0){
				const canvas = document.getElementById('factoryCanvas');
			    const ctx = canvas.getContext('2d');

			    const factoryPlan = new Image();
				var iotList = e.data;
				var factoryImg = iotList[0].FILE_PATHA;
				console.log("Factory Image Path:", factoryImg); // 이미지 경로 확인
					if (factoryImg) {
						// 배경 이미지가 있다면 공장 배경 이미지를 업데이트
				        if (factoryImg) {
				        	factoryPlan.src = factoryImg;
				        }
						/*
						var baseUrl = "${pageContext.request.contextPath}";
						console.log("Factory Image Path (before assignment):", factoryImg);
					    const fullPath = factoryImg;
					    console.log("Full Image URL (calculated):", fullPath);
					    layoutElement.style.backgroundImage = `url('${factoryImg}')`;
					    // CSS 속성을 한 번에 변경하여 리플로우를 최소화
					    layoutElement.style.cssText = `
					        background-image: url(${factoryImg});
					        background-size: cover;
					        background-repeat: no-repeat;
					        background-position: center;
					    `;
					    */
					}
			}
	   	}); 
	}

				
}
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

    function drawEquipment() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.drawImage(factoryPlan, 0, 0, canvas.width, canvas.height); // 배경 도면 그리기
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

    factoryPlan.onload = drawEquipment;

    setInterval(() => {
        equipment.forEach(equip => {
            equip.status = Math.random() > 0.8 ? 'error' : 'normal';
        });
        drawEquipment();
    }, 5000);
});
</script>
    <style>
        canvas {
            border: 1px solid black;
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
			<a href="javascript:doAction('R_Main');" class="btn blue regular">조회</a>
			<a href="javascript:doAction('R_RegiPop');" class="btn blue regular">신규등록</a>
			<a href="javascript:doAction('Excel');" class="btn blue regular">Excel</a>
			</div>
		</div>
		<div class="wrap">
		<canvas id="factoryCanvas" width="800" height="600"></canvas>
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