package lowlevel;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPFileClient {

	public static void main(String[] args) {
		try{
			//데이터를 보내기 위한 UDP소켓 생성
			DatagramSocket socket = new DatagramSocket();
			//보낼 데이터 생성
			String msg = "9.png";
			//보내기 위한 패킷을 생성
			DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.getBytes().length
					,InetAddress.getByName("192.168.0.15"),7777);
			System.out.println("파일 이름 전송 성공");
			//보내기 
			socket.send(dp);
			
			//보내기 위한 파일의스트림을 생성해서 파일의 내용을 배열에 저장하기
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream("/Users/jihyunsub/Documents/9.png"));
			
			//한번에 읽어서 저장
			int len = bis.available();
			byte[] b = new byte[len];
			bis.read(b);
			
			dp = new DatagramPacket(b, len, InetAddress.getByName("192.168.0.15"),7777);
			//데이터 보내기
			socket.send(dp);
			System.out.printf("파일 전송 성공\n");
			/*
			//나눠서 읽기
			while(true)
			{
				byte[] b1 = new byte[128];
				int r = bis.read(b1);
				if(r<0)
				{
					break;
				}
				//배열에 b의 0부터 r까지 추가
			}
			*/
			
			//닫기
			bis.close();
			socket.close();
		}
		catch(Exception e)
		{
			System.out.printf("%s\n", e.getMessage());
			e.printStackTrace();
		}

	}

}
