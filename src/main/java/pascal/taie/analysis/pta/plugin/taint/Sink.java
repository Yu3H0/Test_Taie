

package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.language.classes.JMethod;

/**
 * Represents a sink in taint analysis.
 *
 * @param method   the sink method.
 * @param indexRef the specific reference used to locate the sensitive argument
 *                 at the call site of {@code method}.
 */
record Sink(JMethod method, IndexRef indexRef) {

    @Override
    public String toString() {
        return method + "/" + indexRef;
    }
}
