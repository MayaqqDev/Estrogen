{
  pkgs,
  config,
  ...
}: let
  # Keep the packaged Gradle version identical to the wrapper version.
  gradle96 =
    (pkgs.gradle-packages.mkGradle {
      version = "9.6.0";
      hash = "sha256-u66y/vhxCBjPDiYSAdq5ZMVy+SuUKBLfDDYg1ipSmgE=";
      defaultJava = pkgs.jdk21;
    }).wrapped;
in
  pkgs.stdenvNoCC.mkDerivation (finalAttrs: {
    pname = "estrogen";
    version = "5.0.8+1.21.1"; # Please update me >.<

    src = config.flake-root;

    nativeBuildInputs = [gradle96];

    mitmCache = gradle96.fetchDeps {
      pkg = finalAttrs.finalPackage;
      data = ./deps.json;
    };

    # Required by the dependency-recording proxy on Darwin.
    __darwinAllowLocalNetworking = true;

    gradleFlags = [
      "-Dorg.gradle.java.home=${pkgs.jdk21.home}"
      "-Pdevauth_enabled=false"
    ];

    gradleInitScript = pkgs.writeText "estrogen-gradle-init.gradle" ''
      gradle.projectsLoaded {
        rootProject.allprojects {
          tasks.withType(AbstractArchiveTask).configureEach {
            preserveFileTimestamps = false
            reproducibleFileOrder = true
          }
        }
      }
    '';

    gradleBuildTask = "nixReleaseArtifacts";

    gradleUpdateTask = "nixReleaseArtifacts";

    # Cloche's Fabric run preparation reads build/modId.txt while
    preBuild = ''
      # for some reason it needs this folder, don't ask me why >.>
      mkdir -p run
      gradle writeModId generateFabricMappingsArtifact
    '';

    installPhase = ''
      runHook preInstall

      jars=(build/nix-artifacts/*.jar)
      if [[ ! -e "''${jars[0]}" || "''${#jars[@]}" -ne 2 ]]; then
        echo "Expected exactly two loader jars in build/nix-artifacts" >&2
        find build/nix-artifacts -maxdepth 1 -type f -print >&2 || true
        exit 1
      fi

      mkdir -p "$out/share/estrogen"
      cp -v "''${jars[@]}" "$out/share/estrogen/"

      runHook postInstall
    '';
  })
