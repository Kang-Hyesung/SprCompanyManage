/*===================================
 * EmployeeUpdateFormController.java
 *  - 사용자 정의 컨트롤러 클래스
 ==================================*/

package com.test.mvc;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

// ※ Spring 의 Controller 인터페이스를 구현하는 방법을 통해
//    사용자 정의 컨트롤러 클래스를 구성한다.
//    cf. Controller Annotation 활용

public class EmployeeUpdateFormController implements Controller
{
	// check
	// EmployeeInsertFormController 구성과는 다른 방식으로 처리
	// ----------------------------
	// -> 필요한 모든 메소드를 EmployeeDAO 에 정의해 두었던 상태
	
	// 주요 속성 구성
	// private IEmployeeDAO dao;
	private IEmployeeDAO employeeDAO;
	private IRegionDAO regionDAO;
	private IDepartmentDAO departmentDAO;
	private IPositionDAO positionDAO;
	
	public void setEmployeeDAO(IEmployeeDAO employeeDAO)
	{
		this.employeeDAO = employeeDAO;
	}
	
	public void setRegionDAO(IRegionDAO regionDAO)
	{
		this.regionDAO = regionDAO;
	}
	
	public void setDepartmentDAO(IDepartmentDAO departmentDAO)
	{
		this.departmentDAO = departmentDAO;
	}
	
	public void setPositionDAO(IPositionDAO positionDAO)
	{
		this.positionDAO = positionDAO;
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
		
		
		
		ArrayList<Region> regionList = new ArrayList<Region>();
		ArrayList<Department> departmentList = new ArrayList<Department>();
		ArrayList<Position> positionList = new ArrayList<Position>();
		
		try
		{
			// 이전 페이지(EmployeeList.jsp)로부터 넘어온 데이터 수신
			// employeeId
			String employeeId = request.getParameter("employeeId");
			Employee employee = new Employee();
			
			employee = employeeDAO.searchId(employeeId);
			
			mav.addObject("employee", employee);
			regionList = regionDAO.list();
			departmentList = departmentDAO.list();
			positionList = positionDAO.list();
			
			mav.addObject("employee", employee);
			mav.addObject("regionList", regionList);
			mav.addObject("departmentList", departmentList);
			mav.addObject("positionList", positionList);
			
			
			mav.setViewName("/WEB-INF/view/EmployeeUpdateForm.jsp");
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		
		
		return mav;
	}

	
}
