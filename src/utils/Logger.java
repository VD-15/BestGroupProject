package utils;

/**
 * Manages console logging
 * @author Vee
 *
 */
public class Logger
{
	private static LogSeverity minLogSeverity = LogSeverity.VERBOSE;
	
	/**
	 * Print the log to console if the Severity exceeds the minLogSeverity
	 * @param source - The source object that the log is associated with
	 * @param severity - The severity of the log
	 * @param message - Text to be displayed with the log. A description of the log
	 */
	public static void log(Object source, LogSeverity severity, String message)
	{
		log(source.getClass(), severity, message);
	}
	
	/**
	 * Print the log to console if the Severity exceeds the minLogSeverity
	 * @param source - The source class that the log is associated with
	 * @param severity - The severity of the log
	 * @param message - Text to be displayed with the log. A description of the log
	 */
	public static void log(Class<?> source, LogSeverity severity, String message)
	{
		if (severity.ordinal() >= minLogSeverity.ordinal())
		{
			System.out.println(String.format("%-" + 20 + "s", "[" + source.getSimpleName() + "]:") + "(" + getSeverityString(severity) + ")\t" + message);
		}
	}
	
	/**
	 * Sets the minLogSeverity, The minimum severity required for a log to be outputted to console 
	 * @param severity - The LogSeverity to become the new minLogSeverity
	 */
	public static void setLogSeverity(LogSeverity severity)
	{
		minLogSeverity = severity;
	}
	
	//TODO is this method necessary? LogSeverity enum could store these strings.
	public static String getSeverityString(LogSeverity s)
	{
		switch (s.ordinal())
		{
		case 0:
			return "VERB";
		case 1:
			return "INFO";
		case 2:
			return "WARN";
		case 3:
			return "ERROR";
		case 4:
			return "FATAL";
		default:
			return "";
		}
	}
}
