package com.xworkz.utils;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class ConverterListToString implements Converter<List<String>,String> {

    @Override
    public String convert(MappingContext<List<String>, String> mappingContext) {

        List<String> list = mappingContext.getSource();

        if(list==null || list.isEmpty()){
            return null;
        }

        String result = list.stream().collect(Collectors.joining(", "));
        return result;
    }
}
