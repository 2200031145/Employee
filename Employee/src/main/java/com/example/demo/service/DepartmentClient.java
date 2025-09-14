package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.demo.response.DepartmentResponse;

@Component
public class DepartmentClient {

    @Autowired
    private RestTemplate restTemplate;

    private final String DEPT_SERVICE_URL = "http://localhost:9091/departments";

    public DepartmentResponse getDepartmentById(Long id) {
        return restTemplate.getForObject(DEPT_SERVICE_URL + "/" + id, DepartmentResponse.class);
    }
}
