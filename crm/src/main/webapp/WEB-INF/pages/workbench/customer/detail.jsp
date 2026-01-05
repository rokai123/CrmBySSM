<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

<script type="text/javascript">

	//默认情况下取消和保存按钮是隐藏的
	var cancelAndSaveBtnDefault = true;
	
	$(function(){
		$("#remark").focus(function(){
			if(cancelAndSaveBtnDefault){
				//设置remarkDiv的高度为130px
				$("#remarkDiv").css("height","130px");
				//显示
				$("#cancelAndSaveBtn").show("2000");
				cancelAndSaveBtnDefault = false;
			}
		});
		$("#cancelBtn").click(function(){
			//显示
			$("#cancelAndSaveBtn").hide();
			//设置remarkDiv的高度为130px
			$("#remarkDiv").css("height","90px");
			cancelAndSaveBtnDefault = true;
		});
		
		$("#remarkDiv00").on("mouseover",".remarkDiv",function(){
			$(this).children("div").children("div").show();
		});
		
		$("#remarkDiv00").on("mouseout",".remarkDiv",function(){
			$(this).children("div").children("div").hide();
		});
		
		$("#remarkDiv00").on("mouseover",".myHref",function(){
			$(this).children("span").css("color","red");
		});
		
		$("#remarkDiv00").on("mouseout",".myHref",function(){
			$(this).children("span").css("color","#E6E6E6");
		});

		$("#saveRemarkBtn").click(function(){
			let customerId = "${customer.id}";
			let noteContent = $("#remark").val();
			if(noteContent==""){
				alert("内容を入力してください。");
				return;
			}
			$.ajax({
				url:"workbench/customer/saveCreateCustomerRemark.do",
				data:{
					"customerId":customerId,
					"noteContent":noteContent
				},
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.code=="1"){
						let html = "";
						html +="<div class=\"remarkDiv\" id=\"remarkDiv_"+data.resultData.id+"\" style=\"height: 60px;\">";
						html +="<img title=\""+data.resultData.createBy+"\" src=\"image/user-thumbnail02.png\" style=\"width: 35px; height:35px;\">";
						html +="<div style=\"position: relative; top: -40px; left: 40px;\" >";
						html +="<h5>"+data.resultData.noteContent+"</h5>";
						html +="<font color=\"gray\">会社名</font> <font color=\"gray\">-</font> <b>${customer.name}</b> <small style=\"color: gray;\">"+data.resultData.createTime+"--"+data.resultData.createBy+"さんが作成しました</small>";
						html +="<div style=\"position: relative; left: 550px; top: -30px; height: 30px; width: 100px; display: none;\">";
						html +="<a class=\"myHref\" data-remark-id=\""+data.resultData.id+"\" name=\"editRemarkBtn\" href=\"javascript:void(0);\"><span class=\"glyphicon glyphicon-edit\" style=\"font-size: 20px; color: #E6E6E6;\"></span></a>";
						html +="&nbsp;&nbsp;&nbsp;&nbsp;";
						html +="<a class=\"myHref\" data-remark-id=\""+data.resultData.id+"\" name=\"removeRemarkBtn\" href=\"javascript:void(0);\"><span class=\"glyphicon glyphicon-remove\" style=\"font-size: 20px; color: #E6E6E6;\"></span></a>";
						html +="</div>";
						html +="</div>";
						html +="</div>";
						$("#remarkDiv01").after(html);
						$("#remark").val("");
					}else{
						alert(data.message);
					}
				}
			})
	
		});

		$("#remarkDiv00").on("click","a[name=\"removeRemarkBtn\"]",function(){ 
			let remarkId = $(this).data("remarkId");
			if(window.confirm("削除してもよろしいですか？")){
				$.ajax({
					url:"workbench/customer/deleteCustomerRemarkById.do",
					data:{
						"id":remarkId
					},
					type:"post",
					dataType:"json",
					success:function(data){
						if(data.code=="1"){
							$("#remarkDiv_"+remarkId).remove();
						}else{
							alert(data.message);
						}
					}
				})
			}
		});
		//修改备注的模态窗口
		$("#remarkDiv00").on("click","a[name=\"editRemarkBtn\"]",function(){
			
			let remarkId = $(this).data("remarkId");
			$("#saveRemarkIdInput").val(remarkId);
			$("#edit-noteContent").val($("#remarkDiv_"+remarkId+" h5").text());
			$("#edit-noteContent-Hidden").val($("#remarkDiv_"+remarkId+" h5").text());
			$("#editRemarkModal").modal("show");
		});

		$("#updateRemarkBtn").click(function(){
			let remarkId = $("#saveRemarkIdInput").val();
			let noteContent = $("#edit-noteContent").val();
			if(noteContent==""){
				alert("内容を入力してください");
				return;
			}
			if(noteContent==$("#edit-noteContent-Hidden").val()){
				alert("内容を変更していません");
				return;
			}
			$.ajax({
				url:"workbench/customer/saveEditCustomerRemark.do",
				data:{
					"id":remarkId,
					"noteContent":noteContent
				},
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.code=="1"){
						alert("更新が完了しました。");
						$("#editRemarkModal").modal("hide");
						$("#remarkDiv_"+remarkId+" h5").text(noteContent);
						$("#remarkDiv_"+remarkId+" small").text(data.resultData.editTime+"--${sessionScope.sessionUser.name}さんが編集しました");
					}else{
						alert(data.message);
					}
				}

			})
		});
	});
	
</script>

</head>
<body>
	<!-- 修改备注的模态窗口 -->
	<div class="modal fade" id="editRemarkModal" role="dialog">
		<!-- 备注的id -->
		<input type="hidden" id="remarkId">
        <div class="modal-dialog" role="document" style="width: 40%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">備考編集</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
						<!-- 将remarkId保存在隐藏域 -->
						<input type="hidden" id="saveRemarkIdInput"/>
                        <div class="form-group">
                            <label for="noteContent" class="col-sm-2 control-label">内容</label>
                            <div class="col-sm-10" style="width: 81%;">
								<input type="hidden" id="edit-noteContent-Hidden"/>
                                <textarea class="form-control" rows="3" id="edit-noteContent"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">閉じる</button>
                    <button type="button" class="btn btn-primary" id="updateRemarkBtn">更新</button>
                </div>
            </div>
        </div>
    </div>
	<!-- 删除联系人的模态窗口 -->
	<div class="modal fade" id="removeContactsModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 30%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">删除联系人</h4>
				</div>
				<div class="modal-body">
					<p>您确定要删除该联系人吗？</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal">删除</button>
				</div>
			</div>
		</div>
	</div>

    <!-- 删除交易的模态窗口 -->
    <div class="modal fade" id="removeTransactionModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 30%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title">删除交易</h4>
                </div>
                <div class="modal-body">
                    <p>您确定要删除该交易吗？</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">删除</button>
                </div>
            </div>
        </div>
    </div>
	
	<!-- 创建联系人的模态窗口 -->
	<div class="modal fade" id="createContactsModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="$('#createContactsModal').modal('hide');">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建联系人</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-contactsOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-contactsOwner">
								  <option>zhangsan</option>
								  <option>lisi</option>
								  <option>wangwu</option>
								</select>
							</div>
							<label for="create-clueSource" class="col-sm-2 control-label">来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-clueSource">
								  <option></option>
								  <option>广告</option>
								  <option>推销电话</option>
								  <option>员工介绍</option>
								  <option>外部介绍</option>
								  <option>在线商场</option>
								  <option>合作伙伴</option>
								  <option>公开媒介</option>
								  <option>销售邮件</option>
								  <option>合作伙伴研讨会</option>
								  <option>内部研讨会</option>
								  <option>交易会</option>
								  <option>web下载</option>
								  <option>web调研</option>
								  <option>聊天</option>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-surname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-surname">
							</div>
							<label for="create-call" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-call">
								  <option></option>
								  <option>先生</option>
								  <option>夫人</option>
								  <option>女士</option>
								  <option>博士</option>
								  <option>教授</option>
								</select>
							</div>
							
						</div>
						
						<div class="form-group">
							<label for="create-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-job">
							</div>
							<label for="create-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-mphone">
							</div>
						</div>
						
						<div class="form-group" style="position: relative;">
							<label for="create-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-email">
							</div>
							<label for="create-birth" class="col-sm-2 control-label">生日</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-birth">
							</div>
						</div>
						
						<div class="form-group" style="position: relative;">
							<label for="create-customerName" class="col-sm-2 control-label">客户名称</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-customerName" placeholder="支持自动补全，输入客户不存在则新建">
							</div>
						</div>
						
						<div class="form-group" style="position: relative;">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

                        <div style="position: relative;top: 15px;">
                            <div class="form-group">
                                <label for="edit-contactSummary" class="col-sm-2 control-label">联系纪要</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="3" id="edit-contactSummary">这个线索即将被转换</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
                                <div class="col-sm-10" style="width: 300px;">
                                    <input type="text" class="form-control" id="edit-nextContactTime" value="2017-05-01">
                                </div>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address1" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address1">北京大兴区大族企业湾</textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	

	<!-- 返回按钮 -->
	<div style="position: relative; top: 35px; left: 10px;">
		<a href="javascript:void(0);" onclick="window.history.back();"><span class="glyphicon glyphicon-arrow-left" style="font-size: 20px; color: #DDDDDD"></span></a>
	</div>
	
	<!-- 大标题 -->
	<div style="position: relative; left: 40px; top: -30px;">
		<div class="page-header">
			<h3>${customer.name} <small><a href="${customer.website}" target="_blank">${customer.website}</a></small></h3>
		</div>
		<div style="position: relative; height: 50px; width: 500px;  top: -72px; left: 700px;">
			<button type="button" class="btn btn-default" data-toggle="modal" data-target="#editCustomerModal"><span class="glyphicon glyphicon-edit"></span>編集</button>
			<button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span>削除</button>
		</div>
	</div>
	
	<br/>
	<br/>
	<br/>

	<!-- 详细信息 -->
	<div style="position: relative; top: -70px;">
		<div style="position: relative; left: 40px; height: 30px;">
			<div style="width: 300px; color: gray;">担当者</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${customer.owner}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">会社名</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${customer.name}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 10px;">
			<div style="width: 300px; color: gray;">会社Webサイト</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${customer.website}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">会社電話</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${customer.phone}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 20px;">
			<div style="width: 300px; color: gray;">作成者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${customer.createBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${customer.createTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 30px;">
			<div style="width: 300px; color: gray;">更新者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${customer.editBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${customer.editTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
        <div style="position: relative; left: 40px; height: 30px; top: 40px;">
            <div style="width: 300px; color: gray;">対応履歴</div>
            <div style="width: 630px;position: relative; left: 200px; top: -20px;">
                <b>
                     ${empty customer.contactSummary ? '&nbsp;' : customer.contactSummary}
                </b>
            </div>
            <div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
        </div>
        <div style="position: relative; left: 40px; height: 30px; top: 50px;">
            <div style="width: 300px; color: gray;">次回連絡日時</div>
            <div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${empty customer.nextContactTime?'&nbsp;':customer.nextContactTime}</b></div>
            <div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px; "></div>
        </div>
		<div style="position: relative; left: 40px; height: 30px; top: 60px;">
			<div style="width: 300px; color: gray;">取引先説明</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					 ${customer.description}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
        <div style="position: relative; left: 40px; height: 30px; top: 70px;">
            <div style="width: 300px; color: gray;">住所(詳細)</div>
            <div style="width: 630px;position: relative; left: 200px; top: -20px;">
                <b>
					 ${customer.address}
                </b>
            </div>
            <div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
        </div>
	</div>
	
	<!-- 备注 -->
	<div style="position: relative; top: 10px; left: 40px;" id="remarkDiv00">
		<div class="page-header" id="remarkDiv01">
			<h4>備考</h4>
		</div>
		
		<!-- 备注1 -->
		<c:forEach var="cr" items="${customerRemarkList}">
		<div class="remarkDiv" id="remarkDiv_${cr.id}" style="height: 60px;">
				<img title="${cr.createBy}" src="image/user-thumbnail02.png" style="width: 35px; height:35px;">
				<div style="position: relative; top: -40px; left: 40px;" >
					<h5>${cr.noteContent}</h5>
					<font color="gray">会社名</font> <font color="gray">-</font> <b>${customer.name}</b> <small style="color: gray;">${cr.editFlag=='1'?cr.editTime:cr.createTime}--${cr.editFlag=='1'?cr.editBy:cr.createBy}さんが${cr.editFlag=='1'?'編集しました':'作成しました'}</small>

					<div style="position: relative; left: 550px; top: -30px; height: 30px; width: 100px; display: none;">
						<a class="myHref" data-remark-id="${cr.id}" name="editRemarkBtn" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a class="myHref" data-remark-id="${cr.id}" name="removeRemarkBtn" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
					</div>
				</div>
		</div>
		</c:forEach>
		
		
		<div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
			<form role="form" style="position: relative;top: 10px; left: 10px;">
				<textarea id="remark" class="form-control" style="width: 850px; resize : none;" rows="2"  placeholder="備考を入力..."></textarea>
				<p id="cancelAndSaveBtn" style="position: relative;left: 690px; top: 10px; display: none;">
					<button id="cancelBtn" type="button" class="btn btn-default">キャンセル</button>
					<button type="button" class="btn btn-primary" id="saveRemarkBtn">保存</button>
				</p>
			</form>
		</div>
	</div>
	
	<!-- 交易 -->
	<div>
		<div style="position: relative; top: 20px; left: 40px;">
			<div class="page-header">
				<h4>商談</h4>
			</div>
			<div style="position: relative;top: 0px;">
				<table id="activityTable2" class="table table-hover" style="width: 900px;">
					<thead>
						<tr style="color: #B3B3B3;">
							<td>商談名</td>
							<td>金額</td>
							<td>ステージ</td>
							<td>確度</td>
							<td>成約予定日</td>
							<td>種別</td>
							<td></td>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tran" items="${tranVOList}">
							<tr>
								<td><a href="transaction/detail.html" style="text-decoration: none;">${tran.name}</a></td>
								<td>
									￥<fmt:formatNumber value="${tran.money}" pattern="#,##0"/>
								</td>
								<td>${tran.stage}</td>
								<td>${tran.possibility}%</td>
								<td>${tran.expectedDate}</td>
								<td>${tran.type}</td>
								<td><a href="javascript:void(0);" date-tran-id="${tran.id}" data-toggle="modal" data-target="#removeTransactionModal" style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>削除</a></td>
							</tr>
						</c:forEach>
						
					</tbody>
				</table>
			</div>
			
			<div>
				<a href="transaction/save.html" style="text-decoration: none;"><span class="glyphicon glyphicon-plus"></span>商談を新規作成</a>
			</div>
		</div>
	</div>
	
	<!-- 联系人 -->
	<div>
		<div style="position: relative; top: 20px; left: 40px;">
			<div class="page-header">
				<h4>取引先責任者</h4>
			</div>
			<div style="position: relative;top: 0px;">
				<table id="activityTable" class="table table-hover" style="width: 900px;">
					<thead>
						<tr style="color: #B3B3B3;">
							<td>氏名</td>
							<td>メール</td>
							<td>携帯電話</td>
							<td></td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><a href="contacts/detail.html" style="text-decoration: none;">李四</a></td>
							<td>lisi@bjpowernode.com</td>
							<td>13543645364</td>
							<td><a href="javascript:void(0);" data-toggle="modal" data-target="#removeContactsModal" style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>削除</a></td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<div>
				<a href="javascript:void(0);" data-toggle="modal" data-target="#createContactsModal" style="text-decoration: none;"><span class="glyphicon glyphicon-plus"></span>取引先責任者を新規作成</a>
			</div>
		</div>
	</div>
	
	<div style="height: 200px;"></div>
</body>
</html>