package jsonparsing;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.MappingJsonFactory;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
//import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.*;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.apache.lucene.index.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import jsonparsing.*;

public class Main {
    // 594902 indexed (id not null or content title both not null)
    public static final int TOTAL_DOC_NO = 595037;
    public static final String INDEX_PATH = "/home/arrgee/Documents/index";
    public static final String filename = "wapo";
    @SuppressWarnings("deprecation")
    public static void main2(String[] args){
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
               System.out.println(++count);
            }
            System.out.println(line);
        }catch(Exception e){
            
        }
    }
    public static void main(String[] args) // throws Exception {
    {
        //"/home/arrgee/Documents/WashingtonPost.v2/data/foo"; // comment if not using wapo dataset
        // String filename = "one.json"; // uncomment to run without wapo dataset

        JsonFactory f = new MappingJsonFactory();
        JsonParser jp = null;
        try {
            jp = f.createJsonParser(new File(filename));
        } catch (JsonParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Report r = null;

        Scanner s = new Scanner(System.in);

        /* Index the documents */
        String indexPath = INDEX_PATH;
        /// String docsPath = null;
        boolean append = false;
        boolean createindex = false; // re index all

        Directory directory = null;
        try {
            directory = FSDirectory.open(Paths.get(indexPath));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Analyzer analyzer = new StandardAnalyzer(); // ..standard english word tokenization

        if (createindex) {
            try {
                System.out.println("Indexing to directory '" + indexPath + "'...");

                // Analyzer analyzer = new StandardAnalyzer();
                IndexWriterConfig config = new IndexWriterConfig(analyzer);
                if (!append) {
                    // Create a new index in the directory, removing any
                    // previously indexed documents:
                    config.setOpenMode(OpenMode.CREATE);
                } else {
                    // Add new documents to an existing index:
                    config.setOpenMode(OpenMode.CREATE_OR_APPEND);
                }
                IndexWriter iwriter = new IndexWriter(directory, config);
                // iwriter.

                // Optional: for better indexing performance, if you
                // are indexing many documents, increase the RAM
                // buffer. But if you do this, increase the max heap
                // size to the JVM (eg add -Xmx512m or -Xmx1g):
                //
                // config.setRAMBufferSizeMB(256.0);

                // read one entry from json and add it to index
                Document d = null;
                int i = 0;
                int total = 0;
                for (i = 0; i < TOTAL_DOC_NO; i++) {
                    d = new Document();
                    //System.out.println("Indexing doc: " + ++i);
                    // parse a document from json and store in index
                    r = ParsingOnly.readOneReport2(jp);
                    
                    
                    /*
                    if(i<585037)
                        continue;
                    */
                    if (r == null)
                        continue;// Add fields to the document

                    //if(r.id.equals("-1"))
                    //    break;

                    // StringField.TYPE_STORED store the content as a single token
                    if(r.id!=null) {
                    	Field id = new Field("id", r.id, StringField.TYPE_STORED);
                    	d.add(id);
                    }else
                    	continue;
                    
                    if(r.articleType!=null) {
                    	Field articleType = new Field("articleType", r.articleType, StringField.TYPE_STORED);
                    	d.add(articleType);
                    }else {
                    	Field articleType = new Field("articleType", "", StringField.TYPE_STORED);
                    	d.add(articleType);
                    }
                    
                    if(r.author!=null) {
                    	Field author = new Field("author", r.author, StringField.TYPE_STORED);
                    	d.add(author);
                    }else {
                    	Field author = new Field("author", "", StringField.TYPE_STORED);
                    	d.add(author);
                    }
                    
                    // TextField.TYPE_STORED tokenize the strings

                    // this for making vectors
                    FieldType ft = new FieldType(TextField.TYPE_STORED);
                    ft.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
                    ft.setStoreTermVectors(true);
                    ft.setStoreTermVectorOffsets(true);
                    ft.setStoreTermVectorPositions(true);
                    ft.setStoreTermVectorPayloads(true);
                    ft.setStored(true);
                    
                    if(r.content!=null) {
                        
                        Field content = new Field("content", r.content,ft);
                        //content.term
	                    d.add(content);
                    }else {
                    	Field content = new Field("content", "", ft);
                    	d.add(content);
                    }
                    
                    if(r.date!=null) {
                        Field date = new Field("date", r.date, StringField.TYPE_STORED);
                        //date.
                    	d.add(date);
                    }else{
                        Field date = new Field("date", "", StringField.TYPE_STORED);
                        //date.
                    	d.add(date);
                    }

                    if(r.source!=null) {
                        Field source = new Field("source", r.source, StringField.TYPE_STORED);
                        //Field f = new Field
                        d.add(source);
                    }else{
                        Field source = new Field("source", "", StringField.TYPE_STORED);
                        //Field f = new Field
                        d.add(source);
                    }

                    
                    if(r.title!=null) {
                       
                        Field title = new Field("title", r.title,ft);
                        //Field title = new Field("title", r.title, TextField.TYPE_STORED,Field.TermVector.YES);
                        d.add(title);
                    }else {
                    	Field title = new Field("title","",ft);
                        //Field title = new Field("title", r.title, TextField.TYPE_STORED,Field.TermVector.YES);
                        d.add(title);
                    }


                    if(r.url!=null) {
                        Field url = new Field("url", r.url, StringField.TYPE_STORED);
                        d.add(url);
                    }
                    else{
                        Field url = new Field("url", "", StringField.TYPE_STORED);
                        d.add(url);
                    }
                    // add the document to index
                    iwriter.addDocument(d);

                    total++;

                    if (i % 1000 == 0) {
                        System.out.printf("Indexed %d documents...\n", i + 1);
                    }
                }
                
                //System.out.printf("Indexed total %d documents...\n", i + 1);

                // NOTE: if you want to maximize search performance,
                // you can optionally call forceMerge here. This can be
                // a terribly costly operation, so generally it's only
                // worth it when your index is relatively static (ie
                // you're done adding documents to it):
                //
                // writer.forceMerge(1);

                iwriter.close(); // after adding docs close the indexwriter
                System.out.printf("loop %d...\n", i );
                System.out.printf("Total %d documents...\n", total);

            } catch (Exception e) {
                System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
            }

        }

        else
            System.out.println("Index already in directory '" + indexPath + "'...");
        /* now searching part */

        // Now search the index:
        DirectoryReader ireader = null;
        try {
            ireader = DirectoryReader.open(directory);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        IndexSearcher isearcher = new IndexSearcher(ireader);
        // Parse a simple query that searches for "text":
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
        //ireader.close();
        //directory.close();

        //s.close();
    }
}
