package step2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class EchoClientMain {

	public static void main(String[] args) {
		Socket server = null;
		Scanner sc = new Scanner(System.in);
		BufferedReader br = null;
		PrintWriter pw = null;
		//서버 접속
		try {
			server = new Socket("127.0.0.1",1234);
			//스트림 초기화
			try {
				br = new BufferedReader(new InputStreamReader(server.getInputStream()));
				pw = new PrintWriter(server.getOutputStream());
				while(true) {
				//	메세지 입력받음
					System.out.println("메세지 입력 >");
					String msg = sc.nextLine();
				//	exit 입력시 작업 종료
					if(msg.equals("exit")) break;
				//	서버에 전송
					pw.println(msg);
					pw.flush();
				//	서버가 보낸 내용을 받아서 출력
					msg = br.readLine();
				//  서버가 보낸 내용이 null 이면 역시 작업 종료
					if(msg == null) break;
					System.out.println(msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			//close 
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
