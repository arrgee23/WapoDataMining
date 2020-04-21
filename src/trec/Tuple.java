package trec;

public class Tuple implements Comparable<Tuple>{
	String docId;
	double score;
	String codename;
	String type;
	
	public Tuple(String docId, double score,String codename) {
		super();
		this.docId = docId;
		this.score = score;
		this.codename = codename;
	}
	
	public Tuple(String docId, double score,String codename,String type) {
		super();
		this.docId = docId;
		this.score = score;
		this.codename = codename;
		this.type = type;
	}
	
	public static void main() {
		System.out.println(2/(double)3);
	}

	@Override
	public int compareTo(Tuple o) {
		if(o.score>this.score)
			return 1;
		else if(o.score<this.score)
			return -1;
		return 0;
	}
}
