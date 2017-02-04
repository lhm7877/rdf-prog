package tutorial;

import java.util.HashMap;

public class ToC{

	String[] headings;
	String[] lp;
	HashMap<String,String> h2lp= new HashMap<String,String>();
	HashMap<String,String> lp2h= new HashMap<String,String>();

	public ToC() {
		// TODO Auto-generated constructor stub
	}
		
	public ToC(String[][] headingLocation)
	{
		for (int i = 0; i < headingLocation.length; i++)
		{
			// map
		      // Create a hash map
		      // Put elements to the map
		      h2lp.put(headingLocation[i][0], headingLocation[i][1]);
		      lp2h.put(headingLocation[i][1], headingLocation[i][0]);
		}			
	}

	public String H2LP (String heading)
	{
		return h2lp.get(heading);
	}
	public String LP2H (String location)
	{
		return lp2h.get(location);
	}
	public void run()
	{
//		return h2lp.get(heading);
	}

}
