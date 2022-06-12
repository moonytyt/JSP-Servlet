<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<% 
	int i = 3;
	out.println("i = "+ i);
	request.setAttribute("ia", 3);
%>
<p>
i = <%= i %>
<p>
<!-- 출력이 안된다 -->
i = ${i }
<p>
<!-- request객체로 넘어온 값은 출력  -->
i = ${ia }

</body>
</html>