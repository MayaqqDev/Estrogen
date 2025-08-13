package dev.mayaqq.estrogen.utils.transfer

import dev.mayaqq.cynosure.text.CommonText
import dev.mayaqq.cynosure.text.Text
import dev.mayaqq.cynosure.text.TextBuilder.append
import dev.mayaqq.cynosure.text.TextStyle.color
import dev.mayaqq.cynosure.text.TextStyle.underlined
import dev.mayaqq.cynosure.text.TextStyle.url
import dev.mayaqq.cynosure.utils.colors.McBlue
import dev.mayaqq.cynosure.utils.colors.McRed

object TransferHelper {
    val message = Text.of {
        append("The Estrogen mod has updated and has been split into")
        append(CommonText.NEWLINE)
        append("[Estrogen]") {
            url = "https://modrinth.com/mod/estrogen"
            color = McBlue
            underlined = true
        }
        append(CommonText.SPACE)
        append("and")
        append(CommonText.SPACE)
        append("[Create: Estrogen]") {
            url = "https://modrinth.com/mod/create-estrogen"
            color = McBlue
            underlined = true
        }
        append(".")
        append(CommonText.NEWLINE)
        append("Please install")
        append(CommonText.SPACE)
        append("Create: Estrogen") {
            url = "https://modrinth.com/mod/create-estrogen"
            color = McBlue
            underlined = true
        }
        append(CommonText.SPACE)
        append("to fix.")
        color = McRed
    }
}