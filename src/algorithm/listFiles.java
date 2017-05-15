package algorithm;

import java.io.File;

// 打印文件目录列表
public class listFiles {

	public void printSpace(int n) {
		for (int i = 0; i < 4 * n; i++)
			System.out.print(" ");
	}
	
	public void print(File file, int depth) {
		printSpace(depth);
		System.out.println(file.getName());
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				print(files[i], depth + 1);
			}
		}
	}
	
	
	public static void main(String[] args) {
		listFiles demo = new listFiles();
		demo.print(new File("D:\\java\\Demo"), 0);
	}

}
