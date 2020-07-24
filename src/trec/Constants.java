package trec;

public class Constants {
	// 594902 indexed (id not null or content title both not null)
	// 512000
	
	public static final String INDEX_PATH2 = "index3/";
    public static final String INDEX_PATH1 = "tittle_content_index/";
    public static final String WAPO_V2_INDEX_PATH = "nerIndex/";
    
    public static final String TOPIC_XML_SOURCE2018= "resources/topics.xml";
    public static final String TOPIC_XML_SOURCE2019 = "resources/newsir19-background-linking-topics.xml";
   

    /* for trec 2020 */
    public static final String WAPO_V2_LOCATION = "/media/i3/015d14d9-4f60-41b6-84af-5a23508580fb/home/arrgee/Documents/WashingtonPost.v2/data/TREC_Washington_Post_collection.v2.jl";
    public static final String WAPO_V3_LOCATION = "/media/i3/7233c927-5a17-4fe9-bedd-784c6f7e151d/WashingtonPost.v3/data/TREC_Washington_Post_collection.v3.jl";
    public static final String WAPO_V3_INDEX_PATH = "/media/i3/7233c927-5a17-4fe9-bedd-784c6f7e151d/wapoV3Index/";
    public static final String TOPIC_XML_SOURCE2020 = "resources/newsir20-background-linking-topics.xml";
    public static final String DOCUMENT_TERMS_WEIGHTS_DUMP_FILE_2020 = "terms2020.txt";
    public static final String WORD_EMBEDDING_FILE_2020 = "embedding2020.vec";
    public static final int TOTAL_DOC_NO_V3 = 671947;
    // where content of each report is dumped
    public static final String DUMP_FILE_CONTENT_2020 = "/media/i3/7233c927-5a17-4fe9-bedd-784c6f7e151d/content_dump_word2vec_train_wapo_v3.txt";
    // where word:boostscore for each text is dumped
    public static final String DUMP_WORD2VEC_KEY_VAL_FILE_CONTENT_2020 = "/media/i3/7233c927-5a17-4fe9-bedd-784c6f7e151d/content_key_val_dump_for_embedding.txt";
    public static final String WORD_EMBEDDING_FILE_V3= "/media/i3/7233c927-5a17-4fe9-bedd-784c6f7e151d/wapoV3WordVec/wapov3.bin.vec";
    
    
    public static final int TOTAL_DOC_NO_V2 = 595037;
    
    
    
    
      
    public static final String DOCUMENT_TERMS_WEIGHTS_DUMP_FILE_V2 = "terms.txt";
    
    
    public static final String DOCUMENT_TERMS_WEIGHTS_DUMP_FILE_TITLE_V2 = "terms_title.txt";
    
    public static final String WORD_EMBEDDING_FILE_V2= "embedding.vec";
    
    public static final String DUMP_FILE = "dump.txt";
    public static final String DUMP_FILE_TITLE = "dump_title.txt";
    
     
    public static final String FILE_NAME2 = "wapo";
    
 // The JDBC Connector Class.
    public static final String dbClassName = "com.mysql.jdbc.Driver";

    // Connection string. emotherearth is the database the program
    // is connecting to. You can include user and password after this
    // by adding (say) ?user=paulr&password=paulr. Not recommended!

    public static final String CONNECTION =
                            "jdbc:mysql://127.0.0.1/wapo?&rewriteBatchedStatements=true&jdbcCompliantTruncation=false";
    
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "root";
    
    
    /* final settings */
    public static final int TOTAL_DOC_NO = TOTAL_DOC_NO_V2;
     
    
    public static final String INDEX_PATH = WAPO_V2_INDEX_PATH; /* set it to whatever folder index must be stored in*/
    public static final String TOPIC_XML_SOURCE = TOPIC_XML_SOURCE2018;
    // where every word and embedding is stored
    public static final String WORD_EMBEDDING_FILE = WORD_EMBEDDING_FILE_V2;
    
 // DUMP_WORD2VEC_KEY_VAL_FILE_CONTENT_2020 for 2020
    // DOCUMENT_TERMS_WEIGHTS_DUMP_FILE_V2
    public static final String DOCUMENT_TERMS_WEIGHTS_DUMP_FILE = DOCUMENT_TERMS_WEIGHTS_DUMP_FILE_V2;//DUMP_WORD2VEC_KEY_VAL_FILE_CONTENT_2020;
 
    
    public static final String OUTFILE_NAME = "results/res";
    // path of the raw data wapo file
    public static final String FILE_NAME = WAPO_V3_LOCATION;//"/media/i3/015d14d9-4f60-41b6-84af-5a23508580fb/home/arrgee/Documents/WashingtonPost.v2/data/TREC_Washington_Post_collection.v2.jl";
   
    public static final int CONTENT_QUERY_LENGTH = 50; // 80 good
    public static final int TITLE_QUERY_LENGTH = 20; // 20 good
    public static final int VECTOR_SUM_WORDS = 25; // express report as sum of this many vecs 25
    public static final boolean IS_VECTOR_SUM_WEIGHTED = true; // true is better
    
    // judged term boosting weighted by bm25 or default
    public static final boolean BM25_MODE = false;
    public static final boolean VECTOR_NEEDED = false;
  
    
}
