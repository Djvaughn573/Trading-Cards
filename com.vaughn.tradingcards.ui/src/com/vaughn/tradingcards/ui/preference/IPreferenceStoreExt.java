package com.vaughn.tradingcards.ui.preference;

import org.eclipse.jface.preference.IPreferenceStore;

public interface IPreferenceStoreExt extends IPreferenceStore {
	
    /**
     * The default-default value for short preferences (<code>0</code>).
     */
    public static final short SHORT_DEFAULT_DEFAULT = 0;

    /**
	 * Returns the current value of the short-valued preference with the given
	 * name. Returns the default-default value (<code>0</code>) if there is no
	 * preference with the given name, or if the current value cannot be treated
	 * as an integer.
	 *
	 * @param name
	 *            the name of the preference
	 * @return the short-valued preference
	 */
    public short getShort(String name);

    /**
     * Returns the default value for the short-valued preference
     * with the given name.
     * Returns the default-default value (<code>0</code>) if there
     * is no default preference with the given name, or if the default
     * value cannot be treated as a short.
     *
     * @param name the name of the preference
     * @return the default value of the named preference
     */
    public short getDefaultShort(String name);

    /**
     * Sets the default value for the short-valued preference with the
     * given name.
     * <p>
     * Note that the current value of the preference is affected if
     * the preference's current value was its old default value, in which
     * case it changes to the new default value. If the preference's current
     * is different from its old default value, its current value is
     * unaffected. No property change events are reported by changing default
     * values.
     * </p>
     *
     * @param name the name of the preference
     * @param value the new default value for the preference
     */
    public void setDefault(String name, short value);

    /**
     * Sets the current value of the short-valued preference with the
     * given name.
     * <p>
     * A property change event is reported if the current value of the
     * preference actually changes from its previous value. In the event
     * object, the property name is the name of the preference, and the
     * old and new values are wrapped as objects.
     * </p>
     * <p>
     * Note that the preferred way of re-initializing a preference to its
     * default value is to call <code>setToDefault</code>.
     * </p>
     *
     * @param name the name of the preference
     * @param value the new current value of the preference
     */
    public void setValue(String name, short value);
    
}
