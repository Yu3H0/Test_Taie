

package pascal.taie.analysis.pta.plugin.taint;

import pascal.taie.analysis.pta.plugin.Plugin;

/**
 * Abstract class for taint analysis handlers that require to run on-the-fly
 * with pointer analysis.
 */
abstract class OnFlyHandler extends Handler implements Plugin {

    protected OnFlyHandler(HandlerContext context) {
        super(context);
    }
}
