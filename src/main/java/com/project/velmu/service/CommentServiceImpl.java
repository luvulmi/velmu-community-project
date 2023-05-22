package com.project.velmu.service;

import com.project.velmu.dao.BoardDao;
import com.project.velmu.dao.CommentDao;
import com.project.velmu.domain.CommentDto;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
//  @Autowired
  BoardDao boardDao;
  CommentDao commentDao;

//    @Autowired 여러 개 주입시 필드 주입보다 생성자 주입을 더 권장.
    public CommentServiceImpl(CommentDao commentDao, BoardDao boardDao) {
        this.commentDao = commentDao;
        this.boardDao = boardDao;
    }

  @Override
  public int getCount(Integer bno) throws Exception {
    return commentDao.count(bno);
  }

  @Override
  @Transactional(rollbackFor = Exception.class) // 예외 발생 시 예외. 체크드 에외는 롤백이 안되므로 붙여주기.
  public int remove(Integer cno, Integer bno, String commenter) throws Exception {
    int rowCnt = boardDao.updateCommentCnt(bno, -1);
    System.out.println("updateCommentCnt - rowCnt = " + rowCnt);
//        throw new Exception("test");
    rowCnt = commentDao.delete(cno, commenter);
    System.out.println("rowCnt = " + rowCnt);
    return rowCnt;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int write(CommentDto commentDto) throws Exception {
    boardDao.updateCommentCnt(commentDto.getBno(), 1);
//                throw new Exception("test");
    return commentDao.insert(commentDto);
  }

  @Override
  public List<CommentDto> getList(Integer bno) throws Exception {
//        throw new Exception("test");
    return commentDao.selectAll(bno);
  }

  @Override
  public CommentDto read(Integer cno) throws Exception {
    return commentDao.select(cno);
  }

  @Override
  public int modify(CommentDto commentDto) throws Exception {
    return commentDao.update(commentDto);
  }
}