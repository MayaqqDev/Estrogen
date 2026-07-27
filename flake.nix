# --- flake.nix
{
  description = "Estrogen, Express yourself, in Minecraft!";

  inputs = {
    # --- BASE DEPENDENCIES ---
    nixpkgs.url = "github:nixos/nixpkgs/nixos-unstable";
    flake-parts.url = "github:hercules-ci/flake-parts";
    treefmt-nix.url = "github:numtide/treefmt-nix";
    systems.url = "github:nix-systems/default";

    # --- YOUR DEPENDENCIES ---
  };

  # NOTE Here you can add additional binary cache substituers that you trust.
  # There are also some sensible default caches commented out that you
  # might consider using, however, you are advised to doublecheck the keys.
  nixConfig = {
    extra-trusted-public-keys = [
      # "cache.nixos.org-1:6NCHdD59X431o0gWypbMrAURkbJ16ZPMQFGspcDShjY="
      # "nix-community.cachix.org-1:mB9FSh9qf2dCimDSUo8Zy7bkq5CX+/rkCWyvRCYg3Fs="
    ];
    extra-substituters = [
      # "https://cache.nixos.org"
      # "https://nix-community.cachix.org/"
    ];
  };

  outputs = inputs @ {flake-parts, ...}: let
    inherit (inputs.nixpkgs) lib;
    inherit (import ./flake-parts/_bootstrap.nix {inherit lib;}) loadParts;
  in
    flake-parts.lib.mkFlake {inherit inputs;} {
      imports = loadParts ./flake-parts;
    };
}
