<%@ page language="java" contentType="text/html;charset=iso-8859-1" %>

<%@ page import="com.papyrus.data.ItemListBean" %>

<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%
	/* get the request attribute corresponding to the current itemFormBean */
	String nameBean = request.getParameter("listBean");
	ItemListBean listBean = null;
	
	if (null != nameBean)
		listBean = (ItemListBean) session.getAttribute(nameBean);
	else
		return;
		
	/* put it into the current context */
	pageContext.setAttribute("listBean", listBean);
	pageContext.setAttribute("domain", request.getParameter("domain"));
%>

<c:if test="${not empty listBean.list}">
<br><br>
<!-- number of results = <c:out value="${listBean.nbResults}" /> 
<div class="navigationBox">
	&nbsp;
	<c:if test="${true == listBean.previousPageOK}" >
		<img src="/include/pictures/arrow_previous.png" alt="Page Précédente" onclick="window.location.href='/DomainActionServlet?domain=<c:out value="${domain}" />&action=list&subAction=goto&pageIndex=<c:out value="${listBean.currentPageIndex - 1}" />';">
	</c:if>
</div>
-->

<div class="navigationBoxMiddle">
	&nbsp;
	<c:if test="${true == listBean.previousPageOK || true == listBean.nextPageOK}" >
		<c:if test="${true == listBean.previousPageOK}" >
			<img src="/include/pictures/arrow_first_nav.png" alt="Première Page" onclick="window.location.href='/DomainActionServlet?domain=<c:out value="${domain}" />&action=list&subAction=goto&pageIndex=1';">
			<img src="/include/pictures/arrow_backward_nav.png" alt="Page Précédente" onclick="window.location.href='/DomainActionServlet?domain=<c:out value="${domain}" />&action=list&subAction=goto&pageIndex=<c:out value="${listBean.currentPageIndex - 1}" />';">
		</c:if>
		
		&nbsp;&nbsp;&nbsp;Page&nbsp;
		<input type="int" name="currentPageIndex" maxlength="3" size="3" value="<c:out value="${listBean.currentPageIndex}" />">&nbsp;sur&nbsp;<c:out value="${listBean.maxNbPages}" />
		&nbsp;&nbsp;&nbsp;
		<!--
		<a class="classicButtonGreen" onclick="window.location.href='/DomainActionServlet?domain=<c:out value="${domain}"/>&action=list&subAction=goto&pageIndex=' + document.listForm.currentPageIndex.value;">&nbsp;OK&nbsp;</a>
		&nbsp;&nbsp;
		-->
		
		<c:if test="${true == listBean.nextPageOK}" >
			<img src="/include/pictures/arrow_forward_nav.png" alt="Page suivante" onclick="window.location.href='/DomainActionServlet?domain=<c:out value="${domain}"/>&action=list&subAction=goto&pageIndex=<c:out value="${listBean.currentPageIndex + 1}" />';">
			<img src="/include/pictures/arrow_last_nav.png" alt="Dernière page" onclick="window.location.href='/DomainActionServlet?domain=<c:out value="${domain}"/>&action=list&subAction=goto&pageIndex=<c:out value="${listBean.maxNbPages}" />';">
		</c:if>
	</c:if>
</div>

<!--
<div class="navigationBox">
	&nbsp;
	<c:if test="${true == listBean.nextPageOK}" >
		<img src="/include/pictures/arrow_next.png" alt="Page suivante" onclick="window.location.href='/DomainActionServlet?domain=<c:out value="${domain}"/>&action=list&subAction=goto&pageIndex=<c:out value="${listBean.currentPageIndex + 1}" />';">
	</c:if>
</div>
-->

</c:if>