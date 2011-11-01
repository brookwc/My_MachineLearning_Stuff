import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class Preprocess {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("usage: java Preprocess features_small_set features_big_set");
		System.out.println("args[1] = " + args[0]);
		System.out.println("args[2] = " + args[1]);
		
		List<String> features_small = new ArrayList<String>(); 
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]));
			
			String line = null;
			
			while ((line = bufferedReader.readLine()) != null) {
				features_small.add(line.trim());
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		/*
		for (String feature: features) {
			System.out.println("feature: " + feature);
		}
		*/
		
		List<String> features_big = new ArrayList<String>(); 
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(args[1]));
			
			String line = null;
			
			while ((line = bufferedReader.readLine()) != null) {
				features_big.add(line.trim());
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		int lineNo = 0;
		int match = 0;
		int mismatch = 0;
		for (String feature : features_small) {
			if (!features_big.contains(feature)) {
				System.out.println("lineNo = " + lineNo + " feature = " + feature + " is not in");
				mismatch++;
				//System.exit(0);
			}
			else {
				match++;
			}
			lineNo++;
		}
		
		System.out.println("match = " + match + "; mismatch = " + mismatch);
	}

}
