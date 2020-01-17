package trec;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Main {
   
    @Deprecated
    
    
    public static void main(String[] args) // throws Exception {
    {
        //IndexFiles.createWapoIndex();
    	
    	
        //LuceneQuery.searchInterface();
        
    	// get all topics from file
        ArrayList <Topic> queryIdList = TopicParser.getAllTopics(Constants.TOPIC_XML_SOURCE);
        
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
        
        for(Topic t : queryIdList) {
        	// search for the doc with id to get query document
        	System.out.println(t.docid);
        	Report r = LuceneQuery.getReportWithID(t.docid, isearcher);
        	
        	// for each topic retrieve 100 documents
        	List<Tuple> answers = LuceneQuery.getResultsBaseline(r, isearcher);
        	
        	int count = 0;
        	for(Tuple answer : answers) {
        		System.out.printf("%d Q0 %s %d %.1f %s",extractTopicNo(t),answer.docId,count,answer.score,answer.codename);
        		count++;
        	}
        	
        	System.out.println("report: "+r.getId());
        }
        
    }

	private static int extractTopicNo(Topic t) {
		return Integer.parseInt(t.number.substring(9));
		
	}
}
