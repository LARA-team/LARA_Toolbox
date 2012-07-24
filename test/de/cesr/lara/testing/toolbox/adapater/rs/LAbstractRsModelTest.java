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
package de.cesr.lara.testing.toolbox.adapater.rs;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Sascha Holzhauer
 *
 */
public class LAbstractRsModelTest {
	
	
	private final String	RS_CONFIGURATION		= "/config/rs/testing.rs";
	private final String	BATCH_CONFIGURATION		= "/config/batch/testing.xml";
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		// run configuration
		String[] args = new String[] { "-interactive", "-params",
				System.getenv("ProjectDir") + BATCH_CONFIGURATION,
				System.getenv("ProjectDir") + RS_CONFIGURATION };

		try {
			repast.simphony.batch.BatchMain.main(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
