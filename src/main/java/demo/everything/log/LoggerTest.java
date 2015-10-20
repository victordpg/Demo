package demo.everything.log;

import org.apache.log4j.PropertyConfigurator;

public class LoggerTest {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//只能打出Console Log，无法打出File Log
		//BasicConfigurator.configure();
		//可以打出File Log
		PropertyConfigurator.configure("/Users/Victor/git/Demo/src/main/java/log4j.property");
		
		Log4jTest log4jTest = new Log4jTest();
		Slf4jTest slf4jTest = new Slf4jTest();
		
		log4jTest.getLog();
		slf4jTest.getLog();
	}

}
