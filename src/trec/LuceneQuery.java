package trec;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
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
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TotalHits;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import WordVectors.ReportToVectors;
import WordVectors.ReportVector;
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
				hitDoc.get("date"), hitDoc.get("source"), hitDoc.get("articleType"), hitDoc.get("content"),
				hitDoc.get("nercontent"),hitDoc.get("nertitle"));
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
	/*
	public static Query makeTopKTermQueryFromReportWrapper(int k,String fieldToExtractTermsForm,
			Report r,IndexReader ireader,
			IndexSearcher isearcher, int minTermFreq
			, String[] fieldsToIssueQueryOn) {
		if(fieldToExtractTermsForm.equals("contentner") || fieldToExtractTermsForm.equals("titlener") )
			
			return makeTopKNERTermQueryFromReport(k,fieldToExtractTermsForm,r,
					ireader,isearcher,minTermFreq,fieldsToIssueQueryOn);
		else
			return makeTopKTermQueryFromReport(k,fieldToExtractTermsForm,r,
					ireader,isearcher,minTermFreq,fieldsToIssueQueryOn);
	}
	*/
	private static Query makeTopKNERTermQueryFromReport(int k, String fieldToExtractTermsForm, Report r,
			IndexReader ireader, IndexSearcher isearcher, int minTermFreq, String[] fieldsToIssueQueryOn) {
		
		// TODO Auto-generated method stub
		return null;
	}

	public static Query makeTopKTermQueryFromReport(int k,String fieldToExtractTermsForm,
			Report r,IndexReader ireader,
			IndexSearcher isearcher, int minTermFreq,
			float lambda,
			String[] fieldsToIssueQueryOn) {
		
		// generating query for top content terms
				myTFIDF mlt = new myTFIDF(ireader);
				mlt.setAnalyzer(new EnglishAnalyzer());
				mlt.setMinTermFreq(minTermFreq);
				mlt.setMaxQueryTerms(k);
				mlt.setBoost(true);
				String[] sarr = {fieldToExtractTermsForm};
				mlt.setFieldNames(sarr);


				
				Integer lucene_id = new Integer(0);
				//Report rp = LuceneQuery.getReportWithID(r.id, isearcher);

				lucene_id = r.index;
				//System.out.println("Searching for index id: "+lucene_id);

				Query query = null;
				
				if(lucene_id!=0) {
					try {

						// best tfidf scores with boost from content
						//contentQuery =  mlt.like(lucene_id);

						// extract priority queue of top terms from content and title
						org.apache.lucene.util.PriorityQueue<ScoreTerm> pqContent =  mlt.retrieveTerms(lucene_id);
						//titleQuery = mlt2.like(lucene_id);
						
						//System.out.println(pqContent.size());
						
						// build query with specific number of terms from title and content
						Builder queryBuilder = new BooleanQuery.Builder();

						float leastScoreContent = -1;
						float boostFactorContent = 1;
						int count = 0;
						ScoreTerm scoreTerm;
						while((scoreTerm = pqContent.pop()) != null && count<k) {
							//System.out.println("**"+scoreTerm.word);
							if (leastScoreContent == -1) {
								leastScoreContent = (scoreTerm.score);
							}
							float myScore = (scoreTerm.score);
							
							/*
							 * costruct the query such that
							 * Search for the key word
							 *  on given(fieldsToIssueQueryOn) 
							 *  set of fields
							 */
							for(int i=0;i<fieldsToIssueQueryOn.length;i++) {
								Query tq = new TermQuery(new Term(fieldsToIssueQueryOn[i], scoreTerm.word));
								tq = new BoostQuery(tq,lambda* boostFactorContent * myScore / leastScoreContent);
								try {
									queryBuilder.add(tq, BooleanClause.Occur.SHOULD);
								}
								//catch (IndexSearcher.TooManyClauses ignore) 
								catch(Exception e){
									break;
								}
							}
							//Query tq = new TermQuery(new Term("content", scoreTerm.word));
							//Query tqt = new TermQuery(new Term("title", scoreTerm.word));
							
							//tq = new BoostQuery(tq, boostFactorContent * myScore / leastScoreContent);
							//tqt = new BoostQuery(tqt, boostFactorContent * myScore / leastScoreContent);
							
							


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
		return query;

	}
	
	/*
	 * Merge two queries,
	 * the set of fields q1 and q2 is applied on must be disjoint
	 */
	public static Query mergeQueries(Query q1, Query q2) {
		Builder queryBuilder = new BooleanQuery.Builder();
		//q1.createWeight(searcher, scoreMode, boost)
		//queryBuilder.
		queryBuilder.add(q1,BooleanClause.Occur.SHOULD );
		queryBuilder.add(q2,BooleanClause.Occur.SHOULD );
		
		return queryBuilder.build();
	}

	public static TopDocs getResultsTopTerms(Report r, IndexSearcher isearcher,IndexReader ireader)  {
		String[] fieldsToIssueQueryOn = {"title","content"};
		
		float lambda = (float)0;
		//Query titleQuery = makeTopKTermQueryFromReport(Constants.TITLE_QUERY_LENGTH, "title", r, ireader,isearcher
			//	,1,(float) lambda,fieldsToIssueQueryOn);
		Query contentQuery = makeTopKTermQueryFromReport(Constants.CONTENT_QUERY_LENGTH, "content", r, ireader,isearcher
				,2,(float) 1-lambda,fieldsToIssueQueryOn);
	
		//Query content_title_query = mergeQueries(contentQuery, titleQuery);
		
		//float lambda = (float) 0.6;
		//Query contentQuery = makeTopKTermQueryFromReport(Constants.CONTENT_QUERY_LENGTH, "content", r, 
		//		ireader,isearcher,2,lambda,fieldsToIssueQueryOn);
		
		
		// merge ner topdocs
		//String[] fieldsToIssueQueryOn2 = {"titlener","contentner"};
		//Query queryTitleNer = makeTopKTermQueryFromReport(30, "titlener", r, ireader,isearcher,1,
		//		1-lambda,fieldsToIssueQueryOn2);
		//Query queryContentNer = makeTopKTermQueryFromReport(30, "contentner", r, 
		//		ireader,isearcher,1,1-lambda,fieldsToIssueQueryOn2);
				
		//Query content_queryContentNer = mergeQueries(contentQuery, queryTitleNer);
		
		//Hits hits = is.search(query);
		// now the usual iteration thru 'hits' - the only thing to watch for is to make sure
		//you ignore the doc if it matches your 'target' document, as it should be similar to itself
		TopDocs td1=null,td2 = null,td3=null,td4=null,td5=null,td6=null;
		try {
			//td1 = isearcher.search(titleQuery, 100);
			td2 = isearcher.search(contentQuery, 100);
			//td3 = isearcher.search(queryTitleNer, 100);
			//td4 = isearcher.search(queryContentNer, 100);
			//td5 = isearcher.search(content_queryContentNer, 100);
			//td6 = isearcher.search(content_title_query,100);
			//isearcher.search(query,10000).
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		TopDocs tdMerged = null;
		tdMerged = td2;
		/*
		float lambda = (float) 0.9;
		boolean isNormalize = false;
		tdMerged = weightedMergeTopdoc(td1,td2,lambda,isNormalize);
		
		// 0.4 = 1428, 0.2 = 1402, 0.8=
		float lambda2 = (float) 0.4;
		TopDocs tdMerged2 = weightedMergeTopdoc(td3,td4,lambda2,false);
		//TopDocs tdMerged = tdMerged2;
		
		float lambda3 = (float)0.8;
		tdMerged = weightedMergeTopdoc(tdMerged,tdMerged2,lambda3,false);
		*/
		
		// parse through the returned documents
		//tdMerged = td6;
		return tdMerged;

	}
	
	static  void normalizeScore(TopDocs td) {
		double sum = 0;
		for(int i=0;i<td.scoreDocs.length;i++) {
			
			sum += td.scoreDocs[i].score;
			
		}
		for(int i=0;i<td.scoreDocs.length;i++) {
			td.scoreDocs[i].score/=sum;
		}
	}
	/*
	 * Merge two topdocs with scores changed as 
	 *  t1[i].score =*lambda and t2[i].score=*(1-lambda)
	 * then merge two lists and rerank
	 * during merging if any one of them appears in both list, then they are added
	 * 
	 */
	public static TopDocs weightedMergeTopdoc(TopDocs t1, TopDocs t2, 
			float lambda, boolean isNormalize) {
		if(t2==null)
			return t1;
		// map of docid, ScoreDoc
		HashMap<Integer, ScoreDoc> maptd= new HashMap<Integer,ScoreDoc>();
		
		if(isNormalize) {
			normalizeScore(t1);
			normalizeScore(t2);
		}
		
		for(int i=0;i<t1.scoreDocs.length;i++) {
			
			// put every scoredoc in map put a map entry
			int luceneDocID = t1.scoreDocs[i].doc;
			ScoreDoc ptr = t1.scoreDocs[i];
			float score = ptr.score;
			
			maptd.put(luceneDocID, new ScoreDoc(luceneDocID,score*lambda));
			
		}
		for(int i=0;i<t2.scoreDocs.length;i++) {
			
			// put every scoredoc in map put a map entry
			int luceneDocID = t2.scoreDocs[i].doc;
			ScoreDoc ptr = t2.scoreDocs[i];
			float score = ptr.score;
			
			// if the score for the document doesnt exist put to map
			if(!maptd.containsKey(luceneDocID))
				maptd.put(luceneDocID, new ScoreDoc(luceneDocID,score*(1-lambda) ));
			else { // update the score
				ScoreDoc sameEntry = maptd.get(luceneDocID);
				sameEntry.score += score*(1-lambda);
			}
				
			
		}
		
		// put the map into a sorted ScoreDoc[]
		ScoreDoc[] sd = new ScoreDoc[maptd.size()];
		int  i = 0;
		for(Entry<Integer, ScoreDoc> e : maptd.entrySet()) {
			sd[i] = e.getValue();
			i++;
		}
		
		Arrays.sort(sd, new Comparator <ScoreDoc>() {
			
			public int compare(ScoreDoc s1,ScoreDoc s2) {
				return -Float.compare(s1.score, s2.score);
			}
		});
		
		TopDocs mergedTD = new TopDocs(new TotalHits(sd.length, TotalHits.Relation.EQUAL_TO),sd);
		//printTopDocs(mergedTD);
		return mergedTD;
		
		
	}
	
	static void printTopDocs(TopDocs td) {
		System.out.println("-------------------------------------");
		for(int i=0;i<td.scoreDocs.length;i++) {
			System.out.println(td.scoreDocs[i].doc+" : "+td.scoreDocs[i].score);
		}
		System.out.println("-------------------------------------");
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
/*	
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
*/
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

	
	public static List<Tuple> getResultsWrapper(Report r, IndexSearcher isearcher, DirectoryReader ireader, ReportToVectors rvs) {
		TopDocs td = null;
		if(rvs!=null) {
			List<ReportVector> similar = rvs.computeNNs(r.id);
			
			td = ReportToVectors.makeTopDocsFromReportVectors(similar,isearcher);
		}
		
		TopDocs tdMerged = null;
		
		tdMerged = td; // for only vector
		
		//TopDocs tdquery = getResultsTopTerms(r, isearcher, ireader);
		//tdMerged = tdquery;
		//TopDocs tdMerged = getResultsTopTerms(r, isearcher, ireader);
		
		//tdMerged = weightedMergeTopdoc(tdquery, td, (float) 0.6, true);
		ScoreDoc[] hits = null;
		hits = tdMerged.scoreDocs;
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
				
				// collection may contain duplicate of the original article
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
					
					/* ignore short length stories
					String content = isearcher.doc(hits[i].doc).get("content");
					String[] words = content.split(" ");
					if(words.length<200)
						continue;
					*/
					
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

}
