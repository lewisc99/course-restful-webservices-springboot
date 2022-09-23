package com.in28minutes.rest.webservices.restfulwebservices.resources;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.in28minutes.rest.webservices.restfulwebservices.models.SomeBean;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {



    @GetMapping("/filtering")
    public MappingJacksonValue filtering() //hide field 2
    {
       SomeBean someBean = new SomeBean("Value1","Value2","Value3");

        MappingJacksonValue mappingJacksonValue =  new MappingJacksonValue(someBean); //add serialization logic
        SimpleBeanPropertyFilter filter =  SimpleBeanPropertyFilter.filterOutAllExcept("field1","field3"); //define filters
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter",filter); //call the filter define in the class
        mappingJacksonValue.setFilters(filters);
        return  mappingJacksonValue;
    }
    @GetMapping("/filtering-list") //hide field 1
    public MappingJacksonValue filteringList()
    {

       List<SomeBean> list =  Arrays.asList(new SomeBean("Value1","Value2","Value3"),
                new SomeBean("Value4","Value5","Value6"));

        MappingJacksonValue mappingJacksonValue =  new MappingJacksonValue(list); //add serialization logic
        SimpleBeanPropertyFilter filter =  SimpleBeanPropertyFilter.filterOutAllExcept("field2","field3"); //define filters
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter",filter); //call the filter define in the class
        mappingJacksonValue.setFilters(filters);
        return  mappingJacksonValue;

    }


}
