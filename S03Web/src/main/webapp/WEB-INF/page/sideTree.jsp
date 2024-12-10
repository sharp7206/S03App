<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
       		<nav class="m-1">
        		<ul id="menu_root" class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
	        		<c:set var="_privMenulvl" value="0"/>
	        		<c:set var="_tagOpenCnt" value="0"/>
	        		<c:set var="_data_cnt" value="${ fn:length(userMenu) }"/>
	         		<c:forEach var="menu" items="${ userMenu }">
	         			<c:if test="${ _privMenulvl > menu.menulvl }">
	         				<c:forEach begin="0" end="${ _privMenulvl - menu.menulvl}" step="1">
	         					<c:if test="${_tagOpenCnt > 0 }">
									</ul></li>
									<c:set var="_tagOpenCnt" value="${ _tagOpenCnt-1 }"/>
								</c:if>
							</c:forEach>
	         			</c:if>
	         			<c:choose>
		         			<c:when test="${ fn:length(menu.menucd ) eq 2 }">
		         				<li class="nav-header"><b><font class="text-info" size="3"> <i class="nav-icon ${menu.iconnm}"></i> ${menu.menunm}</font></b></li>
		         			</c:when>
		         			<c:otherwise>
		         				<li class="nav-item">
		         				<c:choose>
		         					<c:when test="${ not empty menu.viewurl }">
		         						<a href="${pageContext.request.contextPath}${ menu.viewurl }" class="nav-link">
		         					</c:when>
		         					<c:otherwise>
		         						<a href="#" class="nav-link">
		         					</c:otherwise>
		         				</c:choose>
		         				<i class="nav-icon ${ menu.iconnm }"></i>
		         				<p>${ menu.menunm }</p>
		         				<c:choose>
		         					<c:when test="${ menu.leafyn eq 'N' }">
		         						<i class="fas fa-angle-left right"></i></a><ul class="nav nav-treeview">
		         						<c:set var="_tagOpenCnt" value="${ _tagOpenCnt+1 }"/>
		         					</c:when>
		         					<c:otherwise>
		         						</a><c:if test="${ status.index+1 ne _data_cnt }"></li></c:if>
		         					</c:otherwise>
		         				</c:choose>
		         			</c:otherwise>
	         			</c:choose>
	         			<c:if test="${ status.index+1 eq _data_cnt }">
	         				<c:forEach begin="1" end="${ _tagOpenCnt }" step="1">
								</ul></li>
							</c:forEach>
							<c:set var="_tagOpenCnt" value="0"/>
	         				</li>
	         			</c:if>
	         			<c:set var="_privMenulvl" value="${ menu.menulvl }"/>
	      			</c:forEach>
        		</ul>
      		</nav>