<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + "/";
 %>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<!-- 引入外部 CSS 文件 -->
  <!-- <link rel="stylesheet" href="CSS/login.css" type="text/css" /> -->
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript">
	//界面加载完成之后，执行函数
	//画面ロードが完了した後、関数を実行します。..
 $(function(){

	//ブラウザウィンドウにキーボードのエンターキーイベントをバインドする
	    $(window).keydown(function(event) {
	        if (event.keyCode == 13) {
	            //エンターキーのキーコードは13です
	            $("#loginBtn").click();//​ログインボタンをクリックする​
	        }
	    });
	    
		
	$("#loginBtn").click(function () {
		//alert("loginBtn");
		//ユーザーのログイン情報を取得する
		var loginAct = $.trim($("#loginAct").val());
		var loginPwd = $.trim($("#loginPwd").val());
		var isRemPwd = $("#isRemPwd").prop("checked");
		//フォーム検証
		if (loginAct == "") {
			alert("ユーザー名を入力してください。");
			return;
		}
		if (loginPwd == "") {
			alert("パスワードを入力してください。");
			return;
		}
		
		//リクエストを送信する
		$.ajax({
			url:"settings/qx/user/Login.do",
			type:"post",
			data:{
				"loginAct":loginAct,
				"loginPwd":loginPwd,
				"isRemPwd":isRemPwd
			},
			dataType:"json",
			success:function(data){
				if (data.code=="1") {
					//業務画面に遷移する
					window.location.href="workbench/index.do";
				}else{
					//ログインに失敗
					//エラーメッセージを表示する
					$("#msg").html(data.message);
				}
			},
			beforeSend:function(){
				//検証中であることを表示する
				$("#msg").html("確認中、少々お待ちください。");
				return true;
			}


		})
	})

	//入力ボックスをクリックしてフォーカスを取得すると、ヒント情報が空になります
	$("#loginAct").focus(function () {
		$("#msg").html("");
	})
	$("#loginPwd").focus(function () {
			$("#msg").html("");
		})
	});

</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 110%; height: 90%; position: relative; top: 100px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2025&nbsp;CRM</span></div>
	</div>
	
	<!-- <div class="login-container"> -->
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>社員ログイン</h1>
			</div>
			<form action="workbench/index.html" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input id="loginAct" class="form-control" type="text" placeholder="ユーザー名">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input id="loginPwd" class="form-control" type="password" placeholder="パスワード">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						<label>
							<input type="checkbox"> ​10日間ログイン状態を保持​
						</label>
						&nbsp;&nbsp;
						<br/><span id="msg" style="color: red;"></span>
					</div>
					<button type="button" id="loginBtn" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">ログイン</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>