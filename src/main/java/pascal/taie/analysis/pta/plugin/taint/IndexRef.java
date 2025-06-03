

package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.analysis.pta.plugin.util.InvokeUtils;
import pascal.taie.language.classes.JField;

record IndexRef(Kind kind, int index, JField field)
        implements Comparable<IndexRef> {

    static final String ARRAY_SUFFIX = "[*]";

    enum Kind {
        VAR, ARRAY, FIELD
    }

    @Override
    public JField field() {
        return field;
    }

    @Override
    public int compareTo(IndexRef other) {
        int cmp = index - other.index;
        return cmp != 0 ? cmp : kind.compareTo(other.kind);
    }

    @Override
    public String toString() {
        String base = InvokeUtils.toString(index);
        return switch (kind) {
            case VAR -> base;
            case ARRAY -> base + ARRAY_SUFFIX;
            case FIELD -> base + "." + field.getName();
        };
    }
}
