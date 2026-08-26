package com.sandro.tradeoptimizer.exception;

import java.util.UUID;

public class RunNotFoundException extends RuntimeException{

    public RunNotFoundException(UUID requestId){
        super("No optimization run found for this id: " + requestId);
    }
}
