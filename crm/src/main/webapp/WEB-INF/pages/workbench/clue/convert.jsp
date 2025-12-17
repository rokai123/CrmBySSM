<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + "/";
 %>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>


<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.ja.js"></script>

<script type="text/javascript">
	$(function(){

		// ページの読み込み完了後、コンテナに対してカレンダーツール関数を呼び出す
		$(".myDate").datetimepicker({
		    language: "ja",          // 言語（日本語に設定）
		    format: "yyyy-mm-dd",    // 日付フォーマット
		    minView: "month",        // 月単位で表示
		    initialDate: new Date(), // デフォルトで現在の日付を表示
		    autoclose: true,         // 日付選択後にカレンダーを自動閉じる
		    todayBtn: true,          // 「今日」ボタンを表示
		    clearBtn: true           // 「クリア」ボタンを表示
		});

		
		$("#isCreateTransaction").click(function(){
			if(this.checked){
				$("#create-transaction2").show(200);
			}else{
				$("#create-transaction2").hide(200);
			}
		});
		//点击搜索按钮，显示模态窗口
		$("#btnSearchAct").click(function(){
			$("#actSearch").val("");
			$("#activityTableBody").html("");
			$("#searchActivityModal").modal("show");
		});
		//搜索已经关联过的市场活动
		$("#actSearch").keyup(function(){
			let name = $(this).val();
			let clueId = "${clue.id}";
			$.ajax({
				url:"workbench/clue/queryActivityForConvertByNameAndClueId.do",
				data:{
					"name":name,
					"clueId":clueId
				},
				type:"get",
				dataType:"json",
				success:function(data){
					// alert(data.name);
					var html = "";
					$.each(data,function(i,act){
						html +="<tr>";
						html +="<td><input type=\"radio\" value=\""+act.id+"\" data-activity-name=\""+act.name+"\" name=\"activityRadio\"/></td>";
						html +="<td>"+act.name+"</td>";
						html +="<td>"+act.startDate+"</td>";
						html +="<td>"+act.endDate+"</td>";
						html +="<td>"+act.owner+"</td>";
						html +="</tr>";
					})
					$("#activityTableBody").html(html);
				}
			})
		});

		//点击其中一条市场活动单选按钮，将活动名称放入输入框，并将活动id放入隐藏域
		$("#activityTable").on("click","input[type='radio']",function(){
			var activityName = $(this).data("activityName");
			$("#activityName").val(activityName);
			$("#activityId").val(this.value);
			$("#searchActivityModal").modal("hide");
		});

		//点击保存按钮，将要转换的数据保存到数据库中
		$("#saveConvertClueBtn").click(function(){
			var clueId = "${clue.id}";
			var money=$("#amountOfMoney").val().replace(/,/g, "");//金额去掉格式化的逗号再提交
			var name = $("#tradeName").val();
			var expectedDate = $("#expectedClosingDate").val();
			var stage = $("#stage").val();
			var activityId = $("#activityId").val();
			var isCreateTran = $("#isCreateTransaction").prop("checked");
			//表单验证暂且不做
					
					
			$.ajax({
				url:"workbench/clue/saveConvertClue.do",
				data:{
					"clueId":clueId,
					"money":money,
					"name":name,
					"expectedDate":expectedDate,
					"stage":stage,
					"activityId":activityId,
					"isCreateTran":isCreateTran
				},
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.code == "1"){
						alert("转换成功");
						window.location.href="workbench/clue/index.do";
					}else{
						alert(data.message);
					}
				}
			})
		});

		//监听输入事件，只允许输入 0–9 的数字，其它字符一律自动删除。
		$("#amountOfMoney").on("input", function () {
		    this.value = this.value.replace(/[^0-9]/g, "");
		});
		//失去焦点后金额格式化
		$("#amountOfMoney").on("blur", function () {
		    if (this.value) {
		        this.value = Number(this.value).toLocaleString("ja-JP");
		    }
		});

		$("#BtnCancel").click(function(){
			//回退到上一个页面(线索详情页面)
			window.history.back();
		});
		
	});
</script>

</head>
<body>
	
	<!-- 搜索市场活动的模态窗口 -->
	<div class="modal fade" id="searchActivityModal" role="dialog" >
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">キャンペーン検索</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" id="actSearch" style="width: 397px;" placeholder="キャンペーン名を入力してください（部分一致検索可）">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>キャンペーン名</td>
								<td>開始日</td>
								<td>終了日</td>
								<td>担当者</td>
								<td></td>
							</tr>
						</thead>
						<tbody id="activityTableBody">
							<!-- <tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>
							<tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr> -->
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<div id="title" class="page-header" style="position: relative; left: 20px;">
		<h4>リード変換 <small>${clue.fullname}${clue.appellation}-${clue.company}</small></h4>
	</div>
	<div id="create-customer" style="position: relative; left: 40px; height: 35px;">
		取引先を新規作成：${clue.company}
	</div>
	<div id="create-contact" style="position: relative; left: 40px; height: 35px;">
		取引先責任者を新規作成：${clue.fullname}${clue.appellation}
	</div>
	<div id="create-transaction1" style="position: relative; left: 40px; height: 35px; top: 25px;">
		<input type="checkbox" id="isCreateTransaction"/>
		商談を作成
	</div>
	<div id="create-transaction2" style="position: relative; left: 40px; top: 20px; width: 80%; background-color: #F7F7F7; display: none;" >
	
		<form>
		  <div class="form-group" style="width: 400px; position: relative; left: 20px;">
		    <label for="amountOfMoney">金額</label>
		    <input type="text" class="form-control" id="amountOfMoney">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="tradeName">商談名</label>
		    <input type="text" class="form-control" id="tradeName" value="${clue.company}--">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="expectedClosingDate">成約予定日</label>
		    <input type="text" class="form-control myDate" id="expectedClosingDate">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="stage">ステージ</label>
		    <select id="stage"  class="form-control">
		    	<option></option>
		    	<c:forEach items="${stageList}" var="s">
					<option value="${s.id}">${s.text}</option>
		    	</c:forEach>
		    </select>
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="activityName">キャンペーンソース&nbsp;&nbsp;<a href="javascript:void(0);" id="btnSearchAct" style="text-decoration: none;"><span class="glyphicon glyphicon-search"></span></a></label>
			<input type="hidden" id="activityId">
		    <input type="text" class="form-control" id="activityName" placeholder="上記から検索" readonly>
		  </div>
		</form>
		
	</div>
	
	<div id="owner" style="position: relative; left: 40px; height: 35px; top: 50px;">
		レコードの所有者：<br>
		<b>${clue.owner}</b>
	</div>
	<div id="operation" style="position: relative; left: 40px; height: 35px; top: 100px;">
		<input class="btn btn-primary" type="button" id="saveConvertClueBtn" value="変換">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="btn btn-default" type="button" id="BtnCancel" value="キャンセル">
	</div>
</body>
</html>