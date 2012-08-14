/**
 * 
 */
package de.cesr.lara.toolbox.adapter.rs.context;

import repast.simphony.context.Context;
import de.cesr.lara.components.environment.LaraEnvironment;

/**
 * Interface for Repast Simphony contexts that shall be uses as {@link LaraEnvironment}.
 *  
 * @author Sascha Holzhauer
 *
 */
public interface LaraEnvironmentContext<T> extends Context<T>, LaraEnvironment {

}
