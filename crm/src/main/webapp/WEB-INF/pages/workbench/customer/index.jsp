<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		
		//定制字段
		$("#definedColumns > li").click(function(e) {
			//防止下拉菜单消失
	        e.stopPropagation();
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
		//加载页面完成后显示客户数据
		queryCustomerByConditionForPage(1,10);
		//検索ボタンをクリック
		$("#queryBtn").click(function () {
			queryCustomerByConditionForPage(1,10);
		})
		//新規作成ボタンをクリック
		$("#create-customer").click(function () {
			//モーダルウィンドウを表示
			$("#createCustomerModal").modal("show");
		})

		//新規作成モーダルウィンドウの保存ボタンをクリック
		$("#saveCreateCustomer").click(function () {
			//收集参数
			let owner = $("#create-customerOwner").val();
			let name = $("#create-customerName").val();
			let website = $("#create-website").val();
			let phone = $("#create-phone").val();
			let description = $("#create-description").val();
			let contactSummary = $("#create-contactSummary").val();
			let nextContactTime = $("#create-nextContactTime").val();
			let address = $("#create-address1").val();
			//取引先名は必須入力
			if (name == "") {
				alert("取引先名は必須入力です。");
				return;
			}
			$.ajax({
				url: "workbench/customer/saveCreateCustomer.do",
				data: {
					"owner": owner,
					"name": name,
					"website": website,
					"phone": phone,
					"description": description,
					"contactSummary": contactSummary,
					"nextContactTime": nextContactTime,
				    "address": address
				},
				type: "post",
				dataType: "json",
				success: function (data) {
					if (data.code == "1") {
						//模态窗口关闭
						$("#createCustomerModal").modal("hide");
						alert("取引先を新規作成しました。");
						//刷新列表
						queryCustomerByConditionForPage(1,10);
					} else {
						alert(data.message);
					}
				}
			})
		})

		// 「全選択」チェックボックスをクリックした時の処理
		$("#checkAll").click(function () {
			// 一覧内のチェックボックスを全て同じ状態にする
			$("#customerTBody input[type='checkbox']").prop("checked", this.checked);
		});

		// 一覧内のチェックボックスをクリックした時の処理（イベント委譲）
		$("#customerTBody").on("click", "input[type='checkbox']", function () {

			// すべてのチェックボックスが選択されているか判定
			if (
				$("#customerTBody input[type='checkbox']").size() ==
				$("#customerTBody input[type='checkbox']:checked").size()
			) {
				// すべて選択されている場合、「全選択」をチェックする
				$("#checkAll").prop("checked", true);
			} else {
				// 一部でも未選択がある場合、「全選択」のチェックを外す
				$("#checkAll").prop("checked", false);
			}
		});

		$("#edit-CustomerBtn").click(function () {
			let checkedBox = $("#customerTBody input[type='checkbox']:checked");
			if (checkedBox.size() == 0) {
				alert("请选择要修改的取引先");
			} else if (checkedBox.size() > 1) {
				alert("只能选择一条取引先进行修改");
			} else {
				let id = checkedBox[0].value;
				$.ajax({
					url: "workbench/customer/queryCustomerById.do",
					data: {
						"id": id
					},
					type: "post",
					dataType: "json",
					success: function (data) {
						$("#edit-id").val(data.id);
						$("#edit-customerName").val(data.name);
						$("#edit-customerOwner").val(data.owner);
						$("#edit-website").val(data.website);
						$("#edit-phone").val(data.phone);
						$("#edit-describe").val(data.description);
						$("#create-contactSummary1").val(data.contactSummary);
						$("#create-nextContactTime2").val(data.nextContactTime);
						$("#edit-address").val(data.address);

						$("#hidden-owner").val(data.owner);
						$("#hidden-name").val(data.name);
						$("#hidden-website").val(data.website);
						$("#hidden-phone").val(data.phone);
						$("#hidden-description").val(data.description);
						$("#hidden-contactSummary").val(data.contactSummary);
						$("#hidden-nextContactTime").val(data.nextContactTime);
						$("#hidden-address").val(data.address);
						//模态窗口打开
						$("#editCustomerModal").modal("show");
					}
				})
			}
		})

		$("#saveEditBtn").click(function () {
			if($("#edit-customerName").val() == "") {
				alert("取引先名は必須入力です。");
				return;
			}
			//判断是否有修改
			if (
				$("#hidden-owner").val() == $("#edit-customerOwner").val() &&
				$("#hidden-name").val() == $("#edit-customerName").val() &&
				$("#hidden-website").val() == $("#edit-website").val() &&
				$("#hidden-phone").val() == $("#edit-phone").val() &&
				$("#hidden-description").val() == $("#edit-describe").val() &&
				$("#hidden-contactSummary").val() == $("#create-contactSummary1").val() &&
				$("#hidden-nextContactTime").val() == $("#create-nextContactTime2").val() &&
				$("#hidden-address").val() == $("#edit-address").val()

			){
				alert("没有修改任何内容！");
				return;
			}
			$.ajax({
				url: "workbench/customer/saveEditCustomer.do",
				data: {
					"id": $("#edit-id").val(),
					"owner": $("#edit-customerOwner").val(),
					"name": $("#edit-customerName").val(),
					"website": $("#edit-website").val(),
					"phone": $("#edit-phone").val(),
					"description": $("#edit-describe").val(),
					"contactSummary": $("#create-contactSummary1").val(),
					"nextContactTime": $("#create-nextContactTime2").val(),
					"address": $("#edit-address").val()
				},
				type: "post",
				dataType: "json",
				success: function (data) {
					if (data.code == "1") {
						//模态窗口关闭
						$("#editCustomerModal").modal("hide");
						alert("取引先を更新しました。");
						//刷新列表,留在当前页
						queryCustomerByConditionForPage($("#customerPage").bs_pagination("getOption","currentPage"),$("#customerPage").bs_pagination("getOption","rowsPerPage"));
					} else {
						alert(data.message);
					}
				}
			})

		})

		$("#delete-CustomerBtn").click(function () {
			let checkedBox = $("#customerTBody input[type='checkbox']:checked");
			if (checkedBox.size() == 0) {
				alert("请选择要删除的取引先");
				return;
			} else {
				if (window.confirm("确定要删除吗？")) {
					let ids = "";
					$.each(checkedBox,function () { 
						ids += "id=" + $(this).val() + "&";
					});
					ids = ids.substring(0,ids.length - 1);
					console.log(ids);
					$.ajax({
						url: "workbench/customer/deleteCustomerByIds.do",
						data: ids,
						type: "post",
						dataType: "json",
						success: function (data) {
							if (data.code == "1") {
								alert("取引先を削除しました。");
								//刷新列表,跳回第一页，每页显示条数不改变
								queryCustomerByConditionForPage(1,$("#customerPage").bs_pagination("getOption","rowsPerPage"))
							}else {
								alert(data.message);
							}
						}
					})
				}
			}
		})
	});

	function queryCustomerByConditionForPage(pageNo,pageSize) {
		//收集参数
		let name = $("#query-name").val();
		let owner = $("#query-owner").val();
		let phone = $("#query-phone").val();
		let website = $("#query-website").val();
		$.ajax({
			url: "workbench/customer/queryCustomerByConditionForPage.do",
			data: {
				"name": name,
				"owner": owner,
				"phone": phone,
				"website": website,
				"pageNo": pageNo,
				"pageSize": pageSize
			},
			type: "post",
			dataType: "json",
			success: function (data) {
				let html = "";
				$.each(data.customerList, function (index, customer) {
					html +="<tr>";
					html +="<td><input type=\"checkbox\" value=\""+customer.id+"\" /></td>";
					html +="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/customer/toCustomerDetail.do?id="+customer.id+"';\">"+customer.name+"</a></td>";
					html +="<td>"+customer.owner+"</td>";
					html +="<td>"+customer.phone+"</td>";
					html +="<td>"+customer.website+"</td>";
					html +="</tr>";
				})
				$("#customerTBody").html(html);

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
				$("#customerPage") .bs_pagination({
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
						queryCustomerByConditionForPage(pageObj.currentPage,pageObj.rowsPerPage);
						//全選択チェックボックスをデフォルトの未選択状態に設定
						//$("#checkAll").prop("checked",false);
						
					}

				})

			}
		})

	}
	
</script>
</head>
<body>

	<!-- 创建客户的模态窗口 -->
	<div class="modal fade" id="createCustomerModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建客户</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-customerOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-customerOwner">
								  <c:forEach items="${userList}" var="u">
									<option value="${u.id}">${u.name}</option>
								  </c:forEach>
								</select>
							</div>
							<label for="create-customerName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-customerName">
							</div>
						</div>
						
						<div class="form-group">
                            <label for="create-website" class="col-sm-2 control-label">公司网站</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-website">
                            </div>
							<label for="create-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-phone">
							</div>
						</div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

                        <div style="position: relative;top: 15px;">
                            <div class="form-group">
                                <label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
                                <div class="col-sm-10" style="width: 300px;">
                                    <input type="text" class="form-control myDate" id="create-nextContactTime">
                                </div>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="create-address1" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="create-address1"></textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveCreateCustomer">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改客户的模态窗口 -->
	<div class="modal fade" id="editCustomerModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">修改客户</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
						<input type="hidden" id="edit-id">
						<div class="form-group">
							<label for="edit-customerOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="hidden" id="hidden-owner">
								<select class="form-control" id="edit-customerOwner">
								  <c:forEach items="${userList}" var="u">
									<option value="${u.id}">${u.name}</option>
								  </c:forEach>
								</select>
							</div>
							<label for="edit-customerName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="hidden" id="hidden-name">
								<input type="text" class="form-control" id="edit-customerName">
							</div>
						</div>
						
						<div class="form-group">
                            <label for="edit-website" class="col-sm-2 control-label">公司网站</label>
                            <div class="col-sm-10" style="width: 300px;">
								<input type="hidden" id="hidden-website">
                                <input type="text" class="form-control" id="edit-website">
                            </div>
							<label for="edit-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="hidden" id="hidden-phone">
								<input type="text" class="form-control" id="edit-phone" value="010-84846003">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<input type="hidden" id="hidden-description">
								<textarea class="form-control" rows="3" id="edit-describe"></textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

                        <div style="position: relative;top: 15px;">
                            <div class="form-group">
                                <label for="create-contactSummary1" class="col-sm-2 control-label">联系纪要</label>
                                <div class="col-sm-10" style="width: 81%;">
									<input type="hidden" id="hidden-contactSummary">
                                    <textarea class="form-control" rows="3" id="create-contactSummary1"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="create-nextContactTime2" class="col-sm-2 control-label">下次联系时间</label>
                                <div class="col-sm-10" style="width: 300px;">
									<input type="hidden" id="hidden-nextContactTime">
                                    <input type="text" class="form-control" id="create-nextContactTime2">
                                </div>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="create-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
									<input type="hidden" id="hidden-address">
                                    <textarea class="form-control" rows="1" id="edit-address"></textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveEditBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>客户列表</h3>
			</div>
		</div>
	</div>
	
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" id="query-name" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" id="query-owner" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司座机</div>
				      <input class="form-control" id="query-phone" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司网站</div>
				      <input class="form-control" id="query-website" type="text">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="queryBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="create-customer"><span class="glyphicon glyphicon-plus"></span> 新規作成</button>
				  <button type="button" class="btn btn-default" id="edit-CustomerBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="delete-CustomerBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkAll"/></td>
							<td>名称</td>
							<td>所有者</td>
							<td>公司座机</td>
							<td>公司网站</td>
						</tr>
					</thead>
					<tbody id="customerTBody">
						<!-- <tr>
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.html';">111</a></td>
							<td>zhangsan</td>
							<td>010-84846003</td>
							<td>http://www.com</td>
						</tr> -->
					</tbody>
				</table>
					
			</div>
			<div>
					<div id="customerPage"></div>
			</div>
			
		</div>
			
	</div>
</body>
</html>