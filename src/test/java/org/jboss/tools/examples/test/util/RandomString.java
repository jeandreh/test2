package org.jboss.tools.examples.test.util;

import java.util.Random;

public class RandomString {

	  private static final char[] symbols;

	  static {
	    StringBuilder tmp = new StringBuilder();
	    for (char ch = '0'; ch <= '9'; ++ch)
	      tmp.append(ch);
	    for (char ch = 'a'; ch <= 'z'; ++ch)
	      tmp.append(ch);
	    symbols = tmp.toString().toCharArray();
	  }   

	  private final Random random = new Random();

	  private final char[] buf;

	  private RandomString(int length) {
	    if (length < 1) 
	    	length = 1;
	    buf = new char[length];
	  }

	  private String nextString() {
	    for (int idx = 0; idx < buf.length; ++idx) 
	      buf[idx] = symbols[random.nextInt(symbols.length)];
	    return new String(buf);
	  }
	  
	  public static String getString(int length) {
		  RandomString rs = new RandomString(length);
		  return rs.nextString();
	  }
	  
	}