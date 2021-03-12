package step1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerMain {

	public static void main(String[] args) {
		ServerSocket server = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			// 서버 소켓 오픈 - 포트번호  ---> 서버 오픈
			server = new ServerSocket(1234);
			// 클라이언트 접속
			Socket client = server.accept();
			System.out.println(client.getInetAddress());//클라이언트 아이피 주소
			// 스트림 초기화
			pw = new PrintWriter(client.getOutputStream());
			br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			// 데이터를 주고 받고
			String msg = br.readLine();//클라이언트가 보낸 메세지 수신
			pw.println(msg);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			// 소켓 close
			try {
				if(pw!=null)pw.close();
				if(br!=null)br.close();
				if(server!=null)server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
