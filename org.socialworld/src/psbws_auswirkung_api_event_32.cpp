// PSBWS_Auswirkung_API_Event_32.cpp: Implementierung der Klasse PSBWS_Auswirkungs_API_Event_32.
//
//////////////////////////////////////////////////////////////////////

#include "psbws_auswirkung_api_event_32.h"
#include "psbws_attributechanger.h"

PSBWS_Effect_API_Event_32::PSBWS_Effect_API_Event_32()
{
  create_pipeline();
}

PSBWS_Effect_API_Event_32::~PSBWS_Effect_API_Event_32()
{
  destroy_pipeline();
}

void PSBWS_Effect_API_Event_32::create_pipeline()
{
  mP_firstAttributeChanger_Type1 = new PSBWS_IllnessChanger_Type1(0);
  mP_firstAttributeChanger_Type2 = new PSBWS_IllnessChanger_Type1(0);
  mP_firstAttributeChanger_Type3 = new PSBWS_IllnessChanger_Type1(0);
  mP_firstAttributeChanger_Type4 = new PSBWS_IllnessChanger_Type1(0);
  mP_firstAttributeChanger_Type5 = new PSBWS_IllnessChanger_Type1(0);
  mP_firstAttributeChanger_Type6 = new PSBWS_IllnessChanger_Type1(0);
  mP_firstAttributeChanger_Type7 = new PSBWS_IllnessChanger_Type1(0);
  mP_firstAttributeChanger_Type8 = new PSBWS_IllnessChanger_Type1(0);
  mP_firstAttributeChanger_Type9 = new PSBWS_IllnessChanger_Type1(0);
  mP_firstAttributeChanger_Type10 = new PSBWS_IllnessChanger_Type1(0);
  mP_firstAttributeChanger_Type11 = new PSBWS_IllnessChanger_Type1(0);
  mP_firstAttributeChanger_Type12 = new PSBWS_IllnessChanger_Type1(0);
  mP_firstAttributeChanger_Type13 = new PSBWS_IllnessChanger_Type1(0);
}

void PSBWS_Effect_API_Event_32::destroy_pipeline()
{
  delete mP_firstAttributeChanger_Type1;
  delete mP_firstAttributeChanger_Type2;
  delete mP_firstAttributeChanger_Type3;
  delete mP_firstAttributeChanger_Type4;
  delete mP_firstAttributeChanger_Type5;
  delete mP_firstAttributeChanger_Type6;
  delete mP_firstAttributeChanger_Type7;
  delete mP_firstAttributeChanger_Type8;
  delete mP_firstAttributeChanger_Type9;
  delete mP_firstAttributeChanger_Type10;
  delete mP_firstAttributeChanger_Type11;
  delete mP_firstAttributeChanger_Type12;
  delete mP_firstAttributeChanger_Type13;
}


void PSBWS_Effect_API_Event_32::calculate_type_1(PSBWS_SimulationObject* aP_object)
{
  mP_firstAttributeChanger_Type1->change_attribute(aP_object);
}

void PSBWS_Effect_API_Event_32::calculate_type_2(PSBWS_SimulationObject* aP_object)
{
  mP_firstAttributeChanger_Type2->change_attribute(aP_object);
}

void PSBWS_Effect_API_Event_32::calculate_type_3(PSBWS_SimulationObject* aP_object)
{
  mP_firstAttributeChanger_Type3->change_attribute(aP_object);
}

void PSBWS_Effect_API_Event_32::calculate_type_4(PSBWS_SimulationObject* aP_object)
{
  mP_firstAttributeChanger_Type4->change_attribute(aP_object);
}

void PSBWS_Effect_API_Event_32::calculate_type_5(PSBWS_SimulationObject* aP_object)
{
  mP_firstAttributeChanger_Type5->change_attribute(aP_object);
}

void PSBWS_Effect_API_Event_32::calculate_type_6(PSBWS_SimulationObject* aP_object)
{
  mP_firstAttributeChanger_Type6->change_attribute(aP_object);
}

void PSBWS_Effect_API_Event_32::calculate_type_7(PSBWS_SimulationObject* aP_object)
{
  mP_firstAttributeChanger_Type7->change_attribute(aP_object);
}

void PSBWS_Effect_API_Event_32::calculate_type_8(PSBWS_SimulationObject* aP_object)
{
  mP_firstAttributeChanger_Type8->change_attribute(aP_object);
}

void PSBWS_Effect_API_Event_32::calculate_type_9(PSBWS_SimulationObject* aP_object)
{
  mP_firstAttributeChanger_Type9->change_attribute(aP_object);
}

void PSBWS_Effect_API_Event_32::calculate_type_10(PSBWS_SimulationObject* aP_object)
{
  mP_firstAttributeChanger_Type10->change_attribute(aP_object);
}

void PSBWS_Effect_API_Event_32::calculate_type_11(PSBWS_SimulationObject* aP_object)
{
  mP_firstAttributeChanger_Type11->change_attribute(aP_object);
}

void PSBWS_Effect_API_Event_32::calculate_type_12(PSBWS_SimulationObject* aP_object)
{
  mP_firstAttributeChanger_Type12->change_attribute(aP_object);
}

void PSBWS_Effect_API_Event_32::calculate_type_13(PSBWS_SimulationObject* aP_object)
{
  mP_firstAttributeChanger_Type13->change_attribute(aP_object);
}

