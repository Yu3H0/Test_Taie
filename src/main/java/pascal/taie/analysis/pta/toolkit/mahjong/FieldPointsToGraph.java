

package pascal.taie.analysis.pta.toolkit.mahjong;

import pascal.taie.analysis.pta.PointerAnalysisResult;
import pascal.taie.analysis.pta.core.heap.Obj;
import pascal.taie.ir.exp.Exp;
import pascal.taie.ir.stmt.LoadArray;
import pascal.taie.ir.stmt.LoadField;
import pascal.taie.language.type.NullType;
import pascal.taie.language.type.ReferenceType;
import pascal.taie.language.type.Type;
import pascal.taie.util.collection.CollectionUtils;
import pascal.taie.util.collection.Maps;
import pascal.taie.util.collection.Sets;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

class FieldPointsToGraph {

    private final Set<Obj> objects;

    private final ConcurrentMap<Obj, ConcurrentMap<Field, Set<Obj>>> fieldPointsTo;

    FieldPointsToGraph(PointerAnalysisResult pta) {
        objects = CollectionUtils.toSet(pta.getObjects());
        fieldPointsTo = Maps.newConcurrentMap();
        initialize(pta);
    }

    private void initialize(PointerAnalysisResult pta) {
        // build field points-to graph by examining points-to results
        // now scan all loaded fields, shall we scan all fields of all objects?
        Field.Factory factory = new Field.Factory();
        pta.getVars().parallelStream().forEach(var -> {
            for (LoadField load : var.getLoadFields()) {
                if (isConcerned(load.getRValue())) {
                    for (Obj baseObj : pta.getPointsToSet(var)) {
                        Field field = factory.get(load.getFieldRef().resolve());
                        Set<Obj> pts = pta.getPointsToSet(load.getRValue());
                        addFieldPointsTo(baseObj, field, pts);
                    }
                }
            }
            for (LoadArray load : var.getLoadArrays()) {
                if (isConcerned(load.getRValue())) {
                    for (Obj baseObj : pta.getPointsToSet(var)) {
                        Field field = factory.getArrayIndex();
                        Set<Obj> pts = pta.getPointsToSet(load.getRValue());
                        addFieldPointsTo(baseObj, field, pts);
                    }
                }
            }
        });
    }

    private static boolean isConcerned(Exp exp) {
        Type type = exp.getType();
        return type instanceof ReferenceType && !(type instanceof NullType);
    }

    private void addFieldPointsTo(Obj baseObj, Field field, Set<Obj> pts) {
        fieldPointsTo.computeIfAbsent(baseObj, o -> Maps.newConcurrentMap())
                .computeIfAbsent(field, f -> Sets.newConcurrentSet())
                .addAll(pts);
    }

    Set<Obj> getObjects() {
        return objects;
    }

    Set<Field> dotFieldsOf(Obj baseObj) {
        ConcurrentMap<Field, Set<Obj>> fp = fieldPointsTo.get(baseObj);
        return fp != null ? fp.keySet() : Set.of();
    }

    Set<Obj> pointsTo(Obj baseObj, Field field) {
        ConcurrentMap<Field, Set<Obj>> fp = fieldPointsTo.get(baseObj);
        return fp != null ? fp.getOrDefault(field, Set.of()) : Set.of();
    }

    boolean hasPointer(Obj baseObj, Field field) {
        ConcurrentMap<Field, Set<Obj>> fp = fieldPointsTo.get(baseObj);
        return fp != null && fp.containsKey(field);
    }
}
