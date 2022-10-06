package com.backend.chain.service.impl;

import com.backend.chain.entity.Transation;
import com.backend.chain.dao.TransationDao;
import com.backend.chain.service.TransationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

@Service
public class TransationserviceImpl  implements TransationService  {
    @Autowired
    private TransationDao transationDao;
    @Override
    public int addTransation(Transation req) {
        return transationDao.addTransation(req);
    }

    @Override
    public List<Transation> getAllByUser(String userId) {
        return transationDao.getAllByUser(userId);
    }

}
