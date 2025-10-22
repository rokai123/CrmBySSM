package com.lukai.crm.settings.web.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lukai.crm.settings.service.Impl.UserServiceImpl;

@Controller
public class UserController {
	@Autowired
	UserServiceImpl userService;
	
	//返回到那个资源，写哪个资源的路径
	@RequestMapping("/settings/qx/user/toLogin.do")
	public String toLogin() {
		
		
		//リクエスト転送の方式でログインページにジャンプする
		return "settings/qx/user/login";
	}
	
	//ログイン機能を実装する。
	@RequestMapping("/settings/qx/user/Login.do")
	@ResponseBody
	public Object Login(String loginPwd,String loginAct,String isRemPwd) {
		//HashMapオブジェクトを作成する
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("loginPwd", loginPwd);
		hashMap.put("loginAct", loginAct);
		return null;
	}
}
