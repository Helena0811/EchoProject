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
	
	// ��ȭ�� ���� �� �ִ� ����
	// ��ȭ�� �� ���� �������κ��� ��� ��Ʈ��, ��ȭ�� ���� ���� �������κ��� �Է� ��Ʈ��
	Socket socket;
	
	BufferedReader buffr;	// û�� �뵵
	BufferedWriter buffw;	// ��ȭ ���� �뵵
	
	public EchoClient() {
		area=new JTextArea();
		scroll=new JScrollPane(area);
		p_south=new JPanel();
		t_input=new JTextField(15);
		bt_connect=new JButton("����");
		
		t_input.addKeyListener(new KeyAdapter() {
			
			public void keyReleased(KeyEvent e) {
				// enter�� ġ��
				int key=e.getKeyCode();
				
				// �Էµ� Ű�� enter���
				if(key==KeyEvent.VK_ENTER){
					send();
				}
			}
		});
		
		// ��ư�� ActionListener ����
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
	
	// ������ �����ϴ� �޼ҵ�
	public void connect(){
		// Socket(String host, int port, boolean stream) 
		try {
			socket=new Socket("localhost", 7777);
			
			// ������ �Ϸ�Ǿ����� ��ȭ�� ������ ���� ��Ʈ���� ������
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ������ ��ȭ ������
	public void send(){
		// �������κ��� ��/��� ��Ʈ���� �����ϴµ�
		// ���� ��ư�� ������ �� ������ �Ϸ�Ǹ� ������� �������κ��� ��/��� ��Ʈ�� �����ϱ�!
		
		// TextField �� ���
		String msg=t_input.getText();
		try {
			// ��Ʈ���� ���� ���
			// -> ���� �� ���Ͽ� ������ ����
			// �� ���� ��Ƽ� ������ �ϴµ� �� ���� ���� ��õǾ� ���� �����Ƿ� ������ �������� ��!
			// �̶� �� �����͵��� ���ۿ� ���̰� �������� ����
			// buffw.write(msg);
			// �޴� �ʿ��� �� ���� ���� �����Ϸ��� "\n"�� ǥ���� ������ ���� �˷��ָ� �ȴ�!
			buffw.write(msg+"\n");
			
			// �������� ���α׷����� buffer write�� ���� �޸� ������ ����� �ӽ� ����
			// "\n"�� ������ ���ۿ� �׿��ִ� �����Ͱ� ������
			// ���� ó���� ���, �ܿ����� ���� �ʰ� ��� �����ִ� �޼ҵ� ����ؾ� ��
			// flush()�� �̿��� ���ۿ� �������� ���� �𸣴� �����͸� ������� ��� ��½�Ŵ(���۸� ���)
			// write!=flush
			buffw.flush();
			
			// �������� ���� �޽����� TextArea�� ���
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
