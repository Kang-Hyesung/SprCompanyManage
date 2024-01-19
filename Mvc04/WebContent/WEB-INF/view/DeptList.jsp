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
<title>DeptList.jsp</title>
<link rel="stylesheet" type="text/css" href="<%=cp %>/css/main.css">

</head>
<body>

<div>
	<!-- 메뉴 영역 -->
	<div>
		<c:import url="EmployeeMenu.jsp"></c:import>
	</div>
	
	<!-- 콘텐츠 영역 -->
	<div id="content">
		<h1>[ 부서 목록 (일반직원 전용)]</h1>
		<br /><br />
		
		<table id="customers" class="table">
			<tr>
				<th>번호</th>
				<th>부서</th>
			</tr>
			
			<c:forEach var="department" items="${departmentList }">
				<tr>
					<td>${department.departmentId }</td>
					<td>${department.departmentName }</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>

</body>
</html>