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
import org.simpleframework.xml.ElementList;

import de.cesr.lara.components.LaraBehaviouralOption;
import de.cesr.lara.components.agents.LaraAgent;


/**
 * Applied to deserialise {@link LaraBehaviouralOption} that provide a setter for a parameter map
 * {@link ParameterMapAssignableBo}.
 * 
 * @author Sascha Holzhauer
 * 
 */
public class LParameterMapBoFactory extends LBoFactory {

	@Element(required = true)
	String classname = null;

	@ElementList(required = false, entry = "parameter")
	Map<String, Object> parameters = new HashMap<>();

	@Attribute(required = true, name = "key")
	String key = null;

	public LParameterMapBoFactory() {
	}

	/**
	 * @param lbc
	 * @return potential option
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 */
	public LaraBehaviouralOption<?, ?> assemblePo(LaraAgent<?,?> lbc)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {

		LaraBehaviouralOption<?, ?> bo = (LaraBehaviouralOption<?, ?>)  Class
				.forName(classname)
						.getConstructor(String.class, LaraAgent.class, Map.class)
						.newInstance(this.key, lbc, this.preferenceWeights);
		
		if (bo instanceof ParameterMapAssignableBo) {
			((ParameterMapAssignableBo)bo).setParameterMap(parameters);
		}
		return bo;
	}
}
