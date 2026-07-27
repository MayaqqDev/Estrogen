# --- flake-parts/pkgs/default.nix
{config, ...}: {
  perSystem = systemConfig @ {pkgs, ...}: {
    packages = {
      # NOTE For more info on the nix `callPackage` pattern see
      # https://nixos.org/guides/nix-pills/13-callpackage-design-pattern.html

      # my-custom-package = pkgs.callPackage ./my-custom-package.nix { };
      estrogen = pkgs.callPackage ./estrogen {inherit pkgs config;};
      default = pkgs.callPackage ./estrogen {inherit pkgs config;};
    };
    apps = {
      update-gradle-deps = {
        type = "app";
        program = "${systemConfig.config.packages.estrogen.mitmCache.updateScript}";
      };
    };
  };
}
