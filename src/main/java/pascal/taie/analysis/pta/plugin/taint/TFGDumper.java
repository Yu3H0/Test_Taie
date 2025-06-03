

package pascal.taie.analysis.pta.plugin.taint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pascal.taie.analysis.graph.flowgraph.FlowEdge;
import pascal.taie.analysis.graph.flowgraph.InstanceFieldNode;
import pascal.taie.analysis.graph.flowgraph.Node;
import pascal.taie.analysis.graph.flowgraph.VarNode;
import pascal.taie.util.collection.Sets;
import pascal.taie.util.graph.DotAttributes;
import pascal.taie.util.graph.DotDumper;
import pascal.taie.util.graph.Edge;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

/**
 * Taint flow graph dumper.
 */
class TFGDumper {

    private static final Logger logger = LogManager.getLogger(TFGDumper.class);

    private final Set<String> highlightNodes;

    private Set<Node> sourceNodes;

    private Set<Node> sinkNodes;

    TFGDumper() {
        this(null);
    }

    TFGDumper(@Nullable String highlightPath) {
        highlightNodes = Sets.newSet();
        try {
            if (highlightPath != null) {
                highlightNodes.addAll(Files.readAllLines(Path.of(highlightPath)));
            }
        } catch (IOException e) {
            logger.warn("Failed to read highlight nodes from {}",
                    highlightPath, e);
        }
    }

    void dump(TaintFlowGraph tfg, File output) {
        logger.info("Dumping {}", output.getAbsolutePath());
        sourceNodes = tfg.getSourceNodes();
        sinkNodes = tfg.getSinkNodes();
        DotDumper<Node> dumper = new DotDumper<Node>()
                .setNodeAttributer(this::nodeAttributer)
                .setEdgeAttributer(this::edgeAttributer);
        dumper.dump(tfg, output);
    }

    private DotAttributes nodeAttributer(Node node) {
        DotAttributes attrs;
        if (node instanceof VarNode) {
            attrs = DotAttributes.of("shape", "box",
                    "style", "filled", "fillcolor", "floralwhite");
        } else if (node instanceof InstanceFieldNode) {
            attrs = DotAttributes.of("shape", "box",
                    "style", "rounded", "style", "filled", "fillcolor", "aliceblue");
        } else { // ArrayIndexNode
            attrs = DotAttributes.of("style", "filled", "fillcolor", "khaki1");
        }
        if (highlightNodes.contains(node.toString())) {
            attrs = attrs.update("fillcolor", "green1");
        }
        if (sourceNodes.contains(node)) {
            attrs = attrs.update("shape", "doubleoctagon", "fillcolor", "gold");
        }
        if (sinkNodes.contains(node)) {
            attrs = attrs.update("shape", "doubleoctagon", "fillcolor", "deepskyblue");
        }
        return attrs;
    }

    private DotAttributes edgeAttributer(Edge<Node> edge) {
        FlowEdge flowEdge = (FlowEdge) edge;
        DotAttributes attrs = switch (flowEdge.kind()) {
            case LOCAL_ASSIGN, CAST -> DotAttributes.of();
            case THIS_PASSING, PARAMETER_PASSING -> DotAttributes.of("color", "blue");
            case RETURN -> DotAttributes.of("color", "blue", "style", "dashed");
            case INSTANCE_STORE, ARRAY_STORE -> DotAttributes.of("color", "red");
            case INSTANCE_LOAD, ARRAY_LOAD -> DotAttributes.of("color", "red", "style", "dashed");
            case OTHER -> DotAttributes.of("color", "green3", "style", "dashed");
            default -> throw new IllegalArgumentException(
                    "Unsupported edge kind: " + flowEdge.kind());
        };
        if (highlightNodes.contains(flowEdge.source().toString()) &&
                highlightNodes.contains(flowEdge.target().toString())) {
            return attrs.add("style", "bold");
        } else {
            return attrs;
        }
    }
}
