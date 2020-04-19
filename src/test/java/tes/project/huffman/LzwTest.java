package tes.project.huffman;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import project.Lzw.Lzw;
import project.Lzw.LzwImpl;
import project.common.CommonUtils;

public class LzwTest {
	
	final static String FOLDER_PATH_LZW = "./src/test/resources/lzw/";
	
	@Test
	public void testLzw() throws IOException {
		Lzw lzw = new LzwImpl();
		String path1 =FOLDER_PATH_LZW+"test1.txt";
		File file1 = new File(path1);
		String path2 =FOLDER_PATH_LZW+"test2.txt";
		File file2 = new File(path2);
		lzw.encode(file1, file2);
		String path3=FOLDER_PATH_LZW+"test3.txt";
		File file3 = new File(path3);
		lzw.decode(file2, file3);
		assertTrue("correct Alogorithme", CommonUtils.compare2File2(path1, path3));
	}
}