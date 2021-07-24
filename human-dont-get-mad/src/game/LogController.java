package game;

/**
* <h1>LogController</h1>
* <p>This method is the global logging facility and is abstract so it cannot be instaciated, since
* there cannot be multiple log controller. It basically just prints messages it recieves through
* the log method based on the global log level. Every method is static so it can be called by every
* object importing it without instaciating an object and so everything gets written to the same
* log controller.</p>
* <b>Note:</b> This could be changed pretty easy to include more log level or to
* write the log to a file instead of printing it into the terminal
*
* @author  Paul Braeuning
* @version 1.0
* @since   2021-07-23
*/
public abstract class LogController {
	private static Log globalLogLvl = Log.info;

	/**
	 *	<h1><i>setGlobalLogLvl</i></h1>
	 * <p>Sets globalLogLvl atribute with the Log enum. This method changes the verbosity of the log
	 * based on the log level provided.</p>
	 * @param logLvl - Log enum that changes the global log level
	 */
	public static void setGlobalLogLvl(Log logLvl) {
		globalLogLvl = logLvl;
	}

	/**
	 *	<h1><i>log</i></h1>
	 * <p>This static method gets called every time a line is added to the log.
	 * It gets the line and the level and prints it in the terminal based on the global
	 * level (but could also do more in the future; like streaming the log to a file).</p>
	 * @param logLvl - Log enum sets the log level of that line
	 * @param logLine - String is the actual log line (you can use [object].toString() when calling this method)
	 */
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
