package dev.mayaqq.estrogen.client.content.screen.config

import dev.mayaqq.cynosure.helpers.McFont
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.components.Tooltip
import net.minecraft.network.chat.Component
import uwu.serenity.kittyconfig.elements.ConfigField
import uwu.serenity.kittyconfig.serialization.ElementType
import uwu.serenity.kittyconfig.validation.DecimalRange
import uwu.serenity.kittyconfig.validation.Pattern
import uwu.serenity.kittyconfig.validation.Range
import uwu.serenity.kittyconfig.validation.ValidationResult

@Suppress("UNCHECKED_CAST")
class ConfigTextWidget<T>(
    field: ConfigField<T>,
    guiWidth: Int,
) : EditBox(
    McFont,
    guiWidth / 2 + 20, 5, 100, 20,
    Component.literal(field.name)
) {
    init {
        this.value = field.value.toString()
        this.setResponder { value ->
            field.value = value.fromType(field.type)!!
            if (field.lastValidationResult is ValidationResult.Failed) {
                this.setTextColor(0xFF0000)
            } else {
                this.setTextColor(0xFFFFFF)
            }
        }
        this.setFilter { value ->
            value.fromType(field.type) != null
        }
        val text = field.validators.joinToString("\n") {
            when (it) {
                is Range -> "Range: from ${it.min} to ${it.max}"
                is DecimalRange -> "Decimal Range: from ${it.min} to ${it.max}"
                is Pattern -> "Pattern: ${it.value}"
                else -> ""
            }
        }
        if (text.isNotEmpty()) {
            this.tooltip = Tooltip.create(Component.literal(text))
        }
    }

    private fun String.fromType(type: ElementType): T? = when(type) {
        ElementType.BYTE -> this.toByteOrNull() as T?
        ElementType.DOUBLE -> this.toDoubleOrNull() as T?
        ElementType.FLOAT -> this.toFloatOrNull() as T?
        ElementType.INT -> this.toIntOrNull() as T?
        ElementType.LONG -> this.toLongOrNull() as T?
        ElementType.SHORT -> this.toShortOrNull() as T?
        ElementType.STRING -> this as T?
        else -> throw Exception()
    }
}