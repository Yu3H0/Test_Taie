package pascal.taie.android.util;

import java.util.List;

public class AndroidSystemClassUtil {

    private static final List<String> SYSTEM_PACKAGES =
            List.of(
//                    "jdk.",
//                    "apple.laf.",
//                    "com.sun.",
//                    "com.ibm.",
//                    "org.w3c.",
//                    "apple.awt.",
//                    "com.apple.",

//                    "java.",
//                    "javax.",
//                    "sun.",
//                    "com.sun.",
                    "org.xml",
                    "org.w3c.dom",
                    "org.json",
                    "org.apache.",
                    "dalvik.system.",
//                    "com.android.",
                    "android.",
                    "androidx."
//                    "kotlin.",
//                    "kotlinx."
            );

    public static boolean isApplicationClass(String name) {
        return SYSTEM_PACKAGES.stream().noneMatch(name::startsWith);
    }

    public static boolean isAndroidSystemClass(String name) {
        return SYSTEM_PACKAGES.stream().anyMatch(name::startsWith);
    }

    public static boolean haveStartWithName(String name) {
        return name != null && !name.isEmpty() && SYSTEM_PACKAGES.stream().anyMatch(name::startsWith);
    }

}
