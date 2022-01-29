import java.util.Scanner;
import java.io.*;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		if(args.length == 1) {		
			// display file names on console
			for(int i=0; i<args.length; i++) {
				System.out.println("args["+ i +"] : " + args[i]);
			}
			
			// open input file
			Scanner inFile = new Scanner(new File(args[0]));
					
			// runLength object created
			runLength x = new runLength(inFile);
			
			// create encodeFile
			x.setNameEncodeFile(args[0].replace(".txt", "_EncodeMethod" +
											 		x.getWhichMethod() + ".txt"));
			PrintWriter encodeFile = new PrintWriter(new File(x.getNameEncodeFile()));
			
			
			// encode
			x.encodeMethod1(encodeFile, inFile);
			inFile.close();
			encodeFile.close();
			inFile = new Scanner(new File(x.getNameEncodeFile()));
			
			
			// create decodeFile
			x.setNameDecodeFile(x.getNameEncodeFile().replace(".txt", "_Decoded.txt"));
			PrintWriter decodeFile = new PrintWriter(new File(x.getNameDecodeFile()));
			
			
			// decode
			x.decodeMethod1(decodeFile, inFile);
			
			// close input and output files	
			inFile.close();
			decodeFile.close();
				
		} 
		else {
			System.out.println("Invalid number of arguments.");
		}
		
	} // end main
	
	
	public static class runLength {
		private int numRows;
		private int numCols;
		private int minVal;
		private int maxVal;
		private int numRuns;
		private int whichMethod;
		private String nameEncodeFile;
		private String nameDecodeFile;
		
		// constructor, read in file header and decode method
		public runLength(Scanner inFile) {
			numRows = inFile.nextInt();
			numCols = inFile.nextInt();
			minVal = inFile.nextInt();
			maxVal = inFile.nextInt();
			whichMethod = 1; // run-length encoding method 1
		} // end constructor
		
		// setters and getters
		public void setNameEncodeFile(String s) {nameEncodeFile = s;}
		public String getNameEncodeFile() {return nameEncodeFile;}
		public void setNameDecodeFile(String s) {nameDecodeFile = s;}	
		public String getNameDecodeFile() {return nameDecodeFile;}
		public int getWhichMethod() {return whichMethod;}
		
		
		// run-length encoding: method 1, encodes zero's with no wrap-around
		public void encodeMethod1(PrintWriter outFile, Scanner inFile) {
			// output header and whichMethod
			outFile.println(numRows + " " + numCols + " " + minVal + " " + maxVal);
			outFile.println(whichMethod + " ");
			
			for(int i=0; i<numRows; i++) {
				int currVal = inFile.nextInt();
				int length = 1;
				for(int j=1; j<numCols; j++) {
					if(j==1) outFile.print(i + " " + (j-1) + " " + currVal + " ");
					int nextVal = inFile.nextInt();
					if(nextVal != currVal) {
						outFile.println(length + " ");
						currVal = nextVal;
						outFile.print(i + " " + j + " " + currVal + " ");
						length = 1;	
					}
					else length++;
					if(j == numCols-1) outFile.println(length + " ");
				}		
			}
		} // end encodeMethod1()
		
		
		// run-length decoding: method 1
		public void decodeMethod1(PrintWriter outFile, Scanner inFile) {
			// read and output header
			int maxRow = inFile.nextInt();
			int maxCol = inFile.nextInt();
			int min = inFile.nextInt();
			int max = inFile.nextInt();
			int methodNum = inFile.nextInt();
			outFile.println(maxRow +" "+ maxCol +" "+ min+" "+max);
			
			// create and initialize 2D-array
			int[][] ary = new int[maxRow][maxCol];
			for(int i=0; i<maxRow; i++) {
				for(int j=0; j<maxCol; j++) {
					ary[i][j] = 0;
				}
			}
			
			// decode: read in data for each run and reconstruct file in 2D-array
			int row, col, gray, length;
			while(inFile.hasNext()) {
				row = inFile.nextInt();
				col = inFile.nextInt();
				gray = inFile.nextInt();
				length = inFile.nextInt();
				while(length > 0) {
					ary[row][col] = gray;
					col++;
					length--;
				}
			}
			
			// output 2D-array
			for(int i=0; i<maxRow; i++) {
				for(int j=0; j<maxCol; j++) {
					outFile.print(ary[i][j] + " ");
				}
				outFile.println();
			}
		} // end decodeMethod1()
				
		
	} // end class: runLength

} // end class: Main
