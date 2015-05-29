package smtcl.mocs.services.device.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;

import smtcl.mocs.pojos.device.TNodeidMatch;
import smtcl.mocs.services.device.INodeMatchService;

public class NodeMatchServiceImpl extends GenericServiceSpringImpl<TNodeidMatch, String> implements
		INodeMatchService {

	@Override
	public List<Map<String, Object>> getMatchedNode(String wisid) {//支持多数据源切换MySQL&Oracle
		// TODO Auto-generated method stub
		String hql = "select new map(a.nodeida3 as a3, a.nodename as name, a.nodeidwis as wis,a.nodedatasource as nodedatasource) from TNodeidMatch a"
				+" where a.nodeidwis ='"+wisid+"'";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return dao.executeQuery(hql, parameters);
	}
	@Override
	public List<Map<String, Object>> getMatchedNodeform(String a3) {//支持多数据源切换MySQL&Oracle
		// TODO Auto-generated method stub
		String hql = "select new map(a.nodeida3 as a3, a.nodename as name, a.nodeidwis as wis,a.nodedatasource as nodedatasource) from TNodeidMatch a"
				+" where a.nodeida3 ='"+a3+"'";
		Collection<Parameter> parameters = new HashSet<Parameter>();
		return dao.executeQuery(hql, parameters);
	}
	@Override
	public void deleteNodeid(String wisid) {//支持多数据源切换MySQL&Oracle
		// TODO Auto-generated method stub
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="delete TNodeidMatch t" +
				" where t.nodeidwis = :nodeidwis";
		parameters.add(new Parameter("nodeidwis", wisid, Operator.EQ));
		dao.executeUpdate(hql, parameters); 
	}

	@Override
	public void saveNodeid(String wisid, String nodename, String a3id) {
		// TODO Auto-generated method stub
		TNodeidMatch match=new TNodeidMatch();
		match.setNodeida3(a3id);
		match.setNodeidwis(wisid);
		
		match.setNodename(nodename);
		dao.save(match);
	}

	@Override
	public void updateNodeid(String wisid, String nodename, String a3id) { //支持多数据源切换MySQL&Oracle
		// TODO Auto-generated method stub
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="update TNodeidMatch t set t.nodename = :nodename ,t.nodeida3 = :nodeida3" +
				" where nodeidwis = :nodeidwis";
		parameters.add(new Parameter("nodename", nodename, Operator.EQ));
		parameters.add(new Parameter("nodeida3", a3id, Operator.EQ));
		parameters.add(new Parameter("nodeidwis", wisid, Operator.EQ));
		dao.executeUpdate(hql, parameters); 
	}

}
