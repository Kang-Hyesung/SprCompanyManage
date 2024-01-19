/*===================================
 * PositionUpdateController.java
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

public class PositionUpdateController implements Controller
{
	private IPositionDAO dao;
	
	public void setDao(IPositionDAO dao)
	{
		this.dao = dao;
	}
	
	@Override			// 요청을 핸들링하겠다.
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{	   // model 과 view를 묶어줄 필요가 있을 때 처리(원래는 모델과 뷰를 컨트롤러가 가로채서 결합을 막음)  
		
		// 액션 코드
		
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("admin")==null)
		{
			mav.setViewName("Redirect:loginform.action");
			return mav;
		}
		
		try
		{
			// 이전 페이지로 부터 PositionId, positionName, minBasicPay 받아와
			String positionId = request.getParameter("positionId");
			String positionName = request.getParameter("positionName");
			int minBasicPay = Integer.parseInt(request.getParameter("minBasicPay"));
			
			Position position = new Position();
			
			ArrayList<Position> positionList = dao.list();
			
			for (Position positione : positionList)
			{
				if(positione.getPositionName().equals(positionName))
				{
					
					/* mav.setViewName("/WEB-INF/view/NameErrorPage.jsp"); */
					mav.addObject("err", "중복불가");
					mav.addObject("positionId", positionId);
					mav.setViewName("redirect:positionupdateform.action");
					
					return mav;
				}
			}
			
			// 객체 생성 후 setter 이용해 값 넣어준다
			position.setPositionId(positionId);
			position.setPositionName(positionName);
			position.setMinBasicPay(minBasicPay);
			
			// nodify 메소드에 객체를 넣어서 수정작업 후 직위 리스트 페이지로 이동
			dao.modify(position);
			
			mav.setViewName("redirect:positionlist.action");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}

	
}

