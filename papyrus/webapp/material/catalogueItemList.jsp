<%@ page language="java" contentType="text/html;charset=iso-8859-1" %>

<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>

<jsp:useBean id="catalogueItemListBean" class="com.papyrus.data.ItemListBean" scope="session" />
<jsp:useBean id="catalogueItemListForm" class="com.papyrus.data.mapping.form.FormMappingObject" scope="session" />
<jsp:useBean id="agenciesBean" class="com.papyrus.data.administration.agency.AgenciesBean" scope="application" />
<jsp:useBean id="typeListBean" class="com.papyrus.data.type.TypeListBean" scope="application" />

<?xml version="1" encoding="ISO-8859-1"?>
<!DOCTYPE html PUBLIC "- //w3c//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml11/DTD/xhtml11-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Liste catalogue r�f�rences</title>
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
			<input type="hidden" name="domain" value="catalogueItem">
			<input type="hidden" name="action" value="list">
			<input type="hidden" name="subAction" value="init">
			<input type="hidden" name="id">
	
		<br>
				
		<h1 class="title">Catalogue R�f�rences&nbsp;&nbsp;:&nbsp;:&nbsp;&nbsp;Liste</h1>
		
		<div class="boxTitle">&nbsp;&nbsp;Recherche par: </div>
		<div class="boxCol">
		
			<div class="boxSearch">
				<p class="boxField">R�f�rence:</p>
				<p class="boxInput"><input type="text" name="reference" length="30" value="<c:out value="${catalogueItemListForm.htmlData['reference']}" />"></p>
			</div>
		
			<div class="boxSearch">
				<p class="boxField">D�signation:</p>
				<p class="boxInput"><input type="text" name="designation" length="30" value="<c:out value="${catalogueItemListForm.htmlData['designation']}" />"></p>
			</div>
		
			<div class="boxSearch">
				<p class="boxField">Marque:</p>
				<p class="boxInput">
					<select name="brandId">
						<option value="-1" <c:if test="${catalogueItemListForm.htmlData['brandId'] == -1}">SELECTED</c:if>>Toutes les marques</option>
						<c:forEach var="attribute" items="${typeListBean.hashMap['brand']}" varStatus="status">
						<option value="<c:out value="${attribute.key}"/>" <c:if test="${attribute.key == catalogueItemListForm.htmlData['brandId']}">SELECTED</c:if>><c:out value="${attribute.value}" /></option>
						</c:forEach>
					</select>
				</p>
			</div>
		
			<div class="boxSearch">
				<p class="boxField">Cat�gorie:</p>
				<p class="boxInput">
					<select name="categoryId">
						<option value="-1" <c:if test="${catalogueItemListForm.htmlData['categoryId'] == -1}">SELECTED</c:if>>Toutes les cat�gories</option>
						<c:forEach var="attribute" items="${typeListBean.hashMap['catalogue_category']}" varStatus="status">
						<option value="<c:out value="${attribute.key}"/>" <c:if test="${attribute.key == catalogueItemListForm.htmlData['categoryId']}">SELECTED</c:if>><c:out value="${attribute.value}" /></option>
						</c:forEach>
					</select>
				</p>
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
					<th width="13%">R�f�rence</th>
					<th width="32%">D�signation</th>
					<th width="17%">Marque</th>
					<th width="20%">Cat�gorie</th>
					<th width="8%">Type</th>
					<th width="8%">Actions</th>
				</tr>
<c:choose>
	<c:when test="${empty catalogueItemListBean.list}">
				<tr class="noresult">
					<td colspan="7">Aucun r�sultat</td>
				</tr>
	</c:when>
	<c:otherwise>
				<c:forEach var="catalogueItem" items="${catalogueItemListBean.list}" varStatus="status">
				<!-- catalogue id <c:out value="${catalogue.id}" /> -->
					<c:if test="${(status.index + 1) % 2 == 0}" > 
				<tr class="result_par">
					</c:if>
					<c:if test="${(status.index + 1) % 2 != 0}" >
				<tr class="result_odd">
					</c:if>
					<td><input type="checkbox" value="<c:out value="${catalogueItem.id}" />"></td>
					<td><c:out value="${catalogueItem.reference}" /></td>
					<td><c:out value="${catalogueItem.designation}" default="N/A" /></td>
					<td>
						<c:forEach var="attribute" items="${typeListBean.hashMap['brand']}" varStatus="status">
							<c:if test="${attribute.key == catalogueItem.brandId}"><c:out value="${attribute.value}" /></c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach var="attribute" items="${typeListBean.hashMap['catalogue_category']}" varStatus="status">
							<c:if test="${attribute.key == catalogueItem.categoryId}"><c:out value="${attribute.value}" /></c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach var="attribute" items="${typeListBean.hashMap['catalogue_type']}" varStatus="status">
							<c:if test="${attribute.key == catalogueItem.typeId}"><c:out value="${attribute.value}" /></c:if>
						</c:forEach>
					</td>
					<td>&nbsp;
						<img src="/include/pictures/pencil.png" alt="Modifier" onclick="document.listForm.id.value='<c:out value="${catalogueItem.id}"/>'; document.listForm.action.value='update'; document.listForm.subAction.value='init'; document.listForm.submit();"/>&nbsp;&nbsp;
						<img src="/include/pictures/glass.png" alt="Voir d�tail">&nbsp;&nbsp;
						<img src="/include/pictures/trash.png" alt="Supprimer" onclick="document.listForm.id.value='<c:out value="${catalogueItem.id}"/>'; document.listForm.action.value='delete'; document.listForm.subAction.value='one'; document.listForm.submit();"/>
					</td>
				</tr>
				</c:forEach>
	</c:otherwise>
</c:choose>
			</table>
		</div>
		
		<!-- multipage navigation -->
		<c:import url="/include/jsp/multipage.jsp">
			<c:param name="domain" value="catalogueItem" />
			<c:param name="listBean" value="catalogueItemListBean" />
		</c:import>
		
		</form>
	<body>
	
</html>