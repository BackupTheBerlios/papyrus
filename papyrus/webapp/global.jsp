<%@ page language="java" contentType="text/html;charset=iso-8859-1" %>

<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>

<jsp:useBean id="employeeBean" class="com.papyrus.data.administration.employee.EmployeeBean" scope="session" />
<jsp:useBean id="agenciesBean" class="com.papyrus.data.administration.agency.AgenciesBean" scope="application" />


<?xml version="1" encoding="ISO-8859-1"?>
<!DOCTYPE html PUBLIC "- //w3c//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml11/DTD/xhtml11-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Accueil</title>
		<link href="/include/style/styleList.css" rel="stylesheet" type="text/css">
		<link href="/include/style/global.css" rel="stylesheet" type="text/css">
		<link href="/include/style/header.css" rel="stylesheet" type="text/css">
		
		<!-- create dynamic menu -->
		<c:import url="/include/jsp/menu.jsp" />
	
	</head>
	
	<body>
	
		<!-- display header : company logo + menu + user info -->
		<!--<c:import url="/include/jsp/header.jsp" />-->
		<!-- Display menu -->
<div class="boxMenu">
	<script>
		document.write(menuBar);	
	</script>
</div>

		<br>
				
		<h1 class="title">Accueil</h1>

		<br><br><br>

		<div class="message">BIENVENUE DANS L'APPLICATION eGestion Commerciale (version 0.1)</<div>
	</body>
</html>