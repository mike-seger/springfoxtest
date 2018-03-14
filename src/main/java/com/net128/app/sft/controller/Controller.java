package com.net128.app.sft.controller;

import com.net128.app.sft.model.Attachment;
import com.net128.app.sft.model.Message;
import com.net128.app.sft.service.MessageService;
import com.net128.app.sft.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
public class Controller {
    @Inject
    private Service service;

    @GetMapping(value="/countries")
    public Map<Country, String> getCountries() {
        return new HashMap<>();
    }
}
