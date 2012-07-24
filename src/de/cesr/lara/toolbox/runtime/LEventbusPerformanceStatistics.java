/**
 * 
 */
package de.cesr.lara.toolbox.runtime;

import java.util.HashSet;
import java.util.Set;

import de.cesr.lara.components.eventbus.events.LaraAsynchronousEvent;
import de.cesr.lara.components.eventbus.events.LaraSequentialEvent;
import de.cesr.lara.components.eventbus.events.LaraSynchronousEvent;
import de.cesr.lara.components.eventbus.impl.LEventbus;


/**
 * @author Sascha Holzhauer
 *
 */
public class LEventbusPerformanceStatistics {

	private class TestStartEvent_Synchronous implements LaraSynchronousEvent {
	}

	private class TestEndEvent_Synchronous implements LaraSynchronousEvent {
	}

	private class TestDecrementEvent_Synchronous implements
			LaraSynchronousEvent {
	}

	private class TestDecrementEvent_Sequential implements LaraSequentialEvent {
	}

	private class TestDecrementEvent_Asynchronous implements
			LaraAsynchronousEvent {
	}

	private class TestIncrementEvent_Synchronous implements
			LaraSynchronousEvent {
	}

	private class TestIncrementEvent_Sequential implements LaraSequentialEvent {
	}

	private class TestIncrementEvent_Asynchronous implements
			LaraAsynchronousEvent {
	}
	
	public void testCountSynchronous() {
		LEventbus eventbus = LEventbus.getInstance("asynchronous");
		Set<Class> eventsToTrack = new HashSet<Class>();
		eventsToTrack.add(TestStartEvent_Synchronous.class);
		eventsToTrack.add(TestEndEvent_Synchronous.class);
		eventsToTrack.add(TestDecrementEvent_Asynchronous.class);
		eventsToTrack.add(TestIncrementEvent_Asynchronous.class);
		new SimplePerformanceStatistics(eventbus, eventsToTrack);
	}
	
	public void testCountSequential() {
		LEventbus eventbus = LEventbus.getInstance("sequential");
		Set<Class> eventsToTrack = new HashSet<Class>();
		eventsToTrack.add(TestStartEvent_Synchronous.class);
		eventsToTrack.add(TestEndEvent_Synchronous.class);
		eventsToTrack.add(TestDecrementEvent_Sequential.class);
		eventsToTrack.add(TestIncrementEvent_Sequential.class);
		new SimplePerformanceStatistics(eventbus, eventsToTrack);
	}
	
	public void testCountAsynchronous() {
		LEventbus eventbus = LEventbus.getInstance("asynchronous");
		Set<Class> eventsToTrack = new HashSet<Class>();
		eventsToTrack.add(TestStartEvent_Synchronous.class);
		eventsToTrack.add(TestEndEvent_Synchronous.class);
		eventsToTrack.add(TestDecrementEvent_Asynchronous.class);
		eventsToTrack.add(TestIncrementEvent_Asynchronous.class);
		new SimplePerformanceStatistics(eventbus, eventsToTrack);
	}
	
	static public void main(String[] arg) {
		LEventbusPerformanceStatistics statistics = new LEventbusPerformanceStatistics();
		statistics.testCountSynchronous();
		statistics.testCountSequential();
		statistics.testCountAsynchronous();
	}
}
