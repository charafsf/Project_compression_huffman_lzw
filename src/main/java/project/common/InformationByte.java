package project.common;

public class InformationByte {
	private boolean isHuffman;
	private int addedInfoEncoded;
	private int addedInfoTree;
	
	public InformationByte(boolean isHuffman, int addedInfoEncoded, int addedInfoTree) {
		this.isHuffman = isHuffman;
		this.addedInfoEncoded = addedInfoEncoded;
		this.addedInfoTree = addedInfoTree;
	}
	
	public InformationByte(char isHuffman, String addedInfoEncoded, String addedInfoTree) {
		setHuffman(isHuffman);
		setAddedInfoEncoded(addedInfoEncoded);
		setAddedInfoTree(addedInfoTree);
	}
	
	private int binarayAsStringToInt(String binaryAsString) {
		int result = 0;
		if(binaryAsString.length() == 3) {
			int division = 4;
			for(int i=0; i< 3; i++) {
				result = result + division * Character.getNumericValue(binaryAsString.charAt(i));
				division = division/2;
			}
		}else {
			//throw exception
		}
		return result;
	}
	private void setAddedInfoTree(String addedInfoTree) {
		this.addedInfoTree = this.binarayAsStringToInt(addedInfoTree);
	}

	private void setAddedInfoEncoded(String addedInfoEncoded) {
		this.addedInfoEncoded = this.binarayAsStringToInt(addedInfoEncoded);
	}

	public boolean isHuffman() {
		return isHuffman;
	}
	public void setHuffman(boolean isHuffman) {
		this.isHuffman = isHuffman;
	}
	public void setHuffman(char bit) {
		isHuffman = true;
		if(bit == '0') {
			isHuffman = false;
		}
	}
	public int getAddedInfoEncoded() {
		return addedInfoEncoded;
	}
	public void setAddedInfoEncoded(int addedInfoEncoded) {
		this.addedInfoEncoded = addedInfoEncoded;
	}
	public int getAddedInfoTree() {
		return addedInfoTree;
	}
	public void setAddedInfoTree(int addedInfoTree) {
		this.addedInfoTree = addedInfoTree;
	}
	
	public String intLTSevenToString(int number) {
		byte addByte = (byte) number;
		return CommonUtils.byteToString(addByte).substring(5);
	}
	
	/*
	@Override
	public String toString() {
		String result = "";
		if(isHuffman) {
			result += "1";
		}else {
			result += "0";
		}
		return result + intLTSevenToString(addedInfoEncoded)
		+intLTSevenToString(addedInfoTree)+"0";
	}
	*/
}
