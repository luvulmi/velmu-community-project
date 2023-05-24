package com.project.velmu.dao;


import com.project.velmu.domain.BoardDto;
import com.project.velmu.domain.UserDto;

import java.util.List;

public interface UserDao {
  UserDto selectUser(String id) throws Exception;
  List<UserDto> selectAll() throws Exception;
  int deleteUser(String id) throws Exception;
  int insertUser(UserDto userDto) throws Exception;
  int updateUser(UserDto userDto) throws Exception;
  int count() throws Exception;
}