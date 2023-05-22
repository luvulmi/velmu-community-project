package com.project.velmu.domain;

public class PageHandler {
  //  private int page; // 현재 페이지
//  private int pageSize; // 한 페이지 크기
//  private String option;
//  private String keyword;
  private SearchCondition sc;

  private int totalCnt; // 총 게시물 갯수
  private int naviSize = 10; // 페이지 내비게이션 크기
  private int totalPage; // 전체 페이지 갯수
  private int beginPage; // 네비게이션 첫번째 페이지
  private int endPage; // 네비게이션 마지막 페이지
  private boolean showPrev; // 이전 페이지로 이동하는 링크를 보여줄 것인지 여부
  private boolean showNext; // 다음 페이지 이동하는 링크 보여줄 여부

  public PageHandler(int totalCnt, SearchCondition sc) {
    this.totalCnt = totalCnt;
    this.sc = sc;

    doPaging(totalCnt, sc);
  }

  public void doPaging(int totalCnt, SearchCondition sc){
    this.totalPage = totalCnt / sc.getPageSize() + (totalCnt % sc.getPageSize()==0? 0:1);
    this.sc.setPage(Math.min(sc.getPage(), totalPage));  // page가 totalPage보다 크지 않게
    this.beginPage = (this.sc.getPage() -1) / naviSize * naviSize + 1; // 11 -> 11, 10 -> 1, 15->11. 따로 떼어내서 테스트
    this.endPage = Math.min(beginPage + naviSize - 1, totalPage);
    this.showPrev = beginPage!=1;
    this.showNext = endPage!=totalPage;
  }

  public int getTotalCnt() {
    return totalCnt;
  }

  public void setTotalCnt(int totalCnt) {
    this.totalCnt = totalCnt;
  }

  public int getNaviSize() {
    return naviSize;
  }

  public void setNaviSize(int naviSize) {
    this.naviSize = naviSize;
  }

  public int getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }

  public int getBeginPage() {
    return beginPage;
  }

  public void setBeginPage(int beginPage) {
    this.beginPage = beginPage;
  }

  public int getEndPage() {
    return endPage;
  }

  public void setEndPage(int endPage) {
    this.endPage = endPage;
  }

  public boolean isShowPrev() {
    return showPrev;
  }

  public void setShowPrev(boolean showPrev) {
    this.showPrev = showPrev;
  }

  public boolean isShowNext() {
    return showNext;
  }

  public void setShowNext(boolean showNext) {
    this.showNext = showNext;
  }

  public SearchCondition getSc() {
    return sc;
  }
  public void setSc(SearchCondition sc) {
    this.sc = sc;
  }

  void print() {
    System.out.println("page = " + sc.getPage());
    System.out.print(showPrev ? "[PREV] " : "");
    for (int i = beginPage; i <= endPage; i++) {
      System.out.print(i + " ");
    }
    System.out.println(showNext ? " [NEXT]" : "");
  }

  @Override
  public String toString() {
    return "PageHandler{" +
        "sc=" + sc +
        ", totalCnt=" + totalCnt +
        ", naviSize=" + naviSize +
        ", totalPage=" + totalPage +
        ", beginPage=" + beginPage +
        ", endPage=" + endPage +
        ", showPrev=" + showPrev +
        ", showNext=" + showNext +
        '}';
  }
}
