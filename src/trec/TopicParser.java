package trec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
 
public class TopicParser {
	
    public static ArrayList<Topic> getAllTopics(String file) {
    	ArrayList<Topic> topicList = new ArrayList<Topic>();
    	
    	SAXBuilder jdomBuilder = new SAXBuilder();
    	 
        // jdomDocument is the JDOM2 Object
        Document jdomDocument;
		try {
			jdomDocument = jdomBuilder.build(file);
			//jdomDocument.get
			
			Element root = jdomDocument.getRootElement();
			List<Element> list = root.getChildren("top");
	        
	        for(Element e:list) {
	        	Topic t = new Topic(e.getChildText("num"),  e.getChildText("URL"),e.getChildText("docid"));
	        	topicList.add(t);
	        	//System.out.println(t.docid);
	        	
	        }
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//for(Topic t: topicList) {
    	//	System.out.println(t.docid);
    	//}
    	return topicList;     
    }
    
    // parsing xml is hard
    public static void main(String[] args) throws JDOMException, IOException {
        
    	List <Topic> lt = getAllTopics(Constants.TOPIC_XML_SOURCE);
    	for(Topic t: lt) {
    		System.out.println(t.docid);
    	}
    	System.out.println(lt.size());
    }
    
}

