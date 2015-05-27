/**
 * com.swg.authority.cache FlatManager.java
 */
package smtcl.mocs.beans.authority.cache;

import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import smtcl.mocs.utils.authority.HelperUtil;


/**
 * @author gaokun
 * @create Nov 23, 2012 5:27:17 PM
 */
public class FlatManager extends Thread{
	/**
	 * FlatManager 日志类;
	 */
	private final static Logger logger = Logger.getLogger(FlatManager.class);
	
	private static FlatManager instance = null;
	
	private Vector<String[]> vector = new Vector<String[]>();
	
	private final static ReentrantLock lock = new ReentrantLock();
	
	private boolean st = false;
	
	/**
	 * 是否需要刷新;
	 */
	private static boolean need = true;
	
	public static void  setPro(boolean n){
		need = n;
	}
	

	private long interval = 5000;
	
	private FlatManager(){
	}
	
	synchronized public static FlatManager getInstance(){
		if(instance == null)
			instance = new FlatManager();
		return instance;
	}
	
	public void start(){
		if( need ){
			if(!st){
				st = true;
				this.setDaemon(true);
				super.start();
				logger.info("com.swg.authority.flat.node.started!");
			}
		}
	}
	
	public void add(){
		if( need ){
			lock.lock();
			try{
				vector.add(new String[]{});
			}finally{
				lock.unlock();
			}
		}
	}
	
	public void add(String nodeId, String oldPId, String newPId){
		if( need ){
			lock.lock();
			try{
				vector.add(new String[]{nodeId, oldPId, newPId});
			}finally{
				lock.unlock();
			}
		}
	}
	
	public void close(){
		if( need ){
			st = false;
			instance = null;
			logger.info("com.swg.authority.flat.node.closed!");
		}
	}
	
	public void run(){
		while(st){
			String[] obj = null;
			lock.lock();
			try{
				if(vector.size() > 0){
					obj = vector.remove(0);
				}
			}finally{
				lock.unlock();
			}
			
			//刷新;
			if(obj != null){
				try{
					HelperUtil.flatOrg(obj);
					logger.info("com.swg.authority.flat.node.success!");
				}catch(Exception e){
					logger.error("com.swg.authority.flat.node.error!" + e.getMessage());
					e.printStackTrace();
				}
			}
			
			try{
				Thread.sleep(interval);
			}catch(Exception e){
			}
			logger.debug("com.swg.authority.flat.node.scan...!");
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FlatManager.getInstance().start();

		for (int i = 0; i < 100; i++) {
//			FlatManager.getInstance().add();
		}
	}

}