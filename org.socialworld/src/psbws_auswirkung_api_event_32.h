// PSBWS_Auswirkung_API_Event_32.h: Schnittstelle für die Klasse PSBWS_Auswirkungs_API_Event_32.
//
//////////////////////////////////////////////////////////////////////

#ifndef _PSBWS_AUSWIRKUNG_API_EVENT_32_H__
#define _PSBWS_AUSWIRKUNG_API_EVENT_32_H__

#include "psbws_auswirkung_api.h"

class PSBWS_AttributeChanger;

class PSBWS_Effect_API_Event_32 : public PSBWS_Effect_API  
{
public:
	PSBWS_Effect_API_Event_32();
	virtual ~PSBWS_Effect_API_Event_32();
protected:
  virtual void create_pipeline();
  virtual void destroy_pipeline();

  virtual void calculate_type_1(PSBWS_SimulationObject*);
	virtual void calculate_type_2(PSBWS_SimulationObject*);
	virtual void calculate_type_3(PSBWS_SimulationObject*);
	virtual void calculate_type_4(PSBWS_SimulationObject*);
	virtual void calculate_type_5(PSBWS_SimulationObject*);
	virtual void calculate_type_6(PSBWS_SimulationObject*);
	virtual void calculate_type_7(PSBWS_SimulationObject*);
	virtual void calculate_type_8(PSBWS_SimulationObject*);
	virtual void calculate_type_9(PSBWS_SimulationObject*);
	virtual void calculate_type_10(PSBWS_SimulationObject*);
	virtual void calculate_type_11(PSBWS_SimulationObject*);
	virtual void calculate_type_12(PSBWS_SimulationObject*);
	virtual void calculate_type_13(PSBWS_SimulationObject*);

  PSBWS_AttributeChanger* mP_firstAttributeChanger_Type1;
  PSBWS_AttributeChanger* mP_firstAttributeChanger_Type2;
  PSBWS_AttributeChanger* mP_firstAttributeChanger_Type3;
  PSBWS_AttributeChanger* mP_firstAttributeChanger_Type4;
  PSBWS_AttributeChanger* mP_firstAttributeChanger_Type5;
  PSBWS_AttributeChanger* mP_firstAttributeChanger_Type6;
  PSBWS_AttributeChanger* mP_firstAttributeChanger_Type7;
  PSBWS_AttributeChanger* mP_firstAttributeChanger_Type8;
  PSBWS_AttributeChanger* mP_firstAttributeChanger_Type9;
  PSBWS_AttributeChanger* mP_firstAttributeChanger_Type10;
  PSBWS_AttributeChanger* mP_firstAttributeChanger_Type11;
  PSBWS_AttributeChanger* mP_firstAttributeChanger_Type12;
  PSBWS_AttributeChanger* mP_firstAttributeChanger_Type13;
};

#endif
