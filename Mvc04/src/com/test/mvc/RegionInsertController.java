/*===================================
 * RegionInsertController.java
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

public class RegionInsertController implements Controller
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
		
		String regionName = request.getParameter("regionName");
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("admin") == null)
		{
			mav.setViewName("redirect:loginform.action");
			return mav;
		}
		
		ArrayList<Region> regionList = dao.list();
		
		for (Region region : regionList)
		{
			if(region.getRegionName().equals(regionName))
			{
				
				/* mav.setViewName("/WEB-INF/view/NameErrorPage.jsp"); */
				mav.addObject("err", "중복불가");
				mav.setViewName("redirect:regioninsertform.action");
				
				return mav;
			}
		}
		
		try
		{
			Region region = new Region();
			// 이전페이지로 부터 가져온 regionName 으로 객체에 set 해준 뒤
			region.setRegionName(regionName);
			
			// dao의 add 메소드 활용하여 지역 추가 후 지역리스트페이지로 이동
			dao.add(region);
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		mav.setViewName("redirect:regionlist.action");
		
		return mav;
	}




	
}
