import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class FindCommonFeatures {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("usage: java FindCommonFeatures feature_set1 feature_set2 output_common");
		System.out.println("args[1] = " + args[0]);
		System.out.println("args[2] = " + args[1]);
		
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(args[0]));
			BufferedReader br2 = new BufferedReader(new FileReader(args[1]));
			PrintWriter pw = new PrintWriter(new File(args[2]));

			Set<String> s1 = new HashSet<String>();
			Set<String> s2 = new HashSet<String>();
			String line = null;
			while ((line = br1.readLine()) != null) {
				s1.add(line.trim());
			}
			br1.close();
			
			while ((line = br2.readLine()) != null) {
				s2.add(line.trim());
			}
			br2.close();

			s1.retainAll(s2);
			for (String str: s1) {
				pw.println(str);
			}
			pw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
