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
<link href="CSS/clueDetail.css" type="text/css" rel="stylesheet" />
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
		
		// $(".remarkDiv").mouseover(function(){
		// 	$(this).children("div").children("div").show();
		// });
		$("#remarkOuterDiv").on("mouseover",".remarkDiv",function(){
			$(this).children("div").children("div").show();
		});
		
		// $(".remarkDiv").mouseout(function(){
		// 	$(this).children("div").children("div").hide();
		// });
		$("#remarkOuterDiv").on("mouseout",".remarkDiv",function(){
			$(this).children("div").children("div").hide();
		});
		
		// $(".myHref").mouseover(function(){
		// 	$(this).children("span").css("color","red");
		// });
		$("#remarkOuterDiv").on("mouseover",".myHref",function(){
			$(this).children("span").css("color","red");
		});
		
		// $(".myHref").mouseout(function(){
		// 	$(this).children("span").css("color","#E6E6E6");
		// });
		$("#remarkOuterDiv").on("mouseout",".myHref",function(){
			$(this).children("span").css("color","#E6E6E6");
		});

		//新規したリード備考を保存するボタンをクリックイベントをバインドする
		$("#saveClueRemarkBtn").click(function(){
			let clueId = "${clue.id}";
	    let noteContent = $.trim($("#remark").val());
			if(noteContent == ""){
				alert("リード備考を入力してください");
				return;
			}
			$.ajax({
				url:"workbench/clue/saveCreateClueRemark.do",
				data:{
					"clueId":clueId,
					"noteContent":noteContent
				},
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.code=="1"){
						//成功
						//添加成功之后，清空输入框
						$("#remark").val("");
						let html = "";
						html +="<div class=\"remarkDiv\" id=\"remarkDiv_"+data.resultData.id+"\" style=\"height: 60px;\">";
						html +="<img title=\""+data.resultData.createBy+"\" src=\"image/user-thumbnail.png\" style=\"width: 30px; height:30px;\">";
						html +="<div style=\"position: relative; top: -40px; left: 40px;\" >";
						html +="<h5>"+data.resultData.noteContent+"</h5>";
						html +="<font color=\"gray\">リード</font> <font color=\"gray\">-</font> <b>${clue.fullname}${clue.appellation}-${clue.company}</b> <small style=\"color: gray;\"> 2017-01-22 10:10:10 由zhangsan</small>";
						html +="<div style=\"position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;\">";
						html +="<a class=\"myHref\" data-remark-id=\""+data.resultData.id+"\" name=\"Clue_edit\" href=\"javascript:void(0);\"><span class=\"glyphicon glyphicon-edit\" style=\"font-size: 20px; color: #E6E6E6;\"></span></a>";
						html +="&nbsp;&nbsp;&nbsp;&nbsp;";
						html +="<a class=\"myHref\" data-remark-id=\""+data.resultData.id+"\" name=\"Clue_remove\" href=\"javascript:void(0);\"><span class=\"glyphicon glyphicon-remove\" style=\"font-size: 20px; color: #E6E6E6;\"></span></a>";
						html +="</div>";
						html +="</div>";
						html +="</div>";
						console.log(html);
						$("#remarkDiv").before(html);

					}else{
                        alert(data.message);
                    }
				}

			})
		});
		
		$("#remarkOuterDiv").on("click","a[name='Clue_remove']",function(){ 
			let id = $(this).data("remark-id")
			//$("#remarkDiv_"+id).remove();
			$.ajax({
				url:"workbench/clue/removeClueRemarkById.do",
				data:{
					"id":id
				},
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.code=="1"){
						//成功
						//添加成功之后，清空输入框
						$("#remarkDiv_"+id).remove();
					}else{
                        alert(data.message);
                    }
				}
			})
		});

		//关联市场活动按钮点击,弹出模态窗口
		$("#btnRelateActivity").click(function(){ 
			$("#tBody").html("");
			$("#activitySearchInput").val("");
			$("#bundModal").modal("show");

		});

		//市场活动搜索框输入内容，键盘弹起触发动态查询
		$("#activitySearchInput").keyup(function(){
			let activityName = $(this).val();
			let clueId = "${clue.id}"
			if(activityName == ""){
				$("#tBody").html("");
				return;
			}
			$.ajax({
				url:"workbench/clue/queryActivityByNameAndClueId.do",
				data:{
					"name":activityName,
					"clueId":clueId
				},
				type:"post",
				dataType:"json",
				success:function(data){
					let html = "";
					$.each(data,function(i,activity){
						html += "<tr>";
						html += "<td><input type=\"checkbox\" name=\"activity\" value=\""+activity.id+"\"/></td>";
						html +="<td>"+activity.name+"</td>"
						html += "<td>"+activity.startDate+"</td>";
						html += "<td>"+activity.endDate+"</td>";
						html += "<td>"+activity.owner+"</td>";
					})
					$("#tBody").html(html);
				}
			})
		})

		//实现框点击全选复选框改变所有行的复选框的状态
		$("#checkAll").click(function(){
			$("input[name='activity']").prop("checked",this.checked);
		});
		//实现点击某行复选框，全选框也选中或取消选中
		$("#tBody").on("click","input[name='activity']",function(){
			$("#checkAll").prop("checked",$("input[name='activity']").length==$("input[name='activity']:checked").length);
		});

		//添加市场活动关联
		$("#btnSaveRelateActivity").click(function(){ 
			let clueId = "${clue.id}"
			let activityIds = [];
			if($("input[name='activity']:checked").length==0){
				alert("関連付けたいキャンペーンを選択してください");
				return;
			}
			$("input[name='activity']:checked").each(function(index,activity){
				activityIds.push(activity.value);
			});
			$.ajax({
				url:"workbench/clue/saveCreateActivityRelations.do",
				data:{
					"clueId":clueId,
					"ids":activityIds
				},
				type:"post",
				traditional: true,  // 添加这行，重要！
				dataType:"json",
				success:function(data){
					if(data.code=="1"){
						//成功
						//activityTbody
						let html = "";
						$.each(data.resultData,function(i,activity){
							
							html += "<tr id=\"tr_"+activity.id+"\">";
							html += "<td>"+activity.name+"</td>";
							html += "<td>"+activity.startDate+"</td>";
							html += "<td>"+activity.endDate+"</td>";
							html += "<td>"+activity.owner+"</td>";
							html += "<td><a href=\"javascript:void(0);\" name=\"removeBund\" data-activity-id=\""+activity.id+"\" style=\"text-decoration: none;\"><span class=\"glyphicon glyphicon-remove\"></span>解除关联</a></td>";
							html += "</tr>";
						})
						$("#activityTbody").append(html);
						alert("关联市场活动成功");
						//关闭模态窗口
						$("#bundModal").modal("hide");
					}else{
                        alert(data.message);
                    }
				}
			})
		});

		//解除关联
		$("#activityTbody").on("click","a[name='removeBund']",function(){
			let acId = $(this).data("activity-id")
			//$("#tr_"+acId).remove();
			if(window.confirm("関連解除よろしいですか")){
				$.ajax({
					url:"workbench/clue/removeClueActivityRelation.do",
					data:{
						"clueId":"${clue.id}",
						"activityId":acId
					},
					type:"post",
					dataType:"json",
					success:function(data){
						if(data.code=="1"){
							//成功
							//activityTbody
							$("#tr_"+acId).remove();
						}else{
                            alert(data.message);
                        }
					}
				});
			}
		});


		//为转换按钮添加点击事件，跳转到转换页面
		$("#btnToConvert").click(function (){
			window.location.href="workbench/clue/toConvert.do?clueId=${clue.id}"
		});
		
	});
	
</script>

</head>
<body>
	<!-- <!-- マーケティングキャンペーン紐付け用モーダルウィンドウ -->
	<div class="modal fade" id="bundModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">キャンペーン紐付け</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" id="activitySearchInput" class="form-control" style="width: 400px;" placeholder="キャンペーン名を入力してください（部分一致検索可）">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td><input type="checkbox" id="checkAll"/></td>
								<td>キャンペーン名</td>
								<td>開始日</td>
								<td>終了日</td>
								<td>担当者</td>
								<td></td>
							</tr>
						</thead>
						<tbody id="tBody">
							
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">キャンセル</button>
					<button type="button" class="btn btn-primary" id="btnSaveRelateActivity">紐付け</button>
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
			<h3>${clue.fullname}${clue.appellation} <small>${clue.company}</small></h3>
		</div>
		<div style="position: relative; height: 50px; width: 500px;  top: -72px; left: 700px;">
			<button type="button" class="btn btn-default" id="btnToConvert"><span class="glyphicon glyphicon-retweet"></span> リード変換</button>
			
		</div>
	</div>
	
	<br/>
	<br/>
	<br/>

	<!-- 详细信息 -->
	<div style="position: relative; top: -70px;">
		<div style="position: relative; left: 40px; height: 30px;">
			<div style="width: 300px; color: gray;">氏名</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.fullname}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">担当者</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.owner}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 10px;">
			<div style="width: 300px; color: gray;">会社名</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.company}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">役職</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.job}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 20px;">
			<div style="width: 300px; color: gray;">メールアドレス</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.email}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">会社電話</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.phone}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 30px;">
			<div style="width: 300px; color: gray;">会社webサイト</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.website}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">携帯電話</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.mphone}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 40px;">
			<div style="width: 300px; color: gray;">リードステータス</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.state}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">リードソース</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.source}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 50px;">
			<div style="width: 300px; color: gray;">作成者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${clue.createBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${clue.createTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 60px;">
			<div style="width: 300px; color: gray;">更新者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${clue.editBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${clue.editTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 70px;">
			<div style="width: 300px; color: gray;">リード説明</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${clue.description}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 80px;">
			<div style="width: 300px; color: gray;">対応履歴</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${clue.contactSummary}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 90px;">
			<div style="width: 300px; color: gray;">次回連絡日時</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.nextContactTime}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px; "></div>
		</div>
        <div style="position: relative; left: 40px; height: 30px; top: 100px;">
            <div style="width: 300px; color: gray;">住所(詳細)</div>
            <div style="width: 630px;position: relative; left: 200px; top: -20px;">
                <b>
                    ${clue.address}
                </b>
            </div>
            <div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
        </div>
	</div>
	
	<!-- 备注 -->
	<div style="position: relative; top: 40px; left: 40px;" id="remarkOuterDiv">
		<div class="page-header">
			<h4>備考</h4>
		</div>
		
		<!-- 备注1 -->
		<c:forEach items="${clueRemarks}" var="remark"> 
			<div class="remarkDiv" id="remarkDiv_${remark.id}" style="height: 60px;">
					<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
					<div style="position: relative; top: -40px; left: 40px;" >
							<h5>${remark.noteContent}</h5>
							<font color="gray">リード</font> <font color="gray">-</font> <b>${clue.fullname}${clue.appellation}-${clue.company}</b> <small style="color: gray;"> 2017-01-22 10:10:10 由zhangsan</small>
							<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
								<a class="myHref" data-remark-id="${remark.id}" name="Clue_edit" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="myHref" data-remark-id="${remark.id}" name="Clue_remove" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
							</div>
					</div>
			</div>
		</c:forEach>
		<!-- 备注2 -->
		<!-- <div class="remarkDiv" style="height: 60px;">
			<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>呵呵！</h5>
				<font color="gray">线索</font> <font color="gray">-</font> <b>李四先生-公司</b> <small style="color: gray;"> 2017-01-22 10:20:10 由zhangsan</small>
				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
				</div>
			</div>
		</div> -->
		
		<div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
			<form role="form" style="position: relative;top: 10px; left: 10px;">
				<textarea id="remark" class="form-control" style="width: 850px; resize : none;" rows="2"  placeholder="備考を入力..."></textarea>
				<p id="cancelAndSaveBtn" style="position: relative;left: 730px; top: 10px; display: none;">
					<button id="cancelBtn" type="button" class="btn btn-default">閉じる</button>
					<button type="button" class="btn btn-primary" id="saveClueRemarkBtn">保存</button>
				</p>
			</form>
		</div>
	</div>
	<!-- 修改线索备注的模态窗口 -->
	<div class="modal fade" id="editRemarkModal" role="dialog">
		<!-- 备注的id -->
		<input type="hidden" id="remarkId">
        <div class="modal-dialog" role="document" style="width: 40%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">修改备注</h4>
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
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="updateRemarkBtn">更新</button>
                </div>
            </div>
        </div>
    </div>
	<!-- 市场活动 -->
	<div>
		<div style="position: relative; top: 60px; left: 40px;">
			<div class="page-header">
				<h4>ﾏｰｹﾃｨﾝｸﾞｷｬﾝﾍﾟｰﾝ</h4>
			</div>
			<div style="position: relative;top: 0px;">
				<div class="table-wrapper" style="width: 900px;">
				    <table class="table table-hover" style="width: 900px;">
				        <thead>
				            <tr style="color: #B3B3B3;">
				                <td>キャンペン名</td>
				                <td>開始日</td>
				                <td>終了日</td>
				                <td>担当者</td>
				                <td></td>
				            </tr>
				        </thead>
				        <!-- tbody 保持不变 -->
				        <tbody id="activityTbody">
				            <c:forEach items="${activities}" var="activity"> 
				            <tr id="tr_${activity.id}">
				                <td>${activity.name}</td>
				                <td>${activity.startDate}</td>
				                <td>${activity.endDate}</td>
				                <td>${activity.owner}</td>
				                <td>
				                    <a href="javascript:void(0);" name="removeBund"
				                       data-activity-id="${activity.id}"
				                       style="text-decoration: none;">
				                        <span class="glyphicon glyphicon-remove"></span>紐付け解除
				                    </a>
				                </td>
				            </tr>
				            </c:forEach>
				        </tbody>
				    </table>
				</div>
			</div>
			<div>
				<a href="javascript:void(0);" id="btnRelateActivity" style="text-decoration: none;"><span class="glyphicon glyphicon-plus"></span>キャンペーン紐付け</a>
			</div>
		</div>
	</div>
	
	
	<div style="height: 200px;"></div>
</body>
</html>