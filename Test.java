import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("hello, world");

		String str = "a	b	c	d			";
		
		String[] strs = str.split("\t", 2);
		
		System.out.println("size = " +  strs.length);
		for (String s : strs) {
			System.out.println("s=" + s +"*");
		}
		
		
		String str1 = "abced\t";
		System.out.println("str1=" + str1);
		System.out.println("str2=" + str1.substring(0, str1.length()-1));
		
		try {
			//Runtime.getRuntime().exec("/bin/sh -c /bin/ls tempScore");
			String cmd = "./evalModel/EvaluateClassifier.exe -f:tempScore -r:roc -s:summary";
			Process p = Runtime.getRuntime().exec(cmd);
			
			p.waitFor();

			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			
			br.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
