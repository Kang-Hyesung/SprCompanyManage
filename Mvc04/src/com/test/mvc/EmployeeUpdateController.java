/*===================================
 * EmployeeUpdateController.java
 *  - 사용자 정의 컨트롤러 클래스
 *  - 회원 리스트 페이지 요청에 대한 액션 처리
 *  - DAO 객체에 대한 의존성 주입(DI)을 위한 준비
 *    - 인터페이스 형태의 자료형을 속성으로 구성
 *    - setter 메소드 준비
 ==================================*/

package com.test.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

// ※ Spring 의 Controller 인터페이스를 구현하는 방법을 통해
//    사용자 정의 컨트롤러 클래스를 구성한다.
//    cf. Controller Annotation 활용

public class EmployeeUpdateController implements Controller
{
	private IEmployeeDAO dao;
	
	public void setDao(IEmployeeDAO dao)
	{
		this.dao = dao;
	}
	
	@Override			// 요청을 핸들링하겠다.
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{	   // model 과 view를 묶어줄 필요가 있을 때 처리(원래는 모델과 뷰를 컨트롤러가 가로채서 결합을 막음)  
		
		// 액션 코드
		
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("admin") == null)
		{
			mav.setViewName("redirect:loginform.action");
			return mav;
		}
		
		// 이전 페이지(EmployeeUpdateForm.jsp)로부터 넘어온 데이터 수신
		// name ~~ employeeId
		String name = request.getParameter("name");
		String ssn1 = request.getParameter("ssn1");
		String ssn2 = request.getParameter("ssn2");
		String birthday = request.getParameter("birthday");
		String lunar = request.getParameter("lunar");
		String telephone = request.getParameter("telephone");
		String regionId = request.getParameter("regionId");
		String departmentId = request.getParameter("departmentId");
		String positionId = request.getParameter("positionId");
		String basicPay = request.getParameter("basicPay");
		String extraPay = request.getParameter("extraPay");	
		String employeeId = request.getParameter("employeeId");
		
		try
		{
			Employee employee = new Employee();
			
			employee.setName(name);
			employee.setSsn1(ssn1);
			employee.setSsn2(ssn2);
			employee.setBirthday(birthday);
			employee.setLunar(Integer.parseInt(lunar));
			employee.setTelephone(telephone);
			employee.setRegionId(regionId);
			employee.setDepartmentId(departmentId);
			employee.setPositionId(positionId);
			employee.setBasicPay(Integer.parseInt(basicPay));
			employee.setExtraPay(Integer.parseInt(extraPay));
			employee.setEmployeeId(employeeId);
			
			dao.modify(employee);
			
			//mav.setView("/WEB-INF/view ...");
			mav.setViewName("redirect:employeelist.action");
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}
	
}
