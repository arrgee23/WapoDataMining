package trec;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
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
			System.out.println("Your query did not return any result...");
			assert(false);
		}
		
		Document hitDoc = null;
		try {
			hitDoc = isearcher.doc(hits[0].doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(Integer.toString(i + 1) + " - " + hitDoc.get("title"));
		Report r = new Report(hitDoc.get("id"),
				hitDoc.get("url"),
				hitDoc.get("title"),
				hitDoc.get("author"),
				hitDoc.get("date"),
				hitDoc.get("source"),
				hitDoc.get("articleType"),
				hitDoc.get("content")
				);
		// System.out.println(hitDoc.get("content"));
		return r;
	}


	public static void searchInterface(){
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
            Query query=null;
            try {
                query = parser.parse(searchQuery);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            ScoreDoc[] hits=null;
            try {
                hits = isearcher.search(query, 10).scoreDocs;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //assertEquals(1, hits.length);

            if(hits.length<1)
            {
                System.out.println("Your query did not return any result...");
                continue;
            }
            // Iterate through the results:
            for (int i = 0; i < hits.length; i++) 
            {
                Document hitDoc=null;
                try {
                    hitDoc = isearcher.doc(hits[i].doc);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(Integer.toString(i+1)+" - "+hitDoc.get("title"));
                
                //System.out.println(hitDoc.get("content"));
                
            }
            System.out.println("\n");
            System.out.print("Enter the document no you want to read: ");
            int choice = Integer.parseInt(s.nextLine());
            if(choice>0 && choice<=hits.length )
            {
                Document doc=null;
                try {
                    doc = isearcher.doc(hits[choice - 1].doc);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(doc.get("content"));
                System.out.println("\n");
            }else {
                System.out.println("Invalid input");
                System.out.println("\n");
            }
        }
    }


	
	public static List<Tuple> getResultsBaseline(Report r, IndexSearcher isearcher) {
		
		return null;
	}


}
