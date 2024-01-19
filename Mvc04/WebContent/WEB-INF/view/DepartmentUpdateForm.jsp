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
<title>DepartmentInsertForm.jsp</title>
<link rel="stylesheet" type="text/css" href="<%=cp %>/css/main.css">
<!-- view안에 넣어버리면 경로가 달라질 수도 있으니 cp 경로 추가해 준다. -->

<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
	
		$().ready(function()
		{
			$("#submitBtn").click(function()
			{
				// 필수 입력 항목(departmentName) 입력해야만 updateForm 으로 이동
				if($("#departmentName").val() == "")
				{
					$("#err").html("필수 입력 항목이 누락되었습니다.");
					$("#err").css("display", "inline");
					return;						
				}
				
				$("#updateForm").submit();
			});
		})
	
</script>


</head>
<body>
<div>
	
	<!-- 메뉴 영역 -->
	<div>
		<c:import url="EmployeeMenu.jsp"></c:import>
	</div>
	
	<!-- 콘텐츠 영역 -->
	<div id="content">
		
		<h1>[ 부서 수정 ] ${param.err }</h1>
		<hr />
		
		<form action="departmentupdate.action" method="post" id="updateForm">
		
		<table>
			<tr>
				<th>부서번호</th>
				<td>														
					<input type="text" id="departmentId" name="departmentId" readonly="readonly" value=${department.departmentId }>
				</td>
			</tr>
			<tr>
				<th>부서명</th>
				<td><input type="text" id="departmentName" name="departmentName"  value="${department.departmentName }"/></td>
			</tr>
			
			
			<tr>
				<td colspan="1" align="center">
					<br /><br />
					
					<button type="button" class="btn" id="submitBtn"
					style="width: 50%; height: 50%;">부서 수정</button>
					<button type="button" class="btn" id="listBtn"
					onclick="location.href='regionlist.action'">부서 리스트</button>
					<br /><br />
					
					<span id="err" style="color: red; font-weight: bold; display: none;"></span>
					
				</td>
			</tr>
		</table>
			
		</form>
	</div>
	
</div>
</body>
</html>
