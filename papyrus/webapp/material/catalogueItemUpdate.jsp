<%@ page language="java" contentType="text/html;charset=iso-8859-1" %>

<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>

<jsp:useBean id="agenciesBean" class="com.papyrus.data.administration.agency.AgenciesBean" scope="application" />
<jsp:useBean id="typeListBean" class="com.papyrus.data.type.TypeListBean" scope="application" />
<jsp:useBean id="catalogueItemUpdateForm" class="com.papyrus.data.mapping.form.FormMappingObject" scope="session" />

<?xml version="1" encoding="ISO-8859-1"?>
<!DOCTYPE html PUBLIC "- //w3c//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml11/DTD/xhtml11-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Modification Catalogue Référence</title>
		<link href="/include/style/styleAdd.css" rel="stylesheet" type="text/css">
		<link href="/include/style/global.css" rel="stylesheet" type="text/css">
		<link href="/include/style/header.css" rel="stylesheet" type="text/css">
		
		<script src="/include/scripts/global.js"></script>
		
		<!-- create dynamic menu -->
		<c:import url="/include/jsp/menu.jsp" />
	
	</head>
	
	<body>
	
		<!-- display header : company logo + menu + user info -->
		<c:import url="/include/jsp/header.jsp" />
	
		<form name="updateForm" action="DomainActionServlet" method="POST">
			<input type="hidden" name="domain" value="catalogueItem">
			<input type="hidden" name="action" value="update">
			<input type="hidden" name="subAction" value="ok">
			
			<input type="hidden" name="id" value="<c:out value="${catalogueItemUpdateForm.htmlData['id']}" />">
	
		<br>
				
		<h1 class="title">Catalogue Références&nbsp;&nbsp;:&nbsp;:&nbsp;&nbsp;Modification</h1>
		
		<div class="boxTitle">&nbsp;&nbsp;Informations: </div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Référence :</p>
				<p class="boxInput"><input type="text" name="reference" value="<c:out value="${catalogueItemUpdateForm.htmlData['reference']}" />"></p>
				<c:if test="${not empty catalogueItemUpdateForm.errorFields['reference']}" >
					<p class="boxError"><img src="/include/pictures/warning.png">&nbsp;&nbsp;&nbsp;<c:out value="${catalogueItemUpdateForm.errorFields['reference']}" /></p>
				</c:if>
			</div>
			<div class="boxCol" id="firstNameBox">
				<p class="boxField">&nbsp;&nbsp;Désignation :</p>
				<p class="boxInput"><input type="text" name="designation" value="<c:out value="${catalogueItemUpdateForm.htmlData['designation']}" />"></p>
				<c:if test="${not empty catalogueItemUpdateForm.errorFields['designation']}" >
					<p class="boxError"><img src="/include/pictures/warning.png">&nbsp;&nbsp;&nbsp;<c:out value="${catalogueItemUpdateForm.errorFields['designation']}" /></p>
				</c:if>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Type :</p>
				<p class="boxInput">
					<select name="typeId">
						<c:forEach var="attribute" items="${typeListBean.hashMap['catalogue_type']}" varStatus="status">
						<option value="<c:out value="${attribute.key}"/>" <c:if test="${attribute.key == catalogueItemUpdateForm.htmlData['typeId']}">SELECTED</c:if>><c:out value="${attribute.value}" /></option>
						</c:forEach>
					</select>
				</p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Marque :</p>
				<p class="boxInput">
					<select name="brandId">
						<option value="0" <c:if test="${catalogueItemUpdateBean.htmlData['brandId'] == 0}">SELECTED</c:if>>Aucune</option>
						<c:forEach var="attribute" items="${typeListBean.hashMap['brand']}" varStatus="status">
						<option value="<c:out value="${attribute.key}"/>" <c:if test="${attribute.key == catalogueItemUpdateForm.htmlData['brandId']}">SELECTED</c:if>><c:out value="${attribute.value}" /></option>
						</c:forEach>
					</select>
				</p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Catégorie :</p>
				<p class="boxInput">
					<select name="categoryId">
						<option value="0" <c:if test="${catalogueItemUpdateBean.htmlData['categoryId'] == 0}">SELECTED</c:if>>Aucune</option>
						<c:forEach var="attribute" items="${typeListBean.hashMap['catalogue_category']}" varStatus="status">
						<option value="<c:out value="${attribute.key}"/>" <c:if test="${attribute.key == catalogueItemUpdateForm.htmlData['categoryId']}">SELECTED</c:if>><c:out value="${attribute.value}" /></option>
						</c:forEach>
					</select>
				</p>
			</div>
		
		<div class="boxAction">
			<a class="classicButtonGreen" onclick="updateForm.submit();">&nbsp;Valider&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="classicButtonRed" onclick="window.location.href = '/DomainActionServlet?domain=catalogueItem&action=list&subAction=init';">&nbsp;Annuler&nbsp;</a>
		</div>
		
		</form>
	</body>
</html>