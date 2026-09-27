package com.example.zgrani.event;

public class TooManyTermsException extends RuntimeException {

    private final int generatedCount;

    public TooManyTermsException(int generatedCount) {
        super("Generated " + generatedCount + " candidate terms, exceeding the limit of 30");
        this.generatedCount = generatedCount;
    }

    public int getGeneratedCount() {
        return generatedCount;
    }
}
