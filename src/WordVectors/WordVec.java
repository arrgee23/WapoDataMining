
package WordVectors;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 *
 * @author dwaipayan
 */
public class WordVec implements Comparable<WordVec> {
    public String  word;       // the word
    public double[] vec;        // the vector for that word
    public double   norm;       // the normalized value
    public double   querySim;   // distance from a reference query point

    public WordVec() {}
    
    public WordVec(int vecsize) { 
        vec = new double[vecsize];
    }
    
    public WordVec(String word, double[] vec) {
        this.word = word;
        this.vec = vec;
    }

    public WordVec(String word, double[] vec, double querySim) {
        this.word = word;
        this.vec = vec;
        this.querySim = querySim;
    }

    public WordVec(String word, double querySim) {
        this.word = word;
        this.querySim = querySim;
    }

    public WordVec(String line) {
        String[] tokens = line.split("\\s+");
        word = tokens[0];
        vec = new double[tokens.length-1];
        for (int i = 1; i < tokens.length; i++)
            vec[i-1] = Float.parseFloat(tokens[i]);
        norm = getNorm();
    }

    public String getWord() {
        return word;
    }

    public double getNorm() {
        if (norm == 0) {
            // calculate and store
            double sum = 0;
            for (int i = 0; i < vec.length; i++) {
                sum += vec[i]*vec[i];
            }
            norm = (double)Math.sqrt(sum);
        }
        return norm;
    }
    static int count = 0;
    public WordVec scalerMult(double d,boolean isConsiderNorm) {
    	//if(count<=2)
        WordVec v = new WordVec(this.vec.length);
    	//System.out.println(this.word+" "+d+" "+this.getNorm());
    	
    	for (int i = 0; i < this.vec.length; i++) {
            //if(count<=2)
            	//System.out.print(i+" "+this.vec[i]+"\t");
    		v.vec[i] = this.vec[i]*d;
    		if(!isConsiderNorm)
    			v.vec[i] /= this.getNorm();
    		
    		//if(count<=2)
            	//System.out.print(this.vec[i]+" \n");
        }
    	v.norm = d*this.norm;
    	if(!isConsiderNorm)
    		v.norm /= this.getNorm();
    	//if(count<=2)
       // System.out.println(" "+v.getNorm());
    	//count++;
    	return v;
    }

    public double cosineSim(WordVec that) {
    	if(that == null || that.vec==null)
    		return 0;
        double sum = 0;
        for (int i = 0; i < this.vec.length; i++) {
            
            sum += vec[i] * that.vec[i];
        }
        double a = this.getNorm();
        double b = that.getNorm();
        if(a==0 || b==0)
        	return 0;
        return sum / (a*b);
    }

    @Override
    public int compareTo(WordVec that) {
    	/*if(this.querySim!=Double.NaN && that.querySim!=Double.NaN)
    		return this.querySim > that.querySim ? -1 : this.querySim == that.querySim ? 0 : 1;
    	return 0;
    	*/
    	return -Double.compare(this.querySim, that.querySim);
    }

    byte[] getBytes() throws IOException {
        
    	byte[] byteArray;
        
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) 
        {
            ObjectOutput out;
            out = new ObjectOutputStream(bos);
            out.writeObject(this);
            byteArray = bos.toByteArray();
            out.close();
        }
        
        return byteArray;
    }

    public static WordVec add(WordVec a, WordVec b) 
    {

    	WordVec sum = new WordVec(a.word + ":" + b.word);
        sum.vec = new double[a.vec.length];
        for (int i = 0; i < a.vec.length; i++) {
            sum.vec[i] = .5 * (a.vec[i]/a.getNorm() + b.vec[i]/b.getNorm());
        }        
        return sum;
    }

    public static WordVec addWithoutAverage(WordVec a, WordVec b) {
        WordVec sum = new WordVec(a.word + ":" + b.word);
        sum.vec = new double[a.vec.length];
        for (int i = 0; i < a.vec.length; i++) {
            sum.vec[i] = (a.vec[i] + b.vec[i]);
        }        
        return sum;
    }

    public static WordVec add(List<WordVec> list) {
    	if(list.size()==0)
    		return null;
        WordVec sum = new WordVec(list.get(0).vec.length); // initially an all zero vector

        for (int i = 0; i < list.size(); i++) {
            sum = addWithoutAverage(sum, list.get(i));
        }
        for (int i=0; i<sum.vec.length; i++)
            sum.vec[i] /= list.size();
        sum.word = list.get(0).word;
        return sum;
    }
    static FileWriter myWriter;
    static {
    	
		try {
			myWriter = new FileWriter("debug.txt");
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
    }

	public void print() {
		try {
			myWriter.write(this.word+"\n");
			
		 //System.out.println(this.word);
	     if(this.vec==null) {
	    	 myWriter.write("null"+"\n");
	    	 return;
	     }
	     /*
		for (int i = 0; i < this.vec.length; i++) {
			myWriter.write(this.vec[i]+" ");
			//System.out.print(this.vec[i]+" ");
        }  */
		myWriter.write("\n"+norm+"\n");
		//System.out.println("\n"+norm);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
	}
		
}    
