package com.backend.chain.dao;

import com.backend.chain.entity.Request;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RequestDao {
    /**
     * add new request record to db
     * @param req
     * @return
     */
    @Insert("insert into request(`from`,`to`,type,amount,price,status,createTime,updateTime,bargainingUser)" +
            "values (#{from},#{to},#{type},#{amount},#{price},#{status}, #{createTime}, #{updateTime}, #{bargainingUser})")
    int addRequest(Request req);

    /**
     * select all request record of user
     * @param user
     * @return
     */
    @Select("select id,`from`,`to`,type,amount,price,status,createTime,updateTime,bargainingUser from request where `from`=#{user} or `to` = #{user}")
    List<Request> getAllByUser(String user);

    /**
     * update request status, bargainingUser, price by id
     * @param id
     * @param price
     * @param bargainingUser
     * @param status
     * @return
     */
    @Update("update request set status = #{status} , price = #{price} , bargainingUser = #{bargainingUser} where id = #{id}")
    int updateRequest(int id, double price,String bargainingUser, int status);

    /**
     * delete request by id
     * @param id
     * @return
     */
    @Delete("delete from request where id = #{id}")
    int deleteRequest(int id);
}
