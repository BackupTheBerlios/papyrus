<%@ page language="java" contentType="text/html;charset=iso-8859-1" %>

<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>

<jsp:useBean id="employeeBean" class="com.papyrus.data.administration.employee.EmployeeBean" scope="session" />
<jsp:useBean id="agenciesBean" class="com.papyrus.data.administration.agency.AgenciesBean" scope="application" />
<jsp:useBean id="typeListBean" class="com.papyrus.data.type.TypeListBean" scope="application" />
<jsp:useBean id="employeeAddForm" class="com.papyrus.data.mapping.form.FormMappingObject" scope="session" />

<?xml version="1" encoding="ISO-8859-1"?>
<!DOCTYPE html PUBLIC "- //w3c//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml11/DTD/xhtml11-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Ajout employé</title>
		<link href="/include/style/styleAdd.css" rel="stylesheet" type="text/css">
		<link href="/include/style/global.css" rel="stylesheet" type="text/css">
		<link href="/include/style/header.css" rel="stylesheet" type="text/css">
		
		<!-- create dynamic menu -->
		<c:import url="/include/jsp/menu.jsp" />
	
	</head>
	
	<body>
	
		<!-- display header : company logo + menu + user info -->
		<c:import url="/include/jsp/header.jsp" />
	
		<form name="addForm" action="DomainActionServlet" method="POST">
			<input type="hidden" name="domain" value="employee">
			<input type="hidden" name="action" value="add">
			<input type="hidden" name="subAction" value="ok">
	
		<br>
				
		<h1 class="title">Employés&nbsp;&nbsp;:&nbsp;:&nbsp;&nbsp;Ajout</h1>
		
		<div class="boxTitle">&nbsp;&nbsp;Identité: </div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Civilité :</p>
				<p class="boxInput">
					<select name="civilityId">
						<c:forEach var="attribute" items="${typeListBean.hashMap['civility']}" varStatus="status">
						<option value="<c:out value="${attribute.key}"/>" <c:if test="${attribute.key == employeeAddForm.htmlData['civilityId']}">SELECTED</c:if>><c:out value="${attribute.value}" /></option>
						</c:forEach>
					</select>
				</p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Nom :</p>
				<p class="boxInput"><input type="text" name="lastName" value="<c:out value="${employeeAddForm.htmlData['lastName']}" />"></p>
				<c:if test="${not empty employeeAddForm.errorFields['lastName']}" >
					<p class="boxError"><img src="/include/pictures/warning.png">&nbsp;&nbsp;&nbsp;<c:out value="${employeeAddForm.errorFields['lastName']}" /></p>
				</c:if>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Prénom :</p>
				<p class="boxInput"><input type="text" name="firstName" value="<c:out value="${employeeAddForm.htmlData['firstName']}" />"></p>
				<c:if test="${not empty employeeAddForm.errorFields['firstName']}" >
					<p class="boxError"><img src="/include/pictures/warning.png">&nbsp;&nbsp;&nbsp;<c:out value="${employeeAddForm.errorFields['firstName']}" /></p>
				</c:if>
			</div>
		</div>
		
		<div class="boxTitle">&nbsp;&nbsp;Coordonnées: </div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Adresse :</p>
				<p class="boxInput"><input type="text" name="address" value="<c:out value="${employeeAddForm.htmlData['address']}" />"></p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Ville :</p>
				<p class="boxInput"><input type="text" name="city" value="<c:out value="${employeeAddForm.htmlData['city']}" />"></p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Code Postal :</p>
				<p class="boxInput"><input type="text" name="postalCode" value="<c:out value="${employeeAddForm.htmlData['postalCode']}" />"></p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Téléphone :</p>
				<p class="boxInput"><input type="text" name="phone" value="<c:out value="${employeeAddForm.htmlData['phone']}" />"></p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Portable :</p>
				<p class="boxInput"><input type="text" name="cellPhone" value="<c:out value="${employeeAddForm.htmlData['cellPhone']}" />"></p>
			</div>
		</div>
		
		<div class="boxTitle">&nbsp;&nbsp;Accès: </div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Agence :</p>
				<p class="boxInput">
					<select name="agencyId" >
						<c:forEach var="agency" items="${agenciesBean.agenciesList}" varStatus="status">
						<option value="<c:out value="${agency.id}" />" <c:if test="${agency.id == employeeListForm.htmlData['agencyId']}"><c:out value="SELECTED" /></c:if>><c:out value="${agency.company}" /></option>
						</c:forEach>
					</select>
				</p>
			</div>		
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;login :</p>
				<p class="boxInput"><input type="text" name="login" value="<c:out value="${employeeAddForm.htmlData['login']}" />"></p>
				<c:if test="${not empty employeeAddForm.errorFields['login']}" >
					<p class="boxError"><img src="/include/pictures/warning.png">&nbsp;&nbsp;&nbsp;<c:out value="${employeeAddForm.errorFields['login']}" /></p>
				</c:if>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;password :</p>
				<p class="boxInput"><input type="text" name="password" value="<c:out value="${employeeAddForm.htmlData['password']}" />"></p>
				<c:if test="${not empty employeeAddForm.errorFields['password']}" >
					<p class="boxError"><img src="/include/pictures/warning.png">&nbsp;&nbsp;&nbsp;<c:out value="${employeeAddForm.errorFields['password']}" /></p>
				</c:if>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;droits :</p>
				<p class="boxInput">
					<select name="rightsId">
						<c:forEach var="attribute" items="${typeListBean.hashMap['rights']}" varStatus="status">
						<option value="<c:out value="${attribute.key}" />" <c:if test="${attribute.key == employeeAddForm.htmlData['rightsId']}">SELECTED</c:if>><c:out value="${attribute.value}" /></option>
						</c:forEach>
					</select>
				</p>
			</div>
			
			
		</div>
		
		<div class="boxAction">
			<a class="classicButtonGreen" onclick="addForm.submit();">&nbsp;Valider&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="classicButtonRed" onclick="window.location.href = '/DomainActionServlet?domain=employee&action=list&subAction=init';">&nbsp;Annuler&nbsp;</a>
		</div>
		
		</form>
	</body>
</html>