/**
 * This file is part of
 * 
 * LARA - Lightweight Architecture for boundedly Rational citizen Agents
 * 
 * Copyright (C) 2012 Center for Environmental Systems Research, Kassel, Germany
 * 
 * LARA is free software: You can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * LARA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.cesr.lara.toolbox.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.StringTokenizer;

import de.cesr.lara.components.eventbus.LaraEventSubscriber;
import de.cesr.lara.components.eventbus.events.LaraEvent;
import de.cesr.lara.components.eventbus.impl.LEventbus;

/**
 * simple output. creates csv-file and provides method to append one line
 */
public class SimpleCsvOutput implements LaraEventSubscriber {

	private LEventbus eventBus;
	private String lineSep = "\n";
	private String outputDir;
	private String timestamp = "" + Calendar.getInstance().getTimeInMillis();
	private String valueSep = ";";

	/**
	 * Will create a new simple csv output. Files will be written in [model base
	 * dir]/[modelName]
	 * 
	 * @param modelName
	 */
	public SimpleCsvOutput(String modelName) {
		eventBus = LEventbus.getInstance();
		outputDir = "output" + File.separator + modelName + File.separator;
		File outputDirFile = new File(outputDir);
		if (!outputDirFile.exists()) {
			outputDirFile.mkdirs();
		}
	}

	/**
	 * appends one line with the value String to the file
	 * 
	 * @param value
	 */
	public void append(String filename, String value) {
		try {
			FileWriter fileWriter = new FileWriter(new File(outputDir
					+ filename + ".csv"), true);
			fileWriter.append(value + lineSep);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public <T extends LaraEvent> void onEvent(T event) {
		// uses event.toString(); --> substring before | will become part of
		// filename, substring after | will become a row
		StringTokenizer stringTokenizer = new StringTokenizer(event.toString(),
				"|");
		append(timestamp + "_" + stringTokenizer.nextToken(),
				stringTokenizer.nextToken());
	}

	public void subscribeToEvent(Class<? extends LaraEvent> eventClass) {
		eventBus.subscribe(this, eventClass);
	}

}