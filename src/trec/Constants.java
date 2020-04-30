package trec;

public class Constants {
	// 594902 indexed (id not null or content title both not null)
    public static final int TOTAL_DOC_NO = 595037;
    public static final String INDEX_PATH2 = "index3/";
    public static final String INDEX_PATH1 = "tittle_content_index/";
    public static final String INDEX_PATH = "index2";
    public static final String TOPIC_XML_SOURCE18 = "resources/topics.xml";
    public static final String TOPIC_XML_SOURCE = "resources/newsir19-background-linking-topics.xml";
    public static final String DOCUMENT_TERMS_WEIGHTS_DUMP_FILE = "terms.txt";
    public static final String DOCUMENT_TERMS_WEIGHTS_DUMP_FILE_TITLE = "terms_title.txt";
    
    public static final String OUTFILE_NAME = "results/res";
    public static final String DUMP_FILE = "dump.txt";
    public static final String DUMP_FILE_TITLE = "dump_title.txt";
    public static final String WORD_EMBEDDING_FILE = "embedding.vec";
    public static final String FILE_NAME = "/media/i3/015d14d9-4f60-41b6-84af-5a23508580fb/home/arrgee/Documents/WashingtonPost.v2/data/TREC_Washington_Post_collection.v2.jl";
    
    
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
    
    
    public static final int CONTENT_QUERY_LENGTH = 80;
    public static final int TITLE_QUERY_LENGTH = 80;
    
}
