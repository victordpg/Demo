package demo.everything.exception;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class _Test {
	final static Logger logger = Logger.getLogger(_Test.class);
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("/Users/Victor/git/Demo/src/main/java/log4j.property");
		// TODO Auto-generated method stub
		logger.info("1-");
		try {
			throw new SQLException("This is SQLException which is not a RuntimeException!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		boolean flag = true;
		if (flag) {
			logger.info("2-");
			try {
				throw new RuntimeException("Runtime 1.......");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("4-");
				e.printStackTrace();
			}
			throw new RuntimeException("Runtime 2.......");
		}else {
			logger.info("3-");
		}
	}

}
