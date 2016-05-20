/**
 * 
 */
package de.cesr.lara.toolbox.io;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.csvreader.CsvWriter;

import de.cesr.lara.components.decision.LaraDecisionConfiguration;
import de.cesr.lara.components.eventbus.LaraAbstractEventSubscriber;
import de.cesr.lara.components.eventbus.events.LaraEvent;
import de.cesr.lara.components.eventbus.impl.LDcSpecificEventbus;
import de.cesr.lara.components.eventbus.impl.LEventbus;
import de.cesr.lara.components.model.impl.LModel;


/**
 * @author Sascha Holzhauer
 *
 */
public class LEventbusStateCsvOutput {

	public static final String[] CSV_HEADER = { "Eventclass", "DecisionConfig", "Subscriber", "Singular", "Triggered" };

	public static void outputEventbusState(LEventbus eventbus) {
		outputEventbusState(eventbus, "./Eventbus_" + eventbus + "_Stage_"
				+ LModel.getModel(eventbus.getId()).getCurrentStep() + ".csv");
	}

	public static void outputEventbusState(LEventbus eventbus, String outputfilename) {
		CsvWriter csvWriter = new CsvWriter(outputfilename);
		outputEventbusState(eventbus, csvWriter, true, false, false);
		csvWriter.close();
	}

	/**
	 * The given csvWriter is not closed here!
	 * 
	 * @param eventbus
	 * @param csvWriter
	 * @param writeHeader
	 * @param simpleEventnames
	 * @param writeEventbus
	 */
	public static void outputEventbusState(LEventbus eventbus, CsvWriter csvWriter, boolean writeHeader,
			boolean simpleEventnames, boolean writeEventbus) {
		try {
			int colnum = CSV_HEADER.length;
			if (writeHeader) {
				List<String> header = new ArrayList<>(Arrays.asList(CSV_HEADER));
				if (writeEventbus) {
					header.add(0, "Eventbus");
				}
				csvWriter.writeRecord(header.toArray(new String[header.size()]));
			}

			if (writeEventbus) {
				colnum++;
			}

			for (Class<? extends LaraEvent> eventClass : eventbus.getAllConsideredEvents()) {
				String eventClassDesc = simpleEventnames ? eventClass.getSimpleName() : eventClass.toString();
				for (LaraAbstractEventSubscriber subscriber : eventbus.getRegularSubscribers(eventClass)) {
					String[] output = new String[colnum];
					int index = 0;
					if (writeEventbus)
						output[index++] = eventbus.toString();
					output[index++] = eventClassDesc;
					output[index++] = "NA";
					output[index++] = subscriber.toString();
					output[index++] = "0";
					output[index++] = eventbus.occured(eventClass) ? "1" : "0";
					csvWriter.writeRecord(output);
				}

				for (LaraAbstractEventSubscriber subscriber : eventbus.getSingularSubscribers(eventClass)) {
					String[] output = new String[colnum];
					int index = 0;
					if (writeEventbus)
						output[index++] = eventbus.toString();
					output[index++] = eventClassDesc;
					output[index++] = "NA";
					output[index++] = subscriber.toString();
					output[index++] = "1";
					output[index++] = eventbus.occured(eventClass) ? "1" : "0";
					csvWriter.writeRecord(output);
				}
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public static void outputEventbusState(LDcSpecificEventbus eventbus) {
		outputEventbusState(eventbus, "./Eventbus_" + eventbus + "_Stage_"
				+ LModel.getModel(eventbus.getId()).getCurrentStep() + ".csv");
	}

	public static void outputEventbusState(LDcSpecificEventbus eventbus, String outputfilename) {
		CsvWriter csvWriter = new CsvWriter(outputfilename);
		outputEventbusState(eventbus, csvWriter, true, false, false);
		csvWriter.close();
	}

	/**
	 * The given csvWriter is not closed here!
	 * 
	 * @param eventbus
	 * @param csvWriter
	 * @param writeHeader
	 * @param simpleEventnames
	 * @param writeEventbus
	 */
	public static void outputEventbusState(LDcSpecificEventbus eventbus, CsvWriter csvWriter, boolean writeHeader,
			boolean simpleEventnames, boolean writeEventbus) {

		try {
			int colnum = CSV_HEADER.length;
			if (writeHeader) {
				List<String> header = new ArrayList<>(Arrays.asList(CSV_HEADER));
				if (writeEventbus) {
					header.add(0, "Eventbus");
				}
				csvWriter.writeRecord(header.toArray(new String[header.size()]));
			}

			if (writeEventbus) {
				colnum++;
			}

			for (Class<? extends LaraEvent> eventClass : eventbus.getAllConsideredEvents()) {
				String eventClassDesc = simpleEventnames ? eventClass.getSimpleName() : eventClass.toString();
				for (LaraAbstractEventSubscriber subscriber : eventbus.getRegularSubscribers(eventClass)) {
					String[] output = new String[colnum];
					int index = 0;
					if (writeEventbus)
						output[index++] = eventbus.toString();
					output[index++] = eventClassDesc;
					output[index++] = "NA";
					output[index++] = subscriber.toString();
					output[index++] = "0";
					output[index++] = eventbus.occured(eventClass) ? "1" : "0";
					csvWriter.writeRecord(output);
				}

				for (LaraAbstractEventSubscriber subscriber : eventbus.getSingularSubscribers(eventClass)) {
					String[] output = new String[colnum];
					int index = 0;
					if (writeEventbus)
						output[index++] = eventbus.toString();
					output[index++] = eventClassDesc;
					output[index++] = "NA";
					output[index++] = subscriber.toString();
					output[index++] = "1";
					output[index++] = eventbus.occured(eventClass) ? "1" : "0";
					csvWriter.writeRecord(output);
				}
				Map<LaraDecisionConfiguration, Set<LaraAbstractEventSubscriber>> dcMap = 						eventbus.getRegularSubscribersDc(eventClass);
				for (LaraDecisionConfiguration dConfig : dcMap.keySet()) {
					for (LaraAbstractEventSubscriber subscriber : dcMap.get(dConfig)) {
						String[] output = new String[colnum];
						int index = 0;
						if (writeEventbus)
							output[index++] = eventbus.toString();
						output[index++] = eventClassDesc;
						output[index++] = dConfig.toString();
						output[index++] = subscriber.toString();
						output[index++] = "1";
						output[index++] = eventbus.occured(eventClass) ? "1" : "0";
						csvWriter.writeRecord(output);
					}
				}
				dcMap = eventbus.getSingularSubscribersDc(eventClass);
				for (LaraDecisionConfiguration dConfig : dcMap.keySet()) {
					for (LaraAbstractEventSubscriber subscriber : dcMap.get(dConfig)) {
						String[] output = new String[colnum];
						int index = 0;
						if (writeEventbus)
							output[index++] = eventbus.toString();
						output[index++] = eventClassDesc;
						output[index++] = dConfig.toString();
						output[index++] = subscriber.toString();
						output[index++] = "1";
						output[index++] = eventbus.occured(eventClass) ? "1" : "0";
						csvWriter.writeRecord(output);
					}
				}
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
}
