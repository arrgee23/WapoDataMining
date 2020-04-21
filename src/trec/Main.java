package trec;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Main {
    
    
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
        
        
        
        PrintWriter output = null;
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");  
			LocalDateTime now = LocalDateTime.now();  
			//System.out.println(dtf.format(now));  
			String outName = Constants.OUTFILE_NAME+dtf.format(now)+".txt";
			System.out.println(outName);
			
			File myObj = new File(outName);
		    
			if (!myObj.createNewFile()) {
		    //    System.out.println("File created: " + myObj.getName());
				assert(true);
			} 
		    //else {
		    //   
		    //}
		      
			output = new PrintWriter(myObj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int cnt = 0;
        for(Topic t : queryIdList) {
        	// search for the doc with id to get query document
        	//System.out.println(t.docid);
        	Integer id = new Integer(0);
        	Report r = LuceneQuery.getReportWithID(t.docid, isearcher,id);
        	
        	System.out.println(++cnt+". Executing report: "+r.getId());
        	// for each topic retrieve 100 documents
        	List<Tuple> answers = LuceneQuery.getResultsTopTerms(r, isearcher,ireader);
        	
        	
        	//answers = Rerank.rerankWithCategory(r,answers);
        	
        	int count = 0;
        	System.out.println("Returned: "+answers.size());
        	for(Tuple answer : answers) {
        		if(answer!=null) {
	        		//System.out.printf("%d Q0 %s %d %.1f %s\n",extractTopicNo(t),answer.docId,count,answer.score,answer.codename);
	        		output.printf("%d Q0 %s %d %f %s\n",extractTopicNo(t),answer.docId,count+1,answer.score,answer.codename);
	        		output.flush();
	        		count++;
        		}
        	}
        	
        	
        }
        
    }

	private static int extractTopicNo(Topic t) {
		String n = t.number.substring(9, t.number.length()-1);
		return Integer.parseInt(n);
		
	}
}
