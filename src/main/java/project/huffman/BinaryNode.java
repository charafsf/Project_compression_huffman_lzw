package project.huffman;
public class BinaryNode {
	
	private Byte val;
	private int freq=-1;
	private BinaryNode left;
	private BinaryNode right;
	
	public Byte getVal() {
		return val;
	}
	public void setVal(Byte val) {
		this.val = val;
	}
	public int getFreq() {
		return freq;
	}
	public void setFreq(Byte freq) {
		this.freq = freq;
	}
	public BinaryNode getLeft() {
		return left;
	}
	public void setLeft(BinaryNode left) {
		this.left = left;
	}
	public BinaryNode getRight() {
		return right;
	}
	public void setRight(BinaryNode right) {
		this.right = right;
	}
	
	public BinaryNode(Byte val, int freq){
		this.val = val;
		this.freq = freq;
		this.left = null;
		this.right = null;
	}
	
	public BinaryNode(Byte val, int freq, BinaryNode left, BinaryNode right){
		this.val = val;
		this.freq = freq;
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String toString() {
		return "{"+val+" ,"+freq+"}";
	}
	
	public boolean isLeafNode() {
		return val != null;
	}

}