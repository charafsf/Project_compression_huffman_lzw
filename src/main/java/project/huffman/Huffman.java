package project.huffman;

import java.io.File;

import project.common.Pair;

public interface Huffman {

	Pair<File,File> encode(File file);
	File decode(Pair<File,File> encodedTree);

}
