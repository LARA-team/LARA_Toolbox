/**
 * 
 */
package de.cesr.lara.toolbox.config.xml;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.filter.Filter;
import org.simpleframework.xml.filter.PlatformFilter;
import org.simpleframework.xml.transform.Matcher;
import org.simpleframework.xml.transform.RegistryMatcher;

import de.cesr.lara.components.LaraPreference;
import de.cesr.lara.components.decision.LaraDecisionConfiguration;
import de.cesr.lara.components.model.LaraModel;
import de.cesr.lara.components.model.LaraModelResetObserver;
import de.cesr.lara.components.model.impl.LModel;

/**
 * @author sholzhau
 *
 */
public class LPersister extends Persister {

	/**
	 * Logger
	 */
	static private Logger logger = Logger.getLogger(LPersister.class);

	static Map<Object, LPersister> persisters = new HashMap<Object, LPersister>();

	{
		LModel.registerResetObserver(new LaraModelResetObserver() {

			@Override
			public void getNotified() {
				LPersister.reset();
			}

			@Override
			public void getNotified(Object id) {
				LPersister.reset(id);
			}
		});
	}

	protected LaraContextulisedPathProvider pathProvider;


	private LPersister(Filter filter, Matcher matcher) {
		super(filter, matcher);
		this.pathProvider = new LaraContextulisedPathProvider() {
			@Override
			public String getPath(String filename) {
				return filename;
			}
		};
	}

	/**
	 * NOTE: THe persister is not accessible from {@link LaraModel} because of LARA_Base's independence from LARA_Tools.
	 * 
	 * @param id
	 *        id to identify {@link LaraModel}
	 * 
	 * @return persister
	 */
	static public LPersister getPersister(Object id) {
		return getPersister(new PlatformFilter(), id);
	}

	/**
	 * NOTE: If the persister has been requested before, even without the (same) filter, the old one is returned! NOTE:
	 * The persister is not accessible from {@link LaraModel} because of LARA_Base's independence from LARA_Tools.
	 * 
	 * @param filter
	 * @param id
	 *        id to identify {@link LaraModel}
	 * 
	 * @return persister
	 */
	static public LPersister getPersister(Filter filter, Object id) {
		if (LPersister.persisters.get(id) == null) {
			RegistryMatcher matcher = new RegistryMatcher();
			matcher.bind(LaraPreference.class,
					new LPreferenceLookupTransformer(id));
			matcher.bind(LaraDecisionConfiguration.class,
					new LDConfigLookupTransformer(id));
			LPersister.persisters.put(id, new LPersister(filter, matcher));
		}
		return LPersister.persisters.get(id);
	}

	/**
	 * Removes the persister associated with the given id from the list of
	 * registered persisters.
	 * 
	 * @param id
	 */
	static public void reset(Object id) {
		persisters.remove(id);
	}

	/**
	 * Clears the list of persisters.
	 */
	static public void reset() {
		persisters.clear();
	}

	public void setPathProvider(LaraContextulisedPathProvider pathProvider) {
		this.pathProvider = pathProvider;
	}

	/**
	 * Reads an XML file relative to the persister's base directory. This is
	 * almost certainly the function you want for reading XML
	 * 
	 * @param <T>
	 * @param type
	 * @param filename
	 * @return T
	 * @throws Exception
	 */
	public <T> T readXML(Class<? extends T> type, String filename)
			throws Exception {
		try {
			return read(type, new File(pathProvider.getPath(filename)));
		} catch (Exception e) {
			logger.error(
					"Couldn't read file '" + pathProvider.getPath(filename)
 + "' for class " + type.getSimpleName()
			                + ". Was setPathProvider() called?", e);
		}
		return null;
	}
}
