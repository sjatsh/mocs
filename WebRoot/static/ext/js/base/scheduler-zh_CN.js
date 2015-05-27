/*

Ext Scheduler 2.2.7
Copyright(c) 2009-2013 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 * English translations for the Scheduler component
 *
 * NOTE: To change locale for month/day names you have to use the corresponding Ext JS language file.
 */
Ext.define('Sch.locale.En', {
    extend      : 'Sch.locale.Locale',
    singleton   : true,

    l10n        : {
        'Sch.util.Date' : {
            unitNames : {
                YEAR        : { single : '年',    plural : '年',   abbrev : 'yr' },
                QUARTER     : { single : '季度', plural : '季度',abbrev : 'q' },
                MONTH       : { single : '月',   plural : '月',  abbrev : 'mon' },
                WEEK        : { single : '周',    plural : '周',   abbrev : 'w' },
                DAY         : { single : '天',     plural : '天',    abbrev : 'd' },
                HOUR        : { single : '小时',    plural : '小时',   abbrev : 'h' },
                MINUTE      : { single : '分',  plural : '分', abbrev : 'min' },
                SECOND      : { single : '秒',  plural : '秒', abbrev : 's' },
                MILLI       : { single : '毫秒',      plural : '毫秒',      abbrev : 'ms' }
            }
        },

        'Sch.plugin.CurrentTimeLine' : {
            tooltipText : '当前时间'
        },

        'Sch.plugin.EventEditor' : {
            saveText    : '保存',
            deleteText  : '删除',
            cancelText  : '取消'
        },

        'Sch.plugin.SimpleEditor' : {
            newEventText    : '新任务...'
        },

        'Sch.widget.ExportDialog' : {
            generalError                : '有错误发生，请稍后重试.',
            title                       : '导出设置',
            formatFieldLabel            : '纸张格式',
            orientationFieldLabel       : '纸张方向',
            rangeFieldLabel             : '导出范围',
            showHeaderLabel             : '添加页号',
            orientationPortraitText     : '纵向',
            orientationLandscapeText    : '横向',
            completeViewText            : '完成时间表',
            currentViewText             : '当前视图',
            dateRangeText               : '日期范围',
            dateRangeFromText           : '导出源',
            pickerText                  : '调整行列',
            dateRangeToText             : '导出到',
            exportButtonText            : '导出',
            cancelButtonText            : '取消',
            progressBarText             : '正在导出...',
            exportToSingleLabel         : '导出为单页',
            adjustCols                  : '调整列宽',
            adjustColsAndRows           : '调整列宽和行高',
            specifyDateRange            : '指定日期范围'
        },

        // -------------- View preset date formats/strings -------------------------------------
        'Sch.preset.Manager' : function () {
            var M = Sch.preset.Manager,
                vp = M.getPreset("hourAndDay");

            if (vp) {
                vp.displayDateFormat = 'G:i';
                vp.headerConfig.middle.dateFormat = 'G:i';
                vp.headerConfig.top.dateFormat = 'D d/m';
            }

            vp = M.getPreset("dayAndWeek");
            if (vp) {
                vp.displayDateFormat = 'm/d h:i A';
                vp.headerConfig.middle.dateFormat = 'D d M';
            }

            vp = M.getPreset("weekAndDay");
            if (vp) {
                vp.displayDateFormat = 'm/d';
                vp.headerConfig.bottom.dateFormat = 'd M';
                vp.headerConfig.middle.dateFormat = 'Y F d';
            }

            vp = M.getPreset("weekAndMonth");
            if (vp) {
                vp.displayDateFormat = 'm/d/Y';
                vp.headerConfig.middle.dateFormat = 'm/d';
                vp.headerConfig.top.dateFormat = 'm/d/Y';
            }

            vp = M.getPreset("weekAndDayLetter");
            if (vp) {
                vp.displayDateFormat = 'm/d/Y';
                vp.headerConfig.middle.dateFormat = 'D d M Y';
            }

            vp = M.getPreset("weekDateAndMonth");
            if (vp) {
                vp.displayDateFormat = 'm/d/Y';
                vp.headerConfig.middle.dateFormat = 'd';
                vp.headerConfig.top.dateFormat = 'Y F';
            }

            vp = M.getPreset("monthAndYear");
            if (vp) {
                vp.displayDateFormat = 'm/d/Y';
                vp.headerConfig.middle.dateFormat = 'M Y';
                vp.headerConfig.top.dateFormat = 'Y';
            }

            vp = M.getPreset("year");
            if (vp) {
                vp.displayDateFormat = 'm/d/Y';
                vp.headerConfig.middle.dateFormat = 'Y';
            }
        }
    }
});
