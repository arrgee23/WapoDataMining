package jsonparsing;


import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;


public class ParsingOnly {
	public static final int TOTAL_DOC_NO = 10;// 0; //608180;
	public static final String INDEX_PATH = "/home/arrgee/Documents/index";
	
	
	static Report readOneReport2(JsonParser parser) {
		ObjectMapper mapper = new ObjectMapper();
		//JsonParser parser = mapper.getFactory().createParser(new File(...));
		try {
			if(parser.nextToken() != JsonToken.START_OBJECT) {
			  throw new IllegalStateException("Expected an array");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//while(parser.nextToken() == JsonToken.START_OBJECT) {
		  // read everything from this START_OBJECT to the matching END_OBJECT
		  // and return it as a tree model ObjectNode
		ObjectNode node = null;  
		try {
			node = mapper.readTree(parser);
			//JsonNode n = node.get("id");
			//System.out.println(n.textValue());
			
			System.out.println(node.get("content").textValue());
				
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
	public static void main(String[] args) // throws Exception {
	{
		String filename = "wapo"; // comment if not using wapo dataset
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
		int i=0;

		for (i = 0; i < TOTAL_DOC_NO; i++) {

			// parse a document from json and store in index
			r = readOneReport2(jp);

			System.out.print(i+1);
			if(r!=null)
				System.out.println(" "+r.id);
			else
				System.out.println();
		}

	}
}