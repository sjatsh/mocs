/**
 * com.swg.authority.util VelocityHelper.java
 */
package smtcl.mocs.utils.authority;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.log.NullLogChute;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

/**
 * Velocity 工具
 * @author gaokun
 * @create Oct 18, 2012 11:42:25 AM
 */
public class VelocityHelper {
	
	/**
	 * VelocityHelper 日志
	 */
	private final static Logger logger = Logger.getLogger(VelocityHelper.class);
	
	private static StringResourceRepository SRP = null;
	
	static{
		Velocity.setProperty(Velocity.RESOURCE_LOADER, "string");
        Velocity.addProperty("string.resource.loader.class", StringResourceLoader.class.getName());
        Velocity.addProperty("string.resource.loader.modificationCheckInterval", "0");
        Velocity.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM_CLASS, NullLogChute.class.getName());

        try {
			Velocity.init();
		} catch (Exception e) {
			logger.error(String.format("smtcl.mocs.beans.authority.VelocityHelper Velocity init error!") + e.getMessage());
		}
        //init DynamicLoad.vm
        SRP = StringResourceLoader.getRepository();
//        SRP.putStringResource("DynamicLoad.vm", resource("/com/swg/authority/component/web/velocity/tree/DynamicLoad.vm"));
        SRP.putStringResource("Flat.vm", resource("/smtcl/mocs/beans/authority/component/velocity/tree/Flat.vm"));
        SRP.putStringResource("Init.vm", resource("/smtcl/mocs/beans/authority/component/velocity/tree/Init.vm"));
        SRP.putStringResource("Pop.vm", resource("/smtcl/mocs/beans/authority/component/velocity/tree/Pop.vm"));
        SRP.putStringResource("Pop_Fun.vm", resource("/smtcl/mocs/beans/authority/component/velocity/tree/Pop_Fun.vm"));
	}
	
	public static String resource(String path){
		return resource(path, "utf-8");
	}
	
	public static String resource(String path, String charset){
		String result = null;
		if(StringUtils.isNotEmpty(path) ){
			path = path.trim();
			if(!path.startsWith("/")){
				path = "/" + path;
			}
			
			InputStream in = null;
			BufferedReader br = null;
			try{
				in = VelocityHelper.class.getResourceAsStream(path);
			
				br = new BufferedReader(new InputStreamReader(in, charset));
				StringBuilder sb = new StringBuilder();
				
				String line = null;
				while((line = br.readLine()) != null){
					sb.append(line +"\r\n");
				}
				result = sb.toString();
			}catch(Exception e){
				logger.error(String.format("smtcl.mocs.beans.authority.VelocityHelper parse template[%s] error!", path) + e.getMessage());
			}finally{
				HelperUtil.close(br);
				HelperUtil.close(in);
			}
		}
		return result;
	}
	
	public static String process(String templateName, Map<String, Object> map){
		return process(templateName, map, "utf-8");
	}
	
	public static String process(String templateName, Map<String, Object> map, String charset){
		String result = null;

		VelocityContext context = new VelocityContext();		
		for (Entry<String, Object> entry : map.entrySet()) {
			context.put(entry.getKey(), entry.getValue());
		}

		Template template = null;
		try{
			template = Velocity.getTemplate(templateName, charset);
			StringWriter sw = new StringWriter();
			template.merge(context, sw);
			result = sw.toString();
		}catch( Exception e ){
			logger.error(String.format("smtcl.mocs.beans.authority.VelocityHelper process [%s] error!", templateName) + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
	}

}
