package controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/index")
public class IndexController{
	@RequestMapping("/login")
	public String login() {
		return "login";//跳转到“/WEB-INF/jsp/login.jsp”
	}
	@RequestMapping("/register")
	public String register() {
		return "register";
	}
	@RequestMapping("/registerBean")
	public String registerBean() {
		return "registerBean";
	}
	@RequestMapping("/multiUser")
	public String multiUser(){
		return "multiUser";
	}
}
