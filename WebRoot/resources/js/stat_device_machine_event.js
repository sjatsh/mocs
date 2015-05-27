var totalwidth = 0;
var dates = new Array();
var currentdate = 0; //日期序列索引
var movewidth = 0; //每次移动的距离
var totalcolors = new Array("FFFFFF", "8A2BE2", "66CD00", "FFD700", "C1C1C1", "4876FF", "CD5555", "40E0D0", "ADFF2F", "BF3EFF", "8470FF", "8DEEEE"); //所有颜色的集合

/**
 * 附加图
 * @param type
 * @param height
 * @param width
 * @param data
 * @param div
 */
function AppendChart(type, height, width, data, div) {
    movewidth = width;
    var d = data.split('+');
    totalwidth = width * d.length;              
    //                    var html = '<table id="tb" cellspadding="0" cellspacing="0" style="position:absolute; left:-' + (width + 3) + 'px; top:10px;"><tr>';
    var html = '<table id="tb" cellspadding="0" cellspacing="0" style="position:absolute; left:-1px; top:18px;"><tr>';
    //                    <td id="td1">' + Chart(height, width, d[0]) + '</td><td id="td2">' + Chart(height, width, d[1]) + '</td><td id="td3">' + Chart(height, width, d[2]) + '</td>
    for (var i = 0; i < d.length; i++) {
        dates[i] = d[i].split('|')[0];
        html += '<td>' + Chart(height, width, d[i]) + '</td>';
    }
    $('#divdate').html(dates[currentdate]);
    $('#divnext').html(dates[currentdate + 1]);

    html += '</tr></table>';
    html += '<table cellspacing="0" style="width:' + width + 'px;height:' + height + 'px;position:absolute;top:50px;"><tr>';
//                    html += '<td align="center"><input id="left" disabled="disabled" type="button" value="向左" onclick="MoveLeft(' + (width + 2) + ',\'' + divdate + '\');"/></td>';
//                    html += '<td align="center"><input id="right" type="button" value="向右" onclick="MoveRight(' + (width + 2) + ',\'' + divdate + '\');" /></td>';
    html += '</tr></table>';
    $('#' + div).html(html);
}

/**
 * 设置图的高度、宽度
 */
function Chart(height, width, data) {
    var d = data.split('|');
    var str = '<table datevalue=' + d[0] + ' cellspacing="0" style="width:' + width + 'px;height:' + height + 'px">';
    str += '<tr>';
    var dataSize = 0; //数据总长度,length为图形长度
    var color = new Array();
    var size = new Array();

    for (var i = 1; i < d.length; i++) {
        color[i] = ColorConvert(d[i].split(',')[0]);
        size[i] = parseInt(d[i].split(',')[1]);
        dataSize += size[i];
    }
    var sizeRate = width / dataSize; //长度系数，某段数据长度为数据长度*长度系数

    //绘图
    for (var i = 1; i < size.length; i++) {
        str += "<td class='" + color[i] + "' width='" + (size[i] * sizeRate) + "px' style='background-color:#" + color[i] + "'></td>";
    }
    str += '</tr>';
    str += '</table>';

    return str;
}

/**
 * 图的颜色控制
 * @param status
 * @returns {String}
 */
function ColorConvert(status) {
    switch (status) {
        case "准备":
            return "8A2BE2";
        case "加工":
            return "66CD00";
        case "空闲":
            return "FFD700";
        case "关机":
            return "C1C1C1";
        case "开机":
            return "4876FF";
        case "故障":
            return "CD5555";
        case "空运行":
            return "40E0D0";
        case "切削":
            return "ADFF2F";
        case "手动运行":
            return "BF3EFF";
        case "上下料":
            return "8470FF";
        case "换刀":
            return "8DEEEE";
    }

}
