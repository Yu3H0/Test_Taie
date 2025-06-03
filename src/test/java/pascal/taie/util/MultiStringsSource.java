


package pascal.taie.util;


import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code @MultiStringsSource} is a repeatable variation of an {@link ArgumentsSource}
 * that provides a single test case with multiple string arguments to a method.
 * Each instance of {@code @MultiStringsSource} defines a set of multiple string values
 * that are supplied as arguments to the annotated {@code @ParameterizedTest} method.
 *
 * <p>Note that this annotation is similar to JUnit's {@code @ValueSource}, but it allows
 * for multiple string arguments to be provided as a single test case,
 * while {@code @ValueSource} only supports single arguments.
 *
 * <p>{@code @MultiStringsSource} can be used in conjunction with the {@code @ParameterizedTest}
 * annotation to create parameterized tests that take multiple string arguments.
 *
 * <p>Example 1: Multiple test cases with multiple string arguments:
 * <pre>
 *     &#064;ParameterizedTest
 *     &#064;MultiStringsSource({"a1", "b1"})
 *     &#064;MultiStringsSource({"a2", "b2"})
 *     void test(String arg1, String arg2) {...}
 * </pre>
 *
 * <p>Example 2: Multiple test cases with multiple string (or null) arguments:
 * <pre>
 *     &#064;ParameterizedTest
 *     &#064;MultiStringsSource({"a1"}) // equivalent to &#064;MultiStringsSource({"a1", null})
 *     &#064;MultiStringsSource({"a2", "b2"})
 *     void test(String arg1, &#064;Nullable String arg2) {...}
 * </pre>
 *
 * <p>Example 3: Multiple test cases with multiple string arguments and varargs:
 * <pre>
 *     &#064;ParameterizedTest
 *     &#064;MultiStringsSource({"a1"})
 *     &#064;MultiStringsSource({"a2", "b2"})
 *     &#064;MultiStringsSource({"a3", "b3", "c3"})
 *     void test(String arg1, String... arg2) {...}
 * </pre>
 *
 * @see org.junit.jupiter.params.provider.ArgumentsSource
 * @see org.junit.jupiter.params.ParameterizedTest
 */
@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(MultiStringsSources.class)
@Documented
@ArgumentsSource(MultiStringsSourceArgumentsProvider.class)
public @interface MultiStringsSource {

    /**
     * The {@link String} values to use as source of arguments; can be empty or null.
     */
    String[] value() default {};

}
