

package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.language.classes.JField;
import pascal.taie.language.type.Type;

public record FieldSource(JField field, Type type) implements Source {

    @Override
    public String toString() {
        return String.format("FieldSource{%s(%s)}", field, type);
    }
}
