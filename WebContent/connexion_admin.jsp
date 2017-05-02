<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
body
{
	background-color: #EEF8FF;
}
h1
{
	color: #3498db;
	text-align: center;
}
form
{
	color: #3498db;
	text-align: center;
}
section
{
	width: 50%;
	color: #BBC8BB;
	display: block;
	margin: auto;

}
</style>
<title>Connexion Administration</title>
</head>
<body>
	<div class="section">
		<h1>Connexion Administration</h1>
	
		<form action="${pageContext.request.contextPath}/Admin" method="get">
			<label> Code d'accès<input type="text" name="code" id="code"/></label>
			<input type="submit" value="Envoyer"/> 
		</form>
	</div>
</body>
</html>