/**
 * 
 */
package de.cesr.lara.toolbox.config.xml;


import java.util.Map;

import de.cesr.lara.components.LaraBehaviouralOption;


/**
 * For {@link LaraBehaviouralOption}s.
 * 
 * @author Sascha Holzhauer
 * 
 */
public interface ParameterMapAssignableBo {

	public void setParameterMap(Map<String, Object> map);

}
