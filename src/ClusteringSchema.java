import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

class Relation
{
	String[] value={"dep","indep"};
	ArrayList<Cluster> clusters = new ArrayList<Cluster>();
}

class Country extends Object{
	
	int id;
	
	String name;
	double woe;
	ArrayList<Cluster> clusters = new ArrayList<Cluster>();
}

class Company extends Object{
	int id;
	
	String name;
	double revenue;
	Country countryLocated;
	int value;
}

class Prototype
{
	
}
class Clustering
{
	int id;
	String name;
		
	public void getDataFromDB(String tableName)
	{
		String dbUrl = "jdbc:mysql://127.0.0.1:3306/elab_dw?characterEncoding=UTF-8";
		String dbUsername = "librarian";
		String dbPassword = "40211";

		boolean output = false;

		Statement statement = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(dbUrl,
					dbUsername, dbPassword);
			statement = connection.createStatement();

			String str = "select * from Company_has_Country;";// where Name='" + queryOperator
//					+ "';";
			rs = statement.executeQuery(str);

//			System.out.println("query in existRelevantAlgorithm:" + queryOperator);
			

			int count = 0;
			
			try {
				rs.last(); count = rs.getRow(); rs.beforeFirst();
			} catch(Exception e) {
				System.err.println(e.getMessage()+"ARGH!!");
			}

			while (rs.next()) {
				String idCompany = rs.getString("Company_idCompany");
				String idCountry = rs.getString("Country_idCountry");
				System.out.println(idCompany + "\t" + idCountry + "\n");
				count++;
			}

			if (count != 0) {
				output = true;
			}

		} catch (SQLException e) {
			System.err.print(e.getMessage() + " ARGH!");
		} catch (Exception e) {
			System.err.print(e.getMessage() + " FUUUUUUUUUU!");
		}

	}
	public void K_means(int k)
	{
		Prototype aPrototype;
		
		
	}
	
	public void ClusteringBySoC()
	{
		
	}
	
}

class Cluster
{
	int dimension;
	ArrayList<Double> aCl=new ArrayList<Double>(); // 1, 2, 3
						   // 4, 5, 6
						   // 7, 8, 9
}

class CT // contingency table
{
	int dimension;
	ArrayList<Cluster> aCT;
}

class Objectives
{
	
	D_KL aD_KL= new D_KL();
	public double f(CT a, String relation)
	{
		double f_value=0;
		
		int k_x = a.aCT.get(0).aCl.size();
		int k_y = a.aCT.size();

		Cluster uniform_x = new Cluster();
		Cluster uniform_y = new Cluster();

		for (int i = 0; i < k_x; i++)
		{
			uniform_x.aCl.add(1./k_x);				
		}
		for (int j = 0; j < k_y; j++)
		{
			uniform_y.aCl.add(1./k_y);				
		}
		
		for (int j = 0; j < k_x; j++)
		{
			double sum_y = 0.0;
			
			for (int i = 0; i < k_y; i++)
			{
				sum_y += aD_KL.ComputeD_KL(a.aCT.get(0), uniform_x);
			}
		}
		return f_value;
	}
	
}

class D_KL
{
	public double ComputeD_KL(Cluster A, Cluster B)
	{
		double a = 0.0;
		
		for (int i = 0; i < A.aCl.size(); i++)
		{
			a += A.aCl.get(i) * Math.log (A.aCl.get(i)/B.aCl.get(i));
		}
		return a;
	}
}

public class ClusteringSchema {

	public ClusteringSchema() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 클러스터 생성 
		Cluster clu_A = new Cluster();
		Cluster clu_B = new Cluster();
		
		//생성된 클러스터링에 클러스터 중심 추가
		clu_A.aCl.add(1.);		
		clu_A.aCl.add(2.);
		clu_A.aCl.add(3.);
		
		clu_B.aCl.add(1.);		
		clu_B.aCl.add(2.);
		clu_B.aCl.add(3.);

		CT aCT = new CT();

		Clustering clustering_A = new Clustering();
		Clustering clustering_B = new Clustering();	

		clustering_A.getDataFromDB("Company");
		clustering_A.K_means(3);

		clustering_B.getDataFromDB("Country");
		clustering_B.K_means(3);
		Optimizer.optimize();
		
		System.out.println();
		
	}
}
