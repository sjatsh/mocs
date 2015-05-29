package smtcl.mocs.services.device;

import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericService;

import smtcl.mocs.pojos.device.TNodeidMatch;

public interface INodeMatchService extends IGenericService<TNodeidMatch, String>{
	public List<Map<String, Object>> getMatchedNode(String wisid);
	public void deleteNodeid(String wisid);
	public void saveNodeid(String wisid,String nodename,String a3id);
	public void updateNodeid(String wisid,String nodename,String a3id);
	public List<Map<String, Object>> getMatchedNodeform(String a3);
}
