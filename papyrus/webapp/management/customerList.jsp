<%@ page language="java" contentType="text/html;charset=iso-8859-1" import="com.papyrus.data.management.customer.CustomerBean" %>

<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>

<jsp:useBean id="customerListBean" class="com.papyrus.data.ItemListBean" scope="session" />
<jsp:useBean id="customerListForm" class="com.papyrus.data.mapping.form.FormMappingObject" scope="session" />
<jsp:useBean id="agenciesBean" class="com.papyrus.data.administration.agency.AgenciesBean" scope="application" />

<% 
	pageContext.setAttribute("STE_CIVILITY", new Integer(CustomerBean.STE_CIVILITY)); 
	pageContext.setAttribute("SARL_CIVILITY", new Integer(CustomerBean.SARL_CIVILITY));
	pageContext.setAttribute("SCI_CIVILITY", new Integer(CustomerBean.SCI_CIVILITY));
%>

<?xml version="1" encoding="ISO-8859-1"?>
<!DOCTYPE html PUBLIC "- //w3c//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml11/DTD/xhtml11-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Liste des clients</title>
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
			<input type="hidden" name="domain" value="customer">
			<input type="hidden" name="action">
			<input type="hidden" name="subAction">
			<input type="hidden" name="id">
	
		<br>
				
		<h1 class="title">Clients&nbsp;&nbsp;:&nbsp;:&nbsp;&nbsp;Liste</h1>
		
		<div class="boxTitle">&nbsp;&nbsp;Recherche par: </div>
		<div class="boxCol">
			
			<div class="boxSearch">
				<p class="boxField">Agence:</p>
				<p class="boxInput">
					<select name="agencyId" >
						<option value="0" <c:if test="${0 == customerListForm.htmlData['agencyId']}"><c:out value="SELECTED" /></c:if>>Toutes</option>
						<c:forEach var="agency" items="${agenciesBean.agenciesList}" varStatus="status">
						<option value="<c:out value="${agency.id}" />" <c:if test="${agency.id == customerListForm.htmlData['agencyId']}"><c:out value="SELECTED" /></c:if>><c:out value="${agency.company}" /></option>
						</c:forEach>
					</select>
				</p>
			</div>
			
			<div class="boxSearch">
				<p class="boxField">Code client:</p>
				<p class="boxInput"><input type="text" name="customerCode" length="30" value="<c:out value="${customerListForm.htmlData['customerCode']}" />"></p>
			</div>
			
			<div class="boxSearch">
				<p class="boxField">Nom:</p>
				<p class="boxInput"><input type="text" name="name" length="30" value="<c:out value="${customerListForm.htmlData['name']}" />"></p>
			</div>
			
			<div class="boxSearch">
				<p class="boxField">Adresse:</p>
				<p class="boxInput"><input type="text" name="address" length="30" value="<c:out value="${customerListForm.htmlData['address']}" />"></p>
			</div>
			
			<div class="boxSearch">
				<p class="boxField">Ville:</p>
				<p class="boxInput"><input type="text" name="city" length="30" value="<c:out value="${customerListForm.htmlData['city']}" />"></p>
			</div>
			
			<div class="boxSearch">
				<p class="boxField">Code Postal:</p>
				<p class="boxInput"><input type="text" name="postalCode" length="30" value="<c:out value="${customerListForm.htmlData['postalCode']}" />"></p>
			</div>
			
			<div class="boxSearch">
				<p class="boxField">Téléphone:</p>
				<p class="boxInput"><input type="text" name="phone" length="30" value="<c:out value="${customerListForm.htmlData['phone']}" />"></p>
			</div>
			
			<div class="boxSearch">
				<p class="boxField">Portable:</p>
				<p class="boxInput"><input type="text" name="cellPhone" length="30" value="<c:out value="${customerListForm.htmlData['cellPhone']}" />"></p>
			</div>
			
			<div class="spacer">&nbsp;</div>
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
					<th width="13%">Agence</th>
					<th width="6%">Code</th>
					<th width="25%">Nom</th>
					<th width="30%">Adresse</th>
					<th width="8%">Téléphone</th>
					<th width="8%">Portable</th>
					<th width="8%">Actions</th>
				</tr>
<c:choose>
	<c:when test="${empty customerListBean.list}">
				<tr class="noresult">
					<td colspan="7">Aucun résultat</td>
				</tr>
	</c:when>
	<c:otherwise>
				<c:forEach var="customer" items="${customerListBean.list}" varStatus="status">
				<!-- customer id <c:out value="${customer.id}" /> -->
					<c:if test="${(status.index + 1) % 2 == 0}" > 
				<tr class="result_par">
					</c:if>
					<c:if test="${(status.index + 1) % 2 != 0}" >
				<tr class="result_odd">
					</c:if>
					<td><input type="checkbox" value="<c:out value="${customer.id}" />"></td>
					<td><c:out value="${agenciesBean.agenciesMap[customer.agencyId].company}" /></td>
					<td><c:out value="${customer.code}" default="N/A" /></td>
					<td><c:choose>
							<c:when test="${customer.civilityId == STE_CIVILITY || customer.civilityId == SCI_CIVILITY || customer.civilityId == SARL_CIVILITY}">
								<c:out value="${customer.company}" />
							</c:when>
							<c:otherwise>
								<c:out value="${customer.lastName}" />&nbsp;<c:out value="${customer.firstName}" /></td>
							</c:otherwise>
						</c:choose>
					<td><c:choose>
        					<c:when test='${customer.address == null && customer.postalCode == null && customer.city == null}'>N/A</c:when>
       						<c:otherwise>
       							<c:out value="${customer.address}" />
       							<c:out value="${customer.postalCode}" />
       							<c:out value="${customer.city}" />
           					</c:otherwise>
    					</c:choose>
					</td>
					<td><c:out value="${customer.phone}" default="N/A" /></td>
					<td><c:out value="${customer.cellPhone}" default="N/A" /></td>
					<td>&nbsp;
						<img src="/include/pictures/pencil.png" alt="Modifier" onclick="document.listForm.id.value='<c:out value="${customer.id}"/>'; document.listForm.action.value='update'; document.listForm.subAction.value='init'; document.listForm.submit();"/>&nbsp;&nbsp;
						<img src="/include/pictures/glass.png" alt="Voir détail">&nbsp;&nbsp;
						<img src="/include/pictures/trash.png" alt="Supprimer" onclick="document.listForm.id.value='<c:out value="${customer.id}"/>'; document.listForm.action.value='delete'; document.listForm.subAction.value='one'; document.listForm.submit();"/>
					</td>
				</tr>
				</c:forEach>
	</c:otherwise>
</c:choose>
			</table>
		</div>
		
		<!-- multipage navigation -->
		<c:import url="/include/jsp/multipage.jsp">
			<c:param name="domain" value="customer" />
			<c:param name="listBean" value="customerListBean" />
		</c:import>
		
		</form>
	<body>
	
</html>