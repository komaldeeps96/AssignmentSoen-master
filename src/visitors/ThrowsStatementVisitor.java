package visitors;
import java.io.File;
import java.util.List;

import org.eclipse.jdt.core.dom.*;

import patterns.ExceptionFinder;

public class ThrowsStatementVisitor extends ASTVisitor {
	public static File f;
	public static int numOfThrowsGenericAP;
	public static int numOfThrowsKitchenSinkAP;

	public static File getF() {
		return f;
	}

	public static void setF(File f) {
		ThrowsStatementVisitor.f = f;
	}

	public static int getNumOfThrowsGenericAP() {
		return numOfThrowsGenericAP;
	}

	public static void setNumOfThrowsGenericAP(int numOfThrowsGenericAP) {
		ThrowsStatementVisitor.numOfThrowsGenericAP = numOfThrowsGenericAP;
	}

	public static int getNumOfThrowsKitchenSinkAP() {
		return numOfThrowsKitchenSinkAP;
	}

	public static void setNumOfThrowsKitchenSinkAP(int numOfThrowsKitchenSinkAP) {
		ThrowsStatementVisitor.numOfThrowsKitchenSinkAP = numOfThrowsKitchenSinkAP;
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		findThrowsGeneric(node);
		findThrowsKitchenSink(node);
		return super.visit(node);
	}


	public void findThrowsGeneric(MethodDeclaration node) {
		boolean throwsGenericeFlag = false;
		List<Name> l = node.thrownExceptions();
		int lssize = l.size();
		for(int i=0;i<lssize;i++){
			Expression e = (Expression)l.get(i);
			if(e.toString().trim().equals(new String("Exception"))){
				throwsGenericeFlag=true;
			}
		}
		if(throwsGenericeFlag)
		{
			numOfThrowsGenericAP++;
			ExceptionFinder.sbtxt.append("\n ***************ANTI-PATTERN : Throws Generic***************");
			ExceptionFinder.sbtxt.append("\n FILE NAME : " + f.getAbsolutePath());
			ExceptionFinder.sbtxt.append("\n Method  : " + node);
			ExceptionFinder.sbtxt.append("\n_________________________________________________________________________________\n");
		}

	}

	public void findThrowsKitchenSink(MethodDeclaration node) {
		List<Name> l = node.thrownExceptions();
		int lssize = l.size();
		if(lssize>1)
		{
			numOfThrowsKitchenSinkAP++;
			ExceptionFinder.sbtxt.append("\n ***************ANTI-PATTERN : Throws Kitchen Sink***************");
			ExceptionFinder.sbtxt.append("\n FILE NAME : " + f.getAbsolutePath());
			ExceptionFinder.sbtxt.append("\n Method : " + node);
			ExceptionFinder.sbtxt.append("\n_________________________________________________________________________________\n");
		}
	}

	private ASTNode findParentMethodDeclaration(ASTNode node) {
		if(node.getParent().getNodeType() == ASTNode.METHOD_DECLARATION) {
			return node.getParent();
		} else {
			return findParentMethodDeclaration(node.getParent());
		}
	}

	private MethodDeclaration findMethodForThrow(ThrowStatement node) {
		return (MethodDeclaration) findParentMethodDeclaration(node);
	}

}
