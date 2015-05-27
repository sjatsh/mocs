package smtcl.mocs.utils.device;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultAttribute;

public class ParseXML {
	/**
	 * @param args
	 */
	
	private static String readFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }
	public static boolean praseElement_Has_On(Element element){
		 boolean flag=false;
			Iterator<DefaultAttribute> its= element.attributeIterator();
			while(its.hasNext()){
				DefaultAttribute daatt=its.next();
				if("ON".equalsIgnoreCase(daatt.getValue())){
					flag=true;
					break;
				}
			}
			
		return flag;
		 
	 }

	public static String praxml(String allstr) {// 第一个参数为传递的xml字符串
		Document document = null;
		StringBuffer xml_to_Str= new StringBuffer();
		try {
			document = DocumentHelper.parseText("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+allstr);
			//document = DocumentHelper.parseText(allstr);
			Element el = document.getRootElement();
			for(Iterator it=el.elementIterator();it.hasNext();){  
				 Element element = (Element) it.next(); 
				 if(!praseElement_Has_On(element)){
					 element.detach();
					// temp_list.add(xml_To_Str(element));
				 }
			}  
			if(el.elementIterator().hasNext()){
				xml_to_Str.append(document.asXML().replaceAll("\\s{3,}", "").replaceAll("\\n", "").substring(38));
			}else{
					xml_to_Str.append("");
			}
			
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml_to_Str.toString();
	}
	
	 public static String  getNodeAttributeName(String xmlstr,String componentType){
	    	Document document = null;
			StringBuffer xml_to_Str= new StringBuffer();
			try {
				document = DocumentHelper.parseText("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+xmlstr);
				Element el = document.getRootElement();
				for(Iterator it=el.elementIterator();it.hasNext();){  
					 Element element = (Element) it.next(); 
					 if(!StringUtils.isEmpty(componentType)&&!componentType.equals(element.getName())) continue;
					 xml_to_Str.append(element.getName()+"@");
					 Iterator<DefaultAttribute> its= element.attributeIterator();
						while(its.hasNext()){
							DefaultAttribute daatt=its.next();
							if("ON".equalsIgnoreCase(daatt.getValue())){
							xml_to_Str.append("'"+daatt.getName().substring(daatt.getName().indexOf(".")+1)+"' , ");
							}
						}
						xml_to_Str.append("&");
				}  
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
			return xml_to_Str.toString();
	    }
	
    
    public static String list_map_to_String(List<Map<String, Object>> templist,String root){
    	if(templist.isEmpty()){
    		return "";
    	}
    	
    	String[] arraysublistsize=templist.get(templist.size()-1).get("component-num").toString().split("&");
    	int[] subListSize = new int [arraysublistsize.length];
    	for(int i=0;i<arraysublistsize.length;i++){
    		if(i==0){
    			
    			subListSize[i]=Integer.valueOf(arraysublistsize[i]);
    			
    		}else{
    			subListSize[i]=Integer.valueOf(arraysublistsize[i])-1-Integer.valueOf(arraysublistsize[i-1]);
    		}
    		
    	}
    	for(int i=0;i<templist.size();i++){
    		Map<String, Object> temp=templist.get(i);
    		if(temp.get("component-num")!=null){
    			templist.remove(i);
    		}
    	}
    	Document document = DocumentHelper.createDocument();
    	
    	document.setXMLEncoding("GBK");
    	try {
    		
			Element rootElement = document.addElement(root);
			
			for(int i=0;i<subListSize.length;i++){
				int pointer_m9Variant=0; int indexs=0;
				List<Map<String, Object>> sublist=templist.subList(0, subListSize[i]);
				 
				 int  jishu=0;
				   while(indexs<sublist.size()&&pointer_m9Variant<sublist.size()&&jishu<sublist.size()){
					
				     String pointer_m9Variant_Str=(String)sublist.get(pointer_m9Variant).get("m9Variant");
				     Element component =rootElement.addElement((String) sublist.get(0).get("component")) ;
	    		     component.addAttribute("m9Variant", (String)sublist.get(pointer_m9Variant).get("m9Variant"));
	    	         component.addAttribute("alertInfo", (String)sublist.get(pointer_m9Variant).get("alertInfo"));
	    	
	    			  for(int index=indexs ;index<sublist.size();index++){
	    				  if(!pointer_m9Variant_Str.equalsIgnoreCase((String)sublist.get(index).get("m9Variant"))){
	    					  pointer_m9Variant=indexs=index;
	    					  break;
	    				  }
	    				  Element cas_solElement = component.addElement("cassol");
	  		    		  cas_solElement.addAttribute("cause", (String)sublist.get(index).get("cause"));
			    		  cas_solElement.addAttribute("solution", (String)sublist.get(index).get("solution"));
			    		  pointer_m9Variant=indexs=index;
			    		  jishu++;
	    			  }
		    		
				   }
	    		//templist.removeAll(templist.subList(0, subListSize[i]));
	    		
	    	   templist.removeAll(sublist);
	    		
	    		
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    	 
		return document.asXML();
    }
    public  static String remove_irrelevant_factor(String xmlStr,String nodename){
    	
    	if(xmlStr.isEmpty()||nodename.isEmpty()){
    		return "";
    	}
    	Document document = null;
    	try {
			document = DocumentHelper.parseText(xmlStr);
			Element el = document.getRootElement();
			for(Iterator it=el.elementIterator();it.hasNext();){  
				 Element element = (Element) it.next(); 
				 Iterator<DefaultAttribute> its= element.attributeIterator();
				 System.out.println(element.attributeValue("name"));
				 
				if(!element.attributeValue("name").equalsIgnoreCase(nodename)){
					element.detach();
				}
			} 
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(document.asXML());
		return document.asXML();
    }
    public static String list_map_to_String_for_flash(List<Map<String, Object>> templist,String root){
    	if(templist.isEmpty()){
    		return "";
    	}
    	
    	String[] arraysublistsize=templist.get(templist.size()-1).get("component-num").toString().split("&");
    	int[] subListSize = new int [arraysublistsize.length];
    	for(int i=0;i<arraysublistsize.length;i++){
    		if(i==0){
    			
    			subListSize[i]=Integer.valueOf(arraysublistsize[i]);
    			
    		}else{
    			subListSize[i]=Integer.valueOf(arraysublistsize[i])-1-Integer.valueOf(arraysublistsize[i-1]);
    		}
    		
    	}
    	for(int i=0;i<templist.size();i++){
    		Map<String, Object> temp=templist.get(i);
    		if(temp.get("component-num")!=null){
    			templist.remove(i);
    		}
    	}
    	Document document = DocumentHelper.createDocument();
    	
    	document.setXMLEncoding("GBK");
    	try {
    		
			Element rootElement = document.addElement(root);
			
			for(int i=0;i<subListSize.length;i++){
				int pointer_m9Variant=0; int indexs=0;
				List<Map<String, Object>> sublist=templist.subList(0, subListSize[i]);
				 
				 int  jishu=0;
				   while(indexs<sublist.size()&&pointer_m9Variant<sublist.size()&&jishu<sublist.size()){
					
				     String pointer_m9Variant_Str=(String)sublist.get(pointer_m9Variant).get("m9Variant");
				     Element component =rootElement.addElement("elementname") ;
				     component.addAttribute("name", (String) sublist.get(0).get("component"));
	    		     component.addAttribute("m9Variant", (String)sublist.get(pointer_m9Variant).get("m9Variant"));
	    	         component.addAttribute("alertInfo", (String)sublist.get(pointer_m9Variant).get("alertInfo"));
	    	
	    			  for(int index=indexs ;index<sublist.size();index++){
	    				  if(!pointer_m9Variant_Str.equalsIgnoreCase((String)sublist.get(index).get("m9Variant"))){
	    					  pointer_m9Variant=indexs=index;
	    					  break;
	    				  }
	    				  Element cas_solElement = component.addElement("cassol");
	  		    		  cas_solElement.addAttribute("cause", (String)sublist.get(index).get("cause"));
			    		  cas_solElement.addAttribute("solution", (String)sublist.get(index).get("solution"));
			    		  pointer_m9Variant=indexs=index;
			    		  jishu++;
	    			  }
		    		
				   }
	    		templist.removeAll(templist.subList(0, subListSize[i]));
	    		
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return document.asXML();
    }
}
