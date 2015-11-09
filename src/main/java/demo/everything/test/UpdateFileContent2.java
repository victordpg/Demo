package demo.everything.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 提取AgentLog中MessageHandler行信息
 * @author DIAOPG
 * @date 2015年11月06日
 */
public class UpdateFileContent2 {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws FileNotFoundException {
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader("D:/Temp/agent.log"));
		String line;
		
		try {
			FileWriter fw = new FileWriter("D:/Temp/agent2.log");
			BufferedWriter  bWriter = new BufferedWriter(fw);
			while (((line = bufferedReader.readLine()) != null)) {
				//boolean contains = line.contains("MSG from InBoundHandler");
				boolean contains = line.contains("MessageHandler");
				if (contains) {
					bWriter.write(line);
					bWriter.newLine();
					System.out.println(line);
				}
			}
			bWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
