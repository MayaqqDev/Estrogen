package dev.mayaqq.estrogen.client.content.screen.config

import com.moulberry.lattice.Lattice
import com.moulberry.lattice.WidgetFunction
import com.moulberry.lattice.element.LatticeElement
import com.moulberry.lattice.element.LatticeElements
import dev.mayaqq.cynosure.text.Text.asComponent
import invoke.kitty.kritter.config.api.*
import invoke.kitty.kritter.config.validation.ValidationResult
import net.minecraft.client.gui.components.EditBox
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

                ElementType.BYTE -> TODO()
                ElementType.CHAR -> TODO()
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

                ElementType.SHORT -> TODO()
                ElementType.STRING -> {
                    field as ConfigField<String>
                    editBoxFiltered(
                        { field.value },
                        { value -> field.value = value },
                        inputLength,
                        { value -> field.validate(value) == ValidationResult.Passed }
                    )
                }

                is ElementType.CODEC<*> -> TODO()
                is ElementType.COLLECTION<*> -> TODO()
                is ElementType.ENUM<*> -> TODO()
                is ElementType.OBJECT<*> -> TODO()
                ElementType.UNDEFINED -> TODO()
                else -> {
                    TODO()
                }
            }?: return null
            return LatticeElement(function, field.displayName, field.comment?.asComponent())
        }
        return null
    }

    fun editBoxFiltered(initial: Supplier<String>, setter: Consumer<String>, maxLength: Int, filter: (String) -> Boolean): WidgetFunction {
        return WidgetFunction { font, title, description, width ->
            val initialValue = initial.get()
            val editBox = EditBox(font, 0, 0, width, 20, title)
            editBox.setMaxLength(maxLength)
            editBox.setFilter(filter)
            editBox.value = initialValue
            editBox.setResponder(setter)
            editBox
        }
    }
}