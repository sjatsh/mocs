if (!BROWSER) var BROWSER = (function () {
    var text = window.navigator.userAgent;
    return text.indexOf ('MSIE') != -1 ?
            text.indexOf ('MSIE 6.0') != -1 ? { isIE6 : true, isIE : true } : { isIE : true } :
            text.indexOf ('Chrome') != -1 ? { isChrome : true, isWebKit : true } :
                    text.indexOf ('Safari') != -1 ? { isSafari : true, isWebKit : true } :
                            text.indexOf ('Firefox') != -1 ? { isFirefox : true, isGecko : true } :
                            {};
}) ();

if (!JasmineAjax) var JasmineAjax = function () {
    var request = BROWSER.isIE ? new ActiveXObject ("Microsoft.XMLHTTP") : new XMLHttpRequest ();
    this.send = function (name, method, parameters, onload, onerror, oncleanup) {
        var requestMethod = (method || "get").toUpperCase();
        if (requestMethod == "GET") name += "?" + parameters;
        request.open (requestMethod, name);
        if (requestMethod == "POST")
            request.setRequestHeader ("Content-Type", "application/x-www-form-urlencoded");
        request.onreadystatechange = function () {
            if (request.readyState == 4) {
                try {
                    switch (request.status) {
                        case 200 :
                            if (onload) onload (request);
                            break;
                        case 404 :
                            alert ("找不到服务：" + name);
                            break;
                        case 405 :
                            alert ("Http Request Method [" + requestMethod + "] NOT ALLOWED!");
                            break;
                        case 406 :
                            alert (name + " is NOT a Jasmine Ajax Service!");
                            break;
                        default :
                            if (onerror)
                                onerror ('unknown error', request);
                            else
                                alert (request.status);
                            break;
                    }
                } catch (E) {
                    if (onerror) onerror (E, request);
                } finally {
                    if (oncleanup) oncleanup (request);
                }
            }
        };
        request.send (requestMethod == "POST" ? parameters : null);
    };
};