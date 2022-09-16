package controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pojo.User;
import pojo.UserForm;
import pojo.UserVO;
import service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userBean")
public class UserBeanController {
	//得到一个用来记录日志的对象，这样打印信息的时候能够标记打印的是那个类的信息
	private static final Log logger = LogFactory.getLog(UserBeanController.class);
	//将服务依赖注入到属性userService
	@Autowired
	 public UserService userService;
	/**
	 * 处理登录  //xxx?id=5
	 */
	@RequestMapping("/login")
	public String login(UserForm user, HttpSession session, Model model) {
		if(userService.login(user)){
			session.setAttribute("u", user);
			logger.info("成功");
			return "main";//登录成功，跳转到main.jsp
		}else{
			logger.info("失败");
			model.addAttribute("messageError", "用户名或密码错误");
			return "login";
		}	
	}
	/**
	 *处理注册
	 */
	@RequestMapping("/register")
	public String register(UserForm user, Model model) {//UserForm对象为DTO(Data Transfer Object)
		if("zhangsan".equals(user.getUname())&&"123456".equals(user.getUpass())){
			logger.info("成功");
			return "login";//注册成功，跳转到login.jsp
		}else{
			logger.info("失败");
			model.addAttribute("uname",user.getUname());
			return "register";//返回register.jsp
		}
	}

	@ModelAttribute
	public String isLogin(HttpSession session){
		UserForm user = (UserForm)session.getAttribute("u");
		if(user == null){
			System.out.println("请重新登录！");
			return "login";//注册成功，跳转到login.jsp
		}else{
			return "main";//登录成功，跳转到main.jsp
		}


	}

}
