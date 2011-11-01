import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.err.println("Usage: java Ensemble scoreFile_1:scoreFile_2:... weight_1:weight_2:... output_ensemble_score_file");
		
		try {
			if (args.length != 3) {
				System.err.println("wrong input arguments - exit");
				System.exit(-1);
			}
			
			String[] scoreFiles = args[0].split(":");
			String[] vals = args[1].split(":");
			float[] weights = new float[vals.length];
			int j = 0;
			for (String e : vals) {
				weights[j++] = Float.parseFloat(e);
			}
			
			BufferedReader[] brs = new BufferedReader[vals.length];
			for (int i=0; i<scoreFiles.length; i++) {
				brs[i] = new BufferedReader(new FileReader(scoreFiles[i]));
			}
			PrintWriter pw = new PrintWriter(new File(args[2]));
			
			String line = brs[0].readLine(); // first line;
			vals = line.split("\t");
			int pos = -1;
			for (int i=0; i<vals.length; i++) {
				if (vals[i].equals("m:Predicted")) {
					pos = i;
					break;
				}
			}

			for (int i=1; i<brs.length; i++) {
				brs[i].readLine();
			}
			pw.println(line);
			
			int cnt = 0;
			float[] scores = new float[brs.length];
			while ((line = brs[0].readLine()) != null) {
				vals = line.split("\t");
				scores[0] = Float.parseFloat(vals[pos]);
				
				for (int i=1; i<brs.length; i++) {
					line = brs[i].readLine();
					String[] temp = line.split("\t");
					scores[i] = Float.parseFloat(temp[pos]);
				}
				
				float ensembleScore = ensemble(scores, weights);
				
				StringBuilder sb = new StringBuilder();
				for (int i=0; i<pos; i++) {
					sb.append(vals[i] + "\t");
				}
				sb.append(ensembleScore + "\t");
				for (int i=pos+1; i<vals.length; i++) {
					sb.append(vals[i] + "\t");
				}				
				pw.println(sb.toString().substring(0, sb.toString().length()-1));
				
				cnt++;
				if (cnt % 1000 == 0) {
					System.err.println(cnt + " lines processed.");
				}
			}
			
			pw.close();
			
			for (int i=0; i<brs.length; i++) {
				brs[i].close();
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
	}
	
	public static float ensemble(float[] scores, float[] weights) {
		if (scores.length != weights.length) {
			System.err.println("wrong input arguments in ensemble() - exit");
			System.exit(-1);
		}
		
		float sum = 0.0f;
		for (int i=0; i<scores.length; i++) {
			sum += weights[i] * scores[i];
		}
		
		return sum;
	}
}
