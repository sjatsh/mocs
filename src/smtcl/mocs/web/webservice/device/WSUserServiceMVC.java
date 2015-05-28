package smtcl.mocs.web.webservice.device;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.dreamwork.persistence.ServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import smtcl.mocs.common.device.LogHelper;
import smtcl.mocs.common.device.Md5;
import smtcl.mocs.pojos.authority.Module;
import smtcl.mocs.pojos.job.TProgramInfo;
import smtcl.mocs.services.device.IAuthorizeService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.services.device.IResourceService;
import smtcl.mocs.services.device.IWSUserService;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.StringUtils;
import sun.misc.BASE64Decoder;

/**
 * WebService用户登录调用接口
 * 
 * @创建者:JiangFeng
 * @修改者:songkaiang
 * @备注:
 * @创建时间：2013-04-26
 * @修改日期：
 * @修改说明：
 * @版本：V1.0
 */
@Controller
@RequestMapping("/WSUserService")
public class WSUserServiceMVC {
	IWSUserService userService = (IWSUserService) ServiceFactory
			.getBean("wsUserService");
	IAuthorizeService authorizeService = (IAuthorizeService) ServiceFactory
			.getBean("authorizeService");
	IOrganizationService organizationService = (IOrganizationService) ServiceFactory
			.getBean("organizationService");
	IResourceService resourceService = (IResourceService) ServiceFactory
			.getBean("resourceService");

	/**
	 * 验证用户登录
	 * 
	 * @param username
	 *            用户Id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/userLoginModule", method = RequestMethod.POST)
	public ModelAndView userLoginModule(String username, String userpwd) {
		Map<String, Object> all = new HashMap<String, Object>();

		Map<String, Object> result = userService.userLogin(username, userpwd);
		try {
			if (result.get("msg").equals(Constants.USER_LOGIN_MSG[2])) {
				String userId = result.get("userId").toString();
				String name = result.get("name").toString();
				List<Module> userModule = authorizeService.getMenu(userId,
						"mocs", null);

				// 先过滤对set集合的拆解
				JsonConfig config = new JsonConfig();
				config.setJsonPropertyFilter(new PropertyFilter() {
					@Override
					public boolean apply(Object arg0, String arg1, Object arg2) {// 过盧不需要的字段
						if (arg1.equals("module") || arg1.equals("app")
								|| arg1.equals("JSONObject")
								|| arg1.equals("buttons")) {
							return true;
						} else {
							return false;
						}
					}
				});
				// 通过JSONArray转为字符串
				JSONArray gg = new JSONArray().fromObject(userModule, config);

				all.put("userId", userId);
				all.put("username", name);
				all.put("authority", gg);

				all.put("success", true);
				all.put("msg", Constants.USER_LOGIN_MSG[2]);
			} else {
				all.put("success", false);
				all.put("msg", result.get("msg"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			all.put("success", false);
			all.put("msg", Constants.USER_LOGIN_MSG[3]);
		}

		return new ModelAndView("/userinfo/show", all);
	}

	/**
	 * 获取当前节点的直接子节点
	 * 
	 * @param username
	 *            用户Id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/getAllNodesByParentNodeId", method = RequestMethod.GET)
	public ModelAndView getAllNodesByParentNodeId(String nodeId, String userId) {
		Map<String, Object> all = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> result = organizationService
					.getAllNodesByParentNodeId(nodeId, userId);
			if (null != result && result.size() > 0) {
				all.put("data", result);
				all.put("success", true);
				all.put("msg", Constants.USER_LOGIN_MSG[2]);
			} else {
				all.put("data", result);
				all.put("success", false);
				all.put("msg", "没有子节点");
			}
		} catch (Exception e) {
			e.printStackTrace();
			all.put("data", null);
			all.put("success", false);
			all.put("msg", Constants.USER_LOGIN_MSG[3]);
		}
		return new ModelAndView("/userinfo/show", all);
	}

	/**
	 * 生产任务管理
	 * 
	 * @param equSerialNo
	 * @return
	 */
	@RequestMapping(value = "/getProduceTask", method = RequestMethod.POST)
	public ModelAndView getProduceTask(String equSerialNo, String processNo,
			String partName, String materialName, String checkRework) {
		Map<String, Object> all = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> result = userService
					.getProduceTask(equSerialNo);
			if (null != result && result.size() > 0) {
				all.put("data", result);
				all.put("success", true);

			} else {
				all.put("data", result);
				all.put("success", false);

			}
		} catch (Exception e) {
			e.printStackTrace();
			all.put("data", null);
			all.put("success", false);
			all.put("msg", Constants.USER_LOGIN_MSG[3]);
		}
		return new ModelAndView("/userinfo/show", all);
	}

	/**
	 * 更新生产任务
	 * 
	 * @param equSerialNo
	 *            机床序列号
	 * @return
	 */
	@RequestMapping(value = "/updateProduceTask", method = RequestMethod.POST)
	public ModelAndView updateProduceTask(String equSerialNo,
			String jobDispatchNo, String jobRepeat) {
		Map<String, Object> all = new HashMap<String, Object>();
		try {
			Boolean result = userService.updateProduceTask(equSerialNo,
					jobDispatchNo);
			if (result) {
				all.put("msg", Constants.USER_UPDATE_MSG[0]);
				all.put("success", true);

			} else {
				all.put("msg", Constants.USER_UPDATE_MSG[1]);
				all.put("success", false);

			}
		} catch (Exception e) {
			all.put("msg", Constants.USER_UPDATE_MSG[1]);
			all.put("success", false);
		}
		return new ModelAndView("/userinfo/show", all);
	}

	/**
	 * 切换工单
	 * 
	 * @param equSerialNo
	 *            机床序列号
	 * @return
	 */
	@RequestMapping(value = "/jobSwitch", method = RequestMethod.POST)
	public ModelAndView changeProduceTask(String equSerialNo, String ID) {
		LogHelper.log("jobSwitch........", equSerialNo + ID);
		Map<String, Object> all = new HashMap<String, Object>();
		try {
			Map<String, Object> result = userService.changeProduceTask(
					equSerialNo, ID);
			if (result != null) {
				all.put("msg", Constants.USER_UPDATE_MSG[0]);
				all.put("success", true);
				all.put("data", result);

			} else {
				all.put("msg", Constants.USER_UPDATE_MSG[1]);
				all.put("success", false);
				all.put("data", null);

			}
		} catch (Exception e) {
			all.put("msg", Constants.USER_UPDATE_MSG[1]);
			all.put("success", false);
		}
		return new ModelAndView("/userinfo/show", all);
	}

	/**
	 * 切换模式
	 * 
	 * @param equSerialNo
	 *            机床序列号
	 * @return
	 */
	@RequestMapping(value = "/switchMode", method = RequestMethod.POST)
	public ModelAndView switchMode(String equSerialNo, String type) {

		Map<String, Object> all = new HashMap<String, Object>();
		try {
			Boolean result = userService.switchMode(equSerialNo, type);
			if (result) {
				all.put("msg", Constants.USER_UPDATE_MSG[0]);
				all.put("success", true);

			} else {
				all.put("msg", Constants.USER_UPDATE_MSG[1]);
				all.put("success", false);

			}
		} catch (Exception e) {
			all.put("msg", Constants.USER_UPDATE_MSG[1]);
			all.put("success", false);
		}
		return new ModelAndView("/userinfo/show", all);
	}

	/**
	 * 更新生产任务
	 * 
	 * @param equSerialNo
	 *            机床序列号
	 * @return
	 */
	@RequestMapping(value = "/jobFinished", method = RequestMethod.POST)
	public ModelAndView jobFinished(String equSerialNo, String jobDispatchNo) {
		Map<String, Object> all = new HashMap<String, Object>();
		try {
			Boolean result = userService
					.jobFinished(equSerialNo, jobDispatchNo);
			if (result) {
				all.put("success", true);

			} else {
				all.put("success", false);

			}
		} catch (Exception e) {
			all.put("success", false);
		}
		return new ModelAndView("/userinfo/show", all);
	}

	/**
	 * 获取菜单
	 * 
	 * @param userId
	 * @param appId
	 * @return
	 */
	@RequestMapping(value = "/getMenu", method = RequestMethod.GET)
	public ModelAndView getMenu(String userId, String appId) {
		Map<String, Object> all = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> rs = authorizeService.getMenu(userId,
					appId);
			all.put("msg", Constants.USER_QUERY_MSG[0]);
			all.put("success", true);
			all.put("content", rs);

		} catch (Exception e) {
			e.printStackTrace();
			all.put("msg", Constants.USER_QUERY_MSG[2]);
			all.put("success", false);
		}
		return new ModelAndView("/userinfo/show", all);
	}

	/**
	 * 用户登录
	 * 
	 * @param equSerialNo
	 * @return
	 */
	@RequestMapping(value = "/memberLogin", method = RequestMethod.POST)
	public ModelAndView memberLogin(String memID, String memPass,
			String equSerialNo) {
		Map<String, Object> all = new HashMap<String, Object>();
		try {
			Map<String, Object> result = userService.memberLogin(memID,
					memPass, equSerialNo);

			if (result.get("msg").equals(Constants.USER_LOGIN_MSG[2])) {
				all.put("msg", result.get("msg").toString());
				all.put("success", (Boolean) result.get("success"));
				result.remove("msg");
				result.remove("success");
				all.put("data", result);
			} else {
				all.put("msg", result.get("msg").toString());
				all.put("success", (Boolean) result.get("success"));
				result.remove("msg");
				result.remove("success");
				all.put("data", result);
			}
		} catch (Exception e) {
			all.put("success", false);
			all.put("msg", Constants.USER_LOGIN_MSG[3]);
		}
		return new ModelAndView("/userinfo/show", all);
	}

	/**
	 * 报工上线检测
	 * 
	 * @param equSerialNo
	 *            机床序列号
	 * @param jobDispatchNo
	 *            工单号
	 * @return
	 */
	@RequestMapping(value = "/JobOnlineCheck", method = RequestMethod.POST)
	public ModelAndView JobOnlineCheck(String equSerialNo, String jobDispatchNo) {
		Map<String, Object> all = new HashMap<String, Object>();
		try {
			int flag = userService.JobOnlineCheck(equSerialNo, jobDispatchNo);
			all.put("jobOnlineCheckOk", flag);
			if (flag == 2) {
				all.put("msg", "该工单加工次数已超计划，请运行结束后通知调度员确认，或立刻取消加工，更换工单");
				all.put("success", "true");
			} else {
				all.put("msg", "可以加工");
				all.put("success", "true");
			}
		} catch (Exception e) {
			e.printStackTrace();
			all.put("success", false);
			all.put("msg", Constants.IS_OK_NOTOK[0]);
		}
		return new ModelAndView("/userinfo/show", all);
	}

	/**
	 * 程序查询
	 * 
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/checkProgramlist", method = RequestMethod.POST)
	public ModelAndView checkProgramlist(HttpServletRequest request,
			String jobDispatchNo, String partId, String equSerialNo) {
		Map<String, Object> all = new HashMap<String, Object>();
		try {

			List<Map<String, Object>> list = resourceService
					.getProgramByJobDispatchNo(jobDispatchNo, partId);
			for (Map<String, Object> map : list) {
				String programId = map.get("programId").toString();
				List<Map<String, Object>> list2 = resourceService
						.getProgramLoadDownInfo(programId, equSerialNo);
				if (list2.size() > 0) {
					map.put("progUpdateMark", "N");

				} else {
					map.put("progUpdateMark", "Y");
				}
				map.put("remark", "");
				map.put("Icon", "");

				// 路径处理
				String programPath = map.get("programPath").toString();
				// String temppath =
				// this.getClass().getClassLoader().getResource("").getPath();
				// temppath =request.getServletContext().getRealPath("/");
				Map<String, String> mmm = StringUtils.getUrl(request
						.getServletContext());
				String realHttpUrl = mmm.get("mocsURL").toString();
				realHttpUrl = realHttpUrl + "/mocs/program/" + programPath;
				map.put("programPath", realHttpUrl);

			}
			all.put("data", list);
			all.put("msg", "查询成功");
			all.put("success", "true");
		} catch (Exception exp) {
			all.put("msg", "查询失败");
			all.put("success", "false");
		}
		return new ModelAndView("/userinfo/show", all);
	}

	/**
	 * 程序下载
	 * 
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/downloadProgram", method = RequestMethod.POST)
	public ModelAndView downloadProgram(String programId, String equSerialNo,
			String userID) {
		Map<String, Object> all = new HashMap<String, Object>();
		try {
			String result = resourceService.saveProgramLoadDownInfo(programId,
					equSerialNo, userID);
			if (result.equals("更新成功")) {
				all.put("msg", result);
				all.put("success", "true");
			} else {
				all.put("msg", "更新失败");
				all.put("success", "false");
			}

		} catch (Exception exp) {
			all.put("msg", "更新失败");
			all.put("success", "false");
		}
		return new ModelAndView("/userinfo/show", all);
	}

	/**
	 * 查询零件
	 * 
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/queryParts", method = RequestMethod.POST)
	public ModelAndView queryParts(String equSerialNo, String partName) {
		Map<String, Object> all = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = resourceService.getParts(partName);
			all.put("data", list);
			all.put("msg", "查询成功");
			all.put("success", "true");

		} catch (Exception exp) {
			all.put("msg", "查询失败");
			all.put("success", "false");
		}
		return new ModelAndView("/userinfo/show", all);
	}

	/**
	 * 程序上传
	 * 
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/uploadProgram", method = RequestMethod.POST)
	public ModelAndView uploadProgram(HttpServletRequest request,
			String progName, String progVersion, String progContent,
			String userID, String progDesc, String md5, String equSerialNo) {
		Map<String, Object> all = new HashMap<String, Object>();
		try {

			if (null == progContent || progContent.equals("")) {
				all.put("msg", "内容为空");
				all.put("success", "false");
			} else {
				// 获取nodeId
				List<Map<String, Object>> equList = resourceService
						.getEquNodeId(equSerialNo);
				if (equList.size() < 1) {
					all.put("msg", "机床不存在");
					all.put("success", "false");
				} else {
					Md5 getMD5 = new Md5();
					String Md5String = getMD5.GetMD5Code(progContent.getBytes()).toUpperCase();

					if (Md5String.equals(md5)) {

						String extension = progName.substring(progName
								.lastIndexOf(".") + 1);
						SimpleDateFormat df = new SimpleDateFormat(
								"yyyyMMddHHmmssss");
						String fileName = df.format(new Date()) + "." + extension;

						String temppath = this.getClass().getClassLoader()
								.getResource("").getPath();
						temppath = request.getServletContext().getRealPath("/");
						String realPath = temppath + "/static/program/";
						File file1 = new File(realPath, fileName);
						if (!file1.exists()) {
							file1.createNewFile();
						}

						FileWriter fw = new FileWriter(realPath + "/" + fileName,
								true);
						BufferedWriter bw = new BufferedWriter(fw);
						bw.append(progContent);

						bw.close();
						fw.close();

						TProgramInfo addTpro = new TProgramInfo();
						addTpro.setProgName(progName);// 程序名称
						addTpro.setProgNo(progName);// 程序编号
						addTpro.setCreateTime(new Date());// 时间
						addTpro.setProgramPath(fileName);// 物理名
						// addTpro.setMd5(Md5String.toUpperCase());
						addTpro.setMd5(md5.toUpperCase());
						addTpro.setCreator(userID);
						addTpro.setDescribe2(progDesc);
						addTpro.setStatus("N");

						// 设置版本
						String nodeId = "";
						if (null != equList.get(0).get("nodeId")) {
							nodeId = equList.get(0).get("nodeId").toString();
						}
						List<Map<String, Object>> list = resourceService
								.getProgramVersion(progName, nodeId);
						if (list.size() > 0) {
							if (null != list.get(0).get("versionNo")) {
								String str = list.get(0).get("versionNo")
										.toString();
								addTpro.setVersionNo("v"
										+ (Integer.valueOf(str.substring(1)) + 1));
							} else {
								addTpro.setVersionNo("v1");
							}
						} else {
							addTpro.setVersionNo("v1");
						}
						addTpro.setNodeid(nodeId);
						String success = resourceService.saveTprogramInfo(addTpro);

						if (success.equals("添加成功")) {
							all.put("progVersion", addTpro.getVersionNo());
							all.put("msg", "上传成功");
							all.put("success", "true");
						} else {
							all.put("msg", success);
							all.put("success", "false");
						}
					}
					else {
						all.put("msg", "文件丢失");
						all.put("success", "false");
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			all.put("success", false);
			all.put("msg", "上传失败");
		}
		return new ModelAndView("/userinfo/show", all);
	}

	/**
	 * 程序上传
	 * 
	 * @param
	 * @param
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadProgramBase64", method = RequestMethod.POST)
	public ModelAndView uploadProgramBase64(HttpServletRequest request,
			String progName, String progVersion, String progContent,
			String userID, String progDesc, String md5, String equSerialNo)
			throws IOException {

		Map<String, Object> all = new HashMap<String, Object>();
		try {
			if (null == progContent || progContent.equals("")) {
				all.put("msg", "内容为空");
				all.put("success", "false");
			} else {
				// 获取nodeId
				List<Map<String, Object>> equList = resourceService.getEquNodeId(equSerialNo);
				if (equList.size() < 1) {
					all.put("msg", "机床不存在");
					all.put("success", "false");
				} else {
					byte[] buffer = new BASE64Decoder().decodeBuffer(progContent); // 对字符串进行解码
					Md5 getMD5 = new Md5();
					String _md5Server = getMD5.GetMD5Code(buffer).toUpperCase();

					if (_md5Server.equals(md5)) {
						String extension = progName.substring(progName.lastIndexOf(".") + 1);
						SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssss");
						String fileName = df.format(new Date()) + "." + extension;

						String temppath = this.getClass().getClassLoader().getResource("").getPath();
						temppath = request.getServletContext().getRealPath("/");
						String realPath = temppath + "/static/program/";

						File file = new File(realPath, fileName);
						try {
							BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
									new FileOutputStream(file));
							bufferedOutputStream.write(buffer);
							bufferedOutputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}

						TProgramInfo addTpro = new TProgramInfo();
						addTpro.setProgName(progName);// 程序名称
						addTpro.setProgNo(progName);// 程序编号
						addTpro.setCreateTime(new Date());// 时间
						addTpro.setProgramPath(fileName);// 物理名
						addTpro.setMd5(_md5Server.toUpperCase());
						addTpro.setCreator(userID);
						addTpro.setDescribe2(progDesc);
						addTpro.setStatus("N");

						// 设置版本
						String nodeId = "";
						if (null != equList.get(0).get("nodeId")) {
							nodeId = equList.get(0).get("nodeId").toString();
						}
						List<Map<String, Object>> list = resourceService
								.getProgramVersion(progName, nodeId);
						if (list.size() > 0) {
							if (null != list.get(0).get("versionNo")) {
								String str = list.get(0).get("versionNo")
										.toString();
								addTpro.setVersionNo("v"
										+ (Integer.valueOf(str.substring(1)) + 1));
							} else {
								addTpro.setVersionNo("v1");
							}
						} else {
							addTpro.setVersionNo("v1");
						}
						addTpro.setNodeid(nodeId);
						String success = resourceService
								.saveTprogramInfo(addTpro);

						if (success.equals("添加成功")) {
							all.put("progVersion", addTpro.getVersionNo());
							all.put("msg", "上传成功");
							all.put("success", "true");
						} else {
							all.put("msg", success);
							all.put("success", "false");
						}

					} else {
						all.put("msg", "文件丢失");
						all.put("success", "false");
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			all.put("success", false);
			all.put("msg", "上传失败");
		}
		return new ModelAndView("/userinfo/show", all);

	}
}
