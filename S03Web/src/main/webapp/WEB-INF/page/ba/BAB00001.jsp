<%--
 -===============================================================================================================
 - 아래 프로그램에 대한 저작권을 포함한 지적재산권은 APPz에 있으며,
 - APPz가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
 - APPz의 지적재산권 침해에 해당됩니다.
 - (Copyright ⓒ APPz Co., Ltd. All Rights Reserved| Confidential)
 -===============================================================================================================
 - 시스템관리 - 보안관리 - 사용자관리
 -===============================================================================================================
  - 2022/08/19 - 이호성 - 최종수정
 -===============================================================================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sys" uri="/WEB-INF/tld/systld.tld"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>공장 설비 모니터링</title>
    <style>
        canvas {
            border: 1px solid black;
        }
    </style>
</head>
<body>
    <canvas id="factoryCanvas" width="800" height="600"></canvas>
    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const canvas = document.getElementById('factoryCanvas');
            const ctx = canvas.getContext('2d');

            const factoryPlan = new Image();
            factoryPlan.src = '/s03/res/img/factory-plan.png';

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
</body>
</html>
