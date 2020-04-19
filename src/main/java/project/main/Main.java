package project.main;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import project.Lzw.LzwImpl;
import project.common.Pair;
import project.huffman.HuffmanImpl;

public class Main {

	private static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		BasicConfigurator.configure();
		Scanner scanner = new Scanner(System.in);
		System.out.println("----------Compression/Decompression-Programm----------");
		System.out.println("Enter Number for the following");
		System.out.println("1- Compression");
		System.out.println("2- Decompression");
		System.out.println("Compression will be choosed if other keyboard are pressed");
		int programm = scanner.nextInt();
		System.out.println("Choose an algorithm between th following (enter number)");
		System.out.println("1- Huffman compression/decompression algorithm");
		System.out.println("2- LZW compression/decompression algorithm");
		System.out.println("Huffman will be choosed if other keyboard are pressed");
		int choosed = scanner.nextInt();
		scanner.nextLine();
		if (choosed == 2) {
			LzwImpl lzw = new LzwImpl();
			if (programm == 2) {
				System.out.println("enter a full path of your compressed file");
				String encodedPath = scanner.nextLine();
				File input = new File(encodedPath);
				System.out.println("enter a full path of your tree to decompress file");
				String treePath = scanner.nextLine();
				File output = new File(treePath);
				try {
					lzw.decode(input, output);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("enter a full path of your file");
				String filePath = scanner.nextLine();
				File input = new File(filePath);
				System.out.println("enter a full path for an empty file");
				String outputPath = scanner.nextLine();
				File ouput = new File(outputPath);
				System.out.println("Compiling LZW ...");
				try {
					lzw.decode(input, ouput);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			HuffmanImpl huffmanImpl = new HuffmanImpl();
			if (programm == 2) {
				System.out.println("enter a full path of your compressed file");
				String encodedPath = scanner.nextLine();
				System.out.println("enter a full path of your tree to decompress file");
				String treePath = scanner.nextLine();
				File encodedFile = new File(encodedPath);
				File treeFile = new File(treePath);
				boolean existsEncoded = encodedFile.exists();
				boolean existsTree = treeFile.exists();
				if (!existsEncoded || !existsTree) {
					System.out.println("File not found");
				} else {
					long start = System.currentTimeMillis();
					//huffmanImpl.decode(new Pair<File, File>(encodedFile, treeFile));
					huffmanImpl.decodeWithStringBuilder(new Pair<File, File>(encodedFile, treeFile));
					long finish = System.currentTimeMillis();
					float timeElapsed = (finish - start) / 1000F;
					logger.info("Excution time (s): "+timeElapsed);
				}
			} else {
				System.out.println("enter a full path of your file");
				String filePath = scanner.nextLine();
				System.out.println("Compiling huffman ...");
				File file = new File(filePath);
				boolean exists = file.exists();
				if (!exists) {
					System.out.println("File not found");
				} else {
					huffmanImpl.encode(file);
				}
			}
		}
		System.out.println("---------Synthesis-----------");
	}
}
