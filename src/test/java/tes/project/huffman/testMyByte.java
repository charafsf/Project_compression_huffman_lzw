package tes.project.huffman;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import project.common.MyByte;

public class testMyByte {

	final static String FOLDER_PATH_MY_BYTE = "./src/test/resources/common/MyByte/";

	@Test
	public void testStringToByte() {
		String s = "abcde";
		List<String> sByte = Arrays.asList("01100001", "01100010", "01100011", "01100100", "01100101");
		byte[] bytes = s.getBytes();
		assertEquals(bytes[0], MyByte.stringToByte(sByte.get(0)));
		assertEquals(bytes[1], MyByte.stringToByte(sByte.get(1)));
		assertEquals(bytes[2], MyByte.stringToByte(sByte.get(2)));
		assertEquals(bytes[3], MyByte.stringToByte(sByte.get(3)));
		assertEquals(bytes[4], MyByte.stringToByte(sByte.get(4)));
	}

	@Test
	public void testBinaryAsStringTobyte() {
		String a = "01100001";
		String b = "01100010";
		String c = "01100011";
		String d = "01100100";
		String e = "01100101";
		String binaire = a + b + c + d + e;
		byte[] bytes = MyByte.binaryAsStringTobyte(binaire);

		assertEquals(bytes.length, 5);
		assertEquals(bytes[0], "a".getBytes()[0]);
		assertEquals(bytes[1], "b".getBytes()[0]);
		assertEquals(bytes[2], "c".getBytes()[0]);
		assertEquals(bytes[3], "d".getBytes()[0]);
		assertEquals(bytes[4], "e".getBytes()[0]);
	}

}
