/*
 * java를 이용한 서버측 프로그램 작성
 * -> 전화를 받는 쪽
 * 고객	전화를 하는 쪽
 * 114	전화를 받는 쪽
 * 
 * 통신 프로그램
 * 클라이언트의 출력 스트림과 서버의 입력 스트림을 주고받음
 * */
package echo.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
	// 대화를 나누기 전에 접속을 알려주기 위한 객체, 아직 대화는 나눌 수 없음
	ServerSocket server;
	
	// 서버는 클라이언트가 찾아오기를 기다리므로, 클라이언트 IP와 약속한 포트번호만 보유하면 됨
	// 원칙) 포트번호는 자유롭게 정하면 됨
	// 예외1) 0~1023 이미 시스템이 점유하고 있음
	// 예외2) 유명한 프로그램들은 피해서 설정		ex) Oracle 1521, MySql 3306
	int port=8888;
	
	// ServerSocket의 accept()에 의해 반환됨
	Socket socket;
	
	public MyServer() {
		try {
			//ServerSocket(int port) 
			// Creates a server socket, bound to the specified port.
			server=new ServerSocket(port);
			
			System.out.println("서버 생성");
			
			// 클라이언트의 접속을 기다림
			// 접속이 있을 때까지 무한 대기(지연) -> Stream의 read()계열과 같음
			// 클라이언트가 들어오면 accept()는 끝나게 됨 
			// -> 여러 클라이언트의 수행도 가능하게 하려면 무한 루프로 돌려야 함
			// 방문하는 클라이언트마다 한 글자씩만 입력받고 대체함
			while(true){
				// 대화용 통신 도구 = 소켓
				/*
				 * Listens for a connection to be made to this socket and accepts it. 
				 * The method blocks until a connection is made.
				 * */
				socket=server.accept();
				System.out.println("접속자 발견");
				
				/*
				 * 소켓을 이용하여 데이터를 받고자 하는 경우에는 입력스트림,
				 * 데이터를 보내고자 하는 경우에는 출력스트림
				 * */
				// byte기반의 입력스트림
				InputStream is=socket.getInputStream();
				
				// 한글이 깨지므로 Stream 업그레이드
				InputStreamReader reader=null; 
				reader=new InputStreamReader(is);
				
				int data;
				
				while(true){
					data=reader.read();	// 1byte씩 읽어들임
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
