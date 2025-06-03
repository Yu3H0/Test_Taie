

package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.language.classes.JMethod;
import pascal.taie.language.type.Type;

/**
 * Represents sources which generates taint objects at method calls.
 *
 * @param method   the method that generates taint object at call site.
 * @param indexRef the index of the tainted reference at the call site.
 * @param type     type of the generated taint object.
 */
record CallSource(JMethod method, IndexRef indexRef, Type type)
        implements Source {

    @Override
    public String toString() {
        return String.format("CallSource{%s/%s(%s)}", method, indexRef, type);
    }
}
