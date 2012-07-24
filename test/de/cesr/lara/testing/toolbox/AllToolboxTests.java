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
package de.cesr.lara.testing.toolbox;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.cesr.lara.testing.toolbox.adapater.rs.AllAdapterRsTests;
import de.cesr.lara.testing.toolbox.preprocessor.AllPreprocessorToolboxTests;


/**
 * 
 * @author Sascha Holzhauer
 * @date 10.02.2010
 * 
 */
@RunWith(Suite.class)
@SuiteClasses( { AllPreprocessorToolboxTests.class, AllAdapterRsTests.class })
public class AllToolboxTests {

}
