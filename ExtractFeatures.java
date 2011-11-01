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


public class ExtractFeatures {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("usage: java ExtractFeatures data_file feature_file output");
		System.out.println("args[0] = " + args[0]);
		System.out.println("args[1] = " + args[1]);
		System.out.println("args[2] = " + args[2]);
		
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(args[1]));
			List<String> features = new ArrayList<String>();
			String line = null;
			while ((line = br1.readLine()) != null) {
				features.add(line.trim());
			}
			br1.close();
			
			PrintWriter pw = new PrintWriter(new File(args[2]));

			BufferedReader br2 = new BufferedReader(new FileReader(args[0]));
			List<String> features2 = new ArrayList<String>();
			line = br2.readLine();
			String[] strs = line.split("\t", 1000000);
			for (String str : strs) {
				features2.add(str);
			}
			if (! features2.containsAll(features)) {
				System.err.println("feature file contains non-existing feature in data file - cannot extract - aborting...");
				features.removeAll(features2);
				for (String ss : features) {
					System.out.println(ss);
				}
				System.exit(-1);
			}
			
			Map<String, Integer> featureMap = new HashMap<String, Integer>();
			int i = 0;
			for (String f : features2) {
				featureMap.put(f, i++);
			}

			StringBuilder sb = new StringBuilder();
			for (String f : features) {
				sb.append(f + "\t");
			}
			pw.println(sb.toString().substring(0, sb.toString().length()-1));
			
			int cnt = 0;
			while ((line = br2.readLine()) != null) {
				String[] vals = line.split("\t", 1000000);
				sb = new StringBuilder();
				for (String f : features) {
					int idx = featureMap.get(f);
					sb.append(vals[idx] + "\t");
				}

				pw.println(sb.toString().substring(0, sb.toString().length()-1));
				if (++cnt % 1000 == 0) {
					System.out.println(cnt + " lines processed.");
				}
			}
			br2.close();

			pw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
