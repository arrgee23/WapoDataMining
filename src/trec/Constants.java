package trec;

public class Constants {
	// 594902 indexed (id not null or content title both not null)
    public static final int TOTAL_DOC_NO = 595037;
    public static final String INDEX_PATH2 = "/home/arrgee/Documents/index2";
    public static final String INDEX_PATH = "/home/arrgee/Documents/index3";
    public static final String INDEX_PATH1 = "/home/arrgee/Documents/index";
    public static final String TOPIC_XML_SOURCE = "/home/arrgee/eclipse-workspace/WapoReportMining/resources/topics.xml";
    public static final String OUTFILE_NAME = "/home/arrgee/eclipse-workspace/WapoReportMining/results/res";
    
    public static final String FILE_NAME = "wapo";
    
 // The JDBC Connector Class.
    public static final String dbClassName = "com.mysql.jdbc.Driver";

    // Connection string. emotherearth is the database the program
    // is connecting to. You can include user and password after this
    // by adding (say) ?user=paulr&password=paulr. Not recommended!

    public static final String CONNECTION =
                            "jdbc:mysql://127.0.0.1/wapo?&rewriteBatchedStatements=true";
    
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "root";
    
}
