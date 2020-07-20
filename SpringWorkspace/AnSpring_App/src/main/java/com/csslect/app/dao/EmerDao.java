package com.csslect.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EmerDao {
	

	DataSource dataSource;

	public EmerDao() { //������
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/cteam"); //context.xml��  ��� �̸� ���� ��
			/*dataSource = (DataSource) context.lookup("java:/comp/env/CSS");*/
		} catch (NamingException e) {
			e.getMessage();
		}

	}

	public int emerJoin(String emergency_name, String emergency_phonenum, 
			            String emergency_email) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		int state = -100; //�ʱ�ȭ
		
		try {
			conn=dataSource.getConnection();
			String query="insert into emergency_contact values('"+emergency_email
					       +"', '"+emergency_name+"', '"+emergency_phonenum+"') ";
			
			ps=conn.prepareStatement(query);
			state=ps.executeUpdate();  //����
			
			if(state>0) {
				System.out.println(state+"���Լ���");
			}else {
				System.out.println(state+"���Խ���");
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
