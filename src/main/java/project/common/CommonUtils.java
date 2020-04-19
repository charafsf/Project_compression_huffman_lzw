package project.common;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import project.huffman.BinaryNode;
import project.huffman.HuffmanImpl;

public class CommonUtils {
	private static Logger logger = Logger.getLogger(HuffmanImpl.class);
	/*
	public static boolean compare2FileFromPath(String filePath1, String filePath2) {
		BufferedReader reader1 = null;
		BufferedReader reader2 = null;

		try {
			reader1 = new BufferedReader(new FileReader(filePath1));
			reader2 = new BufferedReader(new FileReader(filePath2));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String line1 = null;
		String line2 = null;

		try {
			line1 = reader1.readLine();
			line2 = reader2.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (line1 != null || line2 != null) {
			if (line1 == null || line2 == null) {
				return false;
			} else if (!line1.equalsIgnoreCase(line2)) {
				return false;
			}

			try {
				line1 = reader1.readLine();
				line2 = reader2.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			reader1.close();
			reader2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	*/
	public static boolean compare2File(File file1, File file2) {
		FileInputStream fis1 = null;
		BufferedInputStream bis1 = null;
		DataInputStream dis1 = null;

		FileInputStream fis2 = null;
		BufferedInputStream bis2 = null;
		DataInputStream dis2 = null;

		try {
			fis1 = new FileInputStream(file1);
			bis1 = new BufferedInputStream(fis1);
			dis1 = new DataInputStream(bis1);
			fis2 = new FileInputStream(file2);
			bis2 = new BufferedInputStream(fis2);
			dis2 = new DataInputStream(bis2);

			while (dis1.available() != 0 || dis2.available() != 0) {
				if (dis1.available() == 0 || dis2.available() == 0) {
					return false;
				}
				Byte byte1 = dis1.readByte();
				Byte byte2 = dis2.readByte();
				if (Byte.compare(byte1, byte2) != 0) {
					return false;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis1.close();
				bis1.close();
				dis1.close();
				fis2.close();
				bis2.close();
				dis2.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return true;
	}
	
	public static boolean compare2File2(String file1, String file2) {
		FileInputStream fis1 = null;
		BufferedInputStream bis1 = null;
		DataInputStream dis1 = null;

		FileInputStream fis2 = null;
		BufferedInputStream bis2 = null;
		DataInputStream dis2 = null;

		try {
			fis1 = new FileInputStream(file1);
			bis1 = new BufferedInputStream(fis1);
			dis1 = new DataInputStream(bis1);
			fis2 = new FileInputStream(file2);
			bis2 = new BufferedInputStream(fis2);
			dis2 = new DataInputStream(bis2);

			while (dis1.available() != 0 || dis2.available() != 0) {
				if (dis1.available() == 0 || dis2.available() == 0) {
					return false;
				}
				Byte byte1 = dis1.readByte();
				Byte byte2 = dis2.readByte();
				if (Byte.compare(byte1, byte2) != 0) {
					return false;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis1.close();
				bis1.close();
				dis1.close();
				fis2.close();
				bis2.close();
				dis2.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return true;
	}

	public static Map<Byte, Double> frequenceOfByte(byte[] bytes) {
		Map<Byte, Double> result = new HashMap<Byte, Double>();
		double length = bytes.length;
		for (byte b : bytes) {
			if (result.get(b) == null) {
				result.put(b, 1.0 / length);
			} else {
				result.put(b, result.get(b) + (1.0 / length));
			}
		}
		return result;
	}

	public static Map<Byte, Integer> occuOfByte(byte[] bytes) {
		Map<Byte, Integer> result = new HashMap<Byte, Integer>();
		for (byte b : bytes) {
			if (result.get(b) == null) {
				result.put(b, 1);
			} else {
				result.put(b, result.get(b) + 1);
			}
		}
		return result;
	}

	public static byte[] fileToByte(File file) {
		int size = (int) file.length();
		byte[] bytes = new byte[size];
		try {
			BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
			buf.read(bytes, 0, bytes.length);
			buf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}

	/**
	 * te byte in the map should be convertible to String
	public static void printMap(Map<Byte, Double> map) {
		for (Entry<Byte, Double> entry : map.entrySet()) {
			byte[] bytes = new byte[1];
			bytes[0] = entry.getKey();
			String s = "";
			try {
				s = new String(bytes, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			System.out.println("Key : " + s + " Value : " + entry.getValue());
		}
	}

	public static String toBinaryString(byte n) {
		StringBuilder sb = new StringBuilder("00000000");
		for (int bit = 0; bit < 8; bit++) {
			if (((n >> bit) & 1) > 0) {
				sb.setCharAt(7 - bit, '1');
			}
		}
		return sb.toString();
	}
	*/

	public static String byteToString(byte b) {
		return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
	}
	
	public static String bytesToString(byte[] bytes) {
		String result = "";
		for (byte b : bytes) {
			result += byteToString(b);
		}
		return result;
	}

	public static void writeByteToFile(byte[] bytes, File file) {
		try {
			FileUtils.writeByteArrayToFile(file, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	public static Map<Integer, Byte> changeKeyToValue(Map<Byte, Integer> map) {
		Map<Integer, Byte> result = new HashMap<Integer, Byte>();
		for (Entry<Byte, Integer> entry : map.entrySet()) {
			Byte cle = entry.getKey();
			Integer valeur = entry.getValue();
			result.put(valeur, cle);
		}
		return result;
	}
	*/
	public static List<BinaryNode> listOfNodeFromMap(Map<Byte, Integer> map) {
		List<BinaryNode> result = new ArrayList<BinaryNode>();
		for (Entry<Byte, Integer> entry : map.entrySet()) {
			Byte cle = entry.getKey();
			Integer valeur = entry.getValue();
			result.add(new BinaryNode(cle, valeur));
		}
		return result;
	}

	public static List<BinaryNode> sortBinaryNodes(List<BinaryNode> unSortedList) {
		return unSortedList
				.stream()
				.sorted(Comparator.comparingInt(BinaryNode::getFreq))
				.collect(Collectors.toList());
	}

	public static BinaryNode buildHuffmanTree(Map<Byte, Integer> occuOfByte) {
		List<BinaryNode> listOfNodeFromMap = listOfNodeFromMap(occuOfByte);
		List<BinaryNode> sortedListOfNode = sortBinaryNodes(listOfNodeFromMap);
		BinaryNode root = null;
		if(sortedListOfNode.size() == 1) {
			root = sortedListOfNode.get(0);
		}
		while (sortedListOfNode.size() > 1) {
			sortedListOfNode = sortBinaryNodes(sortedListOfNode);
			BinaryNode minFirstNode = sortedListOfNode.get(0);
			BinaryNode minSecondeNode = sortedListOfNode.get(1);
			BinaryNode newNode = new BinaryNode(null, minFirstNode.getFreq()+minSecondeNode.getFreq());
			newNode.setLeft(minFirstNode);
			newNode.setRight(minSecondeNode);
			root = newNode;
			sortedListOfNode.remove(minFirstNode);
			sortedListOfNode.remove(minSecondeNode);
			sortedListOfNode.add(newNode);
		}
		return root;
	}
	
	public static Pair<Boolean, String> compressOneByte(String prefix,Byte b, BinaryNode root) {
		if(root.getVal() != null && Byte.compare((byte) root.getVal(), b) == 0) {
			return new Pair<Boolean, String>(true, prefix);
		}else if(root.getVal() == null){
			Pair<Boolean, String> left = compressOneByte(prefix+"0",b, root.getLeft());
			Pair<Boolean, String> right = compressOneByte(prefix+"1",b, root.getRight());
			if(left.getKey()) {
				return left;
			}else if(right.getKey()){
				return right;
			}
		}
		return new Pair<Boolean, String>(false, prefix);
	}
	/*
	public static String compressFromBinaryNode(byte[] bytes,BinaryNode root) throws Exception {
		int i = 0;
		int loading = 0;
		String result = "";
		for (byte b: bytes) {
			int tmp = (i*100)/bytes.length;
			if(tmp > loading) {
				loading = tmp;
				logger.info("Loading "+loading+"%");
			}
			Pair<Boolean, String> compressOneByte = compressOneByte("",b, root);
			if(compressOneByte.getKey()) {
				result += compressOneByte.getValue();
			}else {
				throw new Exception("byte not found from Huffman tree");
			}
			i++;
		}
		return result;
	}
	*/
	
	
	public static String writeBinaryHuffmanTree(BinaryNode root) {
		if(root.getVal() != null) {
			return "1"+byteToString(root.getVal());
		}
		return "0"+writeBinaryHuffmanTree(root.getLeft())+writeBinaryHuffmanTree(root.getRight());
	}
	
	public static String constructHuffmanTreeFromBinaryRec(BinaryNode parent, String s) {
		String leftS = "";
		if(s.charAt(0) == '0') {
			BinaryNode newNode = new BinaryNode(null, 0);
			if(parent.getLeft() != null) {
				parent.setRight(newNode);
			}else {
				parent.setLeft(newNode);
			}
			leftS = constructHuffmanTreeFromBinaryRec(newNode, s.substring(1,s.length()));
			if(leftS.length()>0) {
				leftS = constructHuffmanTreeFromBinaryRec(newNode, leftS);
			}
		}else {
			if(parent.getLeft() != null) {
				String sByte = s.substring(1,8+1);
				byte bytes = MyByte.stringToByte(sByte);
				BinaryNode newNode = new BinaryNode(bytes, 0);
				parent.setRight(newNode);
			}else {
				String sByte = s.substring(1,8+1);
				byte bytes = MyByte.stringToByte(sByte);
				BinaryNode newNode = new BinaryNode(bytes, 0);
				parent.setLeft(newNode);
			}
			if(s.length()>9) {
				leftS = s.substring(9,s.length());
			}
		}
		return leftS;
	}
	
	public static BinaryNode constructHuffmanTreeFromBinary(String s) {
		BinaryNode root;
		if(s.charAt(0) == '0') {
			root = new BinaryNode(null, 0);
			String leftS = constructHuffmanTreeFromBinaryRec(root,s.substring(1,s.length()));
			if(leftS.length()>0) {
				constructHuffmanTreeFromBinaryRec(root,leftS);
			}
		}else {
			String sByte = s.substring(1,8+1);
			byte bytes = MyByte.stringToByte(sByte);
			root = new BinaryNode(bytes, 0);
		}
		return root;
	}
	
	public static Pair<BinaryNode, String> decompressFromBin(String s, BinaryNode parent) {
		if(parent.isLeafNode()) {
			return new Pair<BinaryNode, String>(parent, s);
		}else if(s.charAt(0) == '0') {
			return decompressFromBin(s.substring(1,s.length()), parent.getLeft());
		}
		return decompressFromBin(s.substring(1,s.length()), parent.getRight());
	}
	
	public static InformationByte extractInformationByte(String s) {
		return new InformationByte(s.charAt(0), s.substring(1, 4), s.substring(4,7));
	}

	public static String decompress(byte[] encoded, byte[] tree) {
		String stringEncoded = bytesToString(encoded);
		String stringInfoByte = stringEncoded.substring(0, 8);
		String stringTree = bytesToString(tree);
		InformationByte informationByte = extractInformationByte(stringInfoByte);
		String justEncoded = stringEncoded.substring(8, stringEncoded.length() - informationByte.getAddedInfoEncoded());
		String justTree = stringTree.substring(0, stringTree.length()-informationByte.getAddedInfoTree());
		BinaryNode root = constructHuffmanTreeFromBinary(justTree);
		String result = "";
		while(justEncoded.length()>0) {
			Pair<BinaryNode, String> leafLeft = decompressFromBin(justEncoded, root);
			justEncoded = leafLeft.getValue();
			result += byteToString(leafLeft.getKey().getVal());
		}
		return result;
	}
	
	public static String repeat(String s, int n) {
		String result = "";
		for(int i=0; i< n ; i++) {
			result+= s;
		}
		return result;
	}
	
	/**
	 * 
	 * @param encoded
	 * @return  ex: <3,011> the integer and his representation in binary as String
	 */
	public static Pair<Integer,String> addInformationToEncoded(String encoded) {
		int addInt = 8 - (encoded.length() % 8);
		byte addByte = (byte) addInt;
		String addString = byteToString(addByte).substring(5);
		return  new Pair<Integer, String>(addInt, addString);
	}

}
