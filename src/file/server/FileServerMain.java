package file.server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServerMain {

	public static void main(String[] args) {
		ServerSocket server = null;
		DataInputStream dis = null;
		FileOutputStream fos = null;
		
		try {
			server = new ServerSocket(1234);
			Socket client = server.accept();
			dis = new DataInputStream(client.getInputStream());
			//파일명을 읽기
			String fileName = dis.readUTF();
			String parentPath = client.getInetAddress().toString().replace("/", "");
			//출력할 파일 경로로 fos 셋팅
			File file = new File("c:\\"+parentPath+"\\" + fileName);
			if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
			fos = new FileOutputStream(file);
			byte[] buf = new byte[1024]; 
			while(true) {
				int count = dis.read(buf);
				if(count == -1) break;
				fos.write(buf,0,count);
			}
			System.out.println("파일 다운로드 완료");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(fos!=null)fos.close();
				if(dis!=null)dis.close();
				if(server!=null)server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
