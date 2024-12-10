<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title><APPz:jsptitle prgmcd="${_prgmcd_}"/></title>
<script type="text/javascript">
var canvas;
var ctx;
var equipment =[];
var isDragging = true;
var dragEquip = null;
var offsetX, offsetY;
var factoryPlan = new Image();
var circleImage = new Image();
var squareImage = new Image();
var selectedEquipment = [];
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
				

	selectSiteCdChanged = function(val){
		var codeList = APPz.ui.filterJson(cmmonCdList, 'etcCd1', val);
		APPz.ui.setComboByKey($('#FAB_CD'), 'FAB_CD', codeList, "classDtlCd", "classDtlNm","S");
	}

	selectFabCdChanged = function(){
		doAction('R_Main');
	}

	canvas = document.getElementById('factoryCanvas');
    ctx = canvas.getContext('2d');
    factoryPlan.src = '../../res/img/mornitoring/factory-plan.png';
    circleImage.src = '../../res/img/mornitoring/circle_normal.png';
    //squareImage.src = '../../cm/images/contents/square_normal.png';
    factoryPlan.onload = function() {
        drawEquipment(equipment);
    };

    circleImage.onload = function() {
        drawEquipment(equipment);
    };

    squareImage.onload = function() {
        drawEquipment(equipment);
    };
    
    canvas.addEventListener('mousedown', function (evt) {
        const mousePos = getMousePos(canvas, evt);
        let clickedEquip = null;

        equipment.forEach(function (equip) {
            if (isInsideCircle(mousePos, equip)) {
                clickedEquip = equip;
            }
        });

        if (clickedEquip) {
            isDragging = true; // Start dragging
            if (evt.ctrlKey) {
                const index = selectedEquipment.indexOf(clickedEquip);
                if (index === -1) {
                    selectedEquipment.push(clickedEquip);
                } else {
                    selectedEquipment.splice(index, 1);
                }
            } else {
                selectedEquipment = [clickedEquip];
            }

            if (selectedEquipment.length > 0) {
                const firstSelected = selectedEquipment[0];
                offsetX = mousePos.x - firstSelected.x;
                offsetY = mousePos.y - firstSelected.y;
            }
            drawEquipment(equipment);
            updateJSONOutput();
        } else {
            if (!evt.ctrlKey) {
                selectedEquipment = [];
                drawEquipment(equipment);
            }
        }
    }, { passive: true });

    canvas.addEventListener('mousemove', function (evt) {
        if (isDragging && selectedEquipment.length) {
            const mousePos = getMousePos(canvas, evt);
            selectedEquipment.forEach(function (equip) {
                equip.x = mousePos.x - offsetX;
                equip.y = mousePos.y - offsetY;
            });

            drawEquipment(equipment);
            updateJSONOutput();
        }
    }, { passive: true });

    canvas.addEventListener('mouseup', function () {
        isDragging = false; // Stop dragging
        updateJSONOutput();
    }, { passive: true });

    canvas.addEventListener('mouseout', function () {
        isDragging = false; // Stop dragging when mouse leaves canvas
        updateJSONOutput();
    }, { passive: true });

    canvas.addEventListener('dblclick', function(evt) {
        const mousePos = getMousePos(canvas, evt);
        equipment.forEach(function(equip) {
            if (isInsideCircle(mousePos, equip)) {
                handleCircleDblClick(equip);
            }
        });
    }, { passive: true });

    setInterval(function() {
        equipment.forEach(function(equip) {
            equip.status = Math.random() > 0.8 ? 'error' : 'normal';
        });
        drawEquipment(equipment);
    }, 5000);

    updateJSONOutput();
};

    var blinkState = true;  // 깜빡임 상태를 저장하는 변수
    var blinkInterval = 9000; // 1초 단위로 깜빡이기

    drawEquipment = function(equipmentList) {
    	
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.drawImage(factoryPlan, 0, 0, canvas.width, canvas.height);
		
        equipmentList.forEach(function(equip) {
        	if (equip.x == null || equip.y == null) {
                equip.x = 0;
                equip.y = 0;
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




        updateJSONOutput = function() {
        	equipment.forEach(function(equip) {
        		var dataInfo = JSON.stringify({x : equip.x, y : equip.y});
                equip.LOC_INFO = dataInfo;
            });
        	
        	// Display updated equipment array as JSON (assuming txt_jsonOutput is a text box for showing JSON)
            if ($("#txt_jsonOutput")) {
            	$("#txt_jsonOutput").val(JSON.stringify(equipment, null, 2));
            }
        	
            localStorage.setItem('equipment', JSON.stringify(equipment));
                
        }
        

            function getMousePos(canvas, evt) {
                var rect = canvas.getBoundingClientRect();
                return {
                    x: evt.clientX - rect.left,
                    y: evt.clientY - rect.top
                };
            }

            function isInsideCircle(pos, equip) {
                var dx = pos.x - equip.x;
                var dy = pos.y - equip.y;
                return dx * dx + dy * dy <= (circleImage.width / 2) * (circleImage.width / 2);
            }

            function handleCircleDblClick(equip) {
                var newName = prompt("Enter new name for " + equip.name, equip.name);
                if (newName) {
                    equip.name = newName;
                    //localStorage.setItem('equipment', JSON.stringify(equipment));
                    drawEquipment(equipment);
                }
            }

    sbx_site_onviewchange = function(info) {
        var codeList = cmmonCdList;
        var retJson = codeList.filter(codeList => codeList.GRP_CD==='10001' && codeList.ATTR_VALUE1==='01');
        var codeOptions = [ 
    					{ grpCd : "10001", compID : "sbx_factory", useLocalCache : false, jsonData : retJson, code : "CODE_NM", codeNm : "COM_CD"  } ];
    		com.data.setCommonCodeN(codeOptions);
    };


    clearCanvas = function(iotList) {
    	equipment = [];
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
    	    	console.log('iotList.length=='+iotList.length);
	    	    for(var idx=0; idx<iotList.length; idx++){
	            	var item = iotList[idx];
	                var equip = {
	                    id: item.IOT_ID,
	                    name: item.IOT_NM,
	                    pathB : item.FILE_PATHB,
	                    x: item ? (JSON.parse(item.LOC_INFO)).x : 0,  // Use first row's LOC_INFO for all equipment
	                    y: item ? (JSON.parse(item.LOC_INFO)).y : 0,  // Use first row's LOC_INFO for all equipment
	                    type: item.circle,
	                    status: item.STATUS,
	                    factory_cd: item.FACTORY_CD
	                };
	                equipment.push(equip); // Add to equipment array
	            }
	
	            // Redraw the equipment on the canvas with the updated data
    	    	drawEquipment(equipment);
    	    }
    	}
    };
    
    btn_horizontalAlign = function() {
        if (selectedEquipment.length > 1) {
            var minY = Math.min(...selectedEquipment.map(equip => equip.y));
            selectedEquipment.forEach(function(equip) {
                equip.y = minY;
            });
            drawEquipment(equipment);
            //localStorage.setItem('equipment', JSON.stringify(equipment));
            updateJSONOutput();
        } else {
            alert('가로 정렬을 위해 두 개 이상의 장비를 선택하십시오.');
        }
    };
    
    btn_verticalAlign = function() {
        if (selectedEquipment.length > 1) {
            var minX = Math.min(...selectedEquipment.map(equip => equip.x));
            selectedEquipment.forEach(function(equip) {
                equip.x = minX;
            });
            drawEquipment(equipment);
            //localStorage.setItem('equipment', JSON.stringify(equipment));
            updateJSONOutput();
        } else {
            alert('세로 정렬을 위해 두 개 이상의 장비를 선택하십시오.');
        }
    };

    function doAction(sAction)
    {
    	switch(sAction)
    	{
    	case "R_Main" :      //조회
    		jsonParam = { param : APPz.ui.convertFormToJson("frm01") };
    		var ApiUrl = "${pageContext.request.contextPath}/api/za/zaa/searchZ01IOTList.do";
    		APPz.cmn.ApiRequest(ApiUrl,jsonParam,true,(e)=>{
    			if(e.data.length>0){
    				//equipment  = e.data;
    				var iotList = e.data;
    				clearCanvas(iotList);
    			}
    	   	}); 
    		break;
    	case "SaveIOT" :
    		jsonParam = { param : APPz.ui.convertFormToJson("frm01") };
    		jsonParam.param.gridData = $("#txt_jsonOutput").val();
    		
    		
    		var url = "${pageContext.request.contextPath}/api/za/zaa/saveZ01IOTList.do";
			duDialog(null,APPz.getMessage("C","confirm.DoProcess","저장"), {
				buttons: duDialog.OK_CANCEL,
				callbacks: {
					okClick:function(e){
						this.hide()
						APPz.cmn.ApiRequest(url,jsonParam,true,function(result){//메뉴저장
							
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
		<canvas id="factoryCanvas" width="1000" height="600"></canvas>
   		<textarea id="txt_jsonOutput" name="txt_jsonOutput" style="width: 100%;height: 200px;">
		</textarea>
		</div>
	</div>
	<!-- //(content) -->

</div>
<!-- //(wrapper) -->

</form>
</body>
</html>