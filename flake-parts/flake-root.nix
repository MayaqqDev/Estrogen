# --- flake-parts/flake-root.nix
{ lib, ... }:
{
  # NOTE This is probably conflicting with https://github.com/srid/flake-root/
  # however it essentially fully replaces that functionality with a simple
  # option (thanks to the known structure) so it should be probably fine.
  options.flake-root = lib.mkOption {
    type = lib.types.path;
    description = ''
      Provides `config.flake-root` with the path to the flake root.
    '';
    default = ../.;
  };
}
