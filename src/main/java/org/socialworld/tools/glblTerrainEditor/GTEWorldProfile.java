package org.socialworld.tools.glblTerrainEditor;

/**
 * Definiert die vier wählbaren Welt-Kataloge für den Generator.
 * Hält die Anzeigenamen für die GUI-Auswahlbox bereit.
 */
public enum GTEWorldProfile {
    KONTINENTAL("Standard Kontinental (Viel Land)"),
    ARCHIPEL("Inselwelt / Archipel"),
    OEDLAND("Endzeit / Ödland (Stein & Asche)"),
    NORDISCH("Schroffes Norden (Schnee & Moos)");

    private final String displayName;

    GTEWorldProfile(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
