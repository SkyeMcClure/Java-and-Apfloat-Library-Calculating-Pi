//import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.apfloat.ApfloatRuntimeException;

public class SkyePi {
	
	public static void main(String[] args) throws ApfloatRuntimeException, IOException {
		// Welcome to Skye's algorithm for calculating Pi!
		// Version 1.00
		
		// Let's set our initial variables.
			
			// User input option to be coded later
			// Scanner reader = new Scanner(System.in);
			// System.out.println("Enter a number: ");
			// int digits = reader.nextInt();
			// reader.close();
				
			// The value of precision (how many digits do we want).
			int digits = 100;
		
			// To get to the desired precision we need a few extra digits to work with.
			// I chose 7 to be sure the last digit we want is exact.
			int limit = digits+7;
			
			// Iteration amount - this algorithm converges at a rate of about 0.6, but not exactly.
			// So we account for how many approximate iterations we'll need.
			// We could divide the limit by 0.6 like I have here, or we could multiply it by 1.666666666...
			// Then we round to the nearest whole number.
			int x = ApfloatMath.ceil(new Apfloat(limit / 0.6d)).toBigInteger().intValue();
			
			// We need to keep a counter on which iteration we are currently on
			// The algorithm will stop before y = x
			// Important as it will be used for calculating Pi at the end via 2^count
			int count = 1;
			

			// I used this variable initially, as what we want is 0.707... or the square root of 0.5.. for some values
			// But calling ApfloatMath.sqrt on 0.5 gave incorrect results
			// Apfloat pointFive = new Apfloat(0.5, limit);

			
			// The numbers 1 and 2.  We use these for some calculations, so we'll call them here for multiple use
			Apfloat one = new Apfloat(1, limit);
			Apfloat two = new Apfloat(2, limit);
			

			// S: One of our initial variables we need to use, the square root of 0.5, or square root of 2 divided by 2
			// It is our side length of the polygon
			Apfloat s = ApfloatMath.sqrt( two).precision(limit).divide(two).precision(limit);

			// P: Another initial variable, which is always half of s
			// Used to calculate next iteration of C
			Apfloat p = s.divide(two).precision(limit);
			
			// C: Begins the same as S
			// Our diameter of the polygon/circle
			Apfloat c = s;
			
			// For our calculation we need to subtract c by 1
			Apfloat cminus = c.subtract(one).precision(limit);
			
			// And the calculation for grabbing our next point to use
			// Think of it like creating polygons of 2^x.. 4 sides, 8 sides, 16 sides, and so forth, around a circle
			// Our initial variables 0.707... * 2^2 = 2.828 (square root of 8)
			// The equation is √((c-1)^2 + s^2)
			Apfloat point = ApfloatMath.sqrt(ApfloatMath.sum(cminus.multiply(cminus), 
					s.multiply(s))).precision(limit);

			
			for (int y = 1; y < x; y= y + 1)
			{
				
				// Grabbing the previous iterative value of pi to compare with the second value later on
				// The equation is s * 2 * 2^count
				Apfloat pi = ApfloatMath.product(ApfloatMath.product(s, two), ApfloatMath.pow(two, count)).precision(limit);
				
				// We turn the value into a String to compare later on
				String piStringCompare = pi.toString().substring(0, digits+3);
				
				
				// Increase the count size by 1
				count++;
						
				// C subtract 1 again
				cminus = c.subtract(one).precision(limit);
				
				// Create our next point
				point = ApfloatMath.sqrt(ApfloatMath.sum(cminus.multiply(cminus), 
						s.multiply(s))).precision(limit);
				
				// S is point divided by 2
				s = point.divide(two).precision(limit);
				
				// C is P divided by S; perform before grabbing new P value
				c = p.divide(s).precision(limit);
				
				// P is always S divided by 2
				p = s.divide(two).precision(limit);
				
				// Our updated value of Pi after we performed the previous calculations
				// Again, s * 2 * 2^count
				pi = ApfloatMath.product(ApfloatMath.product(s, two), ApfloatMath.pow(two, count)).precision(limit);
				
				// Turn the new value into a String to compare
				String piString = pi.toString().substring(0, digits+3);
								
				// If our previous iteration equals our newest iteration to the digit amount selected, we break from the loop
				// We want to check past the value we want, to make sure it's run enough calculations to be correct
				if(piString.equals(piStringCompare)) break;
				
			}
			
			// The final polygon side count, which is 2^count
			Apfloat polygon = ApfloatMath.pow(two, count).precision(limit);
			// We calculate the last iteration of pi again to use
			Apfloat pi = ApfloatMath.product(ApfloatMath.product(s, two), ApfloatMath.pow(two, count)).precision(limit);
			// For comparison purposes, I've included Apfloat's pi variable
			Apfloat piOther = ApfloatMath.pi(limit);
			
			// Now we print our all of our variables
			// S approaches zero
			// C approaches 1
			System.out.println("S is:                      " + s.precision(digits));
			System.out.println("C is:                      " + c.precision(digits));
			System.out.println("P is:                      " + p.precision(digits));
			System.out.println("How many digits of Pi      " + digits);
			System.out.println("My Program Pi calculation: " + pi.precision(digits));
			System.out.println("Apfloat Pi calculation:    " + piOther.precision(digits));
			System.out.println("Polygon Side Count:        " + polygon.precision(digits));
			System.out.println("Initial iteration amount:  " + x);
			System.out.println("Iterations stopped at:     " + count);
			
			// And last we write our pi value to a .txt file
			PrintWriter writer;
			try {
				writer = new PrintWriter("pi_to_"+digits+"_digits.txt", "UTF-8");
				pi.precision(digits).writeTo(writer, true);
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
						
	}
}
