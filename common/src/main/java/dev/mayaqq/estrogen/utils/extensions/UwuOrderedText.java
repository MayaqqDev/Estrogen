package dev.mayaqq.estrogen.utils.extensions;

import dev.mayaqq.estrogen.client.features.UwUfy;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;
import org.jetbrains.annotations.NotNull;

/*
 * This can turn any ordered text into an uwufied one, you will just have to cast it.
 * Sadly, this cannot work with the whole String, only with separate characters.
 */
public class UwuOrderedText implements FormattedCharSequence {
    private final FormattedCharSequence wrapped;
    private final String uwufiedString;

    public UwuOrderedText(FormattedCharSequence wrapped) {
        this.wrapped = wrapped;
        this.uwufiedString = UwUfy.uwufyString(formattedCharSequenceToString(wrapped));
    }

    @Override
    public boolean accept(@NotNull FormattedCharSink visitor) {
        return wrapped.accept((index, style, codePoint) -> {
            for (char c : uwufiedString.toCharArray()) {
                if (!visitor.accept(index, style, c)) return false;
            }
            return false;
        });
    }

    public static String formattedCharSequenceToString(FormattedCharSequence sequence) {
        StringBuilder sb = new StringBuilder();
        sequence.accept((index, style, codePoint) -> {
            sb.appendCodePoint(codePoint);
            return true;
        });
        return sb.toString();
    }
}