package com.in28minutes.rest.webservices.restfulwebservices.resources;


import com.in28minutes.rest.webservices.restfulwebservices.models.SomeBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {



    @GetMapping("/filtering")
    public SomeBean filtering()
    {
        return new SomeBean("Value1","Value2","Value3");
    }
    @GetMapping("/filtering-list")
    public List<SomeBean> filteringList()
    {
        return  Arrays.asList(new SomeBean("Value1","Value2","Value3"),
                                new SomeBean("Value4","Value5","Value6"));
    }


}