import groovy.json.JsonBuilder

/* Libraries -----------------------------------------------------------------*/

ci = load 'ci/jenkins.groovy'
nix = load 'ci/nix.groovy'
utils = load 'ci/utils.groovy'

/* Small Helpers -------------------------------------------------------------*/

def pkgUrl(build) {
  return utils.getEnv(build, 'PKG_URL')
}

def updateBucketJSON(urls, fileName) {
  /* latest.json has slightly different key names */
  def content = [
    DIAWI: urls.Diawi,
    APK: urls.Apk, IOS: urls.iOS,
    APP: urls.App, MAC: urls.Mac,
    WIN: urls.Win, SHA: urls.SHA
  ]
  def filePath = "${pwd()}/pkg/${fileName}"
  /* it might not exist */
  sh 'mkdir -p pkg'
  def contentJson = new JsonBuilder(content).toPrettyString()
  println "${fileName}:\n${contentJson}"
  new File(filePath).write(contentJson)
  return utils.uploadArtifact(filePath)
}

def prep(type = 'nightly') {
  /* build/downloads all nix deps in advance */
  nix.prepEnv()
  /* rebase unless this is a release build */
  utils.doGitRebase()
  /* ensure that we start from a known state */
  sh 'make clean'
  /* Disable git hooks in CI, it's not useful, takes time and creates weird errors at times
     (e.g. bin/sh: 2: /etc/ssl/certs/ca-certificates.crt: Permission denied) */
  sh 'make disable-githooks'

  /* pick right .env and update from params */
  utils.updateEnv(type)

  if (env.TARGET_OS == 'android' || env.TARGET_OS == 'ios') {
    /* Run at start to void mismatched numbers */
    utils.genBuildNumber()
  }

  nix.shell('watchman watch-del-all', attr: 'targets.watchman.shell')

  if (env.TARGET_OS == 'ios') {
    /* install ruby dependencies */
    nix.shell(
      'bundle install --gemfile=fastlane/Gemfile --quiet',
      attr: 'targets.mobile.fastlane.shell')
  }

  if (env.TARGET_OS == 'macos' || env.TARGET_OS == 'linux' || env.TARGET_OS == 'windows') {
    /* node deps, pods, and status-go download */
    utils.nix.shell('scripts/prepare-for-desktop-platform.sh', pure: false)
    sh('scripts/copy-translations.sh')
  } else if (env.TARGET_OS != 'android') {
    // run script in the nix shell so that node_modules gets instantiated before attempting the copies
    utils.nix.shell('scripts/copy-translations.sh chmod')
  }
}

return this
