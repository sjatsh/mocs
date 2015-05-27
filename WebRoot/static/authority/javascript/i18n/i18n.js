var I18n = {
    changeLanguage : function (language) {
        var ajax = new JasmineAjax ();
        ajax.send ('i18n/changeLanguage.ajax', 'POST', 'language=' + language, function (response) {
            var result = eval ('(' + response.responseText + ')');
            if (result.action == 'reload') {
                location.reload ();
            }
        }, function (E, response) {}, null);
    }
};