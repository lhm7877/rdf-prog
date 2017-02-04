package tutorial;

import japa.parser.ASTHelper;
import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.stmt.TypeDeclarationStmt;
import japa.parser.ast.type.Type;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class parsetree_test {

	public parsetree_test() {
		// TODO Auto-generated constructor stub
	}

    public static void main(String[] args) throws Exception {
        // creates an input stream for the file to be parsed
//        FileInputStream in = new FileInputStream("/Users/sunghee/Documents/sunghee-data/2015programs/workspace-java/rdf-prog/src/InfoExtractWithKE.java");
        FileInputStream in = new FileInputStream("/Users/sunghee/Documents/sunghee-data/2015programs/workspace-java/rdf-prog/src/InfoExtractWithKE.java");

        CompilationUnit cu;
        try {
            // parse the file
            cu = JavaParser.parse(in);
        } finally {
            in.close();
        }

        System.out.println("*****************\n*** Now visiting all methods...\n*********************");
        // visit and print the methods names
        new MethodVisitor().visit(cu, null);

        System.out.println("*****************\n*** Now 현재 소스를 출력하라...\n*********************");
        
        // prints the resulting compilation unit to default system output
      System.out.println(cu.toString());
   
      System.out.println("*****************\n*** MethodChangerVisitor() 생성자를 제외한 모든 함수를 대문자로 바꾸고 각 입력인자에 int value를 포함시킨 함수를 출력하라...\n*********************");
      
      // visit and change the methods names and parameters
      // 생성자를 제외한 모든 함수를 대문자로 바꾸고 각 입력인자에 int value를 포함시키는 변경작업을 수행하라.
      new MethodChangerVisitor().visit(cu, null);
      System.out.println("*****************\n*** Now 현재 소스를 출력하라...\n*********************");

      // prints the resulting compilation unit to default system output
      System.out.println(cu.toString());
      
      // change the methods names and parameters
      System.out.println("*****************\n*** Now changeMethods(cu) 변경하라...\n*********************");
      String[] fromKnow={"f"};
      String[] toKnow={"g","h"};
      changeMethods(cu,fromKnow,toKnow);
      System.out.println("*****************\n*** Now 변경된 함수 출력하라...\n*********************");

      // prints the changed compilation unit
      System.out.println(cu.toString());
      System.out.println("*****************\n*** Now 변경된 함수를 출력하라 ...\n*********************");
      
      
      // creates the compilation unit
      CompilationUnit cu2 = createCU();

      // prints the created compilation unit
      System.out.println(cu2.toString());
    }

    /**
     * Simple visitor implementation for visiting MethodDeclaration nodes. 
     */
    private static class MethodVisitor extends VoidVisitorAdapter {

        @Override
        public void visit(MethodDeclaration n, Object arg) {
            // here you can access the attributes of the method.
            // this method will be called for all methods in this 
            // CompilationUnit, including inner class methods
            System.out.println(n.getName());
        }
    }

    /**
     * Simple visitor implementation for visiting MethodDeclaration nodes.
     */
    private static class MethodChangerVisitor extends VoidVisitorAdapter {

        @Override
        public void visit(MethodDeclaration n, Object arg) {
            // change the name of the method to upper case
            n.setName(n.getName().toUpperCase());

            // create the new parameter
            Parameter newArg = ASTHelper.createParameter(ASTHelper.INT_TYPE, "v");

            // add the parameter to the method
            ASTHelper.addParameter(n, newArg);
        }

    }
    
    /* CompilationUnit으로 인자를 받아서 */
    /* 									*/
    private static void changeMethods(CompilationUnit cu, String[] fromKnow, String[] toKnow) {
        List<TypeDeclaration> types = cu.getTypes();
        System.out.print("\ntypes****\n"+types.toString()+"\ntypes-----\n");
        for (TypeDeclaration type : types) {
            System.out.print("\ntype****\n"+type.toString()+"\ntype-----\n");
            List<BodyDeclaration> members = type.getMembers();
            for (BodyDeclaration member : members) {
                System.out.print("\nmember****\n"+member.toString()+"\nmember-----\n");
                if (member instanceof MethodDeclaration) {
                    MethodDeclaration method = (MethodDeclaration) member;
                    System.out.print("\nmethod****\n"+method.toString()+"\nmethod-----\n");                    
                    if (method.getName() == fromKnow[0])
                    {
                    	replaceMethod(method, toKnow);
                    	System.out.println("replaceMethods");
                    }
                    else
                    {
                    	changeMethod(method);
                    	System.out.println("**changeMethods");
                    }
                }
            }
        }
    }

    private static void replaceMethod(MethodDeclaration n, String[] toKnow)
    {
    	n.setName(n.getName().toLowerCase());
    	
    }
    private static void changeMethod(MethodDeclaration n) {
        // change the name of the method to upper case
        n.setName(n.getName().toLowerCase());

        // create the new parameter
        Parameter newArg = ASTHelper.createParameter(ASTHelper.INT_TYPE, "valueWoVisitor");
//        BlockStmt block = new BlockStmt();
//        Statement expr = block.setStatement;
//        FieldDeclaration fd = ASTHelper.createFieldDeclaration(0, ASTHelper.CHAR_TYPE, "gender");
//        ASTHelper.add.addMember(type, decl);

        // add the parameter to the method
        ASTHelper.addParameter(n, newArg);
    }
    /**
     * creates a method
     * @return
     */
    private static void addMethodtoCU (MethodDeclaration mc) {

        // create a method
        MethodDeclaration method = new MethodDeclaration(ModifierSet.PUBLIC, ASTHelper.VOID_TYPE, "main");
        method.setModifiers(ModifierSet.addModifier(method.getModifiers(), ModifierSet.STATIC));
        ASTHelper.addMember(null, method);
    	
    }
    
    /**
     * creates the compilation unit
     */
    private static CompilationUnit createCU() {
        CompilationUnit cu = new CompilationUnit();
        // set the package
        cu.setPackage(new PackageDeclaration(ASTHelper.createNameExpr("java.parser.test")));

        // create the type declaration 
        ClassOrInterfaceDeclaration type = new ClassOrInterfaceDeclaration(ModifierSet.PUBLIC, false, "GeneratedClass");
        ASTHelper.addTypeDeclaration(cu, type);

        // create a method
        MethodDeclaration method = new MethodDeclaration(ModifierSet.PUBLIC, ASTHelper.VOID_TYPE, "main");
        method.setModifiers(ModifierSet.addModifier(method.getModifiers(), ModifierSet.STATIC));
        ASTHelper.addMember(type, method);

        // add a parameter to the method
        Parameter param = ASTHelper.createParameter(ASTHelper.createReferenceType("String", 0), "args");
        param.setVarArgs(true);
        ASTHelper.addParameter(method, param);

        // add a body to the method
        BlockStmt block = new BlockStmt();
        method.setBody(block);

        // add a statement do the method body
        NameExpr clazz = new NameExpr("System");
        FieldAccessExpr field = new FieldAccessExpr(clazz, "out");
        MethodCallExpr call = new MethodCallExpr(field, "println");
        ASTHelper.addArgument(call, new StringLiteralExpr("Hello World!"));
        ASTHelper.addStmt(block, call);
        
        // add a statement do the method body
//        NameExpr clazz = new NameExpr("System");
//        FieldAccessExpr field = new FieldAccessExpr(clazz, "out");
//        MethodCallExpr call = new MethodCallExpr(field, "println");
//        ASTHelper.addArgument(call, new StringLiteralExpr("Hello World!"));
        
        
//      TypeDeclarationStmt expr = new TypeDeclarationStmt(type); 
//      Statement stmt = new Statement();
//        ASTHelper.addStmt(block, stmt);//.addStmt(block, expr);

        
        
        VariableDeclarator varDecl = new VariableDeclarator(new VariableDeclaratorId("gender"));
        FieldDeclaration fieldDecl = new FieldDeclaration(ModifierSet.PUBLIC, ASTHelper.VOID_TYPE, varDecl);
        ASTHelper.addMember(type, fieldDecl);
        
//        VariableDeclarator c = new VariableDeclarator();
        List<VariableDeclarator> vars  = new ArrayList<VariableDeclarator>();
        vars.add(varDecl);
        VariableDeclarationExpr expr = new VariableDeclarationExpr(ASTHelper.VOID_TYPE,vars);
        ASTHelper.addStmt(block, expr);
        

        return cu;
    }
}
