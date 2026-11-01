package org.socialworld.tools.mct;

import java.util.Arrays;

/**
 * Die Regel für das Aneinanderlegen von Rastern: Zwei Ränder passen zusammen,
 * wenn sie sich nur um eine konstante Höhe unterscheiden.
 * Ein Rand ist eine Folge von Eckhöhen, wie sie BorderCalculator liefert.
 */
public final class EdgeProfile {

    private EdgeProfile() {
    }

    /**
     * Normiert eine Höhenlinie, sodass sie immer bei 0 beginnt.
     */
    public static int[] normalize(int[] profile) {
        if (profile == null || profile.length == 0) return null;
        int[] normalized = new int[profile.length];
        int baseValue = profile[0];
        for (int i = 0; i < profile.length; i++) {
            normalized[i] = profile[i] - baseValue;
        }
        return normalized;
    }

    /**
     * Prüft, ob zwei Profile nach der Normierung exakt übereinstimmen.
     */
    public static boolean matches(int[] profileA, int[] profileB) {
        if (profileA == null || profileB == null) return false;
        return Arrays.equals(normalize(profileA), normalize(profileB));
    }

    /**
     * @return die Höhe, um die profile verschoben werden muss, damit es target entspricht,
     *         oder null, wenn die beiden Profile nicht zusammenpassen
     */
    public static Integer heightShift(int[] profile, int[] target) {
        if (!matches(profile, target) || profile.length == 0) return null;
        return target[0] - profile[0];
    }
}
