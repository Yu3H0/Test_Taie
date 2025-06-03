

package pascal.taie.language.natives;

import pascal.taie.ir.IR;
import pascal.taie.ir.IRBuildHelper;
import pascal.taie.language.classes.JMethod;

/**
 * Builds empty IR for every native method.
 */
public class EmptyNativeModel implements NativeModel {

    @Override
    public IR buildNativeIR(JMethod method) {
        return new IRBuildHelper(method).buildEmpty();
    }
}
