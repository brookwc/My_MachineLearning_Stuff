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


public class ConvertFeatureToIni {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("usage: java ConvertFeatureToIni input output");
		System.out.println("args[0] = " + args[0]);
		System.out.println("args[1] = " + args[1]);
		
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(args[0]));
			PrintWriter pw = new PrintWriter(new File(args[1]));
			
			String line = null;
			int i = 1;
			while ((line = br1.readLine()) != null) {
				if (line.trim().contains("m:")) {
					System.out.println(line + " skipped.");
					continue;
				}
				pw.println("[Input:" + i + "]");
				pw.println("Name=" + line);
				pw.println("Transform=Linear");
				pw.println("Slope=1");
				pw.println("Intercept=0");
				pw.println();
				
				if (i++ % 1000 == 0) {
					System.out.println(i + " lines processed.");
				}
			}
			
			pw.println(";The following two sections are only for backward compatibility");
			pw.println("[NeuralNet]");
			pw.println("Inputs=" + (i-1));
			pw.println();
			
			pw.println("[TreeEnsemble]");
			pw.println("Inputs=" + (i-1));
			pw.println();

			br1.close();
			pw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
