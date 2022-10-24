package com.backend.chain.service.impl;

import com.backend.chain.entity.Request;
import com.backend.chain.dao.RequestDao;
import com.backend.chain.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    private RequestDao requestDao;

    @Override
    public int addRequest(Request req) {
        return requestDao.addRequest(req);
    }

    @Override
    public List<Request> getAllByUser(String userId) {
        return requestDao.getAllByUser(userId);
    }

    @Override
    public int updateRequest(int id, double price, String bargainingUser, int status, int updateTime) {
        return requestDao.updateRequest(id, price, bargainingUser, status, updateTime);
    }

    @Override
    public int deleteRequest(int id) {
        return requestDao.deleteRequest(id);
    }
}
