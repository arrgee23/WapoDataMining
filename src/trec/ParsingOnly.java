package trec;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;


public class ParsingOnly {
	public static final int TOTAL_DOC_NO = 595037;//608180;
	public static final String INDEX_PATH = "/home/arrgee/Documents/index";
	
	// generic deapth first traversal
	public static void traverse(JsonNode root){
    
		if(root.isObject()){
			Iterator<String> fieldNames = root.fieldNames();
			System.out.println("object");
			while(fieldNames.hasNext()) {
				String fieldName = fieldNames.next();
				JsonNode fieldValue = root.get(fieldName);
				traverse(fieldValue);
			}
		} else if(root.isArray()){
			System.out.println("array");
			ArrayNode arrayNode = (ArrayNode) root;
			for(int i = 0; i < arrayNode.size(); i++) {
				JsonNode arrayElement = arrayNode.get(i);
				traverse(arrayElement);
			}
		} else {
			// JsonNode root represents a single value field - do something with it.
			System.out.println(root.asText());
			
		}
	}
	
	
	static Report readOneReport2(JsonParser parser) {
		ObjectMapper mapper = new ObjectMapper();
		//JsonParser parser = mapper.getFactory().createParser(new File(...));
		JsonToken tkn = null;
		try {
			tkn = parser.nextToken();
			if(tkn==null) // end of document
			{
				Report r = new Report();
				r.setId("-1");
				return r;
			}
			if(tkn != JsonToken.START_OBJECT) {
			  throw new IllegalStateException("Expected start object");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		JsonNode node = null;  
		try {
			node = mapper.readTree(parser);
			//traverse(node);
			
			//JsonNode n = node.get("id");
			//System.out.println(n.textValue());

			JsonNode id,url,title,date,source,type,author;

			id = node.get("id");
			url = node.get("article_url");
			title = node.get("title");
			date = node.get("published_date");
			source = node.get("source");
			type = node.get("type");
			author = node.get("author");
			// read the contents array
			ArrayNode contentsArr = (ArrayNode)node.get("contents");

			if(id.isNull() || id==null )
				return null;
			else
			{
				String idstr=id.asText(),urlstr=url.asText(),titlestr=title.asText(),
				datestr=date.asText(),authorstr=author.asText(),typestr=type.asText(),sourcestr=source.asText();

				String str = "";
				
				for(int i=5;i<contentsArr.size();i++){
					JsonNode elem = contentsArr.get(i);
					if(elem!=null){
						JsonNode n = elem.get("content");
						if(n!=null)
							str+=n.textValue();
						}
				}
				//System.out.println(str);
				//System.out.println("");

				if(idstr.equals("") || (titlestr.equals("") && str.equals("")))
					return null;

				Report r = new Report();

				r.setArticleType(typestr);
				r.setAuthor(authorstr);
				r.setContent(str);
				r.setDate(datestr);
				r.setId(idstr);
				r.setSource(sourcestr);
				r.setTitle(titlestr);
				r.setUrl(urlstr);

				return r;

			}


			
			//System.out.println(node.get("contents").textValue());
			
				
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		  // do whatever you need to do with this object
		//}

		//parser.close();
		  return null;
	}
	static Report readOneReport(JsonParser jp) //throws Exception {
	{
		Report r = new Report();

		JsonToken current=null;
		try {
			current = jp.nextToken();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (current == null) { // end of file
			return null;
		}

		if (current != JsonToken.START_OBJECT) {
			System.out.println("Json structurre error in reading list");
			return null;

		}

		try {
			JsonToken temp = jp.nextToken();
			while (temp != JsonToken.END_OBJECT && temp!=null)
			{//&& jp.nextToken() != null) {
				String fieldname = jp.getCurrentName();
				// String id=null,url=null,title=null,author=null,date = null;
				if ("id".equals(fieldname)) {
					temp = jp.nextToken();
					r.setId(jp.getText());
				}

				if ("article_url".equals(fieldname)) {
					temp = jp.nextToken();
					r.setUrl(jp.getText());
				}

				if ("title".equals(fieldname)) {
					temp = jp.nextToken();
					r.setTitle(jp.getText());
				}

				if ("author".equals(fieldname)) {
					temp = jp.nextToken();
					r.setAuthor(jp.getText());
				}

				if ("published_date".equals(fieldname)) {
					temp = jp.nextToken();
					r.setDate(jp.getText());
				}

				// System.out.printf("%s\n %s\n %s\n %s\n %s\n",id,url,title,author,date);
				int count = 0;
				String all = "";
				if ("contents".equals(fieldname)) {
					current = jp.nextToken();
					temp = current;
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
					temp = jp.nextToken();
					r.setArticleType(jp.getText());
				}
				if ("source".equals(fieldname)) {
					temp = jp.nextToken();
					r.setSource(jp.getText());
				}
				temp = jp.nextToken();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return r;
	}

	@SuppressWarnings("deprecation")
	public static void main2(String[] args) // throws Exception {
	{
		String filename = "wapo"; // comment if not using wapo dataset
		// String filename = "one.json"; // uncomment to run without wapo dataset

		JsonFactory f = new MappingJsonFactory();
		JsonParser jp = null;
		try {
			jp = f.createJsonParser(new File(filename));
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Report r = null;
		int i=0;
		long missingCount = 0;
		long count = 0;

		for (i = 0; i < TOTAL_DOC_NO; i++) {

			// parse a document from json and store in index
			r = readOneReport2(jp);

			System.out.print(i+1);
			if(r!=null){
				System.out.println(" "+r.id);
				count++;
			}
			else{
				System.out.println();
				missingCount++;
			}
		}
		System.out.println(count+"   "+missingCount);

	}
}