package ink.ptms.adyeshach.common.entity.type

import ink.ptms.adyeshach.common.editor.Editors
import ink.ptms.adyeshach.common.entity.EntityTypes
import net.minecraft.server.v1_16_R1.HorseStyle
import org.bukkit.entity.Horse
import org.bukkit.entity.Villager

/**
 * @author sky
 * @date 2020/8/4 23:15
 */
class AdyHorse() : AdyHorseBase(EntityTypes.HORSE) {

    init {
        /**
         * Variant (Color & Style)
         *
         * 1.15 -> 18
         * 1.14 -> 17
         * 1.10 -> 15
         * 1.9 -> 14
         */
        registerMeta(at(11500 to 18, 11400 to 17, 11000 to 15, 10900 to 14), "variant", 0)
                .canEdit(false)
                .build()
        registerEditor("horseColor")
                .from(Editors.enums(Horse.Color::class) { _, entity, meta, _, e -> "/adyeshachapi edit horse_color ${entity.uniqueId} ${meta.key} $e" })
                .reset { entity, meta ->
                    setColor(Horse.Color.WHITE)
                }
                .display { _, entity, _ ->
                    getColor().name
                }.build()
        registerEditor("horseStyle")
                .from(Editors.enums(Horse.Style::class) { _, entity, meta, _, e -> "/adyeshachapi edit horse_style ${entity.uniqueId} ${meta.key} $e" })
                .reset { entity, meta ->
                    setStyle(Horse.Style.NONE)
                }
                .display { _, entity, _ ->
                    getStyle().name
                }.build()
    }

    fun getVariant(): Int {
        return getMetadata("variant")
    }

    fun getColor(): Horse.Color {
        return Horse.Color.values()[getVariant() and 255]
    }

    fun getStyle(): Horse.Style {
        return Horse.Style.values()[getVariant() ushr 8]
    }

    fun setColor(color: Horse.Color) {
        setColorAndStyle(color, getStyle())
    }

    fun setStyle(style: Horse.Style) {
        setColorAndStyle(getColor(), style)
    }

    fun setColorAndStyle(color: Horse.Color, style: Horse.Style) {
        if (version < 11600) {
            setMetadata("variant", color.ordinal and 255 or style.ordinal shl 8)
        } else {
            setMetadata("variant", color.ordinal and 255 or style.ordinal shl 8 and '\uff00'.toInt())
        }
    }
}