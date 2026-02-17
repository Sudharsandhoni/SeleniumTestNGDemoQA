package core.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.constants.TestGlobals;

public class LogUtils {
	// That creates ONE shared logger for the entire JVM. 
	//private static Logger log = LogManager.getLogger(LogUtils.class);
	
	private LogUtils() {}

    private static final Logger FALLBACK_LOG =
            LogManager.getLogger(LogUtils.class);
    
    private static Logger logger() {
        Logger log = TestGlobals.getLogger();
        return (log != null) ? log : FALLBACK_LOG;
    }

	
	/**
	 * Appends the message to info
	 * 
	 * @param msg
	 *            - String
	 */
	public static void logInfo(String msg) {
		try {
			logger().info(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Appends the message to error
	 * 
	 * @param msg
	 *            - String
	 */
	public static void logError(String msg) {
		try {
			logger().error(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Appends the exception to stack trace
	 * 
	 * @param exception
	 *            - Exception
	 */
	public static void logStackTrace(Exception exception) {
		StringWriter stack = new StringWriter();
		exception.printStackTrace(new PrintWriter(stack));
		logger().debug("Caught exception; decorating with appropriate status template : " + stack.toString());
	}
	
	public static void logErrorAndTrace(String customErrorMessage, Exception e) {
		logError(customErrorMessage);
		logStackTrace(e);		
	}

}