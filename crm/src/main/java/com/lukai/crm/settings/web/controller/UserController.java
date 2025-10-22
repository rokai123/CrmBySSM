package com.lukai.crm.settings.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lukai.crm.commons.domain.ReturnObject;
import com.lukai.crm.settings.domain.User;
import com.lukai.crm.settings.service.UserService;

@Controller
public class UserController {
	//注意：必ず service クラスのインターフェースを注入し、実装クラスではないようにしてください！
	@Autowired
	UserService userService;
	
	//返回到那个资源，写哪个资源的路径
	@RequestMapping("/settings/qx/user/toLogin.do")
	public String toLogin() {
		
		
		//リクエスト転送の方式でログインページにジャンプする
		return "settings/qx/user/login";
	}
	
	//ログイン機能を実装する。
	@RequestMapping("/settings/qx/user/Login.do")
	@ResponseBody
	public Object Login(String loginPwd,String loginAct,String isRemPwd,HttpServletRequest request) {
		//HashMapオブジェクトを作成して、フロントからのソースを入れる。
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("loginPwd", loginPwd);
		hashMap.put("loginAct", loginAct);
		
		User user = userService.queryUserByLoginActAndPwd(hashMap);
		ReturnObject returnObject = new com.lukai.crm.commons.domain.ReturnObject();
		
		if (user==null) {
			//パスワードかユーザー名が存在しない。
			returnObject.setCode("0");
			returnObject.setMessage("パスワードかユーザー名が存在しない");
		}else{
			//有効期限を獲得し、まだ有効期限内であるか検証する
			String expireTime = user.getExpireTime();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formatDate = simpleDateFormat.format(new Date());
			if (formatDate.compareTo(expireTime)>0) {
				//有効期間外のユーザー。
				returnObject.setCode("0");
				returnObject.setMessage("アカウントは有効なアクセス期間外となっており、アクセスできません。");
			}else if ("0".equals(user.getLockState())) {
				//アカウントがロックされてる
				returnObject.setCode("0");
				returnObject.setMessage("アカウントがロックされてる");
			}else if (user.getAllowIps().contains(request.getRemoteAddr())) {
				//アクセスできるIPではない
				//System.out.println(getClientIp(request));
				returnObject.setCode("0");
				returnObject.setMessage("アクセスできるIPではない");}
			else {
				//条件を満たす、ログイン許可する。
				returnObject.setCode("1");
				//returnObject.setResultData(user);
			}
		}
		return returnObject;
	}
	
	
}
