import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PP1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("usage: java pp1 input output");
		System.out.println("args[1] = " + args[0]);
		System.out.println("args[2] = " + args[1]);
		 
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]));
			PrintWriter pw = new PrintWriter(new File(args[1]));
			
			String line = bufferedReader.readLine();
			
			pw.println(line);
			
			Random randomGenerator = new Random();
			while ((line = bufferedReader.readLine()) != null) {
				String vals[] = line.split("\t", 2);
				
				pw.println(randomGenerator.nextInt(256) + "\t" + vals[1]);
			}
			
			bufferedReader.close();
			pw.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
