{ stdenv, callPackage, target-os,
  cmake, extra-cmake-modules, file, status-go,
  darwin }:

with stdenv;

let
  platform = callPackage ../platform.nix { inherit target-os; };
  linuxPlatform = callPackage ./linux { inherit status-go; };
  darwinPlatform = callPackage ./macos { inherit status-go darwin; };
  windowsPlatform = callPackage ./windows { };
  snoreNotifySources = callPackage ./cmake/snorenotify { };
  qtkeychainSources = callPackage ./cmake/qtkeychain { };
  selectedSources =
    lib.optional platform.targetLinux linuxPlatform ++
    lib.optional platform.targetDarwin darwinPlatform ++
    lib.optional platform.targetWindows windowsPlatform;

in
  {
    buildInputs = [
      cmake
      extra-cmake-modules
      file
      snoreNotifySources
      qtkeychainSources
    ] ++ lib.catAttrs "buildInputs" selectedSources;
    shellHook = lib.concatStrings (lib.catAttrs "shellHook" (selectedSources ++ [ snoreNotifySources qtkeychainSources ]));
  }
