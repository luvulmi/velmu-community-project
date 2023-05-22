<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
  <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
</head>
<body>
<h2>commentTest</h2>
<button id="sendBtn" type="button">SEND</button>
<div id="commentList"></div>
<script>
  let bno = 782;
  let showList = function () {
    $.ajax({
      type:'GET',       // 요청 메서드
      url: '/ch4/comments?bno='+bno,  // 요청 URI
      success : function(result){
        $("commentList").html(result);    // 서버로부터 응답이 도착하면 호출될 함수
      },
      error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
    }); // $.ajax()
  }

  $(document).ready(function(){
    $("#sendBtn").click(function(){
      showList(bno);
    });
  });
</script>
</body>
</html>
