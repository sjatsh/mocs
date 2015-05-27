//first level
var itemwidth = wjb51 * 384 / 1920;
var itemheight = hjb51 * 90 / 1080;
var itemfontsize = hjb51 * 40 / 1080;
var ff = "微软雅黑";
// your div id
var ydiv = "zeno";
// container width
var mcerwidth = "100%";
// container width
var mcerheight = hjb51 * 90 / 1080 + "px";
// moving div height
var my2height = hjb51 * 90 / 1080 + "px";
// item panel id
var ipi = "movediv3";
// data
var sdata = eval(document.getElementById("menuform:menuJsonStr").value);
// alert(document.getElementById("menuform:menuJsonStr").value);
// var sdata=demodata;
// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// current move div left
var xx = 0;
// container id
var mcer = "mycontainer";
// moving div id
var my2 = "movediv2";

document.getElementById(ydiv).innerHTML = '<div onselectstart="return false" id="'
		+ mcer
		+ '" style="cursor: pointer; width: '
		+ mcerwidth
		+ '; height: '
		+ mcerheight
		+ '; position: relative; color: #5a5a5a; background-color:white; box-shadow: 0px 0px 10px 0px rgba(100,100,100,0.7); overflow: hidden; font-weight: bolder;"><div id="'
		+ my2
		+ '" style="height: '
		+ my2height
		+ '; position: absolute; top: 0px; left: 0px;" onmousedown="drag(event,this)"></div></div>';

onclick = function(a) {
	if (!a)
		a = window.event;
	if (!a.pageX)
		a.pageX = a.clientX;
	if (!a.pageY)
		a.pageY = a.clientY;
	xx = parseInt(document.getElementById(my2).style.left);
}
document.onmousedown = function(a) {
	xx = parseInt(document.getElementById(my2).style.left);
}
document.onmouseup = function(a) {
	if (!a)
		a = window.event;
	if (!a.pageX)
		a.pageX = a.clientX;
	if (!a.pageY)
		a.pageY = a.clientY;

	fixposition();
	// xx = parseInt(document.getElementById("movediv2").style.left) - xx;
	// xx = parseInt(document.getElementById(my2).style.left);
}
drag = function(a, o) {
	var d = document;
	if (!a)
		a = window.event;
	if (!a.pageX)
		a.pageX = a.clientX;
	if (!a.pageY)
		a.pageY = a.clientY;
	var x = a.pageX, y = a.pageY;
	if (o.setCapture)
		o.setCapture();
	else if (window.captureEvents)
		window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP);
	var backData = {
		x : o.style.top,
		y : o.style.left
	};
	d.onmousemove = function(a) {
		if (!a)
			a = window.event;
		if (!a.pageX)
			a.pageX = a.clientX;
		if (!a.pageY)
			a.pageY = a.clientY;
		var tx = a.pageX - x + parseInt(o.style.left), ty = a.pageY - y
				+ parseInt(o.style.top);
		// if (tx < 0 && tx > -1 * (parseInt($("#" + ipi).width()) -
		// parseInt($("#" + mcer).width()))) {
		o.style.left = tx + "px";
		// document.getElementById(o.id).style.left = tx + "px"; //added
		x = a.pageX; //
		// xx = parseInt(document.getElementById(my2).style.left);
		// y = a.pageY;
		// }
	};
	d.onmouseup = function(a) {
		if (!a)
			a = window.event;
		if (o.releaseCapture)
			o.releaseCapture();
		else if (window.captureEvents)
			window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP);
		d.onmousemove = null;
		d.onmouseup = null;
		if (!a.pageX)
			a.pageX = a.clientX;
		if (!a.pageY)
			a.pageY = a.clientY;
		if (!document.body.pageWidth)
			document.body.pageWidth = document.body.clientWidth;
		if (!document.body.pageHeight)
			document.body.pageHeight = document.body.clientHeight;
		// xx = parseInt(document.getElementById(my2).style.left);
		// if (a.pageX < 1 || a.pageY < 1 || a.pageX > document.body.pageWidth
		// || a.pageY > document.body.pageHeight) {}
		// var xxx = o.style.left.substr(0, o.style.left.length - 2);
		// if (parseInt(xxx) > -190) { mleft(10); }
		// else if (parseInt(xxx) < -190 && parseInt(xxx) > -590) { mleft(-390);
		// }
		// else if (parseInt(xxx) < -590 && parseInt(xxx) > -790) { mleft(-790);
		// }
		// else if (parseInt(xxx) < -790) { mleft(-790); }
		fixposition();
	};
}
// moving panel
var hc = '<table cellpadding=0 cellspacing=0 width="' + itemwidth
		* sdata.content.length + 'px" border="0px" id="' + ipi + '" ><tr>';
// first level
// if(!getCookie("menufirst"))
// SetCookie("menufirst","item0");
// else if(getCookie("menufirst")=="")
// SetCookie("menufirst","item0");
SetCookie("pathitem", "");
for ( var i = 0; i < sdata.content.length; i++) {

	SetCookie("pathitem", getCookie("pathitem") + "|item" + i + "*"
			+ sdata.content[i].url);
	// alert(getCookie("pathitem"));

	var nowcolor = "ffffff";
	// if (i > 0)
	// nowcolor = "00a040";
	var nowfontcolor = "5a5a5a";
	// if (i > 0)
	// nowfontcolor = "ffffff";
	hc += '<td id="item'
			+ i
			+ '" style=" text-align:center; vertical-align:middle; font-family: '
			+ ff
			+ '; font-size:'
			+ itemfontsize
			+ 'px; width:'
			+ itemwidth
			+ 'px; height:'
			+ itemheight
			+ 'px; background: #'
			+ nowcolor
			+ '; color:#'
			+ nowfontcolor
			+ '; text-shadow: 3px 3px 10px rgba(0,0,0,0.5);" onclick="openclose(this);jumpto(\''
			+ sdata.content[i].url + '\');">' + sdata.content[i].label;
	hc += '</td>'; //
	try {
		if (typeof (sdata.content[i].pages) != "undefined")
			if (sdata.content[i].pages.length > 0) {// ' +
				// sdata.content[i].pages.length
				// + '
				hc += '<td  width="0px" id="item'
						+ i
						+ '_1" style="position:relative; background: #00a040;  font-size:'
						+ itemfontsize + 'px; height:' + itemheight
						+ 'px; overflow:hidden; vertical-align:top;" name="'
						+ sdata.content[i].pages.length + '">';
				// hc += '<div class="triangle-left"></div>';
				hc += "<table  height='"
						+ (itemheight)
						+ "px' id='item"
						+ i
						+ "_1_1' cellpadding=0 cellspacing=0 border='0px'  style='margin:1px; margin-left:10px; display:none; background-color:white; text-align:center; vertical-align:middle; font-family: "
						+ ff + "; '><tr>";
				var susub = "";

				for ( var ii = 0; ii < sdata.content[i].pages.length; ii++) {

					hc += "<td  id='item"
							+ i
							+ "_1_1_"
							+ ii
							+ "'  style=' vertical-align:top; font-size:";
					var myfontsa=(itemfontsize - 8)+"";
					
					var myfontsb="px; width:"
							+ itemwidth
							+ "px; ' "
							+'onclick="subopenclose(this);jumpto(\''+sdata.content[i].pages[ii].url+'\')">'
							+"<div class='triangle-left'></div><table id='item"
							+ i
							+ "_1_1_"
							+ ii
							+ "*t'  style=' margin-left:100px; margin-top:";
					var mymargina="1";
					var mymarginb="px; margin-bottom:1px; position:relative; top:0px; height:10px;'><tr><td style=' vertical-align:top; padding:0px;' ><span id='item"
							+ i
							+ "_1_1_"
							+ ii
							+ "*sp' style=' position:relative; top:-4px; '>"
							+ sdata.content[i].pages[ii].label
							+ "</span></td><td><span style='display:none; position:relative; top:-4px;' id='item"
							+ i
							+ "_1_1_"
							+ ii
							+ "*u' >";
					var myastring="▲";
					var mybstring="</span></td><td ><span style=' position:relative; top:-4px;' id='item"
							+ i + "_1_1_" + ii
							+ "*d' >";
					var mycstring="▼";
					var mydstring="</span></td></tr></table></td>";

					susub += '<tr  id="item'
							+ i
							+ '_1_1_'
							+ ii
							+ '_1"  style="height:35px; display:none; overflow:hidden;">';
					susub += '<td colspan="' + sdata.content[i].pages.length
							+ '">';

					if (typeof (sdata.content[i].pages[ii].pages) != "undefined") {

						susub += '<div style=" margin-left:3px; margin-top:-7px; margin-right:3px; border:solid 1px silver; height:25px; width:'
								+ sdata.content[i].pages.length
								* itemwidth
								+ 'px; overflow:hidden; ">';

						susub += '<table  id="item'
								+ i
								+ '_1_1_'
								+ ii
								+ '_8"  style=" position:relative; left:0px; top:0px; width:'
								+ sdata.content[i].pages[ii].pages.length * 110
								+ 'px; font-size:12px;"><tr><td>';
						// susub += "item" + i + "_1_1_" + ii + "_1";
						// if(sdata.content[i].pages[ii].label!="工位管理")

						if (sdata.content[i].pages[ii].pages) {

							for ( var iii = 0; iii < sdata.content[i].pages[ii].pages.length; iii++) {
								if (sdata.content[i].pages[ii].pages[iii].label != "") {
									susub += '<span id="'
											+ sdata.content[i].pages[ii].pages[iii].url
											+ '" style="font-size:13px; margin-left:10px; margin-right:10px;" onclick="jumpto(\''
											+ sdata.content[i].pages[ii].pages[iii].url
											+ '\');">'
											+ sdata.content[i].pages[ii].pages[iii].label
											+ '</span>';
									// 'o' + i + '_1_1_' + ii + '_1'
								}
							}
						}
						
						
						susub += '</td></tr></table>';
						susub += '<div onclick="goleft(this)" id="item'
								+ i
								+ '_1_1_'
								+ ii
								+ '_7"  style="display:none; position: absolute; top: 55px; left: 20px; width: 0; height: 0; border-top: 10px solid transparent; border-right: 20px solid black; border-bottom: 10px solid transparent;"></div>'
						susub += '<div onclick="goright(this,'
								+ sdata.content[i].pages[ii].pages.length
								* 300
								+ ','
								+ sdata.content[i].pages.length
								* itemwidth
								+ ')" id="item'
								+ i
								+ '_1_1_'
								+ ii
								+ '_9"  style="display:none; position: absolute; top: 55px; right: 15px; width: 0; height: 0; border-top: 10px solid transparent; border-left: 20px solid black; border-bottom: 10px solid transparent;"></div>'
						susub += '</div>';
					}
					else{
						myastring="";
						mycstring="";
						myfontsa="18";
						mymargina="10";
						//document.getElementById("item"+ i+ "_1_1_"+ ii	+ "*u").style.display="none";
						//alert(document.getElementById("item"+ i+ "_1_1_"+ ii+ "*u"));
						
					}

					susub += '</td></tr>';
					
					hc+=myfontsa+myfontsb+mymargina+mymarginb+myastring+mybstring+mycstring+mydstring;
				}
				hc += "</tr>";
				hc += susub;
				hc += "</table>";
				hc += "</td>";
			}
	} catch (e) {
	}
}
hc += "</tr></table>";

function goleft(dd) {
	var mm = dd.id.substring(0, dd.id.length - 2)
	$("#" + mm + "_8").animate({
		left : ($("#" + mm + "_8").position().left + itemwidth) + "px"
	}, 300, function() {
		if ($("#" + mm + "_8").position().left > 0)
			$("#" + mm + "_8").animate({
				left : "-1px"
			}, 300, function() {
			});
	});
}

function goright(dd, ddd, dddd) {
	var mm = dd.id.substring(0, dd.id.length - 2)
	$("#" + mm + "_8").animate({
		left : ($("#" + mm + "_8").position().left - itemwidth) + "px"
	}, 300, function() {
		if ($("#" + mm + "_8").position().left < -(ddd - dddd))
			$("#" + mm + "_8").animate({
				left : "-" + (ddd - dddd) + "px"
			}, 300, function() {
			});
	});
}

// var hc = "<table
// style='font-size:12px;'><tr><td>safjdksla;f</td><td>safjdksla;f</td></tr></table>";

function jumpto(dd) {
	if (dd == "/" || dd == "") {
	} else if (Math.abs(parseInt(document.getElementById(my2).style.left) - xx) < 10
			&& dd != "") {
		SetCookie("menuthirdURL", dd);
		window.location = 'index.faces?page=' + dd;
	}
}

document.getElementById(my2).innerHTML = hc;
var d = document, g = 'getElementById', moveEle = document.getElementById(my2);

var stx = sty = etx = ety = curX = curY = 0;
function ts(event) {
	stx = event.touches[0].pageX;
	sty = event.touches[0].pageY;
	event.targetTouches[0].target.style.webkitTransform = 'translate(' + (etx)
			+ 'px, ' + (ety) + 'px,0px)';
	xx = stx;
}
var l1 = moveEle.addEventListener("touchstart", ts, true);
function tm(event) {
	event.preventDefault();
	curX = event.targetTouches[0].pageX - stx + etx;
	// curY = event.targetTouches[0].pageY - sty + ety;
	// event.targetTouches[0].target.style.webkitTransform = 'translate3d(' +
	// (curX) + 'px, ' + (curY) + 'px,0px)';
	// if (curX < 0 && curX > -1 * (parseInt($("#" + ipi).width()) -
	// parseInt($("#" + mcer).width()))) {
	moveEle.style.left = curX + "px";
	// }
}
var l2 = moveEle.addEventListener("touchmove", tm, true);
function te(event) {
	etx = curX;
	ety = curY;
	// xx = curX;
}
var l3 = moveEle.addEventListener("touchend", te, true);
var nowsub = "";
function openclose(who) {
	// alert(who.id);
	if (who.id == "undefined") {
		// alert(2);
		who.id = "item0";
	}
	if (!who.id && typeof (who.id) != "undefined" && who.id != 0) {
		// alert(1);
		who.id = "item0";
	}
	// alert(who.id);
	SetCookie("menufirst", who.id);
	delCookie("menusecond");
	if (Math.abs(parseInt(document.getElementById(my2).style.left) - xx) < 10) {
		for ( var i = 0; i < sdata.content.length; i++) {
			document.getElementById("item" + i).style.backgroundColor = "#ffffff";
			document.getElementById("item" + i).style.color = "#5a5a5a";
		}
		document.getElementById(who.id).style.backgroundColor = "#00a040";
		document.getElementById(who.id).style.color = "#ffffff";
		var w1 = $("#" + who.id + "_1").width();
		if (w1 > 0) {
			$("#" + who.id + "_1_1").fadeOut(
					300,
					function() {
						var w = $("#" + ipi).width();
						$("#" + who.id + "_1").animate({
							width : "0px"
						}, 300, function() {
						});
						$("#" + ipi).animate(
								{
									width : (w - itemwidth
											* parseInt($("#" + who.id + "_1")
													.attr("name")))
											+ "px"
								}, 300, function() {
								});
						nowsub = "";
					});
		} else {
			if (nowsub != "") {
				var w1 = $("#" + nowsub).width();
				$("#" + nowsub + "_1")
						.fadeOut(
								300,
								function() {
									var w2 = $("#" + ipi).width();
									$("#" + nowsub)
											.animate(
													{
														width : "0px"
													},
													300,
													function() {
														if (typeof ($("#"
																+ who.id + "_1")
																.attr("name")) != "undefined") {
															$("#" + ipi)
																	.animate(
																			{
																				width : (w2 - itemwidth
																						* parseInt($(
																								"#"
																										+ who.id
																										+ "_1")
																								.attr(
																										"name")))
																						+ "px"
																			},
																			300,
																			function() {
																				// alert(1)
																				// ///////////////////////////////////////////////////
																				var w = $(
																						"#"
																								+ ipi)
																						.width();

																				$(
																						"#"
																								+ ipi)
																						.animate(
																								{
																									width : (w + itemwidth
																											* parseInt($(
																													"#"
																															+ who.id
																															+ "_1")
																													.attr(
																															"name")))
																											+ "px"
																								},
																								300,
																								function() {
																									// alert(2)
																								});

																				$(
																						"#"
																								+ who.id
																								+ "_1")
																						.animate(
																								{
																									width : itemwidth
																											* parseInt($(
																													"#"
																															+ who.id
																															+ "_1")
																													.attr(
																															"name"))
																											+ "px"
																								},
																								300,
																								function() {
																									// alert(3)
																									$(
																											"#"
																													+ who.id
																													+ "_1_1")
																											.fadeIn(
																													300,
																													function() {
																														// alert(4)
																														nowsub = who.id
																																+ "_1";
																													});
																								});

																				// ////////////////////////////////////////////
																			});
														} else {
															$("#" + ipi)
																	.animate(
																			{
																				width : itemwidth
																						* sdata.content.length
																						+ "px"
																			},
																			300,
																			function() {
																			});
														}
													});
									nowsub = "";
								});
			} else {
				// alert(888)
				// ///////////////////////////////////////////////////
				var w = $("#" + ipi).width();
				$("#" + ipi).animate(
						{
							width : (w + itemwidth
									* parseInt($("#" + who.id + "_1").attr(
											"name")))
									+ "px"
						}, 300, function() {
						});
				$("#" + who.id + "_1").animate(
						{
							width : itemwidth
									* parseInt($("#" + who.id + "_1").attr(
											"name")) + "px"
						}, 300, function() {
							$("#" + who.id + "_1_1").fadeIn(300, function() {
								nowsub = who.id + "_1";
							});
						});
				// ////////////////////////////////////////////
			}
		}
	}
}

var nowsusub = "";
function subopenclose(who) {
	SetCookie("menusecond", who.id);
	if (Math.abs(parseInt(document.getElementById(my2).style.left) - xx) < 10
			|| nowclicksecond != "") {
		if (nowsusub != "") {
			// alert("nosub:" + nowsusub + "==whoid:" + who.id)
			// if (nowsusub.substring(0, nowsusub.length - 4) !=
			// who.id.substring(0, who.id.length - 2))
			// $("#" + nowsusub.substring(0, nowsusub.length - 2)).animate({
			// height: "40px" }, 100, function () {
			$("#" + nowsusub).fadeOut(100, function() {
				// $("#" + nowsusub.substring(0, nowsusub.length - 2)).animate({
				// height: "0px" }, 100, function () {
				// //$("#" + nowsusub.substring(0, nowsusub.length -
				// 2)).fadeOut(100, function () { });
				// });
			});
			// });
		}

		$("#" + who.id.substring(0, who.id.length - 2)).animate({
			height : "40px"
		}, 300, function() {
			$("#" + who.id.substring(0, who.id.length - 2)).animate({
				top : "0px"
			}, 300, function() {
				$("#" + who.id + "_1").fadeIn(1,function(){
					
					$("#" + who.id + "_1").animate({
						height : "10px"
					},1);
				});
				nowsusub = who.id + "_1";
			});
		});
		$("[id$=u]").css('display', 'none');
		$("[id$=d]").css('display', 'block');
		document.getElementById(who.id + "*u").style.display = "block";
		document.getElementById(who.id + "*d").style.display = "none";

		//$("[id$=t]").css('border-top', '1px solid white');
		//$("[id$=t]").css('border-left', '1px solid white');
		//$("[id$=t]").css('border-right', '1px solid white');
		//$("[id$=t]").css('border-bottom', '1px solid transparent');
		
		//$("[id$=t]").css('background-color', 'white');
		$("[id$=sp]").css('color', 'gray');
       //alert(document.getElementById(who.id + "*t").innerHTML)
		//document.getElementById(who.id + "*t").style.backgroundColor="#00a040";
		document.getElementById(who.id + "*sp").style.color="#00a040";
		/*$("#" + who.id + "*t").css('border-top', '1px solid silver');
		$("#" + who.id + "*t").css('border-left', '1px solid silver');
		$("#" + who.id + "*t").css('border-right', '1px solid silver');
		$("#" + who.id + "*t").css('border-bottom', '1px solid white');
		
		*/
		$("#" + who.id + "*u").css("color","green");
	}
}

// if(getCookie("menufirst")==="null")
// SetCookie("menufirst","item0");

var nowclickfirst = getCookie("menufirst");
var nowclicksecond = getCookie("menusecond");
var nowthird = getCookie("menuthirdURL");
var nowpage = "/mocs" + document.getElementById("menuform:currentURL").value;

// alert('---1'+nowclickfirst);
// alert('---2'+nowclicksecond);
// alert('---3'+nowthird);
// alert('---4'+nowpage);

function openguard() {
	nowclickfirst = getCookie("menufirst");

	nowclicksecond = getCookie("menusecond");
	nowthird = getCookie("menuthirdURL");
	nowpage = "/mocs" + document.getElementById("menuform:currentURL").value;
	// alert(getCookie("menuposition"));
	$("#" + my2).animate({
		left : getCookie("menuposition") + "px"
	}, 100, function() {

	});
	if (nowclickfirst != "") {
		var tmpfirst = {
			id : ""
		};
		tmpfirst.id = nowclickfirst;
		openclose(tmpfirst);
	}
	setTimeout(function() {
		if (nowclicksecond != "") {
			var tmpsecond = {
				id : ""
			};
			tmpsecond.id = nowclicksecond;
			subopenclose(tmpsecond);
			// alert(1)
		}
	}, 500);
	setTimeout(
			function() {
				if (nowthird != "") {
					try {
						document.getElementById(nowthird).style.backgroundColor = "#00a040";
						document.getElementById(nowthird).style.color = "#ffffff";
						// alert(2)
					} catch (e) {
					}
				}
			}, 1000);
	setTimeout(function() {
		if (nowthird != "" && nowthird != nowpage) {
			// alert(nowthird)
			// window.location = 'index.faces?page='+nowthird;
		}
	}, 10);
}
setTimeout(openguard, 10);
function fixposition() {
	//alert($("#" + ipi).width()+"---"+$("#" + mcer).width());
if(parseInt($("#" + ipi).width()) > parseInt($("#" + mcer).width()))
	try {
		// setInterval(function(){
		var a = parseInt(document.getElementById(my2).style.left);
		// alert(a);
		// xx = parseInt(document.getElementById(my2).style.left);
		SetCookie("menuposition", a);
		// alert(-1 * (parseInt($("#" + ipi).width()) - parseInt($("#" +
		// mcer).width())))

		if (a < -35
				+ -1
				* (parseInt($("#" + ipi).width()) - parseInt($("#" + mcer)
						.width()))) {

			$("#" + my2).animate(
					{
						left : -1
								* (parseInt($("#" + ipi).width()) - parseInt($(
										"#" + mcer).width())) + "px"
					}, 100, function() {

					});
		}
		if (a >= 0) {
			$("#" + my2).animate({
				left : "0px"
			}, 100, function() {

			});
		}
	} catch (e) {
	}
	else
		$("#" + my2).animate({
			left : "0px"
		}, 100, function() {

		});
		
}
setTimeout(function() {
	fixposition();
}, 500);