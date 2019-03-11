package Utils;

public class Logger
{
	public static void log(Object source, LogSeverity severity, String message)
	{
		log(source.getClass(), severity, message);
	}
	
	public static void log(Class<?> source, LogSeverity severity, String message)
	{
		System.out.println("[" + source.getSimpleName() + "]:\t(" + getSeverityString(severity) + ")\t" + message);
	}
	
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
