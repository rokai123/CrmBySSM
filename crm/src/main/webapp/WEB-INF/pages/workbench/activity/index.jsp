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
	//ページ読み込み完了で実行
	$(function(){

		$("#createActivityBtn").click(function(){
			//模态窗口弹出控制：需通过JS代码而非HTML属性实现，以便在弹出前执行初始化操作
			//モーダル表示制御は、表示前の初期化処理を可能にするため、JSコードで実装すること（HTML属性不可）。
			//フォームのreset()メソッドを利用して、入力情報をリセット（クリア）する
            $("#createActivityForm")[0].reset();
			
            $("#createActivityModal").modal("show");
        });

		//保存ボタンクリック時、成功時のみ閉じる処理を実装（失敗時は閉じない）。
		$("#saveBtn").click(function(){ 
			//パラメータの取得
			var owner = $("#create-marketActivityOwner").val();
			var name = $.trim($("#create-marketActivityName").val());
			var startDate = $("#create-startTime").val();
			var endDate = $("#create-endTime").val();
			var cost = $.trim($("#create-cost").val());
			var description = $.trim($("#create-describe").val());
			//フォーム検証：所有者と名称は空であってはなりません。
			if(owner == ""){
				alert("所有者は必須入力です");
				return;
			}
			if(name == ""){
				alert("キャンペーン名は必須入力です");
				return;
			}
			//若结束日期早于开始日期，提示“结束日期不能比开始日期小”并终止执行。
			//終了日は開始日以降の日付を設定する
			if(startDate == "" || endDate == "" ){
				alert("日付を入力してください​");
				return;
			}else if(endDate < startDate){
				alert("終了日は開始日より前の日付には設定できません");
				return;
			}
			//コストは0以上の整数であること（正規表現でチェック）。
			if(!/^(([1-9]\d*)|0)$/.test(cost)){
				alert("​コストは0以上の整数で入力してください​​");
				return;
			}
            //すべての検証が成功した後、Ajaxを通じて作成リクエストを送信します。
            $.ajax({
                url:"workbench/activity/saveCreateActivity.do",
                type:"post",
                dataType:"json",
                data:{
                    owner:owner,
                    name:name,
                    startDate:startDate,
                    endDate:endDate,
                    cost:cost,
                    description:description
                },
                success:function (data) {
                    if(data.code == "1"){
                        //追加成功、モーダルウィンドウを閉じる。
                        $("#createActivityModal").modal("hide");
                        //刷新市场活动列表，显示第一页数据，保持每页显示的记录数不变(后面再完善)
												//$("#activityPage") .bs_pagination("getOption","rowsPerPage")：ページングのオプションを取得する。
                        queryActivityByConditionForPage(1,$("#activityPage") .bs_pagination("getOption","rowsPerPage"))
                    }else{
                        //追加失敗、エラーメッセージを表示
                        alert(data.message);
                        //モーダルウィンドウを閉じない
                        $("#createActivityModal").modal("show");
                    }
                }

            })
        });

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
		// マーケティングキャンペーン一覧ページの読み込み完了時、全データの最初のページと総件数を取得
		queryActivityByConditionForPage(1,10);

		//検索ボタンにクリックイベントをバインドする。
		$("#researchBtn").click(function () {
			
			queryActivityByConditionForPage(1,$("#activityPage") .bs_pagination("getOption","rowsPerPage"));
		});

		// 全選択ボタンにクリックイベントを設定
		$("#checkAll").click(function () { 
			// 全選択ボタンのチェック状態をテーブル内の全チェックボックスに反映
			$("#activityBody input[type='checkbox']").prop("checked", this.checked);
		});

		//全選択以外のチェックボックスにクリックイベントをバインド
		/* 既存の静的元素にのみ直接イベントバインド可能（動的に追加される要素には無効） $("#activityBody input[type='checkbox']").click(function(){}); */

		//xxx.on() メソッド：静的と動的に追加される要素にはイベントバインド可能
		$("#activityBody").on("click","input[type='checkbox']",function () { 
			//全てのチェックボックスがチェックされているかどうかを判断
			if($("#activityBody input[type='checkbox']:checked").size() == $("#activityBody input[type='checkbox']").size()){
				$("#checkAll").prop("checked",true);	
			} else{
				$("#checkAll").prop("checked",false);
			}
		})

		//削除ボタンにクリックイベントをバインド
		$("#deleteBtn").click(function () { 
			
				//取得チェックボックス
				let checkedIds = $("#activityBody input[type='checkbox']:checked");
				//判断
				if(checkedIds.size() == 0){
					alert("削除するキャンペーンを選択してください");
					return;
				}else{
					if(confirm("削除は取り消せません。削除してもよろしいですか？")){
						// ids = "id=xxx&id=xxx&id=xxx"
						let ids = "";
						$.each(checkedIds,function () { 
							ids += "id=" + $(this).val() + "&";
						});
						ids = ids.substring(0,ids.length - 1);
						/* for(let i = 0; i < checkedIds.size(); i++){
							ids += "id=" + $(checkedIds[i]).val();
							if(i < checkedIds.size() - 1){
								ids += "&";
							}
						} */
						$.ajax({
							url:"workbench/activity/deleteActivityByIds.do",
							type:"post",
							data:ids,
							dataType:"json",
							success:function (data) {
								if(data.code == "1"){
									// 削除成功
									//alert(data.message);
									// マーケティングキャンペーン一覧を更新し、1ページ目のデータを表示、ページあたりの表示件数を維持
									queryActivityByConditionForPage(1,$("#activityPage").bs_pagination("getOption","rowsPerPage"));
								}
							}
						})
					}
				}
		});

		// 編集ボタンにクリックイベントをバインド
		$("#editActivityBtn").click(function () {
			//選択されたキャンペーンを取得
			let checkedId = $("#activityBody input[type='checkbox']:checked");
			if(checkedId.size() == 0){
				alert("編集するキャンペーンを選択してください");
				return;
			} else if(checkedId.size() > 1){
				alert("編集は1件のみ可能です");
				return;
			} else{
				// 選択したキャンペーンを取得
				let id = checkedId.val();
				//コントロールにIDを送信し、選択されたマーケティングキャンペーン情報を取得し、モーダルウィンドウに反映する
				$.ajax({
					url:"workbench/activity/queryActivityById.do",
					type:"get",
					data:{
						id:id
					},
					dataType:"json",
					success:function (data) {
						// モーダルウィンドウに取得したキャンペーン情報を表示
						//$("#edit-id").val(data.id);
						$("#edit-id").val(data.id);
						$("#edit-marketActivityOwner").val(data.owner);
						$("#edit-marketActivityName").val(data.name);
						$("#edit-startTime").val(data.startDate);
						$("#edit-endTime").val(data.endDate);
						$("#edit-cost").val(data.cost);
						$("#edit-describe").val(data.description);
						
						$("#hidden-owner").val(data.owner);
						$("#hidden-name").val(data.name);
						$("#hidden-startTime").val(data.startDate);
						$("#hidden-endTime").val(data.endDate);
						$("#hidden-cost").val(data.cost);
						$("#hidden-describe").val(data.description);
						// モーダルウィンドウを表示
						$("#editActivityModal").modal("show");
					}
				})
				
			}

		});

		//更新ボタンにクリックイベントをバインド
		$("#updateActivityBtn").click(function () {
			// モーダルウィンドウの必須入力項目を取得
			let owner = $("#edit-marketActivityOwner").val();
			let name = $("#edit-marketActivityName").val();
			if(owner =="" || owner==null){
				alert("マーケティングキャンペーンの担当者を入力してください");
				return;
			}
			if(name =="" || name==null){
				alert("キャンペーン名を入力してください");
				return;
			}
			// 変更があるかどうかを判定。変更がない場合はリクエストを送信しない
			if(owner == $("#hidden-owner").val() && name == $("#hidden-name").val() && $("#edit-startTime").val() == $("#hidden-startTime").val() && $("#edit-endTime").val() == $("#hidden-endTime").val() && $("#edit-cost").val() == $("#hidden-cost").val() && $("#edit-describe").val() == $("#hidden-describe").val()){
				alert("キャンペーン情報未変更");
				return;
			}
			let activity={
				id:$("#edit-id").val(),
				owner:owner,
				name:name,
				startDate:$("#edit-startTime").val(),
				endDate:$("#edit-endTime").val(),
				cost:$("#edit-cost").val(),
				description:$("#edit-describe").val()

			}
			$.ajax({
				url:"workbench/activity/saveEditActivity.do",
				type:"post",
				data:activity,
				dataType:"json",
				success:function (data) {
					if(data.code == "1"){
						// キャンペーン更新成功
						//alert(data.message);
						// モーダルウィンドウを閉じる
						$("#editActivityModal").modal("hide");
						// マーケティングキャンペーン一覧を更新し、1ページ目のデータを表示、ページあたりの表示件数を維持
						queryActivityByConditionForPage(1,$("#activityPage").bs_pagination("getOption","rowsPerPage"));
					}else{
						// キャンペーン更新失敗
						alert(data.message);
						$("#editActivityModal").modal("show");
					}
				}
			})
		});
	});
	
	//在页面入口函数外面封装市场活动列表的查询显示的函数
	// ページのエントリ関数外に、マーケティングキャンペーン一覧の検索表示機能をカプセル化
	function queryActivityByConditionForPage(pageNo,pageSize) { 
		
		// パラメータを収集
		let name = $("#query-name").val();
		let owner = $("#query-owner").val();
		let startDate = $("#query-startDate").val();
		let endDate = $("#query-endDate").val();

		$.ajax({
		    url: "workbench/activity/queryActivityByConditionForPage.do",
		    type: "post",
		    data: {
		        pageNo: pageNo,
		        pageSize: pageSize,
		        name: name,
		        owner: owner,
		        startDate: startDate,
		        endDate: endDate
		    },
		    dataType: "json",
		    success: function(data) {
		        // data：サーバーから返されたレスポンスデータ
		        // data.activities：レスポンスデータから取得した検索結果リスト
		        // data.totalRows：レスポンスデータから取得した総レコード数
		        
		        // data.activitiesをループ処理し、すべての行データ（trタグ）を連結
		        //$("#totalRowsB").text(data.totalRows);

		        let html = "";
		        $.each(data.activities, function(i, n) {
		            html += "<tr class=\"active\">";
		            html += "<td><input type=\"checkbox\" value=\"" + n.id + "\" id=\" \"/></td>";
		            html += "<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='detail.html';\">" + n.name + "</a></td>";
		            html += "<td>" + n.owner + "</td>";
		            html += "<td>" + n.startDate + "</td>";
		            html += "<td>" + n.endDate + "</td>";
		            html += "</tr>";
		        });
		        
		        $("#activityBody").html(html);
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
				$("#activityPage") .bs_pagination({
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
						queryActivityByConditionForPage(pageObj.currentPage,pageObj.rowsPerPage);
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

	<!-- 创建市场活动的模态窗口 -->
	<!-- マーケティングキャンペーン作成モーダルウィンドウ -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">マーケティングキャンペーン作成</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="createActivityForm">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">
									<c:forEach items="${userList}" var="user">
								 		<option value="${user.id}">${user.name}</option>
									</c:forEach>
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">キャンペーン名<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">開始日</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control myDate" id="create-startTime" readonly>
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">終了日</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control myDate" id="create-endTime" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">コスト</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">説明</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">閉じる</button>
					<button type="button" class="btn btn-primary" id="saveBtn">送信</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">マーケティングキャンペーンを編集する</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<!-- 編集するマーケティングキャンペーンのidを保存するhidden inputタグ-->
							<input type="hidden" id="edit-id"></input>
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">
									<c:forEach items="${userList}" var="user">
								 		<option value="${user.id}">${user.name}</option>
									</c:forEach>
								</select>
								<input type="hidden" id="hidden-owner"></input>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">キャンペーン名<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName">
								<input type="hidden" id="hidden-name"></input>
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control myDate" id="edit-startTime" value="2020-10-10">
								<input type="hidden" id="hidden-startTime"></input>
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control myDate" id="edit-endTime" value="2020-10-20">
								<input type="hidden" id="hidden-endTime"></input>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
								<input type="hidden" id="hidden-cost"></input>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<input type="hidden" id="hidden-describe"></input>
								<textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateActivityBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 导入市场活动的模态窗口 -->
    <div class="modal fade" id="importActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
                </div>
                <div class="modal-body" style="height: 350px;">
                    <div style="position: relative;top: 20px; left: 50px;">
                        请选择要上传的文件：<small style="color: gray;">[仅支持.xls]</small>
                    </div>
                    <div style="position: relative;top: 40px; left: 50px;">
                        <input type="file" id="activityFile">
                    </div>
                    <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;" >
                        <h3>重要提示</h3>
                        <ul>
                            <li>操作仅针对Excel，仅支持后缀名为XLS的文件。</li>
                            <li>给定文件的第一行将视为字段名。</li>
                            <li>请确认您的文件大小不超过5MB。</li>
                            <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式。</li>
                            <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式。</li>
                            <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
                            <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
                        </ul>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
                </div>
            </div>
        </div>
    </div>
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>マーケティングキャンペーン一覧</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">キャンペーン名</div>
				      <input class="form-control" type="text" id="query-name"/>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="query-owner"/>
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">開始日</div>
					  <input class="form-control myDate" type="text" id="query-startDate"/>
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">終了日</div>
					  <input class="form-control myDate" type="text" id="query-endDate"/>
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="researchBtn">検索</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="createActivityBtn"><span class="glyphicon glyphicon-plus"></span> 新規</button>
				  <button type="button" class="btn btn-default" id="editActivityBtn"><span class="glyphicon glyphicon-pencil"></span> 編集</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 削除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal" ><span class="glyphicon glyphicon-import"></span> 上传列表数据（导入）</button>
                    <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（批量导出）</button>
                    <button id="exportActivityXzBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）</button>
                </div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover" style="width: 100%; border-collapse: collapse;">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkAll"/></td>
							<td>ｷｬﾝﾍﾟｰﾝ名</td>
              				<td>所有者</td>
							<td>開始日</td>
							<td>終了日</td>
						</tr>
					</thead>
					<tbody id="activityBody">
						<!-- <tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.html';">发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr> -->
					</tbody>
				</table>
				<!-- <div id="activityPage"></div> -->
			</div>
			<!-- 分页组件挂载处-->
			<!-- ページネーションコンポーネントマウント位置 -->
			<div id="activityPage"></div>
			<!-- <div style="height: 50px; position: relative;top: 30px;">
				<div>
					<button type="button" class="btn btn-default" style="cursor: default;">共<b id="totalRowsB">50</b>条记录</button>
				</div>
				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">
					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							10
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">20</a></li>
							<li><a href="#">30</a></li>
						</ul>
					</div>
					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
				</div>
				<div style="position: relative;top: -88px; left: 285px;">
					<nav>
						<ul class="pagination">
							<li class="disabled"><a href="#">首页</a></li>
							<li class="disabled"><a href="#">上一页</a></li>
							<li class="active"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">下一页</a></li>
							<li class="disabled"><a href="#">末页</a></li>
						</ul>
					</nav>
				</div>
			</div> -->
			
		</div>
		
	</div>
</body>
</html>