package com.backend.chain.controller;

import com.backend.chain.entity.Request;
import com.backend.chain.response.Response;
import com.backend.chain.response.ResponseFactory;
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
    public Response addRequest(Request req) {
        requestService.addRequest(req);
        return ResponseFactory.buildSuccessResult("Request Added!");
    }

    @GetMapping("api/del/{id}")
    public Response delRequest(@PathVariable Integer id) {
        requestService.deleteRequest(id);
        return ResponseFactory.buildSuccessResult("Request Deleted!");
    }

    @PostMapping("api/update")
    public Response updateRequest(Request req) {
        requestService.updateRequest(req.getId(), req.getPrice(),req.getBargainingUser(), req.getStatus(), req.getUpdateTime());
        return ResponseFactory.buildSuccessResult("Request Updated!");
    }

    @GetMapping("api/query/{userid}")
    public Response queryequest(@PathVariable String userid) {
        List<Request> list = requestService.getAllByUser(userid);
        return ResponseFactory.buildSuccessResult(list);
    }

}