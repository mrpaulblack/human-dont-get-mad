package game;

public abstract class LogController {
	private static Log globalLogLvl = Log.info;

	public static void setGlobalLogLvl(Log logLvl) {
		globalLogLvl = logLvl;
	}

	public static void log(Log logLvl, String logLine) {
		//error lvl
		if (globalLogLvl == Log.error && logLvl == Log.error) {
			System.out.println("[" + logLvl + "] " + logLine);
		}
		//info lvl
		else if (globalLogLvl == Log.info && (logLvl == Log.error || logLvl == Log.info)) {
			System.out.println("[" + logLvl + "] " + logLine);
		}
		//debug lvl
		else if (globalLogLvl == Log.debug && (logLvl == Log.error || logLvl == Log.info || logLvl == Log.debug)) {
			System.out.println("[" + logLvl + "] " + logLine);
		}
	}
}
