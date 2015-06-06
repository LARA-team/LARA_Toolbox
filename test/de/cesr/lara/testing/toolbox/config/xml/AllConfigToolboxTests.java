/**
 * LARA - Lightweight Architecture for boundedly Rational citizen Agents
 *
 * Center for Environmental Systems Research, Kassel
 * Created by Sascha Holzhauer on 17.02.2010
 */
package de.cesr.lara.testing.toolbox.config.xml;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ XmlBosListLoadingTest.class, XmlLAgentConfiguratorTest.class,
		XmlDConfigListLoadingTest.class })
public class AllConfigToolboxTests {
}