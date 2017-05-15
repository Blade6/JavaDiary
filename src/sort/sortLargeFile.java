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

/** �ⲿ���� */
/*
 * ����˵����
 * 1. ����sort������������
 * 2. ����displayFile�������������
 * 1.1 sort���棬���ǵ���initializeSegments��ԭ�ļ��ֳ�numberOfSegments�Σ�ÿ�ζ������
 * 1.2 Ȼ�����numberOfSegments��д��f1��
 * 1.3 ����merge����Щ�ν��й鲢
 * 1.3a merge���ȵ���mergeOneStep
 * 1.3b Ȼ�����merge����
 * 1.3c ����ֶ���С��1����˵�����й鲢��ɡ�
 * 1.3a-1 mergeOneStep���ȵ���copyHalfToF2��f1����ǰnumberOfSegment2/2�ķֶ�д��f2
 * 1.3a-2 Ȼ�����mergeSegments��f1��ʣ�����ݺ�f2�����ݽ��й鲢����д��f3
 * 1.3a-2.1 megeSegments���������mergeTwoSegments�ϲ������ֶ�
 * 1.3a-2.2 mergeSegmentsʵ�����ǰ�f1�ĵ�i���ֶκ�f2�ĵ�i���ֶν��й鲢��Ȼ��д��f3��f1���ж�������ݣ�Ҳд��f3
 * 1.31-2.3 ��һ��ִ�еĽ�����ǰ�ԭ����numberOfSegments���ź���ķֶα����numberOfSegments/2���ź���ķֶ�
 * 1.31-2.4 ����ÿ���ֶζ�������ֶε�������С
 * 1.3b-1 ����merge����鲢numberOfSegments/2���ֶΣ�ÿ���ֶδ�СΪsegmentSize*2��
 * 1.3b-2 ע��˴�f1,f2,f3����ݷ����˱仯���ͺ�ŵ���ĵݹ�ʵ���е����ơ�
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
