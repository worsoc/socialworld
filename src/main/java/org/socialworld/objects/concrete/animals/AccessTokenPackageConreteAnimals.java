package org.socialworld.objects.concrete.animals;
import org.socialworld.core.IAccessToken;

public final class AccessTokenPackageConreteAnimals implements IAccessToken
{

	private static AccessTokenPackageConreteAnimals valid;
	
	static AccessTokenPackageConreteAnimals getValid() {
		if (valid == null) {
			valid = new AccessTokenPackageConreteAnimals();
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
}

