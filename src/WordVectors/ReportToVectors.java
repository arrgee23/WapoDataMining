package WordVectors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import trec.Constants;
import trec.Report;
import trec.Topic;
import trec.TopicParser;
public class ReportToVectors {
	public HashMap<String, ReportVector> reportVecMap;
	public int k;

	/**
	 * Compute the similar words of 'queryWord'
	 * @param queryWord
	 * @return 
	 */
	public List<ReportVector> computeNNs(String reportID) {
		ArrayList<ReportVector> distList = new ArrayList<>(reportVecMap.size());
		ReportVector reportVector = reportVecMap.get(reportID);
		WordVec queryVec = reportVector;//wordvecmap.get(queryWord);   // vector of the corresponding word is find out
		if (queryVec == null)
			// if the word has no vector embedded with it.
			return null;

		for (Map.Entry<String, ReportVector> entry : reportVecMap.entrySet()) {

			// calculate similarity with all the words of the collection
			ReportVector wv = entry.getValue();
			if(wv==null)
				continue;
			if (wv.word.equals(queryVec.word))
				continue;
			//for(int i=0;i<wv.vec.length;i++) {
				//System.out.println(wv.vec[i]+" "+queryVec.vec[i]);
			//}
			wv.querySim = queryVec.cosineSim(wv);
			//System.out.println(wv.querySim);
			
				distList.add(wv);
		}

		// sort the list of words according to similarity with queryWord (descending)
		Collections.sort(distList);

		// return a list of min(k, distList.size()) items which are similar to queryWord
		return distList.subList(0, Math.min(100, distList.size()));
	}

	ReportToVectors(String stringAndWeightsFile,int k)
	{

		this.reportVecMap = new HashMap<String, ReportVector>();
		this.k = k;

		try {
			System.out.println("Loading word embeddings..");
			WordVecs wvs = new WordVecs(Constants.WORD_EMBEDDING_FILE);
			System.out.println("Done...");
			System.out.println("Constructing Vectors for each report...");

			try (FileReader fr = new FileReader(stringAndWeightsFile);
					BufferedReader br = new BufferedReader(fr)) 
			{
				String line;
				int loopcount = 0;
				while ((line = br.readLine()) != null) {
					String[] tokens = line.split("\\s+");
					ReportVector rv = null;
					if(tokens.length>1) {
						rv = new ReportVector(tokens,wvs,k,true,false);
						if(rv.vec!=null)
							reportVecMap.put(rv.word, rv);
					}
					if((loopcount++)%1000==0)
						System.out.println("Vectorized "+loopcount+" Documents");
				}
			}
			System.out.println("Done...");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		ReportToVectors rvs = new ReportToVectors(Constants.DOCUMENT_TERMS_WEIGHTS_DUMP_FILE, 35);
		System.out.println(ReportVector.minNorm+" "+ReportVector.maxNorm);
		PrintWriter output = null;
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");  
			LocalDateTime now = LocalDateTime.now();  
			//System.out.println(dtf.format(now));  
			String outName = Constants.OUTFILE_NAME+dtf.format(now)+".txt";
			System.out.println(outName);

			File myObj = new File(outName);

			if (!myObj.createNewFile()) {
				assert(true);
			} 
			output = new PrintWriter(myObj);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
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
        Integer aaa = new Integer(2);
		for(Topic t : queryIdList)
		{
			System.out.println(t.number+". Executing report: "+t.docid+"\n");
			Report queryReport = trec.LuceneQuery.getReportWithID(t.docid,isearcher);
			List<ReportVector> similar = rvs.computeNNs(t.docid);
			int count = 0;
			for(ReportVector r: similar) 
			{
				Report resultReport = trec.LuceneQuery.getReportWithID(r.word, isearcher);
				if(queryReport.getTitle().equals(resultReport.getTitle()))
					continue;
				System.out.println(r.word+" "+r.querySim);
				output.printf("%d Q0 %s %d %f %s\n",extractTopicNo(t),r.word,count+1,r.querySim,"vector");
				output.flush();
				count++;
			}
		}
	}

	private static int extractTopicNo(Topic t) {
		String n = t.number.substring(9, t.number.length()-1);
		return Integer.parseInt(n);

	}

}
