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
		
		/* $(".remarkDiv").mouseover(function(){
			$(this).children("div").children("div").show();
		});
		
		$(".remarkDiv").mouseout(function(){
			$(this).children("div").children("div").hide();
		}); */
		
		 // 鼠标移入，显示按钮		
		$(document).on("mouseenter", ".remarkDiv", function () {
		   // $(this).children().children("div:last").show();
			$(this).children("div").children("div").show();
		});
		 // 鼠标移出，隐藏按钮
		$(document).on("mouseleave", ".remarkDiv", function () {
		    //$(this).children().children("div:last").hide();
			$(this).children("div").children("div").hide();
		});
				
		// 鼠标移入动态元素
		$(document).on("mouseover", ".myHref", function(){
		    $(this).children("span").css("color","red");
		});

		// 鼠标移出
		$(document).on("mouseout", ".myHref", function(){
		    $(this).children("span").css("color","#E6E6E6");
		});


		//添加备注
		$('#saveCreateActivityRemarkBtn').click(function () { 
			let noteContent = $.trim($("#remark").val());
			if(noteContent == ""){
				alert("何も入力していません");
				return;
			}
			$.ajax({
				url:'workbench/activity/saveCreateActivityRemark.do',
				data:{
					'noteContent':noteContent,
					'activityId':'${activity.id}'
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					if(data.code == "1"){
						//清空输入框
						$("#remark").val("");
						let html = "";
					    html+="<div class=\"remarkDiv\" id=\"div_"+data.resultData.id+"\" style=\"height: 60px;\">";
						html+="<img title=\"${sessionScope.sessionUser.name}\" src=\"image/user-thumbnail.png\" style=\"width: 30px; height:30px;\">";
						html+="<div style=\"position: relative; top: -40px; left: 40px;\" >";
						html+="<h5>"+data.resultData.noteContent+"</h5>";
						html+="<font color=\"gray\">ﾏｰｹﾃｨﾝｸﾞｷｬﾝﾍﾟｰﾝ</font> <font color=\"gray\">-</font><b>${activity.name}</b> <small style=\"color: gray;\">"+data.resultData.createTime+"--${sessionScope.sessionUser.name}さんが作成した</small>";
						html+="<div style=\"position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;\">";
						html+= "<a class='myHref' name='editBtn' remarkId='"+data.resultData.id+"' href='javascript:void(0);'><span class='glyphicon glyphicon-edit' style='font-size: 20px; color: #E6E6E6;'></span></a>";
						html+= '&nbsp;&nbsp;&nbsp;&nbsp;';
						html+= "<a class='myHref' name='removeBtn' remarkId='"+data.resultData.id+"' href='javascript:void(0);'><span class='glyphicon glyphicon-remove' style='font-size: 20px; color: #E6E6E6;'></span></a>";
						html+="</div>";
						html+="</div>";
						html+="</div>";
						$("#page-header-id").after(html);  
					}
				}
			})

		});

		//删除备注
		$("#remarkDivList").on("click", "a[name='removeBtn']", function () {
			let remarkId = $(this).attr("remarkId");
			$.ajax({
				url:'workbench/activity/deleteActivityRemarkById.do',
				data:{
					'id':remarkId
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					if(data.code == "1"){
						//alert(data.message);
						//移除被删除备注的div
						$("#div_"+remarkId).remove();
					}else{
						alert(data.message);
					}
				}
			})
		})

		//修改备注
		$(remarkDivList).on("click", "a[name='editBtn']", function () {
			let remarkId = $(this).attr("remarkId");
			//选择div_remarkId元素内部所有层级的h5标签
			let noteContent = $("#div_"+remarkId+" h5").text();
			$("#saveRemarkIdInput").val(remarkId);
			$("#edit-noteContent").val(noteContent);
			$("#edit-noteContent-Hidden").val(noteContent);
			$("#editRemarkModal").modal("show");
		})

		//編集した備考内容を送信する
		$("#updateRemarkBtn").click(function () {
			let noteContent = $.trim($("#edit-noteContent").val());
			let noteContentHidden = $("#edit-noteContent-Hidden").val();
			let id = $("#saveRemarkIdInput").val();
			if(noteContent == ""){
				alert("何も入力していません");
				return;
			}
			//編集内容が変更されたかどうかを確認する
			if(noteContentHidden == noteContent){
				alert("何も変更していません");
				return;
			}
			$.ajax({
				url:'workbench/activity/saveEditActivityRemark.do',
				data:{
					'id':id,
					'noteContent':noteContent
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					if(data.code == "1"){
						alert(data.message);
						//更新备注的div
						$("#div_"+id+" h5").text(noteContent);
						//创建人改为修改人，创建时间改为修改时间
						$("#div_"+id+" small").text(data.resultData.editTime+"--${sessionScope.sessionUser.name}さんが編集した");
						$("#editRemarkModal").modal("hide");
					}else{
						alert(data.message);
					}
				}
			})
		})








	
	});// 页面加载完毕(尻尾)	
	
</script>

</head>
<body>
	
	<!-- 修改市场活动备注的模态窗口 -->
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

    

	<!-- 返回按钮 -->
	<div style="position: relative; top: 35px; left: 10px;">
		<a href="javascript:void(0);" onclick="window.history.back();"><span class="glyphicon glyphicon-arrow-left" style="font-size: 20px; color: #DDDDDD"></span></a>
	</div>
	
	<!-- 大标题 -->
	<div style="position: relative; left: 40px; top: -30px;">
		<div class="page-header">
			<h3>${activity.name}<small>${activity.startDate} ~ ${activity.endDate}</small></h3>
		</div>
		
	</div>
	
	<br/>
	<br/>
	<br/>

	<!-- 详细信息 -->
	<div style="position: relative; top: -70px;">
		<div style="position: relative; left: 40px; height: 30px;">
			<div style="width: 300px; color: gray;">所有者</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${activity.owner}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">ｷｬﾝﾍﾟｰﾝ名</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${activity.name}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>

		<div style="position: relative; left: 40px; height: 30px; top: 10px;">
			<div style="width: 300px; color: gray;">開始日</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${activity.startDate}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">終了日</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${activity.endDate}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 20px;">
			<div style="width: 300px; color: gray;">コスト</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${activity.cost}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 30px;">
			<div style="width: 300px; color: gray;">作成者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${activity.createBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${activity.createTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 40px;">
			<div style="width: 300px; color: gray;">更新者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${activity.editBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${activity.editTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 50px;">
			<div style="width: 300px; color: gray;">説明</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${activity.description}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
	</div>
	
	<!-- 备注 -->
	<div style="position: relative; top: 30px; left: 40px;" id="remarkDivList">
		<div class="page-header" id="page-header-id">
			<h4>備考</h4>
		</div>
		
		
	<!-- <foreach items="${activityRemarks}" var="remark">
    <div class="remarkDiv" style="height: 60px;">
		<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>${remark.noteContent}</h5>
				<font color="gray">市场活动</font> <font color="gray">-</font> <b>${activity.name}</b>
                <div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
                    <a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
      </div>
			</div>
		</div>
        </foreach> -->
    <!--備考欄1 -->
  <c:forEach items="${activityRemarks}" var="remark" >
		<div class="remarkDiv" id="div_${remark.id}" style="height: 60px;">
			<img title="${remark.createBy}" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>${remark.noteContent}</h5>
				<%-- <c:if test="${remark.editFlag=='1'}">
					<font color="gray">市场活动</font> <font color="gray">-</font> <b>${activity.name}</b> <small style="color: gray;"> ${remark.editTime} 由${remark.editBy }</small>
				</c:if>
				<c:if test="${remark.editFlag=='' || remark.editFlag == null}">
					<font color="gray">市场活动</font> <font color="gray">-</font> <b>${activity.name}</b> <small style="color: gray;"> ${remark.createTime} 由${remark.createBy }</small>
				</c:if> --%>
				<font color="gray">ﾏｰｹﾃｨﾝｸﾞｷｬﾝﾍﾟｰﾝ</font> <font color="gray">-</font> <b>${activity.name}</b> <small style="color: gray;"> ${remark.editFlag=='1'? remark.editTime : remark.createTime}--${remark.editFlag=='1'?remark.editBy:remark.createBy}さんが${remark.editFlag=='1'?'編集した':'作成した' }</small>
				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					<%--remarkId="${remark.id}" はカスタムタグ属性のため、JSで値を取得するにはjQueryのattr()メソッドを使用必須 --%>
					<a class="myHref" remarkId="${remark.id}" name="editBtn" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="myHref" remarkId="${remark.id}" name="removeBtn" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
				</div>
			</div>
		</div>
	</c:forEach>
	
		
		<div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
			<form role="form" style="position: relative;top: 10px; left: 10px;">
				<textarea id="remark" class="form-control" style="width: 850px; resize : none;" rows="2"  placeholder="備考を入力..."></textarea>
				<p id="cancelAndSaveBtn" style="position: relative;left: 700px; top: 10px; display: none;">
					<button id="cancelBtn" type="button" class="btn btn-default">キャンセル</button>
					<button type="button" class="btn btn-primary" id="saveCreateActivityRemarkBtn">保存</button>
				</p>
			</form>
		</div>
	</div>
	<div style="height: 200px;"></div>
</body>
</html>