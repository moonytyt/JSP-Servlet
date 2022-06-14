package controll;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//   -/HelloWorld��� �ּ� url�� ǥ�����־�� �� ���� Ŭ������ ����-
@WebServlet("/HelloWorld")
public class HelloWorld extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		reqPro(request, response);
	}
	
	
	//�ϰ�ó�� ��, doget�̴� dopost�� �Ʒ� reqPro�޼ҵ尡 ����ǰ� ����
	protected void reqPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ȭ�鿡  HelloWorld��� ����� �ϱ�, jsp������ �Ѱ��� �����͸� ����
		String msg = "Hello World �ȳ��ϼ���";
		Integer data =12;
		
		
		// jsp������ �����͸� request�� �Ѱ���
		request.setAttribute("msg", msg);
		request.setAttribute("data", data);
		
		//�������� jsp�� ȣ���ϸ鼭 �����͸� ���� �Ѱ��ִ� ��ü�� ����
		RequestDispatcher dis = request.getRequestDispatcher("HelloWorld.jsp");//JSP���ϸ��� ���
		dis.forward(request, response);
	}
}
