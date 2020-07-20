package com.csslect.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EmerDao {
	

	DataSource dataSource;

	public EmerDao() { //생성자
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/cteam"); //context.xml에  줬던 이름 쓰면 됨
			/*dataSource = (DataSource) context.lookup("java:/comp/env/CSS");*/
		} catch (NamingException e) {
			e.getMessage();
		}

	}

	public int emerJoin(String emergency_name, String emergency_phonenum, 
			            String emergency_email) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		int state = -100; //초기화
		
		try {
			conn=dataSource.getConnection();
			String query="insert into emergency_contact values('"+emergency_email
					       +"', '"+emergency_name+"', '"+emergency_phonenum+"') ";
			
			ps=conn.prepareStatement(query);
			state=ps.executeUpdate();  //실행
			
			if(state>0) {
				System.out.println(state+"삽입성공");
			}else {
				System.out.println(state+"삽입실패");
			}
	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {				
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}
		
		return state;
	}

}
