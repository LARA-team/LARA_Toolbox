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
package de.cesr.lara.testing.components.eventbus;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.cesr.lara.components.eventbus.LaraEventSubscriber;
import de.cesr.lara.components.eventbus.events.LaraAsynchronousEvent;
import de.cesr.lara.components.eventbus.events.LaraEvent;
import de.cesr.lara.components.eventbus.events.LaraSequentialEvent;
import de.cesr.lara.components.eventbus.events.LaraSynchronousEvent;
import de.cesr.lara.components.eventbus.impl.LEventbus;
import de.cesr.lara.toolbox.runtime.SimplePerformanceStatistics;

/**
 * 
 */
public class EventbusTest {

	private static final int numberOfSubscribers = 2000000;
	private static final int numberOfWasteCpuTimeCycles = 10;

	private class TestEnvironment {
		private int counter = 0;

		public synchronized void incrementCounter() {
			counter++;
		}

		public synchronized void decrementCounter() {
			counter--;
		}

		public synchronized int getCounter() {
			return counter;
		}

		public synchronized void reset() {
			counter = 0;
		}
	}

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

	private class TestSubscriber implements LaraEventSubscriber {
		private TestEnvironment testEnvironment;

		private void wasteCpuTime() {
			double resultNobodyCaresAbout = 0d;
			String loremIpsumText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin eleifend convallis magna ut dapibus. Aliquam ornare sagittis sodales. Ut tempus vestibulum ipsum. Integer ut felis quam, sit amet viverra nisl. Fusce ac mi urna, et vestibulum quam. Praesent at dolor mauris, vel condimentum risus. Suspendisse semper ullamcorper imperdiet. Donec vehicula gravida risus, vitae tempus libero sagittis sit amet. Nunc elementum tempus mauris, ut auctor libero aliquam vel. Nullam tempus porta turpis ut elementum. Curabitur id purus massa. Integer massa sem, gravida sed congue sed, placerat quis libero. Aliquam tempor, lacus id venenatis molestie, sem tortor mattis nisl, in scelerisque erat.";

			for (int i = 0; i < numberOfWasteCpuTimeCycles; i++) {
				int count = 0;
				for (char c: loremIpsumText.toCharArray()) {
					if (c == 'a' || c == 'A') {
						count++;
					}
				}
				resultNobodyCaresAbout = (resultNobodyCaresAbout * i) + i;
				resultNobodyCaresAbout = resultNobodyCaresAbout / i;
				resultNobodyCaresAbout += count + loremIpsumText.toLowerCase().indexOf("e");
			}
		}

		public TestSubscriber(TestEnvironment testEnvironment,
				LEventbus eventbus) {
			this.testEnvironment = testEnvironment;
			eventbus.subscribe(this, TestDecrementEvent_Synchronous.class);
			eventbus.subscribe(this, TestDecrementEvent_Sequential.class);
			eventbus.subscribe(this, TestDecrementEvent_Asynchronous.class);
			eventbus.subscribe(this, TestIncrementEvent_Synchronous.class);
			eventbus.subscribe(this, TestIncrementEvent_Sequential.class);
			eventbus.subscribe(this, TestIncrementEvent_Asynchronous.class);
		}

		@Override
		public <T extends LaraEvent> void onEvent(T event) {
			if (event instanceof TestDecrementEvent_Asynchronous
					|| event instanceof TestDecrementEvent_Sequential
					|| event instanceof TestDecrementEvent_Synchronous) {
				// TODO maybe do something "rechenintesives"
				wasteCpuTime();
				testEnvironment.decrementCounter();
			} else if (event instanceof TestIncrementEvent_Asynchronous
					|| event instanceof TestIncrementEvent_Sequential
					|| event instanceof TestIncrementEvent_Synchronous) {
				// TODO maybe do something "rechenintesives"
				wasteCpuTime();
				testEnvironment.incrementCounter();
			}
		}

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCountSynchronous() {
		LEventbus eventbus = LEventbus.getInstance("synchronous");
		Set<Class> eventsToTrack = new HashSet<Class>();
		eventsToTrack.add(TestStartEvent_Synchronous.class);
		eventsToTrack.add(TestEndEvent_Synchronous.class);
		eventsToTrack.add(TestDecrementEvent_Synchronous.class);
		eventsToTrack.add(TestIncrementEvent_Synchronous.class);
		new SimplePerformanceStatistics(eventbus, eventsToTrack);
		eventbus.publish(new TestStartEvent_Synchronous());
		TestEnvironment testEnvironment = new TestEnvironment();
		for (int i = 0; i < numberOfSubscribers; i++) {
			TestSubscriber testSubscriber = new TestSubscriber(testEnvironment,
					eventbus);
		}
		// make every subscriber increment a variable
		eventbus.publish(new TestIncrementEvent_Synchronous());
		// check if variable has expected value = n = number of subscribers
		assertEquals(numberOfSubscribers, testEnvironment.getCounter());
		// make every subscriber decrement a variable
		eventbus.publish(new TestDecrementEvent_Synchronous());
		// check if variable has expected value = n-n = 0
		assertEquals(0, testEnvironment.getCounter());
		eventbus.publish(new TestEndEvent_Synchronous());
		eventbus = null;
		testEnvironment = null;
	}

	@Test
	public void testCountSequential() {
		LEventbus eventbus = LEventbus.getInstance("sequential");
		Set<Class> eventsToTrack = new HashSet<Class>();
		eventsToTrack.add(TestStartEvent_Synchronous.class);
		eventsToTrack.add(TestEndEvent_Synchronous.class);
		eventsToTrack.add(TestDecrementEvent_Sequential.class);
		eventsToTrack.add(TestIncrementEvent_Sequential.class);
		new SimplePerformanceStatistics(eventbus, eventsToTrack);
		eventbus.publish(new TestStartEvent_Synchronous());
		TestEnvironment testEnvironment = new TestEnvironment();
		for (int i = 0; i < numberOfSubscribers; i++) {
			TestSubscriber testSubscriber = new TestSubscriber(testEnvironment,
					eventbus);
		}
		// make every subscriber increment a variable
		eventbus.publish(new TestIncrementEvent_Sequential());
		// check if variable has expected value = n = number of subscribers
		assertEquals(numberOfSubscribers, testEnvironment.getCounter());
		// make every subscriber decrement a variable
		eventbus.publish(new TestDecrementEvent_Sequential());
		// check if variable has expected value = n-n = 0
		assertEquals(0, testEnvironment.getCounter());
		eventbus.publish(new TestEndEvent_Synchronous());
		eventbus = null;
		testEnvironment = null;
	}

	/**
	 * asynch means, there might be increments/decrements after(!) assertEquals
	 */
	@Test
	public void testCountAsynchronous() {
		LEventbus eventbus = LEventbus.getInstance("asynchronous");
		Set<Class> eventsToTrack = new HashSet<Class>();
		eventsToTrack.add(TestStartEvent_Synchronous.class);
		eventsToTrack.add(TestEndEvent_Synchronous.class);
		eventsToTrack.add(TestDecrementEvent_Asynchronous.class);
		eventsToTrack.add(TestIncrementEvent_Asynchronous.class);
		new SimplePerformanceStatistics(eventbus, eventsToTrack);
		eventbus.publish(new TestStartEvent_Synchronous());
		TestEnvironment testEnvironment = new TestEnvironment();
		for (int i = 0; i < numberOfSubscribers; i++) {
			TestSubscriber testSubscriber = new TestSubscriber(testEnvironment,
					eventbus);
		}
		// make every subscriber increment a variable
		eventbus.publish(new TestIncrementEvent_Sequential());
		// check if variable has expected value >= 0 <= number of subscribers
		assertEquals(
				true,
				testEnvironment.getCounter() >= 0
						&& testEnvironment.getCounter() <= numberOfSubscribers);
		// make every subscriber decrement a variable
		eventbus.publish(new TestDecrementEvent_Sequential());
		// check if variable has expected value >= 0 <= number of subscribers
		assertEquals(
				true,
				testEnvironment.getCounter() >= 0
						&& testEnvironment.getCounter() <= numberOfSubscribers);
		eventbus.publish(new TestEndEvent_Synchronous());
		eventbus = null;
		testEnvironment = null;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
}
