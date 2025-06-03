

package pascal.taie.analysis.pta.plugin.assertion;

import pascal.taie.ir.stmt.Invoke;

import java.util.Map;

/**
 * Represents result of {@link Checker}.
 *
 * @param invoke    the checked assertion.
 * @param assertion the information of the assertion.
 * @param failures  the information about failures, which map a program element
 *                  to its relevant analysis result. If this map is empty,
 *                  then it means that the assertion is passed.
 */
record Result(Invoke invoke, String assertion, Map<?, ?> failures) {
}
