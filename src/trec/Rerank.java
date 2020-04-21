package trec;

import java.util.Collections;
import java.util.List;

public class Rerank {
	 static List<Tuple> rerankWithCategory(Report r,List<Tuple> answers){
		for(Tuple answer : answers) {
			double score = Correlation.getScore(r.articleType, answer.type);
			answer.score *= score;
		}
		
		// now sort again according to score
		Collections.sort(answers);
		return answers;
	}
}
	