import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class FractionalPackingMethods {
	
	private static Random rand = new Random();

	public static int fractionalPacking(ArrayList<Integer> objectsSize, int sizeBox) {
		if (sizeBox != 0) {
			int totalSizeObjects = 0;
			for (Integer i : objectsSize)
				totalSizeObjects += i;

			return (totalSizeObjects % sizeBox) >= 1 ? totalSizeObjects / sizeBox + 1 : totalSizeObjects / sizeBox;
		}
		return -1;
	}

	public static ArrayList<Box> firstFitPacking(ArrayList<Integer> sizeObjects, int sizeBox) {
		ArrayList<Box> boxes = new ArrayList<Box>(sizeObjects.size());
		boolean place = false;
		for (Integer object : sizeObjects) {
			place = false;
			for (Box box : boxes) {
				if (object > sizeBox) {
					System.out.println("Cet objet est plus grand que la taille des boîtes : " + object);
					break;
				}
				if (box.sizeOk(object)) {
					box.addObject(object);
					place = true;
					break;
				}
			}
			if (!place) {
				Box b = new Box(sizeBox);
				b.addObject(object);
				boxes.add(b);
			}
		}
		return boxes;
	}

	public static ArrayList<Box> bestFitPacking(ArrayList<Integer> sizeObjects, int sizeBox) {
		ArrayList<Box> boxes = new ArrayList<Box>(sizeObjects.size());
		boxes.add(new Box(sizeBox));
		for (Integer object : sizeObjects) {
			if (object <= sizeBox) {
				int i = findEmptiestBox(boxes, object);
				if (i == -1) {
					Box b = new Box(sizeBox);
					b.addObject(object);
					boxes.add(b);
				} else {
					boxes.get(i).addObject(object);
				}
			}

		}
		return boxes;
	}

	public static int findEmptiestBox(ArrayList<Box> boxes, int object) {
		int indiceBox = 0;
		for (int i = 0; i < boxes.size(); i++) {
			if (boxes.get(i).spaceLeft() > boxes.get(indiceBox).spaceLeft()) {
				indiceBox = i;
			}
		}
		// return -1 if no box can contains the object
		if (boxes.get(indiceBox).spaceLeft() < object)
			return -1;
		return indiceBox;
	}
	
	public static ArrayList<Box> firstFitDecreasingPacking(ArrayList<Integer> sizeObjects, int sizeBox){
		Collections.sort(sizeObjects);
		Collections.sort(sizeObjects, Collections.reverseOrder());
		return firstFitPacking(sizeObjects, sizeBox);
	}
	
	public static ArrayList<Box> bestFitDecreasingPacking (ArrayList<Integer> sizeObjects, int sizeBox){
		Collections.sort(sizeObjects);
		Collections.sort(sizeObjects, Collections.reverseOrder());
		return bestFitPacking(sizeObjects, sizeBox);
	}

	public static String listOfBoxesToString(ArrayList<Box> boxes) {
		StringBuilder s = new StringBuilder();
		for (Box b : boxes) {
			s.append(b.boxToString() + "\n");
		}
		return s.toString();
	}

	public static void test() {
		ArrayList<Integer> objects;
		int sizeBox;
		
		for(int n=100; n<=1000; n += 100) {
			
			float moyFP = 0;
			float moyFFP = 0;
			float moyBFP = 0;
			float moyFFDP = 0;
			float moyBFDP = 0;
			
			for(int i=0; i<20; i++) {
				
				sizeBox = (int) (1.5*n);
				objects = initializeObjects(n);
				
				moyFP += fractionalPacking(objects, sizeBox);
				moyFFP += firstFitPacking(objects, sizeBox).size();
				moyBFP += bestFitPacking(objects, sizeBox).size();
				moyFFDP += firstFitDecreasingPacking(objects, sizeBox).size();
				moyBFDP += bestFitDecreasingPacking(objects, sizeBox).size();
				
			}
			display(n, moyFP, moyFFP, moyBFP, moyFFDP, moyBFDP);
		}
	}
	
	public static ArrayList<Integer> initializeObjects(int n){
		ArrayList<Integer> objects = new ArrayList<>(n);
		for(int i=0; i<n; i++) {
			float r = (float) (0.6*rand.nextFloat() + 0.2);
			objects.add((int) (r*n));
		}
		return objects;
	}
	
	public static void display(int n, float moyFP, float moyFFP, float moyBFP, float moyFFDP, float moyBFDP) {
		System.out.println("N = " + n);
		System.out.println("fractionalPacking : " + moyFP/20);
		System.out.println("firstFitPacking : " + moyFFP/20);
		System.out.println("bestFitPacking : " + moyBFP/20);
		System.out.println("firstFitDecreasingPacking : " + moyFFDP/20);
		System.out.println("bestFitDecreasingPacking : " + moyBFDP/20);
	}
	
	public static void main(String[] args) {
		test();
	}
}
