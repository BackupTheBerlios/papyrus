<%@ page language="java" contentType="text/html;charset=iso-8859-1" %>

<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>

<jsp:useBean id="employeeBean" class="com.papyrus.data.administration.employee.EmployeeBean" scope="session" />
<jsp:useBean id="menu" class="com.papyrus.menu.Tree" scope="application" />
<jsp:useBean id="agenciesBean" class="com.papyrus.data.administration.agency.AgenciesBean" scope="application" />
<jsp:useBean id="now" class="java.util.Date" />

<fmt:setLocale value="fr" />
  
<div class="boxContainer">

	<div class="boxHeaderAgency">
		<p>Agence&nbsp;<c:out value="${agenciesBean.agenciesMap[employeeBean.agencyId].company}" /></p>	
		
	</div>

	<div class="boxHeaderUser">
		<p>Utilisateur: <c:out value="${employeeBean.name}" /></p>
	</div>

	<div class="boxHeaderDate">
		<p><fmt:formatDate value="${now}" type="both" pattern="EEEE, dd MMMM yyyy" /></p>
	</div>
 
</div>

<!-- Display menu -->
<div class="boxMenu">
	<script>
		document.write(menuBar);	
	</script>
</div>