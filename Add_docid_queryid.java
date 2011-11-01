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


public class Add_docid_queryid {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("usage: java Add_docid_queryid input output");
		System.out.println("args[1] = " + args[0]);
		System.out.println("args[2] = " + args[1]);
		
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(args[0]));

			Set<String> queries = new HashSet<String>();
			Set<String> adIds = new HashSet<String>();

			String line = null;
			String firstLine = br1.readLine(); // skip 1st line;
			while ((line = br1.readLine()) != null) {
				String val[] = line.split("\t", 3);
				
				String query = val[0].trim();
				String adId = val[1].trim();
				
				queries.add(query);
				adIds.add(adId);
			}
			br1.close();
			
			Map<String, Integer> queryMap = new HashMap<String, Integer>();
			Map<String, Integer> adIdMap = new HashMap<String, Integer>();
			int i = 0;
			for (String query : queries) {
				queryMap.put(query, i++);
			}
			
			i = 0;
			for (String id : adIds) {
				adIdMap.put(id, i++);
			}

			br1 = new BufferedReader(new FileReader(args[0]));
			PrintWriter pw = new PrintWriter(new File(args[1]));
			line = br1.readLine();
			pw.println("m:DocId\tm:QueryId\t" + firstLine);
			
			int cnt = 0;
			while ((line = br1.readLine()) != null) {
				String val[] = line.split("\t", 3);
				
				String query = val[0].trim();
				String adId = val[1].trim();

				String str = adIdMap.get(adId).toString() + "\t" + queryMap.get(query).toString() + "\t";
				
				pw.println(str + line);
				
				if (cnt++ % 1000 == 0) {
					System.out.println(cnt + " lines processed...");
				}
			}
			
			br1.close();
			pw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
