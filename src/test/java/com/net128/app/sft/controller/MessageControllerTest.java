package com.net128.app.sft.controller;

import com.net128.app.sft.service.Service;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class MessageControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private Service service;

    @Test
    public void test1()  {
    }
}
