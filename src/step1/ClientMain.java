package step1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMain {

	public static void main(String[] args) {
		Socket client = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			//서버 접속
			client = new Socket("127.0.0.1",1234);
			//스트림 초기화
			pw = new PrintWriter(client.getOutputStream());
			br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			//서버와 입출력
			pw.println("send message");
			pw.flush();
			String msg = br.readLine();
			System.out.println(msg);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			//close
			try {
				if(pw!=null)pw.close();
				if(br!=null)br.close();
				if(client!=null)client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
