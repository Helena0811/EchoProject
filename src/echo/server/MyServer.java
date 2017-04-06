/*
 * java�� �̿��� ������ ���α׷� �ۼ�
 * -> ��ȭ�� �޴� ��
 * ��	��ȭ�� �ϴ� ��
 * 114	��ȭ�� �޴� ��
 * 
 * ��� ���α׷�
 * Ŭ���̾�Ʈ�� ��� ��Ʈ���� ������ �Է� ��Ʈ���� �ְ����
 * */
package echo.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
	// ��ȭ�� ������ ���� ������ �˷��ֱ� ���� ��ü, ���� ��ȭ�� ���� �� ����
	ServerSocket server;
	
	// ������ Ŭ���̾�Ʈ�� ã�ƿ��⸦ ��ٸ��Ƿ�, Ŭ���̾�Ʈ IP�� ����� ��Ʈ��ȣ�� �����ϸ� ��
	// ��Ģ) ��Ʈ��ȣ�� �����Ӱ� ���ϸ� ��
	// ����1) 0~1023 �̹� �ý����� �����ϰ� ����
	// ����2) ������ ���α׷����� ���ؼ� ����		ex) Oracle 1521, MySql 3306
	int port=8888;
	
	// ServerSocket�� accept()�� ���� ��ȯ��
	Socket socket;
	
	public MyServer() {
		try {
			//ServerSocket(int port) 
			// Creates a server socket, bound to the specified port.
			server=new ServerSocket(port);
			
			System.out.println("���� ����");
			
			// Ŭ���̾�Ʈ�� ������ ��ٸ�
			// ������ ���� ������ ���� ���(����) -> Stream�� read()�迭�� ����
			// Ŭ���̾�Ʈ�� ������ accept()�� ������ �� 
			// -> ���� Ŭ���̾�Ʈ�� ���൵ �����ϰ� �Ϸ��� ���� ������ ������ ��
			// �湮�ϴ� Ŭ���̾�Ʈ���� �� ���ھ��� �Է¹ް� ��ü��
			while(true){
				// ��ȭ�� ��� ���� = ����
				/*
				 * Listens for a connection to be made to this socket and accepts it. 
				 * The method blocks until a connection is made.
				 * */
				socket=server.accept();
				System.out.println("������ �߰�");
				
				/*
				 * ������ �̿��Ͽ� �����͸� �ް��� �ϴ� ��쿡�� �Է½�Ʈ��,
				 * �����͸� �������� �ϴ� ��쿡�� ��½�Ʈ��
				 * */
				// byte����� �Է½�Ʈ��
				InputStream is=socket.getInputStream();
				
				// �ѱ��� �����Ƿ� Stream ���׷��̵�
				InputStreamReader reader=null; 
				reader=new InputStreamReader(is);
				
				int data;
				
				while(true){
					data=reader.read();	// 1byte�� �о����
					System.out.print((char)data);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		new MyServer();

	}

}
