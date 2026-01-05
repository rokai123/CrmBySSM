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
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<!-- 時間選択のためのJSファイルの読み込み -->
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.ja.js"></script>
<!-- ページングのためのJSファイルの読み込み -->
<script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination-master/localization/ja.js"></script>

<script type="text/javascript">

	$(function(){
		queryTranByConditionForPage(1,10);
		
		$("#researchBtn").click(function () {
			queryTranByConditionForPage(1,10);
		});
		
		// 「全選択」チェックボックスのクリックで、全チェック／全解除を行う
		$("#checkAll").on("click",function () {
		    $("#tranBody input[type='checkbox']").prop("checked",this.checked);
		})

		$("#tranBody").on("click", "input[name='checkBox']", function () {
			// 画面内に存在するチェックボックスの総数を取得
			let totalCheckBox = $("#tranBody input[name='checkBox']").length;
			// チェックされているチェックボックスの数を取得
			let checkedCheckBox = $("#tranBody input[name='checkBox']:checked").length;
			// 全てのチェックボックスが選択されている場合、全選択チェックボックスをONにする
			$("#checkAll").prop("checked", totalCheckBox === checkedCheckBox);
		});

		//实现批量删除
		$("#BtnForDeleteTran").on("click",function(){
			let checkboxes = $("#tranBody input[type='checkbox'][name='checkBox']:checked");
			if(checkboxes.length == 0){
				alert("削除するデータを選択してください。")
				return;
			}else{
				if(confirm("削除してもよろしいですか。")){
					let ids="";
					$.each(checkboxes,function(index,checkbox){
						ids += "id="+$(checkbox).val()+"&";
					})
					ids = ids.substring(0,ids.length-1);
					$.ajax({
						url:"workbench/transaction/deleteTranByIds.do",
						type:"post",
						data:ids,
						dataType:"json",
						success:function(data){
							if(data.code == "1"){
								alert(data.message);
								queryTranByConditionForPage(
									$("#tranPage").bs_pagination("getOption","currentPage"),
									$("#tranPage").bs_pagination("getOption","rowsPerPage")
								);
								
							}else{
								alert(data.message);
							}
						}
					})
				}
			}

		})
	});

	//在页面入口函数外面封装市场活动列表的查询显示的函数
	// ページのエントリ関数外に、マーケティングキャンペーン一覧の検索表示機能をカプセル化
	function queryTranByConditionForPage(pageNo,pageSize) { 
		
		// パラメータを収集
		let owner = $("#owner").val();
		let name = $("#name").val();
		let customerName = $("#customerName").val();
		let stage = $("#stage").val();
		let type = $("#type").val();
		let source = $("#source").val();
		let contactsName = $("#contactsName").val();


		$.ajax({
		    url: "workbench/transaction/selectTransByConditionForPage.do",
		    type: "post",
		    data: {
				owner: owner,
				name: name,
				customerName: customerName,
				stage: stage,
				type: type,
				source: source,
				contactsName: contactsName,
				pageNo: pageNo,
				pageSize: pageSize
		    },
		    dataType: "json",
		    success: function(data) {
		        // data：サーバーから返されたレスポンスデータ
		        // data.activities：レスポンスデータから取得した検索結果リスト
		        // data.totalRows：レスポンスデータから取得した総レコード数
		        
		        // data.activitiesをループ処理し、すべての行データ（trタグ）を連結
		        //$("#totalRowsB").text(data.totalRows);

		        let html = "";
		        $.each(data.tranList, function(i, t) {
					html +="<tr>";
					html +="<td><input type=\"checkbox\" name=\"checkBox\" value=\""+t.id+"\"/></td>";
					html +="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/transaction/TranDetailPage.do?id="+t.id+"';\">"+t.name+"</a></td>";
					html +="<td>"+(t.customerId||"")+"</td>";
					html +="<td>"+t.stage+"</td>";
					html +="<td>"+(t.type||"")+"</td>";
					html +="<td>"+(t.owner||"")+"</td>";
					html +="<td>"+(t.source||"")+"</td>";
					html +="<td>"+(t.contactsId||"")+"</td>";
					html +="</tr>";
		        });
		        
		        $("#tranBody").html(html);
				//総ページ数を計算する
				let totalPages;
				if(data.totalRows % pageSize == 0) {
					totalPages = data.totalRows / pageSize;
				} else {
					//切り上げ関数 Math.ceil()
					totalPages = Math.ceil(data.totalRows / pageSize);
				}

				//在此处设置分页组件的函数（因为在这里才能读取到后台返回的总件数)
				// ここでページネーションコンポーネントの関数を設定（バックエンドから返されたdataを取得できるため）
				$("#tranPage").bs_pagination({
					currentPage: pageNo, // ページ番号
					rowsPerPage: pageSize, // 1ページあたりの行数
					totalRows: data.totalRows, // 総レコード数
					totalPages: totalPages, // 総ページ数
					visiblePageLinks: 10, // 表示するページリンク数
					showGoToPage: true, // Go to pageリンクを表示
					showRowsPerPage: true, // 1ページあたりの行数リンクを表示
					showRowsInfo: true, // ページ情報を表示
					// ページリンクがクリックされたときに呼び出される関数
					onChangePage: function(event, pageObj) { 
						// ページリンクがクリックされたら、マーケティングキャンペーン一覧を表示
						queryTranByConditionForPage(pageObj.currentPage,pageObj.rowsPerPage);
						//全選択チェックボックスをデフォルトの未選択状態に設定
						$("#checkAll").prop("checked",false);
						
					}

				})
		    }
		});
	}
	
</script>
</head>
<body>

	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>商談一覧</h3>
			</div>
		</div>
	</div>
	
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">担当者</div>
				      <input class="form-control" id="owner" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">商談名</div>
				      <input class="form-control" id="name" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">取引先名</div>
				      <input class="form-control" id="customerName" type="text">
				    </div>
				  </div>
				  
				  <br>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">ステージ</div>
					  <select class="form-control" id="stage">
					  	<option value=""></option>
					  	<c:forEach items="${stageList}" var="s">
					  	<option value="${s.id}">${s.text}</option>
					  </c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">種別</div>
					  <select class="form-control" id="type">
					  	<option value=""></option>
					  	<c:forEach items="${transactionTypeList}" var="t">
							<option value="${t.id}">${t.text}</option>
						</c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">リードソース</div>
				      <select class="form-control" id="source">
						  	<option value=""></option>
						<c:forEach items="${sourceList}" var="s"> 
							<option value="${s.id}">${s.text}</option>
						</c:forEach>
						</select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">取引先責任者</div>
				      <input class="form-control" id="contactsName" type="text">
				    </div>
				  </div>
				  
				  <button type="button" id="researchBtn" class="btn btn-default">検索</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 10px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" onclick="window.location.href='workbench/transaction/toCreateTranPage.do';"><span class="glyphicon glyphicon-plus"></span>新規作成</button>
				  <button type="button" class="btn btn-default" onclick="window.location.href='edit.html';"><span class="glyphicon glyphicon-pencil"></span> 編集</button>
				  <button type="button" id="BtnForDeleteTran" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 削除</button>
				</div>
				
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkAll"/></td>
							<td>名称</td>
							<td>客户名称</td>
							<td>阶段</td>
							<td>类型</td>
							<td>所有者</td>
							<td>来源</td>
							<td>联系人名称</td>
						</tr>
					</thead>
					<tbody id="tranBody">
						
					</tbody>
				</table>
			</div>
			
			<div id="tranPage">
				
				
				
			</div>
			
		</div>
		
	</div>
</body>
</html>