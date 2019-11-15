package edu.ku.cete.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class FileUtil {
	
	public static String readFileAsString(ClassLoader classLoader, String filePath) throws java.io.IOException {
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(filePath)));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}
	
	public static String buildFilePath(String folder, String fileName) {
		String filePath = folder;
		if (!filePath.endsWith(File.separator) && !fileName.startsWith(File.separator)) {
			filePath += File.separator;
		}
		filePath += fileName;
		Path path = Paths.get(filePath);
		return path.toString();
	}
	
	/**
	 * Creates a CSVWriter for the specified file in the specified folder (no append). If the folder does not exist,
	 * it and its parents will be created.<br><br>This is equivalent to <code>createCSVWriter(folder, fileName, false)</code>.
	 * @param folder
	 * @param fileName
	 * @return The CSVWriter for the specified file.
	 * @throws IOException
	 */
	public static CSVWriter createCSVWriter(String folder, String fileName) throws IOException {
		return createCSVWriter(folder, fileName, false);
	}
	
	/**
	 * Creates a CSVWriter for the specified file in the specified folder. If the folder does not exist,
	 * it and its parents will be created.
	 * @param folder 
	 * @param fileName
	 * @param append
	 * @return The CSVWriter for the specified file.
	 * @throws IOException
	 */
	public static CSVWriter createCSVWriter(String folder, String fileName, boolean append) throws IOException {
		File f = new File(folder);
		if(!f.exists()){
			f.mkdirs();
		}
		
		String filePath = buildFilePath(folder, fileName);
		
		File csvFile = new File(filePath);
		return new CSVWriter(new FileWriter(csvFile, append), ',');
	}
	
	/**
	 * Common function to write CSV data. Opens a file with specified filename, writes the lines, then closes the file.<br><br>
	 * This is equivalent to <code>writeCSV(lines, folder, fileName, false)</code>.
	 * @param lines
	 * @param folder 
	 * @param fileName
	 * @throws IOException
	 */
	public static void writeCSV(List<String[]> lines, String folder, String fileName) throws IOException {
		CSVWriter csvWriter = createCSVWriter(folder, fileName, false);
		csvWriter.writeAll(lines);
		csvWriter.close();
	}
	
	/**
	 * Common function to write CSV data. Opens a file with specified filename, writes the lines, then closes the file.
	 * @param lines
	 * @param folder 
	 * @param fileName
	 * @param append
	 * @throws IOException
	 */
	public static void writeCSV(List<String[]> lines, String folder, String fileName, boolean append) throws IOException {
		CSVWriter csvWriter = createCSVWriter(folder, fileName, append);
		csvWriter.writeAll(lines);
		csvWriter.close();
	}
	
	/**
	 * Read all lines from a CSV file with the default delimiter ','.
	 * @param folder
	 * @param fileName
	 * @return The lines from the file, in a list of string arrays.
	 * @throws IOException
	 */
	public static List<String[]> readCSV(String folder, String fileName) throws IOException {
		return readCSV(folder, fileName, ',');
	}
	
	/**
	 * Read all lines from a CSV file.
	 * @param folder
	 * @param fileName
	 * @return The lines from the file, in a list of string arrays.
	 * @throws IOException
	 */
	public static List<String[]> readCSV(String folder, String fileName, char separator) throws IOException {
		String filePath = buildFilePath(folder, fileName);
		CSVReader csvReader = new CSVReader(new FileReader(filePath), separator);
		List<String[]> lines = csvReader.readAll();
		csvReader.close();
		return lines;
	}
	
	public static String getFileNameFromPath(String path) {
		if (path == null || path.length() == 0) return null;
		return path.substring(path.lastIndexOf(File.separator) + 1);
	}
}