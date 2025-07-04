

package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.analysis.pta.plugin.util.InvokeUtils;
import pascal.taie.language.classes.JMethod;

/**
 * Represents sanitizers which remove taint objects on method parameters.
 *
 * @param method the method whose parameter are sanitized.
 * @param index  the index of the sanitized parameter.
 */
record ParamSanitizer(JMethod method, int index) implements Sanitizer {

    @Override
    public String toString() {
        return String.format("ParamSanitizer{%s/%s}",
                method, InvokeUtils.toString(index));
    }
}
