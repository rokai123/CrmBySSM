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
		//初期表示でリードリストを表示する
		queryClueByConditionForPage(1,10);
		
		
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

		//实现全选和取消全选
		$("#checkAll").click(function () {
			$("input[name=xz]").prop("checked",this.checked);
		});
		//实现单选取消全选
		$("#clueTableDiv").on("click", "input[name=xz]", function () {
		    $("#checkAll").prop("checked", 
		        $("#clueTableDiv input[name=xz]").length === $("#clueTableDiv input[name=xz]:checked").length
		    );
		});

		//为创建线索按钮绑定点击事件
		$("#createClueBtn").click(function () {
            //alert("クリックされました");
            $("#createClueForm")[0].reset();  //フォームをリセット
            //打开创建线索的模态窗口
            $("#createClueModal").modal("show");
        });
		
		//検索ボタンをクリック
		$("#researchClue").click(function () {
			//alert("クリックされました");
			queryClueByConditionForPage(1,10);
		});

		// 防止创建模态表单执行原生提交（按Enter键），这可能会关闭模态框
		/* $("#createClueModal form").on("submit", function(e) {
			e.preventDefault();
			return false;
		}); */

		//リード登録ボタンをクリック
		$("#saveCreateClueBtn").click(function () {
			 
			let owner = $("#create-clueOwner").val();
			let company = $.trim($("#create-company").val());
			let appellation = $("#create-call").val();
			let fullname = $.trim($("#create-surname").val());
			let job = $.trim($("#create-job").val());
			let email = $.trim($("#create-email").val());
			let phone = $.trim($("#create-phone").val());
			let website = $.trim($("#create-website").val());
			let mphone = $.trim($("#create-mphone").val());
			let state = $("#create-status").val();
			let source = $("#create-source").val();
			let description = $.trim($("#create-describe").val());
			let contactSummary = $.trim($("#create-contactSummary").val());
			let nextContactTime = $("#create-nextContactTime").val();
			let address = $.trim($("#create-address").val());
			//必須入力項目チェック
			if(owner == "") {
				alert("担当者は必須入力です");
				return;
			}else if(company==""){
				alert("会社名は必須入力です");
				return;
			}else if(fullname==""){
				alert("お客様の名前は必須入力です");
				return;
			}
			// 正規表現によるメールアドレスの有効性検証
			if(email != "" && !/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(email)){
				alert("メールアドレスの形式が正しくありません");
				return;
			}
			//電話番号の有効性検証
			if(phone != "" && !/^(?:\+?81[-\s]?|0)?(?:[789]0|50|[1-9]\d?)[-\s]?\d{1,4}[-\s]?\d{4}$/.test(phone)){
				alert("会社の電話番号の形式が正しくありません");
				return;
			}
			if(mphone != "" && !/^(?:\+?81[-\s]?|0)?(?:[789]0|50|[1-9]\d?)[-\s]?\d{1,4}[-\s]?\d{4}$/.test(mphone)){
				alert("携帯電話番号の形式が正しくありません");
				return;
			}
			//フォーム検証完了、非同期リクエストを送信
			$.ajax({
				url: "workbench/clue/saveCreateClue.do",
				type: "post",
				data: {
					owner: owner,
					company: company,
					appellation: appellation,
					fullname: fullname,
					job: job,
					email: email,
					phone: phone,
					website: website,
					mphone: mphone,
					state: state,
					source: source,
					description: description,
					contactSummary: contactSummary,
					nextContactTime: nextContactTime,
					address: address
				},
				dataType: "json",
				success: function(data) {
					if(data.code=="1") {
						// 登録成功
						alert("リード登録成功");
						//保持每页显示条数不变
						queryClueByConditionForPage(1,$("#cluePage") .bs_pagination("getOption","rowsPerPage"));
						$("#createClueModal").modal("hide");
					}else {
                        data.message;
                        $("#createClueModal").modal("show");
                    }
				}
				
			})

		})

		//リードを削除する機能
		$("#deleteClueBtn").click(function() {
			//alert("クリックされました");
			//获取选中的checkbox

			let checkedClue = $("#clueTableDiv input[name=xz]:checked");
			if(checkedClue.length==0) {
				alert("请选择要删除的记录");
				return;
			}else {
				if(confirm("确定要删除选中的记录吗？")) {
					//alert("确定删除");
					let ids = "";
					$.each(checkedClue, function () {
						ids += "id=" + this.value + "&";
					})
					ids = ids.substr(0, ids.length - 1);
					$.ajax({
						url: "workbench/clue/deleteClueByIds.do",
						type: "post",
						data: ids,
						dataType: "json",
						success: function(data) {
							if(data.code=="1") {
								// 删除成功
								alert("リード削除成功");
								//保持每页显示条数不变
								queryClueByConditionForPage($("#cluePage") .bs_pagination("getOption","currentPage"),$("#cluePage") .bs_pagination("getOption","rowsPerPage"));
								
							}else {
                data.message;
              }
						}
					})
				}
			} 
		})

		//編集ボタンをクリックする時
		$("#editClueBtn").click(function() { 
			let checkedClue = $("#clueTableDiv input[name=xz]:checked");
			if(checkedClue.length==0) {
				alert("編集しようとするレコードを選択してください");
				return;
			}else if(checkedClue.length>1) {
				alert("編集できるレコードは1件だけです");
				return;
			}else {
				//alert("クリックされました");
				let clueId = checkedClue.val();
				$("#editClueModal").modal("show");
				$.ajax({ 
					url: "workbench/clue/queryClueForEditById.do",
					type: "post",
					data: {
						id: clueId
					},
					dataType: "json",
					success: function(data) {
						$("#edit-id").val(data.id);
						$("#edit-clueOwner").val(data.owner);
						$("#edit-company").val(data.company);
						$("#edit-call").val(data.appellation);
						$("#edit-surname").val(data.fullname);
						$("#edit-job").val(data.job);
						$("#edit-email").val(data.email);
						$("#edit-phone").val(data.phone);
						$("#edit-website").val(data.website);
						$("#edit-mphone").val(data.mphone);
						$("#edit-status").val(data.state);
						$("#edit-source").val(data.source);
						$("#edit-describe").val(data.description);
						$("#edit-contactSummary").val(data.contactSummary);
						$("#edit-nextContactTime").val(data.nextContactTime);
						$("#edit-address").val(data.address);
						$("#editClueModal").modal("show");
					},
					fail: function() {
						alert("エラー");
					}

				})
			}
		})

		//編集の送信ボタンをクリックする時
		$("#saveEditBtn").click(function() {
			let id = $("#edit-id").val();
			let owner = $("#edit-clueOwner").val();
			let company = $("#edit-company").val();
			let appellation = $("#edit-call").val();
			let fullname = $("#edit-surname").val();
			let job = $("#edit-job").val();
			let email = $("#edit-email").val();
			let phone = $("#edit-phone").val();
			let website = $("#edit-website").val();
			let mphone = $("#edit-mphone").val();
			let state = $("#edit-status").val();
			let source = $("#edit-source").val();
			let description = $("#edit-describe").val();
			let contactSummary = $("#edit-contactSummary").val();
			let nextContactTime = $("#edit-nextContactTime").val();
			let address = $("#edit-address").val();

			//必須入力項目チェック
			if(owner == "") {
				alert("担当者は必須入力です");
				return;
			}else if(company==""){
				alert("会社名は必須入力です");
				return;
			}else if(fullname==""){
				alert("お客様の名前は必須入力です");
				return;
			}
			// 正規表現によるメールアドレスの有効性検証
			if(email != "" && !/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(email)){
				alert("メールアドレスの形式が正しくありません");
				return;
			}
			//電話番号の有効性検証
			if(phone != "" && !/^(?:\+?81[-\s]?|0)?(?:[789]0|50|[1-9]\d?)[-\s]?\d{1,4}[-\s]?\d{4}$/.test(phone)){
				alert("会社の電話番号の形式が正しくありません");
				return;
			}
			if(mphone != "" && !/^(?:\+?81[-\s]?|0)?(?:[789]0|50|[1-9]\d?)[-\s]?\d{1,4}[-\s]?\d{4}$/.test(mphone)){
				alert("携帯電話番号の形式が正しくありません");
				return;
			}

			$.ajax({
				url: "workbench/clue/saveEditClue.do",
				type: "post",
				data: {
					id: id,
					owner: owner,
					company: company,
					appellation: appellation,
					fullname: fullname,
					job: job,
					email: email,
					phone: phone,
					website: website,
					mphone: mphone,
					state: state,
					source: source,
					description: description,
					contactSummary: contactSummary,
					nextContactTime: nextContactTime,
					address: address
				},
				dataType: "json",
				success: function(data) {
					if(data.code=="1") {
						// 編集成功
						alert("リード編集成功");
						
						queryClueByConditionForPage(1,$("#cluePage") .bs_pagination("getOption","rowsPerPage"));
						$("#editClueModal").modal("hide");
					}else {
						data.message;
					}
				}
				
				
			})
		})

	});
	
	//在页面入口函数外面封装线索列表的查询显示的函数
	// ページのエントリ関数外に、リード一覧の検索表示機能をカプセル化
	function queryClueByConditionForPage(pageNo,pageSize) { 
		
		// パラメータを収集
		let fullname = $("#fullname").val();
        let company = $("#company").val();
        let phone = $("#companyPhone").val();
        let state = $("#state").val();
        let owner = $("#owner").val();
        let mphone = $("#phone").val();
        let source = $("#source").val();


		$.ajax({
		    url: "workbench/clue/queryClueByConditionForPage",
		    type: "post",
		    data: {
		    	fullname: fullname,
		        company: company,
		        phone: phone,
		        source: source,
		        owner: owner,
		        mphone: mphone,
		        state: state,
		        pageNo: pageNo,
		        pageSize: pageSize
		    },
		    dataType: "json",
		    success: function(data) {
		        //data.totalRows：レスポンスデータから取得した総レコード数
		        //$("#totalRowsB").text(data.totalRows);

		    let html = "";
			$.each(data.clueList, function(i, clue) {
				html += "<tr class=\"active\">";
				html += "<td><input type=\"checkbox\" name=\"xz\" value=\""+clue.id+"\"/></td>";
				html += "<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/clue/detailClue.do?id=" + clue.id + "';\">"
						+ (clue.fullname || "")
						+ (clue.appellation || "")
						+ "</a></td>";
				html += "<td>"+ (clue.company || "") +"</td>";
				html += "<td>"+ (clue.phone || "") +"</td>";
				html += "<td>"+ (clue.mphone || "") +"</td>";
				html += "<td>"+ (clue.source || "") +"</td>";
				html += "<td>"+ (clue.owner || "") +"</td>";
				html += "<td>"+ (clue.state || "") +"</td>";
				html += "</tr>";
			});

		        
		        $("#clueTableBody").html(html);
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
				$("#cluePage") .bs_pagination({
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
						queryClueByConditionForPage(pageObj.currentPage,pageObj.rowsPerPage);
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

	<!-- 创建线索的模态窗口 -->
	<div class="modal fade" id="createClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">リード作成</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="createClueForm">
					
						<div class="form-group">
							<label for="create-clueOwner" class="col-sm-2 control-label">担当者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-clueOwner">
									<c:forEach items="${userList}" var="user">
										<option value="${user.id}">${user.name}</option>
									</c:forEach>
								</select>
							</div>
							<label for="create-company" class="col-sm-2 control-label">会社名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-company">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-call" class="col-sm-2 control-label">敬称</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-call">
								  		<option></option>
								  	<c:forEach items="${appellationList}" var="appellation">
										<option value="${appellation.id}">${appellation.text}</option>
								  	</c:forEach>
								</select>
							</div>
							<label for="create-surname" class="col-sm-2 control-label">氏名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-surname">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-job" class="col-sm-2 control-label">役職</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-job">
							</div>
							<label for="create-email" class="col-sm-2 control-label">メールアドレス</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-email">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-phone" class="col-sm-2 control-label">会社電話</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-phone">
							</div>
							<label for="create-website" class="col-sm-2 control-label">会社Webサイト</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-website">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-mphone" class="col-sm-2 control-label">携帯電話</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-mphone">
							</div>
							<label for="create-status" class="col-sm-2 control-label">リードステータス</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-status">
								 	    <option></option>
								  	<c:forEach items="${clueStateList}" var="clueState">
										<option value="${clueState.id}">${clueState.text}</option>
								  	</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-source" class="col-sm-2 control-label">リードソース</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-source">
								  		<option></option>
								  	<c:forEach items="${sourceList}" var="source">
										<option value="${source.id}">${source.text}</option>
								  	</c:forEach>
								</select>
							</div>
						</div>
						

						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">リード説明</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>
						
						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="create-contactSummary" class="col-sm-2 control-label">対応履歴</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="create-nextContactTime" class="col-sm-2 control-label">次回連絡日時</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control myDate" id="create-nextContactTime">
								</div>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>
						
						<div style="position: relative;top: 20px;">
							<div class="form-group">
                                <label for="create-address" class="col-sm-2 control-label">住所（詳細）</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="create-address"></textarea>
                                </div>
							</div>
						</div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">閉じる</button>
					<button type="button" class="btn btn-primary" id="saveCreateClueBtn">登録</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改线索的模态窗口 -->
	<div class="modal fade" id="editClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">リード編集</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
						<input type="hidden" id="edit-id">
						<div class="form-group">
							<label for="edit-clueOwner" class="col-sm-2 control-label">担当者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-clueOwner">
									<c:forEach items="${userList}" var="user">
										<option value="${user.id}">${user.name}</option>
									</c:forEach>
								</select>
							</div>
							<label for="edit-company" class="col-sm-2 control-label">会社名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-company" value="">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-call" class="col-sm-2 control-label">敬称</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-call">
									<option></option>
									<c:forEach items="${appellationList}" var="appellation">
									<option value="${appellation.id}">${appellation.text}</option>
									</c:forEach>
								</select>
							</div>
							<label for="edit-surname" class="col-sm-2 control-label">氏名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-surname" value="">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-job" class="col-sm-2 control-label">役職</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-job" value="CTO">
							</div>
							<label for="edit-email" class="col-sm-2 control-label">メールアドレス</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-email" value="">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-phone" class="col-sm-2 control-label">会社電話</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-phone" value="010-84846003">
							</div>
							<label for="edit-website" class="col-sm-2 control-label">会社webサイト</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-website" value="http://www.bjpowernode.com">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-mphone" class="col-sm-2 control-label">携帯電話</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-mphone" value="12345678901">
							</div>
							<label for="edit-status" class="col-sm-2 control-label">リードステータス</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-status">
									<option></option>
									<c:forEach items="${clueStateList}" var="clueState">
									<option value="${clueState.id}">${clueState.text}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-source" class="col-sm-2 control-label">リードソース</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-source">
									<option></option>
									<c:forEach items="${sourceList}" var="source">
									<option value="${source.id}">${source.text}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">リード説明</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe">这是一条线索的描述信息</textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>
						
						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="edit-contactSummary" class="col-sm-2 control-label">対応履歴</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="edit-contactSummary"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="edit-nextContactTime" class="col-sm-2 control-label">次回連絡日時</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control" id="edit-nextContactTime" value="2017-05-01">
								</div>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address" class="col-sm-2 control-label">住所(詳細)</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address"></textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">閉じる</button>
					<!-- <button type="button" class="btn btn-primary" data-dismiss="modal" id="saveEditBtn">更新</button> -->
					 <!-- 删除了data-dismiss="modal"，使点击更新按钮时不会自动关闭模态窗口 -->
					<button type="button" class="btn btn-primary" id="saveEditBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>リード一覧</h3>
			</div>
		</div>
	</div>
	
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
			<h4>検索条件</h4>
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">氏名</div>
				      <input class="form-control" id="fullname" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">会社名</div>
				      <input class="form-control" id="company" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">会社電話</div>
				      <input class="form-control" id="companyPhone" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">リードソース</div>
					  <select class="form-control" id="source">
					  		<option value=""></option>
					  	<c:forEach items="${sourceList}" var="source">
							<option value="${source.id}">${source.text}</option>
					  	</c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <br>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">担当者</div>
				      <input class="form-control" type="text" id="owner">
				    </div>
				  </div>
				  
				  
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">携帯電話</div>
				      <input class="form-control" id="phone" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">リードステータス</div>
					  <select class="form-control" id="state">
							<option value=""></option>
					  	<c:forEach items="${clueStateList}" var="clueState">
							<option value="${clueState.id}">${clueState.text}</option>
					  	</c:forEach>
					  </select>
				    </div>
				  </div>

				  <button type="button" class="btn btn-default" id="researchClue">検索</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 40px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="createClueBtn"><span class="glyphicon glyphicon-plus"></span> 新規作成</button>
				  <button type="button" class="btn btn-default" data-toggle="modal" id="editClueBtn"><span class="glyphicon glyphicon-pencil"></span> 編集</button>
				  <button type="button" class="btn btn-danger" id="deleteClueBtn"><span class="glyphicon glyphicon-minus"></span> 削除</button>
				</div>
				
				
			</div>
			<div style="position: relative;top: 50px;" id="clueTableDiv">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkAll" /></td>
							<td>氏名</td>
							<td>会社名</td>
							<td>会社電話</td>
							<td>携帯電話</td>
							<td>リードソース</td>
							<td>担当者</td>
							<td>リードステータス</td>
						</tr>
					</thead>
					<tbody id="clueTableBody">
						<!-- 通过AJAX异步请求线索数据后，填充到此处 -->
					</tbody>
				</table>
			</div>
			
			<div style="height: 80px; position: relative;top: 60px;" id="cluePage">
				<!--  <div>
					<button type="button" class="btn btn-default" style="cursor: default;">共<b>50</b>条记录</button>
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
