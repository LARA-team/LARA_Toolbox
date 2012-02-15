package de.cesr.lara.toolbox.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import de.cesr.lara.components.util.logging.impl.Log4jLogger;

/**
 * Simple utility to read values from a csv file. Assumes the file has a header
 * row. Column headers will be used to obtain column data. ";" separates the
 * columns. "," separates values in the same column. "." is used for floating
 * values. Encoding of csv file should be utf-8. TODO test and comment
 * 
 */
public class SimpleCsvInput {

	private File file;
	private final String COLUMN_SEPARATOR = ";";
	private final String VALUE_SEPARATOR = ",";
	private List<String> columnIds;
	private List<Row> rows;
	private static Logger logger = Log4jLogger.getLogger(SimpleCsvInput.class
			.getSimpleName());

	/**
	 * Path to file e.g. input/myFile.csv
	 * 
	 * @param inputFilePath
	 * @throws IOException
	 */
	public SimpleCsvInput(String inputFilePath) throws IOException {
		file = new File(inputFilePath);
		readFile(file);
	}

	private void readFile(File file) throws IOException {
		columnIds = new ArrayList<String>();
		rows = new ArrayList<Row>();
		BufferedReader input = new BufferedReader(new FileReader(file));
		// read first line which should contain column ids
		String line = input.readLine();
		StringTokenizer tokenizer = new StringTokenizer(line, COLUMN_SEPARATOR);
		while (tokenizer.hasMoreTokens()) {
			columnIds.add(tokenizer.nextToken());
		}
		// read second line
		line = input.readLine();
		// if no more lines left, line will be null
		while (line != null) {
			rows.add(new Row(line));
			// read next line
			line = input.readLine();
		}
	}

	public class Row {
		private HashMap<String, String> values = new HashMap<String, String>();

		public Row(String line) {
			StringTokenizer tokenizer = new StringTokenizer(line,
					COLUMN_SEPARATOR);
			// put tokens as id - value pairs into map
			for (String columnId : columnIds) {
				values.put(columnId, tokenizer.nextToken());
			}
		}

		public String getValueAsString(String columnId) {
			return values.get(columnId);
		}

		public List<String> getValueAsListOfString(String columnId) {
			List<String> list = new ArrayList<String>();
			StringTokenizer tokenizer = new StringTokenizer(
					values.get(columnId), VALUE_SEPARATOR);
			while (tokenizer.hasMoreTokens()) {
				list.add(tokenizer.nextToken());
			}
			return list;
		}

		public Integer getValueAsInteger(String columnId) {
			return new Integer(values.get(columnId));
		}

		public List<Integer> getValueAsListOfInteger(String columnId) {
			List<Integer> list = new ArrayList<Integer>();
			StringTokenizer tokenizer = new StringTokenizer(
					values.get(columnId), VALUE_SEPARATOR);
			while (tokenizer.hasMoreTokens()) {
				list.add(new Integer(tokenizer.nextToken()));
			}
			return list;
		}

		public Float getValueAsFloat(String columnId) {
			return new Float(values.get(columnId));
		}

		public List<Float> getValueAsListOfFloat(String columnId) {
			List<Float> list = new ArrayList<Float>();
			StringTokenizer tokenizer = new StringTokenizer(
					values.get(columnId), VALUE_SEPARATOR);
			while (tokenizer.hasMoreTokens()) {
				list.add(new Float(tokenizer.nextToken()));
			}
			return list;
		}

		public Double getValueAsDouble(String columnId) {
			return new Double(values.get(columnId));
		}

		public List<Double> getValueAsListOfDouble(String columnId) {
			List<Double> list = new ArrayList<Double>();
			StringTokenizer tokenizer = new StringTokenizer(
					values.get(columnId), VALUE_SEPARATOR);
			while (tokenizer.hasMoreTokens()) {
				list.add(new Double(tokenizer.nextToken()));
			}
			return list;
		}
	}

	public List<Row> getRows() {
		return rows;
	}

}