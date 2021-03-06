<%@ page language="java" contentType="text/html;charset=iso-8859-1" import="com.papyrus.data.management.customer.CustomerBean" %>

<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>

<jsp:useBean id="agenciesBean" class="com.papyrus.data.administration.agency.AgenciesBean" scope="application" />
<jsp:useBean id="typeListBean" class="com.papyrus.data.type.TypeListBean" scope="application" />
<jsp:useBean id="customerUpdateForm" class="com.papyrus.data.mapping.form.FormMappingObject" scope="session" />

<?xml version="1" encoding="ISO-8859-1"?>
<!DOCTYPE html PUBLIC "- //w3c//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml11/DTD/xhtml11-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Modification client</title>
		<link href="/include/style/styleAdd.css" rel="stylesheet" type="text/css">
		<link href="/include/style/global.css" rel="stylesheet" type="text/css">
		<link href="/include/style/header.css" rel="stylesheet" type="text/css">
		
		<script src="/include/scripts/global.js"></script>
		
		<!-- create dynamic menu -->
		<c:import url="/include/jsp/menu.jsp" />
	
	</head>
	
	<script language="Javascript">
		function changeCivility() {
			var ste_code = <%=CustomerBean.STE_CIVILITY%>;
			var sarl_code = <%=CustomerBean.SARL_CIVILITY%>;
			var sci_code = <%=CustomerBean.SCI_CIVILITY%>;
			var civility = document.addForm.civilityId.value;
			
			if (civility == company_code) {
				/* hide the "firstName" box */
				document.addForm.firstNameBox.style.visibility = "hidden";
				document.addForm.firstName.value = "";
			} else {
				document.addForm.firstNameBox.style.visibility = "show";
			}
		}
	
	</script>	
	
	<body>
	
		<!-- display header : company logo + menu + user info -->
		<c:import url="/include/jsp/header.jsp" />
	
		<form name="updateForm" action="DomainActionServlet" method="POST">
			<input type="hidden" name="domain" value="customer">
			<input type="hidden" name="action" value="update">
			<input type="hidden" name="subAction" value="ok">
	
			<input type="hidden" name="id" value="<c:out value="${customerUpdateForm.htmlData['id']}" />">
	
		<br>
				
		<h1 class="title">Clients&nbsp;&nbsp;:&nbsp;:&nbsp;&nbsp;Modification</h1>
		
		<div class="boxTitle">&nbsp;&nbsp;Identit�: </div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Civilit� :</p>
				<p class="boxInput">
					<select name="civilityId" onchange="changeCivility();">
						<c:forEach var="attribute" items="${typeListBean.hashMap['civility']}" varStatus="status">
						<option value="<c:out value="${attribute.key}"/>" <c:if test="${attribute.key == customerUpdateForm.htmlData['civilityId']}">SELECTED</c:if>><c:out value="${attribute.value}" /></option>
						</c:forEach>
					</select>
				</p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Nom :</p>
				<p class="boxInput"><input type="text" name="lastName" value="<c:out value="${customerUpdateForm.htmlData['lastName']}" />"></p>
				<c:if test="${not empty customerUpdateForm.errorFields['lastName']}" >
					<p class="boxError"><img src="/include/pictures/warning.png">&nbsp;&nbsp;&nbsp;<c:out value="${customerUpdateForm.errorFields['lastName']}" /></p>
				</c:if>
			</div>
			<div class="boxCol" id="firstNameBox">
				<p class="boxField">&nbsp;&nbsp;Pr�nom :</p>
				<p class="boxInput"><input type="text" name="firstName" value="<c:out value="${customerUpdateForm.htmlData['firstName']}" />"></p>
				<c:if test="${not empty customerUpdateForm.errorFields['firstName']}" >
					<p class="boxError"><img src="/include/pictures/warning.png">&nbsp;&nbsp;&nbsp;<c:out value="${customerUpdateForm.errorFields['firstName']}" /></p>
				</c:if>
			</div>
		</div>
		
		<div class="boxTitle">&nbsp;&nbsp;Coordonn�es: </div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Adresse :</p>
				<p class="boxInput"><input type="text" name="address" value="<c:out value="${customerUpdateForm.htmlData['address']}" />"></p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Ville :</p>
				<p class="boxInput"><input type="text" name="city" value="<c:out value="${customerUpdateForm.htmlData['city']}" />"></p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Code Postal :</p>
				<p class="boxInput"><input type="text" name="postalCode" value="<c:out value="${customerUpdateForm.htmlData['postalCode']}" />"></p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;T�l�phone :</p>
				<p class="boxInput"><input type="text" name="phone" value="<c:out value="${customerUpdateForm.htmlData['phone']}" />"></p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Portable :</p>
				<p class="boxInput"><input type="text" name="cellPhone" value="<c:out value="${customerUpdateForm.htmlData['cellPhone']}" />"></p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;E-mail :</p>
				<p class="boxInput"><input type="text" name="email" value="<c:out value="${customerUpdateForm.htmlData['email']}" />"></p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Agence :</p>
				<p class="boxInput">
					<select name="agencyId" >
						<c:forEach var="agency" items="${agenciesBean.agenciesList}" varStatus="status">
						<option value="<c:out value="${agency.id}" />" <c:if test="${agency.id == customerListForm.htmlData['agencyId']}"><c:out value="SELECTED" /></c:if>><c:out value="${agency.company}" /></option>
						</c:forEach>
					</select>
				</p>
			</div>
		</div>
		
		<div class="boxAction">
			<a class="classicButtonGreen" onclick="updateForm.submit();">&nbsp;Valider&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="classicButtonRed" onclick="window.location.href = '/DomainActionServlet?domain=customer&action=list&subAction=init';">&nbsp;Annuler&nbsp;</a>
		</div>
		
		</form>
	</body>
</html>