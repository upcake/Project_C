package com.csslect.app.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.csslect.app.dto.ANDto;
import com.csslect.app.dto.AlarmDTO;
import com.csslect.app.dto.EmerDTO;
import com.csslect.app.dto.InfoDTO;
import com.csslect.app.dto.KakaoMemberDTO;
import com.csslect.app.dto.MemberDTO;
import com.csslect.app.dto.NaverMemberDTO;
import com.csslect.app.dto.RecMedDTO;

public class ANDao {

	DataSource dataSource;

	public ANDao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/cteam");
			/* dataSource = (DataSource) context.lookup("java:/comp/env/CSS"); */
		} catch (NamingException e) {
			e.getMessage();
		}

	}

	//오토메딕 앱 로그인
    public MemberDTO anLogin(String member_email, String member_password) {
    	System.out.println("로그인들어옴");
    	MemberDTO adto = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;		
		
		System.out.println("DAO : "+member_password);
		try {
			connection = dataSource.getConnection();
			String query = "select * "					
							+ " from manage_member" 
							+ " where member_email = '" + member_email + "' and member_password = '" + member_password + "' ";
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			
			System.out.println("로그인 패스워드 확인 : "+member_password);
			
			while (resultSet.next()) {
				member_email = resultSet.getString("member_email");
				member_password = resultSet.getString("member_password");
				String member_nickname = resultSet.getString("member_nickname");
				String member_phonenum = resultSet.getString("member_phonenum"); 

				adto = new MemberDTO(member_email, member_password, member_nickname, member_phonenum);							
			}	
			
			System.out.println("MemberDTO member_email : " + adto.getMember_email());
			System.out.println("MemberDTO member_password : " + adto.getMember_password());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return adto;

	}
    
    //오토메딕 비밀번호찾기 (기존 비밀번호를 임시발급 비밀번호로 수정)
    public int anPwChk(String member_email, String member_password) {
    	System.out.println("비밀번호찾기 들어옴");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;
	
		try {			
					
			connection = dataSource.getConnection();
			String query = "update manage_member set " 			             
		             + " member_password = '" + member_password + "' "
		             + " where member_email = '" + member_email+ "' ";
			
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
	
			if (state > 0) {
				System.out.println("수정1성공");
				
			} else {
				System.out.println("수정1실패");
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
	
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
	
			}
		}
	
		return state;
	
	}
    
    //오토메딕 앱 이메일체크
    public MemberDTO anEmailChk(String member_email) {
    	System.out.println("이메일체크들어옴");
    	
    	MemberDTO adto = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;		
		
		try {
			connection = dataSource.getConnection();
			String query = "select * "					
							+ " from manage_member" 
							+ " where member_email = '" + member_email + "'" ;			
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				member_email = resultSet.getString("member_email");
				String member_nickname = resultSet.getString("member_nickname");
				String member_phonenum = resultSet.getString("member_phonenum"); 

				adto = new MemberDTO(member_email, member_nickname, member_phonenum);							
			}	
			
			System.out.println("MemberDTO member_email : " + adto.getMember_email());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return adto;

	}
    
  //오토메딕 앱 닉네임체크
    public MemberDTO anNickNameChk(String member_nickname) {
    	System.out.println("닉네임체크들어옴");
    	
    	MemberDTO adto = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;		
		
		try {
			connection = dataSource.getConnection();
			String query = "select * "					
							+ " from manage_member" 
							+ " where member_nickname = '" + member_nickname + "'" ;			
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				member_nickname = resultSet.getString("member_nickname");

				adto = new MemberDTO(member_nickname);							
			}	
			
			System.out.println("MemberDTO member_nickname : " + adto.getMember_nickname());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return adto;

	}
    
    //오토메딕 앱 회원가입
    public int anJoin(String member_email, String member_password, String member_nickname, 
    							String member_phonenum) { 
    	
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int state = -100;
		
		try {
			connection = dataSource.getConnection();
			String query = "insert into manage_member(member_email, member_password, member_nickname, member_phonenum) " + 
			               "values('" + member_email + "', '" + member_password + "', '" + member_nickname + "', '" + 
			               			member_phonenum + "')";
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
			
			if (state > 0) {
				System.out.println(state + "삽입성공");				
			} else {
				System.out.println(state + "삽입실패");
			}
			
		} catch (Exception e) {			
			System.out.println(e.getMessage());
		} finally {
			try {				
				
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return state;

	}
    
    //네이버 로그인
    public NaverMemberDTO anNaverLogin(String naver_email,String naver_nickname) {
    	System.out.println("네이버 로그인들어옴");
    	NaverMemberDTO adto = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;		
		
		try {
			connection = dataSource.getConnection();
			String query = "select * "					
							+ " from naver_member" 
							+ " where naver_email = '" + naver_email + "' and naver_nickname = '" + naver_nickname + "' ";
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				naver_email = resultSet.getString("naver_email");
				naver_nickname = resultSet.getString("naver_nickname");

				adto = new NaverMemberDTO(naver_email, naver_nickname);							
			}	
			
			System.out.println("NaverMemberDTO naver_email : " + adto.getNaver_email());
			System.out.println("NaverMemberDTO naver_nickname : " + adto.getNaver_nickname());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return adto;

	}
        
    //네이버 회원가입
	public int anNaverJoin(String naver_email, String naver_nickname) {
		System.out.println("네이버회원가입 들어옴");
		System.out.println("네이버이메일"+naver_email);
		System.out.println("네이버닉네임"+naver_nickname);
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int state = -100;

		try {
			connection = dataSource.getConnection();
			String query = "insert into naver_member(naver_email, naver_nickname) "
					+ "values('" + naver_email + "', '" + naver_nickname + "')";
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();

			if (state > 0) {
				System.out.println(state + "네아로 회원가입 삽입성공");
			} else {
				System.out.println(state + "네아로 회원가입 삽입실패");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {

				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return state;
	}

	//카카오 로그인
	public KakaoMemberDTO anKakaoLogin(String kakao_email,String kakao_nickname) {
		System.out.println("카카오 로그인들어옴");
    	KakaoMemberDTO adto = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;		
		
		try {
			connection = dataSource.getConnection();
			String query = "select * "					
							+ " from kakao_member" 
							+ " where kakao_email = '" + kakao_email + "' and kakao_nickname = '" + kakao_nickname + "' ";
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				kakao_email = resultSet.getString("kakao_email");
				kakao_nickname = resultSet.getString("kakao_nickname");

				adto = new KakaoMemberDTO(kakao_email, kakao_nickname);							
			}	
			
			System.out.println("KakaoMemberDTO kakao_email : " + adto.getKakao_email());
			System.out.println("KakaoMemberDTO kakao_nickname : " + adto.getKakao_nickname());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return adto;

	}
	
	// 카카오 회원가입
	public int anKakaoJoin(String kakao_email, String kakao_nickname) {
		System.out.println("카카오회원가입 들어옴");
		System.out.println("카카오이메일" + kakao_email);
		System.out.println("카카오닉네임" + kakao_nickname);
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int state = -100;

		try {
			connection = dataSource.getConnection();
			String query = "insert into kakao_member(kakao_email, kakao_nickname) " + "values('" + kakao_email + "', '"
					+ kakao_nickname + "')";
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();

			if (state > 0) {
				System.out.println(state + "카카오 회원가입 삽입성공");
			} else {
				System.out.println(state + "카카오 회원가입 삽입실패");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {

				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return state;
	}

	/*조수연 끝============================================================*/

	/*노혜지 시작============================================================*/
public ArrayList<EmerDTO> EmerSelectMulti() {
		
		ArrayList<EmerDTO> adtos = new ArrayList<EmerDTO>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			String query = "select * from emergency_contact";
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			System.out.println("dao");
			
			while (resultSet.next()) {
				
				String emergency_name = resultSet.getString("emergency_name");
				String emergency_phonenum = resultSet.getString("emergency_phonenum");
				String emergency_email = resultSet.getString("emergency_email");				
				int alarm=resultSet.getInt("alarm");
				int alarmperiod=resultSet.getInt("alarmperiod");
				int message=resultSet.getInt("message");				
				int tel=resultSet.getInt("tel");	
				
				adtos.add(new EmerDTO(emergency_name,emergency_phonenum,emergency_email,
						alarm,alarmperiod,message,tel));
				
			}
			System.out.println("됨 ");
			System.out.println("adtos크기" + adtos.size());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				
			}
		}
		
		return adtos;
		
	}

	public ArrayList<RecMedDTO> CalSelectMulti() {
		
		ArrayList<RecMedDTO> adtos = new ArrayList<RecMedDTO>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			String query = "select * from medicine_record order by record_take_time asc";
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			System.out.println("dao");
			
			while (resultSet.next()) {
				
				String record_email = resultSet.getString("record_email");
				String record_alarm_name = resultSet.getString("record_alarm_name");
				String record_take_time = resultSet.getString("record_take_time");				
				
				
				adtos.add(new RecMedDTO(record_email,record_alarm_name,record_take_time));
				
			}
			System.out.println("됨 ");
			System.out.println("adtos크기" + adtos.size());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				
			}
		}
		
		return adtos;
		
	}

	public int emerUpdateMultiNo(String emergency_name, String emergency_phonenum, 
			   int alarm,int alarmperiod,int message,int tel) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;
		
		try {
			// 이름은 수정할수 없음
			connection = dataSource.getConnection();
			String query = "update emergency_contact set " 
			+ " emergency_phonenum = '" + emergency_phonenum 
			+ "' , alarm = " + alarm + " , alarmperiod = " + alarmperiod 
			+ " , message = " + message + " , tel= "+ tel
					+ " where emergency_name = '"+emergency_name+"'";
			
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
			
			if (state > 0) {
				
				System.out.println("수정1성공12");
				
			} else {
				System.out.println("수정1실패");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				
			}
		}
		
		return state;
	}
	
	public int emerUpdateMultiNo2(String emergency_name,int alarm,int alarmperiod,int message,int tel) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;
		
		try {
			// 이름은 수정할수 없음
			connection = dataSource.getConnection();
			String query = "update emergency_contact set " 					
					+ "alarm = " + alarm + " , alarmperiod = " + alarmperiod 
					+ " , message = " + message + " , tel= "+ tel
					+ " where emergency_name = '"+emergency_name+"'";
			
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
			
			if (state > 0) {
				
				System.out.println("수정2성공");
				
			} else {
				System.out.println("수정2실패");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				
			}
		}
		
		return state;
	}
	
	
	public int emerDeleteMulti(String emergency_name) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;
		
		try {
			connection = dataSource.getConnection();
			String query = "delete from emergency_contact where emergency_name='"
			               + emergency_name +"'";
			
			System.out.println(emergency_name);
			
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
			
			
			
			if (state > 0) {
				System.out.println("삭제성공");
			} else {
				System.out.println("삭제실패");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return state;
		
	}

	public int emerNamechk(String emergency_name, String emergency_phonenum) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		int a=0;
		int state = -1;
		
		try {
			connection = dataSource.getConnection();
			String query = "select * from emergency_contact where emergency_name='"
					+ emergency_name +"' or emergency_phonenum='"
					+emergency_phonenum+"'";
			
			System.out.println(emergency_name);
			System.out.println(emergency_phonenum);
			
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();			
			
			
			if (resultSet.next()) {
				state=-1;				
				//System.out.println("꺼져"+emergency_phonenum);
				System.out.println("중복있음");
			} else {
				state=1;
				System.out.println("중복없음");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return state;
		
	}

	public int emerJoin(String emergency_name, String emergency_phonenum, String emergency_email,
			     int alarm,int alarmperiod,int message,int tel) {

		Connection conn = null;
		PreparedStatement ps = null;
		int state = -100; // 초기화

		try {
			conn = dataSource.getConnection();
			String query = "insert into emergency_contact values('" + emergency_email;
			query+= "', '" + emergency_name + "', '"+emergency_phonenum +"', "+alarm; 
			query+=", "+ alarmperiod+", "+ message+", "+tel+") ";
			

			ps = conn.prepareStatement(query);
			state = ps.executeUpdate(); // 실행
			
			System.out.println("dao");
			System.out.println(emergency_name);
			System.out.println(emergency_phonenum);
			System.out.println(emergency_email);
			System.out.println(alarm);
			System.out.println(alarmperiod);
			System.out.println(message);
			System.out.println(tel);

			if (state > 0) {
				System.out.println(state + "삽입성공");
			} else {
				System.out.println(state + "삽입실패");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
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
	
	public int memoinsert(String record_email, String record_alarm_name, 
			String record_take_time) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		int state = -100; // 초기화
		
		try {
			conn = dataSource.getConnection();
			String query = "insert into medicine_record values('" + record_email;
			query+= "', '" + record_alarm_name + "', '"+record_take_time +"')"; 
			
			
			
			ps = conn.prepareStatement(query);
			state = ps.executeUpdate(); // 실행
			
			System.out.println("dao");
			System.out.println(record_email);
			System.out.println(record_alarm_name);
			System.out.println(record_alarm_name);
			
			
			if (state > 0) {
				System.out.println(state + "삽입성공");
			} else {
				System.out.println(state + "삽입실패");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
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
	/*노혜지 끝================================================================================ */
	public ArrayList<ANDto> anSelectMulti() {

		ArrayList<ANDto> adtos = new ArrayList<ANDto>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
			String query = "select id, name, hire_date, image_path " + " from android" + " order by id desc"; // DB에서
																												// 정렬을
																												// 먼저 하고
																												// 보내줄것.
																												// 아이디가
																												// int니까
																												// 아이디가
																												// 높은것을
																												// 최 상단에
																												// 넣기로
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				Date date = resultSet.getDate("hire_date");
				String imagePath = resultSet.getString("image_path");

				ANDto adto = new ANDto(id, name, date, imagePath);
				adtos.add(adto);
			}

			System.out.println("adtos크기" + adtos.size());

		} catch (Exception e) {

			System.out.println(e.getMessage());
		} finally {
			try {

				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return adtos;

	}

	public int anInsertMulti(int id, String name, String date, String dbImgPath) {

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		int state = -1;

		try {
			//
			connection = dataSource.getConnection();
			String query = "insert into android(id, name, hire_date, image_path) " + "values(" + id + ",'" + name + "',"
					+ "to_date('" + date + "','rr/mm/dd') , '" + dbImgPath + "' )";

			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();

			if (state > 0) {
				System.out.println(state + "삽입성공");
			} else {
				System.out.println(state + "삽입실패");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return state;

	}

	public int anUpdateMulti(int id, String name, String date, String dbImgPath) {

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		int state = -1;

		try {
			// 아이디는 수정할수 없음
			connection = dataSource.getConnection();
			String query = "update android set " + " name = '" + name + "' " + ", hire_date = '" + date + "' "
					+ ", image_path = '" + dbImgPath + "' " + " where id = " + id;

			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();

			if (state > 0) {
				System.out.println("수정1성공");

			} else {
				System.out.println("수정1실패");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return state;

	}

	public int anUpdateMultiNo(int id, String name, String date) {

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		int state = -1;

		try {
			// 아이디는 수정할수 없음
			connection = dataSource.getConnection();
			String query = "update android set " + " name = '" + name + "' " + ", hire_date = '" + date + "' "
					+ " where id = " + id;

			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();

			if (state > 0) {
				System.out.println("수정2성공");

			} else {
				System.out.println("수정2실패");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return state;
	}

	public int anDeleteMulti(int id) {

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		int state = -1;

		try {
			connection = dataSource.getConnection();
			String query = "delete from android where id=" + id;

			System.out.println(id);

			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();

			if (state > 0) {
				System.out.println("삭제성공");
			} else {
				System.out.println("삭제실패");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return state;

	}
	
	public int alarmInsert(String alarm_Email, String alarm_Id, String alarm_Title, String alarm_Sunday, String alarm_Monday, String alarm_Tuesday, String alarm_Wednesday, String alarm_Thursday, String alarm_Friday, String alarm_Saturday, String alarm_Times, String alarm_Ringtime1_Hour, String alarm_Ringtime1_Minute, String alarm_Ringtime2_Hour, String alarm_Ringtime2_Minute, String alarm_Ringtime3_Hour, String alarm_Ringtime3_Minute, String alarm_Volume, String alarm_Bell, String alarm_Vib, String alarm_Repeat) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int state = -100;

		try {
			connection = dataSource.getConnection();
			String query =
					"INSERT INTO alarm(" + 
						"alarm_Email, alarm_Id, alarm_Title, alarm_Sunday, alarm_Monday, alarm_Tuesday, " + 
						"alarm_Wednesday, alarm_Thursday, alarm_Friday, alarm_Saturday, alarm_Times, " +
						"alarm_Ringtime1_Hour, alarm_Ringtime1_Minute, alarm_Ringtime2_Hour, alarm_Ringtime2_Minute, alarm_Ringtime3_Hour, " +
						"alarm_Ringtime3_Minute, alarm_Volume, alarm_Bell, alarm_Vib, alarm_Repeat)" +
					"VALUES ('" +
						alarm_Email + "', '" + alarm_Id + "', '" + alarm_Title + "', '" + alarm_Sunday + "', '" + alarm_Monday + "', '" + alarm_Tuesday + "', " +
						"'" + alarm_Wednesday + "', '" + alarm_Thursday + "', '" + alarm_Friday + "', '" + alarm_Saturday + "', '" + alarm_Times + "', " + 
						"'" + alarm_Ringtime1_Hour + "', '" + alarm_Ringtime1_Minute + "', '" + alarm_Ringtime2_Hour + "', '" + alarm_Ringtime2_Minute + "', '" + alarm_Ringtime3_Hour + "', " +
						"'" + alarm_Ringtime3_Minute + "', '" + alarm_Volume + "', '" + alarm_Bell + "', '" + alarm_Vib + "', '" + alarm_Repeat + "')";
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();

			if (state > 0) {
				System.out.println(state + "삽입 성공");
			} else {
				System.out.println(state + "삽입 실패");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return state;
	}

	public ArrayList<AlarmDTO> alarmSelectMulti() {		
		
		ArrayList<AlarmDTO> alarmDTOList = new ArrayList<AlarmDTO>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;		
		
		try {
			connection = dataSource.getConnection();
			String query = "SELECT * FROM alarm ORDER BY alarm_times DESC, alarm_Ringtime1_Hour, alarm_Ringtime1_Minute, alarm_Ringtime2_Hour, alarm_Ringtime2_Minute, alarm_Ringtime3_Hour, alarm_Ringtime3_Minute";
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				String alarm_Email = resultSet.getString("alarm_Email");
				String alarm_Id = resultSet.getString("alarm_Id");
				String alarm_Title = resultSet.getString("alarm_Title");
				String alarm_Sunday = resultSet.getString("alarm_Sunday");
				String alarm_Monday = resultSet.getString("alarm_Monday");
				String alarm_Tuesday = resultSet.getString("alarm_Tuesday");

				String alarm_Wednesday = resultSet.getString("alarm_Wednesday");
				String alarm_Thursday = resultSet.getString("alarm_Thursday");
				String alarm_Friday = resultSet.getString("alarm_Friday");
				String alarm_Saturday = resultSet.getString("alarm_Saturday");
				String alarm_Times = resultSet.getString("alarm_Times");

				String alarm_Ringtime1_Hour = resultSet.getString("alarm_Ringtime1_Hour");
				String alarm_Ringtime1_Minute = resultSet.getString("alarm_Ringtime1_Minute");
				String alarm_Ringtime2_Hour = resultSet.getString("alarm_Ringtime2_Hour");
				String alarm_Ringtime2_Minute = resultSet.getString("alarm_Ringtime2_Minute");
				String alarm_Ringtime3_Hour = resultSet.getString("alarm_Ringtime3_Hour");
				
				String alarm_Ringtime3_Minute = resultSet.getString("alarm_Ringtime3_Minute");
				String alarm_Volume = resultSet.getString("alarm_Volume");
				String alarm_Bell = resultSet.getString("alarm_Bell");
				String alarm_Vib = resultSet.getString("alarm_Vib");
				String alarm_Repeat = resultSet.getString("alarm_Repeat");
				
				AlarmDTO alarmDTO = new AlarmDTO(
						alarm_Email, alarm_Id, alarm_Title, alarm_Sunday, alarm_Monday, alarm_Tuesday,
						alarm_Wednesday, alarm_Thursday, alarm_Friday, alarm_Saturday, alarm_Times,
						alarm_Ringtime1_Hour, alarm_Ringtime1_Minute, alarm_Ringtime2_Hour, alarm_Ringtime2_Minute, alarm_Ringtime3_Hour,
						alarm_Ringtime3_Minute, alarm_Volume, alarm_Bell, alarm_Vib, alarm_Repeat);
				alarmDTOList.add(alarmDTO);			
			}	
			
			System.out.println("alarmDTOList 크기" + alarmDTOList.size());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return alarmDTOList;

	}

	public int alarmUpdateMulti(String alarm_Email, String alarm_Id, String alarm_Title, String alarm_Sunday,
			String alarm_Monday, String alarm_Tuesday, String alarm_Wednesday, String alarm_Thursday,
			String alarm_Friday, String alarm_Saturday, String alarm_Times, String alarm_Ringtime1_Hour,
			String alarm_Ringtime1_Minute, String alarm_Ringtime2_Hour, String alarm_Ringtime2_Minute,
			String alarm_Ringtime3_Hour, String alarm_Ringtime3_Minute, String alarm_Volume, String alarm_Bell,
			String alarm_Vib, String alarm_Repeat) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		int state = -1;

		try {
			connection = dataSource.getConnection();
			String query = "UPDATE alarm SET "
					+ "alarm_Title = '" + alarm_Title + "' " 
					+ ", alarm_Sunday = '" + alarm_Sunday + "' "
					+ ", alarm_Monday = '" + alarm_Monday + "' "
					+ ", alarm_Tuesday = '" + alarm_Tuesday + "' "
					+ ", alarm_Wednesday = '" + alarm_Wednesday + "' "
					+ ", alarm_Thursday = '" + alarm_Thursday + "' "
					+ ", alarm_Friday = '" + alarm_Friday + "' "
					
					+ ", alarm_Saturday = '" + alarm_Saturday + "' "
					+ ", alarm_Times = '" + alarm_Times + "' "
					+ ", alarm_Ringtime1_Hour = '" + alarm_Ringtime1_Hour + "' "
					+ ", alarm_Ringtime1_Minute = '" + alarm_Ringtime1_Minute + "' "
					+ ", alarm_Ringtime2_Hour = '" + alarm_Ringtime2_Hour + "' "
					+ ", alarm_Ringtime2_Minute = '" + alarm_Ringtime2_Minute + "' "
					
					+ ", alarm_Ringtime3_Hour = '" + alarm_Ringtime3_Hour + "' "
					+ ", alarm_Ringtime3_Minute = '" + alarm_Ringtime3_Minute + "' "
					+ ", alarm_Volume = '" + alarm_Volume + "' "
					+ ", alarm_Bell = '" + alarm_Bell + "' "
					+ ", alarm_Vib = '" + alarm_Vib + "' "
					+ ", alarm_Repeat = '" + alarm_Repeat + "' "					
					+ "WHERE alarm_Id = " + alarm_Id;

			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();

			if (state > 0) {
				System.out.println("수정 성공");

			} else {
				System.out.println("수정 실패");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}
		return state;
	} //alarmUpdateMulti()

	public int alarmDeleteMulti(int id) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		int state = -1;

		try {
			connection = dataSource.getConnection();
			String query = "DELETE FROM alarm WHERE alarm_id=" + id;

			System.out.println(id);

			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();

			if (state > 0) {
				System.out.println("삭제 성공");
			} else {
				System.out.println("삭제 실패");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return state;
		
	} //alarmDeleteMulti()

	public InfoDTO infoSelectMulti(String info_email) {
		InfoDTO infoDTO = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
			String query = "SELECT member_password, member_nickname, member_phonenum, member_profile FROM manage_member WHERE member_email = '" + info_email + "'"; 
			//DB에서 정렬을 먼저 하고 보내줄것.
			//예시 : 아이디가 int라면 아이디가 높은것을 최 상단에 넣기로
			
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				String member_password = resultSet.getString("member_password");
				String member_nickname = resultSet.getString("member_nickname");
				String member_phonenum = resultSet.getString("member_phonenum");
				String member_profile = resultSet.getString("member_profile");

				infoDTO = new InfoDTO(info_email, member_password, member_nickname, member_phonenum, member_profile);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {

				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return infoDTO;
	} //infoSelectMulti()
}
