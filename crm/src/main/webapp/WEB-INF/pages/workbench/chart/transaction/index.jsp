<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + "/";
 %>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/echars/echarts.min.js"></script>
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script>
	$(function() {
		//发送ajax请求
		$.ajax({
			url:"workbench/chart/transaction/queryCountOfTranGroupByStage",
			type:"get",
			dataType:"json",
			success:function(data){
				var chartDom = document.getElementById('main');
				var myChart = echarts.init(chartDom);
				var option;
				option = {
				title: {
					text: '商談分析'
				},
				tooltip: {
					trigger: 'item',
					formatter: '{a} <br/>{b} : {c}'
				},
				toolbox: {
					feature: {
					dataView: { readOnly: false }, // 数据视图
					restore: {},                   // 还原
					saveAsImage: {}                // 保存为图片
					}
				},
				legend: {
					data: data.map(item => item.name)
				},
				series: [
					{
					name: '商談',
					type: 'funnel',
					left: '10%',
					width: '80%',
					label: {
						formatter: '{b}'
					},
					labelLine: {
						show: false
					},
					itemStyle: {
						opacity: 0.7
					},
					emphasis: {
						label: {
						position: 'inside',
						formatter: '{b} ：{c}'
						}
					},
					data: data
					},
					
				]
				};
				myChart.setOption(option);
			}
		});
			
    });
	

	
</script>
</head>
<body>
		<div class="container-fluid">
		  <div class="row">
		    <div class="col-md-12 text-center">
		      <div id="main" style="width: 900px; height: 600px; margin: 0 auto;"></div>
		    </div>
		  </div>
		</div>

</body>
</html>