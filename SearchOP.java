import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SearchOP {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.err.println("Usage: java SearchOP scoreFile_1:scoreFile_2:scoreFile_3");
		
		try {
			if (args.length != 1) {
				System.err.println("wrong input arguments - exit");
				System.exit(-1);
			}
			
			String[] scoreFiles = args[0].split(":");
            int numScoreFiles = scoreFiles.length;
			
			if (numScoreFiles == 2) {
				double minLoss = 1000;
				int mini=-1, minj=-1;
				for (int i=0; i<=100; i=i+5) {
					for (int j=0; j<=100; j=j+5) {
						if (i + j != 100) {
							continue;
						}
						int[] w = new int[2];
						w[0] = i;
						w[1] = j;
						double loss = eval(scoreFiles, w);
						System.err.println("processing i = " + i + "\tj = " + j + "\tlogloss = " + loss);
						if (loss < minLoss) {
							minLoss = loss;
							mini = i;
							minj = j;
						}
					}
				}
				
				System.out.println("op solution is: i = " + mini + "\tj = " + minj);
				System.out.println("min logloss = " + minLoss);				
			} else if (numScoreFiles == 3) {
				double minLoss = 1000;
				int mini=-1, minj=-1, mink=-1;
				for (int i=0; i<=100; i=i+5) {
					for (int j=0; j<=100; j=j+5) {
						for (int k=0; k<=100; k=k+5) {
							if (i + j + k!= 100) {
								continue;
							}
							
							int[] w = new int[3];
							w[0] = i;
							w[1] = j;
							w[2] = k;
							double loss = eval(scoreFiles, w);
							System.err.println("processing i = " + i + "\tj = " + j + "\tk = " + k + "\tlogloss = " + loss);
							if (loss < minLoss) {
								minLoss = loss;
								mini = i;
								minj = j;
								mink = k;
							}
						}
					}
				}
				
				System.out.println("op solution is: i = " + mini + "\tj = " + minj + "\tk = " + mink);
				System.out.println("min logloss = " + minLoss);
			} else if (numScoreFiles == 4) {
				double minLoss = 1000;
				int mini=-1, minj=-1, mink=-1, minl=-1;
				for (int i=0; i<=100; i=i+5) {
					for (int j=0; j<=100; j=j+5) {
						for (int k=0; k<=100; k=k+5) {
							for (int l=0; l<=100; l=l+5) {
								if (i + j + k + l != 100) {
									continue;
								}
								
								int[] w = new int[4];
								w[0] = i;
								w[1] = j;
								w[2] = k;
								w[3] = l;
								double loss = eval(scoreFiles, w);
								System.err.println("processing i = " + i + "\tj = " + j + "\tk = " + k + "\tl = " + l + "\tlogloss = " + loss);
								if (loss < minLoss) {
									minLoss = loss;
									mini = i;
									minj = j;
									mink = k;
									minl = l;
								}
							}
						}
					}
				}
				
				System.out.println("op solution is: i = " + mini + "\tj = " + minj + "\tk = " + mink + "\tl = " + minl);
				System.out.println("min logloss = " + minLoss);

			} else if (numScoreFiles == 5) {
				double minLoss = 1000;
				int mini=-1, minj=-1, mink=-1, minl=-1, minm=-1;
				for (int i=0; i<=100; i=i+5) {
					for (int j=0; j<=100; j=j+5) {
						for (int k=0; k<=100; k=k+5) {
							for (int l=0; l<=100; l=l+5) {
								for (int m=0; m<=100; m=m+5) {
									if (i + j + k + l + m!= 100) {
										continue;
									}
									
									int[] w = new int[5];
									w[0] = i;
									w[1] = j;
									w[2] = k;
									w[3] = l;
									w[4] = m;
									double loss = eval(scoreFiles, w);
									
									System.err.println("processing i = " + i + "\tj = " + j + "\tk = " + k + "\tl = " + l + "\tm = " + m + "\tlogloss = " + loss);
									if (loss < minLoss) {
										minLoss = loss;
										mini = i;
										minj = j;
										mink = k;
										minl = l;
										minm = m;
									}
								}
							}
						}
					}
				}
				
				System.out.println("op solution is: i = " + mini + "\tj = " + minj + "\tk = " + mink + "\tl = " + minl + "\tm = " + minm);
				System.out.println("min logloss = " + minLoss);
			} else {
				System.err.println("score files cannot be > 5");
				System.exit(-1);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
			
	public static double eval(String[] scoreFiles, int[] w) {
	    float[] weights = new float[w.length];
	    
	    for (int i=0; i<w.length; i++) {
	    	weights[i] = w[i] / 100.0f;
	    }
		
		try {
			BufferedReader[] brs = new BufferedReader[scoreFiles.length];
			for (int i=0; i<scoreFiles.length; i++) {
				brs[i] = new BufferedReader(new FileReader(scoreFiles[i]));
			}
			PrintWriter pw = new PrintWriter(new File("tempScore"));
			
			String line = brs[0].readLine(); // first line;
			String[] vals = line.split("\t");
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
				
				/*
				cnt++;
				if (cnt % 1000 == 0) {
					System.err.println(cnt + " lines processed.");
				}*/
			}
			
			pw.close();
			
			for (int i=0; i<brs.length; i++) {
				brs[i].close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
					
		double result = 0.0;
		try {
			String cmd = "./evalModel/EvaluateClassifier.exe -f:tempScore -r:roc -s:summary";
			Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();

			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (!line.startsWith("Logloss")) {
					continue;
				}
				
				String[] temp = line.trim().split(":");
				result = Double.parseDouble(temp[1]);
				break;
			}
			
			br.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return result;
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
