

package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.language.classes.JMethod;
import pascal.taie.language.type.Type;

/**
 * Represents sources which generate taint objects on method parameters.
 *
 * @param method   the method whose parameter are tainted.
 *                 Usually, such methods are program entry points that
 *                 receive inputs (treated as taints).
 * @param indexRef the index of the tainted reference.
 * @param type     the type of the generated taint object.
 */
record ParamSource(JMethod method, IndexRef indexRef, Type type)
        implements Source {

    @Override
    public String toString() {
        return String.format("ParamSource{%s/%s(%s)}", method, indexRef, type);
    }
}
