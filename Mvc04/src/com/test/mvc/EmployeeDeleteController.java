/*===================================
 * EmployeeDeleteController.java
 *  - 사용자 정의 컨트롤러 클래스
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

public class EmployeeDeleteController implements Controller
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
		
		// 이전페이지(EmployeeList.jsp)로부터 넘어온 데이터 수신(employeeId)
		String employeeId = request.getParameter("employeeId");
		
		try
		{
			dao.remove(employeeId);
			// remove의 반환값인 int 의 값을 가지고 어떤 ViewName으로 이동할지 분기를 정해줄 수 있다.(여기서는 생략)
			
			mav.setViewName("redirect:employeelist.action");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		
		return mav;
	}
	
}
