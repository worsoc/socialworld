package org.socialworld.tools.pct;

/**
 * The texts of the dialog that say something about a perception.
 */
final class PerceptionSelectionText {

	private static final String MARK_OUTSIDE = "   [not in the meta information]";

	private PerceptionSelectionText() {
	}

	static String formatPath(PerceptionPath path, boolean offeredByCatalog) {

		StringBuilder text = new StringBuilder();
		text.append(PerceptionPathTableModel.kindOf(path)).append(": ");
		text.append(path.getValueName().isEmpty() ? "(no name)" : path.getValueName());
		if (!path.getResultType().isEmpty()) {
			text.append(" : ").append(path.getResultType());
		}
		if (!offeredByCatalog) {
			text.append(MARK_OUTSIDE);
		}
		return text.toString();
	}

	static String formatStatus(PerceptionEditor editor) {

		if (!editor.isEditable()) {
			return "Can only be looked at: " + editor.getReasonNotEditable();
		}
		if (editor.isChanged()) {
			return "Not saved yet";
		}
		return editor.isFilled(editor.getSlot()) ? "Saved" : "This slot is empty";
	}
}
