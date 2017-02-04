package ke;
import java.util.Comparator;
import java.util.PriorityQueue;

import addition.Computelli;
import addition.Node2;


public class KFWrapper {

	public KFWrapper() {
		// TODO Auto-generated constructor stub
		

	}
	
	
	
	
	public static void main(String[] args)
	{


		Graph2 graph = new Graph2();
		
		
		graph.addNode2(0, "input", 1.0); // 무엇을 위한 노드인가?
		graph.addNode2(1, "infoExtract", 1.0);
		graph.addNode2(2, "output", 1.0); // 무엇을 위한 노드인가?
	
		graph.addEdge2(0, "in", 1.0, graph.Node2List.get(0), graph.Node2List.get(1));
		graph.addEdge2(1, "out", 1.0, graph.Node2List.get(1), graph.Node2List.get(2));
//		graph.addTriple(0, "곱하기", 1.0, graph.Node2List.get(0), graph.Node2List.get(1), graph.Node2List.get(2));
		

		for (int i = 0; i < graph.Node2List.size(); i++) {
			System.out.print(graph.Node2List.get(i).value + " 와 연결된 노드 : ");

			for (int j = 0; j < graph.Node2List.get(i).outEdge2.size(); j++) {
				if (!graph.Node2List.get(i).outEdge2.isEmpty())
					System.out.print(graph.Node2List.get(i).outEdge2.get(j).to_Node2.value+ " ");
			}
//			for (int j = 0; j < Node2List.get(i).inEdge2.size(); j++) {
//				if (!Node2List.get(i).inEdge2.isEmpty())
//					System.out.print(Node2List.get(i).inEdge2.get(j).from_Node2.value + " ");
//			}
			System.out.println();
		}
		
		Node2 fromNode2 = graph.Node2List.get(0);
		Node2 endNode2 = graph.Node2List.get(2);
		
		graph.open = new PriorityQueue<Node2>(new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				Node2 c1 = (Node2) o1;
				Node2 c2 = (Node2) o2;

				return c1.fScore < c2.fScore ? -1 : c1.fScore > c2.fScore ? 1
						: 0;
			}
		});
		
		Graph2.pathFindingAStar(graph, fromNode2, endNode2);
//		
//		System.out.print(endNode2.value);
		
		Node2 temp = endNode2.parent;
//		while (temp != null) {
//			System.out.print(" -> " + temp.value);
//			temp = temp.parent;
//		}
//		
//		graph2.init();
//		
////		open.removeAll(open);
//		open.clear();

//		Graph2.pathFindingAStar(graph2, fromNode2, endNode2);
//		System.out.print(endNode2.value);
		
//		Graph2.pathFindingAStarWithEdge(graph, endNode2, fromNode2);
//		System.out.print(fromNode2.value);

		double a=0, b=0, c=0;
		
		temp = fromNode2;
		while (temp != null) {

			System.out.print(" -> " + temp.value);
			if (temp.value =="input")
			{ // 여기서  edge를 'in'이라하고 'in'함수를 실행해서 해당 변수에 값을 할당하는 작업을 하도록 해야 한다. 즉,set함수를 실행하도록 하는 셈이다. 	
				a = 3.;
				b = 5.;
			}
			else if (temp.value == "infoExtract")
		{
				c = Computelli.compute(temp.value,a, b);
		}
		else if (temp.value == "output")
		{
			System.out.println(a + temp.inEdge2.get(0).edge_Node2.value + b +" 는 " + c);
			
		}
		
		temp = temp.parent;

	}
		
//		Edge2 tempEdge = endNode2.inEdge2.get(0);
//		while (tempEdge != null) {
//			System.out.print(" -> " + tempEdge.value);
//			temp = tempEdge.from_Node2;
//			System.out.print(" -> " + temp.value);
//			tempEdge = temp.inEdge2.get(0);
//		}

		
	}
		
}
