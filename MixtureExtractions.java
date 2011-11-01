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


public class MixtureExtractions {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("usage: java MixtureExtraction us_data, in_data, fr_data, common_feature_file, output");
		System.out.println("args[0] = " + args[0]);
		System.out.println("args[1] = " + args[1]);
		System.out.println("args[2] = " + args[2]);
		System.out.println("args[3] = " + args[3]);
		System.out.println("args[4] = " + args[4]);
		
		try {
			BufferedReader featureReader = new BufferedReader(new FileReader(args[3]));
			String line = null;
			List<String> features = new ArrayList<String>();
			features.add("m:Query");
			features.add("m:AdId");
			features.add("m:ListingId");
			features.add("m:RGUID");
			features.add("m:Rating");
			features.add("m:MatchType");
			features.add("m:Weight");
			features.add("m:Url");
			
			while ((line = featureReader.readLine()) != null) {
				features.add(line.trim());
			}
			featureReader.close();
			
			PrintWriter pw = new PrintWriter(new File(args[4]));
			StringBuilder sb = new StringBuilder();
			for (int i=0; i<features.size()-1; i++) {
				sb.append(features.get(i) + "\t");
			}
			sb.append(features.get(features.size()-1));
			pw.println(sb.toString());  // header
			
			// add us-data
			System.out.println("started adding us-data");
			BufferedReader br1 = new BufferedReader(new FileReader(args[0]));
			
			line = br1.readLine();
			String features_us[] = line.split("\t");

			Map<String, Integer> featureMap = new HashMap<String, Integer>();
			int pos = 0;
			for (String f : features_us) {
				featureMap.put(f, pos++);
			}
			
			int cnt = 0;
			while ((line = br1.readLine()) != null) {
				String vals[] = line.split("\t", 1000000);
				
				sb = new StringBuilder();
				for (String feature : features) {
					if (featureMap.containsKey(feature)) {
						int pp = featureMap.get(feature);
						sb.append(vals[pp] + "\t");
					}
				}				
				pw.println(sb.toString().substring(0, sb.toString().length()-1));
				
				if (++cnt % 1000 == 0) {
					System.out.println(cnt + " lines processed.");
				}
			}
			br1.close();
			System.out.println("ended adding us-data");

			// 
			System.out.println("started adding in-data");
			BufferedReader br2 = new BufferedReader(new FileReader(args[1]));
			line = br2.readLine();
			String features_in[] = line.trim().split("\t");
			
			featureMap.clear();
			pos = 0;
			for (String f : features_in) {
				featureMap.put(f, pos++);
			}
			
			cnt = 0;
			while ((line = br2.readLine()) != null) {
				String vals[] = line.split("\t", 1000000);
				
				sb = new StringBuilder();
				for (String feature : features) {
					
					if (featureMap.containsKey(feature)) {
						int pp = featureMap.get(feature);
						if (feature.equals("m:MatchType")) {
							String matchType = vals[pp];
							switch (matchType) {
								case "B" :
									sb.append("BROADMATCH\t");
									break;
								case "E" :
									sb.append("EXACTMATCH\t");
									break;
								case "P" :
									sb.append("PHRASEMATCH\t");
									break;
								case "S" :
									sb.append("SMARTMATCH\t");
									break;
								case "" :
									sb.append("\t");
									break;
							}
						} else {
							sb.append(vals[pp] + "\t");
						}
					} else {
						if (feature.equals("m:Weight")) {
							sb.append("1\t");
						} else if (feature.equals("m:Url")) {
							sb.append("\t");
						} else if (feature.endsWith("m:Query")) { // ? need this for some java weird io stuff;
							sb.append(vals[0] + "\t");
						}
					}
				}
				
				pw.println(sb.toString().substring(0, sb.toString().length()-1));
				
				if (++cnt % 1000 == 0) {
					System.out.println(cnt + " lines processed.");
				}
			}
			br2.close();
			System.out.println("ended adding in-data");
			
			// 
			System.out.println("started adding fr-data");
			BufferedReader br3 = new BufferedReader(new FileReader(args[2]));
			line = br3.readLine();
			String features_fr[] = line.split("\t");
			
			featureMap.clear();
			pos = 0;
			for (String f : features_fr) {
				featureMap.put(f, pos++);
			}
			
			cnt = 0;
			while ((line = br3.readLine()) != null) {
				String vals[] = line.split("\t", 1000000);
				
				sb = new StringBuilder();
				for (String feature : features) {
					if (featureMap.containsKey(feature)) {
						int pp = featureMap.get(feature);
						if (feature.equals("m:MatchType")) {
							String matchType = vals[pp];
							switch (matchType) {
								case "B" :
									sb.append("BROADMATCH\t");
									break;
								case "E" :
									sb.append("EXACTMATCH\t");
									break;
								case "P" :
									sb.append("PHRASEMATCH\t");
									break;
								case "S" :
									sb.append("SMARTMATCH\t");
									break;
								case "" :
									sb.append("\t");
									break;
							}
						} else {
							sb.append(vals[pp] + "\t");
						}

					} else {
						if (feature.equals("m:Weight")) {
							sb.append("1\t");
						} else if (feature.equals("m:Url")) {
							sb.append("\t");
						} else if (feature.endsWith("m:Query")) { // ? need this for some java weird io stuff;
							sb.append(vals[0] + "\t");
						}
					}
				}
				
				pw.println(sb.toString().substring(0, sb.toString().length()-1));
				
				if (++cnt % 1000 == 0) {
					System.out.println(cnt + " lines processed.");
				}
			}
			br3.close();
			System.out.println("ended adding fr-data");
			
			
			pw.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
