package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

// ����Ŭ �����ͺ��̽��� �����ϰ� SELECT, INSERT, UPDATE, DELETE�۾��� �������ִ� Ŭ�����Դϴ�.
public class MemberDAO {

	/*// ����Ŭ�� �����ϴ� �ҽ��� �ۼ�
	String id = "JSP1"; // ���Ӿ��̵�
	String pass = "1234";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";// ����url
*/
	Connection con; // �����ͺ��̽��� ������ �� �ֵ��� ����
	PreparedStatement pstmt; // ������ ���̽����� ������ ��������ִ� ��ü
	ResultSet rs; // ������ ���̽��� ���̺��� ����� ���Ϲ޾� �ڹٿ� �������ִ� ��ü

	// ������ ���̽��� ������ �� �ֵ��� �����ִ� �޼ҵ�
	public void getCon() {
		//Ŀ�ؼ� Ǯ�� �̿��Ͽ� ������ ���̽��� ����
		try{
			//�ܺο��� �����͸� �о����� �ϱ⿡
			Context initctx = new InitialContext();
			//��Ĺ ������ ������ ��Ƴ��� ������ �̵�
			Context envctx = (Context) initctx.lookup("java:comp/env");
			//������ �ҽ� ��ü�� ����
			DataSource ds = (DataSource) envctx.lookup("jdbc/pool");
			//������ �ҽ��� �������� Ŀ�ؼ��� �������ּ���
			con = ds.getConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
		/*try {
			// 1.�ش� ������ ���̽��� ����Ѵٰ� ����(Ŭ������ ��� = ����Ŭ��)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. �ش� ������ ���̽��� ����
			con = DriverManager.getConnection(url, id, pass);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	// ������ ���̽��� �ѻ���� ȸ�� ������ �������ִ� �޼ҵ�
	public void insertMember(MemberBean mbean) {
		try {
			getCon();
			// 3. ������ ���� �غ�
			String sql = "INSERT INTO MEMBER VALUES(?,?,?,?,?,?,?,?)";
			// 4. ������ ����ϵ��� ����
			PreparedStatement pstmt = con.prepareStatement(sql); // jsp���� ������
																	// ����ϵ��� ����
			// 5. ?�� �°� �����͸� ����
			pstmt.setString(1, mbean.getId());
			pstmt.setString(2, mbean.getPass1());
			pstmt.setString(3, mbean.getEmail());
			pstmt.setString(4, mbean.getTel());
			pstmt.setString(5, mbean.getHobby());
			pstmt.setString(6, mbean.getJob());
			pstmt.setString(7, mbean.getAge());
			pstmt.setString(8, mbean.getInfo());
			// 6. ����Ŭ���� ������ �����Ͻÿ�
			pstmt.executeUpdate(); // INSERT, UPDATE, DELETE�� ����ϴ� �޼���
			// 7. �ڿ��ݳ�
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ���ȸ���� ������ �������ִ� �޼ҵ� ȣ��
	public ArrayList<MemberBean> allSelectMember() {
		ArrayList<MemberBean> list = new ArrayList<>();

		// ������ �����ͺ��̽��� ����ó���� �ݵ�� �ؾ��մϴ�.
		try {
			// Ŀ�ؼ� ����
			getCon();
			// �����غ�
			String sql = "SELECT * FROM MEMBER";
			// ������ ��������ִ� ��ü ����
			pstmt = con.prepareStatement(sql);
			// ������ ���� ��Ų ����� �����ؼ� �޾���(����Ŭ ���̺��� �˻��� ����� �ڹٰ�ü�� ����)
			rs = pstmt.executeQuery();
			// �ݺ����� ����ؼ� rs�� ����� �����͸� �����س��ƾ� ��
			while (rs.next()) {// ����� ������ ��ŭ���� �ݺ����� �����ڴٶ�� ���Դϴ�.
				MemberBean mbean = new MemberBean();// Ŀ������ �������� �����͸� ��Ŭ������ ����
				mbean.setId(rs.getString(1));
				mbean.setPass1(rs.getString(2));
				mbean.setEmail(rs.getString(3));
				mbean.setTel(rs.getString(4));
				mbean.setHobby(rs.getString(5));
				mbean.setJob(rs.getString(6));
				mbean.setAge(rs.getString(7));
				mbean.setInfo(rs.getString(8));
				// ��Ű¡�� MemberBeanŬ������ ����Ʈ�� ����
				list.add(mbean);
			}
			// �ڿ� �ݳ�
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// �� ����� ����Ʈ ����
		return list;
	}

	// �ѻ���� ���� ������ �����ϴ� �޼ҵ� �ۼ�
	public MemberBean oneSelectMember(String id) {
		// �ѻ���� ���� ������ �����ϱ⿡ ��Ŭ���� ��ü ����
		MemberBean mbean = new MemberBean();
		try {
			// Ŀ�ؼ� ����
			getCon();
			// �����غ�
			String sql = "SELECT * FROM MEMBER WHERE ID=?";
			pstmt = con.prepareStatement(sql);
			// ?�� ���� ����
			pstmt.setString(1, id);
			// ���� ����
			rs = pstmt.executeQuery();
			if (rs.next()) {// ���ڵ尡 �ִٸ�
				mbean.setId(rs.getString(1));
				mbean.setPass1(rs.getString(2));
				mbean.setEmail(rs.getString(3));
				mbean.setTel(rs.getString(4));
				mbean.setHobby(rs.getString(5));
				mbean.setJob(rs.getString(6));
				mbean.setAge(rs.getString(7));
				mbean.setInfo(rs.getString(8));
			}
			// �ڿ� �ݳ�
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ����
		return mbean;
	}

	// ��ȸ���� �н����尪�� �����ϴ� �޼ҵ� �ۼ�
	public String getPass(String id) {
		// ��Ʈ������ �����ؾ� �ϱ⶧���� ��Ʈ������ ����
		String pass = "";

		try {
			getCon();
			String sql = "SELECT PASS1 FROM MEMBER WHERE ID=?";
			pstmt = con.prepareStatement(sql);
			// ?�� ���� ����
			pstmt.setString(1, id);
			// ���� ����
			rs = pstmt.executeQuery();
			if (rs.next()) {
				pass = rs.getString(1);// �н����� ���� ����� �÷��ε���
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pass;
	}

	// �� ȸ���� ������ �����ϴ� �޼ҵ� �ۼ�
	public void updateMember(MemberBean bean) {
		getCon();

		try {

			String sql = "UPDATE MEMBER SET EMAIL=?, TEL=? WHERE ID=?";
			// �������� ��ü ����
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getEmail());
			pstmt.setString(2, bean.getTel());
			pstmt.setString(3, bean.getId());

			// ��������
			pstmt.executeUpdate();
			// �ڿ��ݳ�
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// �� ȸ���� �����ϴ� �޼ҵ� �ۼ�
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
