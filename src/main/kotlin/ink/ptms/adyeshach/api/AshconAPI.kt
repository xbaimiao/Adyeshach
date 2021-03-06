package ink.ptms.adyeshach.api

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import ink.ptms.adyeshach.common.util.Tasks
import io.izzel.taboolib.module.inject.PlayerContainer
import io.izzel.taboolib.module.inject.TListener
import io.izzel.taboolib.util.Files
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

/**
 * @author Arasple
 * @date 2020/8/4 20:38
 */
object AshconAPI {

    private val ASHCON_API = arrayOf("https://api.ashcon.app/mojang/v2/user/")

    @PlayerContainer
    private val CACHED_PROFILES = mutableMapOf<String, JsonObject>()

    fun getProfile(name: String) = CACHED_PROFILES.computeIfAbsent(name) { JsonParser().parse(Files.readFromURL("${ASHCON_API[0]}$name")) as JsonObject }

    fun getTextureValue(name: String): String = getProfile(name).getAsJsonObject("textures").getAsJsonObject("raw").get("value").asString

    fun getTextureSignature(name: String): String = getProfile(name).getAsJsonObject("textures").getAsJsonObject("raw").get("signature").asString

    @TListener
    class ListenerJoin : Listener {

        @EventHandler(priority = EventPriority.HIGHEST)
        fun onJoin(e: PlayerJoinEvent) {
            Tasks.task(true) {
                try {
                    getProfile(e.player.name)
                } catch (ignore: NullPointerException) {
                }
            }
        }
    }
}