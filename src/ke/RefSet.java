package ke;
import java.util.ArrayList;
import java.util.HashMap;

class Ref extends 인자유형
{
	String text;
	Integer 	style;
	Ref()
	{
		
	}
	Ref(String ref, int style)
	{
		this.text = ref;
		this.style = style;
	}
}

 
public class RefSet
{
	// 분할하는 기능을 객체 내에 가지고 있는 것이 특징
	public ArrayList<인자유형>  set_t;
	public HashMap<Integer, ArrayList<인자유형>> splitted_set_t;

	public void 인자분할(ArrayList<인자유형> a)
	{
		// 세팅
		 for (int i = 0; i < a.size(); i++)
		 {
			 System.out.print (a.get(i).refValue);
			 System.out.println (a.get(i).style);
		 }
//		for (int i = 0; i < 0; i++)
		{
			set_t = a;			
		}	
		 for (int i = 0; i < a.size(); i++)
		 {
			 System.out.print (set_t.get(i).refValue);
			 System.out.println (set_t.get(i).style);
		 }

		
		// 인자를 분할하는 것이 핵심
		{
			내부분할();
		}
	};
	private void 내부분할()
	{
		// 같은 클래스가 아니면 따로 만들어라
		ArrayList<Integer > styleList=new ArrayList<Integer>();
		
		for (int i = 0; i < set_t.size(); i++)
		{
			if (!styleList.isEmpty())
			{
				// 안에 있으면 그냥 추가하
				if (styleList.contains((set_t.get(i)).style))
				{
					
					splitted_set_t.get(set_t.get(i).style).add(set_t.get(i));
				}
				else // 안에 없으면 새로 만들어서 추가하기
				{
					styleList.add(set_t.get(i).style);
					ArrayList<인자유형> aThing = new ArrayList<인자유형>();
					aThing.add(set_t.get(i));
					splitted_set_t.put(set_t.get(i).style, aThing);					
				}
			}
			else {
				splitted_set_t = new HashMap<Integer,ArrayList<인자유형>>();
				styleList.add(set_t.get(i).style);
				ArrayList<인자유형> aThing = new ArrayList<인자유형>();
				aThing.add(set_t.get(i));
				splitted_set_t.put(set_t.get(i).style, aThing);					
			}
		}
		
	}
	
	public void 인자분할1()
	{
		
	};
	public void 인자분할2(){};
	
	
	
	public RefSet()
	{
	}
	public RefSet(ArrayList<인자유형> a)
	{
		for (int i = 0; i < 0; i++)
		{
			set_t= a;
			
		}
	}

	
	public static void main(String[] args)
	{
		ArrayList<인자유형> CORA = new ArrayList<인자유형>();
		
		for (int i = 0; i < 5; i++)
		{
			Ref aRef = new Ref("ref"+i, i%2 +1);
			CORA.add(aRef);
		}
		
		RefSet a = new RefSet();
		
		a.인자분할(CORA);

	    HashMap<Integer, ArrayList<인자유형>> resultSplitted;

		resultSplitted = a.splitted_set_t;
		
		for (Integer i : resultSplitted.keySet())
		{
			System.out.print("style "+ i+":");
			int j =	resultSplitted.get(i).size();
			for (int k = 0; k < j; k++ )
			{
				System.out.print(resultSplitted.get(i).get(k).refValue + ",");
			}
			System.out.println("");
			
		}
		
	}
			
}