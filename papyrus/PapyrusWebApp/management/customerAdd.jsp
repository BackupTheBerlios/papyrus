<%@ page language="java" contentType="text/html;charset=iso-8859-1" import="com.eurocash.data.management.customer.CustomerBean" %>

<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>

<jsp:useBean id="agenciesBean" class="com.eurocash.data.administration.agency.AgenciesBean" scope="application" />
<jsp:useBean id="typeListBean" class="com.eurocash.data.type.TypeListBean" scope="application" />
<jsp:useBean id="customerAddForm" class="com.eurocash.data.mapping.form.FormMappingObject" scope="session" />

<?xml version="1" encoding="ISO-8859-1"?>
<!DOCTYPE html PUBLIC "- //w3c//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml11/DTD/xhtml11-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Ajout client</title>
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
			
			if (civility == ste_code || civility == sarl_code || civility == sci_code) {
				/* hide the "firstName" box */
				hide("firstNameBox");
				document.addForm.firstName.value = "";
			} else {
				show("firstNameBox");
			}
		}
	
	</script>	
	
	<body>
	
		<!-- display header : company logo + menu + user info -->
		<c:import url="/include/jsp/header.jsp" />
	
		<form name="addForm" action="DomainActionServlet" method="POST">
			<input type="hidden" name="domain" value="customer">
			<input type="hidden" name="action" value="add">
			<input type="hidden" name="subAction" value="ok">
	
		<br>
				
		<h1 class="title">Clients&nbsp;&nbsp;:&nbsp;:&nbsp;&nbsp;Ajout</h1>
		
		<div class="boxTitle">&nbsp;&nbsp;Identité: </div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Civilité :</p>
				<p class="boxInput">
					<select name="civilityId" onchange="changeCivility();">
						<c:forEach var="attribute" items="${typeListBean.hashMap['civility']}" varStatus="status">
						<option value="<c:out value="${attribute.key}"/>" <c:if test="${attribute.key == customerAddForm.htmlData['civilityId']}">SELECTED</c:if>><c:out value="${attribute.value}" /></option>
						</c:forEach>
					</select>
				</p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Nom :</p>
				<p class="boxInput"><input type="text" name="lastName" value="<c:out value="${customerAddForm.htmlData['lastName']}" />"></p>
				<c:if test="${not empty customerAddForm.errorFields['lastName']}" >
					<p class="boxError"><img src="/include/pictures/warning.png">&nbsp;&nbsp;&nbsp;<c:out value="${customerAddForm.errorFields['lastName']}" /></p>
				</c:if>
			</div>
			<div class="boxCol" id="firstNameBox">
				<p class="boxField">&nbsp;&nbsp;Prénom :</p>
				<p class="boxInput"><input type="text" name="firstName" value="<c:out value="${customerAddForm.htmlData['firstName']}" />"></p>
				<c:if test="${not empty customerAddForm.errorFields['firstName']}" >
					<p class="boxError"><img src="/include/pictures/warning.png">&nbsp;&nbsp;&nbsp;<c:out value="${customerAddForm.errorFields['firstName']}" /></p>
				</c:if>
			</div>
		</div>
		
		<div class="boxTitle">&nbsp;&nbsp;Coordonnées: </div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Adresse :</p>
				<p class="boxInput"><input type="text" name="address" value="<c:out value="${customerAddForm.htmlData['address']}" />"></p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Ville :</p>
				<p class="boxInput"><input type="text" name="city" value="<c:out value="${customerAddForm.htmlData['city']}" />"></p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Code Postal :</p>
				<p class="boxInput"><input type="text" name="postalCode" value="<c:out value="${customerAddForm.htmlData['postalCode']}" />"></p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Téléphone :</p>
				<p class="boxInput"><input type="text" name="phone" value="<c:out value="${customerAddForm.htmlData['phone']}" />"></p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Portable :</p>
				<p class="boxInput"><input type="text" name="cellPhone" value="<c:out value="${customerAddForm.htmlData['cellPhone']}" />"></p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;Fax :</p>
				<p class="boxInput"><input type="text" name="fax" value="<c:out value="${customerAddForm.htmlData['fax']}" />"></p>
			</div>
			<div class="boxCol">
				<p class="boxField">&nbsp;&nbsp;E-mail :</p>
				<p class="boxInput"><input type="text" name="email" value="<c:out value="${customerAddForm.htmlData['email']}" />"></p>
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
			<a class="classicButtonGreen" onclick="addForm.submit();">&nbsp;Valider&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="classicButtonRed" onclick="window.location.href = '/DomainActionServlet?domain=customer&action=list&subAction=init';">&nbsp;Annuler&nbsp;</a>
		</div>
		
		</form>
	</body>
</html>