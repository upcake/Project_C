<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>   

<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.JsonObject"%>

<%@page import="com.csslect.app.dto.AlarmDTO"%>

<%@page import="org.springframework.ui.Model"%>
<%@page import="java.sql.*, java.sql.Date, javax.sql.*, javax.naming.*, 
					java.util.*, java.io.PrintWriter" %>
					
<%		
	Gson gson = new Gson();
	String json = gson.toJson((ArrayList<AlarmDTO>)request.getAttribute("alarmSelectMulti"));
	
	out.println(json);
%>