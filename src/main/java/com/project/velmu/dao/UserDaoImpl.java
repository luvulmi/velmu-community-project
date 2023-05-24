package com.project.velmu.dao;

import com.project.velmu.domain.UserDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

  @Autowired
  private SqlSession session;
  private static String namespace = "com.project.velmu.dao.UserMapper.";

  @Override
  public UserDto selectUser(String id) throws Exception {
    return session.selectOne(namespace + "selectUser");
  }

  @Override
  public List<UserDto> selectAll() throws Exception {
    return session.selectList(namespace + "select");
  }

  // 사용자 정보를 user_info테이블에 저장하는 메서드
  @Override
  public int insertUser(UserDto dto) throws Exception {
    return session.insert(namespace + "insert", dto);
  }

  @Override
  public int updateUser(UserDto dto) throws Exception {
    return session.update(namespace + "update", dto);
  }

  @Override
  public int deleteUser(String id) throws Exception {
    return session.delete(namespace + "deleteUser"); //  insert, delete, update
  }

  @Override
  public int count() throws Exception {
    return session.selectOne(namespace + "count");
  }
}
