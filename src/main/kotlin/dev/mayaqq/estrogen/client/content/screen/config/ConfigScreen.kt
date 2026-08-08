package dev.mayaqq.estrogen.client.content.screen.config

import com.moulberry.lattice.Lattice
import com.moulberry.lattice.WidgetFunction
import com.moulberry.lattice.element.LatticeElement
import com.moulberry.lattice.element.LatticeElements
import com.moulberry.lattice.widget.CenteredStringWidget
import dev.mayaqq.cynosure.text.CommonText
import dev.mayaqq.cynosure.text.Text
import dev.mayaqq.cynosure.text.Text.asComponent
import dev.mayaqq.cynosure.text.TextBuilder.append
import dev.mayaqq.cynosure.text.TextStyle.color
import dev.mayaqq.cynosure.text.TextUtils.splitToWidth
import invoke.kitty.kritter.config.api.*
import invoke.kitty.kritter.config.validation.ValidationResult
import invoke.kitty.kritter.utils.color.MinecraftColors
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.components.MultiLineTextWidget
import net.minecraft.client.gui.components.StringWidget
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import java.util.function.Consumer
import java.util.function.Supplier

object ConfigScreen {
    fun make(config: Config, previous: Screen?): Screen {
        return Lattice.createConfigScreen(
            mapCategory(config, config.id.asComponent {  }),
            config::save,
            previous
        )
    }

    fun mapCategory(kritterCategory: ConfigElementContainer, title: Component): LatticeElements {
        val elements = LatticeElements.empty(title)
        kritterCategory.elements.forEach { element ->
            if (element is ConfigCategory) {
                elements.subcategories.add(mapCategory(element, element.name.asComponent {}))
            } else {
                mapElement(element)?.let { elements.options.add(it) }
            }
        }
        return elements
    }

    @OptIn(ExperimentalCodecsInConfig::class)
    @Suppress("UNCHECKED_CAST")
    fun mapElement(element: ConfigElement): LatticeElement? {
        val inputLength = Int.MAX_VALUE

        (element as? ConfigField<*>)?.let { field ->
            val function = when(field.type) {
                ElementType.BOOLEAN -> {
                    field as ConfigField<Boolean>
                    WidgetFunction.onOffButton(
                        { field.value },
                        { value -> field.value = value }
                    )
                }

                ElementType.BYTE -> {
                    field as ConfigField<Byte>
                    editBoxFiltered(
                        { field.value.toString() },
                        { value -> value.toByteOrNull()?.let { field.value = it } },
                        inputLength,
                        { value -> value.toByteOrNull()?.let { field.validate(it) == ValidationResult.Passed } == true }
                    )
                }
                ElementType.CHAR -> {
                    field as ConfigField<Char>
                    editBoxFiltered(
                        { field.value.toString() },
                        { value -> value.toCharArray().first().let { field.value = it } },
                        inputLength,
                        { value -> value.length == 1 && field.validate(value.toCharArray().first()) == ValidationResult.Passed }
                    )
                }
                ElementType.DOUBLE -> {
                    field as ConfigField<Double>
                    editBoxFiltered(
                        { field.value.toString() },
                        { value -> value.toDoubleOrNull()?.let { field.value = it } },
                        inputLength,
                        { value -> value.toDoubleOrNull()?.let { field.validate(it) == ValidationResult.Passed } == true }
                    )
                }

                ElementType.FLOAT -> {
                    field as ConfigField<Float>
                    editBoxFiltered(
                        { field.value.toString() },
                        { value -> value.toFloatOrNull()?.let { field.value = it } },
                        inputLength,
                        { value -> value.toFloatOrNull()?.let { field.validate(it) == ValidationResult.Passed } == true }
                    )
                }

                ElementType.INT -> {
                    field as ConfigField<Int>
                    editBoxFiltered(
                        { field.value.toString() },
                        { value -> value.toIntOrNull()?.let { field.value = it } },
                        inputLength,
                        { value -> value.toIntOrNull()?.let { field.validate(it) == ValidationResult.Passed } == true }
                    )
                }

                ElementType.LONG -> {
                    field as ConfigField<Long>
                    editBoxFiltered(
                        { field.value.toString() },
                        { value -> value.toLongOrNull()?.let { field.value = it } },
                        inputLength,
                        { value -> value.toLongOrNull()?.let { field.validate(it) == ValidationResult.Passed } == true }
                    )
                }

                ElementType.SHORT -> {
                    field as ConfigField<Short>
                    editBoxFiltered(
                        { field.value.toString() },
                        { value -> value.toShortOrNull()?.let { field.value = it } },
                        inputLength,
                        { value -> value.toShortOrNull()?.let { field.validate(it) == ValidationResult.Passed } == true }
                    )
                }
                ElementType.STRING -> {
                    field as ConfigField<String>
                    editBoxFiltered(
                        { field.value },
                        { value -> field.value = value },
                        inputLength,
                        { value -> field.validate(value) == ValidationResult.Passed }
                    )
                }

                is ElementType.ENUM<*> -> {
                    field as ConfigField<Enum<*>>
                    WidgetFunction.cycleButton(
                        { field.value },
                        { value -> field.value = value },
                        *field.value.javaClass.enumConstants
                    )
                }
                is ElementType.OBJECT<*>,
                is ElementType.COLLECTION<*>,
                is ElementType.CODEC<*>,
                ElementType.UNDEFINED -> editInJson()
                else -> editInJson()
            }
            return LatticeElement(function, field.displayName, field.comment?.asComponent())
        }
        return null
    }

    fun editBoxFiltered(initial: Supplier<String>, setter: Consumer<String>, maxLength: Int, filter: (String) -> Boolean): WidgetFunction {
        return WidgetFunction { font, title, description, width ->
            val initialValue = initial.get()
            EditBox(font, 0, 0, width, 20, title).apply {
                setMaxLength(maxLength)
                setFilter { filter.invoke(it) || it.isEmpty() }
                value = initialValue
                setResponder(setter)
            }
        }
    }

    fun editInJson(): WidgetFunction {
        return WidgetFunction { font, title, description, width ->
            val text = Text.of {
                append("Please edit ") { color = MinecraftColors.Red }
                append(title)
                append(" within the config file.") { color = MinecraftColors.Red }
            }.apply {
                splitToWidth("\n", width)
            }
            MultiLineTextWidget(
            text, font).apply {
                setMaxWidth(width)
            }
        }
    }
}