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
button
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
button:hover
{
	background-color:#0061a7;
}
button:active
{
	position:relative;
	top:1px;
}
</style>
<title>Page d'administration</title>
</head>
<body>
	<div class="section">
		<h1>Page d'administration</h1>
	
		<form action="${pageContext.request.contextPath}/Admin?code=lecode" method="post">
			<button type="submit" name="selection" id="selection" value="start">Démarrer l'IA </button>
			<button type="submit" name="selection" id="selection" value="stop">Arrêter l'IA </button>
		</form>
	</div>
</body>
</html>