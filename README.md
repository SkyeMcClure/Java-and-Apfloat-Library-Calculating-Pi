# Calculating-Pi

Java project using Eclipse IDE and the Apfloat Library (found here: http://www.apfloat.org/apfloat_java/)
Other resources: https://keisan.casio.com/calculator

I devised my own iterative formula to calculate Pi to any decimal place, at a convergent rate of about 0.6. It is a geometric formula, similar to Viète's (https://en.wikipedia.org/wiki/Vi%C3%A8te%27s_formula). 

Essentially, we iterate polygons of 2^n (2 sides, 4 sides, 8 sides.... 32768, and so on) and come up with their side length, then extrapolate the data (2^n * side length).

This is the mathematics equivalent of using compass and ruler.

The Apfloat library allows for arbitrary precision of the decimal value. This allows the output to get exact precision to the chosen decimal place. (My highest iteration so far is 100,000 digits, which took a bit over 5 hours to calculate.)

The exact formula - to test, copy and paste this into https://keisan.casio.com/calculator

s = 1 / sqrt(2);
p = s/2;
c = 1 / sqrt(2);

for (y = 1; y < x; y= y + 1)
{
  s = (sqrt(((c-1)^2) + (s^2)))/2; 
  c = p/s;
  p = s/2;
}

s * 2^x * 2;

Future Updates:
  Graphical interface.
