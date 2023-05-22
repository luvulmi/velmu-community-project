package com.project.velmu.controller;


import com.project.velmu.domain.BoardDto;
import com.project.velmu.domain.PageHandler;
import com.project.velmu.domain.SearchCondition;
import com.project.velmu.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
  @Autowired
  BoardService boardService;

  @PostMapping("/modify")
  public String modify(BoardDto boardDto, Integer page, Integer pageSize, RedirectAttributes rattr, Model m, HttpSession session){
    String writer = (String)session.getAttribute("id");
    boardDto.setWriter(writer);
    try {
      if (boardService.modify(boardDto)!= 1)
        throw new Exception("Modify failed.");

      rattr.addAttribute("page", page);
      rattr.addAttribute("pageSize", pageSize);
      rattr.addFlashAttribute("msg", "MOD_OK");
      return "redirect:/board/list";
    } catch (Exception e) {
      e.printStackTrace();
      m.addAttribute(boardDto);
      m.addAttribute("page", page);
      m.addAttribute("pageSize", pageSize);
      m.addAttribute("msg", "MOD_ERR");
      return "board"; // 등록하려던 내용을 보여줘야 함.
    }
  }

  @GetMapping("/write")
  public String write(Model m) {
    m.addAttribute("mode","new");
    return "board"; // 읽기와 쓰기에 사용. 쓰기에 사용할 때는 mode=new
  }

  @PostMapping("/write")  // insert니까 delete인 remove하고 동일
  public String write(BoardDto boardDto, RedirectAttributes rattr, Model m, HttpSession session) {
    String writer = (String)session.getAttribute("id");
    boardDto.setWriter(writer);

    try {
      if (boardService.write(boardDto) != 1)
        throw  new Exception("Write failed.");
      rattr.addFlashAttribute("msg", "WRT_OK");
      return "redirect:/board/list";
    } catch (Exception e) {
       e.printStackTrace();
      m.addAttribute("mode", "new");
      m.addAttribute(boardDto);
      m.addAttribute("msg","WRT,ERR");
      return "board";
    }
  }

  @PostMapping("/remove")
  public String remove(Integer bno, Integer page,Integer pageSize,RedirectAttributes rattr, HttpSession session) {
    String writer = (String)session.getAttribute("id");
    String msg = "DEL_OK";
    try {
      if(boardService.remove(bno, writer)!=1) {
        throw new Exception("Delete failed.");
      }
    } catch (Exception e) {
      e.printStackTrace();
      msg = "DEL_ERR";
    }
    rattr.addAttribute("page", page);
    rattr.addAttribute("pageSize", pageSize);
    rattr.addAttribute("msg", msg);
    return "redirect:/board/list";
  }

  @GetMapping("/read")
  public String read(Integer bno, Integer page, Integer pageSize, RedirectAttributes rattr, Model m) {
    try {
      BoardDto boardDto = boardService.read(bno);
      m.addAttribute(boardDto);
      m.addAttribute("page", page);
      m.addAttribute("pageSize", pageSize);
    } catch (Exception e) {
      e.printStackTrace();
      rattr.addAttribute("page", page);
      rattr.addAttribute("pageSize", pageSize);
      rattr.addFlashAttribute("msg", "READ_ERR");
      return "redirect:/board/list";
    }
    return "board";
  }

  @GetMapping("/list")
  public String list(SearchCondition sc, Model m, HttpServletRequest request) {
    if(!loginCheck(request))
      return "redirect:/user/login?toURL="+request.getRequestURL();  // 로그인을 안했으면 로그인 화면으로 이동

    try {
      int totalCnt = boardService.getSearchResultCnt(sc);
      m.addAttribute("totalCnt", totalCnt);

      PageHandler pageHandler = new PageHandler(totalCnt, sc);

      List<BoardDto> list = boardService.getSearchResultPage(sc);
      m.addAttribute("list", list);
      m.addAttribute("ph",pageHandler);

      Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
      m.addAttribute("startOfToday", startOfToday.toEpochMilli());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "boardList"; // 로그인을 한 상태이면, 게시판 화면으로 이동
  }

  private boolean loginCheck(HttpServletRequest request) {
    // 1. 세션을 얻어서
    HttpSession session = request.getSession();
    // 2. 세션에 id가 있는지 확인, 있으면 true를 반환
    return session.getAttribute("id")!=null;
  }
}