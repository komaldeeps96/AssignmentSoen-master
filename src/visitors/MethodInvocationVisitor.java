package visitors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import visitors.Node;
import patterns.ExceptionFinder;

public class MethodInvocationVisitor extends ASTVisitor{
	Set<MethodDeclaration> suspectDeclarations = new HashSet<MethodDeclaration>();
	HashSet<MethodInvocation> suspectInvocations = new HashSet<MethodInvocation>();
	List<String> exceptionNames = new LinkedList<>();
	List<Class>	classNames = new LinkedList<>();
	List<String> functionName = new LinkedList<>();
	
	public MethodInvocationVisitor(Set<MethodDeclaration> suspectDeclarations) {
		this.suspectDeclarations = suspectDeclarations;
	}
	
	@Override
	public boolean visit(MethodInvocation node) {
		if (node == null) {
			return super.visit(node);
		}
		IMethodBinding linkedDeclaration;
		
		try {
		linkedDeclaration = node.resolveMethodBinding().getMethodDeclaration();
		}
		catch (Exception e) {
			return super.visit(node);
		}
		
				ITypeBinding[] exceptionTypes = linkedDeclaration.getExceptionTypes();
				for (int i = 0; i < exceptionTypes.length; i++) {
					exceptionNames.add(exceptionTypes[i].getBinaryName());
					classNames.add(exceptionTypes[i].getClass());
				}
				functionName.add(node.getName().toString());
				
		
		for(MethodDeclaration suspectDeclaration: suspectDeclarations) {
			
			if(suspectDeclaration.resolveBinding().getMethodDeclaration().isEqualTo(linkedDeclaration)) {
				suspectInvocations.add(node);
			}
			
		}
		
		
		
		
		ArrayList<String> exceptionList = new ArrayList<String>();
		ITypeBinding iTypeBinding ;
		IMethodBinding iMethodBinding;
		IPackageBinding iPackageBinding;
		
		try {
	
			iTypeBinding = node.resolveMethodBinding().getDeclaringClass();
			 iMethodBinding = node.resolveMethodBinding().getMethodDeclaration();
			 iPackageBinding =	node.resolveMethodBinding().getMethodDeclaration().getDeclaringClass().getPackage();
			
		}
		catch (Exception e) {
			return super.visit(node);
		}
		String calledClass = iTypeBinding.getName();
		String calledMethod = iMethodBinding.toString();
		String calledPackages = iPackageBinding.getName();
		Node calledNode = new Node(calledMethod, calledClass, calledPackages);
		ASTNode astNode = node;
		MethodDeclaration methodDeclarationCall = null ;
		while(true) {
			if(astNode instanceof MethodDeclaration) {
				methodDeclarationCall = (MethodDeclaration)astNode;
				break;
			}
			else if(astNode instanceof TypeDeclaration) {
				return super.visit(node);//to check
			}		
			try {
				astNode = astNode.getParent();
			}
			catch (Exception ex) {
				return super.visit(node);//to check
			}
			

		}
		
		
		IMethodBinding bindingForCall = methodDeclarationCall.resolveBinding();
		ITypeBinding itbCall = methodDeclarationCall.resolveBinding().getDeclaringClass();
		IPackageBinding ipbCall = methodDeclarationCall.resolveBinding().getDeclaringClass().getPackage();
		
		String classNameCall = itbCall.getName();
		String methodNameCall = bindingForCall.toString();
		String packageNameCall = ipbCall.getName();
		Node nodeCall = new Node(methodNameCall, classNameCall, packageNameCall);

		
		if(ExceptionFinder.CallGraph.containsKey(nodeCall)) {
			Set<Node> adjCall = new HashSet<Node>();
			adjCall = ExceptionFinder.CallGraph.get(nodeCall);
			adjCall.add(calledNode);
			ExceptionFinder.CallGraph.put(nodeCall, adjCall);
			
		}
		else {
			Set<Node> adjCall = new HashSet<Node>();
			adjCall.add(calledNode);
			ExceptionFinder.CallGraph.put(nodeCall, adjCall);
		}
		

		if(!ExceptionFinder.CallGraph.containsKey(calledNode)) {
			Set<Node> adjCall = new HashSet<Node>();
			ExceptionFinder.CallGraph.put(calledNode, adjCall);
			
		}

		Set<String> setExceptionCall = new HashSet<String>();
		Set<String> setExceptionCalled = new HashSet<String>();
		try {
			setExceptionCall.addAll(CallstoRuntimeandNonRuntimeException.FindNonRuntimeExceptions(bindingForCall));
			setExceptionCall.addAll(CallstoRuntimeandNonRuntimeException.FindRuntimeExceptions(bindingForCall));
			
			setExceptionCalled.addAll(CallstoRuntimeandNonRuntimeException.FindNonRuntimeExceptions(iMethodBinding));
			setExceptionCalled.addAll(CallstoRuntimeandNonRuntimeException.FindRuntimeExceptions(iMethodBinding));
			
		}
		catch(JavaModelException ex) {
			
		}

		if(ExceptionFinder.ExceptionMap.containsKey(calledNode)) {
			Set<String> tempSet = new HashSet<String>();
			tempSet = ExceptionFinder.ExceptionMap.get(calledNode);
			tempSet.addAll(setExceptionCalled);
			ExceptionFinder.ExceptionMap.put(calledNode, tempSet);
		}
		else {
			ExceptionFinder.ExceptionMap.put(calledNode, setExceptionCalled);
		}

		if(ExceptionFinder.ExceptionMap.containsKey(nodeCall)) {
			Set<String> tempSet = new HashSet<String>();
			tempSet = ExceptionFinder.ExceptionMap.get(nodeCall);
			tempSet.addAll(setExceptionCall);
			ExceptionFinder.ExceptionMap.put(nodeCall, tempSet);
		}
		else {

			ExceptionFinder.ExceptionMap.put(nodeCall, setExceptionCall);
			
		}
		
		
		
		Set<Node> calledNodeSet = new HashSet<Node>();
		
		calledNodeSet = ExceptionFinder.CallGraph.get(calledNode);
		if(calledNodeSet == null) {
			calledNodeSet = new HashSet<Node>();
			calledNodeSet.add(calledNode);
		}
		else {
			calledNodeSet.add(calledNode);
		}

		
		
		Set<String> exceptionSet = new HashSet<String>();
		for(Node temp:calledNodeSet) {
			if(ExceptionFinder.ExceptionMap.get(temp)!=null) {
				exceptionSet.addAll(ExceptionFinder.ExceptionMap.get(temp));
			}
		}

		TryStatementVisitor.exceptionSetfromTry.addAll(exceptionSet);
		
		
		return super.visit(node);
	}
	
	
	
	
	
	
	public HashSet<MethodInvocation> getSuspectInvocations() {
		return suspectInvocations;
	}
	
	public List<String> getExceptionNames() {
		return exceptionNames;
	}

	public List<String> getFunctionName() {
		return functionName;
	}
	
	public List<Class> getClassNames() {
		return classNames;
	}
	
	
}
