# --- flake-parts/treefmt.nix
{ inputs, ... }:
{
  imports = with inputs; [ treefmt-nix.flakeModule ];

  perSystem =
    { pkgs, ... }:
    {
      treefmt = {
        # treefmt is a formatting tool that saves you time: it provides
        # developers with a universal way to trigger all formatters needed for the
        # project in one place.
        # For more information refer to
        #
        # - https://numtide.github.io/treefmt/
        # - https://github.com/numtide/treefmt-nix
        package = pkgs.treefmt;
        flakeCheck = true;
        flakeFormatter = true;
        projectRootFile = "flake.nix";

        settings = {
          global.excludes = [
            "*.age" # Age encrypted files
          ];
          shellcheck.includes = [
            "*.sh"
            ".envrc"
          ];
          prettier.editorconfig = true;
        };

        programs = {
          deadnix.enable = true; # Find and remove unused code in .nix source files
          statix.enable = true; # Lints and suggestions for the nix programming language
          nixfmt.enable = true; # An opinionated formatter for Nix

          prettier.enable = true; # Prettier is an opinionated code formatter
          yamlfmt.enable = true; # An extensible command line tool or library to format yaml files.
          jsonfmt.enable = true; # Formatter for JSON files
          # mdformat.enable = true; # CommonMark compliant Markdown formatter

          # shellcheck.enable = true; # Shell script analysis tool
          # shfmt.enable = true; # Shell parser and formatter

          # actionlint.enable = true; # Static checker for GitHub Actions workflow files
          # mdsh.enable = true; # Markdown shell pre-processor
        };
      };
    };
}
