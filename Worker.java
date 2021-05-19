public class Worker extends Thread {

    private boolean isDone = false;
    private boolean doCompound = false;
    private String input;
    private boolean found = false;
    private int init;
    private Node n;
    
    public Worker (Node n, boolean doComp, String input, int init) {
		super();
		this.n = n;
		this.doCompound = doComp;
		this.input = input;
		this.init = init;
    }

    public synchronized void doStop() {
        this.isDone = true;
    }

    public synchronized boolean keepRunning() {
        return !this.isDone;
    }
    
    public boolean isDone(){
		return isDone;
    }

    private void setFound(){
		this.found = true;
    }

    public boolean found(){
		return this.found;
    }
    
    public void run(){
		if (this.doCompound == false) {
	    	int result = UnHash.unhash(input, this);
	    
	    	if (result != -1) {
	    		n.setSolution(result);
	    		n.setLeaf();

				setFound();
	    	}
		} else {
	    	int [] result = UnHash.unhashCompound(input, this);

	    	if (result[0] != -1) {
				setFound();

				int o1 = init + (result[0]*32);
	    		int o2 = init + (result[1]*32);
	    		int o3 = init + (result[2]*32);
	    		int o4 = init + (result[3]*32);

	    		//Create four children nodes on the tree to search.
	    		Node n1 = new Node(o1);
	    		Node n2 = new Node(o2);
	    		Node n3 = new Node(o3);
	    		Node n4 = new Node(o4);

	    		n.setChildren(n1,n2,n3,n4);

	    		//Create a new thread for each of the
	    		//four offsets, and search down them.
	    		Helper h1 = new Helper(n1);
	    		Helper h2 = new Helper(n2);
	    		Helper h3 = new Helper(n3);
	    		Helper h4 = new Helper(n4);

	    		h1.start();
	    		h2.start();
	    		h3.start();
	    		h4.start();

	    		try {
					h1.join();
	    			h2.join();
	    			h3.join();
	    			h4.join();
	    		}
	    		catch (Exception e){
	    			System.out.println(e);
	    		}

	    	}
		}

	doStop();
    }
}
