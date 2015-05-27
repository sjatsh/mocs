/**
 * 点击弹出框;
 * 只针对本地调用;
 * 点击ctrl键显示全部;
 * jQuery('#google').suggest('http://www.google.cn/complete/search?hl=zh-CN&xhr=t&cp=2&q=', 'google');
 * function google_suggest_transform_data(data) { ... }
 *
 * jQuery('#local_js_func').suggest('local_data', null, 'hiddenId');
 * function local_data(str) {
 *     return [
 *         {val: 'aaa', html: 'aaa001', hidden: 1},
 *         {val: 'bbb', html: 'bbb002', hidden: 2},
 *         {val: 'ccc', html: 'ccc003', hidden: 3}
 *     ];
 * }
 *
 * #UID#_suggest_url();
 * #UID#_suggest_select_value(input, val);
 * #UID#_suggest_clear_value(input);
 * #UID#_suggest_transform_data(data);
 */
(function(jQuery) {
    
    var hiddens = new Object();
    var layers = new Object();
    var iframes = new Object();
    var tipslayers = new Object();
    var tipsiframes = new Object();
    var notipslayers = new Object();
    var notipsiframes = new Object();

    /**
     * @param url: 
     *      1. a ajax request url.
     *      2. if null, will use #UID#_suggest_url() for dynamic url.
     *      3. a local function name (no ajax).
     * @param uid:
     *      1. if null, auto create uid.
     *      2. a unique name (for invork dynamic function).
     * @param hidden:
     *      1. if null, will not use hidden input.  
     *      2. a hidden input name, if not exist, will create a hidden input.
     *      3. a hidden input object, will use exist object.
     */
    jQuery.fn.suggest = function(url, uid, hidden) {
        var uid = uid || uuid_ceate(8);      
        this.attr('uid', uid);
        this.attr('url', url);
        this.attr('autocomplete', 'off');
        
        if (this.attr('title')) {
            this.attr('tips', this.attr('title'));
            this.attr('title', '');
        }
        
        hidden_create(uid, this, hidden);
        var offset = this.offset();
        layer_create(uid, offset.left, offset.top + this[0].offsetHeight, this.attr('tips'),this.attr("notips"));

        this.focus( suggest_onfocus );
        this.blur( suggest_onblur );
        this.keydown( suggest_onkeydown );
        this.keyup( suggest_onkeyup );
    }

    function uuid_ceate(size) {
        var chars =  'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
        var result = '';
        for (var i=0; i<size; i++) {
            var n = Math.floor ( Math.random() * 52 );
            result += chars.charAt(n);
        }
        return result;
    };
    
    function hidden_create(uid, input, hidden) {
        if (hidden == null || hidden == '') return;
        if (typeof(hidden) == 'string') {
            var name = hidden;
            hidden = jQuery('input[name=' + name + ']');
            if (hidden.length == 0) {
                hidden = jQuery('<input type="hidden" />');
                hidden.attr('name', name);
                input.after(hidden);
            }
        } else {
            hidden = jQuery(hidden);
        }
        hiddens[uid] = hidden;
    }

    function layer_create(uid, left, top, tips,notips) {
        var layer = jQuery('<div class="suggest ' + uid + '"></div>');
        layer.css({'position':'absolute','z-index':'10000','display':'none'});
        layer.css({left: left, top: top});
        layer.appendTo('body');
        layers[uid] = layer;

        // if ie, put a iframe at between html element and Layer
        if (jQuery.browser.msie) {
            var iframe = jQuery('<iframe scrolling="no" frameborder="0" style="position:absolute;z-index:9999;display:none"></iframe>');
            iframe.css({left: left, top: top});
            iframe.appendTo('body');
            iframes[uid] = iframe;
        }
        
        if (tips) {
            var tipslayer = jQuery('<div class="suggest-tips"></div>');
            tipslayer.css({'position':'absolute','z-index':'10000','display':'none'});
            tipslayer.css({left: left, top: top});
            tipslayer.html(tips);
            tipslayer.appendTo('body');
            tipslayers[uid] = tipslayer;
            
            // if ie, put a iframe at between html element and Layer
            if (jQuery.browser.msie) {
                var tipsiframe = jQuery('<iframe scrolling="no" frameborder="0" style="position:absolute;z-index:9999;display:none"></iframe>');
                tipsiframe.css({left: left, top: top});
                tipsiframe.appendTo('body');
                tipsiframes[uid] = tipsiframe;
            }
        }
        
        if (notips) {
            var notipslayer = jQuery('<div class="suggest-tips"></div>');
            notipslayer.css({'position':'absolute','z-index':'10000','display':'none'});
            notipslayer.css({left: left, top: top});
            notipslayer.html(notips);
            notipslayer.appendTo('body');
            notipslayers[uid] = notipslayer;
            
            // if ie, put a iframe at between html element and Layer
            if (jQuery.browser.msie) {
                var notipsiframe = jQuery('<iframe scrolling="no" frameborder="0" style="position:absolute;z-index:9999;display:none"></iframe>');
                notipsiframe.css({left: left, top: top});
                notipsiframe.appendTo('body');
                notipsiframes[uid] = notipsiframe;
            }
        }
    }

    function layer_show(uid, input) {
        var offset = jQuery(input).offset();
        offset.top = offset.top + input.offsetHeight;
        
        layers[uid].css({left: offset.left, top: offset.top}).show();

        if (jQuery.browser.msie) {
            iframes[uid].css({
                left: offset.left, 
                top: offset.top, 
                width: layers[uid][0].offsetWidth, 
                height: layers[uid][0].offsetHeight
            }).show();
        }
    }

    function layer_hide(uid) {
        layers[uid].hide();
        if (jQuery.browser.msie) {
            iframes[uid].hide();
        }
    }

    function tipslayer_show(uid, input) {
        if (tipslayers[uid]) {
            var offset = jQuery(input).offset();
            offset.top = offset.top + input.offsetHeight;
            
            tipslayers[uid].css({left: offset.left, top: offset.top}).show();
    
            if (jQuery.browser.msie) {
                tipsiframes[uid].css({
                    left: offset.left, 
                    top: offset.top, 
                    width: tipslayers[uid][0].offsetWidth,
                    height: tipslayers[uid][0].offsetHeight
                }).show();
            }
        }
    }
    
    function tipslayer_hide(uid) {
        if (tipslayers[uid]) {
            tipslayers[uid].hide();
            if (jQuery.browser.msie) {
                tipsiframes[uid].hide();
            }
        }
    }
    
    function notipslayer_show(uid, input) {
        if (notipslayers[uid]) {
            var offset = jQuery(input).offset();
            offset.top = offset.top + input.offsetHeight;
            
            notipslayers[uid].css({left: offset.left, top: offset.top}).show();
    
            if (jQuery.browser.msie) {
                notipsiframes[uid].css({
                    left: offset.left, 
                    top: offset.top, 
                    width: notipslayers[uid][0].offsetWidth,
                    height: notipslayers[uid][0].offsetHeight
                }).show();
            }
        }
    }
    
    function notipslayer_hide(uid) {
        if (notipslayers[uid]) {
            notipslayers[uid].hide();
            if (jQuery.browser.msie) {
                notipsiframes[uid].hide();
            }
        }
    }
    
    function suggest_onfocus(e) {
        if (this.value != '') return;

        var tips = jQuery(this).attr('tips');
        if (tips && tips != '') {
            var uid = jQuery(this).attr('uid');
            tipslayer_show(uid, this);
        }
    }

    function suggest_onblur(e) {
        var uid = jQuery(this).attr('uid');
        layer_hide(uid);
        tipslayer_hide(uid);
        notipslayer_hide(uid);
    }

    function suggest_onkeydown(e) {
        var uid = jQuery(this).attr('uid');
        var key = e ? e.which : event.keyCode;
        if (key == 38) { // UP
            var prev = jQuery('>div.sel', layers[uid]).prev();
            if (prev.length == 0) {
                prev = jQuery('>div:last', layers[uid]);
            }
            select_div_item( prev );
        } else if (key == 40) { // DOWN
            var next = jQuery('>div.sel', layers[uid]).next();
            if (next.length == 0) {
                next = jQuery('>div:first', layers[uid]);
            }
            select_div_item( next );
        } else if (key == 13) { // ENTER
            // prevent default submit button click
            e.preventDefault();
            e.stopPropagation();
        }
    }

    function suggest_onkeyup(e) {
        var uid = jQuery(this).attr('uid');
        var key = e.keyCode;
        if (key == 13) { // ENTER
            if (layers[uid].is(':visible')) {
                layer_hide(uid);
                var sel = jQuery('>div.sel', layers[uid]);
                suggest_select_value( uid, this, sel.attr('val'), sel.attr('hidden') );
            }
        } else if (key >= 37 && key <= 40) { // UP ,DOWN, LEFT, RIGHT
            return false;
        } else if (key == 17){//ctrl
        	tipslayer_hide( uid );
        	notipslayer_hide( uid );
        	suggest_ajax( this );
        }else if (key != 9 && key != 27) { // not (TAB or ESC)
            suggest_clear_value( uid, this );
            tipslayer_hide( uid );
            notipslayer_hide( uid );
            suggest_ajax( this );
        }
        return false;
    }

    function suggest_ajax(input) {
        var uid = jQuery(input).attr('uid');
        var url = jQuery(input).attr('url')
        var str = input.value;

        // support dynamic url
        if (url == null || url == '') {
            var fn = window[uid + '_suggest_url'];
            url = fn();
        }
        
        // support local javascript function callback
        if (typeof(window[url]) == 'function') {
            var json = window[url](str);
            suggest_ajax_result_show(json, uid, input);
        } else {
            jQuery.getJSON(url + encodeURIComponent(str), null, function(data) {
                // support json data transform
                var json = suggest_transform_data(uid, data);
                suggest_ajax_result_show(json, uid, input);
            });
        }
    }
    
    function suggest_ajax_result_show(json, uid, input) {
        if (json.length == 0) {
            layer_hide(uid);
            notipslayer_show(uid,input);
            return;
        }
        var layer = layers[uid].empty();
        for (var i=0; i<json.length; i++) {
            var item = json[i];
            
            // common data transform
            if (item instanceof Array) {
                if (item.length >= 3) {
                    item = { hidden: item[0], val: item[1], html: item[2]};
                } else if (item.length == 2) {
                    item = { hidden: item[0], val: item[0], html: item[1]};
                }
            } else if (typeof(item.val) == 'undefined') {
                item = { hidden: item , val: item, html: item};
            } else if (typeof(item.hidden) == 'undefined') {
                item.hidden = item.val;
            }
            
            var div = jQuery('<div class="item" />');
            div.attr('val', item.val);
            div.attr('hidden', item.hidden);
            div.html(item.html);
            div.appendTo(layer);
            div.mouseover( function() {
                select_div_item( jQuery(this) );
            });
            div.mousedown( function() {
                layer_hide(uid);
                suggest_select_value(uid, input, jQuery(this).attr('val'), jQuery(this).attr('hidden') );
            });
        }
        // first is selected
        select_div_item( jQuery('>div:first', layer) );

        layer_show(uid, input);
    }

    function select_div_item(div) {
        jQuery('>div', div.parent()).removeClass('sel');
        div.addClass('sel');
    }

    function suggest_select_value(uid, input, val, hidden) {
        if (hiddens[uid]) {
            hiddens[uid].val( hidden );
        }
        
        var fn = window[uid + '_suggest_select_value'];
        if (typeof(fn) == 'function') {
            fn(input, val, hidden);
        } else {
            input.value = val;
        }
    }

    function suggest_clear_value(uid, input) {
        if (hiddens[uid]) {
            hiddens[uid].val( '' );
        }
        
        var fn = window[uid + '_suggest_clear_value'];
        if (typeof(fn) == 'function') {
            fn(input);
        }
    }

    function suggest_transform_data(uid, data) {
        var fn = window[uid + '_suggest_transform_data'];
        if (typeof(fn) == 'function') {
            return fn(data);
        } else {
            return data;
        }
    }

})(jQuery);

