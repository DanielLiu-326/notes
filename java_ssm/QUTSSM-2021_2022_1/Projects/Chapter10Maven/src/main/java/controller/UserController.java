package controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pojo.User;
import pojo.UserForm;
import pojo.UserVO;
import service.UserService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
	//得到一个用来记录日志的对象，这样打印信息的时候能够标记打印的是那个类的信息
	private static final Log logger = LogFactory.getLog(UserController.class);
	//将服务依赖注入到属性userService
	@Autowired
	 public UserService userService;
	/**
	 * 处理登录
	 */
	@RequestMapping("/login")
	public String login(UserForm user, HttpSession session, Model model) {
		if(userService.login(user)){
			session.setAttribute("u", user);
			logger.info("成功");
			return "main";//登录成功，跳转到main.jsp
		}else{
			logger.info("失败");
			model.addAttribute("userService", "用户名或密码错误");
			return "login";
		}	
	}
	/**
	 *处理注册
	 */
	@RequestMapping("/register")
	public String register(@ModelAttribute("user") UserForm user, Integer[] favs) {//UserForm对象为DTO(Data Transfer Object)
		if(userService.register(user)){
			logger.info("成功");
			return "login";//注册成功，跳转到login.jsp
		}else{
			logger.info("失败");
			//使用@ModelAttribute("user")与model.addAttribute("user", user)功能相同
		  //在register.jsp页面上可以使用EL表达式${user.uname}取出ModelAttribute的uname值
			return "register";//返回register.jsp
		}
	}

	@RequestMapping("/multiUserEdit")
	public String editUsers(UserVO userVo){
		List<User> users = userVo.getUserList();
		for(User user: users){
			if(user.getId() != null){
				System.out.println("修改了id为" + user.getId() + "的用户名为：" + user.getUsername());
			}
		}
		Map<String,User> userMap = userVo.getUserMap();
		for(User user : userMap.values()){
			System.out.println("修改了id为" + user.getId() + "的用户名为：" + user.getUsername());
		}

		return "main";
	}

//	@ModelAttribute
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
