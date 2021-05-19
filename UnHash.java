import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class UnHash {
    static int[] unhashCompound(String input, Worker thread) {

	    int limit = 1;

	    while (limit < 100) {
			for (int i=1;i<=limit;i++) {
		    	for (int j=1;j<=limit;j++) {
					for (int k=1;k<=limit;k++) {
			    		for (int l=1;l<=limit;l++) {

							if((i == limit) || (j == limit) || (k == limit) || (l == limit)) {
				
								BigInteger O1 = new BigInteger(Integer.toString(i));
								BigInteger O2 = new BigInteger(Integer.toString(j));
								BigInteger O3 = new BigInteger(Integer.toString(k));
								BigInteger O4 = new BigInteger(Integer.toString(l));
								BigInteger ten = new BigInteger(Integer.toString(10));
								BigInteger compound = O1.add(O2.multiply(ten.pow(3))).add(O3.multiply(ten.pow(6))).add(O4.multiply(ten.pow(9)));
						
								String temp = hash(compound.toString());
						
								if (temp.compareTo(input)==0) {
				    				int[] res = new int[4];
				    				res[0]=i;
				    				res[1]=j;
				    				res[2]=k;
				    				res[3]=l;
						
				    				return res;
								}
							}
			    		}
					}
		    	}
			}
		if (thread != null && !thread.keepRunning())
		    break;
		
		limit++;
	    }
	    
	    int[] res = new int[4];
	    res[0]=-1;
	    res[1]=-1;
	    res[2]=-1;
	    res[3]=-1;
	    return res;
	}
	
	public static void main(String[] args) {
		String input = args[0].toString();
		
		int[] res = unhashCompound(input, null);
		for (int i=0;i<4;i++) {
			System.out.println(res[i]);
		}
		
		return;
	}

    static int unhash(String input, Worker thread) {
		
		int max = Integer.MAX_VALUE;
		int min = Integer.MIN_VALUE;
		int i = -1;
		
		while (i<max) {
			i++;
			String temp = hash(Integer.toString(i));
			
			if (temp.compareTo(input)==0) {
				return i;
			}
			
			if (thread != null && !thread.keepRunning())
			    break;
			
		}
		return -1;
	}

	public static String hash(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] message = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1,message);
			String result = number.toString(16);
			while (result.length()<32) 
				result = "0"+result;
			return result;
		}
		catch(NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		
	}
}
