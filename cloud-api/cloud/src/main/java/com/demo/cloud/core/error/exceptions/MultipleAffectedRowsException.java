package com.demo.cloud.core.error.exceptions;

public class MultipleAffectedRowsException extends RuntimeException {
    public MultipleAffectedRowsException(String tableName, String operation) {
        super("Zero or more than one rows in " + tableName + " table is affected by " + operation + " operation.");
    }
}
