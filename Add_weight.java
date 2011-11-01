import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class Add_weight {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("usage: java Add_docid_queryid input output");
		System.out.println("args[1] = " + args[0]);
		System.out.println("args[2] = " + args[1]);
		
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(args[0]));
			PrintWriter pw = new PrintWriter(new File(args[1]));
			
			String line = br1.readLine();
			pw.println("m:Weight\t" + line);
			
			int i = 0;
			while ((line = br1.readLine()) != null) {
				pw.println("1\t" + line);
				
				if (i++ % 1000 == 0) {
					System.out.println(i + " lines processed.");
				}
			}

			br1.close();
			pw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
