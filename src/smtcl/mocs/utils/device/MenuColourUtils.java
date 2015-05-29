package smtcl.mocs.utils.device;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ËÎ¿­°º
 * CreateTime:2014/12/30
 * Description:
 */
public class MenuColourUtils {

    private Map<String,String> colourMenu = null;

    private static MenuColourUtils colourUtils = null;

    public static MenuColourUtils getMenuColourUtils(){
        if(colourUtils == null){
            colourUtils = new MenuColourUtils();

        }
        return colourUtils;
    }

    public Map<String, String> getColourMenu() {
        colourMenu = new HashMap<String,String>();
        colourMenu.put("mocs.module.cbhs","8EBA84");
        colourMenu.put("mocs.module.cjgl","F4DD0C");
        colourMenu.put("mocs.module.sbgl","E87D4D");
        colourMenu.put("mocs.module.scdd","57A8D1");
        colourMenu.put("mocs.module.zyjh","8EBA84");

        colourMenu.put("mocs.cbhs.page.cbhs","8EBA84");
        colourMenu.put("mocs.cjgl.page.cjgl","F4DD0C");
        colourMenu.put("mocs.sbgl.page.sbgl","E87D4D");
        colourMenu.put("mocs.scdd.page.scdd","57A8D1");
        colourMenu.put("mocs.sbgl.page.sjtj","8EBA84");

        colourMenu.put("mocs.gcjm.ljpz.ljgxpz","8EBA84");
        colourMenu.put("mocs.sbgl.page.sbgl","F4DD0C");
        colourMenu.put("mocs.sbgl.page.sjbj","E87D4D");
        colourMenu.put("mocs.sbgl.page.oeeqsfx","57A8D1");
        colourMenu.put("mocs.sbgl.page.oeeqsbj","8EBA84");

        colourMenu.put("mocs.sbgl.page.oeefx","8EBA84");
        colourMenu.put("mocs.sbgl.page.jpfx","F4DD0C");
        colourMenu.put("mocs.sbgl.page.jgsjfx","E87D4D");
        colourMenu.put("mocs.sbgl.page.jcsjfx","57A8D1");
        colourMenu.put("mocs.module.bbcx","8EBA84");

        colourMenu.put("mocs.zzpcx.page.zzpcx","8EBA84");
        colourMenu.put("mocs.module.zzpcx","F4DD0C");
        colourMenu.put("mocs.bbcx.page.yclbb","E87D4D");
        colourMenu.put("mocs.bbcx.page.lstlpccx","57A8D1");
        colourMenu.put("mocs.bbcx.page.scqkhb","8EBA84");

        colourMenu.put("mocs.bbcx.page.jggdmx","8EBA84");
        colourMenu.put("mocs.bbcx.page.ryxqb","F4DD0C");
        colourMenu.put("mocs.bbcx.page.sbmxb","E87D4D");
        colourMenu.put("mocs.module.scsj","57A8D1");
        colourMenu.put("mocs.scsj.page.sdbg","8EBA84");

        colourMenu.put("mocs.scsj.page.bgxz","8EBA84");
        colourMenu.put("mocs.scsj.page.scsjcx","F4DD0C");
        colourMenu.put("mocs.bbcx.page.scbfbb","E87D4D");
        colourMenu.put("mocs.scsj.page.cpctrk","57A8D1");
        colourMenu.put("mocs.kcgl.page.kcwlcx","8EBA84");

        colourMenu.put("mocs.module.kcgl","8EBA84");
        colourMenu.put("mocs.scsj.page.scsjcx","F4DD0C");
        colourMenu.put("mocs.bbcx.page.scbfbb","E87D4D");
        colourMenu.put("mocs.scsj.page.cpctrk","8EBA84");
        colourMenu.put("mocs.kcgl.page.kcwlcx","8EBA84");
        return colourMenu;
    }

    public void setColourMenu(Map<String, String> colourMenu) {
        this.colourMenu = colourMenu;
    }
}
