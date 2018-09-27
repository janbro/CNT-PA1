package com.janbro;

/**
 * Command factory for all commands
 * Math functions implemented here
 */
public class CommandFactory {

    public static final String ADD = "add";
    public static final String SUBTRACT = "subtract";
    public static final String MULTIPLY = "multiply";
    public static final String BYE = "bye";
    public static final String TERMINATE = "terminate";

    public static final String[] ALL = { ADD, SUBTRACT, MULTIPLY, BYE, TERMINATE };

    public static int add(int[] iargs) {
        int result = 0;
        for(int i=0; i < iargs.length; i++) {
            result += iargs[i];
        }
        return result;
    }

    public static int subtract(int[] iargs) {
        int result = iargs[0];
        for(int i=1; i < iargs.length; i++) {
            result -= iargs[i];
        }
        return result;
    }

    public static int multiply(int[] iargs) {
        int result = iargs[0];
        for(int i=1; i < iargs.length; i++) {
            result *= iargs[i];
        }
        return result;
    }

}
