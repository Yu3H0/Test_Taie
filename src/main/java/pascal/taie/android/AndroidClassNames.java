package pascal.taie.android;

/**
 * Provides names of android system classes.
 */
public final class AndroidClassNames {

    public static final String APPLICATION = "android.app.Application";

    public static final String CONTEXT = "android.content.Context";

    public static final String CONTEXT_WRAPPER = "android.content.ContextWrapper";

    public static final String ACTIVITY = "android.app.Activity";

    public static final String FRAGMENT_ACTIVITY = "androidx.fragment.app.FragmentActivity";

    public static final String SERVICE = "android.app.Service";

    public static final String BROADCAST_RECEIVER = "android.content.BroadcastReceiver";

    public static final String INTENT = "android.content.Intent";

    public static final String INTENT_FILTER = "android.content.IntentFilter";

    public static final String HANDLER = "android.os.Handler";

    public static final String MESSENGER = "android.os.Messenger";

    public static final String FRAGMENT = "android.app.Fragment";

    public static final String ANDROIDX_FRAGMENT = "androidx.fragment.app.Fragment";

    public static final String VIEW = "android.view.View";

    public static final String TEXT_VIEW = "android.widget.TextView";

    public static final String FRAGMENT_MANAGER = "android.app.FragmentManager";

    public static final String ANDROIDX_FRAGMENT_MANAGER = "androidx.fragment.app.FragmentManager";

    public static final String FRAGMENT_TRANSACTION = "android.app.FragmentTransaction";

    public static final String ANDROIDX_FRAGMENT_TRANSACTION = "androidx.fragment.app.FragmentTransaction";

    public static final String SHARED_PREFERENCES = "android.content.SharedPreferences";

    public static final String BUNDLE = "android.os.Bundle";

    public static final String URL_CONNECTION = "java.net.URLConnection";

    public static final String ACCOUNT = "android.accounts.Account";

    // Suppresses default constructor, ensuring non-instantiability.
    private AndroidClassNames() {
    }
}
