<%@ page language="java" contentType="text/html;charset=iso-8859-1" %>

<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>

<jsp:useBean id="employeeBean" class="com.eurocash.data.administration.employee.EmployeeBean" scope="session" />
<jsp:useBean id="menu" class="com.eurocash.menu.Tree" scope="application" />
<jsp:useBean id="agenciesBean" class="com.eurocash.data.administration.agency.AgenciesBean" scope="application" />

<div class="boxContainer">
	<div class="boxHeaderVoid">
		Agence&nbsp;<c:out value="${agenciesBean.agenciesMap[employeeBean.agencyId].company}" />
	</div>
	
	<div class="boxHeaderTitle">Projet Papyrus</div>

	<div class="boxHeaderInfos">
		::<c:out value="${employeeBean.lastName}" />&nbsp;<c:out value="${employeeBean.firstName}" />::
	</div>

	
</div>

<!-- Display menu -->
<div class="boxMenu">
	<script>
		document.write(menuBar);	
	</script>
</div>
