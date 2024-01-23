/*=======================================
 * SampleController.java
 *  - @Controller 어노테이션 등록
 *  - @RequestMapping 어노테이션 등록
 ======================================*/

package com.test.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

// 1. Controller implement 할 필요 없다
// 3. 어노테이션 추가해주고 import 해줌. 이 과정에서 Controller 의 위상을 갖게 된다.
@Controller
public class HelloController
{
	/* 2. override 필요 없다. + 각종 import 필요 없음
	@Override			
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{	   
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	*/
	
	// ※ 액션 처리를 위한 메소드는 사용자 지정.
	// 리턴 자료형, 메소드이름, 매개변수, 전부 사용자가 지정한다.
	/*
	@RequestMapping("URL매필주소")
	public String 메소드이름(매개변수)
	{
		// ※ 비지니스 로직 처리(모델 활용)
		// ...
		
		return "뷰 이름.jsp";
	}
	*/
	
	// 메소드의 매개변수에 필요로하는 객체를 지정하면
	// 이것을 스프링에 전달해준다.
	@RequestMapping("/hello.action")
	public String hello(Model model)
	{
		// ※ 비지니스 로직 처리(모델 활용)
		// ...
		
		// 데이터 전달 명령
		model.addAttribute("hello", "Hello, SpringMVCAnnotation World");
		
		return "/WEB-INF/view/Hello.jsp";
	}
}


























