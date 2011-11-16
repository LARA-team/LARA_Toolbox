/**
 * LARA - Lightweight Architecture for boundedly Rational citizen Agents
 *
 * Center for Environmental Systems Research, Kassel
 * Created by Sascha Holzhauer on 17.08.2010
 */
package de.cesr.lara.toolbox.adapter.rs;

import de.cesr.lara.components.agents.LaraAgent;
import de.cesr.lara.components.decision.LaraDecisionConfiguration;
import de.cesr.lara.components.model.impl.LAbstractModel;

/**
 * 
 */
public abstract class LAbstractRsModel extends LAbstractModel {

	public LAbstractRsModel() {
		super();
	}

	/**
	 * @see de.cesr.lara.components.model.impl.LAbstractModel#clean(de.cesr.lara.components.decision.LaraDecisionConfiguration)
	 */
	protected void clean(LaraDecisionConfiguration dConfiguration) {
		for (LaraAgent agent : getAgentIterable()) {
			// TODO repair
			// agent.clean(dConfiguration);
		}
	}

	/**
	 * @see de.cesr.lara.components.model.impl.LAbstractModel#decide(de.cesr.lara.components.decision.LaraDecisionConfiguration)
	 */
	protected void decide(LaraDecisionConfiguration dConfiguration) {
		for (LaraAgent agent : getAgentIterable()) {
			agent.getLaraComp().decide(dConfiguration);
		}
	}

	/**
	 * @see de.cesr.lara.components.model.impl.LAbstractModel#execute(de.cesr.lara.components.decision.LaraDecisionConfiguration)
	 */
	protected void execute(LaraDecisionConfiguration dConfiguration) {
		for (LaraAgent agent : getAgentIterable()) {
			// TODO repair
			// agent.laraExecute(dConfiguration);
		}
	}

	/**
	 * Returns an object that may iterate over all agents.
	 * 
	 * @return Created by Sascha Holzhauer on 18.02.2011
	 */
	protected abstract Iterable<? extends LaraAgent> getAgentIterable();

	/**
	 * @see de.cesr.lara.components.model.impl.LAbstractModel#perceive(de.cesr.lara.components.decision.LaraDecisionConfiguration)
	 */
	protected void perceive(LaraDecisionConfiguration dConfiguration) {
		for (LaraAgent agent : getAgentIterable()) {
			// TODO repair
			// agent.laraPerceive(dConfiguration);
		}
	}

	/**
	 * @see de.cesr.lara.components.model.impl.LAbstractModel#execute(de.cesr.lara.components.decision.LaraDecisionConfiguration)
	 */
	protected void postProcess(LaraDecisionConfiguration dConfiguration) {
		for (LaraAgent agent : getAgentIterable()) {
			// TODO repair
			// agent.laraPostProcess(dConfiguration);
		}
	}

	/**
	 * @see de.cesr.lara.components.model.impl.LAbstractModel#preProcess(de.cesr.lara.components.decision.LaraDecisionConfiguration)
	 */
	protected void preProcess(LaraDecisionConfiguration dConfiguration) {
		for (LaraAgent agent : getAgentIterable()) {
			// TODO repair
			// agent.getLaraComp().preProcess(dConfiguration);
		}
	}
}
