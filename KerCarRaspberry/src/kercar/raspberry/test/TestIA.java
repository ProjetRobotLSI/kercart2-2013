package kercar.raspberry.test;

public class TestIA {
	
	public static void main(String args[]) {
		
		System.out.println(testCalculAngle(7, 4, 3, 1, 45));
		System.out.println(testCalculAngle(2, 4, 6, 1, 106));
		System.out.println(testCalculAngle(1, 1, 4, 2, 23));
		System.out.println(testCalculAngle(1, 4, 4, 1, 295));
		System.out.println(testCalculAngle(4, 5, 2, 1, 295));
		System.out.println(testCalculAngle(5, 0, 2, 1, 315));
	}
	
	public static int testCalculAngle(int pointLongitude, int pointLatitude, int gpsLongitude, int gpsLatitude, int compass) {
		
		//Calcul de a²
		double a = Math.pow(pointLongitude - gpsLongitude, 2) + Math.pow(pointLatitude - gpsLatitude, 2);
		System.out.println("a " + a);
		
		//Calcul de b²
		int YNord = gpsLatitude + 5;
		int XNord = gpsLongitude;
		double b = Math.pow(XNord - gpsLongitude, 2) + Math.pow(YNord - gpsLatitude, 2);
		System.out.println("c " + b);
		
		//Calcul de c²	
		double c = Math.pow(pointLongitude - XNord, 2) + Math.pow(pointLatitude - YNord, 2);
		System.out.println("b " + c);
		
		//Al kashi
		//TODO Attention si a +b  > c à voir si BOUMBOUM
		double angleBetaRadian =  Math.acos(Math.abs(a + b - c) / (2 * Math.sqrt(a) * Math.sqrt(b)));
		int angleBeta = (int) ((180 * angleBetaRadian) / Math.PI);
		System.out.println("angleBeta " + angleBeta);
		
		if(compass <= 180) {
			if(pointLongitude >= XNord)
				return compass + angleBeta;
			else if (angleBeta >= compass)
				return 360 - (angleBeta - compass);
			else
				return compass - angleBeta;
		} else {
			compass = 360 - compass;
			if(pointLongitude <= XNord)
				return 360 - (compass + angleBeta);
			else if (angleBeta >= compass)
				return angleBeta - compass;
			else
				return 360 - (compass - angleBeta);
		}
		
	}
}
