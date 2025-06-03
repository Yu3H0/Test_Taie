

package pascal.taie.language.natives;

import pascal.taie.ir.IR;
import pascal.taie.language.classes.JMethod;

import java.io.Serializable;

public interface NativeModel extends Serializable {

    IR buildNativeIR(JMethod method);
}
