<%@ page language="java" contentType="text/html;charset=iso-8859-1" import="com.eurocash.data.ItemListBean" %>
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
<!-- number of results = <c:out value="${listBean.nbResults}" /> -->
<div class="navigationBox">
	&nbsp;
	<c:if test="${true == listBean.previousPageOK}" >
		<img src="/include/pictures/arrow_previous.png" alt="Page Précédente" onclick="window.location.href='/DomainActionServlet?domain=<c:out value="${domain}" />&action=list&subAction=goto&pageIndex=<c:out value="${listBean.currentPageIndex - 1}" />';">
	</c:if>
</div>
<div class="navigationBoxMiddle">
	&nbsp;
	<c:if test="${true == listBean.previousPageOK || true == listBean.nextPageOK}" >
		<input type="int" name="currentPageIndex" maxlength="3" size="4" value="<c:out value="${listBean.currentPageIndex}" />">&nbsp;/<c:out value="${listBean.maxNbPages}" />&nbsp;&nbsp;&nbsp;
		<a class="classicButtonGreen" onclick="window.location.href='/DomainActionServlet?domain=<c:out value="${domain}"/>&action=list&subAction=goto&pageIndex=' + document.listForm.currentPageIndex.value;">&nbsp;OK&nbsp;</a>
	</c:if>
</div>
<div class="navigationBox">
	&nbsp;
	<c:if test="${true == listBean.nextPageOK}" >
		<img src="/include/pictures/arrow_next.png" alt="Page suivante" onclick="window.location.href='/DomainActionServlet?domain=<c:out value="${domain}"/>&action=list&subAction=goto&pageIndex=<c:out value="${listBean.currentPageIndex + 1}" />';">
	</c:if>
</div>
</c:if>