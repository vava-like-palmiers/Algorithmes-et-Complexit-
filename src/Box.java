import java.util.ArrayList;

public class Box {

	private ArrayList<Integer> objects;
	private int sizeUsed;
	private int sizeBox;

	public Box(int s) {
		if (s != 0) {
			sizeBox = s;
			sizeUsed = 0;
			objects = new ArrayList<Integer>();
		}
	}

	public void addObject(int sizeObject) {
		if (sizeOk(sizeObject)) {
			objects.add(sizeObject);
			sizeUsed += sizeObject;
		}
	}

	public boolean sizeOk(int sizeObject) {
		return sizeObject + sizeUsed <= sizeBox;
	}
	
	public int spaceLeft() {
		return sizeBox - sizeUsed;
	}

	public int sizeBox() {
		return sizeBox;
	}

	public int sizeUsed() {
		return sizeUsed;
	}

	public String boxToString() {
		StringBuilder s = new StringBuilder();
		s.append("( " + sizeBox + ", [ ");
		if (objects != null) {
			for (int i=0; i<objects.size()-1; i++) {
				s.append(objects.get(i) + ", ");
			}
		}
		s.append(objects.get(objects.size()-1));
		s.append(" ] )");
		return s.toString();
	}
}
