/**
 * 
 */
package de.cesr.lara.toolbox.config.xml;

/**
 * Provides the full path for the given filename. Indented for use with
 * {@link LPersister}.
 * 
 * @author Sascha Holzhauer
 * 
 */
public interface LaraContextulisedPathProvider {

	/**
	 * @param filename
	 * @return full path including filename
	 */
	public String getPath(String filename);
}
