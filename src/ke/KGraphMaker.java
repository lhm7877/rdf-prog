package ke;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import addition.ConnectionAlgoDB;
import addition.Edge2;
import addition.Node2;

public class KGraphMaker {
	static Graph2 kgraph;
	static ResultSet rs = null;
//	static String queryOperator;

	public static void main(String[] args) {
//		queryOperator = "infoExtract";
		KGraphMaker kGraphMaker = new KGraphMaker();
		kgraph.printGraph(kgraph);
	}

	public Graph2 init(Graph2 kgraph) {
		rs = ConnectionAlgoDB.getRdfs();
		try {
			while (rs.next()) {
				// Node objectNode = new Node(0, rs.getString("object"), 0);
				// Node predicateNode = new Node(0,
				// rs.getString("predicate"),0);
				// Node subjectNode = new Node(0, rs.getString("subject"), 0);
				kgraph.addNode2(0, rs.getString("object"), 0,false);
				kgraph.addNode2(1, rs.getString("predicate"), 0,true);
				kgraph.addNode2(2, rs.getString("subject"), 0,false);
				Node2 fromNode = null, toNode = null, totoNode = null;
				for (int i = 0; i < kgraph.Node2List.size(); i++) {
					if (((Node2) (kgraph.Node2List.get(i))).value.equals(rs.getString("object"))) {
						fromNode = ((Node2) (kgraph.Node2List.get(i)));
					} else if (((Node2) (kgraph.Node2List.get(i))).value.equals(rs.getString("predicate"))) {
						toNode = ((Node2) (kgraph.Node2List.get(i)));
					} else if (((Node2) (kgraph.Node2List.get(i))).value.equals(rs.getString("subject"))) {
						totoNode = ((Node2) (kgraph.Node2List.get(i)));
					}
				}
				kgraph.addEdge2(0, "value", 0.0, fromNode, toNode);
				kgraph.addEdge2(0, "value", 0.0, toNode, totoNode);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		kgraph.addNode2(0, "Style2", 0.0,false);
//		Node2 tempfrom_Node2 = null, tempto_Node2 = null, tempto_to_Node2 = null;
//		for (int i = 0; i < kgraph.Node2List.size(); i++) {
//			if (((Node2) kgraph.Node2List.get(i)).value.equals("propertyOf")) {
//				tempfrom_Node2 = ((Node2) (kgraph.Node2List.get(i)));
//			} else if (((Node2) kgraph.Node2List.get(i)).value.equals("Style2")) {
//				tempto_Node2 = ((Node2) (kgraph.Node2List.get(i)));
//			} else if (((Node2) kgraph.Node2List.get(i)).value.equals("typeOf")) {
//				tempto_to_Node2 = ((Node2) (kgraph.Node2List.get(i)));
//			}
//		}
//		kgraph.addEdge2(0, "value", 0.0, tempfrom_Node2, tempto_Node2);
//		kgraph.addEdge2(0, "value", 0.0, tempto_Node2, tempto_to_Node2);
		return kgraph;
	}

	public Node2 findNode(String value) {
		Node2 node = null;
		Iterator<Node2> it = kgraph.Node2List.iterator();
		while (it.hasNext()) {
			node = it.next();
			if (node.value.equals(value)) {
				break;
			}
		}
		return node;
	}

	public Node2 travelNode(Node2 node) {
		if (node.outEdge2.isEmpty()) {
			return node;
		} else {
			for (Edge2 i : node.outEdge2) {
				System.out.println(i.from_Node2.value + "�� Ž����");
				if (ConnectionAlgoDB.existRelevantAlgorithm(node.value)) {
					return travelNode(i.to_Node2);
				}
			}
			return node;
		}
	}
}
