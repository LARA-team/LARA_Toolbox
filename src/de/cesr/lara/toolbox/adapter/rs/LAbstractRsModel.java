/**
 * This file is part of
 * 
 * LARA - Lightweight Architecture for boundedly Rational citizen Agents
 * 
 * Copyright (C) 2012 Center for Environmental Systems Research, Kassel, Germany
 * 
 * LARA is free software: You can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * LARA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.cesr.lara.toolbox.adapter.rs;

import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.random.RandomHelper;
import de.cesr.lara.components.LaraBehaviouralOption;
import de.cesr.lara.components.agents.LaraAgent;
import de.cesr.lara.components.eventbus.LaraEventSubscriber;
import de.cesr.lara.components.eventbus.events.LModelFinishEvent;
import de.cesr.lara.components.eventbus.events.LModelInstantiatedEvent;
import de.cesr.lara.components.eventbus.events.LModelStepEvent;
import de.cesr.lara.components.eventbus.events.LaraEvent;
import de.cesr.lara.components.model.LaraModel;
import de.cesr.lara.components.model.impl.LAbstractModel;

/**
 * For Repast Simphony Models, a context builder (a class implementing
 * {@link ContextBuilder} builds up the model (at least its main context) and schedules actions.
 * 
 * Therefore, the {@link LAbstractRsModel} serves as
 * <ul>
 * <li>Implementation of {@link LaraModel}</li>
 * <li>LARA model controller</li>
 * <li>Repast Simphony {@link ContextBuilder}.
 * </ul>
 * 
 * Instructions:
 * Call <code>LEventbus.getInstance().publish(new LModelInstantiatedEvent());</code> 
 * in your {@link ContextBuilder#build(repast.simphony.context.Context)} method!
 * 
 * NOTE: Your context creation class MUST implement ContextBuilder<Object> at its own
 * since Repast Simphony is not able (why so ever) to check whether super-classes extend/implement the
 * interface!!
 *
 * @author Sascha Holzhauer
 *
 * @param <A> Agent class
 * @param <BO> BO class
 * @param <C> Type the root context is suitable for (mostly Object)
 */
public abstract class LAbstractRsModel<A extends LaraAgent<A, BO>, BO extends LaraBehaviouralOption<?, ? extends BO>, C> 
	extends LAbstractModel implements LaraEventSubscriber, ContextBuilder<C>{

	
	/**
	 * @see de.cesr.lara.components.model.impl.LAbstractModel#onInternalEvent(de.cesr.lara.components.eventbus.events.LaraEvent)
	 */
	@Override
	public void onInternalEvent(LaraEvent event) {
		super.onInternalEvent(event);
		if (event instanceof LModelInstantiatedEvent) {
			this.initRsModel();
		}
	}

	/**
	 * This method should be called in your {@link ContextBuilder#build(repast.simphony.context.Context)}!
	 * 
	 * Schedules methods at RS schedule and publishes a {@link LModelInstantiatedEvent}.
	 * Sets consistent random seed.
	 */
	protected void initRsModel() {
		RunEnvironment.getInstance().getCurrentSchedule().schedule(this);
		RunEnvironment
				.getInstance()
				.getCurrentSchedule()
				.schedule(
						ScheduleParameters
								.createAtEnd(ScheduleParameters.LAST_PRIORITY),
						this, "finish");
		RandomHelper.setSeed(getLRandom().getSeed());
	}
	
	/****************************************************
	 * MODEL CONTROLLER METHODS                         *
	 ****************************************************/
	
	/**
	 * Anchor method for Repast Simphony to schedule the simulation.
	 */
	@ScheduledMethod(start = 1, interval = 1)
	public void stepIt() {
		eventBus.publish(new LModelStepEvent());
	}

	@ScheduledMethod(start = ScheduleParameters.END, priority=ScheduleParameters.LAST_PRIORITY)
	public void finish() {
		eventBus.publish(new LModelFinishEvent());
	}
	
	/****************************************************
	 * ABSTRACT METHODS                                 *
	 ****************************************************/

	/**
	 * Returns an object that may iterate over all agents.
	 * 
	 * @return iterable over agents
	 */
	protected abstract Iterable<? extends LaraAgent<A, BO>> getAgentIterable();
}
