package ke;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InfoExtract {
	public static void InfoExtract(String aRef, CRFmodel b) {

//		System.out.println("지금 mallet 알고리즘 실행 중");
		// in case of mallet ...we need a seq. labeler
		String command = "java -cp  \"C:/mallet/class;C:/mallet/lib/mallet-deps.jar\"" // class path
				+ " cc.mallet.fst.SimpleTagger " // class to be executed
				+ " --train true --model-file C:/mallet/nouncrf  " // model to be trained
				+ "C:/mallet/sample"; // training data
		executeSystemCommand(command);

		String command2 = "java -cp  \"C:/mallet/class;C:/mallet/lib/mallet-deps.jar\"" // class path
				+ " cc.mallet.fst.SimpleTagger "
				+ " --model-file C:/mallet/nouncrf "
				+ " C:/mallet/stest";

		executeSystemCommand(command2);

	}

	public static double evaluate() {
		 try {
		      BufferedReader in = new BufferedReader(new FileReader("C:/mallet/stest"));
		      String s;
		      
		      while ((s = in.readLine()) != null) {
		        System.out.println(s);
		      }
		      in.close();
		    } catch (IOException e) {
		        System.err.println(e);
		    }
		return 0;
	}

	public static void executeSystemCommand(String s) {
		// String s= null;

		try {

			// run the Unix "ps -ef" command
			// using the Runtime exec method:
			Process p = Runtime.getRuntime().exec(s);
			// +
			// "/Users/sunghee/Documents/sunghee-data/2016programs/mallet-2.0.7/bin/mallet
			// ");

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			// read the output from the command
//			System.out.println("!!!!Here is the standard output of the command:\n");
//			while ((s = stdInput.readLine()) != null) {
//				System.out.println(s);
//			}
//
//			// read any errors from the attempted command
//			System.out.println("Here is the standard error of the command (if any):\n");
//			while ((s = stdError.readLine()) != null) {
//				System.out.println(s);
//			}

//			System.exit(0);
		} catch (IOException e) {
			System.out.println("exception happened - here's what I know: ");
			e.printStackTrace();
//			System.exit(-1);
		}
	}

	public static void main(String[] args)

	{

		// infoExtract aThisClass = new infoExtract();

		// aThisClass.infoExtract(1,2);
		RefSet aRefSet = new RefSet();
		CRFmodel aCRFmodel = new CRFmodel();
		InfoExtract aInfoEx = new InfoExtract();
		aInfoEx.InfoExtract(aRefSet, aCRFmodel);
	}

}