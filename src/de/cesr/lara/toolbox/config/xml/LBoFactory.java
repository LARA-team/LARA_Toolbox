/**
 * This file is part of
 * 
 * CRAFTY - Competition for Resources between Agent Functional TYpes
 *
 * Copyright (C) 2015 School of GeoScience, University of Edinburgh, Edinburgh, UK
 * 
 * CRAFTY is free software: You can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *  
 * CRAFTY is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * School of Geoscience, University of Edinburgh, Edinburgh, UK
 * 
 * Created by Sascha Holzhauer on 5 Jun 2015
 */
package de.cesr.lara.toolbox.config.xml;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;

import de.cesr.lara.components.LaraBehaviouralOption;
import de.cesr.lara.components.LaraPreference;
import de.cesr.lara.components.agents.LaraAgent;

/**
 * Applied to deserialise {@link LaraBehaviouralOption}s.
 * 
 * @author Sascha Holzhauer
 * 
 */
public class LBoFactory {

	@Attribute(required = true)
	String classname = null;

	@ElementMap(name = "preferenceUtilities", entry = "utility", key = "pref", attribute = true, inline = false, required = false)
	Map<LaraPreference, Double> preferenceWeights = new HashMap<LaraPreference, Double>();

	@Element(required = true, name = "key")
	String key = null;

	@Element(required = false)
	String agentclassname = null;

	public LBoFactory() {
	}

	/**
	 * @param lbc
	 * @param modelId
	 * @return potential option
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 */
	public LaraBehaviouralOption<?, ?> assembleBo(LaraAgent<?, ?> lbc,
			Object modelId)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {

		Class<?> agentclass = LaraAgent.class;
		if (agentclassname != null) {
			agentclass = Class.forName(agentclassname);
		}
		return (LaraBehaviouralOption<?, ?>) Class
				.forName(classname)
				.getConstructor(String.class, agentclass, Map.class)
				.newInstance(this.key, lbc, preferenceWeights);
	}
}
