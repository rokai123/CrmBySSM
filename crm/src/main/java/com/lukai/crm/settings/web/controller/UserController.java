package com.lukai.crm.settings.web.controller;

import java.util.Date;
import java.util.HashMap;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lukai.crm.commons.contants.Contants;
import com.lukai.crm.commons.domain.ReturnObject;
import com.lukai.crm.commons.utils.DateUtils;
import com.lukai.crm.commons.utils.IpUtils;
import com.lukai.crm.settings.domain.User;
import com.lukai.crm.settings.service.UserService;
import com.lukai.crm.workbench.web.controller.WorkbenchIndexController;

@Controller
public class UserController {

    private final WorkbenchIndexController workbenchIndexController;
	//注意：必ず service クラスのインターフェースを注入し、実装クラスではないようにしてください！
	@Autowired
	UserService userService;

    UserController(WorkbenchIndexController workbenchIndexController) {
        this.workbenchIndexController = workbenchIndexController;
    }
	
	//返回到哪个资源，写哪个资源的路径
	@RequestMapping("/settings/qx/user/toLogin.do")
	public String toLogin() {
		
		
		//リクエスト転送の方式でログインページにジャンプする
		return "settings/qx/user/login";
	}
	
	//ログイン機能を実装する。
	@RequestMapping("/settings/qx/user/Login.do")
	@ResponseBody
	public Object Login(String loginPwd,String loginAct,String isRemPwd,HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		//HashMapオブジェクトを作成して、フロントからのソースを入れる。
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("loginPwd", loginPwd);
		hashMap.put("loginAct", loginAct);
		System.out.println("dasdasdasd");
		User user = userService.queryUserByLoginActAndPwd(hashMap);
		ReturnObject returnObject = new com.lukai.crm.commons.domain.ReturnObject();
		
		if (user==null) {
			//パスワードかユーザー名が存在しない。
			//returnObject.setCode("0");
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("パスワードかユーザー名が存在しない");
		}else{
			//有効期限を獲得し、まだ有効期限内であるか検証する
			/*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formatDate = simpleDateFormat.format(new Date());*/
			//日付形式をユーティリティクラスにカプセル化して返すことで
			//拡張性を向上させます。
			if (DateUtils.formateDateTime(new Date()).compareTo(user.getExpireTime())>0) {
				//有効期間外のユーザー。
				//コードを直接固定値で記述することは推奨されません。
				//後からの修正と保守が不便になります。最もよいのは定数としてカプセル化することです
				//returnObject.setCode("0");
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				returnObject.setMessage("アカウントは有効なアクセス期間外となっており、アクセスできません。");
			}else if ("0".equals(user.getLockState())) {
				//アカウントがロックされてる
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				returnObject.setMessage("アカウントがロックされてる");
			}else if (!user.getAllowIps().contains(IpUtils.getClientIp(request))) {
				//アクセスできるIPではない
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				returnObject.setMessage("アクセスできるIPではない");
			}else {
				//条件を満たす、ログイン許可する。
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
				//returnObject.setResultData(user);
				//ユーザーをセッションスコープに配置し、各画面でユーザー名を表示する機能を実現します。
				session.setAttribute(Contants.SESSION_USER, user);
				if ("true".equals(isRemPwd)) {
					//ユーザーがログイン情報を記憶するチェックボックスをオンにしている。
					//Cookieオブジェクトを作成する。
					Cookie cookie = new Cookie("loginAct", user.getLoginAct());
					cookie.setMaxAge(60*60*24*10);
					//将cookie返回浏览器
					System.out.println("cookie="+cookie);
					response.addCookie(cookie);
					Cookie cookie2 = new Cookie("loginPwd", user.getLoginPwd());
					cookie2.setMaxAge(60*60*24*10);
					response.addCookie(cookie2);
				}else {
					//ユーザーがログイン情報を記憶するチェックボックスをオフにしている。！！！
					System.out.println("取消记住密码");
					Cookie cookie = new Cookie("loginAct", "");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					Cookie cookie2 = new Cookie("loginPwd", "");
					cookie2.setMaxAge(0);
					response.addCookie(cookie2);
				}
			}
		}
		return returnObject;
	}
	
	
}
