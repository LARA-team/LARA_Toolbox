/**
 * 
 */
package de.cesr.lara.toolbox.config.xml;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import de.cesr.lara.components.LaraBehaviouralOption;
import de.cesr.lara.components.LaraPreference;
import de.cesr.lara.components.agents.LaraAgent;
import de.cesr.lara.components.container.exceptions.LContainerFullException;
import de.cesr.lara.components.container.exceptions.LInvalidTimestampException;
import de.cesr.lara.components.preprocessor.LaraPreprocessorConfigurator;
import de.cesr.lara.components.preprocessor.impl.LPreprocessorConfigurator;
import de.cesr.lara.toolbox.config.LaraAgentConfigurator;

/**
 * @author Sascha Holzhauer
 * @param <A>
 * @param <BO>
 * 
 */
@Root(name = "agentConfigurator")
public class LXmlAgentConfigurator<A extends LaraAgent<A, BO>, BO extends LaraBehaviouralOption<?, ? extends BO>>
		implements LaraAgentConfigurator<A, BO> {


	/**
	 * Location of XML parameter file for BOs (it is possible to have several BO
	 * configuration files).
	 */
	@ElementList(required = false, inline = true, entry = "bofile")
	List<String> boFileList = new ArrayList<String>();

	@ElementMap(entry = "preferenceWeight", key = "pref", attribute = true, required = false, inline = true)
	Map<LaraPreference, Double> preferenceWeights = new HashMap<LaraPreference, Double>();

	@Element(name = "preprocessorConfigurator", required = false, type = LPreprocessorConfigurator.class)
	LaraPreprocessorConfigurator<A, BO> ppConfigurator = LPreprocessorConfigurator.getNewPreprocessorConfigurator();

	Set<LBoFactory> boFactories = new HashSet<LBoFactory>();

	Object modelId = null;

	public void load(Object id) throws Exception {
		this.modelId = id;
		loadBoFactories(this.modelId);
	}

	/**
	 * @param id
	 * @throws Exception
	 */
	protected void loadBoFactories(Object id) throws Exception {
		for (String boFile : boFileList) {
			boFactories.addAll(LPersister.getPersister(id)
					.readXML(LBoFactoryList.class, boFile).getBoFactories());
		}
	}

	/**
	 * @see de.cesr.lara.toolbox.config.LaraAgentConfigurator#configure(de.cesr.lara.components.agents.LaraAgent)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void configure(A agent) {
		for (LBoFactory factory : boFactories) {
			try {
				agent.getLaraComp().getBOMemory()
						.memorize((BO) factory.assembleBo(agent, modelId));
			} catch (LContainerFullException exception) {
				exception.printStackTrace();
			} catch (LInvalidTimestampException exception) {
				exception.printStackTrace();
			} catch (InstantiationException exception) {
				exception.printStackTrace();
			} catch (IllegalAccessException exception) {
				exception.printStackTrace();
			} catch (IllegalArgumentException exception) {
				exception.printStackTrace();
			} catch (InvocationTargetException exception) {
				exception.printStackTrace();
			} catch (NoSuchMethodException exception) {
				exception.printStackTrace();
			} catch (SecurityException exception) {
				exception.printStackTrace();
			} catch (ClassNotFoundException exception) {
				exception.printStackTrace();
			}
		}


		agent.getLaraComp().addPreferenceWeights(preferenceWeights);
		agent.getLaraComp().setPreprocessor(ppConfigurator.getPreprocessor());
	}
}
