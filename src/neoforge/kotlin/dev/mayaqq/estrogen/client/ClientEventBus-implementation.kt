package dev.mayaqq.estrogen.client

// Cynosure 1.0.0.24's generated Nullbus dispatchers cannot resolve nested
// client event classes under NeoForge's transforming module class loader.
// NeoForge events are bridged directly in EstrogenForgeClient instead.
actual fun hookPlatformClientEventBus() {
    hookClientEventBus()
}
