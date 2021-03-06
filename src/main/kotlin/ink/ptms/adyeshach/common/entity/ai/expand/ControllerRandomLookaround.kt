package ink.ptms.adyeshach.common.entity.ai.expand

import ink.ptms.adyeshach.common.entity.EntityInstance
import ink.ptms.adyeshach.common.entity.ai.Controller
import io.izzel.taboolib.util.lite.Numbers

/**
 * 随机看向附近
 *
 * @Author sky
 * @Since 2020-08-19 22:09
 */
class ControllerRandomLookaround(entity: EntityInstance) : Controller(entity) {

    override fun isAsync(): Boolean {
        return true
    }

    override fun shouldExecute(): Boolean {
        return Numbers.random(0.01) && !entity!!.isControllerMoving()
    }

    override fun onTick() {
        entity!!.position.run {
            entity.controllerLook(yaw + Numbers.getRandomDouble(-90, 90).toFloat(), Numbers.getRandomDouble(-1, 1).toFloat())
        }
    }
}