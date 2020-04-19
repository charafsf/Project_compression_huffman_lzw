package project.Lzw;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;


public class LzwImpl implements Lzw {

	public HashMap<String, Integer> Dictionnary = new HashMap<String, Integer>();
	public String[] tab_caractere;
	public int count;

	public void encode(File input, File output) throws IOException {

		tab_caractere = new String[4096];
		for (int i = 0; i < 256; i++) {
			Dictionnary.put(Character.toString((char) i), i);
			tab_caractere[i] = Character.toString((char) i);
		}
		count = 256;
		DataInputStream read = new DataInputStream(new BufferedInputStream(new FileInputStream(input)));
		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(output)));

		byte input_byte;
		String tmp = "";
		boolean bite_gauche = true;
		byte[] table = new byte[3];

		try {
			input_byte = read.readByte();
			int i = new Byte(input_byte).intValue();
			if (i < 0) {
				i += 256;
			}
			char c = (char) i;
			tmp = "" + c;

			while (true) {
				input_byte = read.readByte();
				i = new Byte(input_byte).intValue();

				if (i < 0) {
					i += 256;
				}
				c = (char) i;

				if (Dictionnary.containsKey(tmp + c)) {
					tmp = tmp + c;
				} else {
					String s12 = to12bit(Dictionnary.get(tmp));

					if (bite_gauche) {
						table[0] = (byte) Integer.parseInt(s12.substring(0, 8), 2);
						table[1] = (byte) Integer.parseInt(s12.substring(8, 12) + "0000", 2);
					} else {
						table[1] += (byte) Integer.parseInt(s12.substring(0, 4), 2);
						table[2] = (byte) Integer.parseInt(s12.substring(4, 12), 2);
						for (int b = 0; b < table.length; b++) {
							out.writeByte(table[b]);
							table[b] = 0;
						}
					}
					bite_gauche = !bite_gauche;

					if (count < 4096) {
						Dictionnary.put(tmp + c, count++);
					}
					tmp = "" + c;
				}

			}
		} catch (Exception e) {
			String to_12 = to12bit(Dictionnary.get(tmp));
			if (bite_gauche) {
				table[0] = (byte) Integer.parseInt(to_12.substring(0, 8), 2);
				table[1] = (byte) Integer.parseInt(to_12.substring(8, 12) + "0000", 2);
				out.writeByte(table[0]);
				out.writeByte(table[1]);
			} else {
				table[1] += (byte) Integer.parseInt(to_12.substring(0, 4), 2);
				table[2] = (byte) Integer.parseInt(to_12.substring(4, 12), 2);
				for (int b = 0; b < table.length; b++) {
					out.writeByte(table[b]);
					table[b] = 0;
				}
			}
			read.close();
			out.close();
		}
	}
	public String to12bit(Integer i) {
		String tp = Integer.toBinaryString(i);
		while (tp.length() < 12) {
			tp = "0" + tp;
		}
		return tp;
	}
	public int getval(byte bit1, byte bit2, boolean bite_gauche) {
		String conver1 =Integer.toBinaryString(bit1);
		String conver2 =Integer.toBinaryString(bit2);
		
		while (conver1.length() < 8) {
			conver1 = "0" + conver1;
		}
		if (conver1.length() == 32) {
			conver1 = conver1.substring(24, 32);
		}
		while (conver2.length() < 8) {
			conver2 = "0" + conver2;
		}
		if (conver2.length() == 32) {
			conver2 = conver2.substring(24, 32);
		}

		if (bite_gauche) {
			return Integer.parseInt(conver1 + conver2.substring(0, 4), 2);
		} else {
			return Integer.parseInt(conver1.substring(4, 8) + conver2, 2);
		}
	}
	public void decode(File input, File output) throws IOException {
		
			tab_caractere = new String[4096];
			for (int i = 0 ; i < 256; i++) {
				Dictionnary.put(Character.toString((char) i), i);
				tab_caractere[i] = Character.toString((char) i);
			}
			count = 256;
			DataInputStream read2 = new DataInputStream(new BufferedInputStream(new FileInputStream(input)));

			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(output)));
			
			int premiermot , motselect; 
			byte[] table = new byte[3];
			boolean bite_gauche = true; 
			
			try {
				table[0] = read2.readByte();
				table[1] = read2.readByte();

				premiermot = getval(table[0], table[1], bite_gauche);
				bite_gauche = !bite_gauche;
				out.writeBytes(tab_caractere[premiermot]);
			
				while (true) {
					if (bite_gauche) {
						table[0] = read2.readByte();
						table[1] = read2.readByte();
						motselect = getval(table[0], table[1], bite_gauche);
					} else {
						table[2] = read2.readByte();
						motselect = getval(table[1], table[2], bite_gauche);
					}
					bite_gauche = !bite_gauche;
					if (motselect >= count) {

						if (count < 4096)
							tab_caractere[count] = tab_caractere[premiermot]
									+ tab_caractere[premiermot].charAt(0);
						count++;
						out.writeBytes(tab_caractere[premiermot]
								+ tab_caractere[premiermot].charAt(0));
					} else {

						if (count < 4096)
							tab_caractere[count] = tab_caractere[premiermot]
									+ tab_caractere[motselect].charAt(0);
						count++;
						out.writeBytes(tab_caractere[motselect]);
					}
					premiermot = motselect;
				}
			}catch (EOFException e) {
				read2.close();
				out.close();
			}
			}
		

}
