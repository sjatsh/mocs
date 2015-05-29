package smtcl.mocs.utils.device;

import java.sql.SQLException;

import org.hibernate.event.PostLoadEvent;
import org.hibernate.event.PostLoadEventListener;
import org.hibernate.event.PreLoadEvent;
import org.hibernate.event.PreLoadEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyPreLoadEventListener implements PreLoadEventListener,PostLoadEventListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(MyPreLoadEventListener.class);
	@Override
	public void onPreLoad(PreLoadEvent arg0) {
		// TODO Auto-generated method stub
		String url=null;
		try {
			url=arg0.getSession().connection().getMetaData().getURL();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("MyPreLoadEventListener----onPreLoad-----"+url);
	}
	@Override
	public void onPostLoad(PostLoadEvent arg0) {
		// TODO Auto-generated method stub
		String url=null;
		try {
			url=arg0.getSession().connection().getMetaData().getURL();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("MyPreLoadEventListener----onPostLoad-----"+url);
	}

}
