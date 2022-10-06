package com.backend.chain.service;

import com.backend.chain.entity.Transation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TransationService {
    int addTransation(Transation req);
    List<Transation> getAllByUser(String userId);
}
