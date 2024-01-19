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
<title>PosList.jsp</title>
<link rel="stylesheet" type="text/css" href="<%=cp %>/css/main.css">
<!-- view안에 넣어버리면 경로가 달라질 수도 있으니 cp 경로 추가해 준다. -->
</head>
<body>

<div>
	<!-- 메뉴 영역 -->
	<div>
		<c:import url="EmployeeMenu.jsp"></c:import>
	</div>
	
	<!-- 콘텐츠 영역 -->
	<div id="content">
		
		<h1>[ 직위 목록 (일반직원 전용)]</h1>
		<br /><br />
		
		
		<table id="customers" class="table">
			<tr>
				<th>번호</th>
				<th>직위</th>
				<th>최소 급여</th>
			</tr>
			
			<c:forEach var="position" items="${positionList }">
				<tr>
					<td>${position.positionId }</td>
					<td>${position.positionName }</td>
					<td>${position.minBasicPay }</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>

</body>
</html>