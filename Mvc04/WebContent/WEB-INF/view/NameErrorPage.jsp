<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ErrorPage</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>
	
	<div>
		<c:import url="EmployeeMenu.jsp"></c:import>
	</div>
	
	<h1>NameErrorPage</h1>
		<hr />
	<h2>이름이 중복되어 추가할 수 없습니다..</h2>
	
	<button type="button" onclick="location.href='employeelist.action'" 
	style="height: 40px; width: 30%">직원 리스트로 돌아가기</button>

</body>
</html>