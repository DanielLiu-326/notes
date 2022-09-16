package annotation.controller;
import annotation.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import javax.annotation.Resource;

@Controller
public class TestController {
	@Autowired
	//Resource(name="testService")
	public TestService testService;

	public void save() {
		testService.save();
		System.out.println("testController save");
	}
}
