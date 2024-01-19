/*===================================
 * RegionUpdateFormController.java
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

public class RegionUpdateFormController implements Controller
{
	private IRegionDAO dao;
	
	public void setDao(IRegionDAO dao)
	{
		this.dao = dao;
	}
	
	@Override			// 요청을 핸들링하겠다.
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{	   // model 과 view를 묶어줄 필요가 있을 때 처리(원래는 모델과 뷰를 컨트롤러가 가로채서 결합을 막음)  
		
		// 액션 코드
		
		ModelAndView mav = new ModelAndView();
		
		String regionId = request.getParameter("regionId");
		
		try
		{
			HttpSession session = request.getSession();
			
			if(session.getAttribute("admin") == null)
			{
				mav.setViewName("/loginform.action");
				return mav;
			}
			
			// 이전페이지로부터 가져온 regionId 로 region 객체 뽑아냄
			Region region = dao.searchId(regionId);
			
			mav.addObject("region", region);
			
			mav.setViewName("/WEB-INF/view/RegionUpdateForm.jsp");
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}
	
}
