package patterns;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.JavaModelException;
import handlers.SampleHandler;
import visitors.TryCatchInfo;
import visitors.TryStatementVisitor;
import visitors.CatchClauseVisitor;
import visitors.ThrowsStatementVisitor;
import visitors.Node;
import org.eclipse.jdt.core.*;
import org.eclipse.jdt.core.dom.*;


public class ExceptionFinder {

	HashMap<MethodDeclaration, String> suspectMethods = new HashMap<>();
	int numOverCatch=0,overcatchabort=0;
	int specific=0;
	int	numofSubSumption=0,numofNonRevoverableException=0,numofRevoverableException=0,numofDocumentedException=0;
	CatchClauseVisitor exceptionVisitor;
	public static StringBuilder sb = new StringBuilder();
	public static StringBuilder sbtxt = new StringBuilder();
	public static HashMap<String, String> runtimeExceptionExtends = new HashMap<String, String>();
	public static HashMap<String, String> exceptionExtends = new HashMap<String, String>();
	public static HashMap<Node, Set<String>> ExceptionMap = new HashMap<>();
	public static HashMap<Node, Set<Node>> CallGraph = new HashMap<Node, Set<Node>>();
	private void initExceptionExtends() {
		exceptionExtends.put("Exception", "Throwable");
		exceptionExtends.put("RuntimeException", "Exception");
		exceptionExtends.put("AnnotationTypeMismatchException", "RuntimeException");
		exceptionExtends.put("ArithmeticException", "RuntimeException");
		exceptionExtends.put("ArrayStoreException", "RuntimeException");
		exceptionExtends.put("BufferOverflowException", "RuntimeException");
		exceptionExtends.put("BufferUnderflowException", "RuntimeException");
		exceptionExtends.put("CannotRedoException", "RuntimeException");
		exceptionExtends.put("CannotUndoException", "RuntimeException");
		exceptionExtends.put("ClassCastException", "RuntimeException");
		exceptionExtends.put("ClassCastException", "RuntimeException");
		exceptionExtends.put("CMMException", "RuntimeException");
		exceptionExtends.put("CompletionException", "RuntimeException");
		exceptionExtends.put("ConcurrentModificationException", "RuntimeException");
		exceptionExtends.put("DirectoryIteratorException", "ConcurrentModificationException");
		exceptionExtends.put("DataBindingException", "RuntimeException");
		exceptionExtends.put("DateTimeException", "RuntimeException");
		exceptionExtends.put("DateTimeParseException", "DateTimeException");
		exceptionExtends.put("UnsupportedTemporalTypeException", "DateTimeException");
		exceptionExtends.put("ZoneRulesException", "DateTimeException");
		exceptionExtends.put("DOMException", "RuntimeException");
		exceptionExtends.put("EmptyStackException", "RuntimeException");
		exceptionExtends.put("EnumConstantNotPresentException", "RuntimeException");
		exceptionExtends.put("EventException", "RuntimeException");
		exceptionExtends.put("FileSystemAlreadyExistsException", "RuntimeException");
		exceptionExtends.put("FileSystemNotFoundException", "RuntimeException");
		exceptionExtends.put("IllegalArgumentException", "RuntimeException");
		exceptionExtends.put("IllegalChannelGroupException", "IllegalArgumentException");
		exceptionExtends.put("IllegalCharsetNameException", "IllegalArgumentException");
		exceptionExtends.put("IllegalFormatException", "IllegalArgumentException");
		exceptionExtends.put("DuplicateFormatFlagsException", "IllegalFormatException");
		exceptionExtends.put("FormatFlagsConversionMismatchException", "IllegalFormatException");
		exceptionExtends.put("IllegalFormatCodePointException", "IllegalFormatException");
		exceptionExtends.put("IllegalFormatConversionException", "IllegalFormatException");
		exceptionExtends.put("IllegalFormatFlagsException", "IllegalFormatException");
		exceptionExtends.put("IllegalFormatPrecisionException", "IllegalFormatException");
		exceptionExtends.put("IllegalFormatWidthException", "IllegalFormatException");
		exceptionExtends.put("MissingFormatArgumentException", "IllegalFormatException");
		exceptionExtends.put("MissingFormatWidthException", "IllegalFormatException");
		exceptionExtends.put("UnknownFormatConversionException", "IllegalFormatException");
		exceptionExtends.put("UnknownFormatFlagsException", "IllegalFormatException");
		exceptionExtends.put("IllegalSelectorException", "IllegalArgumentException");
		exceptionExtends.put("IllegalThreadStateException", "IllegalArgumentException");
		exceptionExtends.put("InvalidKeyException", "IllegalArgumentException");
		exceptionExtends.put("InvalidOpenTypeException", "IllegalArgumentException");
		exceptionExtends.put("InvalidParameterException", "IllegalArgumentException");
		exceptionExtends.put("InvalidPathException", "IllegalArgumentException");
		exceptionExtends.put("KeyAlreadyExistsException", "IllegalArgumentException");
		exceptionExtends.put("NumberFormatException", "IllegalArgumentException");
		exceptionExtends.put("PatternSyntaxException", "IllegalArgumentException");
		exceptionExtends.put("ProviderMismatchException", "IllegalArgumentException");
		exceptionExtends.put("UnresolvedAddressException", "IllegalArgumentException");
		exceptionExtends.put("UnsupportedAddressTypeException", "IllegalArgumentException");
		exceptionExtends.put("UnsupportedCharsetException", "IllegalArgumentException");
		exceptionExtends.put("IllegalMonitorStateException", "RuntimeException");
		exceptionExtends.put("IllegalPathStateException", "RuntimeException");
		exceptionExtends.put("AcceptPendingException", "IllegalStateException");
		exceptionExtends.put("AlreadyBoundException", "IllegalStateException");
		exceptionExtends.put("AlreadyConnectedException", "IllegalStateException");
		exceptionExtends.put("CancellationException", "IllegalStateException");
		exceptionExtends.put("CancelledKeyException", "IllegalStateException");
		exceptionExtends.put("ClosedDirectoryStreamException", "IllegalStateException");
		exceptionExtends.put("ClosedFileSystemException", "IllegalStateException");
		exceptionExtends.put("ClosedSelectorException", "IllegalStateException");
		exceptionExtends.put("ClosedWatchServiceException", "IllegalStateException");
		exceptionExtends.put("ConnectionPendingException", "IllegalStateException");
		exceptionExtends.put("FormatterClosedException", "IllegalStateException");
		exceptionExtends.put("IllegalBlockingModeException", "IllegalStateException");
		exceptionExtends.put("IllegalComponentStateException", "IllegalStateException");
		exceptionExtends.put("InvalidDnDOperationException", "IllegalStateException");
		exceptionExtends.put("InvalidMarkException", "IllegalStateException");
		exceptionExtends.put("NoConnectionPendingException", "IllegalStateException");
		exceptionExtends.put("NonReadableChannelException", "IllegalStateException");
		exceptionExtends.put("NonWritableChannelException", "IllegalStateException");
		exceptionExtends.put("NotYetBoundException", "IllegalStateException");
		exceptionExtends.put("NotYetConnectedException", "IllegalStateException");
		exceptionExtends.put("OverlappingFileLockException", "IllegalStateException");
		exceptionExtends.put("ReadPendingException", "IllegalStateException");
		exceptionExtends.put("ShutdownChannelGroupException", "IllegalStateException");
		exceptionExtends.put("WritePendingException", "IllegalStateException");
		exceptionExtends.put("IllegalStateException", "RuntimeException");
		exceptionExtends.put("IllformedLocaleException", "RuntimeException");
		exceptionExtends.put("ImagingOpException", "RuntimeException");
		exceptionExtends.put("IncompleteAnnotationException", "RuntimeException");
		exceptionExtends.put("IndexOutOfBoundsException", "RuntimeException");
		exceptionExtends.put("ArrayIndexOutOfBoundsException", "IndexOutOfBoundsException");
		exceptionExtends.put("StringIndexOutOfBoundsException", "IndexOutOfBoundsException");
		exceptionExtends.put("JMRuntimeException", "RuntimeException");
		exceptionExtends.put("MonitorSettingException", "JMRuntimeException");
		exceptionExtends.put("RuntimeErrorException", "JMRuntimeException");
		exceptionExtends.put("RuntimeMBeanException", "JMRuntimeException");
		exceptionExtends.put("RuntimeOperationsException", "JMRuntimeException");
		exceptionExtends.put("LSException", "RuntimeException");
		exceptionExtends.put("MalformedParameterizedTypeException", "RuntimeException");
		exceptionExtends.put("MalformedParametersException", "RuntimeException");
		exceptionExtends.put("MirroredTypesException", "RuntimeException");
		exceptionExtends.put("MissingResourceException", "RuntimeException");
		exceptionExtends.put("NegativeArraySizeException", "RuntimeException");
		exceptionExtends.put("NoSuchElementException", "RuntimeException");
		exceptionExtends.put("InputMismatchException", "NoSuchElementException");
		exceptionExtends.put("NullPointerException", "RuntimeException");
		exceptionExtends.put("ProfileDataException", "RuntimeException");
		exceptionExtends.put("ProviderException", "RuntimeException");
		exceptionExtends.put("ProviderNotFoundException", "RuntimeException");
		exceptionExtends.put("RasterFormatException", "RuntimeException");
		exceptionExtends.put("RejectedExecutionException", "RuntimeException");
		exceptionExtends.put("SecurityException", "RuntimeException");
		exceptionExtends.put("AccessControlException", "SecurityException");
		exceptionExtends.put("RMISecurityException", "SecurityException");
		exceptionExtends.put("SystemException", "RuntimeException");
		exceptionExtends.put("TypeConstraintException", "RuntimeException");
		exceptionExtends.put("TypeNotPresentException", "RuntimeException");
		exceptionExtends.put("UncheckedIOException", "RuntimeException");
		exceptionExtends.put("UndeclaredThrowableException", "RuntimeException");
		exceptionExtends.put("UnknownEntityException", "RuntimeException");
		exceptionExtends.put("UnknownAnnotationValueException", "UnknownEntityException");
		exceptionExtends.put("UnknownElementException", "UnknownEntityException");
		exceptionExtends.put("UnknownTypeException", "UnknownEntityException");
		exceptionExtends.put("UnmodifiableSetException", "RuntimeException");
		exceptionExtends.put("UnsupportedOperationException", "RuntimeException");
		exceptionExtends.put("HeadlessException", "UnsupportedOperationException");
		exceptionExtends.put("ReadOnlyBufferException", "UnsupportedOperationException");
		exceptionExtends.put("ReadOnlyFileSystemException", "UnsupportedOperationException");
		exceptionExtends.put("WebServiceException", "RuntimeException");
		exceptionExtends.put("ProtocolException", "WebServiceException");
		exceptionExtends.put("HTTPException", "ProtocolException");
		exceptionExtends.put("SOAPFaultException", "ProtocolException");
		exceptionExtends.put("WrongMethodTypeException", "RuntimeException");

	}

	private void initRunTimeExceptionExtends() {
		runtimeExceptionExtends.put("Exception", "Throwable");
		runtimeExceptionExtends.put("RuntimeException", "Exception");
		runtimeExceptionExtends.put("AnnotationTypeMismatchException", "RuntimeException");
		runtimeExceptionExtends.put("ArithmeticException", "RuntimeException");
		runtimeExceptionExtends.put("ArrayStoreException", "RuntimeException");
		runtimeExceptionExtends.put("BufferOverflowException", "RuntimeException");
		runtimeExceptionExtends.put("BufferUnderflowException", "RuntimeException");
		runtimeExceptionExtends.put("CannotRedoException", "RuntimeException");
		runtimeExceptionExtends.put("CannotUndoException", "RuntimeException");
		runtimeExceptionExtends.put("ClassCastException", "RuntimeException");
		runtimeExceptionExtends.put("ClassCastException", "RuntimeException");
		runtimeExceptionExtends.put("CMMException", "RuntimeException");
		runtimeExceptionExtends.put("CompletionException", "RuntimeException");
		runtimeExceptionExtends.put("ConcurrentModificationException", "RuntimeException");
		runtimeExceptionExtends.put("DirectoryIteratorException", "ConcurrentModificationException");
		runtimeExceptionExtends.put("DataBindingException", "RuntimeException");
		runtimeExceptionExtends.put("DateTimeException", "RuntimeException");
		runtimeExceptionExtends.put("DateTimeParseException", "DateTimeException");
		runtimeExceptionExtends.put("UnsupportedTemporalTypeException", "DateTimeException");
		runtimeExceptionExtends.put("ZoneRulesException", "DateTimeException");
		runtimeExceptionExtends.put("DOMException", "RuntimeException");
		runtimeExceptionExtends.put("EmptyStackException", "RuntimeException");
		runtimeExceptionExtends.put("EnumConstantNotPresentException", "RuntimeException");
		runtimeExceptionExtends.put("EventException", "RuntimeException");
		runtimeExceptionExtends.put("FileSystemAlreadyExistsException", "RuntimeException");
		runtimeExceptionExtends.put("FileSystemNotFoundException", "RuntimeException");
		runtimeExceptionExtends.put("IllegalArgumentException", "RuntimeException");
		runtimeExceptionExtends.put("IllegalChannelGroupException", "IllegalArgumentException");
		runtimeExceptionExtends.put("IllegalCharsetNameException", "IllegalArgumentException");
		runtimeExceptionExtends.put("IllegalFormatException", "IllegalArgumentException");
		runtimeExceptionExtends.put("DuplicateFormatFlagsException", "IllegalFormatException");
		runtimeExceptionExtends.put("FormatFlagsConversionMismatchException", "IllegalFormatException");
		runtimeExceptionExtends.put("IllegalFormatCodePointException", "IllegalFormatException");
		runtimeExceptionExtends.put("IllegalFormatConversionException", "IllegalFormatException");
		runtimeExceptionExtends.put("IllegalFormatFlagsException", "IllegalFormatException");
		runtimeExceptionExtends.put("IllegalFormatPrecisionException", "IllegalFormatException");
		runtimeExceptionExtends.put("IllegalFormatWidthException", "IllegalFormatException");
		runtimeExceptionExtends.put("MissingFormatArgumentException", "IllegalFormatException");
		runtimeExceptionExtends.put("MissingFormatWidthException", "IllegalFormatException");
		runtimeExceptionExtends.put("UnknownFormatConversionException", "IllegalFormatException");
		runtimeExceptionExtends.put("UnknownFormatFlagsException", "IllegalFormatException");
		runtimeExceptionExtends.put("IllegalSelectorException", "IllegalArgumentException");
		runtimeExceptionExtends.put("IllegalThreadStateException", "IllegalArgumentException");
		runtimeExceptionExtends.put("InvalidKeyException", "IllegalArgumentException");
		runtimeExceptionExtends.put("InvalidOpenTypeException", "IllegalArgumentException");
		runtimeExceptionExtends.put("InvalidParameterException", "IllegalArgumentException");
		runtimeExceptionExtends.put("InvalidPathException", "IllegalArgumentException");
		runtimeExceptionExtends.put("KeyAlreadyExistsException", "IllegalArgumentException");
		runtimeExceptionExtends.put("NumberFormatException", "IllegalArgumentException");
		runtimeExceptionExtends.put("PatternSyntaxException", "IllegalArgumentException");
		runtimeExceptionExtends.put("ProviderMismatchException", "IllegalArgumentException");
		runtimeExceptionExtends.put("UnresolvedAddressException", "IllegalArgumentException");
		runtimeExceptionExtends.put("UnsupportedAddressTypeException", "IllegalArgumentException");
		runtimeExceptionExtends.put("UnsupportedCharsetException", "IllegalArgumentException");
		runtimeExceptionExtends.put("IllegalMonitorStateException", "RuntimeException");
		runtimeExceptionExtends.put("IllegalPathStateException", "RuntimeException");
		runtimeExceptionExtends.put("AcceptPendingException", "IllegalStateException");
		runtimeExceptionExtends.put("AlreadyBoundException", "IllegalStateException");
		runtimeExceptionExtends.put("AlreadyConnectedException", "IllegalStateException");
		runtimeExceptionExtends.put("CancellationException", "IllegalStateException");
		runtimeExceptionExtends.put("CancelledKeyException", "IllegalStateException");
		runtimeExceptionExtends.put("ClosedDirectoryStreamException", "IllegalStateException");
		runtimeExceptionExtends.put("ClosedFileSystemException", "IllegalStateException");
		runtimeExceptionExtends.put("ClosedSelectorException", "IllegalStateException");
		runtimeExceptionExtends.put("ClosedWatchServiceException", "IllegalStateException");
		runtimeExceptionExtends.put("ConnectionPendingException", "IllegalStateException");
		runtimeExceptionExtends.put("FormatterClosedException", "IllegalStateException");
		runtimeExceptionExtends.put("IllegalBlockingModeException", "IllegalStateException");
		runtimeExceptionExtends.put("IllegalComponentStateException", "IllegalStateException");
		runtimeExceptionExtends.put("InvalidDnDOperationException", "IllegalStateException");
		runtimeExceptionExtends.put("InvalidMarkException", "IllegalStateException");
		runtimeExceptionExtends.put("NoConnectionPendingException", "IllegalStateException");
		runtimeExceptionExtends.put("NonReadableChannelException", "IllegalStateException");
		runtimeExceptionExtends.put("NonWritableChannelException", "IllegalStateException");
		runtimeExceptionExtends.put("NotYetBoundException", "IllegalStateException");
		runtimeExceptionExtends.put("NotYetConnectedException", "IllegalStateException");
		runtimeExceptionExtends.put("OverlappingFileLockException", "IllegalStateException");
		runtimeExceptionExtends.put("ReadPendingException", "IllegalStateException");
		runtimeExceptionExtends.put("ShutdownChannelGroupException", "IllegalStateException");
		runtimeExceptionExtends.put("WritePendingException", "IllegalStateException");
		runtimeExceptionExtends.put("IllegalStateException", "RuntimeException");
		runtimeExceptionExtends.put("IllformedLocaleException", "RuntimeException");
		runtimeExceptionExtends.put("ImagingOpException", "RuntimeException");
		runtimeExceptionExtends.put("IncompleteAnnotationException", "RuntimeException");
		runtimeExceptionExtends.put("IndexOutOfBoundsException", "RuntimeException");
		runtimeExceptionExtends.put("ArrayIndexOutOfBoundsException", "IndexOutOfBoundsException");
		runtimeExceptionExtends.put("StringIndexOutOfBoundsException", "IndexOutOfBoundsException");
		runtimeExceptionExtends.put("JMRuntimeException", "RuntimeException");
		runtimeExceptionExtends.put("MonitorSettingException", "JMRuntimeException");
		runtimeExceptionExtends.put("RuntimeErrorException", "JMRuntimeException");
		runtimeExceptionExtends.put("RuntimeMBeanException", "JMRuntimeException");
		runtimeExceptionExtends.put("RuntimeOperationsException", "JMRuntimeException");
		runtimeExceptionExtends.put("LSException", "RuntimeException");
		runtimeExceptionExtends.put("MalformedParameterizedTypeException", "RuntimeException");
		runtimeExceptionExtends.put("MalformedParametersException", "RuntimeException");
		runtimeExceptionExtends.put("MirroredTypesException", "RuntimeException");
		runtimeExceptionExtends.put("MissingResourceException", "RuntimeException");
		runtimeExceptionExtends.put("NegativeArraySizeException", "RuntimeException");
		runtimeExceptionExtends.put("NoSuchElementException", "RuntimeException");
		runtimeExceptionExtends.put("InputMismatchException", "NoSuchElementException");
		runtimeExceptionExtends.put("NullPointerException", "RuntimeException");
		runtimeExceptionExtends.put("ProfileDataException", "RuntimeException");
		runtimeExceptionExtends.put("ProviderException", "RuntimeException");
		runtimeExceptionExtends.put("ProviderNotFoundException", "RuntimeException");
		runtimeExceptionExtends.put("RasterFormatException", "RuntimeException");
		runtimeExceptionExtends.put("RejectedExecutionException", "RuntimeException");
		runtimeExceptionExtends.put("SecurityException", "RuntimeException");
		runtimeExceptionExtends.put("AccessControlException", "SecurityException");
		runtimeExceptionExtends.put("RMISecurityException", "SecurityException");
		runtimeExceptionExtends.put("SystemException", "RuntimeException");
		runtimeExceptionExtends.put("TypeConstraintException", "RuntimeException");
		runtimeExceptionExtends.put("TypeNotPresentException", "RuntimeException");
		runtimeExceptionExtends.put("UncheckedIOException", "RuntimeException");
		runtimeExceptionExtends.put("UndeclaredThrowableException", "RuntimeException");
		runtimeExceptionExtends.put("UnknownEntityException", "RuntimeException");
		runtimeExceptionExtends.put("UnknownAnnotationValueException", "UnknownEntityException");
		runtimeExceptionExtends.put("UnknownElementException", "UnknownEntityException");
		runtimeExceptionExtends.put("UnknownTypeException", "UnknownEntityException");
		runtimeExceptionExtends.put("UnmodifiableSetException", "RuntimeException");
		runtimeExceptionExtends.put("UnsupportedOperationException", "RuntimeException");
		runtimeExceptionExtends.put("HeadlessException", "UnsupportedOperationException");
		runtimeExceptionExtends.put("ReadOnlyBufferException", "UnsupportedOperationException");
		runtimeExceptionExtends.put("ReadOnlyFileSystemException", "UnsupportedOperationException");
		runtimeExceptionExtends.put("WebServiceException", "RuntimeException");
		runtimeExceptionExtends.put("ProtocolException", "WebServiceException");
		runtimeExceptionExtends.put("HTTPException", "ProtocolException");
		runtimeExceptionExtends.put("SOAPFaultException", "ProtocolException");
		runtimeExceptionExtends.put("WrongMethodTypeException", "RuntimeException");
	}


	public HashMap<MethodDeclaration, String> getSuspectMethods() {
		return suspectMethods;
	}

	public void findExceptions(IProject project) throws JavaModelException, ClassNotFoundException {
		
		initExceptionExtends();
		initRunTimeExceptionExtends();
		sbtxt.append(" SOEN 691 Assignment: Discovering Anti Patterns \n");

		IPackageFragment[] packages = JavaCore.create(project).getPackageFragments();

		for(IPackageFragment mypackage : packages){

			findTargetCatchClauses(mypackage);

		}




	}

	private void findTargetCatchClauses(IPackageFragment packageFragment) throws JavaModelException, ClassNotFoundException {
		for (ICompilationUnit unit : packageFragment.getCompilationUnits()) {
			CompilationUnit parsedCompilationUnit = parse(unit);
			
			String path= unit.getPath().toString();
			if(path.contains("/test/"))
			{
				continue;
			}
			
			exceptionVisitor = new CatchClauseVisitor();
			SampleHandler.printMessage(unit.getCorrespondingResource().getLocation().toFile().toString());
			exceptionVisitor.setF(unit.getCorrespondingResource().getLocation().toFile());
			initializeMetricCounters();
			parsedCompilationUnit.accept(exceptionVisitor);
			TryStatementVisitor tryStatmentVisitor = new TryStatementVisitor();
			tryStatmentVisitor.setFile(unit.getCorrespondingResource().getLocation().toFile());
			parsedCompilationUnit.accept(tryStatmentVisitor);
			ThrowsStatementVisitor throwsStatementVisitor = new ThrowsStatementVisitor();
			throwsStatementVisitor.setF(unit.getCorrespondingResource().getLocation().toFile());
			parsedCompilationUnit.accept(throwsStatementVisitor);
			findMissingExceptionOverCatch(tryStatmentVisitor);
			findMissingExceptionOverCatchAbort(tryStatmentVisitor);
			
			findCatchRecoverability(tryStatmentVisitor);
			if (TryStatementVisitor.getNumOfTryBlocks() != 0 && CatchClauseVisitor.getNumOfCatchBlocks()!=0) {
				float abort_Percentage = (TryStatementVisitor.getActionAbort()/TryStatementVisitor.getNumOfTryBlocks())*100;
				float continue_Percentage = (TryStatementVisitor.getActionContinue()/TryStatementVisitor.getNumOfTryBlocks())*100;
				float default_Percentage = (TryStatementVisitor.getActionDefault()/TryStatementVisitor.getNumOfTryBlocks())*100;
				float empty_Percentage = (TryStatementVisitor.getActionEmpty()/TryStatementVisitor.getNumofTryLOC())*100;
				float actions_Log_Percentage = (TryStatementVisitor.getActionLog()/TryStatementVisitor.getNumOfTryBlocks())*100;
				float actions_Method_Percentage = (TryStatementVisitor.getActionMethod()/TryStatementVisitor.getNumOfTryBlocks())*100;
				float actions_Nestedtry_PercentageFromTry = (TryStatementVisitor.getActionNestedTry()/TryStatementVisitor.getNumOfTryBlocks())*100;
				float actions_Return_Percentage = (TryStatementVisitor.getActionReturn()/TryStatementVisitor.getNumOfTryBlocks())*100;
				float actions_Throwcurrent_Percentage = (TryStatementVisitor.getActionThrowCurrent()/TryStatementVisitor.getNumOfTryBlocks())*100;
				float actions_Thrownew_Percentage = (TryStatementVisitor.getActionThrowNew()/TryStatementVisitor.getNumOfTryBlocks())*100;
				float actions_Throwwrap_Percentage =(TryStatementVisitor.getActionThrowWrap()/TryStatementVisitor.getNumOfTryBlocks())*100;
				float actions_Todo_Percentage = (TryStatementVisitor.getActionTODO()/TryStatementVisitor.getNumOfTryBlocks())*100;
				float subSumptionPercentage = (numofSubSumption/TryStatementVisitor.getNumOfTryBlocks())*100;
				float specificPercentage = (specific/TryStatementVisitor.getNumOfTryBlocks())*100;
				float overcatchPercentage = (numOverCatch/CatchClauseVisitor.getNumOfCatchBlocks())*100;
				float overcatchAbortPercentage = (overcatchabort/CatchClauseVisitor.getNumOfCatchBlocks())*100;
				float doNothingPercentage = (CatchClauseVisitor.getNumOfCatchAndDoNothingAP()/CatchClauseVisitor.getNumOfCatchBlocks())*100;
				float returnNullPercentage = (CatchClauseVisitor.getNumOfCatchAndReturnNullAP()/CatchClauseVisitor.getNumOfCatchBlocks())*100;
				float catchGenericPercentage = (CatchClauseVisitor.getNumOfCatchGenericAP()/CatchClauseVisitor.getNumOfCatchBlocks())*100;
				float destructiveWrappingPercentage =(CatchClauseVisitor.getNumOfDestructiveWrapAP()/CatchClauseVisitor.getNumOfCatchBlocks())*100;
				float dummyPercentage = (CatchClauseVisitor.getNumOfDummyHandlerAP()/CatchClauseVisitor.getNumOfCatchBlocks())*100;
				float ignoreInterruptedExceptionPercentage = (CatchClauseVisitor.getNumOfIgnoreInterruptedExceptionAP()/CatchClauseVisitor.getNumOfCatchBlocks())*100;
				float incompletePercentage = (CatchClauseVisitor.getNumOfIncompleteImplementationAP()/CatchClauseVisitor.getNumOfCatchBlocks())*100;
				float logReturnNullPercentage = (CatchClauseVisitor.getNumOfLogAndReturnNullAP()/CatchClauseVisitor.getNumOfCatchBlocks())*100;
				float logThrowPercentage = (CatchClauseVisitor.getNumOfLogAndThrowAP()/CatchClauseVisitor.getNumOfCatchBlocks())*100;
				float multipleLineLogPercentage = (CatchClauseVisitor.getNumOfMultilineLogAP()/CatchClauseVisitor.getNumOfCatchBlocks())*100;
				float actions_Nestedtry_PercentageFromCatch = (CatchClauseVisitor.getNumOfNestedTryAP()/CatchClauseVisitor.getNumOfCatchBlocks())*100;
				float throwInFinallyPercentage = (CatchClauseVisitor.getNumOfThrowWithinFinallyAP()/CatchClauseVisitor.getNumOfCatchBlocks())*100;
			
				findSpecific(tryStatmentVisitor);
			findDocumentedException(tryStatmentVisitor);
			sb.append(""+unit.getCorrespondingResource().getLocation().toFile());
			sb.append(',');
			SampleHandler.printMessage("NumOfCatchBlocks"+ CatchClauseVisitor.getNumOfCatchBlocks());

			sb.append(""+CatchClauseVisitor.getNumOfCatchBlocks());
			sb.append(",");
			if(CatchClauseVisitor.getNumOfCatchBlocks()==0)
			{
				sb.append("0");
				sb.append(",");
				sb.append("0");
				sb.append(",");
			}
			else
			{
				sb.append("" + CatchClauseVisitor.getNumofCatchBlockLOC()/CatchClauseVisitor.getNumOfCatchBlocks());
				sb.append(",");
				sb.append("" + CatchClauseVisitor.getNumofCatchBlockSLOC()/CatchClauseVisitor.getNumOfCatchBlocks());
				sb.append(",");
			}
			sb.append("" + CatchClauseVisitor.getNumOfCatchAndReturnNullAP());
			sb.append(",");
			sb.append("" + CatchClauseVisitor.getNumOfDestructiveWrapAP());
			sb.append(",");

			sb.append("" + CatchClauseVisitor.getNumOfCatchAndDoNothingAP());
			sb.append(",");
			sb.append("" + CatchClauseVisitor.getNumOfCatchGenericAP());
			sb.append(",");
			sb.append("" + CatchClauseVisitor.getNumOfDummyHandlerAP());
			sb.append(",");
			sb.append("" + CatchClauseVisitor.getNumOfIgnoreInterruptedExceptionAP());
			sb.append(",");
			sb.append("" + CatchClauseVisitor.getNumOfIncompleteImplementationAP());
			sb.append(",");
			sb.append("" + CatchClauseVisitor.getNumOfLogAndReturnNullAP());
			sb.append(",");
			sb.append("" + CatchClauseVisitor.getNumOfLogAndThrowAP());
			sb.append(",");
			sb.append("" + CatchClauseVisitor.getNumOfMultilineLogAP());
			sb.append(",");
			sb.append("" + CatchClauseVisitor.getNumOfNestedTryAP());
			sb.append(",");
			sb.append("" + CatchClauseVisitor.getNumOfRelyingOnGetCauseAP());
			sb.append(",");
			sb.append("" + CatchClauseVisitor.getNumOfThrowWithinFinallyAP());
			sb.append(",");
			int numofInvokedMethods = findNumberOfInvokedMethods(tryStatmentVisitor);
			sb.append(numofInvokedMethods);
			sb.append(",");
			
			sb.append("" +TryStatementVisitor.getNumOfTryBlocks());
			sb.append(",");
			if(TryStatementVisitor.getNumOfTryBlocks()==0)
			{
				sb.append("0");
				sb.append(",");
				sb.append("0");
				sb.append(",");
			}
			else
			{
				sb.append("" + TryStatementVisitor.getNumofTryLOC()/TryStatementVisitor.getNumOfTryBlocks());
				sb.append(",");
				sb.append("" + TryStatementVisitor.getNumofTryBlockSLOC()/TryStatementVisitor.getNumOfTryBlocks());
				sb.append(",");
			}
			sb.append("" +ThrowsStatementVisitor.getNumOfThrowsGenericAP());
			sb.append(",");
			sb.append("" +ThrowsStatementVisitor.getNumOfThrowsKitchenSinkAP());
			sb.append(",");
			sb.append("" +numOverCatch);
			sb.append(",");
			sb.append("" +overcatchabort);
			sb.append(",");
			sb.append(numofDocumentedException);
			sb.append(',');
			sb.append(TryStatementVisitor.getDeclarationTry());
			sb.append(",");
			sb.append(TryStatementVisitor.getConditionTry());
			sb.append(",");
			sb.append(TryStatementVisitor.getEHTry());
			sb.append(",");
			sb.append(TryStatementVisitor.getLoopTry());
			sb.append(",");
			sb.append(TryStatementVisitor.getOtherTry());
			sb.append(",");
			sb.append(TryStatementVisitor.getActionAbort());
			sb.append(",");
			sb.append(TryStatementVisitor.getActionContinue());
			sb.append(",");
			sb.append(TryStatementVisitor.getActionDefault());
			sb.append(",");
			sb.append(TryStatementVisitor.getActionEmpty());
			sb.append(",");
			sb.append(TryStatementVisitor.getActionLog());
			sb.append(",");
			sb.append(TryStatementVisitor.getActionMethod());
			sb.append(",");
			sb.append(TryStatementVisitor.getActionNestedTry());
			sb.append(",");
			sb.append(TryStatementVisitor.getActionReturn());
			sb.append(",");
			sb.append(TryStatementVisitor.getActionThrowCurrent());
			sb.append(",");
			sb.append(TryStatementVisitor.getActionThrowNew());
			sb.append(",");
			sb.append(TryStatementVisitor.getActionThrowWrap());
			sb.append(",");
			sb.append(TryStatementVisitor.getActionTODO());
			sb.append(",");
		
			
			
				sb.append(abort_Percentage);
				sb.append(",");
				sb.append(continue_Percentage);
				sb.append(",");
				sb.append(default_Percentage);
				sb.append(",");
				sb.append(empty_Percentage);
				sb.append(",");
				sb.append(actions_Log_Percentage);
				sb.append(",");
				sb.append(actions_Method_Percentage);
				sb.append(",");
				sb.append(actions_Nestedtry_PercentageFromTry);
				sb.append(",");
				sb.append(actions_Return_Percentage);
				sb.append(",");
				sb.append(actions_Throwcurrent_Percentage);
				sb.append(",");
				sb.append(actions_Thrownew_Percentage);
				sb.append(",");
				sb.append(actions_Throwwrap_Percentage);
				sb.append(",");
				sb.append(actions_Todo_Percentage);
				sb.append(",");
				sb.append(subSumptionPercentage);
				sb.append(",");
				sb.append(specificPercentage);
				sb.append(",");
				sb.append(numofRevoverableException);
				sb.append(",");
				sb.append(numofNonRevoverableException);
				sb.append(",");	
					sb.append(overcatchPercentage);
				sb.append(",");
				sb.append(overcatchAbortPercentage);
				sb.append(",");
				sb.append(doNothingPercentage);
				sb.append(",");
				sb.append(returnNullPercentage);
				sb.append(",");
				sb.append(catchGenericPercentage);
				sb.append(",");
				sb.append(destructiveWrappingPercentage);
				sb.append(",");
				sb.append(dummyPercentage);
				sb.append(",");
				sb.append(ignoreInterruptedExceptionPercentage);
				sb.append(",");
				sb.append(incompletePercentage);
				sb.append(",");
				sb.append(logReturnNullPercentage);
				sb.append(",");
				sb.append(logThrowPercentage);
				sb.append(",");
				sb.append(multipleLineLogPercentage);
				sb.append(",");
				sb.append(actions_Nestedtry_PercentageFromCatch);
				sb.append(",");
				sb.append(throwInFinallyPercentage);
				sb.append("\n");
		}
			
		}
	}


	public void initializeMetricCounters() {

		CatchClauseVisitor.setNumofCatchBlockLOC(0);
		CatchClauseVisitor.setNumOfCatchBlocks(0);
		CatchClauseVisitor.setNumofCatchBlockSLOC(0);
		CatchClauseVisitor.setNumOfCatchAndReturnNullAP(0);
		CatchClauseVisitor.setNumOfDestructiveWrapAP(0);
		CatchClauseVisitor.setNumOfCatchAndDoNothingAP(0);
		CatchClauseVisitor.setNumOfCatchGenericAP(0);
		CatchClauseVisitor.setNumOfDummyHandlerAP(0);
		CatchClauseVisitor.setNumOfIgnoreInterruptedExceptionAP(0);
		CatchClauseVisitor.setNumOfIncompleteImplementationAP(0);
		CatchClauseVisitor.setNumOfLogAndReturnNullAP(0);
		CatchClauseVisitor.setNumOfLogAndThrowAP(0);
		CatchClauseVisitor.setNumOfMultilineLogAP(0);
		CatchClauseVisitor.setNumOfNestedTryAP(0);
		CatchClauseVisitor.setNumOfRelyingOnGetCauseAP(0);

		TryStatementVisitor.setNumOfTryBlocks(0);
		TryStatementVisitor.setNumofTryLOC(0);
		TryStatementVisitor.setNumofTryBlockSLOC(0);
		
		TryStatementVisitor.setDeclarationTry(0);
		TryStatementVisitor.setConditionTry(0);
		TryStatementVisitor.setEHTry(0);
		TryStatementVisitor.setLoopTry(0);
		TryStatementVisitor.setOtherTry(0);
		TryStatementVisitor.setWholeExceptionsInCatch(null);
		TryStatementVisitor.setActionAbort(0); 
		TryStatementVisitor.setActionContinue(0); 
		TryStatementVisitor.setActionDefault(0); 
		TryStatementVisitor.setActionEmpty(0); 
		TryStatementVisitor.setActionLog(0); 
		TryStatementVisitor.setActionMethod(0); 
		TryStatementVisitor.setActionNestedTry(0); 
		TryStatementVisitor.setActionReturn(0); 
		TryStatementVisitor.setActionThrowCurrent(0); 
		TryStatementVisitor.setActionThrowNew(0); 
		TryStatementVisitor.setActionThrowWrap(0); 
		TryStatementVisitor.setActionTODO(0); 

		
		ThrowsStatementVisitor.setNumOfThrowsGenericAP(0);
		ThrowsStatementVisitor.setNumOfThrowsKitchenSinkAP(0);
	}

	public int findNumberOfInvokedMethods(TryStatementVisitor tryStatementVisitor) {
		int x =0;
		for (TryCatchInfo info : tryStatementVisitor.getTryCatchInfo()) {
			x = x + info.getNumberOfInvokedMehtods();
		}
		return x;
	}

	public static CompilationUnit parse(ICompilationUnit unit) {
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setStatementsRecovery(true);
		return (CompilationUnit) parser.createAST(null);
	}
	
	
public void findMissingExceptionOverCatch(TryStatementVisitor tryStatmentVisitor) throws ClassNotFoundException {
		
		
		for (TryCatchInfo info : tryStatmentVisitor.getTryCatchInfo()) {
			
			if(info.getExceptionThrownByMethod().size()<1 && (info.getCatchBlockException().contains("Exception") || info.getCatchBlockException().contains("Throwable")))
			{
				Set<String> settry = tryStatmentVisitor.exceptionSetfromTry;

				List<String> catches = info.getCatchBlockException();
				
				if(catches==null||catches.size()==0)
				{
					break;
				}
				
				for (String sc: catches)
				{
					for(String st: settry)
					{
						String exception = ExceptionFinder.exceptionExtends.get(sc);
						while(exception!=null)
						{
							
							if(exception.equals(st))
							{
								//overcatch
								numOverCatch++;
								
							}
							else
							{
								exception = ExceptionFinder.exceptionExtends.get(exception);
								if(exception==null)
								{
									break;
								}
							}
							
						}
					}
				}
			}
					
		}

	}

public void findMissingExceptionOverCatchAbort(TryStatementVisitor tryStatmentVisitor) throws ClassNotFoundException {
	
	
	for (TryCatchInfo info : tryStatmentVisitor.getTryCatchInfo()) {
		
		if(info.getExceptionThrownByMethod().size()<1 && (info.getCatchBlockException().contains("Exception") || info.getCatchBlockException().contains("Throwable")))
		{
			Set<String> settry = tryStatmentVisitor.exceptionSetfromTry;

			List<String> catches = info.getCatchBlockException();
			
			
			if(catches==null||catches.size()==0)
			{
				break;
			}
			
			for (String sc: catches)
			{
				for(String st: settry)
				{
					String exception = ExceptionFinder.exceptionExtends.get(sc);
					while(exception!=null)
					{
						
						if(exception.equals(st)&& info.getBody().contains("System.exit"))
						{
							//overcatch abort
							overcatchabort++;
							
						}
						else
						{
							exception = ExceptionFinder.exceptionExtends.get(exception);
							if(exception==null)
							{
								break;
							}
						}
						
					}
				}
			}
		}
				
	}

}


	public void findSpecific(TryStatementVisitor tryStatmentVisitor) throws ClassNotFoundException{
	
		
		for (TryCatchInfo info : tryStatmentVisitor.getTryCatchInfo()) {
			
			if(info.getExceptionThrownByMethod().size()<1 && (info.getCatchBlockException().contains("Exception") || info.getCatchBlockException().contains("Throwable")))
			{
				Set<String> settry = tryStatmentVisitor.getExceptionSetfromTry();

				List<String> catches = info.getCatchBlockException();
				
				if(catches==null||catches.size()==0)
				{
					break;
				}
				
				for (String sc: catches)
				{
					for(String st: settry)
					{
							if(st.equals(sc))
							{
								//specific
								specific++;
								
							}
					}
				}
			}
					
		}
	}
	
	
	
	
public void findCatchRecoverability(TryStatementVisitor tryStatmentVisitor) throws ClassNotFoundException{
	
		
		for (TryCatchInfo info : tryStatmentVisitor.getTryCatchInfo()) {
			
			if(info.getExceptionThrownByMethod().size()<1 && (info.getCatchBlockException().contains("Exception") || info.getCatchBlockException().contains("Throwable")))
			{
				List<String> catches = info.getCatchBlockException();
				
				if(catches==null||catches.size()==0)
				{
					break;
				}
				
				for (String sc: catches)
				{
					if(runtimeExceptionExtends.containsKey(sc) || exceptionExtends.containsKey(sc)) 
					{
						numofRevoverableException++;
					} 
					  else
					  {
						  numofNonRevoverableException++; 
					  }
				}		  
				 
			}
					
		}
	}
	
	
	
	
	
	
	
public void findSubSumption(TryStatementVisitor tryStatmentVisitor) throws ClassNotFoundException{
	
		
		for (TryCatchInfo info : tryStatmentVisitor.getTryCatchInfo()) {
			
			if(info.getExceptionThrownByMethod().size()<1 && (info.getCatchBlockException().contains("Exception") || info.getCatchBlockException().contains("Throwable")))
			{
				Set<String> settry = tryStatmentVisitor.getExceptionSetfromTry();

				List<String> catches = info.getCatchBlockException();
				
				if(catches==null||catches.size()==0)
				{
					break;
				}
				
				for (String sc: catches)
				{
					for(String st: settry)
					{
						String exception = ExceptionFinder.exceptionExtends.get(st);
						while(exception!=null)
						{
							
							if(exception.equals(sc))
							{
								//subsumption
								numofSubSumption++;
								
							}
							else
							{
								exception = ExceptionFinder.exceptionExtends.get(exception);
								if(exception==null)
								{
									break;
								}
							}
							
						}
					}
				}
			}
					
		}
	}


public void findDocumentedException(TryStatementVisitor tryStatmentVisitor) throws ClassNotFoundException{
	
	Set<String> settry =tryStatmentVisitor.getExceptionSetfromTry();
	for(String st:settry)
	{
		if(ExceptionMap.containsKey(st))
		{
			numofDocumentedException++;
		}
	}
}





}
