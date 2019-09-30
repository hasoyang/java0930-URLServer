package urldownload;

import java.io.BufferedInputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLFileDownload {

	public static void main(String[] args) {
		try
		{
			//다운로드 받을 URL을 생성
			String addr = "http://www.topstarnews.net/news/photo/201901/572180_246763_4411.jpg";
			URL url = new URL(addr);
			
			//마지막 /뒤의 문자열 분할
			int idx = addr.lastIndexOf("/");
			String filename = addr.substring(idx+1);
			
			//URL과 연결하는 Connection을 생성
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			//옵션 설정
			con.setConnectTimeout(30000);
			con.setUseCaches(true);//캐싱된 데이터가 있으면 그 데이터를 사용
			
			//응답 코드를 확인해서 정상 응답을 했다면
			if(con.getResponseCode()>=200 &&con.getResponseCode()<=300)
			{
				//문자가 아니므로 바이트단위로 읽을 수 있는 스트림을 생성
				BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
				//파일에 기록할 수 있는 스트림을 생성
				PrintStream ps = new PrintStream(filename);
				
				while(true)
				{
					byte[] b = new byte[128];
					//b만큼 읽어서 b에 저장하고 그 읽은 개수를 리턴해서 r에 저장
					int r = bis.read(b);
					//읽은 개수가 없다면 - 전체를 다 읽은 후 라면 그만 읽기
					if(r <0)
					{
						break;
					}
					//읽은 데이터를 이용(b배열의 0부터 r만큼)
					ps.write(b, 0, r);
				}
				ps.flush();
				//전부 읽었으므로 종료
				bis.close();
				con.disconnect();
				ps.close();
			}
		}
		catch(Exception e)
		{
			System.out.printf("%s\n", e.getMessage());
			e.printStackTrace();
		}

	}

}
