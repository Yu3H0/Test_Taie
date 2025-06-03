

package pascal.taie.analysis.pta.plugin.util;

import pascal.taie.analysis.pta.core.cs.element.CSMethod;
import pascal.taie.language.classes.JMethod;

import java.util.Set;

/**
 * Models specific APIs by generating corresponding IR.
 *
 * @deprecated Use {@link IRModelPlugin} instead.
 */
@Deprecated
public interface IRModel {

    Set<JMethod> getModeledAPIs();

    void handleNewMethod(JMethod method);

    void handleNewCSMethod(CSMethod csMethod);
}
