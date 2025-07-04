/**
 * Precompiled [maven-publish.conventions.gradle.kts][Maven_publish_conventions_gradle] script plugin.
 *
 * @see Maven_publish_conventions_gradle
 */
public
class MavenPublish_conventionsPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("Maven_publish_conventions_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
