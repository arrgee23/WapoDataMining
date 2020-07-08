package WordVectors;

import static java.lang.Character.isLetter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ReportVector extends WordVec{
			

	// given a string of
	// @param s(string) = <reportID> word1 weight1 word2 Weight2 wordn weightn
	// @param k(int) = number of word vectors to consider
	// @param isWeighted = weight by tdidf or not
	// @param isConsiderNorm -> consider norms of individual word embeddings
	static double maxNorm = Double.MIN_VALUE;
	static double minNorm = Double.MAX_VALUE;
	public ReportVector(String[] tokens, WordVecs wvs, int k, boolean isWeighted, boolean isConsiderNorm) {
		
		this.word = tokens[0];
		String[] words = new String[(tokens.length - 1) / 2];
		double[] weights = new double[(tokens.length - 1) / 2];
		int cnt = 0;
		// System.out.println(reportId+" ");
		
		double minWeight = Double.MAX_VALUE;
		for (int i = tokens.length - 1; i > 0; i = i - 2) {
			String weight = tokens[i];
			String word = tokens[i - 1];
			if (isLegalToken(word)) {
				words[cnt] = word;
				weights[cnt] = Double.parseDouble(weight);
				if(weights[cnt]<minWeight)
					minWeight = weights[cnt];
				cnt++;
			}
			if (cnt >= k)
				break;
		}
		
		// dividing by the least weight normalizes
		for (int i = 0; i < cnt; i++) {
			weights[i] = weights[i]/minWeight;
		}
		
		//int loopCount = 0;
		List<WordVec> ls = new LinkedList<WordVec>();
		
		for (int i = 0; i < cnt; i++) {
			WordVec wv = wvs.wordvecmap.get(words[i]);
			WordVec newVec = wv;
		
			if(wv!=null) {
				
					if(isWeighted) {
					
						newVec = wv.scalerMult(weights[i],isConsiderNorm);
					
					}
				
				ls.add(newVec);
			}
		}
		WordVec all = WordVec.add(ls);
		if(all!=null) {
			this.vec = all.vec;
			all.norm = 0;
			this.norm = all.getNorm();
			if(all.norm == 0) { // zero vector
				this.vec = null;
			}
		}else {
			this.vec = null;
		}
		
		//this.print();
		// norm = getNorm();
		
	}

	public static WordVec addWithoutAverage(WordVec a, WordVec b) {
		WordVec sum = new WordVec(a.word + ":" + b.word);
		sum.vec = new double[a.vec.length];
		for (int i = 0; i < a.vec.length; i++) {
			sum.vec[i] = (a.vec[i] + b.vec[i]);
		}
		return sum;
	}

	public static WordVec add(List<WordVec> list) {

		WordVec sum = new WordVec(list.get(0).vec.length); // initially an all zero vector

		for (int i = 0; i < list.size(); i++) {
			sum = addWithoutAverage(sum, list.get(i));
		}
		for (int i = 0; i < sum.vec.length; i++)
			sum.vec[i] /= list.size();
		sum.word = list.get(0).word;
		return sum;
	}

	private boolean isLegalToken(String word) {
		boolean flag = true;
		for (int i = 0; i < word.length(); i++) {
//	            if(isDigit(word.charAt(i))) {
//	                flag = false;
//	                break;
//	            }
			if (isLetter(word.charAt(i))) {
				continue;
			} else {
				flag = false;
				break;
			}
		}
		return flag;
	}

	public static void main(String[] args) {

	}

}
