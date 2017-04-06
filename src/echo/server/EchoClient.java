package echo.server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EchoClient extends JFrame{
	JTextArea area;
	JScrollPane scroll;
	JPanel p_south;
	JTextField t_input;
	JButton bt_connect;
	
	// 대화를 나눌 수 있는 소켓
	// 대화를 걸 때는 소켓으로부터 출력 스트림, 대화를 들을 때는 소켓으로부터 입력 스트림
	Socket socket;
	
	BufferedReader buffr;	// 청취 용도
	BufferedWriter buffw;	// 대화 시작 용도
	
	public EchoClient() {
		area=new JTextArea();
		scroll=new JScrollPane(area);
		p_south=new JPanel();
		t_input=new JTextField(15);
		bt_connect=new JButton("접속");
		
		t_input.addKeyListener(new KeyAdapter() {
			
			public void keyReleased(KeyEvent e) {
				// enter를 치면
				int key=e.getKeyCode();
				
				// 입력된 키가 enter라면
				if(key==KeyEvent.VK_ENTER){
					send();
				}
			}
		});
		
		// 버튼과 ActionListener 연결
		bt_connect.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		
		add(scroll);
		
		p_south.add(t_input);
		p_south.add(bt_connect);
		
		add(p_south, BorderLayout.SOUTH);
		setSize(300,400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	// 서버에 접속하는 메소드
	public void connect(){
		// Socket(String host, int port, boolean stream) 
		try {
			socket=new Socket("localhost", 7777);
			
			// 접속이 완료되었으니 대화를 나누기 위해 스트림을 얻어놓기
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 서버에 대화 보내기
	public void send(){
		// 소켓으로부터 입/출력 스트림을 생성하는데
		// 접속 버튼을 눌렀을 때 연결이 완료되면 만들어진 소켓으로부터 입/출력 스트림 생성하기!
		
		// TextField 값 얻기
		String msg=t_input.getText();
		try {
			// 스트림을 통해 출력
			// -> 서버 측 소켓에 데이터 전송
			// 한 줄을 모아서 보내야 하는데 한 줄의 끝이 명시되어 있지 않으므로 보내지 않은것이 됨!
			// 이때 이 데이터들은 버퍼에 쌓이고 보내지지 않음
			// buffw.write(msg);
			// 받는 쪽에서 한 줄의 끝을 이해하려면 "\n"을 표기해 버퍼의 끝을 알려주면 된다!
			buffw.write(msg+"\n");
			
			// 실행중인 프로그램에서 buffer write는 일정 메모리 공간을 만들어 임시 저장
			// "\n"을 만나면 버퍼에 쌓여있던 데이터가 보내짐
			// 버퍼 처리의 경우, 잔여물이 남지 않게 모두 보내주는 메소드 사용해야 함
			// flush()를 이용해 버퍼에 남아있을 지도 모르는 데이터를 대상으로 모두 출력시킴(버퍼를 비움)
			// write!=flush
			buffw.flush();
			
			// 서버에서 받은 메시지를 TextArea에 출력
			String servMsg=buffr.readLine();
			area.append(servMsg+"\n");
			t_input.setText("");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new EchoClient();
	}
}
