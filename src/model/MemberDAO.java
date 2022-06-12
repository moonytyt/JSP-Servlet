package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// ����Ŭ �����ͺ��̽��� �����ϰ� SELECT, INSERT, UPDATE, DELETE�۾��� �������ִ� Ŭ�����Դϴ�.
public class MemberDAO {

	// ����Ŭ�� �����ϴ� �ҽ��� �ۼ�
	String id = "JSP1"; // ���Ӿ��̵�
	String pass = "1234";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";// ����url

	Connection con; // �����ͺ��̽��� ������ �� �ֵ��� ����
	PreparedStatement pstmt; // ������ ���̽����� ������ ��������ִ� ��ü
	ResultSet rs; // ������ ���̽��� ���̺��� ����� ���Ϲ޾� �ڹٿ� �������ִ� ��ü
	
	
	// ������ ���̽��� ������ �� �ֵ��� �����ִ� �޼ҵ�
	public void getCon() {
		try {
			//1.�ش� ������ ���̽��� ����Ѵٰ� ����(Ŭ������ ��� = ����Ŭ��)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2. �ش� ������ ���̽��� ����
			con = DriverManager.getConnection(url,id,pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ������ ���̽��� �ѻ���� ȸ�� ������ �������ִ� �޼ҵ�
	public void insertMember(MemberBean mbean){
		try{
			getCon();
			//3. ������ ���� �غ�
			String sql = "INSERT INTO MEMBER VALUES(?,?,?,?,?,?,?,?)";
			//4. ������ ����ϵ��� ����
			PreparedStatement pstmt = con.prepareStatement(sql); //jsp���� ������ ����ϵ��� ����
			//5. ?�� �°� �����͸� ����
			pstmt.setString(1, mbean.getId());
			pstmt.setString(2, mbean.getPass1());
			pstmt.setString(3, mbean.getEmail());
			pstmt.setString(4, mbean.getTel());
			pstmt.setString(5, mbean.getHobby());
			pstmt.setString(6, mbean.getJob());
			pstmt.setString(7, mbean.getAge());
			pstmt.setString(8, mbean.getInfo());
			//6. ����Ŭ���� ������ �����Ͻÿ�
			pstmt.executeUpdate(); //INSERT, UPDATE, DELETE�� ����ϴ� �޼���
			//7. �ڿ��ݳ�
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//���ȸ���� ������ �������ִ� �޼ҵ� ȣ��
	public ArrayList<MemberBean> allSelectMember(){
		ArrayList<MemberBean> mbean = new ArrayList<>();
		return mbean;
	}
	
}
