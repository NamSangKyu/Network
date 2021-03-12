package step2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiEchoServerMain {
	private static ArrayList<ServerWorker> list = new ArrayList<ServerWorker>();
	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			//서버 오픈
			server = new ServerSocket(1234);
			while(true) {
				//클라이언트 접속
				Socket client = server.accept();
				//스레드 생성
				ServerWorker sw = new ServerWorker(client);
				//스레드 실행
				sw.start();
				list.add(sw);
				System.out.println(list.size()+"명 클라이언트 접속 중입니다.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static class ServerWorker extends Thread {
		private Socket client;
		private BufferedReader br;
		private PrintWriter pw;

		public ServerWorker(Socket client) {
			super();
			this.client = client;
			try {
				br = new BufferedReader(new InputStreamReader(client.getInputStream()));
				pw = new PrintWriter(client.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			String msg;
			try {
				while (true) {
					msg = br.readLine();
					if (msg == null || msg.equals("exit"))
						break;
					pw.println(msg);
					pw.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					if(pw!=null)pw.close();
					if(br!=null)br.close();
					if(client!=null)client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				//현재 스레드를 리스트에서 제거
				list.remove(this);
				System.out.println(client.getInetAddress()+" 접속 종료 하셨습니다.");
				System.out.println(list.size()+"명 클라이언트 접속 중입니다.");
				
			}

		}

	}
}
