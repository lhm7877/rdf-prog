package ke;
/* created by Sung Hee May 21, 2016 */



import java.io.*;
import java.util.*;

class Node 
{
	int id;
	String value;
	ArrayList<Edge> inEdge;  // inbound
	ArrayList<Edge> outEdge; // outbound
	boolean visited;
	
	Node(int id, String value)
	{
		this.id = id;
		this.value = value;
		inEdge = new ArrayList<Edge>();
		outEdge = new ArrayList<Edge>();	
		this.visited = false;
	}
}

class Edge
{
	int id;
	Node from_node;
	Node edgeNode;
	
	HashMap<String, String> input;
	HashMap<String, String> output;
	
	Node to_node;
	String value;
	boolean visited=false;
	
	Edge(int id, String value)
	{
		this.id = id;
		this.value = value;
		this.visited = false;
	}
}

class Triple
{
	//String value;
	Node sub;
	Edge pred;
	Node obj;
	
	Triple(Node sub, Edge pred, Node obj)
	{
		this.sub = sub;
		this.pred = pred;
		this.obj = obj;
	}
}

class RDFGraph
{
	
	

}
// 문제의 난이도 차이
// 1. 그래프를 만들어 주는 것
// 2. 그래프를 자동으로 만드는 것
// 문제 세팅


public class Graph<V, E> {

	
	ArrayList<Node> nodeList;
	ArrayList<Edge> edgeList;
	ArrayList<Triple> tripleList;
	
	public Graph() {
		// TODO Auto-generated constructor stub
		nodeList = new ArrayList<Node>();
		edgeList = new ArrayList<Edge>();
		tripleList = new ArrayList<Triple>();
		
		
	}
	
// 가능 경로를 찾는다.
// 
	ArrayList<ArrayList<Node>> findShortestPath(Node start, Node end, Graph<V,E> a)
	{
		
		ArrayList<Node> path = new ArrayList<Node>();
		ArrayList<ArrayList<Node>> paths = new ArrayList<ArrayList<Node>>();
		
//		for (int i = 0; i < start.outEdge.size(); i++)
//		{
//			start.outEdge.get(0)
//		}
		Iterator itr = start.outEdge.iterator();
		
		while(itr.hasNext()) {
		    Edge element = (Edge)itr.next();
		    System.out.print(element.from_node.id + " ");
		    System.out.print(element.to_node.id + " ");
		    
		    path.add(start);
		}
		
	    System.out.println();
		System.out.println ("Here is findShortestPath");
		paths.add(path);
		return paths;
	}
	
	boolean evaluate(ArrayList<Integer> path)
	{
		Iterator itr = edgeList.iterator();
		
		while(itr.hasNext()) {
		    Edge element = (Edge)itr.next();
			if (element.value=="나누기")
			{
			}
			else {
			    System.out.print(element.from_node.id + " ");
			    System.out.print(element.to_node.id + " ");				
			}
		}
		
	    System.out.println();
		System.out.println ("Here is findShortestPath");
		
		return true;
	}
	// Triple을 네트워크에 삽입하는 기능이다.
	boolean addTriple(String subj, String pred, String obj)
	{
		
		// 각 토큰이 기존에 해당하는 노드나 에지가 있는지 확인한다.
//		search();

		// 노드 생성 
//		for (int i = 0; i < 2; i++)
		Node a = new Node(nodeList.size(),subj);
		nodeList.add(a);
		Node b = new Node(nodeList.size(),obj);
		nodeList.add(b);
		
		// 에지 생성
		Edge c = new Edge(edgeList.size(),pred);
		c.from_node = a;
		c.to_node = b;
		edgeList.add(c);
				
		// 노드에 이웃 에지 저장
		a.outEdge.add(c);
		b.inEdge.add(c);

		Triple tp = new Triple(a, c, b);
		tripleList.add(tp);
		
		return true;
	}
	
	void getTriple()
	{
		for (Triple tri : tripleList)
		{
			System.out.println(tri.sub.value + "," + tri.pred.value +"," + tri.obj.value);
			
		}
		
	}
	// Breadth First Search
	void BFS(Node start)
	{
		Queue<Node> qu = new LinkedList<Node>();
		System.out.println("Node:"+start.value);
		Iterator itr = start.outEdge.iterator();
		
		start.visited = true;
		qu.add(start);
//		Node a = qu.remove();
		while (!qu.isEmpty())
		{
			Node a = qu.remove();
			for (Edge aEdge : a.outEdge)
			{
					if (!aEdge.to_node.visited)
					{
						aEdge.to_node.visited = true;
						System.out.println ("Node "+a.value +"에서 "+ aEdge.value + "를 거쳐 Node "+aEdge.to_node.value + "로 이동");
					}
				}
			}
		}
	
	// Depth First Search
	void DFS(Node start)
	{
		System.out.println("Node:"+start.value);
		Iterator itr = start.outEdge.iterator();
		
		while(itr.hasNext()) {
		    Edge element = (Edge)itr.next();
			System.out.println("Edge:"+element.value);

			System.out.println("Node:"+element.to_node.value);
			
		}				
	}
	
	void findShortestPathAllowingJumping()
	{
		// find the jumping node
		// minimizing the penalty function
		// A* algorithm
		// 가정 1: 그래프가 존재한다
		// 가정 2: 시작노드와 목표노드가 주어진다. 그래서, 시작노드가 무엇인지 목표노드가 무엇인지 알 수 있다.
		// 1. 시작노드를 Open에 넣는다. 시작노드의 penalty 함수 식에 따라 계산한다.
		// 2. Open에 노드가 남아 있는 동안 다음을 반복한다.
		//    2.1 Open에서 예측Penalty ^p값이 최소인 노드를 꺼내어 Closed에 넣는다. 이 노드를 n이라한다. 만일 동일한 예측 penalty ^p를 가지고 있는 노드가 여러 개 있을 때에는 
		//        임의로 선택하되 목표노드가 있다면 우선적으로 선택한다.
		//    2.2 노드 n이 목표노드라면 탐색은 성공적으로 끝난다. 포인터를 역으로 추적하면 탐색경로를 얻을 수 있다.
		//    2.3 노드 n을 확장하여 후계노드 n1, n2, …, ni를 생성한다. 이들 후계 노드에 부노모드인 노드 n을 가리키는 포인터를 첨부한다.
		//    2.4 각각의 후계노드에 대한 예측 penalty 함수, ^p(n1), ^p(n2),…, ^p(ni)를 계산하여 (어디에?) 첨부한다.
		//    2.5 각각의 후계노드 nk, k = 1, 2, …, i 에 대하여
		//       2.5.1 동일한 노드가 Open에 이미 존재한다면 (그 노드를 nold라 하자)
		//          2.5.1.1 ^p(nold)가 ^p(nk)보다 작다면 nk는 버린다.
		//          2.5.1.2 그렇지 않으면, nold를 open에서 제거한다.
		//       2.5.2 동일한 노드가 Closed에 이미 존재한다면 (그 노드를 n’old라 하자)
		//          2.5.2.1 ^p(n’old)가 ^p(nk)보다 작다면 nk는 버린다.
		//          2.5.2.2 그렇지 않으면, n’old의 부모포인터가 노드 n을 가리키도록 수정하고 평가함수를 ^p(nk)으로 수정한다. 또한 n’old의 모든 후계노드에 대한 경로비용 g가 변화하였으므로 이를 수정한다.
		//       2.5.3 동일한 노드가 Open이나 Closed에 존재하지 않으면 nk를 Open에 삽입한다.
		// 3. 탐색은 실패로 끝난다.		
		
		// 1. 시작노드를 Open에 넣는다. 시작노드의 penalty 함수 식에 따라 계산한다.
		RDFGraph Open;
//		Open.addTriple(subj, pred, obj)
		// 2. Open에 노드가 남아 있는 동안 다음을 반복한다.

		// 2.1 Open에서 예측Penalty ^p값이 최소인 노드를 꺼내어 Closed에 넣는다. 이 노드를 n이라한다. 만일 동일한 예측 penalty ^p를 가지고 있는 노드가 여러 개 있을 때에는 임의로 선택하되 목표노드가 있다면 우선적으로 선택한다.

		// 2.2 노드 n이 목표노드라면 탐색은 성공적으로 끝난다. 포인터를 역으로 추적하면 탐색경로를 얻을 수 있다.
		// 2.3 노드 n을 확장하여 후계노드 n1, n2, …, ni를 생성한다. 이들 후계 노드에 부노모드인 노드 n을 가리키는 포인터를 첨부한다.
		// 2.4 각각의 후계노드에 대한 예측 penalty 함수, ^p(n1), ^p(n2),…, ^p(ni)를 계산하여 첨부한다.
		// 2.5 각각의 후계노드 nk, k = 1, 2, …, i 에 대하여
		// 2.5.1 동일한 노드가 Open에 이미 존재한다면 (그 노드를 nold라 하자)
		// 2.5.1.1 ^p(nold)가 ^p(nk)보다 작다면 nk는 버린다.
		// 2.5.1.2 그렇지 않으면, nold를 open에서 제거한다.
		// 2.5.2 동일한 노드가 Closed에 이미 존재한다면 (그 노드를 n’old라 하자)
		// 2.5.2.1 ^p(n’old)가 ^p(nk)보다 작다면 nk는 버린다.
		// 2.5.2.2 그렇지 않으면, n’old의 부모포인터가 노드 n을 가리키도록 수정하고 평가함수를 ^p(nk)으로 수정한다. 또한 n’old의 모든 후계노드에 대한 경로비용 g가 변화하였으므로 이를 수정한다.
		// 2.5.3 동일한 노드가 Open이나 Closed에 존재하지 않으면 nk를 Open에 삽입한다.
		// 3. 탐색은 실패로 끝난다.
		
		
		findJumpingNode();
		// 가져온 점핑노드를 나중을 위해서 hoppingBridge라는 graph에 넣는다.
		
		// hopping edge의 시작 노드와 끝노드를 설정하자
		
	}
	void findJumpingNode()
	{
		
	}
	

	void KE(Triple tp, Graph<Node,Edge> a)
	{
		
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 1. 문제해결 네트워크 하나를 읽어온다. "10 곱하기 2 는 X" 여기서 X는 최종목적지이다. 그런데, 길에 해당하는 '곱하기'길 직행이 없다면?
		// 여기서, 10이 숫자라는 것과, 2도 숫자, '곱하기'가 연산자라는 것을 그리고, X가 가야할 목적지라는 것을 알아야 한다. 이것을 자동으로 알아내야는 것은 정보추출분야이므로 추후에 따로 다룬다
		// 지금은 위의 것이 이미 트리플로 주어진다고 보자
		// 오직 "곱하기"만 중요한 것이다. 이 곱하기에 대한 정보를 찾아서 문제를 해결하도록 하자. 곱하기로부터 곱하기의 문제해결 네트워크를 구성해 보도록 하자. 
		// 즉, 곱하기 안에 네트워크가 있다. 만약 곱하기에 대한 정보가 없으면 그 때부터 
		// 1. 문제해결 네트워크 하나를 읽어온다. "나누기 피제수 10"
		// 2. 문제해결 네트워크 하나를 읽어온다. "나누기 제수 2"
		// 문제해결의 의미는 1) 경로 탐색과 2) 정해진 경로에서의 최종목적지 결정,이 두 가지이다. 
		// 그런데, 1) 정해진 경로에서 최종 목적지 결정에 대한 문제인데, 정해진 경로가 없고 다른 우회도로가 있다면?
		// 그것은 우회도로를 찾아서 최종 목적지에 도착하는 문제와 같다. 함수는 일단 길만 들어서면 그 길이 데려다 주는 곳이 문제해결의 끝이다.

		// 문제해결 네트워크를 만들자
		// 1. 먼저 그래프를 하나 생성하자 
		// 2. 그 속에 노드와 에지를 생성해서 넣자.
		// 3. 각 정보를 넣자.
		Graph<Node,Edge> a = new Graph<Node,Edge>();
		
		// 문제네트워크가 하나 들어오면, 여기서, "곱하기" 연산자를 구분해 내고 이 연산자를 계산하는 
		a.addTriple("10","나누기","2");
		// 피연산자 수 2개짜리 숫자인 연산자를 검색하라. 이것은 처음 가야할 edge를 찾는 것과 같다. 
		// 즉, edge가 하나로 정해졌느냐라고 했는데 정해지지 않았을 때, 후보 노드들을 찾게 된다.
		// 후보 노드들을 찾았을 때, 가장 적절한 노드로 가 본다. 여기서, A*알고리즘을 사용해 볼 수 있을 것이다. 
		// 즉, 각 후보들을 Node에 집어 넣고 하나씩 꺼내어 가장 Cost가 적게 드는 길을 고르는 것이다. 
		// 바로, Computelli에서 하는 작업이다.
		
		// 네트워크가 끝에 도착했다면 다른 길을 갈 수 있을지에 대해서 살펴본다.
		// Hopping Network
		// 그렇게 해서 하나의 지식네트워크(그래프)를 검색해 왔다면?
//		a.addTriple("나누기", "역", "곱하기"); // 나누기에 추가하여 
		a.addTriple("나누기", "제수", "10");
		a.addTriple("나누기", "피제수", "2");
		a.addTriple("나누기", "는", "X");
		
		// node = {"10", "2", "X"}
		// edge = {"나누기"

		a.getTriple();
		
		a.KE(new Triple(new Node(0, "나누기"),new Edge(0, "역"), new Node(1, "곱하기")), a);
//		a.addTriple("곱하기", "반복", "더하기");
		
		// 2. 외부지식 네트워크 하나를 읽어온다. "나누기 역 곱하기"
		// 2. 외부지식 네트워크 하나를 읽어온다. "나누기 피연산자개수 2"
		
//		a.DFS(a.nodeList.get(0));
		
		a.BFS(a.nodeList.get(0));
		
		
		a.addTriple("Cluster_A", "independent", "Cluster_B");
		
		
//		a.findShortestPath(a.nodeList.get(0), a.nodeList.get(1), a);
		
		
	}

}
