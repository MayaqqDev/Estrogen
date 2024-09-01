package dev.mayaqq.estrogen.client.cosmetics.ui;

import com.simibubi.create.foundation.gui.ScreenOpener;
import dev.mayaqq.estrogen.client.cosmetics.service.CosmeticsApi;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class CosmeticUI {

    public static final MutableComponent GO_BACK = Component.literal("Go Back");
    public static final MutableComponent CLOSE = Component.literal("Close");
    public static final MutableComponent CLAIM_REWARD = Component.literal("Claim Reward");

    public static final MutableComponent LOGIN_DESCRIPTION = Component.literal("""
            Welcome to Estrogen Cosmetics!
            
            To get started, please log in to your account.
            """);
    public static final MutableComponent LOGIN_BUTTON = Component.literal("Login");

    public static final MutableComponent LOGIN_INIT = Component.literal("Logging in...");
    public static final MutableComponent LOGIN_UNAUTHORIZED = Component.literal("Failed to login, check if you are logged in to Minecraft.");
    public static final MutableComponent LOGIN_SERVER_ERROR = Component.literal("Error occurred on server side while logging in.");
    public static final MutableComponent LOGIN_FAILED = Component.literal("Unknown error occurred while logging in.");

    public static final MutableComponent COSMETICS_INIT = Component.literal("Getting Cosmetics...");
    public static final MutableComponent COSMETICS_UNAUTHORIZED = Component.literal("Failed to get cosmetics, try again later.");
    public static final MutableComponent COSMETICS_SERVER_ERROR = Component.literal("Error occurred on server side while getting cosmetics.");
    public static final MutableComponent COSMETICS_FAILED = Component.literal("Unknown error occurred while getting cosmetics.");

    public static final MutableComponent CLAIM_DESCRIPTION = Component.literal("""
            Welcome to Estrogen Cosmetics Reward Claim!
            
            Enter the code you received,
            into the text box below, to claim your cosmetic.
            """);
    public static final MutableComponent CLAIM_BUTTON = Component.literal("Claim");

    public static final MutableComponent CLAIM_INIT = Component.literal("Claiming...");
    public static final MutableComponent CLAIM_FORBIDDEN = Component.literal("Failed, code was already claimed.");
    public static final MutableComponent CLAIM_NOT_FOUND = Component.literal("Failed, code is invalid.");
    public static final MutableComponent CLAIM_FAILED = Component.literal("Unknown error occurred while claiming.");

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
