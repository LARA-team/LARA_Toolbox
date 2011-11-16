/**
 * LARA - Lightweight Architecture for boundedly Rational citizen Agents
 *
 * Center for Environmental Systems Research, Kassel
 * Created by Sascha Holzhauer on 10.02.2010
 */
package de.cesr.lara.testing.toolbox;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.cesr.lara.testing.toolbox.preprocessor.AllPreprocessorToolboxTests;


/**
 * 
 * @author Sascha Holzhauer
 * @date 10.02.2010
 * 
 */
@RunWith(Suite.class)
@SuiteClasses( { AllPreprocessorToolboxTests.class })
public class AllToolboxTests {

}
