/*
 * ��� ���α׷��� ��ǻ� ���� ��ǻ�Ϳ� ��ǻ�Ͱ� ����Ǵ� ���� �ƴ϶� 
 * �߾ӿ� ������ �ΰ� ����ϴ� ��!
 * ������ ���� : ������ input�� output
 * 
 * echo ���α׷�
 * Ŭ���̾�Ʈ�� �޽����� �׵��� �ٽ� �����ϴ� ����� ����, ä�� ���� ���� 1�ܰ�
 * 
 * -> ����� 1�� ���Ӹ� ����
 * */
package echo.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	ServerSocket server;
	int port=7777;
	
	public EchoServer() {
		try {
			server=new ServerSocket(port);
			System.out.println("���� ����");
			
			// accept()�� �����ڰ� ���� �� ���� ���� ���
			// �����ڰ� ������ ��ȭ�� ������ socket�� ����!
			Socket socket=server.accept();
			
			// ���� ������ ������ ������ ����
			InetAddress inet=socket.getInetAddress();
			String ip=inet.getHostAddress();
			
			System.out.println(ip+"������ �߰�");
			
			// ��� �뵵
			// Ŭ���̾�Ʈ�� �����͸� �ޱ� ���� �Է� ��Ʈ�� ���
			// ����Ʈ -> ���� -> ���۱�ݱ��� ���׷��̵� �ؾ� �ѱ� ���� ���� ���� 
			BufferedReader buffr=new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			
			// ���ϴ� �뵵
			BufferedWriter buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));
			
			// Ŭ���̾�Ʈ ��ȭ�� ��� ���ϱ�
			// Ŭ���̾�Ʈ �� ���
			String msg;
			
			// ��ȭ�� ��� ����������, ����ΰ� �Ʒ��� while�� �ȿ� �����ϹǷ� 
			// �� �̻� �߰� �����ڿ� ���� ���� ����� �Ұ�
			// ��� : ���� ���� ���� ���� ����� 1�ο� ����
			while(true){
				// �޽��� ���
				msg=buffr.readLine();	
				System.out.println("Ŭ���̾�Ʈ�� ���� �� : "+msg);
				
				// �޽����� �ٽ� ������
				buffw.write(msg+"\n");	// �� �� ������
				buffw.flush();			// ���� ����
			}
			
			/*
			msg=buffr.readLine();	
			System.out.println("Ŭ���̾�Ʈ�� ���� �� : "+msg);
			*/
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new EchoServer();

	}

}
