import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class AddRandomFeatures {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.err.println("Usage: java AddRandomFeature random_feature_names(: separated) cadinality <input_tsv_file >output_tsv_file");
		
		try {
			if (args.length != 2) {
				System.err.println("wrong input arguments - exit");
				System.exit(-1);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter pw = new PrintWriter(System.out, true);
			
			String line = br.readLine(); // first line;
			String str = "";
			
			String[] names = args[0].split(":");
			String[] upperBoundStrs = args[1].split(":");
			if (names.length != upperBoundStrs.length) {
				System.err.println("wrong input arguments - exit");
				System.exit(-1);				
			}
			
			int n = names.length;
			int[] upperBounds = new int[upperBoundStrs.length];
			int j=0;
			for (String e: upperBoundStrs) {
				upperBounds[j++] = Integer.parseInt(e);
			}
			
			for (String e : names) {
				str += e + "\t";
			}
			
			pw.println(str + line);
			
			Random randomGenerator = new Random();
			
			int cnt = 0;
			while ((line = br.readLine()) != null) {
				str = "";
				for (int i=0; i<n; i++) {
				    int f = randomGenerator.nextInt(upperBounds[i]) + 1;
				    str += f + "\t";
				}
				pw.println(str + line);
				
				cnt++;
				if (cnt % 1000 == 0) {
					System.err.println(cnt + " lines processed.");
				}
			}
			
			pw.close();
			br.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
	}
}
