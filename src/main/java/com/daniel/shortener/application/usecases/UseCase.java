package com.daniel.shortener.application.usecases;

public interface UseCase<Input, Output> {

    Output execute(Input input);
    
}
