package tes.project.huffman;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;

import org.junit.Test;

import project.common.CommonUtils;

public class commonUtilsTest {

	final static String FOLDER_PATH_COMMON_UTILS = "./src/test/resources/common/commonUtils/";

	@Test
	public void testFrequenceOfByte() {
		// abaabaac
		// a: 5/8
		// b: 2/8
		// c: 1/8
		int NUMBER_CHAR = 3;
		Double PRECISION = 0.02;
		String FILE_NAME = "frequenceOfByte.txt";
		File file = new File(FOLDER_PATH_COMMON_UTILS + FILE_NAME);
		byte[] bytes = CommonUtils.fileToByte(file);
		Map<Byte, Double> mapOfFrequency = CommonUtils.frequenceOfByte(bytes);

		assertEquals(mapOfFrequency.size(), NUMBER_CHAR, PRECISION);
		assertEquals(mapOfFrequency.get("a".getBytes()[0]), 5.0 / 8.0, PRECISION);
		assertEquals(mapOfFrequency.get("b".getBytes()[0]), 2.0 / 8.0, PRECISION);
		assertEquals(mapOfFrequency.get("c".getBytes()[0]), 1.0 / 8.0, PRECISION);
	}

	@Test
	public void testByteToString() {
		byte a = 97;
		String expected = "01100001";
		String actual = CommonUtils.byteToString(a);
		assertEquals(expected, actual);
	}

	@Test
	public void testBytesToString() {
		byte[] abcde = { 97, 98, 99, 100, 101 };
		String a = "01100001";
		String b = "01100010";
		String c = "01100011";
		String d = "01100100";
		String e = "01100101";
		String expected = a + b + c + d + e;
		String actual = CommonUtils.bytesToString(abcde);
		assertEquals(expected, actual);
	}

	@Test
	public void sameFile() {
		File file1 = new File(FOLDER_PATH_COMMON_UTILS + "test1.txt");
		File file2 = new File(FOLDER_PATH_COMMON_UTILS + "test1_copie.txt");
		assertTrue("same file", CommonUtils.compare2File(file1, file2));
	}

	@Test
	public void DifferentFile() {
		File file1 = new File(FOLDER_PATH_COMMON_UTILS + "test1.txt");
		File file2 = new File(FOLDER_PATH_COMMON_UTILS + "test2.txt");
		assertFalse("different file", CommonUtils.compare2File(file1, file2));
	}
}
