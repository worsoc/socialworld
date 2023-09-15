package org.socialworld.objects.statics;

import org.socialworld.Constants;
import org.socialworld.objects.enums.EnumBaseSimObj;
import org.socialworld.objects.enums.EnumBird;
import org.socialworld.objects.enums.EnumFood;
import org.socialworld.objects.enums.EnumHumanCrafted;
import org.socialworld.objects.enums.EnumLiquid;
import org.socialworld.objects.enums.EnumMammal;

public class GetLexemIDHigherPartFromMapping {

	public static int getForClassName(String className) {
		
		{
			EnumBaseSimObj key = EnumBaseSimObj.getName(className);
			if (key != EnumBaseSimObj.ignore) {
				return Mapping_Base2LexemIDHigherPart.getInstance().get(key);
			}
		}

		
		{
			EnumBird key = EnumBird.getName(className);
			if (key != EnumBird.ignore) {
				return Mapping_Base2LexemIDHigherPart.getInstance().get(EnumBaseSimObj.Bird);
			}
		}
		{
			EnumFood key = EnumFood.getName(className);
			if (key != EnumFood.ignore) {
				return Mapping_Base2LexemIDHigherPart.getInstance().get(EnumBaseSimObj.Food);
			}
		}
		{
			EnumHumanCrafted key = EnumHumanCrafted.getName(className);
			if (key != EnumHumanCrafted.ignore) {
				return Mapping_Base2LexemIDHigherPart.getInstance().get(EnumBaseSimObj.HumanCrafted);
			}
		}
		{
			EnumLiquid key = EnumLiquid.getName(className);
			if (key != EnumLiquid.ignore) {
				return Mapping_Base2LexemIDHigherPart.getInstance().get(EnumBaseSimObj.Liquid);
			}
		}
		{
			EnumMammal key = EnumMammal.getName(className);
			if (key != EnumMammal.ignore) {
				return Mapping_Base2LexemIDHigherPart.getInstance().get(EnumBaseSimObj.Mammal);
			}
		}
		

		return Constants.MAPPING_NO_ENTRY_FOR_KEY;
	}
}
