<%@ page language="java" contentType="text/html;charset=iso-8859-1" %>

<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>

<jsp:useBean id="employeeBean" class="com.papyrus.data.administration.employee.EmployeeBean" scope="session" />
<jsp:useBean id="typeListBean" class="com.papyrus.data.type.TypeListBean" scope="application" />
<jsp:useBean id="agencyUpdateForm" class="com.papyrus.data.mapping.form.FormMappingObject" scope="session" />

<?xml version="1" encoding="ISO-8859-1"?>
<!DOCTYPE html PUBLIC "- //w3c//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml11/DTD/xhtml11-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Modification agence</title>
		<link href="/include/style/styleAdd.css" rel="stylesheet" type="text/css">
		<link href="/include/style/global.css" rel="stylesheet" type="text/css">
		<link href="/include/style/header.css" rel="stylesheet" type="text/css">
		
		<!-- create dynamic menu -->
		<c:import url="/include/jsp/menu.jsp" />
	
	</head>
	
	<body>
	
		<!-- display header : company logo + menu + user info -->
		<c:import url="/include/jsp/header.jsp" />
	
		<form name="UpdateForm" action="DomainActionServlet" method="POST">
			<input type="hidden" name="domain" value="agency">
			<input type="hidden" name="action" value="update">
			<input type="hidden" name="subAction" value="ok">
			<input type="hidden" name="id" value="<c:out value="${agencyUpdateForm.htmlData['id']}" />">
	
		<br>
				
		<h1 class="title">Agences&nbsp;&nbsp;:&nbsp;:&nbsp;&nbsp;Modification</h1>
		
		<div class="boxTitle">&nbsp;&nbsp;Informations principales: </div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Nom :</p>
				<p class="boxInput"><input type="text" name="company" value="<c:out value="${agencyUpdateForm.htmlData['company']}" />"></p>
				<c:if test="${not empty agencyUpdateForm.errorFields['company']}" >
					<p class="boxError"><img src="/include/pictures/warning.png">&nbsp;&nbsp;&nbsp;<c:out value="${agencyUpdateForm.errorFields['company']}" /></p>
				</c:if>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Responsable agence :</p>
				<p class="boxInput">
					<select name="employeeLeader" >
						<c:forEach var="employee" items="${agenciesBean.agenciesMap[agencyUpdateForm.htmlData['id']].employeesList}" varStatus="status">
						<option value="<c:out value="${employee.id}" />" <c:if test="${employee.id == agencyUpdateForm.htmlData['employeeLeader']}"><c:out value="SELECTED" /></c:if>><c:out value="${employee.name}" /></option>
						</c:forEach>
					</select>
				</p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Agence mère :</p>
				<p class="boxInput"><input type="checkbox" value="true" name="parentCompany" <c:if test="${true == agencyUpdateForm.htmlData['parentCompany']}">CHECKED</c:if>></p>
			</div>
		</div>
		
		<div class="boxTitle">&nbsp;&nbsp;Coordonnées: </div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Adresse :</p>
				<p class="boxInput"><input type="text" name="address" value="<c:out value="${agencyUpdateForm.htmlData['address']}" />"></p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Ville :</p>
				<p class="boxInput"><input type="text" name="city" value="<c:out value="${agencyUpdateForm.htmlData['city']}" />"></p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Code Postal :</p>
				<p class="boxInput"><input type="text" name="postalCode" value="<c:out value="${agencyUpdateForm.htmlData['postalCode']}" />"></p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Téléphone :</p>
				<p class="boxInput"><input type="text" name="phone" value="<c:out value="${agencyUpdateForm.htmlData['phone']}" />"></p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Fax :</p>
				<p class="boxInput"><input type="text" name="fax" value="<c:out value="${agencyUpdateForm.htmlData['fax']}" />"></p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Email :</p>
				<p class="boxInput"><input type="text" name="email" value="<c:out value="${agencyUpdateForm.htmlData['email']}" />"></p>
			</div>
		</div>
		
		<div class="boxTitle">&nbsp;&nbsp;Informations compta: </div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Code Agence :</p>
				<p class="boxInput"><input type="text" name="agencyCode" value="<c:out value="${agencyUpdateForm.htmlData['agencyCode']}" />"></p>
				<c:if test="${not empty agencyAddForm.errorFields['agencyCode']}" >
					<p class="boxError"><img src="/include/pictures/warning.png">&nbsp;&nbsp;&nbsp;<c:out value="${agencyAddForm.errorFields['agencyCode']}" /></p>
				</c:if>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Code Client :</p>
				<p class="boxInput"><input type="text" name="customerCode" value="<c:out value="${agencyUpdateForm.htmlData['customerCode']}" />"></p>
				<c:if test="${not empty agencyAddForm.errorFields['customerCode']}" >
					<p class="boxError"><img src="/include/pictures/warning.png">&nbsp;&nbsp;&nbsp;<c:out value="${agencyAddForm.errorFields['customerCode']}" /></p>
				</c:if>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Code Facture :</p>
				<p class="boxInput"><input type="text" name="billCode" value="<c:out value="${agencyUpdateForm.htmlData['billCode']}" />"></p>
				<c:if test="${not empty agencyAddForm.errorFields['billCode']}" >
					<p class="boxError"><img src="/include/pictures/warning.png">&nbsp;&nbsp;&nbsp;<c:out value="${agencyAddForm.errorFields['billCode']}" /></p>
				</c:if>
			</div>
		</div>	
			
		<div class="boxAction">
			<a class="classicButtonGreen" onclick="UpdateForm.submit();">&nbsp;Valider&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="classicButtonRed" onclick="window.location.href = '/DomainActionServlet?domain=agency&action=list&subAction=init';">&nbsp;Annuler&nbsp;</a>
		</div>
		
		</form>
	</body>
</html>