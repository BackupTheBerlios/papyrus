<jsp:useBean id="errorBean" scope="request" class="com.eurocash.common.ErrorBean" />

<html>

<HEAD>
  <TITLE>Papyrus - authentification</TITLE>

  <link rel="StyleSheet" HREF="/include/style/general.css" type="text/css">
  <link rel="StyleSheet" HREF="/include/style/global.css" type="text/css">

</HEAD>

<script language="JavaScript">

</script>

<body>

<form name="loginForm" action="DomainActionServlet" method="POST">

	<input type="hidden" name="domain" value="login">
	<br>
	<br>
	<table align="center" border="0" width="360px" cellspacing="1" cellpadding="1" class="login_table">
  		<tr>
  			<td align="left" width="20%"><img src="/include/pictures/login.png"></td>
    		<td align="center" width="80%" class="login_title">:: Authentification ::</td>
    	</tr>
  		<tr><td height="20px" colspan="2">&nbsp;</td></tr>
  		
  		<% if (errorBean.isError()) { %>
  		<tr><td colspan="2" class='login_failed' align="center"><%=errorBean.getCode()%></td></tr>	
  		<% } %>
        
        <tr><td colspan="2">&nbsp;</td></tr>
     	<tr><td align="center" colspan="2">
       		<table border="0">
         		<tr height="30px">
           			<td width="70%" class="login_text">Login: </td>
                    <td width="30%"><input type="text" name="login" maxlength="10" size="12"></td>
                </tr>
         		<tr height="30px">
                	<td width="70%" class="login_text">Mot de passe: </td>
               		<td width="30%"><input type="password" name="password" maxlength="10" size="12"></td>
         		</tr>
        		<tr><td colspan="2">&nbsp;</td></tr>
         		<tr>
           			<td colspan="2" align="center">
	           			<a class="classicButtonGreen" onclick="loginForm.submit();">&nbsp;OK&nbsp;</a>
           				<!-- <input type="submit" value="OK" class="button"> -->
           			</td>
         		</tr>
         	</table>
         </td></tr>
	</table>
	
</form>
         		
</body>

</html>