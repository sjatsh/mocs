var CURRENT_WIDTH = 0, CURRENT_HEIGHT = 0;
$(document).ready(function() {
	//i18n.options.lng="en";
	//获取数据
	//getMapData();
	getMapPoint();
	
	//事件绑定
	//点击显示事件
	$(".point").find(".pointImg").click(function() {
		if($(this).parent().find(".pointInfo .mapHref").attr("node_id") != "") {
			$(this).parent().siblings().find(".pointInfo").addClass("hidden");
			$(this).parent().find(".pointInfo").toggleClass("hidden");
		}
		if(!$(this).parent().find(".pointInfo").hasClass("hidden")) {
			$(this).parent().css("z-index", "3").siblings(".point:not(.small)").css("z-index", "2");
		}
	});
	//点击跳转事件
	$(".point").find(".mapHref").click(function() {
		SetCookie("defNode", $(this).attr("node_id"));
		location.href = "../index.faces";
	});
	dataTranslate("map", function(t) { $("span").i18n();},"../static/i18n");	
	$.autosizeInit(1920,1080,800,500);
	$.autosizeAll();
	//加载动画
	$.autosizeReturn(mapAnimate);
	$.autosizeMore();
});

//获取世界地图数据
function getMapData() {
	var error;
	dataTranslate("map", function(t) {
		error = t("top.error");
	},"../static/i18n");
	
	var apiUrl = "";
	//调用接口
	$.ajax({
		async: false,
		type: "GET",
		url: apiUrl,
		cache: false,
		dataType: "json",
		success: function(data) {
			if(data.success) {
				//清空
				$(".mapDatas").empty();
				//循环绘制
				var dataContent = data.content;
				for(var i = 0; i < dataContent.length; i++) {
					showData(dataContent[i].xAxis, dataContent[i].yAxis, dataContent[i].nodeName, dataContent[i].runNum, dataContent[i].idleNum, dataContent[i].repairNum);
				}
			}
			else {
				console.log(error + data.msg);
			}
		},
		error: function() {
			//测试数据
			var testData = {"content":[{"yAxis":"100","idleNum":"50","runNum":"10","nodeName":"汉偌威","repairNum":"1","xAxis":"100"},{"yAxis":"300","idleNum":"60","runNum":"20","nodeName":"非洲","repairNum":"1","xAxis":"200"},{"yAxis":"400","idleNum":"70","runNum":"30","nodeName":"俄罗斯","repairNum":"1","xAxis":"300"}],"success":true,"msg":"调用成功"};

			//清空
			$(".mapDatas").empty();
			//循环绘制
			var dataContent = testData.content;
			for(var i = 0; i < dataContent.length; i++) {
				showData(dataContent[i].xAxis, dataContent[i].yAxis, dataContent[i].nodeName, dataContent[i].runNum, dataContent[i].idleNum, dataContent[i].repairNum);
			}
		}
	});
}
//获取地图数据
function getMapPoint() {
	var error;
	dataTranslate("map", function(t) {
		error = t("top.error");
	},"../static/i18n");
	var apiUrl = "../WSUserService/getMapdata.json";
	//var apiUrl = "http://10.10.60.98:8080/machinearchive/MachineService/getChinaData.json";
	//调用接口
	$.ajax({
		async: false,
		type: "GET",
		url: apiUrl,
		cache: false,
		dataType: "json",
		success: function(data) {
			//test
			//data = {"content":[{"yxs":0.0,"nodeId":"","zs":15.0,"userName":"三鼎机电","y":739.00635,"x":1290.2704},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"上海中英阀门管件有限公司","y":1552.6698,"x":-1455.6318},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"上海凯佑辉精密机械有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"上海华晋精密机械制造有限公司","y":661.311,"x":1291.2725},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"上海华讯汽车配件有限公司","y":1552.6698,"x":-1455.6318},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"上海天竺机械刀片有限公司","y":1552.6698,"x":-1455.6318},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"上海宝山液压油缸有限公司","y":661.2616,"x":1289.6697},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"上海施宸","y":675.0454,"x":1291.2035},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"上海旺红五金电器有限公司","y":668.34796,"x":1282.9083},{"yxs":0.0,"nodeId":"2c9080843f210eba013f215e5fb40002","zs":8.0,"userName":"上海研究院车间","y":665.32684,"x":1292.2323},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"上海科曼精密机械有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"上海美铁有限公司","y":666.84265,"x":1296.3441},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"上海翼正机械制造有限公司","y":666.5381,"x":1296.3346},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"上海迈壹","y":662.1719,"x":1286.7375},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"上海迈壹实业有限公司","y":1552.6698,"x":-1455.6318},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"上海项禹","y":675.0454,"x":1291.2035},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"上海高斯通船舶配件有限公司","y":666.84265,"x":1296.3441},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"东新动力有限公司","y":757.73206,"x":1273.8724},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"东莞市兴胜气动元件厂","y":899.1467,"x":1116.7284},{"yxs":0.0,"nodeId":"","zs":3.0,"userName":"东莞市志业机电有限公司（经销商）","y":899.1467,"x":1116.7284},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"东莞市捷昌五金制品有限公司","y":899.1467,"x":1116.7284},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"东莞懋盛五金制品厂","y":899.1467,"x":1116.7284},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"个体户","y":696.24756,"x":1262.3154},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"中国兵器工业集团北京北方车辆厂","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"2c9080844ca25b42014ca73b7b060002","zs":2.0,"userName":"丰然阀门有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"丹东凤城宏图美康增压器有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"丹东润成机械厂","y":413.73297,"x":1357.1835},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"丹棱凌云精机模具有限公司","y":700.6793,"x":884.89435},{"yxs":3.0,"nodeId":"2c9080844be3a021014c22e79e1a0017","zs":5.0,"userName":"丹棱县和兴机械加工厂","y":700.6793,"x":884.89435},{"yxs":1.0,"nodeId":"2c9080844be3a021014c91bfccc300bd","zs":1.0,"userName":"云华五金机械厂","y":688.23956,"x":1267.842},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"伯恩光学深圳有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"余姚市大中机床物资有限公司","y":707.4222,"x":1290.0984},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"余姚市陆埠鼎盛五金厂","y":704.66833,"x":1292.9186},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"佛山市柏盛联嘉机械有限公司","y":899.251,"x":1102.478},{"yxs":0.0,"nodeId":"2c9080843ef6eff3013ef827ae9a0001","zs":2.0,"userName":"佛山广物机电有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"佛山里水逾奥气动元件有限公司","y":894.835,"x":1102.7249},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"信博翔机械加工有限公司","y":450.19113,"x":1200.3567},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"入库（南方机床集团有限公司）","y":739.00635,"x":1290.2704},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"入库（江苏佰易机电有限公司）","y":630.7435,"x":1256.3939},{"yxs":0.0,"nodeId":"2c9080844be3a021014c73fdd01d00b2","zs":1.0,"userName":"兰州丰瑞机械设备有限公司","y":529.0346,"x":892.5541},{"yxs":2.0,"nodeId":"","zs":2.0,"userName":"兴奥管阀有限公司","y":757.73206,"x":1273.8724},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"凤城天博机械有限公司","y":413.73297,"x":1357.1835},{"yxs":0.0,"nodeId":"","zs":7.0,"userName":"劲达泰机械制造有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"2c9080844b29b502014b48fc968e0004","zs":1.0,"userName":"包头市天隆永磁电机制造有限责任公司","y":402.01505,"x":1032.1926},{"yxs":0.0,"nodeId":"2c9080844be3a021014c4485b81b0050","zs":1.0,"userName":"南京兴润机械厂","y":646.11334,"x":1230.6241},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"南京科锐挤出机械有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"南方机床集团有限公司","y":727.03046,"x":1249.9989},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"南通东源数控机床有限公司","y":644.7193,"x":1278.3777},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"厦门大禾机械有限公司","y":857.26416,"x":1215.4282},{"yxs":0.0,"nodeId":"2c9080844be3a021014c9e19814100e0","zs":1.0,"userName":"厦门市海乐信精密机械有限公司","y":853.7918,"x":1212.4656},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"厦门闽虹金属有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":3.0,"userName":"吉林省豪门科技有限公司","y":308.83792,"x":1406.6935},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"吴忠市圣昌工贸有限公司","y":474.051,"x":945.8343},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"吴江市申华机械厂","y":663.8513,"x":1271.2593},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"商丘金振源电子科技有限公司","y":575.5663,"x":1159.8112},{"yxs":2.0,"nodeId":"","zs":4.0,"userName":"嘉善力达复合轴承有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"嘉航实业有限公司","y":899.1467,"x":1116.7284},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"四川大金不锈钢有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":79.0,"userName":"四川巴中渝翔机械配件制造有限公司","y":657.79895,"x":966.9872},{"yxs":0.0,"nodeId":"2c9080844be3a021014c22ef058f001c","zs":5.0,"userName":"四川精锐机电有限公司","y":677.06805,"x":901.3545},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"四川青神永亨机械有限公司","y":700.6793,"x":884.89435},{"yxs":0.0,"nodeId":"","zs":10.0,"userName":"埃尼斯阀门有限公司","y":757.73206,"x":1273.8724},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"大洋精锻有限公司","y":610.18695,"x":1268.1747},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"大连市金州区腾毅科技","y":439.8169,"x":1296.0541},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"大连顺诚模具制造有限公司","y":439.8169,"x":1296.0541},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"天安汽车部件有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"天津个人","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"天津沈创机械贸易有限公司（展机）","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"天津神封科技发展有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"太仓翔远模具塑胶","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"太原信丰通机电贸易有限公司（展机）","y":367.92264,"x":1328.4851},{"yxs":1.0,"nodeId":"","zs":1.0,"userName":"太原市信丰通机电贸易有限公司","y":477.56357,"x":1089.6761},{"yxs":0.0,"nodeId":"2c9080844be3a021014c91bf298700bc","zs":3.0,"userName":"太原晋博瑞机械有限公司","y":474.88348,"x":1089.4302},{"yxs":0.0,"nodeId":"2c9080844b63bd99014b67c9cbbf0001","zs":38.0,"userName":"太原理工大学","y":478.08826,"x":1088.662},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"宁晋合一机械有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"宁波东联机械有限公司","y":707.4222,"x":1290.0984},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"宁波国云轴承有限公司","y":707.4222,"x":1290.0984},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"宁波塞德森减振系统有限公司","y":707.4222,"x":1290.0984},{"yxs":0.0,"nodeId":"","zs":3.0,"userName":"宁波塞维思机械有限公司","y":704.66833,"x":1292.9186},{"yxs":0.0,"nodeId":"","zs":4.0,"userName":"宁波宏特工贸有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"宁波宝葫芦华韵消防器械有限公司","y":707.4222,"x":1290.0984},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"宁波市文昌金属制品有限公司","y":707.4222,"x":1290.0984},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"宁波市鄞州盛顺机械配件厂","y":704.66833,"x":1292.9186},{"yxs":0.0,"nodeId":"","zs":6.0,"userName":"宁波惠丰模具有限公司","y":707.4222,"x":1290.0984},{"yxs":0.0,"nodeId":"ff8080813d6bcb9a013db01673650041","zs":6.0,"userName":"宁波方力机床工贸有限公司","y":707.4222,"x":1290.0984},{"yxs":0.0,"nodeId":"","zs":31.0,"userName":"宁波方力机床工贸有限公司（4S店）","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"宁波明鑫电器机械制造有限公司","y":707.4222,"x":1290.0984},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"宁波晟源机电科技有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":25.0,"userName":"宁波海格威流体机械连接件有限公司","y":704.66833,"x":1292.9186},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"宁波瑞升密封材料有限公司","y":704.66833,"x":1292.9186},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"宁波生命力通讯科技有限公司","y":707.4222,"x":1290.0984},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"宁波鼎泰机床有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"2c9080844be3a021014c449e4dc10058","zs":4.0,"userName":"宁海双海机械制造有限公司","y":707.4222,"x":1290.0984},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"客户 ","y":550.64984,"x":1120.6361},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"客户（无锡沈机机电设备有限公司）","y":656.6982,"x":1265.5275},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"展机","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"展机（南京广德数控设备有限公司）","y":642.60754,"x":1230.8243},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"展机（江苏佰易机电有限公司）","y":630.7435,"x":1256.3939},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"展机（江苏省徐州国际会展中心）","y":580.2599,"x":1194.5632},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"山东众冶集团变速机械有限公司","y":489.046,"x":1290.6609},{"yxs":0.0,"nodeId":"","zs":5.0,"userName":"山东巨能","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"2c9080844be3a021014c9e1e45a200e2","zs":1.0,"userName":"山东日照山森数控技术有限公司","y":555.9098,"x":1192.7909},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"山西东业机械制造有限公司","y":477.56357,"x":1089.6761},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"平原县祥瑞机械有限公司","y":511.9015,"x":1189.7438},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"平湖市日灸机械制造有限公司","y":679.9873,"x":1274.976},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"广州市峰达机械有限公司","y":895.9946,"x":1105.7083},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"广州数控设备有限公司","y":899.251,"x":1102.478},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"库存(上海鼎亚精密机械设备有限公司)","y":662.1719,"x":1286.7375},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"廊坊大庆汽车配件有限公司","y":444.42783,"x":1178.4573},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"徐州市圣旺达液压机械有限公司","y":580.2599,"x":1194.5632},{"yxs":2.0,"nodeId":"","zs":4.0,"userName":"慈溪市鸿运实业有限公司","y":704.66833,"x":1292.9186},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"慈溪振华机械有限公司","y":707.4222,"x":1290.0984},{"yxs":2.0,"nodeId":"2c9080844be3a021014c20fd6c6d000c","zs":6.0,"userName":"慈溪鸿运电器有限公司","y":697.6804,"x":1293.2498},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"扬州市天海机床销售有限公司","y":632.8956,"x":1244.6887},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"招远乐华齿轮有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"文登市机电设备厂有限公司","y":501.854,"x":1305.0465},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"新乡市沈机机床有限公司","y":550.64984,"x":1120.6361},{"yxs":0.0,"nodeId":"2c9080844be3a021014c447dc616004e","zs":4.0,"userName":"无锡亿利达机械有限公司","y":656.6982,"x":1265.5275},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"无锡瑞德元机械有限公司","y":656.6982,"x":1265.5275},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"日照市时正锻压有限公司","y":545.65717,"x":1245.7507},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"日照续顺机械","y":545.65717,"x":1245.7507},{"yxs":0.0,"nodeId":"","zs":6.0,"userName":"日照金港活塞有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":8.0,"userName":"日照金马工业集团股份有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"昆山恒顺精密金属制造有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"晟璟机电","y":662.1719,"x":1286.7375},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"杭州国盛机电设备有限公司","y":693.79913,"x":1261.1375},{"yxs":0.0,"nodeId":"","zs":3.0,"userName":"杭州市机电设备有限公司","y":693.79913,"x":1261.1375},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"杭州陆氏机械有限公司","y":696.24756,"x":1262.3154},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"柘城县新源超硬材料制品有限公司","y":575.5663,"x":1159.8112},{"yxs":0.0,"nodeId":"","zs":5.0,"userName":"武汉三鑫达精密技术有限公司","y":688.1062,"x":1132.2666},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"江北永创金刚石有限公司","y":707.4222,"x":1290.0984},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"江苏中力齿轮有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"江苏先科表面工程技术有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"江苏协易机床城有限公司","y":656.6982,"x":1265.5275},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"江苏南通乐士机械有限公司","y":649.68,"x":1294.6311},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"江苏太仓苏安消防设备有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"江苏斯勒威机床有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"江阴创捷电气设备有限公司","y":656.6982,"x":1265.5275},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"江阴格瑞特金属制品公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"2c9080844545b0470145cb469b7d0029","zs":16.0,"userName":"沈一车轴杠车间","y":367.4265,"x":1331.4789},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"沈阳245厂","y":362.52927,"x":1335.8112},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"沈阳东利钛业有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"沈阳机床集团吉林省销售服务有限公司","y":307.80273,"x":1378.5026},{"yxs":0.0,"nodeId":"","zs":4.0,"userName":"沈阳机床（江门）展示中心","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"沈阳陆盛机械厂","y":362.52927,"x":1335.8112},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"沧州晟和五金制品有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":3.0,"userName":"河北军威机械制造有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":3.0,"userName":"河北双天机械制造有限公司","y":449.08823,"x":1155.5239},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"河北沈鑫机床销售有限公司","y":472.76294,"x":1134.1415},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"河北津泊电气阀门有限公司","y":471.7949,"x":1181.2163},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"河北省沧州市汪家铺乡个体客户","y":471.7949,"x":1181.2163},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"河北省衡水市故城内燃机配件制造厂","y":492.74716,"x":1166.57},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"河北荣昌恒达铸造有限公司","y":472.76294,"x":1134.1415},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"河北铸诚工矿机械有限公司","y":500.5842,"x":1133.5984},{"yxs":0.0,"nodeId":"2c9080844be3a021014c68e2e32b0098","zs":1.0,"userName":"河南矿山起重机有限公司","y":556.42725,"x":1138.5762},{"yxs":0.0,"nodeId":"","zs":6.0,"userName":"河南矿山集团","y":550.64984,"x":1120.6361},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"泊头市达丰机械制造有限公司","y":471.7949,"x":1181.2163},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"泓利达设备有限公司","y":757.73206,"x":1273.8724},{"yxs":1.0,"nodeId":"","zs":2.0,"userName":"泰鑫机械制造厂","y":447.95535,"x":1294.4937},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"派瑞特液压件制造有限公司","y":441.71637,"x":1193.2745},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"济宁华晨液压机械有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"浙江三凯机电有限公司","y":739.00635,"x":1290.2704},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"浙江中控流体技术有限公司","y":696.24756,"x":1262.3154},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"浙江午马减速机有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"2c9080844be3a021014c3043be920029","zs":2.0,"userName":"浙江博星工贸有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"浙江好事达阀业有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"浙江康思特动力机械有限公司","y":701.5222,"x":1270.9183},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"浙江省宁波市司海彬有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":8.0,"userName":"浙江麦得机器有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"海宁六合汽配有限公司","y":688.23956,"x":1267.842},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"海航机械厂山西分公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":4.0,"userName":"淄博颂工机械","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"深圳奔联有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"深圳富成达科技有限公司","y":910.355,"x":1120.6626},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"深圳市必拓电子有限公司","y":910.355,"x":1120.6626},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"深圳统盛有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"深圳迈瑞生物医疗电子股份有限公司","y":910.355,"x":1120.6626},{"yxs":0.0,"nodeId":"","zs":4.0,"userName":"温岭市明华齿轮有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"2c9080844be3a021014c9e1df7a500e1","zs":1.0,"userName":"温州","y":758.0072,"x":1273.5475},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"温州市华海密封件有限公司","y":757.73206,"x":1273.8724},{"yxs":0.0,"nodeId":"2c9080844be3a021014c3143a0610043","zs":1.0,"userName":"湖北三环成套贸易有限公司","y":684.3128,"x":1129.4215},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"湖州市大东吴汽车电机有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"潍坊联信增压器制造有限公司","y":489.3613,"x":1265.6257},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"烟台佰斯特汽车配件","y":489.046,"x":1290.6609},{"yxs":0.0,"nodeId":"2c9080844be3a021014c550f363e0087","zs":12.0,"userName":"烟台西蒙西实业发展有限公司","y":489.046,"x":1290.6609},{"yxs":0.0,"nodeId":"","zs":4.0,"userName":"烟台鸿雁机床设备有限公司","y":489.046,"x":1290.6609},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"珠海市业成交通轨道交通设备有限公司","y":921.8252,"x":1103.5234},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"瑞克精密机械有限公司","y":679.9873,"x":1274.976},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"瑞安市奔科模具厂","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"瑞安市科望汽摩配有限公司","y":757.73206,"x":1273.8724},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"石家庄博翔机械制造有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"石家庄根成机械有限公司","y":367.92264,"x":1328.4851},{"yxs":1.0,"nodeId":"2c9080844be3a021014c9ccaf12a00da","zs":1.0,"userName":"绍兴诸暨市嵘华机械厂","y":707.46375,"x":1265.1052},{"yxs":0.0,"nodeId":"","zs":4.0,"userName":"绵阳恒弘机械制造有限公司","y":658.22504,"x":913.58875},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"耐迪特减震器","y":757.73206,"x":1273.8724},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"耿福友","y":511.9015,"x":1189.7438},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"联洲五金制品有限公司","y":909.49927,"x":1105.3911},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"腾璇科技","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"苏州个体户","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"苏州博喜来电器有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"2c9080844be3a021014c4495f6fb0053","zs":6.0,"userName":"苏州艾特生精密机械有限公司","y":665.6524,"x":1269.0656},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"苏州迈壹有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"苏州黄埭客户","y":367.92264,"x":1328.4851},{"yxs":1.0,"nodeId":"2c9080844be3a021014c366a61c90048","zs":3.0,"userName":"荆州友力汽车零部件有限公司","y":692.41785,"x":1083.1208},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"荆州市友力机械有限公司","y":691.56195,"x":1082.1438},{"yxs":0.0,"nodeId":"","zs":7.0,"userName":"莲花山凿岩钎具有限公司","y":1552.6698,"x":-1455.6318},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"萨克赛斯机械制造有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":4.0,"userName":"藁城高精工具厂","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"融兴科技有限公司","y":656.6982,"x":1265.5275},{"yxs":0.0,"nodeId":"","zs":32.0,"userName":"襄阳汽车轴承股份有限公司","y":644.1702,"x":1079.206},{"yxs":0.0,"nodeId":"2c9080844be3a021014c6972a1a800a0","zs":2.0,"userName":"西安鼎瑞机械设备有限公司","y":580.83453,"x":1005.18536},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"西峡乐力嘉液压科技有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":10.0,"userName":"许昌远东传动轴股份有限公司","y":586.7464,"x":1118.6006},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"诚信机电设备有限公司","y":367.92264,"x":1328.4851},{"yxs":1.0,"nodeId":"2c9080844be3a021014c27be7c5b0021","zs":3.0,"userName":"辉翔汽车零部件制造有限公司","y":724.6477,"x":1121.2283},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"辽中个体","y":367.92264,"x":1328.4851},{"yxs":2.0,"nodeId":"2c9080844be3a021014c54f208680081","zs":5.0,"userName":"辽宁营口大力汽保设备科技有限公司","y":399.0182,"x":1310.7281},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"重庆万子机械制造有限公司","y":715.3499,"x":936.6817},{"yxs":0.0,"nodeId":"","zs":3.0,"userName":"重庆凯仁机械制造公司","y":709.06085,"x":955.4198},{"yxs":0.0,"nodeId":"","zs":9.0,"userName":"重庆浙西机床设备公司","y":715.18066,"x":952.7724},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"重庆渝宇机床设备公司","y":714.7097,"x":953.95526},{"yxs":0.0,"nodeId":"","zs":3.0,"userName":"重庆渝青机械配件制造有限公司","y":715.903,"x":946.5232},{"yxs":0.0,"nodeId":"","zs":6.0,"userName":"重庆渝青机械配件制造有限公司柳州分公司","y":862.13654,"x":1018.3437},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"金华市步云机电设备有限公司","y":727.03046,"x":1249.9989},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"金峰自动化仪表有限公司","y":757.73206,"x":1273.8724},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"铭正机械有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"镇海博浪金属制品厂","y":707.4222,"x":1290.0984},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"长春一汽四环运达贸易有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"长春武玉汽车配件有限公司","y":307.80273,"x":1378.5026},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"震航机械","y":550.64984,"x":1120.6361},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"霸州市天成电子有限公司","y":444.42783,"x":1178.4573},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"青岛宏晨机械公司","y":1552.6698,"x":-1455.6318},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"青岛恒泰宇工贸有限公司","y":367.92264,"x":1328.4851},{"yxs":0.0,"nodeId":"","zs":1.0,"userName":"韶关市华研机械有限公司","y":848.86194,"x":1111.7123},{"yxs":0.0,"nodeId":"","zs":2.0,"userName":"龙腾机械","y":472.76294,"x":1134.1415}],"msg":"查询成功！","success":true};
			if(data.success) {
				//清空
				$(".mapPoints").empty();
				//循环绘制
				var dataContent = data.content;
				for(var i = 0; i < dataContent.length; i++) {
					showPoint(dataContent[i].x, dataContent[i].y, dataContent[i].userName, dataContent[i].yxs, dataContent[i].zs, dataContent[i].nodeId);
				}
			}
			else {
				console.log(error + data.msg);
			}
		}
	});	
}

///*
// * 地图上显示数据点
// * _x：X坐标
// * _y: Y坐标
// * userName: 用户节点名称
// * runNum: 运行机床数
// * idleNum: 空闲机床数
// * repairNum: 维修机床数
// */
//function showData(_x, _y, userName, runNum, idleNum, repairNum) {
//	//外框
//	var dataFrame = document.createElement("div");
//	$(dataFrame).addClass("point").css({left: _x + "px", top: _y + "px"});
//	//信息框架
//	var dataInfoFrame = document.createElement("div");
//	//加载数据
//	$(dataInfoFrame).addClass("dataInfo").addClass("hidden").attr({
//		"run_num": runNum,
//		"idle_num": idleNum,
//		"repair_num": repairNum
//	});
//	$(dataFrame).append(dataInfoFrame);
//	$(".mapDatas").append(dataFrame);
//}

/*
 * 地图上显示坐标点
 * _x：X坐标
 * _y: Y坐标
 * userName: 用户节点名称
 * subDevice: 当前运行机床数
 * allDevice: 机床总数
 * nodeId: 节点ID
 */
function showPoint(_x, _y, userName, subDevice, allDevice, nodeId) {
	var username,status,tiaozhuan;
	dataTranslate("map", function(t) {
		username = t("top.username");
		status = t("top.status");
		tiaozhuan = t("top.tiaozhuan");
		
	},"../static/i18n");
	//外框
	var pointFrame = document.createElement("div");
	$(pointFrame).addClass("point").css({left: _x + "px", top: _y + "px"});
	//小图片
	if(nodeId == "") {
		$(pointFrame).addClass("small");
	}
	//坐标图片
	var pointImg = document.createElement("img");
	$(pointImg).addClass("pointImg").attr("src", "../images/map/map_point_color.png");
	//小图片
	if(nodeId == "") {
		$(pointImg).attr("src", "../images/map/map_point_ring.png");
	}
	//信息框架
	var pointInfoFrame = document.createElement("div");
	$(pointInfoFrame).addClass("pointInfo").addClass("hidden");
	//状态信息
	var infoStr = "";
	//用户名称
	if(userName.length > 8) {
		//userName = userName.substr(0, 15) + "...";
		var fontSize = 16, defaultWidth = 225;
		$(pointInfoFrame).css("width", function() {
			return (defaultWidth + ((userName.length -8) * fontSize * 1.2)) + "px";
		});
	}
	infoStr += "<span>"+username+ userName + "</span><br />";
	//运行状态
	infoStr += "<span>"+status+ allDevice + "</span><br />";
	//跳转
	infoStr += "<span class='mapHref' node_id='" + nodeId + "'>"+tiaozhuan+"</span>";
	$(pointInfoFrame).append(infoStr);
	
	$(pointFrame).append(pointImg).append(pointInfoFrame);
	
	$(".mapPoints").append(pointFrame);
}

/*
 * 开场动画
 */
function mapAnimate() {
	CURRENT_WIDTH = $(".mapDatas").width();
	CURRENT_HEIGHT = $(".mapDatas").height();
	
	/*
	 * 使用动画
	//坐标点隐藏
	$("#map_china,.mapPoints").removeClass("hidden").hide();
	//显示世界地图
	$("#map_all").removeClass("hidden").hide().fadeIn(1000);
	//创建瞄准点
	var aim = createAim();
	//瞄准点归位
	var mapDatas = $(".mapDatas>.point").each(function() {
		//瞄准点移动&点击&显示
		aimMove(aim, $(this).offset().left, $(this).offset().top, 5, dataShow);
	});
	
	//切换地图
	aimMove(aim, CURRENT_WIDTH * 1446 / 1920, CURRENT_HEIGHT * 487 / 1080, 5, changeMap);
	
	 */
	//调整地图图标LEFT位置
	$(".mapPoints>point>pointImg").css("left", function() {
		return $(this).width() - (CURRENT_WIDTH * 28 / 1920) / 2 + $(this).position().left;
	});
	$("#map_china,.mapPoints").removeClass("hidden").hide();
	changeMap();
}

///*
// * 创建瞄准点
// */
//function createAim() {
//	var aim = document.createElement("div");
//	$(aim).attr("id", "aim").appendTo("body");
//	var aimCenter = document.createElement("div");
//	$(aimCenter).css({
//		position: "absolute",
//		width: "2%",
//		height: "2%",
//		left: "1%",
//		top: "1%",
//		border: "solid 2px #000"
//	}).appendTo(aim);
//	var aimLineTop = document.createElement("div");
//	$(aimLineTop).css({
//		position: "absolute",
//		width: "0",
//		height: "0",
//		left: "2%",
//		top: "0",
//		border: "solid 1px #000"
//	}).appendTo(aim);
//	var aimLineRight = document.createElement("div");
//	$(aimLineRight).css({
//		position: "absolute",
//		width: "96%",
//		height: "0",
//		left: "4%",
//		top: "2%",
//		border: "solid 1px #000"
//	}).appendTo(aim);
//	var aimLineBottom = document.createElement("div");
//	$(aimLineBottom).css({
//		position: "absolute",
//		width: "0",
//		height: "96%",
//		left: "2%",
//		top: "4%",
//		border: "solid 1px #000"
//	}).appendTo(aim);
//	var aimLineLeft = document.createElement("div");
//	$(aimLineLeft).css({
//		position: "absolute",
//		width: "0",
//		height: "0",
//		left: "0",
//		top: "2%",
//		border: "solid 1px #000"
//	}).appendTo(aim);
//	return aim;
//}
//
///*
// * 瞄准点移动
// */
//function aimMove(_aim, _x, _y, _speed, _func) {
//	//移动计算
//	var moveX = _x / CURRENT_WIDTH * 100;
//	var moveY = _y / CURRENT_HEIGHT * 100;
//	var moveSpeed = 100.0 / _speed;
//	var moveRange = Math.sqrt(((moveX - 50) * (moveX - 50)) + ((moveY - 50) * (moveY - 50)));
//	//瞄准元素寻找
//	var aimCenter = $(_aim).find("div:eq(0)");
//	var aimLineTop = $(_aim).find("div:eq(1)");
//	var aimLineRight = $(_aim).find("div:eq(2)");
//	var aimLineBottom = $(_aim).find("div:eq(3)");
//	var aimLineLeft = $(_aim).find("div:eq(4)");
//	$(aimCenter).animate({
//		left: moveX - 1 + "%",
//		top: moveY - 1 + "%"
//	}, moveSpeed * moveRange);
//	$(aimLineTop).animate({
//		left: moveX + "%",
//		height: moveY - 2 + "%"
//	}, moveSpeed * moveRange);
//	$(aimLineRight).animate({
//		left: moveX + 2 + "%",
//		top: moveY + "%",
//		width: 98 - moveX + "%"
//	}, moveSpeed * moveRange);
//	$(aimLineBottom).animate({
//		left: moveX + "%",
//		top: moveY + 2 + "%",
//		height: 98 - moveY + "%"
//	}, moveSpeed * moveRange);
//	$(aimLineLeft).animate({
//		top: moveY + "%",
//		width: moveX - 2 + "%"
//	}, moveSpeed * moveRange);
//	//点击
//	aimClick(aimCenter, _func);
//}
//
///*
// * 瞄准点点击动作
// */
//function aimClick(_aim, _func) {
//	var w = $(_aim).width() * .3;
//	var h = $(_aim).height() * .3;
//	$(_aim).animate({
//		width: "-=" + w + "px",
//		height: "-=" + h + "px",
//		left: "+=" + w / 2.0 + "px",
//		top: "+=" + h / 2.0 + "px"
//	}, 300);
//	$(_aim).animate({
//		width: "+=" + w + "px",
//		height: "+=" + h + "px",
//		left: "-=" + w / 2.0 + "px",
//		top: "-=" + h / 2.0 + "px"
//	}, 200, "linear",_func);
//	$(_aim).nextAll().animate({textIndent: "10px"}, 505);
//}
//
///*
// * 按序号显示节点内容
// */
//var dataIndex = 0;
//function dataShow() {
//	var currentElement = $(".mapDatas>.point:eq(" + dataIndex + ")>.dataInfo");
//	//显示
//	currentElement.removeClass("hidden");
//	//绘图
//	addPie(currentElement, currentElement.attr("run_num"), currentElement.attr("idle_num"), currentElement.attr("repair_num"))
//	dataIndex++;
//}
//
///*
// * 添加饼图
// */
//function addPie(_obj, runNum, idleNum, repairNum) {
//	$(_obj).highcharts({
//	    chart: {
//		    backgroundColor: 'rgba(255, 255, 255, 0)',
//            plotBorderColor : null,
//            plotBackgroundColor: null,
//            plotBackgroundImage: null,
//            plotBorderWidth: null,
//            plotShadow: false
//        },
//        title: {
//            text: ''
//	    },
//	    tooltip: {
//	    	enabled: false
//	    },
//	    plotOptions: {
//	        pie: {
//	            allowPointSelect: false,
//	            cursor: 'pointer',
//	            showInLegend: false,
//                dataLabels: {
//                    enabled: false
//                },
//	    	    innerSize: '60%'
//	        }
//	    },
//		colors:[                   
//			'rgba(142,186,132,.75)',   				   
//			'rgba(244,221,12,.75)',			   
//			'rgba(232,125,77,.75)'
//		],
//		credits: {
//				 text: '',
//				 href: ''
//		},
//		exporting:{ 
//			enabled:false //用来设置是否显示‘打印?'导出'等功能按钮，不设置时默认为显礿
//		}, 
//				
//	    series: [{
//	        type: 'pie',
//	        data: [
//               ['run', parseFloat(runNum)],
//               ['idle', parseFloat(idleNum)],
//               ['repair', parseFloat(repairNum)]         
//           ]
//	    }]
//	});
//}

/*
 * 切换地图至中国区域
 */
function changeMap() {
	/*
	 * 使用动画
	//隐藏全球数据
	$(".mapDatas,#aim").fadeOut();
	//扩张&位移地图 446 250
	var moveLift = CURRENT_WIDTH * -1240 * 4.3 / 1920;
	var moveTop = CURRENT_HEIGHT * -338 * 4.3 / 1080;
	var moveWidth = CURRENT_WIDTH * 4.3;
	var moveHeight = CURRENT_HEIGHT * 4.3;
	$("#map_all").css({
		"transition": "2.5s"
	});
	$("#map_all").css({
		"left": moveLift + "px",
		"top": moveTop + "px",
		"width": moveWidth + "px",
		"height": moveHeight + "px"
	}).fadeOut(500, function() {
		$("#map_china,.mapPoints").fadeIn(500);
	});
	 */
	setTimeout(function() {
		$("#mapAni").fadeOut(500, function() {
			$("#map_china,.mapPoints").fadeIn(500);
		});
	}, 5000);
}