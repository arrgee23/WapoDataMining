package trec;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import trec.myTFIDF.ScoreTerm;

public class DumpDocumentTermScore {

	public static String getScoreString(int luceneID, int k, IndexReader ireader,String field) {
		// generating query for top content terms
		myTFIDF mlt = new myTFIDF(ireader);
		mlt.setAnalyzer(new EnglishAnalyzer());
		mlt.setMaxQueryTerms(k);
		mlt.setBoost(true);
		mlt.setMinTermFreq(1);
		
		String[] sarr = { field};
		mlt.setFieldNames(sarr);
		
		StringBuffer sb = new StringBuffer();
		if (luceneID != 0) {
			try {

				// best tfidf scores with boost from content
				// contentQuery = mlt.like(lucene_id);
				String docID = ireader.document(luceneID).get("id");
				sb.append(docID);
				
				// extract priority queue of top terms from content and title
				org.apache.lucene.util.PriorityQueue<ScoreTerm> pqContent = mlt.retrieveTerms(luceneID);

				
				float leastScoreContent = -1;
				int count = 0;
				ScoreTerm scoreTerm;
				while ((scoreTerm = pqContent.pop()) != null && count < k) {

					if (leastScoreContent == -1) {
						leastScoreContent = (scoreTerm.score);
					}
					float myScore = (scoreTerm.score);
					myScore = myScore / leastScoreContent;

					sb.append(" "+scoreTerm.word + " " + myScore);
					count++;

				}

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		System.out.println(sb.toString());
		return sb.toString();
	}
	// write k top words with their weights to given filename
	public static void writeDocumentImportnatWordsTofile(IndexReader ir,int k,String fileName,String field) {
		
            try {
				FileWriter dumpFW = new FileWriter(fileName);
				 int maxDoc = ir.maxDoc();
				 for (int i = 0; i < maxDoc; i++) {
					dumpFW.write(getScoreString(i,k,ir,field));
	                dumpFW.write("\n");
				 }
				 dumpFW.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
	}
		
	public static void main(String[] args) {
		File indexFile = new File(Constants.INDEX_PATH);
	
		try {
			IndexReader reader = DirectoryReader.open(FSDirectory.open(indexFile.toPath()));
			writeDocumentImportnatWordsTofile(reader,40,Constants.DUMP_WORD2VEC_KEY_VAL_FILE_CONTENT_2020,"content");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
