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


public class Convert_matchType {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("usage: java Convert_matchType input output");
		System.out.println("args[1] = " + args[0]);
		System.out.println("args[2] = " + args[1]);
		
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(args[0]));
			PrintWriter pw = new PrintWriter(new File(args[1]));
			
			String line = br1.readLine();
			String features[] = line.split("\t");
			
			int matchTypeIdx = 0;
			for (String feature : features) {
				if ("m:MatchType".equals(feature)) {
					break;
				}
				matchTypeIdx ++;
			}
			pw.println(line);
			
			int cnt = 0;
			
			while ((line = br1.readLine()) != null) {
				String vals[] = line.split("\t", 1000000);
				
				StringBuilder sb = new StringBuilder();
				
				for (int i=0; i<matchTypeIdx; i++) {
					sb.append(vals[i] + "\t");
				}
				
				String matchType = vals[matchTypeIdx];
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

				}
				
				for (int i=matchTypeIdx+1; i<vals.length-1; i++) {
					sb.append(vals[i] + "\t");
				}
				sb.append(vals[vals.length -1]);
				
				pw.println(sb.toString());
				
				if (cnt++ % 1000 == 0) {
					System.out.println(cnt + " lines processed.");
				}
			}

			br1.close();
			pw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
