package trec;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.PostingsEnum;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

public class DumpForWord2VecFromIndex {
	
	class TermPosition {
	    String  term;
	    int     position;

	    public TermPosition(String term, int position) {
	        this.term = term;
	        this.position = position;
	    }
	    
	}
	

	    //Properties  prop;               // prop of the init.properties file
	    File        indexFile;          // place where the index will be stored
	    boolean     boolIndexExists;    // boolean flag to indicate whether the index exists or not
	    int         docIndexedCounter;  // document indexed counter
	    boolean     boolDumpIndex;      // true if want ot dump the entire collection
	    String      dumpPath;           // path of the file in which the dumping to be done
	    String      field;              // field to dump from each document

	    static final public String FIELD_ID = "docid";
	    static final public String FIELD_BOW = "content";       // ANALYZED bow content
	    static final public String FIELD_RAW = "raw-content";   // raw, UNANALYZED content

	    private DumpForWord2VecFromIndex(String propFile) throws Exception {

	        
	        //----- Properties file loaded

	        /* index path setting */
	        Directory indexDir = FSDirectory.open(Paths.get(Constants.INDEX_PATH));

	        if (DirectoryReader.indexExists(indexDir)) {
	            System.out.println("Index exists in "+indexFile.getAbsolutePath());
	            boolIndexExists = true;
	            dumpPath = Constants.DUMP_FILE;
	            if(dumpPath == null) {
	                System.err.println("Error: dumpPath missing in prop file\n");
	                System.exit(1);
	            }
	        }
	        else {
	            System.err.println("Error: Index does not exists.\n");
	            boolIndexExists = true;
	        }
	        /* index path set */
	        field = "content";//prop.getProperty("field");
	        if(null == field) {
	            System.err.println("Error: Fields to dump missing in prop");
	            System.exit(1);
	        }
	    }

	    private DumpForWord2VecFromIndex(String indexPath, String dumpPath, String field) throws Exception {

	        /* index path setting */
	        indexFile = new File(indexPath);
	        Directory indexDir = FSDirectory.open(indexFile.toPath());

	        if (DirectoryReader.indexExists(indexDir)) {
	            System.out.println("Index exists in "+indexFile.getAbsolutePath());
	            boolIndexExists = true;
	            this.dumpPath = dumpPath;
	            if(dumpPath == null) {
	                System.err.println("Error: dumpPath missing in prop file\n");
	                System.exit(1);
	            }
	        }
	        else {
	            System.err.println("Error: Index does not exists.\n");
	            boolIndexExists = true;
	        }
	        /* index path set */
	        this.field = field;
	    }

	    private void dumpIndexUnanalyzed() {

	        System.out.println("Dumping unanalyzed index in: "+ dumpPath);

	        try (IndexReader reader = DirectoryReader.open(FSDirectory.open(indexFile.toPath()))) {
	            PrintWriter pout = new PrintWriter(dumpPath);
	            int maxDoc = reader.maxDoc();
	            for (int i = 0; i < maxDoc; i++) {
	                Document d = reader.document(i);
	                //System.out.print(d.get(FIELD_BAG_OF_WORDS) + " ");
	                pout.print(d.get(field) + " ");
	                pout.print("\n");
	            }
	            System.out.println("Index dumped in: " + dumpPath);
	            pout.close();
	        }
	        catch(IOException e) {
	            System.err.println("Error: indexFile reading error");
	            e.printStackTrace();
	        }
	    }

	    /**
	     * Dumps the analyzed index in the specified path
	     */
	    private void dumpIndex() {

	        System.out.println("Dumping the index in: "+ dumpPath);

	        try (IndexReader reader = DirectoryReader.open(FSDirectory.open(indexFile.toPath()))) {
	            FileWriter dumpFW = new FileWriter(dumpPath);
	            int maxDoc = reader.maxDoc();
//	            String docContent[] = new String[maxDocLength];
	            ArrayList<TermPosition> docContent;

	            for (int i = 0; i < maxDoc; i++) {
	                docContent = new ArrayList<>();
	                Document d = reader.document(i);
	                System.out.println(i+": <"+d.get("id")+">");
	                //System.out.print(d.get(FIELD_BOW) + " ");
	                Terms vector = reader.getTermVector(i, field);
	                if(vector==null) {
	                	continue;
	                }
	                TermsEnum termsEnum = null;
	                termsEnum = vector.iterator();
	                BytesRef text;
	                PostingsEnum docsPosEnum = null;
	                while ((text = termsEnum.next()) != null) {
	                    String term = text.utf8ToString();
	                    //System.out.print(term+": ");
	                    //PostingsEnum docsPosEnum = termsEnum.postings(null, null, DocsAndPositionsEnum.FLAG_FREQS); 
	                    docsPosEnum = termsEnum.postings(docsPosEnum, PostingsEnum.FREQS); 
	                    
	                    docsPosEnum.nextDoc();

	                    int freq=docsPosEnum.freq();
	                    for(int k=0; k<freq; k++){
	                        int position=docsPosEnum.nextPosition();
	                        //System.out.print(position+" ");
	                        docContent.add(new TermPosition(term, position));
	                    }
	                    //System.out.println("");
	                }
	                //Collections.sort(docContent, (TermPosition t1, TermPosition t2) -> t1.position - t2.position);
	                Collections.sort (docContent, new Comparator<TermPosition>(){
	                    @Override
	                    public int compare(TermPosition r1, TermPosition r2){
	                        return r1.position - r2.position;
	                    }}
	                );
	                for (TermPosition termPos : docContent) {
//	                    System.out.print(docContent.get(j).term+" ");
	                    dumpFW.write(termPos.term + " ");
	                }
	                dumpFW.write("\n");
	                //System.out.println("");
	            }

	            System.out.println("Index dumped in: " + dumpPath);
	            dumpFW.close();
	        } catch(IOException e) {
	            System.err.println("Error: indexFile reading error");
	            e.printStackTrace();
	        }
	    }

	    

	
	public static void main(String[] args) throws Exception {
		DumpForWord2VecFromIndex dumpIndex = new DumpForWord2VecFromIndex(Constants.INDEX_PATH, Constants.DUMP_FILE_CONTENT_2020, "content");
        dumpIndex.dumpIndex();
    	
    }
}
