# --- flake-parts/_bootstrap.nix
{ lib }:
rec {
  # This nix file is used to minimally set up and bootstrap the `loadParts`
  # function which is then used to load all the modules in the
  #  `./flake-parts` directory. The user also has the option to remove
  # this file and directly load the `loadParts` function from
  # the `lib` attribute of the `github:tsandrini/flake-parts-builder`
  # flake, however, that brings an additional dependency to the project,
  # which may be undesirable for some and isn't really necessary.


  /*
    Main function for recursively traversing and loading all the modules 
    in a provided flake-parts  directory.

    For more information and specifics on how this function works, see the
    doccomment of the `loadModules` function below.

    *Type*: `loadParts :: Path -> { name :: String; value :: AttrSet a; }`
  */
  loadParts = dir: flatten (mapModules dir (x: x));

  /*
    Recursively flattens a nested attrset into a list of just its values.

    *Type*: `flatten :: AttrSet a -> [a]`

    Example:
    ```nix title="Example" linenums="1"
    flatten {
      keyA = 10;
      keyB = "str20";
      keyC = {
        keyD = false;
        keyE = {
          a = 10;
          b = "20";
          c = false;
        };
      };
    }
     => [ 10 "str20" false 10 "20" false ]
    ```
  */
  flatten = attrs: lib.collect (x: !lib.isAttrs x) attrs;

  /*
    Apply a map to every attribute of an attrset and then filter the resulting
    attrset based on a given predicate function.

    *Type*: `mapFilterAttrs :: (AttrSet b -> Bool) -> (AttrSet a -> AttrSet b) -> AttrSet a -> AttrSet b`
  */
  mapFilterAttrs =
    pred: f: attrs:
    lib.filterAttrs pred (lib.mapAttrs' f attrs);

  /*
    Recursively read a directory and apply a provided function to every `.nix`
    file. Returns an attrset that reflects the filenames and directory
    structure of the root.

    Notes:

     1. Files and directories starting with the `_` or `.git` prefix will be
        completely ignored.

     2. If a directory with a `myDir/default.nix` file will be encountered,
        the function will be applied to the `myDir/default.nix` file
        instead of recursively loading `myDir` and applying it to every file.

    *Type*: `mapModules :: Path -> (Path -> AttrSet a) -> { name :: String; value :: AttrSet a; }`

    Example:
    ```nix title="Example" linenums="1"
    mapModules ./modules import
      => { hardware = { moduleA = { ... }; }; system = { moduleB = { ... }; }; }

    mapModules ./hosts (host: mkHostCustomFunction myArg host)
      => { hostA = { ... }; hostB = { ... }; }
    ```
  */
  mapModules =
    dir: fn:
    mapFilterAttrs (n: v: v != null && !(lib.hasPrefix "_" n) && !(lib.hasPrefix ".git" n)) (
      n: v:
      let
        path = "${toString dir}/${n}";
      in
      if v == "directory" && builtins.pathExists "${path}/default.nix" then
        lib.nameValuePair n (fn path)
      else if v == "directory" then
        lib.nameValuePair n (mapModules path fn)
      else if v == "regular" && n != "default.nix" && lib.hasSuffix ".nix" n then
        lib.nameValuePair (lib.removeSuffix ".nix" n) (fn path)
      else
        lib.nameValuePair "" null
    ) (builtins.readDir dir);
}
