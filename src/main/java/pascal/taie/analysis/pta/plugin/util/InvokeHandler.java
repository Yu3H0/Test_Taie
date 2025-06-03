

package pascal.taie.analysis.pta.plugin.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for conveniently marking Invoke handlers in API models.
 */
@Documented
@Repeatable(InvokeHandlers.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface InvokeHandler {

    /**
     * Method signature of the {@link pascal.taie.ir.stmt.Invoke}
     * to be handled. Multiple method signatures can be specified
     * in a single {@link InvokeHandler} annotation.
     */
    String[] signature();

    /**
     * Indexes for the arguments used by the handler.
     */
    int[] argIndexes() default {};
}
