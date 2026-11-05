package org.socialworld.tools.pct;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.socialworld.core.EventType;

/**
 * The perception that is being worked on, without any user interface.
 * Nothing reaches the store before save.
 */
final class PerceptionEditor {

	static final int DEFAULT_RELEVANCE_THRESHOLD = 100;

	private final PerceptionDescriptionStore store;
	private final PerceptionDescriptionComposer composer = new PerceptionDescriptionComposer();
	private final PerceptionDescriptionReader reader = new PerceptionDescriptionReader();

	private EventType eventType = EventType.nothing;
	private int slot;
	private String subject = "";
	private int relevanceThreshold = DEFAULT_RELEVANCE_THRESHOLD;
	private final List<PerceptionPath> paths = new ArrayList<PerceptionPath>();
	private Map<Integer, String> descriptionsOfEvent = Collections.emptyMap();
	private String reasonNotEditable = "";
	private boolean changed;

	PerceptionEditor(PerceptionDescriptionStore store) {
		this.store = store;
	}

	/**
	 * @param subjectIfEmpty the subject to start with, if the slot is empty
	 */
	void open(EventType eventType, int slot, String subjectIfEmpty) {

		this.eventType = eventType;
		this.slot = slot;
		this.subject = subjectIfEmpty;
		this.relevanceThreshold = DEFAULT_RELEVANCE_THRESHOLD;
		this.paths.clear();
		this.reasonNotEditable = "";
		this.changed = false;
		this.descriptionsOfEvent = store.loadAll(eventType.getIndex());

		String json = descriptionsOfEvent.get(slot);
		if (json != null) {
			PerceptionSelection selection = readForSlot(slot, json);
			if (selection.isEditable()) {
				this.subject = selection.getSubjectValueName();
				this.relevanceThreshold = selection.getRelevanceThreshold();
				this.paths.addAll(selection.getPaths());
			}
			else {
				this.reasonNotEditable = selection.getReasonNotEditable();
			}
		}
	}

	private PerceptionSelection readForSlot(int slot, String json) {

		PerceptionSelection selection = reader.read(json);

		if (selection.isEditable() && (selection.getEventType() != eventType || selection.getPerceptionType() != slot)) {
			return PerceptionSelection.notEditable("The JSON belongs to " + selection.getEventType()
					+ " / slot " + selection.getPerceptionType() + ", but is stored at " + eventType + " / slot " + slot);
		}
		return selection;
	}

	boolean isEditable() {
		return reasonNotEditable.isEmpty();
	}

	String getReasonNotEditable() {
		return reasonNotEditable;
	}

	boolean isChanged() {
		return changed;
	}

	int getSlot() {
		return slot;
	}

	EventType getEventType() {
		return eventType;
	}

	String getSubject() {
		return subject;
	}

	int getRelevanceThreshold() {
		return relevanceThreshold;
	}

	List<PerceptionPath> getPaths() {
		return Collections.unmodifiableList(paths);
	}

	boolean isFilled(int slot) {
		return descriptionsOfEvent.containsKey(slot);
	}

	boolean isSlotEditable(int slot) {
		String json = descriptionsOfEvent.get(slot);
		return json == null || readForSlot(slot, json).isEditable();
	}

	/**
	 * All paths of one criterion stay together, in the same order as in the JSON. A path that is
	 * already selected is left out.
	 *
	 * @return how many paths were added
	 */
	int add(Collection<PerceptionPath> newPaths) {

		requireEditable();

		int added = 0;
		for (PerceptionPath path : newPaths) {
			if (!paths.contains(path)) {
				paths.add(positionFor(path), path);
				added++;
			}
		}
		changed |= added > 0;
		return added;
	}

	private int positionFor(PerceptionPath path) {
		if (path.isProperty()) {
			for (int index = paths.size() - 1; index >= 0; index--) {
				if (paths.get(index).getCriterion() == path.getCriterion()) {
					return index + 1;
				}
			}
		}
		return paths.size();
	}

	void remove(Collection<PerceptionPath> oldPaths) {
		requireEditable();
		changed |= paths.removeAll(oldPaths);
	}

	void setRelevanceThreshold(int relevanceThreshold) {
		requireEditable();
		if (relevanceThreshold < 1) {
			throw new IllegalArgumentException("The threshold must be at least 1");
		}
		changed |= this.relevanceThreshold != relevanceThreshold;
		this.relevanceThreshold = relevanceThreshold;
	}

	/**
	 * All paths of a description start at its subject, so the subject can only change while none is selected.
	 *
	 * @return whether the subject is changed
	 */
	boolean setSubject(String subject) {
		if (subject.equals(this.subject)) return true;
		if (!isEditable() || !paths.isEmpty()) return false;

		this.subject = subject;
		return true;
	}

	/**
	 * @return the JSON as it would be saved for the opened slot; a description that is not editable as it is stored
	 */
	String getJson() {
		if (!isEditable()) return descriptionsOfEvent.get(slot);
		return paths.isEmpty() ? "" : composeFor(slot);
	}

	boolean canSave() {
		return isEditable() && !paths.isEmpty();
	}

	/**
	 * @return those of the slots that are filled, apart from the opened slot: saving would overwrite them
	 */
	List<Integer> findOtherFilledSlots(Collection<Integer> slots) {
		List<Integer> filled = new ArrayList<Integer>();
		for (int other : new TreeSet<Integer>(slots)) {
			if (other != slot && isFilled(other)) {
				filled.add(other);
			}
		}
		return filled;
	}

	/**
	 * Saves into the opened slot and into the given ones.
	 *
	 * @return the slots that were written
	 */
	Set<Integer> save(Collection<Integer> otherSlots) {

		if (!canSave()) {
			throw new IllegalStateException("Nothing that could be saved");
		}

		Set<Integer> slots = new TreeSet<Integer>(otherSlots);
		slots.add(slot);

		for (int target : slots) {
			store.save(eventType.getIndex(), target, composeFor(target));
		}

		open(eventType, slot, subject);
		return slots;
	}

	private String composeFor(int target) {
		return composer.toJson(composer.compose(eventType, target, relevanceThreshold, subject, paths));
	}

	private void requireEditable() {
		if (!isEditable()) {
			throw new IllegalStateException(reasonNotEditable);
		}
	}
}
