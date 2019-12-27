package jsonparsing;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.MappingJsonFactory;


//import org.codehaus.jackson.*;

import java.io.File;
import java.util.Scanner;
public class ParseDemo {
    /*
    LinkedList <Report> reports readAllReports(JsonParser jp) throws Exception{
        JsonToken current;
        current = jp.nextToken();
        LinkedList <Report> reports = new LinkedList<Report>();
        if (current != JsonToken.START_OBJECT) {
            System.out.println("Error: root should be object: quiting.");
            return null;
        }
        return null;
    }
    */
    static Report readOneReport(JsonParser jp) throws Exception{
        Report r = new Report();

        JsonToken current;
        current = jp.nextToken();

        if(current == null){ // end of file
            return null;
        }

        if (current != JsonToken.START_OBJECT) {
            System.out.println("Json structurre error in reading list");
            return null;
     
        }

        while (jp.nextToken() != JsonToken.END_OBJECT) {
            String fieldname = jp.getCurrentName();
            //String id=null,url=null,title=null,author=null,date = null;
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
         
            //System.out.printf("%s\n %s\n %s\n %s\n %s\n",id,url,title,author,date);
            int count = 0;
            String all = "";
            if ("contents".equals(fieldname)) {
                current = jp.nextToken();
                
                while (current != JsonToken.END_ARRAY) {

                    if(current == JsonToken.FIELD_NAME && jp.getCurrentName().equals("content")){
                        current = jp.nextToken();
                        count++;
                        if(count>4){
                            String str = jp.getText();
                            
                            if(str!= null)
                                all += str;
                        }
                    }
                    //System.out.println(jp.getText());
                    else
                        current = jp.nextToken();
                }
                //System.out.println(all);
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

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws Exception {
		String filename = "wapo"; // comment if not using wapo dataset
		// String filename = "one.json"; // uncomment to run without wapo dataset
        JsonFactory f = new MappingJsonFactory();
        JsonParser jp = f.createJsonParser(new File(filename));
        //JsonParser j = f.createParser(null);
        
        //JsonToken current;
        Report r = null;
        Scanner s = new Scanner(System.in);
        
        do{
            int i = s.nextInt();
            if(i!=1)
                break;
            r = readOneReport(jp);
            if(r!=null)
            	System.out.println(r.title);
        }while(r!=null);
        s.close();
    }
}
