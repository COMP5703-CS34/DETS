package com.backend.chain.controller;



import com.backend.chain.entity.Request;
import com.backend.chain.response.Response;


import com.backend.chain.service.RequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class RequestController {

    @Autowired
    RequestService requestService;

    @PostMapping("api/add")
    public Response addRequest(Request req){
        requestService.addRequest(req);
        return Response.success();
    }
    @GetMapping("api/del/{id}")
    public Response delRequest(@PathVariable Integer id){
        requestService.deleteRequest(id);
        return Response.success();
    }
    @PostMapping("api/update")
    public Response updateRequest(Request req){
        requestService.updateRequest(req.getId(),req.getStatus());
        return Response.success();
    }
    @GetMapping("api/query/{userid}")
    public Response addRequest(@PathVariable String userid){
        List<Request> list=requestService.getAllByUser(userid);
        return Response.success(list);
    }


}