<%@ page language="java" contentType="text/html;charset=iso-8859-1" %>

<jsp:useBean id="employeeBean" class="com.papyrus.data.administration.employee.EmployeeBean" scope="session" />
<jsp:useBean id="menu" class="com.papyrus.menu.Tree" scope="application" />

<link href="/include/style/xmenu.css" type="text/css" rel="stylesheet">
<script src="/include/scripts/cssexpr.js"></script>
<script src="/include/scripts/xmenu.js"></script>
<script>
	webfxMenuImagePath = "/include/pictures/xmenu/";
	var menuBar = new WebFXMenuBar;
	
	menuBar.top = 90;
	//menuBar.left = 200;
	//menuBar.width = 300;
	//menuBar.borderLeft = 300;
	
	
	
	<%=menu.toJavaScript(menu.getRoot(), employeeBean.getRightsId(), 0)%>
	/*
	submenu2.top = 500;
	submenu2.left = 200;
	submenu2.width = 50;
	
	menu3.width=50;
	*/
</script>