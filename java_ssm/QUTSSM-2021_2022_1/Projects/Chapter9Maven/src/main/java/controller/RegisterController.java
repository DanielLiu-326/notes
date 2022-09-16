package controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
public class RegisterController implements Controller{
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("register");
		mv.addObject("sayHello","hello Spring MVC!");
		return mv;
//		return new ModelAndView("/WEB-INF/jsp/register.jsp");
//		return new ModelAndView("register");
	}
}
