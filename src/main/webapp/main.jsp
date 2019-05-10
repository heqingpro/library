<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML >
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/source/jslib/ztree/jquery-1.8.0.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/source/jslib/ztree/jquery.easyui.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/source/jslib/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
</head>

<body>

<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="div0">
	<div id="main0" style="width: 100%; height:50%;"></div>
</div>

<div id="div1" style="float: left; width: 33%; height:50%;" align="center">
	<div id="main1" style="height:90%;"></div>
	<div>
		<strong style="font-size: 13px;">核号:</strong>
		<select id="cpu_id" style="width: 60px;"></select>
	</div>
</div>
<div id="div2" style="float: left; width: 34%; height:50%;" align="center">
	<div id="main2" style="height:90%;"></div>
	<div>
		<strong style="font-size: 13px;">类型:</strong>
		<select id="mem_id">
			<option value='mem' selected='selected'>物理内存</option>
			<option value='swap'>交换内存</option>
		</select>
	</div>
</div>
<div id="div3" style="float: left; width: 33%; height:50%;" align="center">
	<div id="main3" style="height:90%;"></div>
	<div>
		<strong style="font-size: 13px;">盘符:</strong>
		<select id="disk_id" style="width: 60px;"></select>
	</div>
</div>
<div id="div4" align="center" style="display: none;">
	<h1 style="font-size: 25px;">统计数据获取失败，请重启Tomcat服务器！</h1>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/source/echarts/echarts.min.js"></script>
<script type="text/javascript">

	//基于准备好的dom，初始化echarts实例
	var myChart0 = echarts.init(document.getElementById('main0'));
	var myChart1 = echarts.init(document.getElementById('main1'));
	var myChart2 = echarts.init(document.getElementById('main2'));
	var myChart3 = echarts.init(document.getElementById('main3'));
	
	//全局变量，用于存放统计信息
	var data;
	
	$.ajax({
		url : '${pageContext.request.contextPath}/systemController/getCountInfomations',
		dataType : 'json',
		success : function(result) {
			if (result.code == 0) {
				data = result.data;
				console.log(data);
				
				// 访问统计报表
				var data1 = new Array();
				var data2 = new Array();
				for(var i=0; i<data['counts'].length; i++) {
					data1.push(data['counts'][i]['id']);
					data2.push(data['counts'][i]['count']);
				}
			    var option0 = {
				    title: {
				        text: '最近15天访问量',
				        subtext: '单位: 次',
				        left: 'center',
				        top:0
				    },
				    tooltip: {
				        trigger: 'axis',
				        axisPointer: {
				            type: 'cross'
				        }
				    },
				    xAxis:  {
				        type: 'category',
				        boundaryGap: false,
				        data: data1
				    },
				    yAxis: {
				        type: 'value',
				        axisLabel: {
				            formatter: '{value}'
				        },
				        axisPointer: {
				            snap: true
				        }
				    },
				    visualMap: {
				        show: false,
				        dimension: 1,
				        pieces: [{
				            gte: 0,
				            lte: 10000,
				            color: '#0C76CD'
				        }, {
				            gt: 10000,
				            color: '#D53A35'
				        }]
				    },
				    series: [
				        {
				            name:'访问量',
				            type:'line',
				            smooth: true,
				            data: data2
				        }
				    ]
				};
				
				// CPU: 指定图表的配置项和数据
				var preCpu;
				for(var key in data['cpus']) {
					if(preCpu == null || preCpu =='') {
						preCpu = key;
						$("#cpu_id").append("<option value='"+key+"' selected='selected'>"+key+"</option>");
					} else {
						$("#cpu_id").append("<option value='"+key+"'>"+key+"</option>");
					}
				}
				var count = Object.keys(data['cpus']).length;
				var frequency = data['cpus'][preCpu]['frequency'];
			    var option1 = {
					    title: {
					        text: 'CPU使用情况',
					        subtext: count+'核CPU，单核'+frequency+'MHZ',
					        left: 'center',
					        top:0
					    },
					    tooltip : {
					        trigger: 'item',
					        formatter: "{a} <br/>{b} : {d}%"
					    },
					    legend: {
					        bottom: 15,
					        left: 'center',
					        data: ['用户使用率', '系统使用率', '当前空闲率']
					    },
					    series : [
					        {
					            name: '状态',
					            type: 'pie',
					            radius : '45%',
					            center: ['50%', '50%'],
					            selectedMode: 'single',
					            data:[
					                {value:data['cpus'][preCpu]['user'] * 100, name:'用户使用率'},
					                {value:data['cpus'][preCpu]['sys'] * 100, name:'系统使用率'},
					                {value:data['cpus'][preCpu]['idle'] * 100, name: '当前空闲率'}
					            ],
					            itemStyle: {
					                emphasis: {
					                    shadowBlur: 10,
					                    shadowOffsetX: 0,
					                    shadowColor: 'rgba(0, 0, 0, 0.5)'
					                }
					            }
					        }
					    ]
					};
					
					// 内存: 指定图表的配置项和数据
					var memTotal = data['memory']['memTotal'];
					var swapTotal = data['memory']['swapTotal'];
				    var option2 = {
						    title: {
						        text: '内存使用情况',
						        subtext: '物理内存'+memTotal+'MB，交换内存'+swapTotal+'MB',
						        left: 'center',
						        top:0
						    },
						    tooltip : {
						        trigger: 'item',
						        formatter: "{a} <br/>{b} : {d}%"
						    },
						    legend: {
						        bottom: 15,
						        left: 'center',
						        data: ['使用率', '空闲率']
						    },
						    series : [
						        {
						            name: '状态',
						            type: 'pie',
						            radius : '45%',
						            center: ['50%', '50%'],
						            selectedMode: 'single',
						            data:[
						                {value:data['memory']['memUsedPercent'] * 100, name:'使用率'},
						                {value:data['memory']['memFreePercent'] * 100, name:'空闲率'}
						            ],
						            itemStyle: {
						                emphasis: {
						                    shadowBlur: 10,
						                    shadowOffsetX: 0,
						                    shadowColor: 'rgba(0, 0, 0, 0.5)'
						                }
						            }
						        }
						    ]
						};
					
						// 磁盘: 指定图表的配置项和数据
						var preDisk;
						var total = 0;
						for(var key in data['disks']) {
							total = total + data['disks'][key]['total'];
							if(preDisk == null || preDisk =='') {
								preDisk = key;
								$("#disk_id").append("<option value='"+key+"' selected='selected'>"+key+"</option>");
							}  else {
								$("#disk_id").append("<option value='"+key+"'>"+key+"</option>");
							}
						}
						var count = Object.keys(data['disks']).length;
					    var option3 = {
							    title: {
							        text: '磁盘使用情况',
							        subtext: '共'+count+'块分区，总容量'+total+'GB',
							        left: 'center',
							        top:0
							    },
							    tooltip : {
							        trigger: 'item',
							        formatter: "{a} <br/>{b} : {d}%"
							    },
							    legend: {
							        bottom: 15,
							        left: 'center',
							        data: ['使用率', '空闲率', '损坏率']
							    },
							    series : [
							        {
							            name: '状态',
							            type: 'pie',
							            radius : '45%',
							            center: ['50%', '50%'],
							            selectedMode: 'single',
							            data:[
							                {value:data['disks'][preDisk]['usedPercent'] * 100, name:'使用率'},
							                {value:data['disks'][preDisk]['availPercent'] * 100, name:'空闲率'},
							                {value:data['disks'][preDisk]['unAvailPercent'] * 100, name:'损坏率'}
							            ],
							            itemStyle: {
							                emphasis: {
							                    shadowBlur: 10,
							                    shadowOffsetX: 0,
							                    shadowColor: 'rgba(0, 0, 0, 0.5)'
							                }
							            }
							        }
							    ]
							};
					
					// 使用刚指定的配置项和数据显示图表。
					myChart0.setOption(option0);
				    myChart1.setOption(option1);
				    myChart2.setOption(option2);
				    myChart3.setOption(option3);
				
			}else{
				$.messager.alert('提示', '统计数据加载异常！','error');
				$("#div0").attr("style", "display:none;");
				$("#div1").attr("style", "display:none;");
				$("#div2").attr("style", "display:none;");
				$("#div3").attr("style", "display:none;");
				$("#div4").attr("style", "display:block;");
			}
		},
		error: function (textStatus) {//请求失败后调用的函数
                $.messager.alert('提示', '统计数据加载异常！','error');
                $("#div0").attr("style", "display:none;");
				$("#div1").attr("style", "display:none;");
				$("#div2").attr("style", "display:none;");
				$("#div3").attr("style", "display:none;");
				$("#div4").attr("style", "display:block;");
            }
	});

	
	$("select#cpu_id").change(function(){
	    console.log($(this).val());
	    var id = $(this).val();
	    var count = Object.keys(data['cpus']).length;
		var frequency = data['cpus'][id]['frequency'];
	    var option1 = {
			    title: {
			        text: 'CPU使用情况',
			        subtext: count+'核CPU，单核'+frequency+'MHZ',
			        left: 'center',
			        top:0
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {d}%"
			    },
			    legend: {
			        bottom: 15,
			        left: 'center',
			        data: ['用户使用率', '系统使用率', '当前空闲率']
			    },
			    series : [
			        {
			            name: '状态',
			            type: 'pie',
			            radius : '45%',
			            center: ['50%', '50%'],
			            selectedMode: 'single',
			            data:[
			                {value:data['cpus'][id]['user'] * 100, name:'用户使用率'},
			                {value:data['cpus'][id]['sys'] * 100, name:'系统使用率'},
			                {value:data['cpus'][id]['idle'] * 100, name: '当前空闲率'}
			            ],
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                }
			            }
			        }
			    ]
			};
		myChart1.setOption(option1);
	});
	
	$("select#mem_id").change(function(){
	    console.log($(this).val());
	    var type = $(this).val();
	    var memTotal = data['memory']['memTotal'];
		var swapTotal = data['memory']['swapTotal'];
	    var option2 = {
			    title: {
			        text: '内存使用情况',
			        subtext: '物理内存'+memTotal+'MB，交换内存'+swapTotal+'MB',
			        left: 'center',
			        top:0
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {d}%"
			    },
			    legend: {
			        bottom: 15,
			        left: 'center',
			        data: ['使用率', '空闲率']
			    },
			    series : [
			        {
			            name: '状态',
			            type: 'pie',
			            radius : '45%',
			            center: ['50%', '50%'],
			            selectedMode: 'single',
			            data:[
			                {value:data['memory'][type+'UsedPercent'] * 100, name:'使用率'},
			                {value:data['memory'][type+'FreePercent'] * 100, name:'空闲率'}
			            ],
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                }
			            }
			        }
			    ]
			};
		myChart2.setOption(option2);
	});
	
	$("select#disk_id").change(function(){
	    var devName = $(this).val();
	    var total = 0;
		for(var key in data['disks']) {
			total = total + data['disks'][key]['total'];
		}
		var count = Object.keys(data['disks']).length;
	    var option3 = {
			    title: {
			        text: '磁盘使用情况',
			        subtext: '共'+count+'块分区，总容量'+total+'GB',
			        left: 'center',
			        top:0
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {d}%"
			    },
			    legend: {
			        bottom: 15,
			        left: 'center',
			        data: ['使用率', '空闲率', '损坏率']
			    },
			    series : [
			        {
			            name: '状态',
			            type: 'pie',
			            radius : '45%',
			            center: ['50%', '50%'],
			            selectedMode: 'single',
			            data:[
			                {value:data['disks'][devName]['usedPercent'] * 100, name:'使用率'},
			                {value:data['disks'][devName]['availPercent'] * 100, name:'空闲率'},
			                {value:data['disks'][devName]['unAvailPercent'] * 100, name:'损坏率'}
			            ],
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                }
			            }
			        }
			    ]
			};
		myChart3.setOption(option3);
	});
    
    
</script>
</body>
</html>
