package pascal.taie.analysis.pta.plugin.android.misc;

import pascal.taie.analysis.pta.core.cs.element.CSVar;

/**
 * Holder for the information stored in any map structure.
 * @param key map key.
 * @param value map value.
 */
record MapHolder(CSVar key,
                 CSVar value) {

}
