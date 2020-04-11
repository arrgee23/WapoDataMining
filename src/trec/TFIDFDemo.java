package trec;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

public class TFIDFDemo {

	public static void tfIdfScores(int docId,IndexReader reader) {
		try {
			Terms t = reader.getTermVector(docId, "content");
			TermsEnum termsEnum = t.iterator();
		    int numTerms = 0;
		    BytesRef a = null;
		    while((a = termsEnum.next()) !=null)
			{
		    	System.out.println(a.utf8ToString());
		    	numTerms++;
		    }
			System.out.println(numTerms);
			
			System.out.println(t.getStats());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
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
        
        tfIdfScores(1, ireader);
	}

}
