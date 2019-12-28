package jsonparsing;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.MappingJsonFactory;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.apache.lucene.index.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class ParseDemo {
    public static final int TOTAL_DOC_NO = 10000;

    static Report readOneReport(JsonParser jp) throws Exception {

        Report r = new Report();

        JsonToken current;
        current = jp.nextToken();

        if (current == null) { // end of file
            return null;
        }

        if (current != JsonToken.START_OBJECT) {
            System.out.println("Json structurre error in reading list");
            return null;

        }

        while (jp.nextToken() != JsonToken.END_OBJECT) {
            String fieldname = jp.getCurrentName();
            // String id=null,url=null,title=null,author=null,date = null;
            if ("id".equals(fieldname)) {
                jp.nextToken();
                r.setId(jp.getText());
            }

            if ("article_url".equals(fieldname)) {
                jp.nextToken();
                r.setUrl(jp.getText());
            }

            if ("title".equals(fieldname)) {
                jp.nextToken();
                r.setTitle(jp.getText());
            }

            if ("author".equals(fieldname)) {
                jp.nextToken();
                r.setAuthor(jp.getText());
            }

            if ("published_date".equals(fieldname)) {
                jp.nextToken();
                r.setDate(jp.getText());
            }

            // System.out.printf("%s\n %s\n %s\n %s\n %s\n",id,url,title,author,date);
            int count = 0;
            String all = "";
            if ("contents".equals(fieldname)) {
                current = jp.nextToken();

                while (current != JsonToken.END_ARRAY) {

                    if (current == JsonToken.FIELD_NAME && jp.getCurrentName().equals("content")) {
                        current = jp.nextToken();
                        count++;
                        if (count > 4) {
                            String str = jp.getText();

                            if (str != null)
                                all += str;
                        }
                    }
                    // System.out.println(jp.getText());
                    else
                        current = jp.nextToken();
                }
                // System.out.println(all);
                r.setContent(all);
            }
            if ("type".equals(fieldname)) {
                jp.nextToken();
                r.setArticleType(jp.getText());
            }
            if ("source".equals(fieldname)) {
                jp.nextToken();
                r.setSource(jp.getText());
            }
        }

        return r;
    }

    static void indexOneReport(Document d, Report r) {

    }

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws Exception {
        String filename = "wapo"; // comment if not using wapo dataset
        // String filename = "one.json"; // uncomment to run without wapo dataset
        
        JsonFactory f = new MappingJsonFactory();
        JsonParser jp = f.createJsonParser(new File(filename));
        // JsonParser j = f.createParser(null);

        // JsonToken current;
        Report r = null;
        Scanner s = new Scanner(System.in);

        /*
         * do{ int i = s.nextInt(); if(i!=1) break; r = readOneReport(jp); if(r!=null)
         * System.out.println(r.content); }while(r!=null);
         */

        /* Index the documents */

        Analyzer analyzer = new StandardAnalyzer();

        Path indexPath = Files.createTempDirectory("index");
        Directory directory = FSDirectory.open(indexPath);
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter iwriter = new IndexWriter(directory, config);

        Document[] docArr = new Document[TOTAL_DOC_NO];
        for (int i = 0; i < TOTAL_DOC_NO; i++) {
            docArr[i] = new Document();

            // parse a document from json and store in index
            r = readOneReport(jp);

            // Add fields to the document

            // StringField.TYPE_STORED store the content as a single token
            Field id = new Field("id", r.id, StringField.TYPE_STORED);
            docArr[i].add(id);

            Field articleType = new Field("articleType", r.articleType, StringField.TYPE_STORED);
            docArr[i].add(articleType);

            Field author = new Field("author", r.author, StringField.TYPE_STORED);
            docArr[i].add(author);

            // TextField.TYPE_STORED tokenize the strings
            Field content = new Field("content", r.content, TextField.TYPE_STORED);
            docArr[i].add(content);

            Field date = new Field("date", r.date, StringField.TYPE_STORED);
            docArr[i].add(date);

            Field source = new Field("source", r.source, StringField.TYPE_STORED);
            docArr[i].add(source);

            Field title = new Field("title", r.title, TextField.TYPE_STORED);
            docArr[i].add(title);

            Field url = new Field("url", r.url, StringField.TYPE_STORED);
            docArr[i].add(url);

            // add the document to index
            iwriter.addDocument(docArr[i]);
        }
        iwriter.close(); // after adding docs close the indexwriter

        /* now searching part */
        // Now search the index:
        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(ireader);
        // Parse a simple query that searches for "text":
        QueryParser parser = new QueryParser("content", analyzer);

        while(true)
        {
            System.out.print("Enter the string you want to search: ");
            String searchQuery = s.next();
            Query query = parser.parse(searchQuery);

            ScoreDoc[] hits = isearcher.search(query, 10).scoreDocs;
            //assertEquals(1, hits.length);

            if(hits.length<1)
            {
                System.out.println("Your query did not return any result...");
                continue;
            }
            // Iterate through the results:
            for (int i = 0; i < hits.length; i++) 
            {
                Document hitDoc = isearcher.doc(hits[i].doc);
                System.out.println(Integer.toString(i+1)+" - "+hitDoc.get("title"));
                
                //System.out.println(hitDoc.get("content"));
                
            }
            System.out.println("\n");
            System.out.print("Enter the document no you want to read: ");
            int choice = s.nextInt();

            Document doc = isearcher.doc(hits[choice-1].doc);
            System.out.println(doc.get("content"));
            System.out.println("\n");
        }
        //ireader.close();
        //directory.close();

        //s.close();
    }
}
