package dev.mayaqq.estrogen.utils.extensions;

import dev.mayaqq.estrogen.utils.UwUfy;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.OrderedText;

public class UwuOrderedText implements OrderedText {
    private final OrderedText wrapped;

    public UwuOrderedText(OrderedText wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public boolean accept(CharacterVisitor visitor) {
        return wrapped.accept((index, style, codePoint) -> {
            String uwufied = UwUfy.uwufyChar((char) codePoint);
            char[] uwufiedChars = uwufied.toCharArray();
            for (char uwufiedChar : uwufiedChars) {
                visitor.accept(index, style, uwufiedChar);
            }
            return false;
        });
    }
}
