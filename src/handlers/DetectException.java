package handlers;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.JavaModelException;
import patterns.ExceptionFinder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;

public class DetectException extends AbstractHandler {
	public static PrintWriter csvWriter = null;
	public static PrintWriter csvWritertxt = null;

	@Override
	public Object execute(ExecutionEvent event) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		
		IProject[] projects = root.getProjects();
		
		try {
			detectInProjects(projects);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		SampleHandler.printMessage("DONE DETECTING");
		
		return null;
	}
	
	private void detectInProjects(IProject[] projects) throws ClassNotFoundException {

		try  {
			String filePath = System.getProperty("user.home")+"/Desktop/myResult.csv";
			File file = new File(filePath);
			if(file.exists())
				file.delete();
			csvWriter = new PrintWriter(file);
			  
		      ExceptionFinder.sb.append("file_name");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("number_of_Catch_Blocks");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Catch Block LOC");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Catch Block SLOC");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Catch And Return Null AP");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Destructive Wrapping AP");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Catch and Do Nothing AP");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Catch Generic AP");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Dummy Handler AP");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Ignoring Interrupted Exception AP");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Incomplete Implementation AP");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Log and Return Null AP");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Log and Throw AP");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Mutliline log AP");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Nested Try AP");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Relying on Get Cause AP");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Throw Within Finally AP");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Invoked methods");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Try Blocks");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Try Block LOC");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Try Block SLOC");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Throws Generic AP");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Throws Kitchen Sink AP");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Over Catches");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Over Catches and Abort");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("numofDocumentedException");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Try in Declaration");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Try in Condition");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Try in EH");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Try in Loop");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Number of Try in Other");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Action Abort");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Action Continue");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Action Default");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Action Empty");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Action Log");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Action Method");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Action NestedTry");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Action Return");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Action ThrowCurrent");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Action ThrowNew");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Action ThrowWrap");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Action TODO");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Percentage Action Abort");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Percentage Action Continue");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Percentage Action Default");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Percentage Action Empty");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Percentage Action Log");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Percentage Action Method");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Percentage Action NestedTry");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Percentage Action Return");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Percentage Action ThrowCurrent");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Percentage Action ThrowNew");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Percentage Action ThrowWrap");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("Percentage Action TODO");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("subSumptionPercentage");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("specificPercentage");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("numofRevoverableException");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("numofNonRevoverableException");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("overcatchPercentage");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("overcatchAbortPercentage");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("doNothingPercentage");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("returnNullPercentage");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("catchGenericPercentage");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("destructiveWrappingPercentage");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("dummyPercentage");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("ignoreInterruptedExceptionPercentage");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("incompletePercentage");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("logReturnNullPercentage");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("logThrowPercentage");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("multipleLineLogPercentage");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("actions_Nestedtry_PercentageFromCatch");
		      ExceptionFinder.sb.append(",");
		      ExceptionFinder.sb.append("throwInFinallyPercentage");
		      ExceptionFinder.sb.append("\n");
	    

		    } catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		    }

		
		for(IProject project : projects) {
			SampleHandler.printMessage("Starting");
			SampleHandler.printMessage("DETECTING IN: " + project.getName());
			ExceptionFinder exceptionFinder = new ExceptionFinder();
			
			try {
				exceptionFinder.findExceptions(project);
				
			} catch (JavaModelException e) {
				e.printStackTrace();
			}	
	}
		 csvWriter.write(ExceptionFinder.sb.toString());
		 csvWriter.close();
		 //csvWritertxt.write(ExceptionFinder.sbtxt.toString());
		// csvWritertxt.flush();
		 //csvWritertxt.close();
	}
}
