package types;

public class MyMain {
	public static void main(String[] args) {
		Filling[] vec = new Filling[7];
		vec[0] = Filling.LOVE;
		vec[1] = Filling.LOVE;
		
		for (int i = 2; i < vec.length; i++) {
			vec[i] = Filling.ANGEL;
		}
		
		Bottle bottle = new Bottle(vec);
		bottle.pourOut();
		bottle.pourOut();
		System.out.println(bottle);
		System.out.println();
		bottle.receive(Filling.LOVE);
		System.out.println();
		System.out.println(bottle);
	}
}
