package ftp;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

// 运行此程序首先应先开启xampp，在服务器上放一个名为forFTP.txt的文件，然后再去下载该文件。
public class downloadRemoteFile {

	private void download(String address, String saveName) {
		Scanner input = null;
		URL url = null;
		PrintWriter pw = null;
		
		try {
			url = new URL(address);
			input = new Scanner(url.openStream());
			pw = new PrintWriter(new File(saveName));
			
			while (input.hasNext()) {
				pw.print(input.nextLine() + "\n");
			}
			
			System.out.println("File download successfully!");
		} catch (MalformedURLException e) {
			System.out.println(url + " not found!");
		} catch (IOException e) {
			System.out.println("IO Error!");
		} finally {
			if (input != null) input.close();
			if (pw != null) pw.close();
		}
	}
	
	public static void main(String[] args) {
		downloadRemoteFile demo = new downloadRemoteFile();
		demo.download("http://192.168.201.80:8080/forFTP.txt", "./resources/hello.txt");
	}

}
