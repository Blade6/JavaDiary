package sort;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

// 外部排序，处理超大文件，无法一次性读入内存的文件
// 此程序生成一个大文件，内含800004个整数。
public class CreateLargeFile {

	public static void main(String[] args) throws Exception {
		DataOutputStream output = new DataOutputStream(
			new BufferedOutputStream(
			new FileOutputStream("largedata.dat")));
		
		for (int i = 0; i < 800004; i++)
			output.writeInt((int)(Math.random() * 1000000));

		output.close();
		
		DataInputStream input = 
			new DataInputStream(new FileInputStream("largedata.dat"));
		for (int i = 0; i < 100; i++)
			System.out.print(input.readInt() + " ");
		
		input.close();
	}

}
