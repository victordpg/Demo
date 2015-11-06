package demo.everything.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 提取AgentLog信息
 * @author DIAOPG
 * @date 2015年11月06日
 */
public class UpdateFileContent3 {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws FileNotFoundException {
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader("D:/Temp/agent2.log"));
		String line;
		
		try {
			FileWriter fw = new FileWriter("D:/Temp/agent3.log");
			BufferedWriter  bWriter = new BufferedWriter(fw);
			while (((line = bufferedReader.readLine()) != null)) {
				int start = line.indexOf("104,");
				if (start!=63) {
					bWriter.write(line);
					bWriter.newLine();
				}
			}
			bWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
