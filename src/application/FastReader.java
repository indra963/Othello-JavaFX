package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * CPT: Othello - a strategic two-player board game
 * 
 * <p> Class: FastReader - an Object reads files quickly using BufferedReader and StringTokenizer </p>
 * 
 * @author Steven Mathew
 * @version 1.0
 *
 */

public class FastReader {
	// declare BufferedReader and StringTokenizer objects
	public BufferedReader br; 
	public StringTokenizer st; 

    /**
    * <p>
    * 	A constructor for the FastReader class that is set to user-desired values.
    * </p>
    * 
    * @param file - String: the directory of the file
    * @version 1.0
    * @author Steven Mathew
    */ 
	
	public FastReader(String file) throws FileNotFoundException { 
		br = new BufferedReader(new FileReader(file)); 
	} 

	
	 /**
     * <h1> 
     *   <i> 
     *     <br>
     *       read
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic String read()
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that reads the next word as a <b>String</b>.
     * </p>
     * 
     * <br>
     * 
     * @return the token as a <b>String</b>  
     * @version 1.0
     * @author Steven Mathew
     */
	
	public String read() { 
		// while the tokenizer cannot find any tokens...
		while (st == null || !st.hasMoreElements())  { 
			// try and catch for input/output errors
			try { 
				// read the line
				st = new StringTokenizer(br.readLine()); 
			} 
			catch (IOException  e) { 
				e.printStackTrace(); 
			} 
		} 
		// return the next token
		return st.nextToken(); 
	} 

	 /**
     * <h1> 
     *   <i> 
     *     <br>
     *       readInt
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic int readInt()
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that reads the next int by parsing.
     * </p>
     * 
     * <br>
     * 
     * @return the parsed token as an <b>int</b> 
     * @version 1.0
     * @author Steven Mathew
     */
	
	public int readInt() { 
		return Integer.parseInt(read()); 
	} 

	 /**
     * <h1> 
     *   <i> 
     *     <br>
     *       readLong
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic long readLong()
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that reads the next long by parsing.
     * </p>
     * 
     * <br>
     * 
     * @return the parsed token as a <b>long</b> 
     * @version 1.0
     * @author Steven Mathew
     */
	
	public long readLong() { 
		return Long.parseLong(read()); 
	} 

	 /**
     * <h1> 
     *   <i> 
     *     <br>
     *       readDouble
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic double readDouble()
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that reads the next double by parsing.
     * </p>
     * 
     * <br>
     * 
     * @return the parsed token as a <b>double</b>
     * @version 1.0
     * @author Steven Mathew
     */
	
	public double readDouble() { 
		return Double.parseDouble(read()); 
	} 

	 /**
     * <h1> 
     *   <i> 
     *     <br>
     *       readLine
     *     </br> 
     *   </i>
     * </h1>
     * 
     * <p>
     *   <code>
     *      &nbsp&nbsppublic String readLine()
     *   </code>
     * </p>
     * 
     * <p>
     *    A method that reads the next line as a <b>String</b>.
     * </p>
     * 
     * <br>
     * 
     * @return str - String: the line of input
     * @version 1.0
     * @author Steven Mathew
     */
	
	public String readLine() { 
		// declare a temp String var
		String str = ""; 
		
		// try and catch for input/output errors
		try {
			// store the next line
			str = br.readLine(); 
		} 
		catch (IOException e) { 
			e.printStackTrace(); 
		} 
		
		// return the line that was read
		return str; 
	}
}
