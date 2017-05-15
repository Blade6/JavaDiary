package sort;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/** 外部排序 */
/*
 * 程序说明：
 * 1. 调用sort函数进行排序
 * 2. 调用displayFile函数进行输出。
 * 1.1 sort里面，先是调用initializeSegments把原文件分成numberOfSegments段，每段都排序好
 * 1.2 然后把这numberOfSegments段写到f1里
 * 1.3 调用merge把这些段进行归并
 * 1.3a merge里先调用mergeOneStep
 * 1.3b 然后调用merge自身
 * 1.3c 如果分段数小于1，则说明所有归并完成。
 * 1.3a-1 mergeOneStep里先调用copyHalfToF2把f1里面前numberOfSegment2/2的分段写入f2
 * 1.3a-2 然后调用mergeSegments把f1的剩余内容和f2的内容进行归并，并写入f3
 * 1.3a-2.1 megeSegments里面调用了mergeTwoSegments合并两个分段
 * 1.3a-2.2 mergeSegments实际上是把f1的第i个分段和f2的第i个分段进行归并，然后写入f3，f1还有多余的内容，也写入f3
 * 1.31-2.3 这一步执行的结果就是把原来的numberOfSegments个排好序的分段变成了numberOfSegments/2个排好序的分段
 * 1.31-2.4 其中每个分段都是最初分段的两倍大小
 * 1.3b-1 调用merge自身归并numberOfSegments/2个分段，每个分段大小为segmentSize*2。
 * 1.3b-2 注意此处f1,f2,f3的身份发生了变化，和汉诺塔的递归实现有点相似。
 * 
 */

public class sortLargeFile {

	public static final int MAX_ARRAY_SIZE = 100000;
	public static final int BUFFER_SIZE = 100000;
	
	public static void main(String[] args) throws Exception {
		// Sort largedata.dat to sortedfile.dat
		sort("largedata.dat", "sortedfile.dat");
		
		// Display the first 100 numbers in the sorted file
		displayFile("sortedfile.dat");
	}
	
	/** Sort data in source file and into target file */
	public static void sort(String sourcefile, String targetfile) 
		throws Exception {
		// Implement Phase 1: Create initial segments
		int numberOfSegments = 
			initializeSegments(MAX_ARRAY_SIZE, sourcefile, "f1.dat");
		
		// Implement Phase 2: Merge segments recursively
		merge(numberOfSegments, MAX_ARRAY_SIZE,
			"f1.dat", "f2.dat", "f3.dat", targetfile);	
	}
	
	/** Sort original file into sorted segments */
	private static int initializeSegments
		(int segmentSize, String originalFile, String f1)
		throws Exception {
		int[] list = new int[segmentSize];
		DataInputStream input = new DataInputStream(
			new BufferedInputStream(new FileInputStream(originalFile)));
		DataOutputStream output = new DataOutputStream(
			new BufferedOutputStream(new FileOutputStream(f1)));
		
		int numberOfSegments = 0;
		while (input.available() > 0) {
			numberOfSegments++;
			int i = 0;
			for ( ; input.available() > 0 && i < segmentSize; i++) {
				list[i] = input.readInt();
			}
			
			// Sort an array list[0..i-1]
			Arrays.sort(list, 0, i);
			
			// Write the array to f1.dat
			for (int j = 0; j < i; j++) {
				output.writeInt(list[j]);
			}
		}
		
		input.close();
		output.close();
		
		return numberOfSegments;
	}
	
	private static void merge(int numberOfSegments, int segmentSize,
		String f1, String f2, String f3, String targetfile)
		throws Exception {
		if (numberOfSegments > 1) {
			mergeOneStep(numberOfSegments, segmentSize, f1, f2, f3);
			merge((numberOfSegments + 1) / 2, segmentSize * 2, f3, f1, f2, targetfile);
		} else {
			File sortedFile = new File(targetfile);
			if (sortedFile.exists()) sortedFile.delete();
			new File(f1).renameTo(sortedFile);
		}
	}
	
	private static void mergeOneStep(int numberOfSegments,
		int segmentSize, String f1, String f2, String f3)
		throws Exception {
		DataInputStream f1Input = new DataInputStream(
			new BufferedInputStream(new FileInputStream(f1), BUFFER_SIZE));
		DataOutputStream f2Output = new DataOutputStream(
			new BufferedOutputStream(new FileOutputStream(f2), BUFFER_SIZE));
		
		// Copy half number of segments from f1.dat to f2.dat
		copyHalfToF2(numberOfSegments, segmentSize, f1Input, f2Output);
		f2Output.close();
		
		// Merge remaining segments in f1 with segments in f2 into f3
		DataInputStream f2Input = new DataInputStream(
			new BufferedInputStream(new FileInputStream(f2), BUFFER_SIZE));
		DataOutputStream f3Output = new DataOutputStream(
			new BufferedOutputStream(new FileOutputStream(f3), BUFFER_SIZE));
		
		mergeSegments(numberOfSegments / 2, segmentSize, f1Input, f2Input, f3Output);
		
		f1Input.close();
		f2Input.close();
		f3Output.close();
	}
	
	/** Copy first half number of segments from f1.dat to f2.dat */
	private static void copyHalfToF2(int numberOfSegments,
		int segmentSize, DataInputStream f1, DataOutputStream f2)
		throws Exception {
		for (int i = 0; i < (numberOfSegments / 2) * segmentSize; i++) {
			f2.writeInt(f1.readInt());
		}
	}
	
	/** Merge all segments */
	private static void mergeSegments(int numberOfSegments,
		int segmentSize, DataInputStream f1, DataInputStream f2,
		DataOutputStream f3) throws Exception {
		for (int i = 0; i < numberOfSegments; i++) {
			mergeTwoSegments(segmentSize, f1, f2, f3);
		}
		
		while (f1.available() > 0) {
			f3.writeInt(f1.readInt());
		}
	}
	
	/** Merge two segments */
	private static void mergeTwoSegments(int segmentSize,
		DataInputStream f1, DataInputStream f2,
		DataOutputStream f3) throws Exception {
		int intFromF1 = f1.readInt();
		int intFromF2 = f2.readInt();
		int f1Count = 1;
		int f2Count = 1;
		
		while (true) {
			if (intFromF1 < intFromF2) {
				f3.writeInt(intFromF1);
				if (f1.available() == 0 || f1Count++ >= segmentSize) {
					f3.writeInt(intFromF2);
					break;
				} else {
					intFromF1 = f1.readInt();
				}
			} else {
				f3.writeInt(intFromF2);
				if (f2.available() == 0 || f2Count++ >= segmentSize) {
					f3.writeInt(intFromF1);
					break;
				} else {
					intFromF2 = f2.readInt();
				}
			}
		}
		
		while (f1.available() > 0 && f1Count++ < segmentSize) {
			f3.writeInt(f1.readInt());
		}
		
		while (f2.available() > 0 && f2Count++ < segmentSize) {
			f3.writeInt(f2.readInt());
		}
	}
	
	public static void displayFile(String filename) {
		try {
			DataInputStream input = 
				new DataInputStream(new FileInputStream(filename));
			for (int i = 0; i < 100; i++)
				System.out.print(input.readInt() + " ");
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
