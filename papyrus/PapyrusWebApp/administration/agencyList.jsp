<%@ page language="java" contentType="text/html;charset=iso-8859-1" %>

<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>

<jsp:useBean id="agencyListBean" class="com.eurocash.data.ItemListBean" scope="session" />
<jsp:useBean id="agencyListForm" class="com.eurocash.data.mapping.form.FormMappingObject" scope="session" />
<jsp:useBean id="employeeBean" class="com.eurocash.data.administration.employee.EmployeeBean" scope="session" />
<jsp:useBean id="agenciesBean" class="com.eurocash.data.administration.agency.AgenciesBean" scope="application" />


<?xml version="1" encoding="ISO-8859-1"?>
<!DOCTYPE html PUBLIC "- //w3c//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml11/DTD/xhtml11-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Liste des agences</title>
		<link href="/include/style/styleList.css" rel="stylesheet" type="text/css">
		<link href="/include/style/global.css" rel="stylesheet" type="text/css">
		<link href="/include/style/header.css" rel="stylesheet" type="text/css">
		
		<!-- create dynamic menu -->
		<c:import url="/include/jsp/menu.jsp" />
	
	</head>
	
	<body>
	
		<!-- display header : company logo + menu + user info -->
		<c:import url="/include/jsp/header.jsp" />
	
		<form name="listForm" action="DomainActionServlet" method="POST">
			<input type="hidden" name="domain" value="agency">
			<input type="hidden" name="action">
			<input type="hidden" name="subAction">
			<input type="hidden" name="id">
	
		<br>
				
		<h1 class="title">Agences&nbsp;&nbsp;:&nbsp;:&nbsp;&nbsp;Liste</h1>
		
		<div class="boxTitle">&nbsp;&nbsp;Recherche par: </div>
		<div class="boxCol">
			<p class="boxSearch">Nom:&nbsp;<input type="text" name="name" length="30" value="<c:out value="${agencyListForm.htmlData['name']}" />"></p>
		</div>
		
		
		<div class="boxTitle">&nbsp;&nbsp;Actions:</div>
		<div class="boxCol">
			<p class="boxAction"><a class="classicButtonBlue" onclick="document.listForm.action.value='list'; document.listForm.subAction.value='init'; document.listForm.submit();">&nbsp;[ rechercher ]&nbsp;</a></p>
			<p class="boxAction"><a class="classicButtonBlue" onclick="document.listForm.action.value='add'; document.listForm.subAction.value='init'; document.listForm.submit();">&nbsp;[ ajouter ]&nbsp;</a></p>
			<p class="boxAction"><a class="classicButtonBlue" onclick="">&nbsp;[ supprimer ]&nbsp;</a></p>
		</div>
		
		<div class="boxResults">
			<table border="0" width="100%" cellpadding="0px" cellspacing="0px"> 
				<tr>
					<th width="2%">&nbsp;</th>
					<th width="20%">Agence</th>
					<th width="30%">Adresse</th>
					<th width="10%">T�l�phone</th>
					<th width="10%">Fax</th>
					<th width="10%">Email</th>
					<th width="10%">Nombre employ�s</th>
					<th width="8%">Actions</th>
				</tr>
<c:choose>
	<c:when test="${empty agencyListBean.list}">
				<tr class="noresult">
					<td colspan="8">Aucun r�sultat</td>
				</tr>
	</c:when>
	<c:otherwise>
		<c:forEach var="agency" items="${agencyListBean.list}" varStatus="status">
			<c:if test="${(status.index + 1) % 2 == 0}" > 
				<tr class="result_par">
			</c:if>
			<c:if test="${(status.index + 1) % 2 != 0}" >
				<tr class="result_odd">
			</c:if>
					<td><input type="checkbox" value="<c:out value="${agency.id}" />"></td>
					<td><c:out value="${agency.company}" /></td>
					<td><c:choose>
        					<c:when test='${agency.address == null && agency.postalCode == null && agency.city == null}'>N/A</c:when>
       						<c:otherwise>
       							<c:out value="${agency.address}" />
       							<c:out value="${agency.postalCode}" />
       							<c:out value="${agency.city}" />
           					</c:otherwise>
    					</c:choose>
					</td>
					<td><c:out value="${agency.phone}" default="N/A" /></td>
					<td><c:out value="${agency.fax}" default="N/A" /></td>
					<td><c:out value="${agency.email}" default="N/A" /></td>
					<td><c:out value="${agency.nbEmployees}" default="0" /></td>
					<td>&nbsp;
						<img src="/include/pictures/pencil.png" alt="Modifier" onclick="document.listForm.id.value='<c:out value="${agency.id}"/>'; document.listForm.action.value='update'; document.listForm.subAction.value='init'; document.listForm.submit();"/>&nbsp;&nbsp;
						<img src="/include/pictures/glass.png" alt="Voir d�tail">&nbsp;&nbsp;
						<img src="/include/pictures/trash.png" alt="Supprimer" onclick="document.listForm.id.value='<c:out value="${agency.id}"/>'; document.listForm.action.value='delete'; document.listForm.subAction.value='one'; document.listForm.submit();"/>
					</td>
				</tr>
			</c:forEach>
	</c:otherwise>
</c:choose>
			</table>
		</div>
		
		<!-- multipage navigation -->
		<c:import url="/include/jsp/multipage.jsp">
			<c:param name="domain" value="agency" />
			<c:param name="listBean" value="agencyListBean" />
		</c:import>
		
		</form>
	<body>
	
</html>