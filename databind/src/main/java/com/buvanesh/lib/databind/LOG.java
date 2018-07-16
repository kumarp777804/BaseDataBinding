package com.buvanesh.lib.databind;

import android.util.Log;

/*
 * @author Buvaneshkumar
 * © copyrights reserved 2018.
 * */

public class LOG {
        private LOG() {
        }

        public static final int VERBOSE = Log.VERBOSE;
        public static final int DEBUG = Log.DEBUG;
        public static final int INFO = Log.INFO;
        public static final int WARN = Log.WARN;
        public static final int ERROR = Log.ERROR;

        // Current log level
        private static int sLogLevel = Log.ERROR;

        static {
            if (BuildConfig.DEBUG) {
                sLogLevel = Log.DEBUG;
            }
        }

        /**
         * Set the current log level.
         *
         * @param logLevel set log level to print the statement
         */
        public static void setLogLevel(int logLevel) {
            sLogLevel = logLevel;
        }

        /**
         * Set the current log level.
         *
         * @param logLevel set log level to print the statement
         */
        public static void setLogLevel(String logLevel) {
            if ("VERBOSE".equals(logLevel)) {
                sLogLevel = VERBOSE;
            } else if ("DEBUG".equals(logLevel)) {
                sLogLevel = DEBUG;
            } else if ("INFO".equals(logLevel)) {
                sLogLevel = INFO;
            } else if ("WARN".equals(logLevel)) {
                sLogLevel = WARN;
            } else if ("ERROR".equals(logLevel)) {
                sLogLevel = ERROR;
            }
        }

        /**
         * Determine if log level will be logged
         *
         * @param logLevel set log level to print the statement
         * @return true if the parameter passed in is greater than or equal to the
         * current log level
         */
        public static boolean isLoggable(int logLevel) {
            return logLevel >= sLogLevel;
        }

        /**
         * Verbose log message.
         *
         * @param tag to describe the tag mode
         * @param s   print the string
         */
        public static void v(String tag, String s) {
            if (LOG.VERBOSE >= sLogLevel) {
                Log.v(tag, "" + s);
            }
        }

        /**
         * Debug log message.
         *
         * @param tag specify the tag
         * @param s   specify the string
         */
        public static void d(String tag, String s) {
            if (LOG.DEBUG >= sLogLevel) {
                Log.d(tag, "" + s);
            }
        }

        /**
         * Debug log message.
         *
         * @param tag specify the tag
         * @param s   specify the content
         */
        public static void d(String tag, String s, boolean mustLog) {
            if (BuildConfig.DEBUG || mustLog) {
                Log.d(tag, "" + s);
            }
        }


        /**
         * Info log message.
         *
         * @param tag specify the tag
         * @param s   specify the content
         */
        public static void i(String tag, String s) {
            if (LOG.INFO >= sLogLevel) {
                Log.i(tag, "" + s);
            }
        }

        public static void i(String tag, String string, boolean mustLog) {
            if (BuildConfig.DEBUG || mustLog) {
                Log.i(tag, "" + string);
            }
        }

        /**
         * Warning log message.
         *
         * @param tag specify the tag
         * @param s   specify the content
         */
        public static void w(String tag, String s) {
            if (LOG.WARN >= sLogLevel) {
                Log.w(tag, "" + s);
            }
        }

        /**
         * Error log message.
         *
         * @param tag specify the tag
         * @param s   specify the content
         */
        public static void e(String tag, String s) {
            if (LOG.ERROR >= sLogLevel) {
                Log.e(tag, "" + s);
            }
        }

        public static void e(String tag, String s, boolean mustLog) {
            if (BuildConfig.DEBUG || mustLog) {
                Log.e(tag, "" + s);
            }
        }

        /**
         * Verbose log message.
         *
         * @param tag specify the tag
         * @param s   specify the content
         * @param e   specify the error
         */
        public static void v(String tag, String s, Throwable e) {
            if (LOG.VERBOSE >= sLogLevel) {
                Log.v(tag, "" + s, e);
            }
        }

        /**
         * Debug log message.
         *
         * @param tag specify the tag
         * @param s   specify the content
         * @param e   specify the error
         */
        public static void d(String tag, String s, Throwable e) {
            if (LOG.DEBUG >= sLogLevel) {
                Log.d(tag, "" + s, e);
            }
        }

        /**
         * Info log message.
         *
         * @param tag specify the tag
         * @param s   specify the content
         * @param e   specify the error
         */
        public static void i(String tag, String s, Throwable e) {
            if (LOG.INFO >= sLogLevel) {
                Log.i(tag, "" + s, e);
            }
        }

        /**
         * Warning log message.
         *
         * @param tag specify the tag
         * @param s   specify the content
         * @param e   specify the error
         */
        public static void w(String tag, String s, Throwable e) {
            if (LOG.WARN >= sLogLevel) {
                Log.w(tag, "" + s, e);
            }
        }

        /**
         * Error log message.
         *
         * @param tag specify the tag
         * @param s   specify the content
         * @param e   specify the error
         */
        public static void e(String tag, String s, Throwable e) {
            if (LOG.ERROR >= sLogLevel) {
                Log.e(tag, "" + s, e);
            }
        }

        /**
         * Verbose log message with printf formatting.
         *
         * @param tag specify the content
         */
        public static void v(String tag, Throwable exception) {
            if (LOG.VERBOSE >= sLogLevel) {
                Log.v(tag, Log.getStackTraceString(exception));
            }
        }

        /**
         * Debug log message with printf formatting.
         *
         * @param tag specify the tag
         */
        public static void d(String tag, Throwable exception) {
            if (LOG.DEBUG >= sLogLevel) {
                Log.v(tag, Log.getStackTraceString(exception));
            }
        }

        /**
         * Info log message with printf formatting.
         *
         * @param tag specify the content
         */
        public static void i(String tag, Throwable exception) {
            if (LOG.INFO >= sLogLevel) {
                Log.v(tag, Log.getStackTraceString(exception));
            }
        }

        /**
         * Warning log message with printf formatting.
         *
         * @param tag specify the content
         */
        public static void w(String tag, Throwable exception) {
            if (LOG.WARN >= sLogLevel) {
                Log.v(tag, Log.getStackTraceString(exception));
            }
        }

        /**
         * Error log message with printf formatting.
         *
         * @param tag specify the content
         */
        public static void e(String tag, Throwable exception) {
            if (LOG.ERROR >= sLogLevel) {
                Log.v(tag, Log.getStackTraceString(exception));
            }
        }

        /**
         * Verbose log message with printf formatting.
         *
         * @param tag  specify the content
         * @param s    specify the string
         * @param args specify the argument
         */
        public static void v(String tag, String s, Object... args) {
            if (LOG.VERBOSE >= sLogLevel) {
                Log.v(tag, String.format(s, args));
            }
        }

        /**
         * Debug log message with printf formatting.
         *
         * @param tag  specify the content
         * @param s    specify the string need to print
         * @param args specify the argument if any
         */
        public static void d(String tag, String s, Object... args) {
            if (LOG.DEBUG >= sLogLevel) {
                Log.d(tag, String.format(s, args));
            }
        }

        /**
         * Info log message with printf formatting.
         *
         * @param tag  specify the content
         * @param s    specify the string need to print
         * @param args specify the argument if any
         */
        public static void i(String tag, String s, Object... args) {
            if (LOG.INFO >= sLogLevel) {
                Log.i(tag, String.format(s, args));
            }
        }

        /**
         * Warning log message with printf formatting.
         *
         * @param tag  specify the content
         * @param s    specify the string need to print
         * @param args specify the argument if any
         */
        public static void w(String tag, String s, Object... args) {
            if (LOG.WARN >= sLogLevel) {
                Log.w(tag, String.format(s, args));
            }
        }

        /**
         * Error log message with printf formatting.
         *
         * @param tag  specify the content
         * @param s    specify the string need to print
         * @param args specify the argument if any
         */
        public static void e(String tag, String s, Object... args) {
            if (LOG.ERROR >= sLogLevel) {
                Log.e(tag, String.format(s, args));
            }
        }

        public static void log(final String mTag, Exception e) {
            if (BuildConfig.DEBUG) {
                Log.i(mTag, String.valueOf(e));
            }
        }
}
