<jsp:useBean id="errorBean" scope="request" class="com.papyrus.common.ErrorBean" />

<html>

<HEAD>
  <TITLE>Papyrus - authentification</TITLE>

  <link rel="StyleSheet" HREF="/include/style/login.css" type="text/css">
 
</HEAD>

<script language="JavaScript">

</script>

<body>

<form name="loginForm" action="DomainActionServlet" method="POST">

	<input type="hidden" name="domain" value="login">
	
	<div class="loginContainer">
		<div class="loginTitle">  
			<img class="loginLogo" src="/include/pictures/login_title.png" alt="papyrus">
		</div>
		<div class="loginBody">
			<div class="loginAuthenticate">
				<img class="loginIcon" src="/include/pictures/login_icon.png" alt="authentification">
				:: Authentification ::</div>

		<% if (errorBean.isError()) { %>
  			<div class="loginError"><%=errorBean.getCode()%></div>
  		<% } %>

			<div class="loginBox">
				<p class="loginField">Utilisateur:</p>
				<p class="loginInput"><input type="text" name="login" maxlength="20" size="20"></p>
			</div>
			<div class="loginBox">
				<p class="loginField">Mot de passe:</p>
				<p class="loginInput"><input type="password" name="password" maxlength="20" size="20"></p>
			</div>

			<div class="loginButton">
				<a class="classicButtonGreen" onclick="loginForm.submit();">&nbsp;OK&nbsp;</a>
			</div>
		</div>
	</div>
</form>
         		
</body>

</html>