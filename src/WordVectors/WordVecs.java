
package WordVectors;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Character.isLetter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import org.apache.lucene.analysis.Analyzer;
import org.xml.sax.SAXException;
import trec.Constants;
/**
 *
 * @author dwaipayan
 * 
 * Calculates the similar terms of each of the word of the vocabulary dump
 * 
 */
public class WordVecs {

    //static Properties  prop;       // the properties file
    public int         k;          // k in kNN; Number of NNs to precompute and store
    public HashMap<String, WordVec> wordvecmap;    // each word and its vector
    //public HashMap<String, List<WordVec>> nearestWordVecsMap; // Store the pre-computed NNs after read from file
    static WordVecs singleTon;
    
    public WordVecs(String wordvecFile) throws Exception { 

        

    		k=10;
            
            wordvecmap = new HashMap();
            System.out.println("Started loading all vectors into hashmap...");
            try (FileReader fr = new FileReader(wordvecFile);
                BufferedReader br = new BufferedReader(fr)) {
                String line;

                while ((line = br.readLine()) != null) {
                    WordVec wv = new WordVec(line);
                    if(isLegalToken(wv.word))
                        wordvecmap.put(wv.word, wv);
                }
            }
        }
    
    
    /**
     * Compute the similar words of 'queryWord'
     * @param queryWord
     * @return 
     */
    public List<WordVec> computeNNs(String queryWord) {
        ArrayList<WordVec> distList = new ArrayList<>(wordvecmap.size());
        
        WordVec queryVec = wordvecmap.get(queryWord);   // vector of the corresponding word is find out
        if (queryVec == null)
        // if the word has no vector embedded with it.
            return null;
        
        for (Map.Entry<String, WordVec> entry : wordvecmap.entrySet()) {
        // calculate similarity with all the words of the collection
            WordVec wv = entry.getValue();
            if (wv.word.equals(queryWord))
                continue;
            wv.querySim = queryVec.cosineSim(wv);
            distList.add(wv);
        }

        // sort the list of words according to similarity with queryWord (descending)
        Collections.sort(distList);

        // return a list of min(k, distList.size()) items which are similar to queryWord
        return distList.subList(0, Math.min(k, distList.size()));
    }

    /**
     * Compute list of similar terms of the WordVec w and return
     * @param w
     * @return 
     */
    public List<WordVec> computeNNs(WordVec w) {
        ArrayList<WordVec> distList = new ArrayList<>(wordvecmap.size());
        
        if (w == null)
            return null;
        
        for (Map.Entry<String, WordVec> entry : wordvecmap.entrySet()) {
            WordVec wv = entry.getValue();
            if (wv.word.equals(w.word)) // ignoring computing similarity with itself
                continue;
            wv.querySim = w.cosineSim(wv);
            distList.add(wv);
        }
        Collections.sort(distList);
        return distList.subList(0, Math.min(k, distList.size()));        
    }

    /**
     * Compute list of similar terms of the WordVec w from feedbackTerms and return
     * @param w
     * @return 
     */
    public List<WordVec> computeNNs(WordVec w, HashMap<String, WordVec> feedbackTerms) {
        ArrayList<WordVec> distList = new ArrayList<>(feedbackTerms.size());
        
        if (w == null)
            return null;
        
        for (Map.Entry<String, WordVec> entry : feedbackTerms.entrySet()) {
            WordVec wv = entry.getValue();
            if (wv.word.equals(w.word)) // ignoring computing similarity with itself
                continue;
            wv.querySim = w.cosineSim(wv);
            distList.add(wv);
        }
        Collections.sort(distList);
        return distList.subList(0, Math.min(k, distList.size()));        
    }

    public double getSim(String u, String v) {
        WordVec uVec = wordvecmap.get(u);
        WordVec vVec = wordvecmap.get(v);
        if (uVec == null || vVec == null) {
//            System.err.println("words not found...<" + ((uVec == null)?u:v) + ">");
            return 0;
        }

        return uVec.cosineSim(vVec);
    }

    private boolean isLegalToken(String word) {
        boolean flag = true;
        for ( int i=0; i< word.length(); i++) {
//            if(isDigit(word.charAt(i))) {
//                flag = false;
//                break;
//            }
            if(isLetter(word.charAt(i))) {
                continue;
            }
            else {
                flag = false;
                break;
            }
        }
        return flag;
    }
    
 
    public static void main(String[] args) {
    	/*
        String usage = "Usage: java WordVecs <properties-path>\n"
            + "Properties file must contain:\n"
            + "1. vectorPath = path of the word2vec trained .vec file\n"
            + "2. nnDumpPath = path of the file, in which the precomputed NNs will be stored\n"
            + "3. k = number of NNs to precompute and store\n"
            + "4. [queryPath] = path of the query file";

        
        args = new String[1];
        args[0] = "/home/dwaipayan/preComputedNNs.properties";
        

        if(args.length == 0) {
            System.out.println(usage);
            System.exit(1);
	}*/
        try {
	    WordVecs wv = new WordVecs(Constants.WORD_EMBEDDING_FILE);
	    List<WordVec> wvl = wv.computeNNs("dog");
	    for(WordVec vec: wvl) {
	    	System.out.println(vec.word+" "+vec.norm+" "+vec.querySim);
	    }
           	/*
	    	if(prop.containsKey("queryPath"))
                wv.computeAndStoreQueryNNs();
            else
                wv.computeAndStoreNNs();
			*/
	    
            /* // to print all precomputed NNs
            qe.loadPrecomputedNNs();
            List<WordVec> nwords = qe.computeNNs("conclus");
            for (WordVec word : nwords)
                System.out.println(word.word + "\t" + word.querySim);
            */
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

    

