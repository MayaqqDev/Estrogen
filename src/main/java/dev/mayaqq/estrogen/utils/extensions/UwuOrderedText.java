package dev.mayaqq.estrogen.utils.extensions;

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
            if (codePoint == 'r' || codePoint == 'l') codePoint = 'w';
            else if (codePoint == 'R' || codePoint == 'L') codePoint = 'W';
            else if (codePoint == 'u') {
                return visitor.accept(index, style, 'u')
                        && visitor.accept(index, style, 'w')
                        && visitor.accept(index, style, 'u');
                }
            else if (codePoint == 'U') {
                    return visitor.accept(index, style, 'U')
                            && visitor.accept(index, style, 'w')
                            && visitor.accept(index, style, 'U');
                }

                return visitor.accept(index, style, codePoint);
        });
    }
}
