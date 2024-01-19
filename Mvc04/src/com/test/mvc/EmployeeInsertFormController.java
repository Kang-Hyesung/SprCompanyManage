/*=====================================
 * #22. EmployeeInsertFormController.java
 *  - 사용자 정의 컨트롤러 클래스
 *  - 회원 데이터 입력 폼 리스트 페이지 요청에 대한 액션 처리
 *  - DAO 객체에 대한 의존겅 주입(DI)를 위한 준비
 *    -> 인터페이스 형태의 자료형을 속성으로 구성
 *    -> setter 메소드 준비
 ====================================*/

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

public class EmployeeInsertFormController implements Controller
{
	// EmployeeInsertForm.jsp
	
	// 인터페이스 형태의 속성 구성
	private IEmployeeDAO dao;
	
	// setter 구성
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
		
		
		ArrayList<Region> regionList = new ArrayList<Region>();
		ArrayList<Department> departmentList = new ArrayList<Department>();
		ArrayList<Position> positionList = new ArrayList<Position>();
		
		try
		{
			regionList = dao.regionList();
			departmentList = dao.departmentList();
			positionList = dao.positionList();
			
			mav.addObject("regionList", regionList);
			mav.addObject("departmentList", departmentList);
			mav.addObject("positionList", positionList);
			
			mav.setViewName("WEB-INF/view/EmployeeInsertForm.jsp");
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}
	
}

