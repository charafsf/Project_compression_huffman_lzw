package project.huffman;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import project.common.CommonUtils;
import project.common.CommonUtilsStringBuilder;
import project.common.InformationFile;
import project.common.MyByte;
import project.common.Pair;

public class HuffmanImpl implements Huffman{
	private static Logger logger = Logger.getLogger(HuffmanImpl.class);
	
	@Override
	public Pair<File,File> encode(File file) {
		InformationFile infoFile = new InformationFile(file);
		String pathOfEncoded = infoFile.getPath()+infoFile.getName()+"Encoded"+infoFile.getExtension();
		String pathOfTree = infoFile.getPath()+infoFile.getName()+"Tree"+infoFile.getExtension();
		File encoded = new File(pathOfEncoded);
		File tree = new File(pathOfTree);
		//
		byte[] bytes = CommonUtils.fileToByte(file);
		Map<Byte, Integer> occuOfBytes = CommonUtils.occuOfByte(bytes);
		BinaryNode root = CommonUtils.buildHuffmanTree(occuOfBytes);
		String encodedString = "";
		try {
			encodedString = CommonUtilsStringBuilder.compressFromBinaryNode(bytes, root);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String huffmanBinaryTree = CommonUtils.writeBinaryHuffmanTree(root);
		Pair<Integer,String> informationForEncoded  = CommonUtils.addInformationToEncoded(encodedString);
		Pair<Integer, String> informationForTree = CommonUtils.addInformationToEncoded(huffmanBinaryTree);
		// last bit 1 for huffman
		// 0 for LWZ
		// index [1 - 4] number of added 0 to the encoded file
		// index [5 - 7] number of added 0 to the Tree file
		// index 8 = '0'
		String byteForInformation = "1"+informationForEncoded.getValue() + informationForTree.getValue() + "0";
		
		String newEncoded = byteForInformation + encodedString + CommonUtils.repeat("0",informationForEncoded.getKey());
		String newHuffManBinaryTree = huffmanBinaryTree + CommonUtils.repeat("0",informationForTree.getKey());
		byte[] newEncodedByte = CommonUtilsStringBuilder.binaryAsStringTobyte(newEncoded);
		byte[] newEncodedTree = CommonUtilsStringBuilder.binaryAsStringTobyte(newHuffManBinaryTree);
		
		CommonUtils.writeByteToFile(newEncodedByte, encoded);
		CommonUtils.writeByteToFile(newEncodedTree, tree);
		logger.info("your encoded file path");
		logger.info(pathOfEncoded);
		logger.info("your tree file path to decompress");
		logger.info(pathOfTree);
		//
		return new Pair<File, File>(encoded, tree);
	}

	@Override
	public File decode(Pair<File, File> encodedTree) {
		InformationFile infoFile = new InformationFile(encodedTree.getKey());
		
		byte[] newEncodedByte = CommonUtils.fileToByte(encodedTree.getKey());
		byte[] newEncodedTree = CommonUtils.fileToByte(encodedTree.getValue());
		String decompressed = CommonUtils.decompress(newEncodedByte, newEncodedTree);
		byte[] decoded = MyByte.binaryAsStringTobyte(decompressed);
		String fileName = "";
		if(infoFile.getName().contains("Encoded")) {
			fileName = infoFile.getName().replace("Encoded", "Decoded");
		}else {
			fileName = infoFile.getName() + "Decoded";
		}
		String decodedPath = infoFile.getPath()+fileName+infoFile.getExtension();
		File decodedFile = new File(decodedPath);
		CommonUtils.writeByteToFile(decoded, decodedFile);
		logger.info("your Dencoded file path");
		logger.info(decodedPath);
		return decodedFile;
	}
	
	public File decodeWithStringBuilder(Pair<File, File> encodedTree) {
		InformationFile infoFile = new InformationFile(encodedTree.getKey());
		
		byte[] newEncodedByte = CommonUtils.fileToByte(encodedTree.getKey());
		byte[] newEncodedTree = CommonUtils.fileToByte(encodedTree.getValue());
		String decompressed = CommonUtilsStringBuilder.decompress(newEncodedByte, newEncodedTree);
		byte[] decoded = CommonUtilsStringBuilder.binaryAsStringTobyte(decompressed);
		String fileName = "";
		if(infoFile.getName().contains("Encoded")) {
			fileName = infoFile.getName().replace("Encoded", "Decoded");
		}else {
			fileName = infoFile.getName() + "Decoded";
		}
		String decodedPath = infoFile.getPath()+fileName+infoFile.getExtension();
		File decodedFile = new File(decodedPath);
		CommonUtils.writeByteToFile(decoded, decodedFile);
		logger.info("your Dencoded file path");
		logger.info(decodedPath);
		return decodedFile;
	}
	/*
	//try todecode by 4096 byte
	//and write this decoded
	//i 'll add thread after
	public List<Byte> decodeSome(File file,int from){
		try(BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
		    byte[] someEncodedByte = new byte[4096];
		    int len;
		    while ((len = in.read(someEncodedByte)) != -1) {
		        // process data here: bbuf[0] thru bbuf[len - 1]
		    	byte[] newEcoded = Arrays.copyOfRange(someEncodedByte, 0, len);
		    	String someEncodedString = CommonUtilsStringBuilder.bytesToString(newEcoded); 
		    	
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public File enhanceDecode(Pair<File, File> encodedTree) {
		InformationFile infoFile = new InformationFile(encodedTree.getKey());
		
		//mauvais //lire by buffer
		byte[] newEncodedByte = CommonUtils.fileToByte(encodedTree.getKey());
		//bon
		byte[] newEncodedTree = CommonUtils.fileToByte(encodedTree.getValue());
		// write by buffer
		String decompressed = CommonUtilsStringBuilder.enhanceDecompress(newEncodedByte, newEncodedTree);
		byte[] decoded = CommonUtilsStringBuilder.binaryAsStringTobyte(decompressed);
		
		String fileName = "";
		if(infoFile.getName().contains("Encoded")) {
			fileName = infoFile.getName().replace("Encoded", "Decoded");
		}else {
			fileName = infoFile.getName() + "Decoded";
		}
		String decodedPath = infoFile.getPath()+fileName+infoFile.getExtension();
		File decodedFile = new File(decodedPath);
		CommonUtils.writeByteToFile(decoded, decodedFile);
		logger.info("your Dencoded file path");
		logger.info(decodedPath);
		return decodedFile;
	}*/
}
