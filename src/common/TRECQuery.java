/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import static common.CommonVariables.FIELD_BOW;
import java.io.StringReader;

import javax.swing.text.AbstractDocument.Content;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

/**
 *
 * @author dwaipayan
 */
public class TRECQuery {
    public String       qid;
    public String       qtitle;
   // public String       qdesc;
    //public String       qnarr;
    public Query        luceneQuery;
    public String       fieldToSearch;

    @Override
    public String toString() {
        return qid + "\t" + luceneQuery;
    }

    public Query getLuceneQuery() { return luceneQuery; }

    /**
     * Returns analyzed queryFieldText from the query
     * @param analyzer
     * @param queryFieldText
     * @return (String) The content of the field
     * @throws Exception 
     */
    public static String queryFieldAnalyze(Analyzer analyzer, String queryFieldText) throws Exception {
        //fieldToSearch = FIELD_BOW;
        StringBuffer localBuff = new StringBuffer(); 
//        queryFieldText = queryFieldText.replace(".", "");
        TokenStream stream = analyzer.tokenStream(Constants.FIELD_TO_EXTRACT_QUERY_FROM, new StringReader(queryFieldText));
        CharTermAttribute termAtt = stream.addAttribute(CharTermAttribute.class);
        stream.reset();
        int count = 0;
        while (stream.incrementToken() && count<Constants.MAX_NO_OF_TERMS_FOR_INITIAL_QUERY) {
            String term = termAtt.toString();
            term = term.toLowerCase();
            localBuff.append(term).append(" ");
            count++;
        }
        stream.end();
        stream.close();
        return localBuff.toString();
    }
/*
    public Query getBOWQuery(Analyzer analyzer, TRECQuery query) throws Exception {
        fieldToSearch = FIELD_BOW;
        BooleanQuery q = new BooleanQuery();
        Term thisTerm;
        
        String[] terms = queryFieldAnalyze(analyzer, query.qtitle).split("\\s+");
        for (String term : terms) {
            thisTerm = new Term(fieldToSearch, term);
            Query tq = new TermQuery(thisTerm);
            q.add(tq, BooleanClause.Occur.SHOULD);
        }
        luceneQuery = q;

        return luceneQuery;
    }
*/
}