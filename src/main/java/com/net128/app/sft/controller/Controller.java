package com.net128.app.sft.controller;

import com.net128.app.sft.model.Country;
import com.net128.app.sft.service.Service;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {
    @Inject
    private Service service;

    @GetMapping(value="/countries")
    public Map<Country, String> getCountries() {
        return new HashMap<>();
    }
}
