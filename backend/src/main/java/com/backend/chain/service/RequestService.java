package com.backend.chain.service;

import com.backend.chain.entity.Request;

import java.util.List;

public interface RequestService {
    int addRequest(Request req);

    List<Request> getAllByUser(String userId);

    int updateRequest(int id, double price, String bargainingUser, int status, int updateTime);

    int deleteRequest(int id);
}
