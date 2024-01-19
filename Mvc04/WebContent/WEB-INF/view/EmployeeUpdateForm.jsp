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
<title>EmployeeUpdateForm</title>
<link rel="stylesheet" type="text/css" href="<%=cp %>/css/main.css">
<link rel="stylesheet" type="text/css" href="<%=cp %>/css/jquery-ui.css">

<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript" src="<%=cp%>/js/jquery-ui.js"></script>

<script type="text/javascript">
	$(document).ready(function()
	{
		ajaxRequest();
		
		//jQuery-UI 캘린더를 불러오는 함수 처리(datepicker())
		$("#birthday").datepicker(
		{
			dateFormat: "yy-mm-dd"
			, changeMonth: true
			, changeYear: true
		});
		
		// 직위(select)의 선택된 내용이 변경되었을 경우 수행해야 할 코드 처리
		$("#positionId").change(function()
		{
			// 테스트
			// alert("변경");
			
			// Ajax 요청 및 응답 처리
			ajaxRequest();	
			
		});
		
		// 직원 추가 버튼이 클릭되었을 때 수행되어야 할 코드 처리
		$("#submitBtn").click(function()
		{
			var basicPay = Number($("#basicPay").val());
			var minBasicPay = Number($("#minBasicPay").html()); // text
			
			if($("#name").val() == "" || $("#ssn1").val() == "" || $("#ssn2").val() == ""
					    || $("#birthday").val() == "" || $("#telephone").val() == ""
					    || $("extraPay").val() == "")
			{
				$("#err").html("필수 입력 항목이 누락되었습니다.");
				$("#err").css("display", "inline");
				return;						// submit 액션 처리 중단
			}
			
			// 최소 기본급에 대한 유효성 검사
			// if(직급별 최소 기본급 > 사용자가 입력한 기본급)
			// -> 안내 후 submit 액션 처리 중단
			
			if( basicPay < minBasicPay )
			{
				$("#err").html("입력하신 기본급이 최소 기본급보다 작습니다.").css("display", "inline");
				
				return;		// submit 액션 처리 중단
			}
			
			$("#employeeForm").submit();
	
		});

	});
	
	function ajaxRequest()
	{
		$.post("ajax.action", {positionId: $("#positionId").val()}, function(data)
		{
			$("#minBasicPay").html(data);
		})	
	}
	
	
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
		
		<h1>[ 직원 수정 ] ${param.err }</h1>
		<hr />
		
		<form action="employeeupdate.action" method="post" id="employeeForm">
			
			<table>
			
				<!-- 기존 입력 폼에서 추가되는 항목 -->
				<tr>
					<th>사원번호</th>
					<td>														<!-- 수정불가하므로 readonly -->
						<input type="text" id="employeeId" name="employeeId" readonly="readonly" value=${employee.employeeId }>
					</td>
				</tr>
			
				<tr>
					<th>이름</th>
					<td><input type="text" id="name" name="name" placeholder="이름" value="${employee.name }"/></td>
				</tr>
				<tr>
					<th>주민번호</th>
					<td><input type="text" id="ssn1" name="ssn1" style="width: 100px;" value="${employee.ssn1 }"/> - 
					    <input type="text" id="ssn2" name="ssn2" style="width: 110px;" placeholder="뒤 7자리"/>
					</td>
				</tr>
				<tr>
					<th>생년월일</th>
						<td><input type="text" id="birthday" name="birthday" placeholder="생년월일" value="${employee.birthday }"/>
					</td>
				</tr>
				<tr>
					<th>양/음력</th>
					<td>
						<input type="radio" id="lunar0" value="0" name="lunar"
						${employee.lunar==0? "checked=\"checked\"" : ""}/>
						<label for="lunar0">양력</label>
						<input type="radio" id="lunar1" value="1" name="lunar"
						${employee.lunar==1? "checked=\"checked\"" : ""}/>
						<label for="lunar1">음력</label>
					</td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td>
						<input id="telephone" name="telephone" placeholder="전화번호" value="${employee.telephone }"></input>
					</td>
				</tr>
				<tr>
					<th>지역</th>
					<td>
						<select name="regionId" id="regionId">
							  <%--  <c:set var="result" value="1"></c:set>
							<c:forEach var="region" items="${regionList }" >
								<option value="${result }">${region.regionName }</option>
							   <c:set var="result" value="${result + 1 }"></c:set>
							</c:forEach> --%>
							
							<c:forEach var="region" items="${regionList }">
							
									<%-- <c:choose>
										<c:when test="${region.regionId == employee.regionId}">
											<option value="${region.regionId }" selected>${region.regionName }</option>
										</c:when>
										
										<c:otherwise>
											<option value="${region.regionId }">${region.regionName }</option>
										</c:otherwise>
									</c:choose> --%>
									
									<option value="${region.regionId }"
									${employee.regionId == region.regionId ? "selected=\"selected\"" : "" }>${region.regionName }</option>
									
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>	
					<th>부서</th>
					<td>
						<select name="departmentId" id="departmentId">
						
							<%-- <c:forEach var="i" begin="0" end="${departmentList.size()-1 }" step="1">	
								<option value="${i+1 }">${departmentList[i].departmentName }</option>
							</c:forEach> --%>
							
							<c:forEach var="department" items="${departmentList }">
								<c:choose>
									<c:when test="${department.departmentId == employee.departmentId}">
										<option value="${department.departmentId }" selected>${department.departmentName }</option>
									</c:when>
									
									<c:otherwise>
										<option value="${department.departmentId }">${department.departmentName }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						
						</select>
					</td>
				</tr>
				<tr>	
					<th>직위</th>
					<td>
						<select name="positionId" id="positionId">
						
							<c:forEach var="position" items="${positionList }">
								<c:choose>
									<c:when test="${position.positionId == employee.positionId}">
										<option value="${position.positionId }"selected>${position.positionName }</option>
									</c:when>
									
									<c:otherwise>
										<option value="${position.positionId }">${position.positionName }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							
						</select>
					</td>
				</tr>
				
				<tr>
					<th>기본급</th>
					<td>
						<input type="text" id="basicPay" name="basicPay" value="${employee.basicPay }"/>
						(최소 기본급 <span id="minBasicPay"
						style="color: red; font-weight: bold;"></span>원)
					</td>
				</tr>
				
				<tr>
					<th>수당</th>
					<td>
						<input type="text" id="extraPay" name="extraPay" value="${employee.extraPay }"/>
					</td>
				</tr>
				
				<tr>
					<td colspan="2" align="center">
						<br /><br />
						
						<button type="button" class="btn" id="submitBtn"
						style="width: 40%; height: 50%;">직원 수정</button>
						<button type="button" class="btn" id="listBtn"
						onclick="location.href='employeelist.action'">직원 리스트</button>
						<br /><br />
						
						<span id="err" style="color: red; font-weight: bold; display: none;"></span>
						
					</td>
				</tr>
			</table>
			
		</form>
		
	</div>
	
	<!-- 회사 소개 및 어플리케이션 소개 영역 -->
	<div id="footer">
	</div>
	
</div>

</body>
</html>