package trec;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexExploration {

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
        
        int mid=0,mauthor=0,murl=0,mtitle=0,mcontent=0,mdate=0,marticleType=0,msource=0;
		for (int i=0; i<ireader.maxDoc(); i++) {
			
			//if (ireader.is)
				//continue;

			Document doc;
			try {
				doc = ireader.document(i);
				if(mid< doc.get("id").length())
					mid = doc.get("id").length();
				
				if(murl <  doc.get("url").length())
					murl = doc.get("url").length();
				
				if(mtitle < doc.get("title").length())
					mtitle = doc.get("title").length();
				
				if(mauthor< doc.get("author").length())
					mauthor = doc.get("author").length();
				
				if(mdate <  doc.get("date").length())
					mdate = doc.get("date").length();
				
				if(msource < doc.get("source").length())
					msource = doc.get("source").length();
				
				if(marticleType< doc.get("articleType").length())
					marticleType = doc.get("articleType").length();
				
				if(mcontent <  doc.get("content").length())
					mcontent = doc.get("content").length();
				
				if(mtitle < doc.get("title").length())
					mtitle = doc.get("title").length();
				String articleType = doc.get("articleType");
				String content = doc.get("content");
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			

			// do something with docId here...
		}

	}

}
