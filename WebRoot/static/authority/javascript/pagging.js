/**
 * 翻页类;
 * @returns
 */

if( window._Pag == undefined )
	window._Pag = {};
var _index = 1;

var PaggingInfo = function(label,pageNo){
	this.label = label;
	this.pageNo = pageNo;
}

var Pagging = function(id,page,pageSize,max,fn){
	
	var id = id || _index++;
	_Pag[id] = this;
	var self = this;
	var total = 0;
	var pageNo = page;
	var pageSize = pageSize;
	var max = max;
	var callback = fn;
	var totalPages = 1;
	
	this.setTotal=function(t){
		t = (t<=0)?0:t;
		total = t;
		totalPages = parseInt(total/pageSize);
		
		if( total%pageSize != 0 ) totalPages++;
		
		if (totalPages == 0) totalPages++;
	}
	
	//跳转;
	this.go = function( page ){
		pageNo = page;
		this.pagging();
	}
	
	//向前;
	this.pre = function(){
		pageNo  = ((pageNo -1) <= 0) ? 1:(pageNo -1); 
		this.go (pageNo);
	}
	
	//向后;
	this.back = function ( pageNo ){
		pageNo  = ((pageNo +1) >= totalPages ) ? totalPages:(pageNo +1); 
		this.go (pageNo);
	}
	
	//首页;
	this.start = function (){
		pageNo = 1;
		this.go (pageNo);
	}
	
	//末页;
	this.last = function( pageNo ){
		pageNo = total;
		this.go (pageNo);
	}
	
	this.pagging = function (){
		callback( pageNo, pageSize,self );
	}
	
	this.show = function ( ){
		var list = getLabels();
		var s = '<div ><div class="default-pagging-left">共'+total+'行 '+pageSize+'行/页</div>'
				+'<div class="default-pagging-right">'
				+'<ul class="default-pagging-class">';
		for( var i =0;i<list.length;i++){
			var tmp = '<li><a href="javascript:';
			var info = list[i];
			if( info.pageNo == pageNo ){
				tmp +='void(null);" class="pagging-current"';
			}else if(info.pageNo == -1 ){
				tmp +='void(null);" class="pagging-dot"';
			}else{
				tmp +='_Pag[\''+id+'\'].go('+info.pageNo+')"';
			}
			tmp+='>'+info.label+'</a></li>';
			s+=tmp;
		}
		s+='</ul></div><div class="default-padding-clear"></div></div>';
		return s;
	}
	
	/**
	 * 返回列表;
	 */
	function getLabels (){
		var labels = [];
		if (totalPages <= max ) for (var i = 1; i <= totalPages; i ++) {
            labels.push (new PaggingInfo (i, i));
        } else {
            labels.push (new PaggingInfo ("1", 1));

            if (pageNo <= parseInt(max /2)) {
                var i;
                for (i = 2; i <= max - 2; i ++)
                    labels.push (new PaggingInfo (i, i));
                if (i < totalPages - 1)
                    labels.push (new PaggingInfo ("...", -1));
            } else if ((totalPages - pageNo) <= parseInt(max / 2)) {
                var start = totalPages - max + 3;
                if (start > 2)
                    labels.push (new PaggingInfo ("...", -1));
                if (start <= 2) start = 2;
                for (var i = start; i < totalPages; i ++) {
                    labels.push (new PaggingInfo (i, i));
                }
            } else {
                labels.push (new PaggingInfo ("...", -1));
                var start = pageNo - parseInt((max - 4) / 2);
                var end = pageNo + parseInt((max - 4) / 2);
                for (var i = start; i < end; i ++)
                    labels.push (new PaggingInfo (i, i));
                labels.push (new PaggingInfo ("...", -1));
            }
            labels.push (new PaggingInfo (totalPages, totalPages));
        }
		return labels;
	}
	
	this.pagging();
}
