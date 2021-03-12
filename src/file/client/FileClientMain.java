package file.client;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class FileClientMain {

	public static void main(String[] args) {
		String fileName = "info.pdf";
		Socket server = null;
		FileInputStream fis = null;
		DataOutputStream dos = null;
		
		try {
			//파일서버 접속
			server = new Socket("127.0.0.1",1234);
			//스트림 초기화
			fis = new FileInputStream(fileName);//파일 클래스
			dos = new DataOutputStream(server.getOutputStream());
			
			//fileName send
			dos.writeUTF(fileName);
			//file upload
			//buffer
			byte[] buf = new byte[1024];
			int total = 0;
			while(true) {
				int count = fis.read(buf);
				if(count == -1) break;
				dos.write(buf,0,count);
				dos.flush();
				total += count;//전송한 바이트 수 누적
			}
			System.out.println("파일 전송완료");
			System.out.println(total + " byte 전송");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(dos!=null)dos.close();
				if(fis!=null)fis.close();
				if(server!=null)server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}








