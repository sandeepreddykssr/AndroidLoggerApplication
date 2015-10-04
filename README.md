# AndroidLoggerApplication

1. The LoggerUtil in com.sandeep.loggerapp.util Package is the Logging  Class which handles all the logging done in the application
2. The EventReceiver class in com.sandeep.loggerapp.receivers package handles all the Location and Network Changes
3. You can type in the message to be logged and press the button to save the logs to log file (Note: Logs above Info Level, i.e., Verbose and Debug logs will not be displayed or written to logs file as they are disabled by MIN_LOG_LEVEL variable in LoggerUtil class change it according to display logs of different levels).
4. The Network Utilities are defined in com.sandeep.loggerapp.util.network pckage nad network services are defined in com.sandeep.loggerapp.util.network.networkservice package.