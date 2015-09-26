package com.bonds0097.substratehelper;

import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by aramirez on 11/20/14.
 */
public class MethodLog {
    static private StringBuilder MethodCallInfo(Method method, Object... args)
    {
        StringBuilder output = new StringBuilder();
        output.append("METHOD: " + method.toGenericString() + "\n");
        for (Object arg : args)
        {
            output.append(" ARG: " + arg.getClass().getName() + " - " + arg.toString() + "\n");
        }

        return output;
    }

    static String ParseArg(Object arg)
    {
        StringBuilder output = new StringBuilder();
        output.append(" ARG: " + arg.getClass().getName() + "\n");

        output.append("  " + arg.toString() + "\n");

        return output.toString();
    }

    static private StringBuilder ConstructorCallInfo(Object... args)
    {
        StringBuilder output = new StringBuilder();
        output.append("CONSTRUCTOR CALLED\n");
        for (Object arg : args)
        {
            output.append(ParseArg(arg));
        }

        return output;
    }

    static private StringBuilder MethodCallReturnInfo(Method method, Object returned, Object... args)
    {
        StringBuilder output = MethodCallInfo(method, args);

        output.append(" RETURN TYPE: " + returned.getClass().getName() + "\n");
        output.append(" RETURN DATA: " + returned.toString() + "\n");

        return output;
    }

    static public void LogMethodCall(String logTag, Method method, Object returned, Object...args)
    {
        StringBuilder message = MethodCallReturnInfo(method, returned, args);

        Log.d(logTag, message.toString());
    }

    static public void LogMethodCall(String logTag, Method method, Object...args)
    {
        StringBuilder message = MethodCallInfo(method, args);

        Log.d(logTag, message.toString());
    }

    static public void LogConstructorCall(String logTag, Object...args)
    {
        StringBuilder message = ConstructorCallInfo(args);

        Log.d(logTag, message.toString());
    }
}
