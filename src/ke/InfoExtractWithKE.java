package ke;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class InfoExtractWithKE implements KE1 {

	public InfoExtractWithKE() {
		// TODO Auto-generated constructor stub
	}
	
	public InfoExtractWithKE(RefSet a) {
		// TODO Auto-generated constructor stub

	}


	public void infoExtract(RefSet set) {
		// 새로운 클래스, 함수를 만들어 내는 함수
		// evolution: 지식을 받아들여 스스로를 변형함
		System.out.print("지금 함수infoExtract 실행 중");
	}

	public void f() {
		System.out.print("지금 f함수 실행 중");
	}

	// 외부지식에 의해서 실제 알고리즘 함수를 출력할 수 있는 함수
	private void knowledgeEmbedding(String[] fromKnow, String[] toKnow) {

		// 현재 클래스의 모든 함수 정보를 출력하는 코드
		System.out.println(this.getClass().getName());
		try {
			Class c = this.getClass();
			Field[] fd =c.getDeclaredFields();
			Method[] m = c.getDeclaredMethods();
			for (int i = 0; i < m.length; i++) {
				if (m[i].getName() == fromKnow[0].toString())
				{	
					System.out.println(m[i].getName());
					System.out.println(" will be replaced by "+toKnow[0]+", "+toKnow[1]);
				}
				else
				{
//					System.out.println(fd[i].toString());										
					System.out.println(m[i].getName());					
				}
					
			}
		} catch (Throwable e) {
			System.err.println(e);
		}

		// 현재 클래스의 코드를 새롭게 꾸미는 부분. 특히, 여기는 외부 지식을 통해서 내부를 변경하는 부분이 포함된다.
		String str = "public class "
				+ this.getClass().getName()
				+ "_x\n" // 클래스 선언
				+ "{ \n"
				+ "	public "
				+ this.getClass().getName()
				+ "_x() {\n" // 클래스 생성자
				+ "} \n"
				// for (int i = 0; i < toKnow.size(); i++)
				// retrieveSourcesFromDB(빼기);
				+ "	public void "+ toKnow[0]+"() {\n" // 외부지식에서 제시된 함수 < 사실 이 부분은 DB에서 검색을 통해
											// 가져와야 하는 부분
				+ "		System.out.println(\"지금 "+toKnow[0]+"함수 실행 중\");\n"
				+ "} \n"
				+ "	public void "+ toKnow[1]+"() {\n" // 외부지식에서 제시된 함수 정의 <- 사실 이 부분은
											// 데이터베이스에서 검색을 통해 가져와야 하는 부분
				+ "		System.out.println(\"지금 "+toKnow[1]+"함수 실행 중\");\n"
				+ "} \n"
				+ "	public static void main(String[] args) {\n" // 메인함수
				+ "		" + this.getClass().getName() + "_x aThisClass = new "
				+ this.getClass().getName() + "_x();\n" // 현재 클래스 생성
				+ "     aThisClass."+toKnow[0]+"();\n" // 외부지식에서 제시된 함
				+ "     aThisClass."+toKnow[1]+"();\n" + "	}\n" + "}";
		// System.out.print(str);
		// File Writing
		// FileInputStream f = new FileInputStream("i");
		// FileOutputStream outfile = new
		// FileOutputStream(this.getClass().getName()+"_x.java");
		try {
			// str을 파일에 쓴다.
			FileWriter os = new FileWriter(this.getClass().getName()
					+ "_x.java");
			for (int x = 0; x < str.length(); x++) {
				os.write(str.charAt(x)); // writes the bytes
			}
			os.close();
			// 쓴 파일을 읽어서 출력한다.
			FileReader is = new FileReader(this.getClass().getName()
					+ "_x.java");
			int c;// = is..read.available();

			while ((c = is.read()) != -1) {
				System.out.print((char) c);
			}
			// for(int i=0; i< size; i++){
			// System.out.print((char)is.read() + "");
			// }
			is.close();
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}
		// 현재 클래스의 패스
		String algoPath = this.getClass().getClassLoader().getResource("")
				.getPath();
		// 현재 클래스에 이름을 바꿈
		String algoName = this.getClass().getName() + "_x.java";

		// 현재 작업 디렉토리 출력
		String workingdirectory = System.getProperty("user.dir");
		System.out.println("Current working dirctory: " + workingdirectory);

		// 현재 클래스를 자바 컴파일 명령어 생성
		String cmd = "javac " + workingdirectory + "/" + algoName;

		System.out.println("algoPath+algoName:" + algoPath + algoName);
		System.out.println("workingdirectory+algoName" + workingdirectory
				+ "/src/" + algoName);
		System.out.println("cmd:" + cmd);
		try {
			// 현재 클래스 컴파일
			ShellCommander.shellCmd(cmd);
			// cmd = "java -cp " +algoPath.subSequence(0,
			// algoPath.lastIndexOf("/")) + " "+algoName.subSequence(0,
			// algoName.indexOf(".java")) ;
			
			// 현재 클래스 실행 명령어 생성
			cmd = "java -cp " + workingdirectory + "/ "
					+ algoName.subSequence(0, algoName.indexOf(".java"));
			System.out.println(cmd + "\n");
			// 현재 클래스 실행
			ShellCommander.shellCmd(cmd);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}


	public static void main(String[] args) {
		ArrayList<인자유형> CORA = new ArrayList<인자유형>();

		//입력인자유형의 배열리스트에 각 ref의 이름과 StyleID를 세팅한다. 
		// 예를 들어, ref이름: ref0, styleID: 1
		for (int i = 0; i < 5; i++) {
			Ref aRef = new Ref("ref" + i, i % 2 + 1);
			CORA.add(aRef);
		}

		RefSet a = new RefSet();

		a.인자분할(CORA); // Style ID 에 따라 입력 자료를 분할한다. 곧, 하나의 classifier에 해당한다. 

		HashMap<Integer, ArrayList<인자유형>> resultSplitted;

		resultSplitted = a.splitted_set_t;

		// 인자분할이 제대로 이루어졌는지 RefSet a 출력하기
		for (Integer i : resultSplitted.keySet()) 
		{
			System.out.print("style " + i + ":");

			// 각 스타일별
			int j = resultSplitted.get(i).size();
			//ArrayList<인자유형>
			
			for (인자유형 k : resultSplitted.get(i)) {
				System.out.print(k.refValue + ",");
			}
			System.out.println("");

		}

		// create an info extraction class using knowledge embedding
		InfoExtractWithKE aIE = new InfoExtractWithKE();

		aIE.infoExtract(a);
		// where can we get this external knowledge 
		String[] fromKnow = { "f" }; // this is the original path
		String[] toKnow = { "g", "h" }; // this would be another path. this can be found by 'Redescription' data mining..

		// knowledge embedding: executing info extraction class
		aIE.knowledgeEmbedding(fromKnow, toKnow);

	}

}
