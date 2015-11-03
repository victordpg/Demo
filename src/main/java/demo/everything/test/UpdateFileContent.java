package demo.everything.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 将PY脚本中指标名改为大写
 * @author DIAOPG
 * @date 2015年8月12日
 */
public class UpdateFileContent {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws FileNotFoundException {
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader("D:/Temp/test.txt"));
		String line;
		
		try {
			FileWriter fw = new FileWriter("D:/Temp/test2.txt");
			BufferedWriter  bWriter = new BufferedWriter(fw);
			while (((line = bufferedReader.readLine()) != null)) {
				int start = line.indexOf("NAME: \"");
				int end = line.indexOf("\", CODE");
				if (start!=-1) {
					String upperCaseString = line.substring(start+7, end).toUpperCase();
					//System.out.println(upperCaseString);
					//System.out.println(line.substring(0, start+6)+upperCaseString+line.substring(end+1));
					line = line.substring(0, start+7)+upperCaseString+line.substring(end);

				}
				bWriter.write(line);
				bWriter.newLine();
				System.out.println(line);
			}
			bWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
