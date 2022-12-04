package machine;

public class MemoryMode {
	int value;
	String name;
	char code;
	byte base;
	
	public static final MemoryMode BINARY=new MemoryMode(0,"binary",'b',(byte)16);
	public static final MemoryMode DECIMAL=new MemoryMode(1,"decimal",'d',(byte)10);
	
	// this is a duoton class ;-)
	private MemoryMode(int value, String name, char code, byte base) {
		this.value=value;
		this.name=name;
		this.code=code;
		this.base=base;
	}
	
	public byte getBase() {
		return base;
	}
	
}