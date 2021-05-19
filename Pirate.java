//Spencer Vilicic U07828843

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Semaphore;
import java.lang.InterruptedException;
import java.util.Queue;
import java.util.LinkedList;


public class Pirate {
    private static BufferedReader br;
    private static String input;
    private static Semaphore sem = new Semaphore(4, true);
    
    public static void findTreasure(Node n) {
		String toUnhash = input.substring(n.getHint(), n.getHint()+32);
	    try {
	    	sem.acquire();

			Worker w1 = new Worker(n, false, toUnhash, n.getHint());
			Worker w2 = new Worker(n, true, toUnhash, n.getHint());

			w1.start();
			w2.start();

			while(!w1.found() && !w2.found()) {
	    		try {
					Thread.sleep(1);
	    		}	
	    		catch (InterruptedException in) {
					in.printStackTrace();
	    		}
			}

			if (!w1.isDone())
	    		w1.doStop();

			if (!w2.isDone())
		   		w2.doStop();

			sem.release();

			//Keep threads from ending too early
			w1.join(); 
			w2.join();
		}
		catch (InterruptedException e) {
			System.out.println(e);
		}
    }
    
    public static void printTree(Node root, String treasureString) {
    	Queue<Node> queue = new LinkedList<>(); 

    	queue.add(root);

    	while (queue.peek() != null){
    		Node temp = queue.remove();
    		if (temp.getLeaf())
    			System.out.print(treasureString.charAt(temp.getSolution()));
    		else {
    			for (int i = 0; i < 4; i++)
    				queue.add(temp.getChild(i));
    		}
    	}
    }

    public static void main(String[] args) {
		int start_offset = Integer.parseInt(args[0]);
		File file = new File(args[1]); 
		Pirate ts = new Pirate();
		Node n = new Node(start_offset);
		
	
		try {
	    	br = new BufferedReader(new FileReader(file)); 
	    	input = br.readLine();
			String treasureString = new String(Files.readAllBytes(Paths.get(args[2])));

	    	ts.findTreasure(n);
	    	printTree(n, treasureString);
		} 
	
		catch (IOException ex) {
	    	ex.printStackTrace();
		}
    }
}
