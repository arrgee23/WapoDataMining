package trec;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

class LuceneQuery {

	public static Report getReportWithID(String id, IndexSearcher isearcher) {

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
		return r;
	}

	public static void searchInterface() {
		Scanner s = new Scanner(System.in);

		// now searching part

		// Now search the index:
		Directory directory = null;
		try {
			directory = FSDirectory.open(Paths.get(Constants.INDEX_PATH));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		DirectoryReader ireader = null;
		try {
			ireader = DirectoryReader.open(directory);
		} catch (IOException e) {
			e.printStackTrace();
		}
		IndexSearcher isearcher = new IndexSearcher(ireader);
		// Parse a simple query that searches for "text":
		Analyzer analyzer = new StandardAnalyzer(); // ..standard english word tokenization
		QueryParser parser = new QueryParser("title", analyzer);

		while (true) {
			System.out.print("Enter the string you want to search: ");
			String searchQuery = s.nextLine();
			// while(searchQuery==null){
			// searchQuery = s.nextLine();
			// }
			Query query = null;
			try {
				query = parser.parse(searchQuery);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ScoreDoc[] hits = null;
			try {
				hits = isearcher.search(query, 10).scoreDocs;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// assertEquals(1, hits.length);

			if (hits.length < 1) {
				System.out.println("Your query did not return any result...");
				continue;
			}
			// Iterate through the results:
			for (int i = 0; i < hits.length; i++) {
				Document hitDoc = null;
				try {
					hitDoc = isearcher.doc(hits[i].doc);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(Integer.toString(i + 1) + " - " + hitDoc.get("title"));

				// System.out.println(hitDoc.get("content"));

			}
			System.out.println("\n");
			System.out.print("Enter the document no you want to read: ");
			int choice = Integer.parseInt(s.nextLine());
			if (choice > 0 && choice <= hits.length) {
				Document doc = null;
				try {
					doc = isearcher.doc(hits[choice - 1].doc);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(doc.get("content"));
				System.out.println("\n");
			} else {
				System.out.println("Invalid input");
				System.out.println("\n");
			}
		}
	}
	
	public static int flag = 0;
	
	public static List<Tuple> getResultsBaseline(Report r, IndexSearcher isearcher) {
		Analyzer analyzer = new EnglishAnalyzer(); // ..standard english word tokenization
		
		// search in the content field of all articles
		QueryParser parser = new QueryParser("content", analyzer);

		Query query = null;
		
		// make a query concatenating title and content
		List<String> st = returnStringTokens(r.title);
		List<String> sl = returnStringTokens(r.content);
		StringBuffer sb = new StringBuffer();

		int count = 0;

		for (String s : st) {
			if (count >= 1000)
				break;
			sb.append(s);
			sb.append(" ");

			count++;
		}
		
		for (String s : sl) {
			if (count >= 1000)
				break;
			sb.append(s);
			sb.append(" ");

			count++;
		}


		try {
			query = parser.parse(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		// parse through the returned documents
		ScoreDoc[] hits = null;
		try {
			hits = isearcher.search(query, 10000).scoreDocs;
		} catch (IOException e) {
			e.printStackTrace();
		}
		// assertEquals(1, hits.length);

		if (hits.length < 1) {
			System.out.println("Your query did not return any result...");
			assert (false);
		}

		LinkedList<Tuple> answers = new LinkedList<Tuple>();
		try {
			int added = 0;
			for (int i = 0; i < hits.length; i++) {
				
				if (added >= 100)
					break;

				String id = isearcher.doc(hits[i].doc).get("id");
				String dateStr = isearcher.doc(hits[i].doc).get("date");
				
				
					//Document d = isearcher.doc();
					if(flag==0) {
						System.out.println(isearcher.getSimilarity().toString());
						
						
						
						flag = 1;
					}

				@SuppressWarnings("deprecation")
				// Date resultReportDate = new Date(dateStr);
				// Date queryReportDate = new Date(r.date);
				Date resultDate = null;
				Date queryDate = null;
				
				// convert string to date when filterinng to classes
				// TODO add Date field in report class
				if (dateStr != null && r.date != null && !dateStr.equals("null") && !r.date.equals("null")) {
					Instant result_instant = Instant.ofEpochSecond( Long.parseLong(dateStr));
					Instant query_instant = Instant.ofEpochSecond( Long.parseLong(r.date) );
					
					
					resultDate = Date.from( result_instant );
					//resultDate = Long.parseLong(dateStr);
					queryDate = Date.from( query_instant );
				}
				double score = hits[i].score;
				String codename = "onlytitle";
				
				// allow only articles published on or before
				if(		true
//						resultDate.before(queryDate) || 
//						(
//								resultDate.getDay()==queryDate.getDay() 
//								&& resultDate.getMonth()==queryDate.getMonth() 
//								&& resultDate.getYear()==queryDate.getYear()
//						)
				)
				{
					String type = isearcher.doc(hits[i].doc).get("articleType");
					if(type!=null) {
						// ignore articles with this type as specified
						if(type.equals("Opinion") || type.equals("Opinios") ||
								type.equals("Letters to the Editor") || type.equals("The Post's View")) {
							continue;
						}
							
					}
					answers.add(new Tuple(id, score, codename));
					added++;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return answers;

	}

	static List<String> returnStringTokens(String str) {

		List<String> result = new ArrayList<String>();
		Analyzer testAnalyzer = new EnglishAnalyzer();

		TokenStream ts = testAnalyzer.tokenStream("context", new StringReader(str));
		CharTermAttribute attr = ts.addAttribute(CharTermAttribute.class);

		try {
			ts.reset();
			int count = 0;
			while (ts.incrementToken()) {
				// Use AttributeSource.reflectAsString(boolean)
				// for token stream debugging.
				// System.out.println("token: " + attr.toString());

				result.add(attr.toString());
				count++;
				// System.out.println("token start offset: " + offsetAtt.startOffset());
				// System.out.println(" token end offset: " + offsetAtt.endOffset());
			}
			ts.end();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ts.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return result;
	}

	public static List<Tuple> getResultsContent(Report r, IndexSearcher isearcher) {
		// Parse a simple query that searches for "text":
		Analyzer analyzer = new EnglishAnalyzer(); // ..standard english word tokenization
		QueryParser parser = new QueryParser("content", analyzer);

		Query query = null;

		List<String> st = returnStringTokens(r.title);
		List<String> sl = returnStringTokens(r.content);
		StringBuffer sb = new StringBuffer();

		int count = 0;

		for (String s : st) {
			if (count > 1000)
				break;
			sb.append(s);
			sb.append(" ");

			count++;
		}

		for (String s : sl) {
			if (count > 1000)
				break;

			sb.append(s);
			sb.append(" ");

			count++;
		}

		try {
			query = parser.parse(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		ScoreDoc[] hits = null;
		try {
			hits = isearcher.search(query, 1000).scoreDocs;
		} catch (IOException e) {
			e.printStackTrace();
		}
		// assertEquals(1, hits.length);

		if (hits.length < 1) {
			System.out.println("Your query did not return any result...");
			assert (false);
		}

		LinkedList<Tuple> answers = new LinkedList<Tuple>();
		try {
			int added = 0;
			for (int i = 0; i < hits.length; i++) {

				if (added >= 100)
					break;

				String id = isearcher.doc(hits[i].doc).get("id");
				String dateStr = isearcher.doc(hits[i].doc).get("date");

				@SuppressWarnings("deprecation")
				// Date resultReportDate = new Date(dateStr);
				// Date queryReportDate = new Date(r.date);
				long resultReportDate = 0;
				long queryReportDate = 0;

				if (dateStr != null && r.date != null && !dateStr.equals("null") && !r.date.equals("null")) {
					resultReportDate = Long.parseLong(dateStr);
					queryReportDate = Long.parseLong(r.date);
				}
				double score = hits[i].score;
				String codename = "arrgee1";

				// if(queryReportDate>=resultReportDate){
				answers.add(new Tuple(id, score, codename));
				added++;
				// }
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return answers;
	}

	// send words based on sorted tf idf score
	public static List<Tuple> getResultsContentSorted(Report r, IndexSearcher isearcher) {
		// Parse a simple query that searches for "text":
		Analyzer analyzer = new EnglishAnalyzer(); // ..standard english word tokenization
		QueryParser parser = new QueryParser("content", analyzer);

		Query query = null;

		List<String> sl = returnStringTokens(r.content);
		StringBuffer sb = new StringBuffer();
		for (String s : sl) {
			sb.append(s);
			sb.append(" ");
		}

		try {
			query = parser.parse(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		ScoreDoc[] hits = null;
		try {
			hits = isearcher.search(query, 1000).scoreDocs;
		} catch (IOException e) {
			e.printStackTrace();
		}
		// assertEquals(1, hits.length);

		if (hits.length < 1) {
			System.out.println("Your query did not return any result...");
			assert (false);
		}

		LinkedList<Tuple> answers = new LinkedList<Tuple>();
		try {
			int count = 0;
			for (int i = 0; i < hits.length; i++) {
				String type = isearcher.doc(hits[i].doc).get("id");
				// “Opinion”, “Letters to the Editor”, or “The Post’s View” are eleminated
				// if()
				if (count < 100) {
					String id = isearcher.doc(hits[i].doc).get("id");
					double score = hits[i].score;
					String codename = "arrgee1";

					answers.add(new Tuple(id, score, codename));
					count++;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return answers;
	}

}
