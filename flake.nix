{
  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-unstable";
  };

  outputs = {nixpkgs, ...} @ inputs: let
    lib = nixpkgs.lib;
    supportedSystems = lib.systems.flakeExposed;
    forEachSupportedSystem = f:
      lib.genAttrs supportedSystems (system:
        f {
          pkgs = nixpkgs.legacyPackages.${system};
        });
  in {
    devShells = forEachSupportedSystem ({pkgs}: let
      java21 = pkgs.jetbrains.jdk-no-jcef-21;

      nativeBuildInputs = [
        java21
      ];

      buildInputs = with pkgs; [
        libGL
        glfw3-minecraft
        flite
        libpulseaudio
      ];
    in {
      default = pkgs.mkShell {
        inherit nativeBuildInputs buildInputs;

        env = {
          LD_LIBRARY_PATH = lib.makeLibraryPath buildInputs;
          JAVA_HOME = "${java21.home}";
        };
      };
    });
  };
}
