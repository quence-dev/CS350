public class Helper extends Thread {
	private Node n;

	//Helper constructor to initiate subsequent searches.
  	public Helper (Node n) {
		super();
		this.n = n;
    }

	//Run function for thread.
    public void run(){
    	Pirate ts = new Pirate();
		try {
			ts.findTreasure(this.n);
		}
		catch (Exception e){
			System.out.println(e);
		}
	}
}