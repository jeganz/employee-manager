package com.litmus7.employeemanager.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TextFileUtil {
	public static List<String> readTextFile(String inputFile) throws IOException {
		// TODO Auto-generated method stub
			FileReader file = new FileReader(inputFile);
			BufferedReader bf = new BufferedReader(file);
			List<String> lines=new ArrayList<String>();
			String line;
			while((line = bf.readLine() )!=null) {
				lines.add(line);	
			}
			bf.close();
			return lines;
	}

	public static void writeCSVFile(List<String> csvStrings, String outputFile) throws IOException {
		// TODO Auto-generated method stub
			FileWriter file=new FileWriter(outputFile);
			PrintWriter printWriter=new PrintWriter(file);
			printWriter.println("Id,First Name,Last Name,Mobile,Email,Joining Date,Active Status");
			for (String string : csvStrings) {
				printWriter.println(string);
			}
			printWriter.close();
		
	}

	public static void writeDataToCSV(String employeeString, String outputFile) throws IOException {
		// TODO Auto-generated method stub
			FileWriter file=new FileWriter(outputFile,true);
			PrintWriter printWriter=new PrintWriter(file);
			printWriter.println(employeeString);
			printWriter.close();
	}

	

	
}
