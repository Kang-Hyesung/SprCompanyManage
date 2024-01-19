<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PositionList.jsp</title>
<link rel="stylesheet" type="text/css" href="<%=cp %>/css/main.css">
<!-- view안에 넣어버리면 경로가 달라질 수도 있으니 cp 경로 추가해 준다. -->

<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">

	$(function()
	{
		// 수정 버튼 클릭 시 액션 처리
		$(".updateBtn").click(function()
		{
			$(location).attr("href", "positionupdateform.action?positionId=" + $(this).val());
		});
		
		
		// 삭제 버튼 클릭 시 액션 처리
		$(".deleteBtn").click(function()
		{
			
			
			if(confirm("현재 선택한 데이터를 정말 삭제하시겠습니까?"))
			{
				$(location).attr("href", "positiondelete.action?positionId=" + $(this).val());
			}
			
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
		<h1>[ 직위 관리 (관리자 전용)]</h1>
		<hr />
		<div>
			<form>
				<input type="button" value="부서 추가" class="btn" 
				onclick="location.href='positioninsertform.action'"/>
			</form>
		</div>
		<br /><br />
	
	
	
		<table id="customers" class="table">
			<tr>
				<th>번호</th>
				<th>직위</th>
				<th>최소 급여</th>
				<th>삭제가능여부</th>
				<th>수정</th>
				<th>삭제</th>
			</tr>
			
			<c:forEach var="position" items="${positionList }">
				<tr>
					<td>${position.positionId }</td>
					<td>${position.positionName }</td>
					<td><fmt:formatNumber value="${position.minBasicPay }" type="currency"/></td>
					<td>${position.delCheck > 0 ? "삭제 불가" : "삭제 가능" }</td>
					<td>
						<button type="button" class="btn updateBtn"
						value="${position.positionId }">수정</button>
					</td>
					<td>
						<button type="button" class="btn deleteBtn"
						value="${position.positionId }">삭제</button>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>

</body>
</html>