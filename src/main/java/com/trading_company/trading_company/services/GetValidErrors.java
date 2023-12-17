package com.trading_company.trading_company.services;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class GetValidErrors {
    public String getErrors(BindingResult bindingResult){

        return bindingResult
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList()
                .toString();
    }
}
