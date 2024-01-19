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
<title>EmployeInsertForm.jsp</title>
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
		// 테스트
		//alert("Ajax 요청 및 응답 처리");
		
		// $.post() / $,get() 
		// jQuery 에서 Ajax 를 써야 할 경우 지원해 주는 함수
		// (서버 측에서 요청한 데이터를 받아오는 기능의 함수)
		
		// ※ 이 함수($.post())의 사용 방법(방식)
		//    $.post(요청주소, 전송데이터, 응답액션처리);
		//    .요청주소(url)
		//    -> 데이터를 요청할 주소(파일)에 대한 정보
		//    .전송데이터(data)
		//    -> 서버 측에 요청하는 과정에서 내가 전달할 파라미터
		//    .응답액션처리(function)
		//    -> 응답을 받아 처리하는 함수(기능 처리)
		
		// ※ 참고 data 는 파라미터의 데이터타입을 그대로 취하게 되므로
		//    html 이든 문자열이든 상관이 없다.
		
		$.post("ajax.action", {positionId: $("#positionId").val()}, function(data)
		{
			$("#minBasicPay").html(data);
		})
		
		
	}
	
	
</script>

</head>
<body>

<!--==================================
	#21. EmployeeInsertForm.jsp
	     - 직원 데이터 입력 페이지
	     - 관리자만 접근 허용
====================================-->

<div>
	
	<!-- 메뉴 영역 -->
	<div>
		<c:import url="EmployeeMenu.jsp"></c:import>
	</div>
	
	<!-- 콘텐츠 영역 -->
	<div id="content">
		
		<h1>[ 직원 추가 ]</h1>
		<hr />
		
		<form action="employeeinsert.action" method="post" id="employeeForm">
		
			<table>
				<tr>
					<th>이름</th>
					<td><input type="text" id="name" name="name" placeholder="이름"/></td>
				</tr>
				<tr>
					<th>주민번호</th>
					<td><input type="text" id="ssn1" name="ssn1" style="width: 100px;" placeholder="앞 6자리"/> - 
					    <input type="text" id="ssn2" name="ssn2" style="width: 110px;" placeholder="뒤 7자리"/>
					</td>
				</tr>
				<tr>
					<th>생년월일</th>
						<td><input type="text" id="birthday" name="birthday" placeholder="생년월일"/>
					</td>
				</tr>
				<tr>
					<th>양/음력</th>
					<td>
						<input type="radio" id="lunar0" value="0" checked="checked" name="lunar"/>
						<label for="lunar0">양력</label>
						<input type="radio" id="lunar1" value="1" name="lunar"/>
						<label for="lunar1">음력</label>
					</td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td>
						<input id="telephone" name="telephone" placeholder="전화번호"></input>
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
									<option value="${region.regionId }">${region.regionName }</option>
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
									<option value="${department.departmentId }">${department.departmentName }</option>
							</c:forEach>
						
						</select>
					</td>
				</tr>
				<tr>	
					<th>직위</th>
					<td>
						<select name="positionId" id="positionId">
						
							<c:forEach var="position" items="${positionList }">
								<option value="${position.positionId }">${position.positionName }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				
				<tr>
					<th>기본급</th>
					<td>
						<input type="text" id="basicPay" name="basicPay" />
						(최소 기본급 <span id="minBasicPay" 
						style="color: red; font-weight: bold;">0</span>원)
					</td>
				</tr>
				
				<tr>
					<th>수당</th>
					<td>
						<input type="text" id="extraPay" name="extraPay"/>
					</td>
				</tr>
				
				<tr>
					<td colspan="2" align="center">
						<br /><br />
						
						<button type="button" class="btn" id="submitBtn"
						style="width: 40%; height: 50%;">직원 추가</button>
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























