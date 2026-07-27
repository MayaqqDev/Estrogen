# --- flake-parts/systems.nix
{ inputs, ... }:
{
  # NOTE We use the default `systems` defined by the `nix-systems` flake, if
  # you need any additional systems, simply add them in the following manner
  #
  # `systems = (import inputs.systems) ++ [ "armv7l-linux" ];`
  systems = import inputs.systems;
}
