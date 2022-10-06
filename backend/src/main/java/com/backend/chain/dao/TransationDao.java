package com.backend.chain.dao;

import com.backend.chain.entity.Transation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TransationDao  {
    @Insert("insert into transation(from,to,type,amount,price,status)" +
            "values (#{from},#{to},#{type},#{amount},#{price},#{status})")
 int addTransation(Transation req);
    @Select("select id,from,to,type,amount,price,status from transation where from=#{user} or to = #{user}")
    List<Transation> getAllByUser(String user);
}
