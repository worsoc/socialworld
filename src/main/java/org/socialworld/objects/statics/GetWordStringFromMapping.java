package org.socialworld.objects.statics;

import org.socialworld.objects.enums.EnumBaseSimObj;
import org.socialworld.objects.enums.EnumBird;
import org.socialworld.objects.enums.EnumFood;
import org.socialworld.objects.enums.EnumHumanCrafted;
import org.socialworld.objects.enums.EnumLiquid;
import org.socialworld.objects.enums.EnumMammal;

public class GetWordStringFromMapping {

	
	public static String getForClassName(String className) {
		
		{
			EnumBaseSimObj key = EnumBaseSimObj.getName(className);
			if (key != EnumBaseSimObj.ignore) {
				return Mapping_Base2WordString.getInstance().get(key);
			}
		}


		{
			EnumBird key = EnumBird.getName(className);
			if (key != EnumBird.ignore) {
				return Mapping_Bird2WordString.getInstance().get(key);
			}
		}
		{
			EnumFood key = EnumFood.getName(className);
			if (key != EnumFood.ignore) {
				return Mapping_Food2WordString.getInstance().get(key);
			}
		}
		{
			EnumHumanCrafted key = EnumHumanCrafted.getName(className);
			if (key != EnumHumanCrafted.ignore) {
				return Mapping_HumanCrafted2WordString.getInstance().get(key);
			}
		}
		{
			EnumLiquid key = EnumLiquid.getName(className);
			if (key != EnumLiquid.ignore) {
				return Mapping_Liquid2WordString.getInstance().get(key);
			}
		}
		{
			EnumMammal key = EnumMammal.getName(className);
			if (key != EnumMammal.ignore) {
				return Mapping_Mammal2WordString.getInstance().get(key);
			}
		}
		return "";
	}	
}
