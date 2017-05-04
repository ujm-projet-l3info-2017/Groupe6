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
	font-family:Calibri;
}
h1
{
	color: #007dc1;
	text-align: center;
}
form
{
	color: #3498db;
	text-align: center;
}
.bouton
{
	background-color:#007dc1;
	-moz-border-radius:7px;
	-webkit-border-radius:7px;
	border-radius:7px;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Calibri;
	font-size:1em;
	padding:6px 24px;
	text-decoration:none;
}
.bouton:hover
{
	background-color:#0061a7;
}
.bouton:active
{
	position:relative;
	top:1px;
}
</style>
<title>Connexion Administration</title>
</head>
<body>
	<div class="section">
		<h1>Connexion Administration</h1>
	
		<form action="${pageContext.request.contextPath}/Admin" method="get">
			<label> <b>Code d'accès:</b> <input type="text" name="code" id="code"/></label>
			<input class="bouton" type="submit" value="Envoyer"/> 
		</form>
	</div>
</body>
</html>