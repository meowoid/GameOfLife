package uk.ac.cam.yp242.tick1star;

public class InspectDouble {

 public static void main(String[] args) throws Exception {

  double d = Double.parseDouble(args[0]);

  // return the bits which represent the floating point number
  long bits = Double.doubleToLongBits(d);


  long mask = 0x8000000000000000L;
  boolean negative = ( bits & mask ) >> 63 != 0;

  long exponent =  ((bits & 0x7ff0000000000000L) >>> 52) - 1023;

  long mantissabits = (bits & 0xFFFFFFFFFFFFFL) ;
                  
  double mantissa = mantissaToDecimal(mantissabits);
  
  System.out.println((negative ? "-" : "") + mantissa + " x 2^" + exponent);
 }

 private static double mantissaToDecimal(long mantissabits) {
  long one = 0x0010000000000000L;
  return (double)(mantissabits + one) / (double)one;
 }
}
