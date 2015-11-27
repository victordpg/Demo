package demo.everything.zeromq;

import org.zeromq.ZMQ;

public class MainThread {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 System.out.println(String.format("Version string: %s, Version int: %d",
	                ZMQ.getVersionString(),
	                ZMQ.getFullVersion()));
	}

}
