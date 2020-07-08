package common;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;

public class LuceneQuery {

	public static Report getReportWithID(String id, IndexSearcher isearcher,Integer index) {

		// Parse a simple query that searches for "text":
		Analyzer analyzer = new WhitespaceAnalyzer(); // ..standard english word tokenization
		QueryParser parser = new QueryParser("id", analyzer);

		Query query = null;
		try {
			query = parser.parse(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ScoreDoc[] hits = null;
		try {
			hits = isearcher.search(query, 1).scoreDocs;
		} catch (IOException e) {
			e.printStackTrace();
		}
		// assertEquals(1, hits.length);

		if (hits.length < 1) {
			System.out.println("Your query did not return any result with the given id...");
			assert (false);
		}

		Document hitDoc = null;
		try {
			hitDoc = isearcher.doc(hits[0].doc);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(Integer.toString(i + 1) + " - " + hitDoc.get("title"));
		Report r = new Report(hitDoc.get("id"), hitDoc.get("url"), hitDoc.get("title"), hitDoc.get("author"),
				hitDoc.get("date"), hitDoc.get("source"), hitDoc.get("articleType"), hitDoc.get("content"));
		// System.out.println(hitDoc.get("content"));
		
		r.index = hits[0].doc;
		return r;
	}

	
	

}
