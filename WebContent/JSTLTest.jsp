<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- JSTL을 사용하기위해 꼭 추가해야한다!  -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 변수 선언 -->
<c:set var="i" value="4" />

<!-- if문 사용법 -->
<c:if test="${i>3}">
	안녕하세요<br>
</c:if>

<!-- 반복문 for -->
<c:forEach var="i" begin="1" end="10">
점심시간입니다.${i }<br>
</c:forEach>
<c:forEach var="i" begin="1" end="10">
<c:set var="sum" value="${sum=sum+i }"/>
</c:forEach>
${sum }
</body>
</html>