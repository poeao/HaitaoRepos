package com.me.smvc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页控制器
 *
 * @author HenryYan
 */
@Controller
@RequestMapping("/main")
public class MainController {

	@RequestMapping(value = "/index")
	public String index() {
		return "/main/index";
	}
	
	@RequestMapping(value = "/index0")
	public String index0() {
		return "/main/index0";
	}
	
	@RequestMapping(value = "/welcome")
	public String welcome() {
		return "/main/welcome";
	}
	
}
