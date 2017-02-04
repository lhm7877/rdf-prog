package tutorial;

import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Derivation;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.PrintUtil;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

public class Tutorial10 {
	public static String getNodes(Model infmodel, Resource a, Property p)
	{
		String str="";
		for (NodeIterator i = infmodel.listObjectsOfProperty(a,p); i.hasNext(); ) {
	        //Statement stmt = i.nextStatement();
	    	RDFNode nd = i.nextNode();
	    	
	        System.out.println(" - " + nd.toString());
//	        System.out.println(" - "  + PrintUtil.print(nd));
	        str =  nd.toString();
	    }
		return str;
	}

	public static void printStatements(OntModel infModel, Resource A, Property p, Resource D)
	{
	    for (StmtIterator i = infModel.listStatements(A,p,D); i.hasNext(); ) {
	        Statement stmt = i.nextStatement();
	        System.out.println(" - " + PrintUtil.print(stmt));
	    }
	}
	public static void printStatements(InfModel infModel, Resource A, Property p, Resource D)
	{
		PrintWriter out = new PrintWriter(System.out);
		System.out.println("Here in the print function.");
		for (StmtIterator i = infModel.listStatements(A, p, D); i.hasNext(); ) {
		    Statement s = i.nextStatement();
		    System.out.println("Statement is " + s);
		    for (Iterator id = infModel.getDerivation(s); id.hasNext(); ) {
		        Derivation deriv = (Derivation) id.next();
		        deriv.printTrace(out, true);
		    }
		}
		out.flush();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model data = FileManager
				.get()
				.loadModel(
						"file:/Users/sunghee/Documents/sunghee-data/2015programs/workspace-java/rdf-prog/src/data.ttl");
		Model XK = FileManager
				.get()
				.loadModel(
						"file:/Users/sunghee/Documents/sunghee-data/2015programs/workspace-java/rdf-prog/src/exKnowledge.ttl");
		List rules = Rule
				.rulesFromURL("file:/Users/sunghee/Documents/sunghee-data/2015programs/workspace-java/rdf-prog/src/myrules.rules");

		GenericRuleReasoner reasoner = new GenericRuleReasoner(
				(java.util.List<Rule>) rules);
		reasoner.setOWLTranslation(true);
		// not needed in RDFS case
		reasoner.setTransitiveClosureCaching(true);
		InfModel infmodel = ModelFactory.createInfModel(reasoner, data);

		String eg="http://example.org/";
		String rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#";
		String rdfs="http://www.w3.org/2000/01/rdf-schema#";
		
		Model someModel = ModelFactory.createDefaultModel();
		someModel.add(XK);
		Resource a = infmodel.getResource(eg+"InfoExt");
		Property p = infmodel.getProperty(rdf,"ID");
		System.out.println("xxx:"+ infmodel.getResource("rdf:ID").getPropertyResourceValue(p));
		Resource d = infmodel.getResource(eg+"");
		System.out.println(someModel.getProperty(eg,"InfoEt").toString());		
		System.out.println(infmodel.getProperty(eg,"InfoExt").toString());
		System.out.println("a:"+a.toString());
		System.out.println("p:"+p.toString());
		System.out.println("a has types:");
		
		
		// extract directly a literal, rdf:id, from a statement
//		System.out.println("^_^_^_: "+infmodel.getgetPropertyResourceValue(p).toString());
		getNodes(infmodel, a, p);
		
//	    printStatements(infmodel, a, p , null);

		System.out.println("*****************");
		
		
		
		// list the statements in the graph
		StmtIterator iter = infmodel.listStatements();
		// print out the predicate, subject and object of each statement       
		while (iter.hasNext()) {
			Statement stmt = iter.nextStatement();
			// get next statement           
			Resource subject = stmt.getSubject();
			// get the subject
			Property predicate = stmt.getPredicate();
			// get the predicate           
			RDFNode object = stmt.getObject();
			// get the object
			System.out.print(subject.toString());
			System.out.print(" " + predicate.toString() + " ");
			if (object instanceof Resource) {

				System.out.print(object.toString());

			} else {

				// object is a literal

				System.out.print(" \"" + object.toString() + "\"");

			}

			System.out.println(" .");
		}
		
		System.out.println("###################");
		
		// list the statements in the graph
		StmtIterator iter2 = someModel.listStatements();
		// print out the predicate, subject and object of each statement       
		while (iter2.hasNext()) {
			Statement stmt = iter2.nextStatement();
			// get next statement           
			Resource subject = stmt.getSubject();
			// get the subject
			Property predicate = stmt.getPredicate();
			// get the predicate           
			RDFNode object = stmt.getObject();
			// get the object
			System.out.print(subject.toString());
			System.out.print(" " + predicate.toString() + " ");
			if (object instanceof Resource) {

				System.out.print(object.toString());

			} else {

				// object is a literal

				System.out.print(" \"" + object.toString() + "\"");

			}

			System.out.println(" .");
		}
		
		System.out.println("**********************");
		infmodel.add(XK);
		// list the statements in the graph
		iter = infmodel.listStatements();
		// print out the predicate, subject and object of each statement       
		while (iter.hasNext()) {
			
			Statement stmt = iter.nextStatement();
			// get next statement           
			Resource subject = stmt.getSubject();
			// get the subject
			Property predicate = stmt.getPredicate();
			// get the predicate           
			RDFNode object = stmt.getObject();
			// get the object
			System.out.print(subject.toString());
			System.out.print(" " + predicate.toString() + " ");
			if (object instanceof Resource) {

				System.out.print(object.toString());

			} else {

				// object is a literal

				System.out.print(" \"" + object.toString() + "\"");

			}

			System.out.println(" .");
		}
		
		
		
        // Case#1: HTSCRE
		String strString = "java.lang.String";
        String strClass = "tutorial.HTSCRE";
		String strMethod = getNodes(infmodel, a ,p);
		
        
        try {
        	Class<?> passedClass = Class.forName(strClass); // tutorial.HTSCRE
        	
            // parameter types for methods
             Class<?>[] partypes = new Class[]{Class.forName(strString)}; // input type
             
             
             // Create method object. method name and parameter types
             Method meth = passedClass.getMethod(strMethod, partypes); // class, method, input type
             
             // parameter types for constructor
             Class<?>[] constrpartypes = new Class[]{};
             //Create constructor object. parameter types
             Constructor<?> constr = passedClass.getConstructor(constrpartypes); // class constructor
             // create instance
             Object dummyto = constr.newInstance(); // create class instance 
               
             // Arguments to be passed into method
             Object[] arglist = new Object[]{"REFset"}; 
             // invoke method!!
             String output = (String) meth.invoke(dummyto, arglist); // method, class instance
             System.out.println(arglist[0] + "," + output);
             
             
             // 만약 외부지식 가운데 subclass와 학습자료가 있다면 입력자료를 확인하여 classification을 수행하라.
             
             Resource res = someModel.createResource(eg+"Ref");
             Resource exRes = someModel.createResource(eg+"IEEEStyledRef");
             
//             Property pro = someModel.getProperty(RDFS.subClassOf);
             System.out.println(res.toString());
             System.out.println(exRes.toString());
             
             Statement subclassstmt = someModel.createStatement(res,RDFS.subClassOf,exRes);
             System.out.println(subclassstmt.toString());
             if (someModel.containsResource(RDFS.subClassOf)) // someModel이 subClass를 가지고 있다면
             {
            	 Resource res2 = someModel.getResource(eg+"InfoExtract");
            	 System.out.println("do contain");
            	 
            	 Classifier cl = new Classifier();
            	 System.out.println(cl.classify(exRes.toString()));
            	 
            	 
             }
             else
             {
            	 System.out.println("does not contain");
             }
             
             
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

		

	}

	private static void printStatements(InfModel infmodel, Resource colin,
			Property type, Object object) {

		// TODO Auto-generated method stub

	}

}
