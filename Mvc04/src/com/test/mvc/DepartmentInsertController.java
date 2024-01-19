/*===================================
 * DepartmentInsertController.java
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

public class DepartmentInsertController implements Controller
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
		
		String departmentName = request.getParameter("departmentName");
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("admin") == null)
		{
			mav.setViewName("redirect:loginform.action");
			return mav;
		}
		
		ArrayList<Department> departmentList = dao.list();
		
		for (Department department : departmentList)
		{
			if(department.getDepartmentName().equals(departmentName))
			{
				
				/* mav.setViewName("/WEB-INF/view/NameErrorPage.jsp"); */
				mav.addObject("err", "중복불가");
				mav.setViewName("redirect:departmentinsertform.action");
				
				return mav;
			}
		}
		
		
		try
		{
			Department department = new Department();
			department.setDepartmentName(departmentName);
			
			dao.add(department);
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		mav.setViewName("redirect:departmentlist.action");
		
		return mav;
	}


}
