


package pascal.taie.util;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code @MultiStringsSources} is a simple container for one or more
 * {@link MultiStringsSource} annotations.
 *
 * <p>Note, however, that use of the {@code @MultiStringsSources} container is completely
 * optional since {@code @MultiStringsSource} is a {@linkplain java.lang.annotation.Repeatable
 * repeatable} annotation.
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultiStringsSources {

    /**
     * An array of one or more {@link MultiStringsSource @MultiStringsSource}
     * annotations.
     */
    MultiStringsSource[] value();

}
