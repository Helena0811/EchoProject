/*
 * 통신 프로그램은 사실상 직접 컴퓨터와 컴퓨터가 연결되는 것이 아니라 
 * 중앙에 서버를 두고 통신하는 것!
 * 서버의 역할 : 끝없는 input과 output
 * 
 * echo 프로그램
 * 클라이언트의 메시지를 그데로 다시 전달하는 방식의 서버, 채팅 구현 기초 1단계
 * 
 * -> 현재는 1인 접속만 가능
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
			System.out.println("서버 생성");
			
			// accept()은 접속자가 있을 때 까지 무한 대기
			// 접속자가 들어오면 대화를 시작할 socket을 만듬!
			Socket socket=server.accept();
			
			// 접속 정보를 소켓이 가지고 있음
			InetAddress inet=socket.getInetAddress();
			String ip=inet.getHostAddress();
			
			System.out.println(ip+"접속자 발견");
			
			// 듣는 용도
			// 클라이언트의 데이터를 받기 위해 입력 스트림 얻기
			// 바이트 -> 문자 -> 버퍼기반까지 업그레이드 해야 한글 한줄 적용 가능 
			BufferedReader buffr=new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			
			// 말하는 용도
			BufferedWriter buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));
			
			// 클라이언트 대화를 듣고 말하기
			// 클라이언트 말 듣기
			String msg;
			
			// 대화는 계쏙 가능하지만, 실행부가 아래의 while문 안에 존재하므로 
			// 더 이상 추가 접속자에 대한 접속 허용은 불가
			// 결론 : 최초 가장 빨리 들어온 사람의 1인용 서버
			while(true){
				// 메시지 듣기
				msg=buffr.readLine();	
				System.out.println("클라이언트가 보낸 말 : "+msg);
				
				// 메시지를 다시 보내기
				buffw.write(msg+"\n");	// 한 줄 보내기
				buffw.flush();			// 버퍼 비우기
			}
			
			/*
			msg=buffr.readLine();	
			System.out.println("클라이언트가 보낸 말 : "+msg);
			*/
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new EchoServer();

	}

}
