package tutorial;
import java.lang.String;
import java.util.HashMap;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import edu.hannam.cismlab.*;



class L2P {
	String[] lp; // logical page
	String[] pp; // physical page
    HashMap<String,String> l2p = new HashMap<String,String>();
    HashMap<String,String> p2l = new HashMap<String,String>();
	
	// Constructor
	L2P(String[] lp, String[] pp)
	{
		for (int i = 0; i < lp.length; i++)
		{
			// map
		      // Create a hash map
		      // Put elements to the map
		      l2p.put(lp[i], pp[i]);
		      p2l.put(pp[i], lp[i]);
		}			
	}
	
	// PP -> LP
	String PP2LP (String pp)
	{
		String str="";
		
		return p2l.get(pp);
	}
	
	// LP -> PP
	String LP2PP (String lp)
	{
		String str="";
		
		return l2p.get(lp);	
	}
	
}

public class KFTest {

	public KFTest() {
		// TODO Auto-generated constructor stub
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Property aproperty = new Property();
		// 여기서, 입력은 일반 알고리즘의 맵핑과 의미론적 외부지식
		// 알고리즘 맵핑 표현은 입력 A, 출력 B와의 관계성 즉, 그래프 형식
		// 의미론적 외부지식에 대한 표현은 역시 그래프 형 
		// 문제는 그래프에서의 점프를 허용한 두 그래프의 연결이라는 측면과
		// 시맨틱웹에서 추론이라는 측면이 어떤 공통점과 차이점이 있는지 알아내는 것이 중요하다.
		// 점프를 허용한 두 그래프의 연결은 온톨로지 매핑이라는 이름으로 연구가 되어져 왔다.
		// 시맨틱웹에서 추론은 명시적인 트리플 관계로부터 명시적이지 않은 트리플 관계를 찾아내는 과정이다.
		
        // 입력: triple A (resource a, property b, resource c), triple B (resource d, property e, resource f)
		// 출력: triple C (resource g, property h, resource i)
		// dww-dkh-blf
		
		// 먼저, 입력에 출력을 만들어 보자
		// 결론은 길을 다 찾아서 제시하는 일
		String str[]={"reference","style",""};
		String [][] tableOfContent = {{"Table of Content","i"},
									{"Table of Tables","ii"},
									{"Table of Figures","iii"}, 
									{"Introduction","1"},
									{"Related Work","2"},
									{"Methods","3"}};
		
		String strClass = "tutorial.ToC";
		String strMethod = "H2LP";
		String strString = "java.lang.String";
		String strString2 = "[[Ljava.lang.String;";
		
		
//		ToC toc = new ToC(tableOfContent);
		
//		ClassLoader cl = .; // ClassLoader의 객체를 생성한다.
//		Class klass = null;
//		try {
//		     klass = cl.loadClass(strClass);
//		} catch(ClassNotFoundException ex) {
//		     // 클래스를 발견할 수 없을 경우에 발생한다.
//		     ex.printStackTrace();
//		}
		
//        try {
//            if (args.length < 1) {
//               System.out.println("사용법: java RuntimeLoading [클래스 이름]");
//               System.exit(1);
//            }
//            klass = Class.forName(strClass);
//            Object obj = (strClass klass.newInstance();
////            Runnable r = (Runnable) obj;
////            r.run();
//            
//    		
////    		System.out.println (tableOfContent[0][0] +","+ r.H2LP(tableOfContent[0][0]));
////    		System.out.println (tableOfContent[0][1] +","+ r.LP2H(tableOfContent[0][1]));
//
//        } catch(Exception ex) {
//            ex.printStackTrace();
//         }
//				
		Class aClass = String[][].class;
		String className = aClass.getName();
		System.out.println("Class Name:" + className);
		
		
//source: https://en.wikibooks.org/wiki/Java_Programming/Reflection/Dynamic_Invocation		
        
        try {
        	Class<?> passedClass = Class.forName(strClass);
        	
            // parameter types for methods
             Class<?>[] partypes = new Class[]{Class.forName(strString)};
             
             
             // Create method object. methodname and parameter types
             Method meth = passedClass.getMethod(strMethod, partypes);
             
             // parameter types for constructor
             Class<?>[] constrpartypes = new Class[]{Class.forName(strString2)};
             //Create constructor object. parameter types
             Constructor<?> constr = passedClass.getConstructor(constrpartypes);
             // create instance
             Object dummyto = constr.newInstance(new Object[]{tableOfContent});
               
             // Arguments to be passed into method
             Object[] arglist = new Object[]{tableOfContent[1][0]};
             // invoke method!!
             String output = (String) meth.invoke(dummyto, arglist);
             System.out.println(tableOfContent[1][0] + "," + output);
           } catch (ClassNotFoundException e) {
             e.printStackTrace();
         } catch (SecurityException e) {
             e.printStackTrace();
         } catch (NoSuchMethodException e) {
             e.printStackTrace();
         } catch (IllegalArgumentException e) {
             e.printStackTrace();
         } catch (IllegalAccessException e) {
             e.printStackTrace();
         } catch (InvocationTargetException e) {
             e.printStackTrace();
         } catch (InstantiationException e) {
             e.printStackTrace();
         }
        
        // Case#1: HTSCRE
        strClass = "tutorial.HTSCRE";
		strMethod = "InfoExt";
		
        
        try {
        	Class<?> passedClass = Class.forName(strClass);
        	
            // parameter types for methods
             Class<?>[] partypes = new Class[]{Class.forName(strString)};
             
             
             // Create method object. methodname and parameter types
             Method meth = passedClass.getMethod(strMethod, partypes);
             
             // parameter types for constructor
             Class<?>[] constrpartypes = new Class[]{};
             //Create constructor object. parameter types
             Constructor<?> constr = passedClass.getConstructor(constrpartypes);
             // create instance
             Object dummyto = constr.newInstance();
               
             // Arguments to be passed into method
             Object[] arglist = new Object[]{"IEEE"};
             // invoke method!!
             String output = (String) meth.invoke(dummyto, arglist);
             System.out.println("IEEE" + "," + output);
           } catch (ClassNotFoundException e) {
             e.printStackTrace();
         } catch (SecurityException e) {
             e.printStackTrace();
         } catch (NoSuchMethodException e) {
             e.printStackTrace();
         } catch (IllegalArgumentException e) {
             e.printStackTrace();
         } catch (IllegalAccessException e) {
             e.printStackTrace();
         } catch (InvocationTargetException e) {
             e.printStackTrace();
         } catch (InstantiationException e) {
             e.printStackTrace();
         }        
        //
//		String lp[] = {"i","ii","iii","1","2","3"};
//		String pp[] = {"1","2","3","4","5","6"};
//		
//		L2P lp2pp = new L2P(lp,pp);
//		System.out.println (lp[1] +","+ lp2pp.LP2PP(lp[1]));
//		System.out.println (pp[3] +","+ lp2pp.PP2LP(pp[3]));
//		
//		
//		// headings -> location = logical page -> physical page
//		System.out.println (tableOfContent[0][0] + "," + lp2pp.LP2PP(toc.H2LP(tableOfContent[0][0])));
//		// physical page -> logical page = location -> headings
//		System.out.println (pp[3] + "," + toc.LP2H(lp2pp.PP2LP(pp[3]))); 

		// 결국에는 이렇게 하는 것을 시맨틱웹을 통해서 자동으로 만들고 싶다.
		// 
		// semantic web을 통해서 headings-> location과 logical page -> physical page에 대한 정보를 안 다음에
		// location ~ logical page인 점을 링크시켜서 headings -> physical page를 얻어내는 관계를 자동으로 이끌어낸다.
		// 0. logically structuring : each lines -> headings
		// 1. RDF를 통해서 입력과 출력에 대한 정보를 read한다.
		// 2. headings -> location은 A -> B, logical page -> physical page => C -> D, B = C를 외부지식으로 넣어준다.
		// 3. semantic web 추론을 활용한다. 규칙은
		// 3.1 f:A -> B 이고  g: C -> D 이고, h: B ~ C이면 i: A -> D이다.

		// System.out.println (tableOfContent[0][0] + "," + lp2pp.LP2PP(toc.H2LP(tableOfContent[0][0])));
		// 을 분석하면 
		// tableOfContent[0][0] 입력, 즉, A 
		// f= ToC.H2LP, ToC.H2LP(A) -> B
		// g= H2LP

	}

}
