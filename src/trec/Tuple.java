package trec;

public class Tuple {
	String docId;
	double score;
	String codename;
	
	public Tuple(String docId, double score,String codename) {
		super();
		this.docId = docId;
		this.score = score;
		this.codename = codename;
	}
	
}
