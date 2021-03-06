package ink.ptms.adyeshach.api.event

import ink.ptms.adyeshach.common.entity.EntityInstance
import io.izzel.taboolib.module.event.EventCancellable
import org.bukkit.Bukkit
import org.bukkit.Location

/**
 * @Author sky
 * @Since 2020-08-14 19:21
 */
class AdyeshachEntityTeleportEvent(val entity: EntityInstance, var location: Location) : EventCancellable<AdyeshachEntityTeleportEvent>() {

    init {
        async(!Bukkit.isPrimaryThread())
    }
}