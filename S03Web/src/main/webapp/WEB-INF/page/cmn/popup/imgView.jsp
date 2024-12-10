<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
let cmmonCdList; 
let gs_currentRow;
function retName(nm)  { return eval("frm01." + nm); }
function retValue(nm) { return eval("frm01." + nm + ".value"); }
function retFocus(nm) { return eval("frm01." + nm + ".focus()"); }
$(document).ready(function(){
	
	$('#S_OEPARTNO').val('${list[0].OEPARTNO}');
	$('#S_ITEMNAME').val('${list[0].ITEMNAME}');
});

function goBack(){
	alert('1');
	history.back();
}
    </script>
    <style>
        body {
            font-family: 'Raleway', sans-serif;
            font-size: 20px;
            line-height: 34px;
        }

        * {
            box-sizing: border-box;
        }

        .container .gallery a img {
            float: left;
            width: 25%;
            height: auto;
            border: 2px solid #fff;
            -webkit-transition: -webkit-transform .15s ease;
            -moz-transition: -moz-transform .15s ease;
            -o-transition: -o-transform .15s ease;
            -ms-transition: -ms-transform .15s ease;
            transition: transform .15s ease;
            position: relative;
        }

        .clear {
            clear: both;
        }

        a {
            color: #009688;
            text-decoration: none;
        }

        a:hover {
            color: #01695f;
            text-decoration: none;
        }
    </style>    
<form id="frm01" name="frm01" method="post">
 <div class="container">
 
                    <div class="search"> 
                    <table>
                        <colgroup>
                            <col style="width:8rem">
                            <col>
                            <col style="width:8rem">
                            <col>
                            <col style="width:8rem">
                            <col>
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>메이커</th>
                                <td>
                                    <select name="S_MAKERID" id="S_MAKERID">
                                        <option value="">선택</option>
                                    </select>
                                </td>
                                <th>차종</th>
                                <td>	                
                                	<select class="outbox3"  id="S_CARID" name="S_CARID" style="width:100%;">
										<option value="">선택</option>
									</select>
								</td>
                                <th>제품군</th>
                                <td>
                                    <select name="S_GROUPID" id="S_GROUPID">
                                        <option value="">선택</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th>OE PartNo</th>
                                <td>
                                	<input type="text" name="S_OEPARTNO" id="S_OEPARTNO">
                                </td>
                                <th>제품명</th>
                                <td colspan="3">
                                    <div class="form-search">
                                        <input type="text"  name="S_ITEMNAME" id="S_ITEMNAME" class="w100">
                                        <button class="btn white regular" onclick="javascript:window.history.back();return false;">뒤로</button>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
    <div class="gallery">
       	<c:forEach items="${list}" var="list">
       		<a href='${pageContext.request.contextPath}/file/GetImgVw.do?filePath=${list.LINK_IMG }' class="big"><img src='${pageContext.request.contextPath}/file/GetImgVw.do?filePath=${list.LINK_TMNAIL_IMG }' alt="" title="" /></a>
		</c:forEach>
    </div>
<script src="${pageContext.request.contextPath}/dist/simple-lightbox.js?v2.11.0"></script>
<script>
    (function() {
        var $gallery = new SimpleLightbox('.gallery a', {});
    })();
</script>    
</div>
</form>