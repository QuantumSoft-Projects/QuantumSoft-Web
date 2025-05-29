package com.QuantomSoft.Exception;

public class InvalidJobDataException extends Throwable {
    public InvalidJobDataException(String jobTitleCannotBeEmpty) {
        super(jobTitleCannotBeEmpty);
    }
}
