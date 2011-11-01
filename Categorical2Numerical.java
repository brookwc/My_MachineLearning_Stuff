import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class Categorical2Numerical {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
				
		try {
			PrintWriter pw = new PrintWriter(System.out, true);
			if (args.length != 3) {
				System.err.println("wrong arguments - exit");
				System.exit(-1);
			}
			String feature = args[0];
			String newFeature = args[1];
			String mapString = args[2];
			
			String[] a = mapString.split(":");
			
			Map<String, Integer> valueMap = new HashMap<String, Integer>();
			for (String e : a) {
				String[] pair = e.split("#");
				if (pair.length != 2) {
					System.err.println("wrong argument " + mapString);
					System.exit(-1);
				}

				valueMap.put(pair[0], Integer.parseInt(pair[1]));
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String line = null;
			List<String> features = new ArrayList<String>();
			line = br.readLine();
			pw.println(line + "\t" + newFeature);
			String[] strs = line.split("\t", 1000000);
			for (String str : strs) {
				features.add(str);
			}
			
			int pos = features.indexOf(feature);			
			if (pos < 0) {
				System.err.println("feature does not exist in data file - aborting...");
				System.exit(-1);
			}

			System.err.println("Start processing input file");
			int cnt = 0;
			
			while ((line = br.readLine()) != null) {
				String[] vals = line.split("\t", 1000000);
				String val = vals[pos];

				if (val != null && !val.isEmpty()) {
					if (valueMap.containsKey(val) == false) {
						System.err.println("feature value " + val + " does not exist in data file - aborting...");
						System.exit(-1);
					}
					pw.println(line + "\t" + valueMap.get(val));
				} else {
					pw.println(line + "\t");
				}
				
				if (++cnt % 1000 == 0) {
					System.err.println(cnt + " lines processed.");
				}
			}
			
			br.close();
			System.err.println("done processing input file.\n values on " + feature + " are:");
			for (String key : valueMap.keySet()) {
				System.err.println(key + " -> " + valueMap.get(key));
			}

			pw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
