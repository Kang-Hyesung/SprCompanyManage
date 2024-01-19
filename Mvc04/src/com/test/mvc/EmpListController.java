/*===================================
 * EmpListController.java
 *  - 사용자 정의 컨트롤러 클래스
 *  - 회원 리스트 페이지 요청에 대한 액션 처리
 *    (일반 회원 적용)
 *  - DAO 에 대한 의존성 주입(DI)을 위한 준비
 *    -> 인터페이스 형태의 자료형을 속성으로 구성
 *    -> setter 메소드 준비
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

public class EmpListController implements Controller
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
		
		// 로그인 여부만 확인 -> 관리자인지 일반 직원인지 확인할 필요 없음
		// 세션 처리과정 추가 --------------------------------------------
		if(session.getAttribute("name") == null)	// 로그인이 되어 있지 않은 상황
		{
			mav.setViewName("redirect:loginform.action");
			return mav;
		}
		// ---------------------------------------------세션 처리과정 추가
		
		ArrayList<Employee> employeeList = new ArrayList<Employee>();
		
		try
		{
			employeeList = dao.list();
			
			mav.addObject("employeeList", employeeList);
			
			
			mav.setViewName("/WEB-INF/view/EmpList.jsp");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		
		return mav;
	}

}
