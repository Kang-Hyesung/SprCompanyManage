/*===================================
 * DepartmentDeleteController.java
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

public class DepartmentDeleteController implements Controller
{
	private IDepartmentDAO dao;
	
	public void setDao(IDepartmentDAO dao)
	{
		this.dao = dao;
	}

	
	@Override			// 요청을 핸들링하겠다.
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{	   // model 과 view를 묶어줄 필요가 있을 때 처리(원래는 모델과 뷰를 컨트롤러가 가로채서 결합을 막음)  
		
		// 액션 코드
		
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = request.getSession();
		
		// 관리자가 아니라면 로그인 페이지로 돌아가기
		if(session.getAttribute("admin")==null)
		{
			mav.setViewName("redirect:loginform.action");
			return mav;
		}
		
		String departmentId = request.getParameter("departmentId");
		
		
		try
		{
			Department department = dao.searchId(departmentId);
			
			// 삭제 가능한 컬럼이 아니라면 에러페이지로 이동
			if(department.getDelCheck() > 0)
				mav.setViewName("/WEB-INF/view/ErrorPage.jsp");
			else // 삭제 가능하다면 삭제 후 부서리스트로 이동
			{
				dao.remove(departmentId);
				mav.setViewName("redirect:departmentlist.action");
			}
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}

		
		return mav;
	}

}

