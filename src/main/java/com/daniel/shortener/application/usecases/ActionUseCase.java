package com.daniel.shortener.application.usecases;

public interface ActionUseCase<Input> {
    
    void execute(Input input);

}
