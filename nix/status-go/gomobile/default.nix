{ stdenv, target-os, callPackage, utils, fetchgit,
  buildGoPackage, glibc, ncurses5, zlib, makeWrapper, patchelf,
  platform-tools, composeXcodeWrapper, xcodewrapperArgs ? {}
}:

with stdenv;

let
  xcodeWrapper = composeXcodeWrapper xcodewrapperArgs;
  platform = callPackage ../../platform.nix { inherit target-os; };

in buildGoPackage rec {
  name = "gomobile-${version}";
  version = "20190319-${lib.strings.substring 0 7 rev}";
  rev = "167ebed0ec6dd457a6b24a4f61db913f0af11f70";
  sha256 = "0lspdhikhnhbwv8v0q6fs3a0pd9sjnhkpg8z03m2dc5h6f84m38w";

  goPackagePath = "golang.org/x/mobile";
  subPackages = [ "bind" "cmd/gobind" "cmd/gomobile" ];

  buildInputs = [ makeWrapper ]
    ++ lib.optional isDarwin xcodeWrapper;

  # Ensure XCode and the iPhone SDK are present, instead of failing at the end of the build
  preConfigure = lib.optionalString isDarwin utils.enforceiPhoneSDKAvailable;

  patches = [ ./ndk-search-path.patch ./resolve-nix-android-sdk.patch ]
    ++ lib.optional isDarwin ./ignore-nullability-error-on-ios.patch;

  postPatch =
    lib.optionalString platform.targetAndroid ''
    substituteInPlace cmd/gomobile/install.go --replace "\`adb\`" "\`${platform-tools}/bin/adb\`"
    '' + ''
    WORK=$NIX_BUILD_TOP/gomobile-work

    # Prevent a non-deterministic temporary directory from polluting the resulting object files
    substituteInPlace cmd/gomobile/env.go --replace \
      'tmpdir, err = ioutil.TempDir("", "gomobile-work-")' \
      "tmpdir = \"$WORK\"" \
      --replace '"io/ioutil"' ""

    echo "Creating $dev"
    mkdir -p $dev/src/$goPackagePath
    echo "Copying from $src"
    cp -a $src/. $dev/src/$goPackagePath
  '';

  preBuild = ''
    mkdir $WORK
  '';
  postBuild = ''
    rm -rf $WORK
  '';

  postInstall = ''
    mkdir -p $out $bin/lib

    ln -s ${ncurses5}/lib/libncursesw.so.5 $bin/lib/libtinfo.so.5
  '' + (if isDarwin then ''
    wrapProgram $bin/bin/gomobile \
      --prefix "PATH" : "${lib.makeBinPath [ xcodeWrapper ]}" \
      --prefix "LD_LIBRARY_PATH" : "${lib.makeLibraryPath [ ncurses5 zlib ]}:$bin/lib"
  '' else ''
    wrapProgram $bin/bin/gomobile \
      --prefix "LD_LIBRARY_PATH" : "${lib.makeLibraryPath [ ncurses5 zlib ]}:$bin/lib"
  '') + ''
    $bin/bin/gomobile init
  '';

  src = fetchgit {
    inherit rev sha256;
    url = "https://go.googlesource.com/mobile";
  };

  outputs = [ "bin" "dev" "out" ];

  meta = {
    description = "A tool for building and running mobile apps written in Go.";
    longDescription = "Gomobile is a tool for building and running mobile apps written in Go.";
    homepage = https://go.googlesource.com/mobile;
    license = lib.licenses.bsdOriginal;
    maintainers = with lib.maintainers; [ sheenobu pombeirp ];
    platforms = with lib.platforms; linux ++ darwin;
  };
}
