package project.Lzw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface Lzw {

	void encode(File input, File output) throws FileNotFoundException, IOException;

	void decode(File input, File output) throws IOException ;
	

}
