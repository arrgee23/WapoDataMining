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
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import trec.myTFIDF.ScoreTerm;

public class LuceneQuery {

	public static Report getReportWithID(String id, IndexSearcher isearcher) {

		// Parse a simple query that searches for "text":
		//System.out.println("Searching for: "+id);
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
				hits = isearcher.search(query, 100).scoreDocs;
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

	// query concatenating title and content
	public static String makeTitleContentQuery(Report r, IndexSearcher isearcher, int noTerms) {
		// make a query concatenating title and content
		List<String> st = returnStringTokens(r.title);
		List<String> sl = returnStringTokens(r.content);
		StringBuffer sb = new StringBuffer();

		int count = 0;

		for (String s : st) {
			if (count >= noTerms)
				break;
			sb.append(s);
			sb.append(" ");

			count++;
		}

		for (String s : sl) {
			if (count >= noTerms)
				break;
			sb.append(s);
			sb.append(" ");

			count++;
		}

		return sb.toString();
	}

	public static List<Tuple> getResultsBaseline(Report r, IndexSearcher isearcher,IndexReader ireader) {
		Analyzer analyzer = new EnglishAnalyzer(); // ..standard english word tokenization

		// search in the content field of all articles
		QueryParser parser = new QueryParser("content", analyzer);

		Query query = null;

		String queryStr = makeTitleContentQuery(r,isearcher,1000);
		try {
			query = parser.parse(queryStr);
		} catch (Exception e) {
			e.printStackTrace();
		}


		// parse through the returned documents
		ScoreDoc[] hits = null;
		try {
			hits = isearcher.search(query, 10000).scoreDocs;
			//isearcher.search(query,10000).
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
					answers.add(new Tuple(id, score, codename,type));
					added++;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return answers;

	}




	public static List<Tuple> getResultsMoreLikeThis(Report r, IndexSearcher isearcher,IndexReader ireader)  {
		// generating query for top content terms
		MoreLikeThis mlt = new MoreLikeThis(ireader);
		mlt.setAnalyzer(new EnglishAnalyzer());
		mlt.setMaxQueryTerms(Constants.CONTENT_QUERY_LENGTH);
		mlt.setBoost(true);
		String[] sarr = {"content"};
		mlt.setFieldNames(sarr);

		// generating query for top title terms
		MoreLikeThis mlt2 = new MoreLikeThis(ireader);
		mlt2.setAnalyzer(new EnglishAnalyzer());
		mlt2.setMinDocFreq(0);
		mlt2.setMinTermFreq(0);
		mlt2.setMaxQueryTerms(Constants.TITLE_QUERY_LENGTH);
		mlt2.setBoost(true);
		String[] sarr2 = {"title"};
		mlt2.setFieldNames(sarr2);

		//mlt.setSimilarity( );
		//System.out.println(mlt.getSimilarity().toString());
		//tfIdf.
		//mlt.
		//System.out.println(mlt.describeParams()+"\nBoostFactor: "+mlt.getBoostFactor());

		//Reader target = ireader;//... // orig source of doc you want to find similarities to

		Integer lucene_id = new Integer(0);
		Report rp = LuceneQuery.getReportWithID(r.id, isearcher);

		lucene_id = r.index;
		System.out.println("Searching for index id: "+lucene_id);

		Query query = null;
		Query contentQuery = null;
		Query titleQuery = null;

		if(lucene_id!=0) {
			try {

				// best tfidf scores with boost from content
				contentQuery =  mlt.like(lucene_id);
				titleQuery = mlt2.like(lucene_id);

				Builder queryBuilder = new BooleanQuery.Builder()
						.add(contentQuery, Occur.SHOULD);
				queryBuilder.add(titleQuery,Occur.SHOULD);



				List<String> st = returnStringTokens(r.title);

				// add strings in title to query 
				for (String s : st) 
				{
					queryBuilder.add(new
							TermQuery(new Term("content", s)), Occur.SHOULD); 
				}
				//queryBuilder.add(new TermQuery(new Term("author", r.author)), Occur.SHOULD); 
				query = queryBuilder.build();
				System.out.println(query.toString());

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else
			assert(false);

		//Hits hits = is.search(query);
		// now the usual iteration thru 'hits' - the only thing to watch for is to make sure
		//you ignore the doc if it matches your 'target' document, as it should be similar to itself

		// parse through the returned documents
		ScoreDoc[] hits = null;
		try {
			TopDocs td = isearcher.search(query, 100);

			hits = td.scoreDocs;

			//isearcher.search(query,10000).
		} catch (IOException e) {
			e.printStackTrace();
		}
		// assertEquals(1, hits.length);

		if (hits.length < 1) {
			System.out.println("Your query did not return any result...");
			assert (false);
		}
		/*
		 * try { Explanation e = isearcher.explain(query,hits[2].doc);
		 * System.out.println(e.getDescription()); for(Explanation e1:e.getDetails()) {
		 * //System.out.println(e1.getDescription()+" "+e1.getValue()); } } catch
		 * (IOException e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
		 */

		LinkedList<Tuple> answers = new LinkedList<Tuple>();
		try {
			int added = 0;
			for (int i = 0; i < hits.length; i++) {

				if (added >= 5)
					break;

				String id = isearcher.doc(hits[i].doc).get("id");
				String title = isearcher.doc(hits[i].doc).get("tltle");

				if(id.equals(r.getId())) // document most similar with itself
					continue;
				if(r.title!=null && title!=null) {
					if(title.equals(r.getTitle()))
						continue;
				}


				// look in the previous documents with same title
				String currTitle = isearcher.doc(hits[i].doc).get("title");
				boolean alreadyAdded = false;
				for(int j=i-1;j>=0;j--) {
					String prevTitle = isearcher.doc(hits[j].doc).get("title");
					if(prevTitle.equals(currTitle))
					{
						alreadyAdded = true;
						break;
					}
				}
				//if(alreadyAdded)
				//continue;


				String dateStr = isearcher.doc(hits[i].doc).get("date");



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
				if(
						resultDate==null || queryDate==null ||
						//resultDate.after(queryDate) || 
						resultDate.before(queryDate) || 
						(
								resultDate.getDay()==queryDate.getDay() 
								&& resultDate.getMonth()==queryDate.getMonth() 
								&& resultDate.getYear()==queryDate.getYear()
								)
						)
				{
					String type = isearcher.doc(hits[i].doc).get("articleType");
					if(type!=null) {
						// ignore articles with this type as specified
						if(type.equals("Opinion") || type.equals("Opinions") ||
								type.equals("Letters to the Editor") || type.equals("The Post's View")) {
							continue;
						}

					}
					//System.out.println(added+": "+hits[i].doc+" "+id);
					answers.add(new Tuple(id, score, codename,type));
					added++;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return answers;

	}

	
	public static List<Tuple> getResultsTopTermsContent(Report r, IndexSearcher isearcher,IndexReader ireader)  {
		// generating query for top content terms
		myTFIDF mlt = new myTFIDF(ireader);
		mlt.setAnalyzer(new EnglishAnalyzer());
		mlt.setMaxQueryTerms(Constants.CONTENT_QUERY_LENGTH);
		mlt.setBoost(true);
		String[] sarr = {"content"};
		mlt.setFieldNames(sarr);


		
		Integer lucene_id = new Integer(0);
		Report rp = LuceneQuery.getReportWithID(r.id, isearcher);

		lucene_id = r.index;
		System.out.println("Searching for index id: "+lucene_id);

		Query query = null;
		Query contentQuery = null;
		Query titleQuery = null;

		if(lucene_id!=0) {
			try {

				// best tfidf scores with boost from content
				//contentQuery =  mlt.like(lucene_id);

				// extract priority queue of top terms from content and title
				org.apache.lucene.util.PriorityQueue<ScoreTerm> pqContent =  mlt.retrieveTerms(lucene_id);
				//titleQuery = mlt2.like(lucene_id);
				
				System.out.println(pqContent.size());
				
				// build query with specific number of terms from title and content
				Builder queryBuilder = new BooleanQuery.Builder();

				float leastScoreContent = -1;
				float boostFactorContent = 1;
				int count = 0;
				ScoreTerm scoreTerm;
				while((scoreTerm = pqContent.pop()) != null && count<Constants.CONTENT_QUERY_LENGTH) {
					Query tq = new TermQuery(new Term("content", scoreTerm.word));
					
					if (leastScoreContent == -1) {
						leastScoreContent = (scoreTerm.score);
					}
					float myScore = (scoreTerm.score);
					tq = new BoostQuery(tq, boostFactorContent * myScore / leastScoreContent);
					
					try {
						queryBuilder.add(tq, BooleanClause.Occur.SHOULD);
						
					}
					//catch (IndexSearcher.TooManyClauses ignore) 
					catch(Exception e){
						break;
					}


					count++;
				}
				


				query = queryBuilder.build();
				System.out.println(query.toString());

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else
			assert(false);

		//Hits hits = is.search(query);
		// now the usual iteration thru 'hits' - the only thing to watch for is to make sure
		//you ignore the doc if it matches your 'target' document, as it should be similar to itself

		// parse through the returned documents
		ScoreDoc[] hits = null;
		try {
			TopDocs td = isearcher.search(query, 50);

			hits = td.scoreDocs;

			//isearcher.search(query,10000).
		} catch (IOException e) {
			e.printStackTrace();
		}
		// assertEquals(1, hits.length);

		if (hits.length < 1) {
			System.out.println("Your query did not return any result...");
			assert (false);
		}
		/*
		 * try { Explanation e = isearcher.explain(query,hits[2].doc);
		 * System.out.println(e.getDescription()); for(Explanation e1:e.getDetails()) {
		 * //System.out.println(e1.getDescription()+" "+e1.getValue()); } } catch
		 * (IOException e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
		 */

		LinkedList<Tuple> answers = new LinkedList<Tuple>();
		try {
			int added = 0;
			for (int i = 0; i < hits.length; i++) {

				if (added >= 100)
					break;

				String id = isearcher.doc(hits[i].doc).get("id");
				String title = isearcher.doc(hits[i].doc).get("tltle");

				if(id.equals(r.getId())) // document most similar with itself
					continue;
				if(r.title!=null && title!=null) {
					if(title.equals(r.getTitle()))
						continue;
				}


				// look in the previous documents with same title
				String currTitle = isearcher.doc(hits[i].doc).get("title");
				boolean alreadyAdded = false;
				for(int j=i-1;j>=0;j--) {
					String prevTitle = isearcher.doc(hits[j].doc).get("title");
					if(prevTitle.equals(currTitle))
					{
						alreadyAdded = true;
						break;
					}
				}
				//if(alreadyAdded)
				//continue;


				String dateStr = isearcher.doc(hits[i].doc).get("date");



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
				if(
						resultDate==null || queryDate==null ||
						//resultDate.after(queryDate) || 
						resultDate.before(queryDate) || 
						(
								resultDate.getDay()==queryDate.getDay() 
								&& resultDate.getMonth()==queryDate.getMonth() 
								&& resultDate.getYear()==queryDate.getYear()
								)
						)
				{
					String type = isearcher.doc(hits[i].doc).get("articleType");
					if(type!=null) {
						// ignore articles with this type as specified
						if(type.equals("Opinion") || type.equals("Opinions") ||
								type.equals("Letters to the Editor") || type.equals("The Post's View")) {
							continue;
						}

					}
					//System.out.println(added+": "+hits[i].doc+" "+id);
					answers.add(new Tuple(id, score, codename,type));
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
