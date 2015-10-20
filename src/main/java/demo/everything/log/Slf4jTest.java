package demo.everything.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Slf4jTest {
	final Logger loggerSlf4j = LoggerFactory.getLogger(Slf4jTest.class);
	
	public void getLog(){
		loggerSlf4j.debug("This log from Slf4jTest Slf4jTest...");
		LogBean logBean = new LogBean(1,"SLF4J_Bean");
		loggerSlf4j.info("This log for the bean {}", logBean);
	}

}
