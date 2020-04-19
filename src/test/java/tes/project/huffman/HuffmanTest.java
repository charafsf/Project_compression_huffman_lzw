package tes.project.huffman;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import project.common.CommonUtils;
import project.common.Pair;
import project.huffman.Huffman;
import project.huffman.HuffmanImpl;

public class HuffmanTest {

	final static String FOLDER_PATH_HUFFAN = "./src/test/resources/huffman/";

	@Test
	public void testHuffman() {
		Huffman huffman = new HuffmanImpl();
		File filetoEncode = new File(FOLDER_PATH_HUFFAN + "test1.txt");
		Pair<File, File> compressedFile = huffman.encode(filetoEncode);
		File decodedFile = huffman.decode(compressedFile);
		assertTrue("correct Alogorithme", CommonUtils.compare2File(filetoEncode, decodedFile));
	}

	@Test
	public void testHuffmanText() {
		Huffman huffman = new HuffmanImpl();
		File filetoEncode = new File(FOLDER_PATH_HUFFAN + "text.txt");
		Pair<File, File> compressedFile = huffman.encode(filetoEncode);
		File decodedFile = huffman.decode(compressedFile);
		assertTrue("correct Alogorithme", CommonUtils.compare2File(filetoEncode, decodedFile));
	}

	@Test
	public void testDecodeWithStringBuilderText() {
		HuffmanImpl huffman = new HuffmanImpl();
		File filetoEncode = new File(FOLDER_PATH_HUFFAN + "text.txt");
		Pair<File, File> compressedFile = huffman.encode(filetoEncode);
		File decodedFile = huffman.decodeWithStringBuilder(compressedFile);
		assertTrue("correct Alogorithme", CommonUtils.compare2File(filetoEncode, decodedFile));
	}

	@Test
	public void testDecodeWithStringBuilder() {
		HuffmanImpl huffman = new HuffmanImpl();
		File filetoEncode = new File(FOLDER_PATH_HUFFAN + "test1_stringbuilder.txt");
		Pair<File, File> compressedFile = huffman.encode(filetoEncode);
		File decodedFile = huffman.decodeWithStringBuilder(compressedFile);
		assertTrue("correct Alogorithme", CommonUtils.compare2File(filetoEncode, decodedFile));
	}
}