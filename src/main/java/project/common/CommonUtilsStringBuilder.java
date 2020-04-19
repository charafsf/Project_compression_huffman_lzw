package project.common;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import project.huffman.BinaryNode;

public class CommonUtilsStringBuilder {

	private final static int BYTE_LENGTH = 8;
	private static Logger logger = Logger.getLogger(CommonUtilsStringBuilder.class);
	private static ScheduledExecutorService scheduler;

	/*
	 * write tmp file
	 */
	public static String compressFromBinaryNode(byte[] bytes, BinaryNode root) throws Exception {
		int i = 0;
		int loading = 0;
		StringBuilder result = new StringBuilder("");
		for (byte b : bytes) {
			int tmp = (i * 100) / bytes.length;
			if (tmp > loading) {
				loading = tmp;
				logger.info("Loading " + loading + "%");
			}
			Pair<Boolean, String> compressOneByte = CommonUtils.compressOneByte("", b, root);
			if (compressOneByte.getKey()) {
				result.append(compressOneByte.getValue());
			} else {
				throw new Exception("byte not found from Huffman tree");
			}
			i++;
		}
		return result.toString();
	}

	public static String[] stringsFromString(String s) {
		String[] result = s.split("(?<=\\G.{8})");
		result[result.length - 1] = checkString(result[result.length - 1]);
		return result;
	}

	public static byte[] binaryAsStringTobyte(String s) {
		String[] strings = stringsFromString(s);
		byte[] result = new byte[strings.length];
		for (int i = 0; i < strings.length; i++) {
			result[i] = MyByte.stringToByte(strings[i]);
		}
		return result;
	}

	private static String checkString(String s) {
		String result = s;
		for (int i = 0; i < BYTE_LENGTH - s.length(); i++) {
			result = "0" + s;
		}
		return result;
	}

	public static String bytesToString(byte[] bytes) {
		StringBuilder result = new StringBuilder();
		for (byte b : bytes) {
			result.append(CommonUtils.byteToString(b));
		}
		return result.toString();
	}
	
	/*
	public static String bytesToString(List<Byte> bytes) {
		StringBuilder result = new StringBuilder();
		for (byte b : bytes) {
			result.append(CommonUtils.byteToString(b));
		}
		return result.toString();
	}*/

	public static BinaryNode decompressFromBin(StringBuilder s, BinaryNode parent) {
		if (parent.isLeafNode()) {
			return parent;
		} else if (s.charAt(0) == '0') {
			return decompressFromBin(s.delete(0, 1), parent.getLeft());
		}
		return decompressFromBin(s.delete(0, 1), parent.getRight());
	}

	public static String decompress(byte[] encoded, byte[] tree) {
		String stringEncoded = bytesToString(encoded);
		String stringInfoByte = stringEncoded.substring(0, 8);
		String stringTree = bytesToString(tree);
		InformationByte informationByte = CommonUtils.extractInformationByte(stringInfoByte);
		StringBuilder justEncoded = new StringBuilder(
				stringEncoded.substring(8, stringEncoded.length() - informationByte.getAddedInfoEncoded()));
		String justTree = stringTree.substring(0, stringTree.length() - informationByte.getAddedInfoTree());
		BinaryNode root = CommonUtils.constructHuffmanTreeFromBinary(justTree);
		StringBuilder result = new StringBuilder();
		//
		int i = 0;
		int loading = 0;
		int max = justEncoded.length();
		//
		scheduler = Executors.newScheduledThreadPool(1);
		while (justEncoded.length() > 0) {
			int tmp = (max - justEncoded.length()) * 100 / max;
			if (tmp > loading) {
				loading = tmp;
				logger.info("Loading " + loading + "%");
			}
			BinaryNode leaf = decompressFromBin(justEncoded, root);
			i++;
			logger.info(i);
			scheduler.schedule(getFuture(result, leaf.getVal(),i), 0, TimeUnit.NANOSECONDS);
			// result.append(CommonUtils.byteToString(leafLeft.getKey().getVal()));
		}
		shutDownScheduler(scheduler);
		return result.toString();
	}

	public static void shutDownScheduler(ScheduledExecutorService scheduler) {
		scheduler.shutdown();
		try {
			scheduler.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/*
	 * Pair List<Byte> : String reste
	public static String enhanceDecompressFromBin(String encoded, String left, List<Byte> bytes, BinaryNode parent) {

		if (parent.isLeafNode()) {
			bytes.add(parent.getVal());
			return encoded;
		} else if (encoded.length() == 0) {
			return left;
		} else if (encoded.charAt(0) == '0') {
			return enhanceDecompressFromBin(encoded.substring(1, encoded.length()), left, bytes, parent.getLeft());
		}
		return enhanceDecompressFromBin(encoded.substring(1, encoded.length()), left, bytes, parent.getRight());
	}

	/*
	public static String enhanceDecompress(byte[] encoded, byte[] tree) {
		String stringEncoded = bytesToString(encoded);
		String stringInfoByte = stringEncoded.substring(0, 8);
		String stringTree = bytesToString(tree);
		InformationByte informationByte = CommonUtils.extractInformationByte(stringInfoByte);
		String justEncoded = stringEncoded.substring(8, stringEncoded.length() - informationByte.getAddedInfoEncoded());
		String[] justEncodedArr = justEncoded.split("(?<=\\G.{" + tree.length + "})");
		String justTree = stringTree.substring(0, stringTree.length() - informationByte.getAddedInfoTree());
		BinaryNode root = CommonUtils.constructHuffmanTreeFromBinary(justTree);
		
		//
		List<Byte> bytes = new ArrayList<Byte>();
		for (int i = 0; i < justEncodedArr.length; i++) {
			logger.info(i);
			String left = enhanceDecompressFromBin(justEncodedArr[i], justEncodedArr[i], bytes, root);
			if (left.length() > 0) {
				justEncodedArr[i + 1] = left + justEncodedArr[i + 1];
			}
		}
		String result = bytesToString(bytes);
		return result;
	}*/

	public static Runnable getFuture(StringBuilder builder, byte encodedByte,int i) {
		return new Runnable() {
			public void run() {
				logger.info(i);
				builder.append(CommonUtils.byteToString(encodedByte));
			}
		};
	}

}
