package ke;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import addition.ConnectionAlgoDB;
import addition.Edge2;
import addition.Node2;

public class Graph2<V, E> {
	public ArrayList<Node2> Node2List;
	public ArrayList<Edge2> Edge2List;
	public ArrayList<Graph2> aGraphList;
	public static PriorityQueue<Node2> open;
	static PriorityQueue<Node2> arExtendableNode2Queue = new PriorityQueue<Node2>();

	public Graph2() {
		// TODO Auto-generated constructor stub
		Node2List = new ArrayList<Node2>();
		Edge2List = new ArrayList<Edge2>();
	}

	public void init() {
		Iterator<Node2> itr = Node2List.iterator();
		Node2 t;
		while (itr.hasNext()) {
			t = itr.next();
			t.fScore = 0;
			t.visited = false;
			t.parent = null;
		}
		Iterator<Edge2> itr2 = Edge2List.iterator();
		Edge2 s;
		while (itr2.hasNext()) {
			s = itr2.next();
			s.setVisited(false);
		}
	}

	public void refreshGraph(Graph2 aGraph, String dB) {
		ResultSet rs;
		if (dB.equals("algorithmn")) {
			rs = ConnectionAlgoDB.getAlgorithm();
		} else if (dB.equals("rdfs")) {
			rs = ConnectionAlgoDB.getRdfs();
		}
	}

	public void addNode2(int id, String value, double hScore, boolean isOperator) {
		Node2 node2 = new Node2(id, value, hScore, isOperator);
		boolean isIn = true;
		for (int i = 0; i < Node2List.size(); i++) {
			if (Node2List.get(i).value.equals(value))
				isIn = false;
		}
		if (isIn) {
			Node2List.add(node2);
		}
	}

	public void addNode2withInArgType(int id, String value, double hScore, boolean isOperator, String inArgType,
			boolean isAlgoGraphNode) {
		Node2 node2 = new Node2(id, value, hScore, isOperator);
		node2.inArgType = inArgType;
		boolean isIn = true;
		node2.setAlgoGraphNode(isAlgoGraphNode);
		for (int i = 0; i < Node2List.size(); i++) {
			if (Node2List.get(i).value.equals(value))
				isIn = false;
		}
		if (isIn) {
			Node2List.add(node2);
		}
	}

	public void addEdge2(int id, String value, double weight, Node2 from_Node2, Node2 to_Node2) {
		Edge2 Edge2 = new Edge2(id, value, weight);
		Edge2.from_Node2 = from_Node2;
		Edge2.to_Node2 = to_Node2;
		from_Node2.outEdge2.add(Edge2);
		to_Node2.inEdge2.add(Edge2);
		Edge2List.add(Edge2);
	}

	public void addTriple(int id, String value, double weight, Node2 from_Node2, Node2 edge_Node2, Node2 to_Node2) {
		Edge2 Edge2 = new Edge2(id, value, weight);
		Edge2.from_Node2 = from_Node2;
		Edge2.edge_Node2 = edge_Node2;
		Edge2.to_Node2 = to_Node2;
		from_Node2.outEdge2.add(Edge2);
		to_Node2.inEdge2.add(Edge2);
		Edge2List.add(Edge2);
	}

	public static Object makeProblemGraph(Object input, String operator, Object output) {
		Graph2 graph = new Graph2();
		graph.open = new PriorityQueue<Node2>(new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				Node2 c1 = (Node2) o1;
				Node2 c2 = (Node2) o2;

				return c1.fScore < c2.fScore ? -1 : c1.fScore > c2.fScore ? 1 : 0;
			}
		});
		graph.addNode2(0, "inputNode", 1.0, false);
		graph.addNode2(1, "input", 1.0, true);
		graph.addNode2(2, "inArg", 1.0, false);
		graph.addNode2(3, operator, 1.0, true);
		graph.addNode2(4, "outArg", 1.0, false);
		graph.addNode2(5, "output", 1.0, true);
		graph.addNode2(6, "outputNode", 1.0, false);

		// 2번 방법
		graph.addEdge2(0, "in", 1.0, (Node2) graph.Node2List.get(0), (Node2) graph.Node2List.get(1));
		graph.addEdge2(1, "out", 1.0, (Node2) graph.Node2List.get(1), (Node2) graph.Node2List.get(2));
		graph.addEdge2(2, "out", 1.0, (Node2) graph.Node2List.get(2), (Node2) graph.Node2List.get(3));
		graph.addEdge2(3, "out", 999.0, (Node2) graph.Node2List.get(3), (Node2) graph.Node2List.get(4));
		graph.addEdge2(4, "out", 1.0, (Node2) graph.Node2List.get(4), (Node2) graph.Node2List.get(5));
		graph.addEdge2(5, "out", 1.0, (Node2) graph.Node2List.get(5), (Node2) graph.Node2List.get(6));

		Node2 fromNode2 = (Node2) graph.Node2List.get(0);
		Node2 endNode2 = (Node2) graph.Node2List.get(6);

		Graph2.pathFindingAStar(input, graph, fromNode2, endNode2, new AlgoGraphMaker().init(),
				new KGraphMaker().init());
		return null;
	}

	public static Node2 isExtendableNode(String currentValue, Graph2 algoGraph, Graph2<?, ?> kGraph, boolean algoFlag) {
		Iterator<Node2> itr = algoGraph.Node2List.iterator();
		// 문제그래프에서 점핑을 하는데 알고리즘 그래프와 지식 그래프 두 곳에서 점핑을 할 수 있는 경우는? -> 해결 : 알고리즘
		// 그래프가 있을 경우 알고리즘 그래프를 탐색한다.
		if (!algoFlag) {
			while (itr.hasNext()) {
				Node2 aNode2 = itr.next();
				if ((currentValue.equals(aNode2.value) || aNode2.value.contains(currentValue + "_"))
						&& aNode2.dfsVisited == false) {
					System.out.println("*******************같은거 발견(AlgoGraph) : " + aNode2.value);
					aNode2.dfsVisited = true;
					return aNode2;
				}
			}
		}
		Iterator<Node2> itr2 = kGraph.Node2List.iterator();
		while (itr2.hasNext()) {
			Node2 aNode2 = itr2.next();
			if (currentValue.equals(aNode2.value) && aNode2.dfsVisited == false) {
				System.out.println("*******************같은거 발견(KGraph) : " + aNode2.value);
				aNode2.dfsVisited = true;
				return aNode2;
			}
		}
		return null;
	}

	public static void addExtendableNodewithDFS(Node2 current, Graph2 algoGraph, Graph2 kGraph) {
		// 해당 노드의 InArgType이 점핑 가능한가?(해당 노드 검사)
		boolean algoFlag = true;
		if (current.value.equals("InfoExtract")) {
			current.inArgType = "Ref";
		}
		//
		Node2 extendableNode = isExtendableNode(current.inArgType, algoGraph, kGraph, algoFlag);
		if (extendableNode != null) {
			// System.out.println("null이아니다" + current.value);
			arExtendableNode2Queue.offer(extendableNode);
		}

		// TODO 이부분은 알고리즘 그래프가 노드 한개만 있는 경우로 바뀌었으므로 수행하지 않음(선결 조건에 걸림)

		// 들어오는 노드가 없다면 리턴 (선결조건)
		if (current.inEdge2.isEmpty()) {
			return;
		}
		//
		// Iterator itr = current.inEdge2.iterator();
		// while (itr.hasNext()) {
		// Edge2 aEdge = (Edge2) itr.next();
		// System.out.println("이터레이터" + aEdge.from_Node2.value);
		// aEdge.from_Node2.visited = true;
		// extendableNode = isExtendableNode(aEdge.from_Node2.value, algoGraph,
		// kGraph, algoFlag);
		// if (extendableNode != null) {
		// System.out.println("null이아니다" + aEdge.from_Node2.value);
		// arExtendableNode2Queue.offer(extendableNode);
		// }
		// addExtendableNodewithDFS(aEdge.from_Node2, algoGraph, kGraph);
		// }
	}

	public static Stack<Node2> searchFinalNode(Node2 node) {
		Stack<Node2> stack = new Stack<Node2>();
		if (node.outEdge2.isEmpty()) {
			return null;
		} else {
			for (Edge2 i : node.outEdge2) {
				// System.out.println(i.from_Node2.value + "를 탐색중");
				if (ConnectionAlgoDB.existRelevantAlgorithm(node.value)) {
					stack.push(node);
					return searchFinalNode(i.to_Node2);
				}
			}
			return stack;
		}
	}

	public static Stack<Edge2> searchFinalNode2(Node2 node) {
		Stack<Edge2> stack = new Stack<Edge2>();
		Queue<Node2> q = new LinkedList<Node2>();
		q.offer(node);
		while (!q.isEmpty()) {
			Node2 temp = q.poll();
			// stack.push(temp);
			// System.out.println(temp.value + ".poll()");
			Iterator<Edge2> itr = temp.outEdge2.iterator();
			while (itr.hasNext()) {
				Edge2 tempEdge = itr.next();
				if (!tempEdge.isVisited()) {
					q.offer(tempEdge.to_Node2);
					stack.push(tempEdge);
					tempEdge.setVisited(true);
				}
			}
		}
		return stack;
	}

	public static void applyKnowledgeNode(Stack pathStack, Stack<Object> outputStack, Graph2 kGraph) {
		ConnectionAlgoDB connectionAlgoDB = new ConnectionAlgoDB();
		// TODO 해결 : 제일 아래 노드인 String 노드를 하드코딩해서 추가시킴 -> pathStack의 마지막 노드의 값을
		// 추가
		if (!pathStack.isEmpty()) {
			Edge2 tempEdge = (Edge2) pathStack.pop();
			outputStack.push(tempEdge.to_Node2.value);
		}
		// to_Node2가 아래 노드 from_Node2가 위에 노드
		while (!pathStack.isEmpty()) {
			Edge2 temp = (Edge2) pathStack.pop();
			// temp.to_Node2.
			if (temp.to_Node2.isOperator) {
				Object result = connectionAlgoDB.execute(temp.to_Node2.value, outputStack, temp.from_Node2.value);
				if (result != null) {
					outputStack.push(result);
				}

			} else if (!temp.to_Node2.isOperator) {
			}
		}
		
	}
	

	public static Object pathFindingAStar(Object refText, Graph2 aGraph2, Node2 fromNode2, Node2 endNode2,
			Graph2 algoGraph, Graph2 kGraph) {

		
		// 아래 코드들이 필요한가?
		// 노드들의 apply 결과를 담을 outputStack이다.
		// input의 다음 노드를 제일 아래에 깔아둔다(여기선 infoExtract)
		// input의 다음 노드(연산자 노드라고 생각) 연산자 노드를 업그레이드 시킨 연산자를 만들어야 하기 때문
		Stack<Object> outputStack = new Stack<Object>();

		Node2 operatorNode = (Node2) aGraph2.Node2List.get(3);
		outputStack.push(operatorNode.getValue());

		// outputStack.push("초기crfpath는 없다");//2017.07.05 infoextract실행을위해
		
		//TODO: input함수 안에 넣는다.
		if (refText != null) {
			outputStack.push(((addition.Ref_Style) refText).getText());// 원래는
																		// input으로
																		// 집어넣는다.
		}else{
			outputStack.push("");
		}

		// ArrayList<Node2> arFinalKNode2List = new ArrayList<Node2>();
		open.add(fromNode2);
		Node2 current;
		Node2 extendableNode;
		while (true) {
			current = open.poll(); // 확장할 노드
			System.out.println("현재 노드는??????" + current.value);
			// 선결조건을 여기에 즉, 이전의 것이 방문했으면 오케이, 그렇지 않으면 거기서 다시
			// current가 null이면 jumping을 시도한다. 즉, 지식 그래프에 접속해서 새로운
			// pathFindingAStar를 시도한
			if (current == null) {
				break;
			}
			if (current.equals(endNode2)) { // 현재 노드가 최종노드이면 마치고
				return null;
			}

			// boolean값은 알고그래프노드인 경우만 true로 한다.
			extendableNode = isExtendableNode(current.value, algoGraph, kGraph, false);
			if (extendableNode != null) {
				// 점핑시 점핑 전의 연산자 노드를 false로 바꾼다.
				// input과 output노드는 해당사항 없음
				if (!(current.value.equals("input") || current.value.equals("output"))) {
					current.isOperator = false;
				}

				// 확장한 노드와 현재 노드를 이어주는 엣지를 만든다.
				aGraph2.addEdge2(0, "value", 1.0, current, extendableNode);
				double weight;
				if (extendableNode.value.equals("InfoExtract_Style")) {
					weight = 0.1;
				} else {
					weight = 5.0;
				}
				for (Edge2 outEdge : current.outEdge2) {
					aGraph2.addEdge2(0, "value", weight, extendableNode, outEdge.to_Node2);
				}

				ConnectionAlgoDB connectionAlgoDB = new ConnectionAlgoDB();
				outputStack.push(connectionAlgoDB.execute(extendableNode.value, outputStack));
				addExtendableNodewithDFS(extendableNode, algoGraph, kGraph);
				Stack<Edge2> historystack = new Stack<Edge2>();
				// 점핑 가능한 노드의 점핑 후 마지막 노드 탐색
				// Iterator<Node2> itr = arExtendableNode2Queue.iterator();
				while (!arExtendableNode2Queue.isEmpty()) {
					Node2 aNode2 = arExtendableNode2Queue.poll();
					// arFinalKNode2List.add(searchFinalNode(aNode2));
					// historystack = searchFinalNode2(aNode2)식으로 한다면
					// while문 돌때마다 historyStack에 엣지들이 쌓이는게 아니라 교체됨
					// 그러므로 historystack을 쌓는(push)방식으로 바꿈
					// historystack = searchFinalNode2(aNode2);
					Queue<Node2> q = new LinkedList<Node2>();
					q.offer(aNode2);
					while (!q.isEmpty()) {
						Node2 temp = q.poll();
						Iterator<Edge2> itr2 = temp.outEdge2.iterator();
						while (itr2.hasNext()) {
							Edge2 tempEdge = itr2.next();
							if (!tempEdge.isVisited()) {
								q.offer(tempEdge.to_Node2);
								historystack.push(tempEdge);
								tempEdge.setVisited(true);
							}
						}
					}
					// stack = searchFinalNode(aNode2);
				}
				// System.out.println("stack 꺼내기");
				applyKnowledgeNode(historystack, outputStack, kGraph);
			}
			// for(int i =0; i<stack.size(); i++){
			// System.out.println(stack.pop().value+"스택");
			// }
			// if(!arFinalKNode2List.isEmpty())
			// System.out.println(arFinalKNode2List.get(0).value+"마지막 노드****");
			current.visited = true; // 그렇지 않으면 방문한 것으로 하고 즉, close에 넣고
			Node2 t;
			Edge2 s;
			Iterator<Edge2> iter = current.outEdge2.iterator();
			while (iter.hasNext()) {
				s = iter.next(); // outEdge
				t = s.to_Node2; // neighborNode
				checkAndUpdateCost(current, t, current.fScore // fscore of the
																// current node
						+ s.weight); // weight of the outEdge
			}
			iter = current.inEdge2.iterator();
			while (iter.hasNext()) {
				s = iter.next();
				t = s.from_Node2;
				checkAndUpdateCost(current, t, current.fScore + s.weight);
			}
			System.out.println();

			// DB정보 업데이트 했을 경우 이 노드 다시 탐색, 노드 다시 추가
			if (ConnectionAlgoDB.updateDB) {
				System.out.println("다시 탐색할 노드 추가");
				open.offer(current);

				AlgoGraphMaker algoGraphMaker = new AlgoGraphMaker();
				KGraphMaker kGraphMaker = new KGraphMaker();
				algoGraph = algoGraphMaker.init(algoGraph);
				kGraph = kGraphMaker.init(kGraph);
			}
			ConnectionAlgoDB.updateDB = false;

		}
		return outputStack.pop();

	}

	public static void pathFindingAStarWithEdge(Graph2 aGraph2, Node2 fromNode2, Node2 endNode2) {
		open.add(fromNode2);
		Node2 current;
		while (true) {
			current = open.poll();
			if (current == null)
				break;
			current.visited = true;

			if (current.equals(endNode2)) {
				return;
			}

			Node2 t;
			Edge2 s;
			Iterator<Edge2> iter = current.outEdge2.iterator();
			while (iter.hasNext()) {
				s = iter.next(); // outEdge
				t = s.to_Node2; // neighborNode
				checkAndUpdateCost(s, current, t, current.fScore // fscore of
																	// the
																	// current
																	// node
						+ s.weight); // weight of the outEdge

			}
			iter = current.inEdge2.iterator();
			while (iter.hasNext()) {
				s = iter.next(); // inEdge
				t = s.from_Node2; // neighborNode
				checkAndUpdateCost(s, current, t, current.fScore + s.weight);
			}
		}
	}

	public static void pathFindingAStarWithEdgeAndKE(Graph2 aGraph2, Node2 fromNode2, Node2 endNode2) {
		open.add(fromNode2);
		Node2 current;
		while (true) {
			current = open.poll(); // 확장할 노드
			// 선결조건을 여기에 즉, 이전의 것이 방문했으면 오케이, 그렇지 않으면 거기서 다시
			if (current == null) // current가 null이면 jumping을 시도한다. 즉, 지식 그래프에
									// 접속해서 새로운 pathFindingAStar를 시도한
			{
				break;
			}
			current.visited = true; // 그렇지 않으면 방문한 것으로 하고 즉, close에 넣고

			if (current.equals(endNode2)) { // 현재 노드가 최종노드이면 마치고
				return;
			}

			Node2 t;
			Edge2 s;
			Iterator<Edge2> iter = current.outEdge2.iterator();
			while (iter.hasNext()) {
				s = iter.next(); // outEdge
				t = s.to_Node2; // neighborNode
				checkAndUpdateCost(s, current, t, current.fScore // fscore of
																	// the
																	// current
																	// node
						+ s.weight); // weight of the outEdge

			}
			iter = current.inEdge2.iterator();
			while (iter.hasNext()) {
				s = iter.next(); // inEdge
				t = s.from_Node2; // neighborNode
				checkAndUpdateCost(s, current, t, current.fScore + s.weight);
			}
		}
	}

	static void checkAndUpdateCost(Node2 current, Node2 t, double cost) {
		if (t == null || t.visited)
			return;// t노드가 없거나. || t를 방문한적이 있었다면 리턴
		double t_final_cost = t.hScore + cost;

		boolean inOpen = open.contains(t);
		if (!inOpen || t_final_cost < t.fScore) {// t칸이 현재 open큐에 없다 || t의 휴리스틱
													// + cost의 비용<t의 현재까지 계산한
													// fscore
			t.fScore = t_final_cost;
			t.parent = current;
			if (!inOpen)
				open.add(t);
		}
	}

	static void checkAndUpdateCost(Edge2 iter, Node2 current, Node2 t, double cost) {
		if (t == null || t.visited)
			return;// t노드가 없거나. || t를 방문한적이 있었다면 리턴
		double t_final_cost = t.hScore + cost;

		boolean inOpen = open.contains(t);
		if (!inOpen || t_final_cost < t.fScore) {// t칸이 현재 open큐에 없다 || t의 휴리스틱
													// + cost의 비용<t의 현재까지 계산한
													// fscore
			t.fScore = t_final_cost;

			// t.parent = current;
			iter.edge_Node2.parent = current;
			t.parent = iter.edge_Node2;
			if (!inOpen)
				open.add(t);
		}
	}

	public static void printGraph(Graph2 aGraph) {
		for (int i = 0; i < aGraph.Node2List.size(); i++) {
			System.out.print(((Node2) aGraph.Node2List.get(i)).value + " 와 연결된 노드 : ");
			for (int j = 0; j < ((Node2) aGraph.Node2List.get(i)).outEdge2.size(); j++) {
				if (!((Node2) aGraph.Node2List.get(i)).outEdge2.isEmpty())
					System.out.print(((Node2) aGraph.Node2List.get(i)).outEdge2.get(j).to_Node2.value + " ");
			}
			for (int j = 0; j < ((Node2) aGraph.Node2List.get(i)).inEdge2.size(); j++) {
				if (!((Node2) aGraph.Node2List.get(i)).inEdge2.isEmpty())
					System.out.print(((Node2) aGraph.Node2List.get(i)).inEdge2.get(j).from_Node2.value + " ");
			}
			System.out.println();
		}
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		int Node2sNu = 7;
		Graph2 graph2 = new Graph2<Node2, Edge2>();

		Node2 fromNode2 = (Node2) graph2.Node2List.get(0);
		Node2 endNode2 = (Node2) graph2.Node2List.get(7);

		open = new PriorityQueue<Node2>(new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				Node2 c1 = (Node2) o1;
				Node2 c2 = (Node2) o2;

				return c1.fScore < c2.fScore ? -1 : c1.fScore > c2.fScore ? 1 : 0;
			}
		});

		// Graph2.pathFindingAStar(graph2, fromNode2, endNode2);
		//
		// System.out.print(endNode2.value);

		Node2 temp = endNode2.parent;
		// while (temp != null) {
		// System.out.print(" -> " + temp.value);
		// temp = temp.parent;
		// }
		//
		// graph2.init();
		//
		//// open.removeAll(open);
		// open.clear();

		// Graph2.pathFindingAStar(graph2, fromNode2, endNode2);
		// System.out.print(endNode2.value);

		// Graph2.pathFindingAStar(graph2, endNode2, fromNode2);
		System.out.print(fromNode2.value);

		temp = fromNode2.parent;
		while (temp != null) {
			System.out.print(" -> " + temp.value);
			temp = temp.parent;
		}
	}
}

class inputClass {
	HashMap<Object, Object> inArgs;

	public void in() {

	}

}

class outputClass {
	void out() {
	}

	HashMap<Object, Object> inArgs;
}