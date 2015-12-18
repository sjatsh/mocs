package smtcl.mocs.beans.device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dreamwork.jasmine2.web.IWebControl;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

import smtcl.mocs.pojos.authority.Module;
import smtcl.mocs.pojos.authority.Page;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.services.device.IAuthorizeService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;

/**
 * �˵���������Ӧ�ĺ�̨ BEAN
 * @���ߣ�JiangFeng
 * @����ʱ�䣺2013-04-22 
 * @�޸��ߣ�
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
@SuppressWarnings("serial")
@ManagedBean(name = "MenuHeadBean")
@SessionScoped
public class MenuHeadBean implements Serializable {
	/**
	 * �����ർ����Ϣ��Ӧ����תURL
	 */
	private String urlPath = "../parts/part_class_config.xhtml";
	/**
	 * ǰ̨������ͽṹ��Ӧ�Ĳ�ͬҳ���action��Ϣ
	 */
	private String onNodeSelect = "ProductOutputBean.onNodeSelect";
	/**
	 * pageId
	 */
	private String pageId="mocs.sbgl.page.sbgl";
	/**
	 * ��ǰ�ڵ�����
	 */
	private String thisNodeName = "��ѡ��ڵ�";
	
	/**
	 * ��ǰ�ڵ�
	 */
	private String nodeStr;
	/**
	 * ���ҳ����Ϣ����
	 */
	private List<Page> menuPage=new ArrayList<Page>();
	/**
	 * ��ǰ���ڵ�λ��
	 */
	private List<String> thisPosition = new ArrayList<String>();
	/**
	 * ��ǰ�����ҳ��
	 */
	private String position="������ģ";
	/**
	 * I5ƽ̨�Ż���ַ
	 */
	private String authorityPath;
	/**
	 * ��ǰ·����Ϣ
	 */
	private List<String> pathList = new ArrayList<String>();
	/**
	 * ��ǰ�˵�����
	 */
	private List<Module> menuList=new ArrayList<Module>();
	/**
	 * �ڵ���
	 */
	private TreeNode root;
	/**
	 * ��ǰѡ�нڵ���Ϣ
	 */
	private TreeNode selectedNode; 
	/**
	 * Ȩ�޽ӿ�ʵ��
	 */
	private IOrganizationService organizationService = (IOrganizationService) ServiceFactory.getBean("organizationService");
	
	/**
	 * Ȩ�޽ӿ�ʵ��
	 */
	private IAuthorizeService authorizeService = (IAuthorizeService) ServiceFactory.getBean("authorizeService");
	private String userName;
	/**
	 * @�޸� ���
	 * @���� 2015��12��10��
	 */
	private TUser tUser;

	public String getUrlPath() {
		System.out.println(urlPath);
		//����������תʹ�� URL������
		String para = FaceContextUtil.getContext().getRequestParameterMap().get("pagename");
		System.out.println("pagename para:"+para);
		if(!StringUtils.isEmpty(para)) 
		{			
			pageId = Constants.MOCS_PATH_MAP.get(para.toString());
			String URL = (para.toString()).replace("faces", "xhtml");
			URL = URL.replace("/mocs/", "/");
			this.urlPath = URL;
			return urlPath;
		}		
		//����������תʹ�� URL������  end    
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		String param1 = StringUtils.getCookie(request, "pagename");
		if(!StringUtils.isEmpty(param1)){
			//���COOKIE
			StringUtils.delCookie(request, response, "pagename");
			
			pageId = Constants.MOCS_PATH_MAP.get(param1.toString());
			String URL = (param1.toString()).replace("faces", "xhtml");
			URL = URL.replace("/mocs/", "/");
			this.urlPath = URL;

			if (pageId.equals("zxgc.sbcx.page.oeeqsbj") || pageId.equals("zxgc.sbcx.page.sjbj")) {
				FaceContextUtil.getContext().getSessionMap().remove("CURRENTNODE");
			}
		}
		for(Module tt:menuList){
			System.out.println(tt.getLabel()+"---get set");
		}
		
		return urlPath;
	}
	
	/**
	 * ���ص�ǰȫ·��
	 */
	public void pathOnLoad() {
		pathList.clear();		
		String path = organizationService.getAllParentName(this.nodeStr);
		if(path!=null){
			String[] temp=path.split("/");
			for(int i=1;i<temp.length;i++){
			   if(!"��".equals(temp[i])){
				   pathList.add(temp[i]);
			   }
			}
		}		
	}

	/**
	 * ���������������õĺ�̨����
	 * @param event
	 */
	public void onNodeSelect(NodeSelectEvent event) {
		smtcl.mocs.beans.authority.cache.TreeNode currentNode = (smtcl.mocs.beans.authority.cache.TreeNode) event.getTreeNode().getData();
		if(!currentNode.isNocheck()){
			thisNodeName=currentNode.getNodeName();
			 HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			 session.setAttribute("nodeid2", currentNode.getNodeId());
		}
	}
	
	public void selectNode(NodeSelectEvent event){
		smtcl.mocs.beans.authority.cache.TreeNode currentNode = (smtcl.mocs.beans.authority.cache.TreeNode) event.getTreeNode().getData();
		if(!currentNode.isNocheck()){
			thisNodeName=currentNode.getNodeName();
			 HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			 session.setAttribute("nodeid2", currentNode.getNodeId());
		}
	}
	/**
	 * ��������˵���Ӧ�ĺ�̨ACTION
	 * @param menuName
	 */
	public void clickMenuList(String menuName) {
		thisPosition.clear();
		thisPosition.add(menuName);
		position=menuName;
		System.out.println(position+"------------");
		for (Module list : menuList) {
			if (list.getLabel().equals(menuName)) {
				menuPage = (List<Page>) list.getPages();
			}
		}
	}

	/**
	 * ����������Ӷ�Ӧ��ACTION
	 * @param url
	 * @param menuName
	 */
	public void clickMenu(String url,String menuName) {
		thisPosition.clear();
		thisPosition.add(position);
		if(menuName!=null){
			String[] temp=menuName.split(",");
			for(int i=0;i<temp.length;i++){
				thisPosition.add(temp[i]);
			}
		}
		
		pageId = Constants.MOCS_PATH_MAP.get(url);
		TUser user = (TUser) FaceContextUtil.getContext().getSessionMap().get(Constants.USER_SESSION_KEY);
		
		//�û��л�ҳ���жϽڵ�Ȩ��
		boolean isAuthority = organizationService.checkedClickNodeAuthority(user.getUserId(), pageId, "button.view", nodeStr);
		System.out.println(user.getUserId());
		System.out.println(pageId);
		System.out.println(nodeStr);
		if(!isAuthority){
			nodeStr=null;
			FaceContextUtil.getContext().getSessionMap().put("CURRENTNODE", null);
		}
		String URL = url.replace("faces", "xhtml");
		URL = URL.replace("/mocs/", "/");
		System.out.println("��ǰ�����ҳ��URL��" + URL);
		urlPath = URL;
	}
	
	/**
	 * ������ҳ��������Ӧ
	 * @param url
	 */
	public void clickMenu(String url){
		pageId = Constants.MOCS_PATH_MAP.get(url);
		String URL = url.replace("faces", "xhtml");
		URL = URL.replace("/mocs/", "/");
		System.out.println("��ǰ�����ҳ��URL��" + URL);
		urlPath = URL;
	}
	/**
	 * ������ת�¼�
	 * @param parm
	 */
	public void onClick(String parm,String parmTwo){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		
		if(parm != null && parm.indexOf("ljgxxdup") >= 0){
			String uu[] = parm.split(",");
			session.setAttribute("selectProcess", uu[0]);
			session.setAttribute("selectProcessPlan", uu[1]);
			session.setAttribute("processId", parmTwo);
			System.out.println("selectProcess:"+uu[0]+"  selectProcessPlan:"+uu[1]+"  processId:"+parmTwo);
			urlPath = "../parts/part_process_guide_update.xhtml";
			
		}else if(parm != null && parm.indexOf("ljgxxd") >= 0){
			String uu[] = parm.split(",");
			session.setAttribute("selectProcess", uu[0]);
			session.setAttribute("selectProcessPlan", uu[1]);
			urlPath = "../parts/part_process_guide.xhtml";
		}else if(parm!=null&&parm.indexOf("cpgcsj")>=0){
			urlPath = "../product/product_process_list.xhtml";
		}else if(parm!=null&&parm.indexOf("gczlsj")>=0){
			urlPath = "../product/process_quality_list.xhtml";
		}else if(parm!=null&&parm.indexOf("sunInfo")>=0){
			urlPath = "../storage/set_unit_Info.xhtml";
		}else if(parm!=null&&parm.indexOf("sunconver")>=0){
			urlPath = "../storage/set_conversion_Info.xhtml";
		}else if(parm!=null&&parm.indexOf("sun")>=0){
			urlPath = "../storage/set_unit.xhtml";
		}else if(parm!=null&&parm.indexOf("xjzzwl")>=0){
			urlPath = "../storage/organize_materiel_add.xhtml";
		}else if(parm!=null&&parm.indexOf("bzzzwl")>=0){
			session.setAttribute("organizeMateriel",parmTwo);
			urlPath = "../storage/organize_materiel_update.xhtml";
		}else if(parm!=null&&parm.indexOf("fhzzwlcx")>=0){
			urlPath = "../storage/organize_materiel_manage.xhtml";
		}
		
	}
	/**
	 * �˳���¼
	 */
	public void LogOut(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		/*Enumeration em = session.getAttributeNames();
		 while(em.hasMoreElements()){
			   System.out.println(em.nextElement().toString());
		}*/
		session.setAttribute(Constants.USER_SESSION_KEY, null);
		session.setAttribute(Constants.APPLICATION_ID, null);
		session.setAttribute("nodeid", null);
		session.setAttribute("swg.authority.session.user", null);
		session.setAttribute("factoryProfileBean", null);
		//session.setAttribute("javax.faces.request.charset", null);
		session.setAttribute("tsControlBean", null);
		//session.setAttribute("MenuHeadBean", null);
		//session.setAttribute("dreamwork.jasmine2.locale", null);
		//session.setAttribute("menuBean", null);
		//session.setAttribute("URLMAPPING", null);
		session.setAttribute("nodeid2", null);
		//session.setAttribute("com.sun.faces.renderkit.ServerSideStateHelper.LogicalViewMap", null);
		//	���cookie
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		StringUtils.saveCookie(request, response, "menuActive", "");
		StringUtils.saveCookie(request, response, "defNode", "");
//		StringUtils.delCookie(request, response, "menuActive");
//		StringUtils.delCookie(request, response, "defNode");

	}
	
	/**
	 * �޲ι��캯�� ������ȡ���ṹ
	 */
	@SuppressWarnings({ "unchecked" })
	public MenuHeadBean() {		
		
		tUser = (TUser) FaceContextUtil.getContext().getSessionMap().get(Constants.USER_SESSION_KEY);
		userName=tUser.getLoginName();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		menuList = authorizeService.getMenu(tUser.getUserId(),
				Constants.APPLICATION_ID,
				IWebControl.LOCALE_KEY);
		clickMenuList("������ģ");	
		for(Module tt:menuList){
			System.out.println(tt.getLabel()+"---gouzao");
		}
		
		String nodeId  = StringUtils.getCookie(request,"nodeidvalue");
		root = organizationService.returnTree(tUser.getUserId() , pageId );
		
		System.out.println("COOKIEid--"+nodeId);
		
		Map<String, String> temp =(Map<String, String>) FaceContextUtil.getSessionMap().get("URLMAPPING");
		authorityPath=temp.get("authURL");
		String nodeName=temp.get("defaultNode");
		
		List nodeidlist=new ArrayList();//��֤Ĭ�Ͻڵ�
		List<smtcl.mocs.beans.authority.cache.TreeNode> list = authorizeService.getAuthorizedNodeTree(tUser.getUserId(), pageId);
		if (null != list & list.size() > 0) {
			smtcl.mocs.beans.authority.cache.TreeNode tnodes = list.get(0);
			nodeidlist=this.getNodesAllId(tnodes, nodeidlist); 
		}
		Collection<Parameter> parameters = new HashSet<Parameter>();//����Ĭ�Ͻڵ�
		parameters.add(new Parameter("t.nodeName", "nodeName", nodeName, Operator.EQ));
		List<Map<String, Object>> dnode=organizationService.get_All_Node_By_nodeClass(parameters);
		if(null!=dnode&&dnode.size()>0){
			Map<String, Object> nodemap=dnode.get(0);
			if(nodeidlist.contains(nodemap.get("nodeId")+"")){
//				HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
				session.setAttribute("nodeid2", nodemap.get("nodeId"));
				this.thisNodeName=nodeName;
			}
			
		}
	}
	
	public List getNodesAllId(smtcl.mocs.beans.authority.cache.TreeNode tnodes,
			List nodeIdList) {
		nodeIdList.add(tnodes.getNodeId());
		for (smtcl.mocs.beans.authority.cache.TreeNode tt : tnodes.getChildNodes()) {
			getNodesAllId(tt, nodeIdList);
		}
		return nodeIdList;
	}
	/*--------------------------------------------------------------------------------------------------------------*/

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public List<Page> getMenuPage() {
		return menuPage;
	}

	public void setMenuPage(List<Page> menuPage) {
		this.menuPage = menuPage;
	}

	public String getOnNodeSelect() {
		
		return onNodeSelect;
	}

	public void setOnNodeSelect(String onNodeSelect) {
		this.onNodeSelect = onNodeSelect;
	}

	public String getNodeStr() {
		return nodeStr;
	}

	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}

	public String getAuthorityPath() {
		return authorityPath;
	}
	
	public void setAuthorityPath(String authorityPath) {
		this.authorityPath = authorityPath;
	}

	public String getThisNodeName() {
		return thisNodeName;
	}

	public void setThisNodeName(String thisNodeName) {
		this.thisNodeName = thisNodeName;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public void setMenuList(List<Module> menuList) {
		this.menuList = menuList;
	}

	public List<String> getPathList() {
		return pathList;
	}

	public void setThisPosition(List<String> thisPosition) {
		this.thisPosition = thisPosition;
	}

	public void setPathList(List<String> pathList) {
		this.pathList = pathList;
	}
	
	public List<String> getThisPosition() {
		return thisPosition;
	}
	
	public List<Module> getMenuList() {
		return menuList;
	}
	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @�޸� ���
	 * @���� 2015��12��10��
	 * @return
	 */
	public TreeNode getRoot() {
		
		root = organizationService.returnTree(tUser.getUserId() , pageId );
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
