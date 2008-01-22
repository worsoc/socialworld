/**
 * 
 */
package org.socialworld.core;

import java.util.List;

import org.apache.log4j.Logger;
import org.socialworld.objects.Human;

/**
 * @author Andre Schade
 * 
 */
public class ObjectManager {
	
	private static final Logger logger = Logger.getLogger(ObjectManager.class.getName());

	private static ObjectManager manager = null;

	Simulation simulation;

	private ObjectManager() {
		logger.debug("create simulation object");
		this.simulation = new Simulation();
	}

	/**
	 * Returns the {@link ObjectManager} instance.
	 * 
	 * @return {@link ObjectManager} instance
	 */
	public static ObjectManager getCurrent() {
		if (manager == null) {
			manager = new ObjectManager();
		}
		return manager;
	}

	public Simulation getSimulation() {
		return this.simulation;
	}

	public EventMaster getEventMaster() {
		return this.simulation.getEventMaster();
	}

	public List<Human> getHumans() {
		return this.simulation.getHumans();
	}
}

// class PSBWS_Object_Manager
// {
// public:
// PSBWS_Object_Manager();
// virtual ~PSBWS_Object_Manager();
//
// Magic getMagic(long magicNumber);
// ManagerEffectAPI getManagerEffectAPI();
// ManagerReactionAPI getManagerReactionAPI();
// ActionCoderHuman getActionCoderHuman();
//  
//  
// protected:
// PSBWS_Effect_API_Manager* mP_manager_effectAPI;
// PSBWS_Reaction_API_Manager* mP_manager_reactionAPI;
// PSBWS_ActionCoder_Human* mP_actionCoder_human;
// };

// PSBWS_Object_Manager::PSBWS_Object_Manager()
// {
//	
// mP_simulation = new PSBWS_Simulation(this);
// mP_manager_effectAPI = new PSBWS_Effect_API_Manager();
// mP_manager_reactionAPI = new PSBWS_Reaction_API_Manager();
// mP_actionCoder_human = new PSBWS_ActionCoder_Human();
//
// }
//

// //////////////////////////////////////////////////////////////////////
//
// PSBWS_Effect_API_Manager* PSBWS_Object_Manager::get_manager_effectAPI()
// {
// return mP_manager_effectAPI;
// }
//
// //////////////////////////////////////////////////////////////////////
//
// PSBWS_Reaction_API_Manager* PSBWS_Object_Manager::get_manager_reactionAPI()
// {
// return mP_manager_reactionAPI;
// }
//

// /////////////////////////////////////////////////////////////////////
//
// PSBWS_ActionCoder_Human* PSBWS_Object_Manager::get_actionCoder_human()
// {
// return mP_actionCoder_human;
// }
