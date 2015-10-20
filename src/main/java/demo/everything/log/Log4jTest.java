package demo.everything.log;

import org.apache.log4j.Logger;

public class Log4jTest {
	
	final Logger logger = Logger.getLogger(Log4jTest.class);
	
	public void getLog(){
		logger.debug("This log from Log4jTest Log4jTest...");
	}

}
