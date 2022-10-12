package com.backend.chain.dao;

import com.backend.chain.entity.Request;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RequestDao {
    @Insert("insert into request(`from`,`to`,type,amount,price,status,createTime,updateTime,bargainingUser)" +
            "values (#{from},#{to},#{type},#{amount},#{price},#{status}, #{createTime}, #{updateTime}, #{bargainingUser})")
    int addRequest(Request req);

    @Select("select id,`from`,`to`,type,amount,price,status,createTime,updateTime,bargainingUser from request where `from`=#{user} or `to` = #{user}")
    List<Request> getAllByUser(String user);

    @Update("update request set status = #{status} , price = #{price} , bargainingUser = #{bargainingUser} where id = #{id}")
    int updateRequest(int id, double price,String bargainingUser, int status);

    @Delete("delete from request where id = #{id}")
    int deleteRequest(int id);
}
