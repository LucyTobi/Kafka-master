package com.example.Kafka.Exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class BaseException extends Exception{
    /**
     *
     */
    private static final long serialVersionUID = 6293060990360355320L;

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    //define some error code
    public static final String CODE_NULL_POINTER = "NULL_ERROR";

    private Throwable cause;
    private String code;
    private String additionalInfor;
    private String solution;

    public BaseException(String code) {
        super();
        this.cause = null;
        this.code = code;
        this.additionalInfor = null;
    }

    /**
     *
     */
    public BaseException(String code, String additionalInfor) {
        super(additionalInfor);
        this.cause = null;
        this.code = code;
        this.additionalInfor = additionalInfor;

    }

    public BaseException(String code, String additionalInfor, String solution){
        super(additionalInfor);
        this.cause = null;
        this.code = code;
        this.additionalInfor = additionalInfor;
        this.solution = solution;

    }

    public BaseException(Throwable cause){
        super();
        this.cause = cause;
        this.code = null;
        this.additionalInfor = null;
    }

    public String getMessage(){
        if(this.cause == null) {
            return super.getMessage();
        }
        else {
            return this.cause.getMessage();
        }
    }

    public void printStackTrace(){
        if(this.cause == null) {
            super.printStackTrace();
        }
        else {
            this.cause.printStackTrace();
        }
    }

    public String toString() {
        if(this.cause == null) {
            return super.toString();
        }
        else {
            return this.cause.toString();
        }
    }

    public String getCode() {
        if(this.code != null) {
            return this.code;
        }
        else {
            if(this.cause != null) {
                if(this.cause instanceof BaseException ) {
                    return ((BaseException)this.cause).getCode();
                }
                else {
                    return null;
                }
            }
            else
            {
                return null;
            }
        }
    }

    /**
     * @return
     */
    public String getAdditionalInfor() {
        return additionalInfor;
    }

    /**
     * @return
     */
    public String getSolution() {
        return solution;
    }

}

