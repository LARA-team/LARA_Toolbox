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
package de.cesr.lara.toolbox.runtime;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import de.cesr.lara.components.eventbus.LaraEventSubscriber;
import de.cesr.lara.components.eventbus.LaraInternalEventSubscriber;
import de.cesr.lara.components.eventbus.events.LAgentDecideEvent;
import de.cesr.lara.components.eventbus.events.LAgentExecutionEvent;
import de.cesr.lara.components.eventbus.events.LAgentPerceptionEvent;
import de.cesr.lara.components.eventbus.events.LAgentPostprocessEvent;
import de.cesr.lara.components.eventbus.events.LAgentPreprocessEvent;
import de.cesr.lara.components.eventbus.events.LModelFinishEvent;
import de.cesr.lara.components.eventbus.events.LModelInitializedEvent;
import de.cesr.lara.components.eventbus.events.LModelInstantiatedEvent;
import de.cesr.lara.components.eventbus.events.LModelStepEvent;
import de.cesr.lara.components.eventbus.events.LModelStepFinishedEvent;
import de.cesr.lara.components.eventbus.events.LUpdateEnvironmentEvent;
import de.cesr.lara.components.eventbus.events.LaraEvent;
import de.cesr.lara.components.eventbus.impl.LEventbus;

/**
 * simple performance data output. creates csv-file and appends one line for
 * every
 */
public class SimplePerformanceStatistics implements LaraEventSubscriber,
		LaraInternalEventSubscriber {

	private String filename;
	private String lineSep = "\n";
	private String valueSep = ";";
	private LEventbus eventBus;
	private boolean initialized = false;
	private long timestamp;
	private String lastMetaInfo = "undefined";
	private String lastEvent = "StatsInitialized";

	/**
	 * creates the file with key as name and header
	 * 
	 * @param key
	 */
	public SimplePerformanceStatistics(LEventbus eventbus) {
		Set<Class> eventsToTrack = new HashSet<Class>();
		eventsToTrack.add(LAgentDecideEvent.class);
		eventsToTrack.add(LAgentExecutionEvent.class);
		eventsToTrack.add(LAgentPerceptionEvent.class);
		eventsToTrack.add(LAgentPostprocessEvent.class);
		eventsToTrack.add(LAgentPreprocessEvent.class);
		eventsToTrack.add(LModelFinishEvent.class);
		eventsToTrack.add(LModelInitializedEvent.class);
		eventsToTrack.add(LModelInstantiatedEvent.class);
		eventsToTrack.add(LModelStepEvent.class);
		eventsToTrack.add(LModelStepFinishedEvent.class);
		eventsToTrack.add(LUpdateEnvironmentEvent.class);
		init(eventbus, eventsToTrack);
	}

	/**
	 * creates the file with key as name and header
	 * 
	 * @param key
	 */
	public SimplePerformanceStatistics(LEventbus eventbus,
			Set<Class> eventsToTrack) {
		init(eventbus, eventsToTrack);
	}

	/**
	 * appends current stats to the output file
	 * 
	 * @param value
	 */
	public void appendCurrentStats(LaraEvent event, String newMetaInfo) {
		try {
			FileWriter fileWriter = new FileWriter(new File(filename), true);
			long executionTime = Calendar.getInstance().getTimeInMillis()
					- timestamp;
			timestamp = Calendar.getInstance().getTimeInMillis();
			// call the garbage collector
			System.gc();
			Runtime runtime = Runtime.getRuntime();
			String line = lastEvent + valueSep + executionTime + valueSep
					+ (runtime.totalMemory() - runtime.freeMemory()) + valueSep
					+ lastMetaInfo;
			line += lineSep;
			fileWriter.append(line);
			fileWriter.close();
			lastEvent = event.getClass().getSimpleName();
			lastMetaInfo = newMetaInfo;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public <T extends LaraEvent> void onEvent(T event) {
		appendCurrentStats(event, "");
	}

	@Override
	public void onInternalEvent(LaraEvent event) {
		appendCurrentStats(event, "internal");
	}

	/**
	 * initializes the output. creates a new file to write to. writes the header
	 * to the file.
	 */
	private void init(LEventbus eventbus, Set<Class> eventsToTrack) {
		timestamp = Calendar.getInstance().getTimeInMillis();
		eventBus = eventbus;
		subscribeToEvents(eventsToTrack);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		filename = "output" + File.separator + "performance" + File.separator
				+ "performance_"
				+ dateFormat.format(Calendar.getInstance().getTime()) + ".csv";
		File outdir = new File("output" + File.separator + "performance");
		outdir.mkdirs();
		try {
			FileWriter fileWriter = new FileWriter(new File(filename), false);
			// write header
			String line = "Event" + valueSep + "ExecutionTime (millis)"
					+ valueSep + "Memory (bytes)" + valueSep + "Meta";
			line += lineSep;
			fileWriter.append(line);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void subscribeToEvents(Set<Class> eventsToTrack) {
		for (Class eventClass : eventsToTrack) {
			eventBus.subscribe(this, eventClass);
		}
	}

}