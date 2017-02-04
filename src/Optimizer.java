import ke.Graph;

import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.functions.LinearMultivariateRealFunction;
import com.joptimizer.optimizers.JOptimizer;
import com.joptimizer.optimizers.LPOptimizationRequest;
import com.joptimizer.optimizers.LPPrimalDualMethod;
import com.joptimizer.optimizers.NewtonLEConstrainedFSP;
import com.joptimizer.optimizers.OptimizationRequest;



public class Optimizer {


	Graph gr;
	
	Objectives obj;
	
	public Optimizer() {
		// TODO Auto-generated constructor stub
		
	}

	public static void optimize()
	{
		// 1. define Objectives
		// 2. 
	}
	
	public static void main(String[] args)
	{
		//Objective function
		double[] c = new double[] { -1., -1. };
		
		//Inequalities constraints
		double[][] G = new double[][] {{4./3., -1}, {-1./2., 1.}, {-2., -1.}, {1./3., 1.}};
		double[] h = new double[] {2., 1./2., 2., 1./2.};
		
		//Bounds on variables
		double[] lb = new double[] {0. , 0.};
		double[] ub = new double[] {10., 10.};
		
		//optimization problem
		LPOptimizationRequest or = new LPOptimizationRequest();
		or.setC(c);
		or.setG(G);
		or.setH(h);
		or.setLb(lb);
		or.setUb(ub);
		or.setDumpProblem(true); 
		
		//optimization
		LPPrimalDualMethod opt = new LPPrimalDualMethod();
		
		opt.setLPOptimizationRequest(or);
		try {
			int returnCode = opt.optimize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double[] sol = opt.getOptimizationResponse().getSolution();
		System.out.println("sol[0] = "+(Double)(sol[0]));
		System.out.println("sol[1] = "+(Double)(sol[1]));
	}
}
