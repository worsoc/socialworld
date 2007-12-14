/***************************************************************************
                          psbws_aktionsschlange.cpp  -  description
                             -------------------
    begin                : Sun Jun 22 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#include "psbws_aktionsschlange.h"

#include "psbws_objekt_manager.h"

#include "psbws_gott.h"


PSBWS_ActionQueue::PSBWS_ActionQueue(PSBWS_Object_Manager* aP_objectManager, TPositive au_max_count)
{
			mP_objectManager = aP_objectManager;
			mSM_priorityQueues = new SM_IndexListe<STR_TimeQueue>(au_max_count);
      STR_TimeChainElement* lT_timeChainElement = new STR_TimeChainElement;
      lT_timeChainElement->u_time = 9999999;
      lT_timeChainElement->STR_timeQueue = NULL;

			mSM_timeChain = new SM_KetteMitKopf<STR_TimeChainElement>(0,lT_timeChainElement);

}

PSBWS_ActionQueue::~PSBWS_ActionQueue()

{
}

int PSBWS_ActionQueue::insert(TNatural au_time, TUChar au_priority, STR_ActionElement* aT_actionElement)
{
			STR_TimeQueue* lSTR_timeQueue = mSM_priorityQueues->getElement(au_time);
      if (lSTR_timeQueue != 0)
			{
					lSTR_timeQueue->SM_priorityQueue->insert(au_priority, aT_actionElement);
					return 0;
			}

			lSTR_timeQueue = new STR_TimeQueue;
      lSTR_timeQueue->SM_priorityQueue = new SM_PrioritaetsSchlange<STR_ActionElement>();
			lSTR_timeQueue->u_time = au_time;
			lSTR_timeQueue->SM_priorityQueue->insert(au_priority, aT_actionElement);
      mSM_priorityQueues->insert(au_time, lSTR_timeQueue);

			STR_TimeChainElement* lT_timeChainElement
        = new STR_TimeChainElement;
      lT_timeChainElement->u_time = au_time;
      lT_timeChainElement->STR_timeQueue = lSTR_timeQueue;

      if (mSM_timeChain->getListElementActual() != 0)
      if (mSM_timeChain->getActual()->u_time < au_time)
			{
					while(true)
          {
					      if (mSM_timeChain->getListElementActual() == 0)
                {
										lSTR_timeQueue->SM_time_in_chain =
                      mSM_timeChain->insertNext(lT_timeChainElement);
                    break;
                }
                if (mSM_timeChain->getListElementActual()->m_pData->u_time >
                    au_time) 								{
                        lSTR_timeQueue->SM_time_in_chain =
                           mSM_timeChain->insertPrev(lT_timeChainElement);
                        break;
                }
                else
										if (mSM_timeChain->next() == false) {
                        lSTR_timeQueue->SM_time_in_chain =
                           mSM_timeChain->insertNext(lT_timeChainElement);
                        break;
                        }
           }
           return 0;
			}
			else
			{
					while(true)
          {
                if (mSM_timeChain->getListElementActual() == 0)
                {
										lSTR_timeQueue->SM_time_in_chain =
                       mSM_timeChain->insertPrev(lT_timeChainElement);
                    break;
								}

                if (mSM_timeChain->getListElementActual()->m_pData->u_time <  au_time) {
                      lSTR_timeQueue->SM_time_in_chain =
                        mSM_timeChain->insertNext(lT_timeChainElement);
                      break;
                }
                else
										if (mSM_timeChain->prev() == false)  {
                      lSTR_timeQueue->SM_time_in_chain =
                         mSM_timeChain->insertPrev(lT_timeChainElement);
                      break;
                    }
          }
          return 0;
			}
      lSTR_timeQueue->SM_time_in_chain =
        mSM_timeChain->insertNext(lT_timeChainElement);
      return 0;
}

STR_ActionElement* PSBWS_ActionQueue::get_element()
{
      STR_ActionElement* lT_actionElement_result = 0;
      SM_ListenElement<STR_TimeChainElement>* lS_listElement;
			SM_ListenElement<STR_TimeChainElement>* lS_listElement2;

      lS_listElement = mSM_timeChain->getListElementHead();
			lS_listElement2 = mSM_priorityQueues->getElement(mP_objectManager->get_god()->get_time())->SM_time_in_chain;

			STR_ActionElement* lT_actionElement_head = mSM_timeChain->getHead()->STR_timeQueue->SM_priorityQueue->getElement();
			TUChar lu_priority_result = lT_actionElement_head->u_priority;

      STR_ActionElement* lT_actionElement;
			TUChar au_priority;

			while (lS_listElement != lS_listElement2)
      {
            lT_actionElement = lS_listElement->m_pData->STR_timeQueue->SM_priorityQueue->getElement();
						au_priority = lT_actionElement->u_priority;
						if ( au_priority > lu_priority_result)
        		{
				    		lu_priority_result = au_priority;
								lT_actionElement_result = lT_actionElement;
						}
						lS_listElement = lS_listElement->m_pNext;
      };

			lT_actionElement = lS_listElement->m_pData->STR_timeQueue->SM_priorityQueue->getElement();
			au_priority = lT_actionElement->u_priority;
			if ( au_priority > lu_priority_result)
    	{
		    		lu_priority_result = au_priority;
						lT_actionElement_result = lT_actionElement;
			}
      return lT_actionElement_result;
}
