package dev.mayaqq.estrogen.client.cosmetics.ui;

import com.simibubi.create.foundation.gui.ScreenOpener;
import dev.mayaqq.estrogen.client.cosmetics.service.CosmeticsApi;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class CosmeticUI {

    public static final MutableComponent TITLE = Component.translatable("gui.estrogen.cosmetics.title");
    public static final MutableComponent GO_BACK = Component.translatable("gui.estrogen.cosmetics.back");
    public static final MutableComponent CLOSE = Component.translatable("gui.estrogen.cosmetics.close");
    public static final MutableComponent CLAIM_REWARD = Component.translatable("gui.estrogen.cosmetics.claim");
    public static final MutableComponent NONE = Component.translatable("gui.estrogen.cosmetics.none");

    public static final MutableComponent LOGIN_DESCRIPTION = Component.translatable("gui.estrogen.cosmetics.login.description");
    public static final MutableComponent LOGIN_BUTTON = Component.translatable("gui.estrogen.cosmetics.login.button");
    public static final MutableComponent LOGIN_INIT = Component.translatable("gui.estrogen.cosmetics.login.init");
    public static final MutableComponent LOGIN_UNAUTHORIZED = Component.translatable("gui.estrogen.cosmetics.login.unauthorized");
    public static final MutableComponent LOGIN_SERVER_ERROR = Component.translatable("gui.estrogen.cosmetics.login.server_error");
    public static final MutableComponent LOGIN_FAILED = Component.translatable("gui.estrogen.cosmetics.login.failed");

    public static final MutableComponent COSMETICS_INIT = Component.translatable("gui.estrogen.cosmetics.init");
    public static final MutableComponent COSMETICS_UNAUTHORIZED = Component.translatable("gui.estrogen.cosmetics.unauthorized");
    public static final MutableComponent COSMETICS_SERVER_ERROR = Component.translatable("gui.estrogen.cosmetics.server_error");
    public static final MutableComponent COSMETICS_FAILED = Component.translatable("gui.estrogen.cosmetics.failed");

    public static final MutableComponent CLAIM_DESCRIPTION = Component.translatable("gui.estrogen.cosmetics.claim.description");
    public static final MutableComponent CLAIM_BUTTON = Component.translatable("gui.estrogen.cosmetics.claim.button");
    public static final MutableComponent CLAIM_INIT = Component.translatable("gui.estrogen.cosmetics.claim.init");
    public static final MutableComponent CLAIM_FORBIDDEN = Component.translatable("gui.estrogen.cosmetics.claim.forbidden");
    public static final MutableComponent CLAIM_NOT_FOUND = Component.translatable("gui.estrogen.cosmetics.claim.not_found");
    public static final MutableComponent CLAIM_FAILED = Component.translatable("gui.estrogen.cosmetics.claim.failed");

    public static MutableComponent getLoginMessage(CosmeticsApi.StatusCode code) {
        if (code == null) return LOGIN_INIT;
        return switch (code) {
            case UNAUTHORIZED -> LOGIN_UNAUTHORIZED;
            case INTERNAL_SERVER_ERROR -> LOGIN_SERVER_ERROR;
            case UNKNOWN_ERROR -> LOGIN_FAILED;
            default -> Component.literal("Status: " + code);
        };
    }

    public static MutableComponent getCosmeticsMessage(CosmeticsApi.StatusCode code) {
        if (code == null) return COSMETICS_INIT;
        return switch (code) {
            case UNAUTHORIZED -> COSMETICS_UNAUTHORIZED;
            case INTERNAL_SERVER_ERROR -> COSMETICS_SERVER_ERROR;
            case UNKNOWN_ERROR -> COSMETICS_FAILED;
            default -> Component.literal("Status: " + code);
        };
    }

    public static MutableComponent getClaimMessage(CosmeticsApi.StatusCode code) {
        if (code == null) return CLAIM_INIT;
        return switch (code) {
            case FORBIDDEN -> CLAIM_FORBIDDEN;
            case NOT_FOUND -> CLAIM_NOT_FOUND;
            case UNKNOWN_ERROR -> CLAIM_FAILED;
            default -> Component.literal("Status: " + code);
        };
    }

    public static void open(Screen parent) {
        if (CosmeticsApi.hasSessionToken()) {
            ScreenOpener.open(new CosmeticsScreen(parent));
        } else {
            ScreenOpener.open(new CosmeticsLoginScreen(parent));
        }
    }
}
