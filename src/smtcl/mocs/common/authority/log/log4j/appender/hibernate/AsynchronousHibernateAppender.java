package smtcl.mocs.common.authority.log.log4j.appender.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import smtcl.mocs.common.authority.log.ILogFinal;
import smtcl.mocs.dao.authority.ICommonDao;
import smtcl.mocs.pojos.authority.Log;

/**
 * 
 * @author gaokun
 * @create Sep 4, 2012 2:53:36 PM
 */
public class AsynchronousHibernateAppender extends AppenderSkeleton implements Appender, Runnable {
	/**
	 * The logging event class name.
	 */
	private String loggingEventClass;
	/**
	 * The logging event class.
	 */
	private Class<Log> loggingEvent;
	/**
	 * The Hibernate session factory.
	 */
	private SessionFactory sessionFactory;
	/**
	 * The Spring transaction template.
	 */
	private TransactionTemplate transactionTemplate;
	/**
	 * The loggers which will be attached when calling {@linkplain
	 * #registerWithLoggers.}
	 */
	private String[] loggers = new String[]{ILogFinal.CATEGORY_NAME};
	/**
	 * The event queue.
	 */
	private final BlockingQueue<LoggingEvent> queue = new LinkedBlockingQueue<LoggingEvent>();
	/**
	 * The queue processing thread.
	 */
	private final AtomicReference<Thread> queueThreadReference = new AtomicReference<Thread>();
	
	private ICommonDao commonDao;

	/**
	 * @return the commonDao
	 */
	public ICommonDao getCommonDao() {
		return commonDao;
	}

	/**
	 * @param commonDao the commonDao to set
	 */
	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	/**
	 * Gets the Spring transaction template.
	 * @return the Spring transaction emplate
	 */
	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	/**
	 * Sets the Spring transaction template.
	 * @param template the transaction template to set
	 */
	public void setTransactionTemplate(final TransactionTemplate template) {
		this.transactionTemplate = template;
	}
	/**
	 * Registers with loggers and starts the queue processing thread, if it is not currently running.
	 */
	public void initialize() {
		registerWithLoggers();
		maybeStartQueueThread();
	}
	/**
	 * Unregisters with loggers and stops the queue processing thread, if it is currently running.
	 */
	public void shutdown() {
		unregisterWithLoggers();
		Thread queueThread = queueThreadReference.get();
		if (queueThread!=null) {
			/*
			 * Politely ask the queue thread to stop processing and exit.
			 */
			queueThread.interrupt();
			try {
				/*
				 * Wait for the queue thread to exit.
				 */
				queueThread.join();
			} catch(InterruptedException ex) {
				/*
				 * If some other application code interrupted the current
				 * thread, an InterruptedException may have been thrown during
				 * our call to queueThread.join().  The current thread's
				 * interrupted flag is usually cleared when this exception is
				 * thrown.
				 *
				 * Here, we ignore the exception but reset the current thread's
				 * interrupted flag and finish our shutdown routine.  The
				 * interruption might still be handled by some external
				 * application code after this method's execution completes, but
				 * it won't (and shouldn't) be handled here.
				 */
				Thread.currentThread().interrupt();
			} finally {
				queueThreadReference.compareAndSet(queueThread, null);
			}
		}
	}
	/**
	 * Starts the queue thread, if it was not currently running.
	 */
	private void maybeStartQueueThread() {
		Thread queueThread = queueThreadReference.get();
		if (queueThread == null || !queueThread.isAlive()) {
			Thread newThread = new Thread(this);
			newThread.setName(getClass().getName()+"_queueThread");
			if (queueThreadReference.compareAndSet(queueThread, newThread)) {
				newThread.start();
			}
		}
	}
	/**
	 * Loops indefinitely, flushing the queue, until the thread is interrupted.
	 */
	public void run() {
		while (true) {
			try {
				flushQueue();
			} catch (InterruptedException ex) {
				return;
			}
		}
	}
	/**
	 * Writes the contents of the queue to Hibernate.  This method will block
	 * until there is at least one event in the queue or the thread is
	 * interrupted.  If the queue contains more than one event, then all
	 * events in the queue will be added within a single transaction.
	 * @throws InterruptedException if the thread is interrupted
	 */
	private void flushQueue() throws InterruptedException {
		int queueSize = getQueueSize();
		if (queueSize > 1) {
			List<LoggingEvent> events = new ArrayList<LoggingEvent>(queueSize);
			for (int i = 0; i < queueSize; i++) {
				events.add(queue.poll());
			}
			addCollectionOfEvents(events);
		} else {
			/*
			 * This will block the thread until there is a event in the
			 * queue.  If the thread was interrupted (such as by a call to
			 * shutdown()), this call will throw an InterruptedException.
			 */
			addSingleMessage(queue.take());
		}
	}
	/**
	 * Writes a logging event to a Hibernate session.
	 * @param session an open Hibernate session
	 * @param event the event to write
	 */
	private void doAddMessage(final Session session, final LoggingEvent event) {
		Object obj = event.getMessage();
		if( obj != null && obj instanceof Log){
			Log l = (Log)obj;
			l.setOpTime(new Date(event.timeStamp));
//			session.save(l);
//			session.flush();
			commonDao.save(l);
		}
//		CapLoggingEvent loggingEventWrapper = null;
//		try {
//			loggingEventWrapper = getLoggingEvent().newInstance();
//		} catch (IllegalAccessException iae) {
//			logError("Unable to instantiate class " + getLoggingEvent().getName(), iae, ErrorCode.GENERIC_FAILURE);
//			return;
//		} catch (InstantiationException ie) {
//			logError("Unable to instantiate class " + getLoggingEvent().getName(), ie, ErrorCode.GENERIC_FAILURE);
//			return;
//		}
//		loggingEventWrapper.setMessage(event.getRenderedMessage());
//		LocationInfo information = event.getLocationInformation();
//		loggingEventWrapper.setClassName(information.getClassName());
//		loggingEventWrapper.setFileName(information.getFileName());
//		loggingEventWrapper.setLineNumber(information.getLineNumber());
		
////		loggingEventWrapper.setLoggerName(event.getLoggerName());
//		loggingEventWrapper.setMethodName(information.getMethodName());
		
		
		
//		loggingEventWrapper.setStartDate(startDate);
//		loggingEventWrapper.setThreadName(event.getThreadName());
//		if (event.getThrowableStrRep() != null) {
//			for (int j = 0; j < event.getThrowableStrRep().length; j++) {
//				loggingEventWrapper.addThrowableMessage(j, event.getThrowableStrRep()[j]);
//			}
//		}
//		Level bufferLevel = event.getLevel();
//		if (bufferLevel == null) {
//			loggingEventWrapper.setLevel("UNKNOWN");
//		} else {
//			loggingEventWrapper.setLevel(bufferLevel.toString());
//		}
	}
	/**
	 * Adds a collection of logging events in a single transaction.
	 * @param events the events to add.
	 */
	private void addCollectionOfEvents(final Collection<LoggingEvent> events) {
//		getTransactionTemplate().setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		getTransactionTemplate().execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus ts) {
//				Session session = getSessionFactory().openSession();
				try {
					for (LoggingEvent event : events) {
						doAddMessage(null, event);
					}
				} catch (HibernateException he) {
					logError("HibernateException", he, ErrorCode.GENERIC_FAILURE);
				} finally {
//					if (session != null && session.isOpen()) {
//						session.close();
//					}
				}
			}
		});
	}
	/**
	 * Adds a single event.
	 * @param event the event to add.
	 */
	private void addSingleMessage(final LoggingEvent event) {
//		getTransactionTemplate().setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		getTransactionTemplate().execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus ts) {
//				Session session = getSessionFactory().openSession();
				try {
					doAddMessage(null, event);
				} catch (HibernateException he) {
					logError("HibernateException", he, ErrorCode.GENERIC_FAILURE);
				} finally {
//					if (session != null && session.isOpen()) {
//						session.close();
//					}
				}
			}
		});
	}
	/**
	 * Appends a logging event to the queue and lazily initializes the queue
	 * thread.
	 * @see org.apache.log4j.AppenderSkeleton#append(
	 * org.apache.log4j.spi.LoggingEvent)
	 */
	protected void append(final LoggingEvent loggingEvent) {
		queue.add(loggingEvent);
		maybeStartQueueThread();
	}

	/**
	 * Gets the Hibernate session factory.
	 * @return the Hibernate session factory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	/**
	 * Sets the Hibernate session factory.
	 * @param factory the Hibernate session factory
	 */
	public void setSessionFactory(SessionFactory factory) {
		this.sessionFactory = factory;
	}


	/**
	 * Returns {@code false}.
	 * @see org.apache.log4j.Appender#requiresLayout()
	 */
	public boolean requiresLayout() {
		return false;
	}

	/**
	 * Log errors to log4j Error Handler in case the hibernate cannot write to datanase
	 * @param errorMessage Error Message occurred during writing to database
	 * @param accessException Exception raised
	 * @param failure failure code
	 */
	private void logError(String errorMessage, Exception accessException, int failure) {
		errorHandler.error(errorMessage, accessException, failure);
	}

	/**
	 * Returns the name of the class implementing the
	 * {@link HibernateAppenderLoggingEvent} interface.
	 *
	 * @return Fully qualified class name
	 */
	public String getLoggingEventClass() {
		return loggingEventClass;
	}

	/**
	 * Sets the name of class implementing the
	 * {@link HibernateAppenderLoggingEvent} interface.
	 *
	 * @param string qualified class name
	 */
	public void setLoggingEventClass(String string) {
		loggingEventClass = string;
		try {
			setLoggingEvent((Class<Log>) Class.forName(loggingEventClass));
		} catch (ClassNotFoundException cnfe) {
			logError("Invalid LoggingEvent class " + string, cnfe, ErrorCode.GENERIC_FAILURE);
		}
	}
	/**
	 * {@inheritDoc}
	 * <p>Stops the queue thread if it was running.</p>
	 */
	public void close() {
		shutdown();
	}

	/**
	 * Gets the logging event class.
	 * @return the logging event class
	 */
	public Class<Log> getLoggingEvent() {
		return loggingEvent;
	}

	/**
	 * Sets the logging event class
	 * @param loggingEventClass the logging event class to set
	 */
	public void setLoggingEvent(Class<Log> loggingEventClass) {
		this.loggingEvent = loggingEventClass;
	}
	/**
	 * Sets the loggers to which the appender should be registered.
	 * @param loggers the loggers to which the appender should be registered.
	 */
//	public void setLoggers(String[] loggers) {
//		this.loggers = loggers;
//	}
	/**
	 * Gets the loggers to which the appender should be registered.
	 * @return the loggers to which the appender should be registered.
	 */
	public String[] getLoggers() {
		return loggers;
	}
	/**
	 * Registers the appender to its loggers.
	 */
	public synchronized void registerWithLoggers() {
		String[] desiredLoggers = getLoggers();
		if (desiredLoggers != null) {
			for (String loggerName : desiredLoggers) {
				Logger logger = Logger.getLogger(loggerName);
				if (logger != null && !logger.isAttached(this)) {
					logger.setLevel(Level.ALL);
					logger.addAppender(this);
				}
			}
		}
	}
	/**
	 * Unregisters the appender from its loggers.
	 */
	public synchronized void unregisterWithLoggers() {
		String[] desiredLoggers = getLoggers();
		if (desiredLoggers != null) {
			for (String loggerName : desiredLoggers) {
				Logger logger = Logger.getLogger(loggerName);
				if (logger != null && logger.isAttached(this)) {
					logger.removeAppender(this);
				}
			}
		}
	}
	/**
	 * Gets the current queue size.
	 * @return the queue size
	 */
	public int getQueueSize() {
		return queue.size();
	}
}
