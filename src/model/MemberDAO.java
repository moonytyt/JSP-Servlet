package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

// 오라클 데이터베이스에 연결하고 SELECT, INSERT, UPDATE, DELETE작업을 실행해주는 클래스입니다.
public class MemberDAO {

	/*// 오라클에 접속하는 소스를 작성
	String id = "JSP1"; // 접속아이디
	String pass = "1234";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";// 접속url
*/
	Connection con; // 데이터베이스에 접근할 수 있도록 설정
	PreparedStatement pstmt; // 데이터 베이스에서 쿼리를 실행시켜주는 객체
	ResultSet rs; // 데이터 베이스의 테이블의 결과를 리턴받아 자바에 저장해주는 객체

	// 데이터 베이스에 접근할 수 있도록 도와주는 메소드
	public void getCon() {
		//커넥션 풀을 이용하여 데이터 베이스에 접근
		try{
			//외부에서 데이터를 읽어드려야 하기에
			Context initctx = new InitialContext();
			//톰캣 서버에 정보를 담아놓은 곳으로 이동
			Context envctx = (Context) initctx.lookup("java:comp/env");
			//데이터 소스 객체를 선언
			DataSource ds = (DataSource) envctx.lookup("jdbc/pool");
			//데이터 소스를 기준으로 커넥션을 연결해주세요
			con = ds.getConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
		/*try {
			// 1.해당 데이터 베이스를 사용한다고 선언(클래스를 등록 = 오라클용)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. 해당 데이터 베이스에 접속
			con = DriverManager.getConnection(url, id, pass);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	// 데이터 베이스에 한사람의 회원 정보를 저장해주는 메소드
	public void insertMember(MemberBean mbean) {
		try {
			getCon();
			// 3. 접속후 쿼리 준비
			String sql = "INSERT INTO MEMBER VALUES(?,?,?,?,?,?,?,?)";
			// 4. 쿼리를 사용하도록 설정
			PreparedStatement pstmt = con.prepareStatement(sql); // jsp에서 쿼리를
																	// 사용하도록 설정
			// 5. ?에 맞게 데이터를 맵핑
			pstmt.setString(1, mbean.getId());
			pstmt.setString(2, mbean.getPass1());
			pstmt.setString(3, mbean.getEmail());
			pstmt.setString(4, mbean.getTel());
			pstmt.setString(5, mbean.getHobby());
			pstmt.setString(6, mbean.getJob());
			pstmt.setString(7, mbean.getAge());
			pstmt.setString(8, mbean.getInfo());
			// 6. 오라클에서 쿼리를 실행하시오
			pstmt.executeUpdate(); // INSERT, UPDATE, DELETE시 사용하는 메서드
			// 7. 자원반납
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 모든회원의 정보를 리턴해주는 메소드 호출
	public ArrayList<MemberBean> allSelectMember() {
		ArrayList<MemberBean> list = new ArrayList<>();

		// 무조건 데이터베이스는 예외처리를 반드시 해야합니다.
		try {
			// 커넥션 연결
			getCon();
			// 쿼리준비
			String sql = "SELECT * FROM MEMBER";
			// 쿼리를 실행시켜주는 객체 선언
			pstmt = con.prepareStatement(sql);
			// 쿼리를 실행 시킨 결과를 리턴해서 받아줌(오라클 테이블의 검색된 결과를 자바객체에 저장)
			rs = pstmt.executeQuery();
			// 반복문을 사용해서 rs에 저장된 데이터를 추출해놓아야 함
			while (rs.next()) {// 저장된 데이터 만큼까지 반복문을 돌리겠다라는 뜻입니다.
				MemberBean mbean = new MemberBean();// 커럼으로 나뉘어진 데이터를 빈클래스에 저장
				mbean.setId(rs.getString(1));
				mbean.setPass1(rs.getString(2));
				mbean.setEmail(rs.getString(3));
				mbean.setTel(rs.getString(4));
				mbean.setHobby(rs.getString(5));
				mbean.setJob(rs.getString(6));
				mbean.setAge(rs.getString(7));
				mbean.setInfo(rs.getString(8));
				// 패키징된 MemberBean클래스를 리스트에 저장
				list.add(mbean);
			}
			// 자원 반납
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 다 저장된 리스트 리턴
		return list;
	}

	// 한사람의 대한 정보를 리턴하는 메소드 작성
	public MemberBean oneSelectMember(String id) {
		// 한사람에 대한 정보만 리턴하기에 빈클래스 객체 생성
		MemberBean mbean = new MemberBean();
		try {
			// 커넥션 연결
			getCon();
			// 쿼리준비
			String sql = "SELECT * FROM MEMBER WHERE ID=?";
			pstmt = con.prepareStatement(sql);
			// ?에 값을 맵핑
			pstmt.setString(1, id);
			// 쿼리 실행
			rs = pstmt.executeQuery();
			if (rs.next()) {// 레코드가 있다면
				mbean.setId(rs.getString(1));
				mbean.setPass1(rs.getString(2));
				mbean.setEmail(rs.getString(3));
				mbean.setTel(rs.getString(4));
				mbean.setHobby(rs.getString(5));
				mbean.setJob(rs.getString(6));
				mbean.setAge(rs.getString(7));
				mbean.setInfo(rs.getString(8));
			}
			// 자원 반납
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 리턴
		return mbean;
	}

	// 한회원의 패스워드값을 리턴하는 메소드 작성
	public String getPass(String id) {
		// 스트링으로 리턴해야 하기때문에 스트링변수 선언
		String pass = "";

		try {
			getCon();
			String sql = "SELECT PASS1 FROM MEMBER WHERE ID=?";
			pstmt = con.prepareStatement(sql);
			// ?에 값을 맵핑
			pstmt.setString(1, id);
			// 쿼리 실행
			rs = pstmt.executeQuery();
			if (rs.next()) {
				pass = rs.getString(1);// 패스워드 값이 저장된 컬럼인덱스
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pass;
	}

	// 한 회원의 정보를 수정하는 메소드 작성
	public void updateMember(MemberBean bean) {
		getCon();

		try {

			String sql = "UPDATE MEMBER SET EMAIL=?, TEL=? WHERE ID=?";
			// 쿼리실행 객체 선언
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getEmail());
			pstmt.setString(2, bean.getTel());
			pstmt.setString(3, bean.getId());

			// 쿼리실행
			pstmt.executeUpdate();
			// 자원반납
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 한 회원을 삭제하는 메소드 작성
	public void deleteMember(String id) {
		getCon();
		try {
			String sql = "DELETE FROM MEMBER WHERE ID = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
